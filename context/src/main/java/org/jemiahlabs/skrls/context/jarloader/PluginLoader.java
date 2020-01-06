package org.jemiahlabs.skrls.context.jarloader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jemiahlabs.skrls.context.Plugin;
import org.jemiahlabs.skrls.context.events.FailedCase;
import org.jemiahlabs.skrls.context.exceptions.AbortivedPluginLoadException;
import org.jemiahlabs.skrls.context.exceptions.PluginsNotLoadException;
import org.jemiahlabs.skrls.core.ExtractableKnowledge;
import org.jemiahlabs.skrls.core.Nameable;

public class PluginLoader {
	public final static String PLUGINS_DIRECTORY = System.getProperty("user.home") + System.getProperty("file.separator") + "skrlsplugins";
	
	public static Plugin createPlugin(Nameable nameable, ExtractableKnowledge extractableKnowledgeClass, String location, URLClassLoader classLoader) {
		return new Plugin(nameable, extractableKnowledgeClass, location, classLoader);
	}

	public PluginLoader() {
		
	}
	
	public Plugin loadPlugin(File fileJar, boolean isNewPlugin) throws AbortivedPluginLoadException {
		try {
			if(isNewPlugin) {
				File pluginDir = copyJarFileTopluginDirectory(fileJar);
				fileJar = new File(pluginDir.getAbsolutePath() + System.getProperty("file.separator") + fileJar.getName());
			}
			
			Plugin pluginLoaded = resolvePlugin(fileJar);									
			return pluginLoaded;
			
		} catch (IOException e) {
			throw new AbortivedPluginLoadException(e.getMessage(), e.getCause());
		} catch (ClassNotFoundException e) {
			throw new AbortivedPluginLoadException(e.getMessage(), e.getCause());
		} catch (NoSuchMethodException e) {
			throw new AbortivedPluginLoadException(e.getMessage(), e.getCause());
		} catch (SecurityException e) {
			throw new AbortivedPluginLoadException(e.getMessage(), e.getCause());
		} catch (InstantiationException e) {
			throw new AbortivedPluginLoadException(e.getMessage(), e.getCause());
		} catch (IllegalAccessException e) {
			throw new AbortivedPluginLoadException(e.getMessage(), e.getCause());
		} catch (IllegalArgumentException e) {
			throw new AbortivedPluginLoadException(e.getMessage(), e.getCause());
		} catch (InvocationTargetException e) {
			throw new AbortivedPluginLoadException(e.getMessage(), e.getCause());
		} catch (NullPointerException e) {
			throw new AbortivedPluginLoadException(e.getMessage(), e.getCause());
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new AbortivedPluginLoadException("File is not type .jar", e.getCause());
		} catch (Exception e) {
			throw new AbortivedPluginLoadException(e.getMessage(), e.getCause());
		} catch(NoClassDefFoundError e) {
			throw new AbortivedPluginLoadException(e.getMessage(), e.getCause());
		}
	}
	
	public List<Plugin> loadPlugins() throws AbortivedPluginLoadException {
		return loadPlugins((ex) -> {});
	}
	
	public List<Plugin> loadPlugins(FailedCase<PluginsNotLoadException> failedCase) throws AbortivedPluginLoadException  {
		File pluginDir = new File(PLUGINS_DIRECTORY);
		List<Plugin> pluginsCorrect = new ArrayList<Plugin>();
		
		if(!pluginDir.exists() || !pluginDir.isDirectory() )
			throw new AbortivedPluginLoadException ("Not have plugins for load");
		
		for (File folderPlugin : pluginDir.listFiles()) {
			for (File fileJar: folderPlugin.listFiles( (file) -> file.getName().endsWith(".jar") )) {
				try {
					Plugin plugin = loadPlugin(fileJar, false);
					pluginsCorrect.add(plugin);				
				} catch (AbortivedPluginLoadException e) {
					failedCase.failed(new PluginsNotLoadException(e));
					continue;
				}
			}
		}
		
		return pluginsCorrect;
	}
	
	public void removePlugin(Plugin plugin) throws IOException {
		File file = new File(plugin.getLocation());
		plugin.disconect();
		removeDirectory(file);
	}
	
	private void removeFile(File file) {
		try {
			Files.deleteIfExists(file.toPath());
		} catch (IOException e) {
			System.out.println(file.getAbsolutePath() + ": " + e.getMessage());
		}
	}
	
	private void removeDirectory(File file) {
		File[] contents = file.listFiles();
		for(File content: contents) {
			if(content.isDirectory()) removeDirectory(content);
			else removeFile(content);
		}
		removeFile(file);
	}
	
	private File copyJarFileTopluginDirectory(File fileJar) throws IOException, ArrayIndexOutOfBoundsException {
		File pluginDir = copyPluginTopluginDirectory(fileJar);
		copyPluginDependeciesTopluginDirectory(fileJar, pluginDir);
		return pluginDir;
	}
	
	private File copyPluginTopluginDirectory(File fileJar) throws IOException, ArrayIndexOutOfBoundsException {
		String folderInside = fileJar.getName().split("\\.jar")[0];
		File pluginDir = new File(PLUGINS_DIRECTORY + System.getProperty("file.separator") + folderInside);
		
		if (!pluginDir.exists()) 
			Files.createDirectories(pluginDir.toPath());
		
        Files.copy(
        	fileJar.toPath(), 
        	Paths.get(pluginDir.getAbsolutePath(), System.getProperty("file.separator"), fileJar.getName()), 
        	StandardCopyOption.REPLACE_EXISTING
        );
        
        return pluginDir;
	}
	
	private void copyPluginDependeciesTopluginDirectory(File fileJar, File pluginDir) throws IOException {
		File pluginDependecies = new File(fileJar.getParent() + System.getProperty("file.separator") + "lib");
        
        if(!pluginDependecies.exists() || !pluginDependecies.isDirectory())
        	return;
        
        File pathDestination = new File(pluginDir.getAbsolutePath() + System.getProperty("file.separator") + pluginDependecies.getName());
        if(!pathDestination.exists()) {
        	Files.copy(
                pluginDependecies.toPath(), 
                pathDestination.toPath(), 
                StandardCopyOption.REPLACE_EXISTING
            );
        }
        
        for (File fileDependence : pluginDependecies.listFiles()) {
        	Files.copy(
        		fileDependence.toPath(), 
                Paths.get(
                	pathDestination.getAbsolutePath(), 
                	System.getProperty("file.separator"),
                	fileDependence.getName()
                ), 
                StandardCopyOption.REPLACE_EXISTING
            );
		}
	}
	
	private Plugin resolvePlugin(File fileJar) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NullPointerException {
		ClassLoader loader = loadJar(fileJar);
		Properties properties = loadProperties(loader);
		
		Constructor<? extends Nameable> constructorNameable 
			= findSubClass(loader, properties.getProperty("plugin.package.info"), Nameable.class)
				.getConstructor();
		
		Constructor<? extends ExtractableKnowledge> constructorExtractableKnowledge
			= findSubClass(loader, properties.getProperty("plugin.package.parser"), ExtractableKnowledge.class)
				.getConstructor();
		
		return createPlugin(constructorNameable.newInstance(), constructorExtractableKnowledge.newInstance(), fileJar.getParent(), (URLClassLoader)loader);
	}
	
	private Properties loadProperties(ClassLoader loader) throws IOException {
		Properties properties = new Properties();
		properties.load(loader.getResourceAsStream("plugin.properties"));		
		return properties;
	}
	
	private ClassLoader loadJar(File fileJar) throws IOException, ClassNotFoundException {
		return URLClassLoader.newInstance(
			new URL[] { fileJar.toURI().toURL() },
			getClass().getClassLoader()
		);
	}
	
	private <T> Class<? extends T> findSubClass(ClassLoader loader, String location, Class<T> typeClass) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(location, true, loader);
		Class<? extends T> newClass = clazz.asSubclass(typeClass);
		return newClass;
	}
	
}
