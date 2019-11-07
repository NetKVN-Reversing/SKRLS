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
import org.jemiahlabs.skrls.context.exceptions.AbortivedPluginLoadException;
import org.jemiahlabs.skrls.context.exceptions.PluginsNotLoadException;
import org.jemiahlabs.skrls.core.ExtractableKnowledge;
import org.jemiahlabs.skrls.core.Nameable;

public class PluginLoader {
	public final static String PLUGINS_DIRECTORY = System.getProperty("user.home") + System.getProperty("file.separator") + "skrlsplugins";
	
	public static Plugin createPlugin(Nameable nameable, ExtractableKnowledge extractableKnowledgeClass) {
		return new Plugin(nameable, extractableKnowledgeClass);
	}

	public PluginLoader() {
		
	}
	
	public Plugin loadPlugin(File fileJar, boolean isNewPlugin) throws AbortivedPluginLoadException {
		try {
			if(isNewPlugin)
				copyJarFileToPluginsDirectory(fileJar);
			
			return resolvePlugin(fileJar);
			
		} catch (IOException e) {
			throw new AbortivedPluginLoadException(e.toString(), e.getCause());
		} catch (ClassNotFoundException e) {
			throw new AbortivedPluginLoadException(e.toString(), e.getCause());
		} catch (NoSuchMethodException e) {
			throw new AbortivedPluginLoadException(e.toString(), e.getCause());
		} catch (SecurityException e) {
			throw new AbortivedPluginLoadException(e.toString(), e.getCause());
		} catch (InstantiationException e) {
			throw new AbortivedPluginLoadException(e.toString(), e.getCause());
		} catch (IllegalAccessException e) {
			throw new AbortivedPluginLoadException(e.toString(), e.getCause());
		} catch (IllegalArgumentException e) {
			throw new AbortivedPluginLoadException(e.toString(), e.getCause());
		} catch (InvocationTargetException e) {
			throw new AbortivedPluginLoadException(e.toString(), e.getCause());
		} catch (NullPointerException e) {
			throw new AbortivedPluginLoadException(e.toString(), e.getCause());
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new AbortivedPluginLoadException("File is not type .jar", e.getCause());
		} catch (Exception e) {
			throw new AbortivedPluginLoadException(e.toString(), e.getCause());
		}
	}
	
	public List<Plugin> loadPlugins() throws PluginsNotLoadException {
		File pluginsDir = new File(PLUGINS_DIRECTORY);
		List<Plugin> pluginsCorrect = new ArrayList<Plugin>();
		
		if(!pluginsDir.exists() || !pluginsDir.isDirectory() )
			throw new PluginsNotLoadException("Not have plugins for load");
		
		for (File folderPlugin : pluginsDir.listFiles()) {
			for (File fileJar: folderPlugin.listFiles( (file) -> file.getName().endsWith(".jar") )) {
				try {
					Plugin plugin = loadPlugin(fileJar, false);
					pluginsCorrect.add(plugin);
				
				} catch (AbortivedPluginLoadException e) {
					System.out.println(e.getMessage());
					continue;
				}
			}
		}
		
		return pluginsCorrect;
	}
	
	private void copyJarFileToPluginsDirectory(File fileJar) throws IOException, ArrayIndexOutOfBoundsException {
		File pluginsDir = copyPluginToPluginsDirectory(fileJar);
		copyPluginDependeciesToPluginsDirectory(fileJar, pluginsDir);
	}
	
	private File copyPluginToPluginsDirectory(File fileJar) throws IOException, ArrayIndexOutOfBoundsException {
		String folderInside = fileJar.getName().split("\\.jar")[0];
		File pluginsDir = new File(PLUGINS_DIRECTORY + System.getProperty("file.separator") + folderInside);
		
		if (!pluginsDir.exists()) 
			Files.createDirectories(pluginsDir.toPath());
		
        Files.copy(
        	fileJar.toPath(), 
        	Paths.get(pluginsDir.getAbsolutePath(), System.getProperty("file.separator"), fileJar.getName()), 
        	StandardCopyOption.REPLACE_EXISTING
        );
        
        return pluginsDir;
	}
	
	private void copyPluginDependeciesToPluginsDirectory(File fileJar, File pluginsDir) throws IOException {
		File pluginDependecies = new File(fileJar.getParent() + System.getProperty("file.separator") + "lib");
        
        if(!pluginDependecies.exists() || !pluginDependecies.isDirectory())
        	return;
        
        File pathDestination = new File(pluginsDir.getAbsolutePath() + System.getProperty("file.separator") + pluginDependecies.getName());
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
		
		return createPlugin(constructorNameable.newInstance(), constructorExtractableKnowledge.newInstance());
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
