package org.jemiahlabs.skrls.context.jarloader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.jemiahlabs.skrls.context.Plugin;
import org.jemiahlabs.skrls.context.exceptions.AbortivedPluginLoadException;
import org.jemiahlabs.skrls.core.ExtractableKnowledge;
import org.jemiahlabs.skrls.core.Nameable;

public class PluginLoader {
	public final static String PLUGINS_DIRECTORY = System.getProperty("user.dir") + "skrlsplugins";
	public final static String CLASSPATH = "org.jemiahlabs.skrls.plugin";
	
	private final Class<Nameable> nameableClass;
	private final Class<ExtractableKnowledge> extractableKnowledgeClass;
	
	public static Plugin createPlugin(Nameable nameable, ExtractableKnowledge extractableKnowledgeClass) {
		return new Plugin(nameable, extractableKnowledgeClass);
	}

	public PluginLoader() {
		this.nameableClass = Nameable.class;
		this.extractableKnowledgeClass = ExtractableKnowledge.class;
	}
	
	public void createPluginsDirectory() throws IOException {
		Files.createDirectory(Paths.get(PLUGINS_DIRECTORY));
	}
	
	public Plugin loadPlugin(File fileJar) throws AbortivedPluginLoadException {
		try {
			copyJarFileToPluginsDirectory(fileJar);
			return resolvePlugin(fileJar);
			
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
		}
	}
	
	private Plugin resolvePlugin(File fileJar) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClassLoader loader = loadJar(fileJar);
		
		return findSubClass(loader);
	}
	
	private ClassLoader loadJar(File fileJar) throws MalformedURLException {
		ClassLoader loader = URLClassLoader.newInstance(
			new URL[] { fileJar.toURL() },
			getClass().getClassLoader()
		);
		
		return loader;
	}
	
	private Plugin findSubClass(ClassLoader loader) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = Class.forName(CLASSPATH, true, loader);
		Class<? extends Nameable> newClassNameable = clazz.asSubclass(nameableClass);
		Class<? extends ExtractableKnowledge> newClassExtractableKnowledge = clazz.asSubclass(extractableKnowledgeClass);
		
		Constructor<? extends Nameable> constructorNameable = newClassNameable.getConstructor();
		Constructor<? extends ExtractableKnowledge> constructorExtractableKnowledge = newClassExtractableKnowledge.getConstructor();
		
		return createPlugin(constructorNameable.newInstance(), constructorExtractableKnowledge.newInstance());
	}
	
	private void copyJarFileToPluginsDirectory(File fileJar) throws IOException {
        Path destination = Paths.get(PLUGINS_DIRECTORY);
        Files.copy(fileJar.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
	}
	
}
