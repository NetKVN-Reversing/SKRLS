package org.jemiahlabs.skrls.context;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import org.jemiahlabs.skrls.context.events.FailedCase;
import org.jemiahlabs.skrls.context.events.SuccessCase;
import org.jemiahlabs.skrls.context.exceptions.AbortivedPluginLoadException;
import org.jemiahlabs.skrls.context.exceptions.PluginsNotLoadException;
import org.jemiahlabs.skrls.context.jarloader.PluginLoader;
import org.jemiahlabs.skrls.core.Nameable;
import org.jemiahlabs.skrls.core.Receiver;

public class ApplicationContext {
	private static ApplicationContext context;
	
	private PluginLoader pluginLoader;
	private Hashtable<Nameable, Plugin> plugins;
	private Receiver receiver;
	
	public static ApplicationContext GetNewInstance(Receiver receiver) {
		context = new ApplicationContext(receiver);
		return context;
	}
	
	public static ApplicationContext GetInstance() {
		return context;
	}
	
	private ApplicationContext(Receiver receiver) {
		pluginLoader = new PluginLoader();
		plugins = new Hashtable<Nameable, Plugin>(5);
		this.receiver = receiver;
	}
	
	public void addPlugin(String uri, SuccessCase<Plugin> successCase, FailedCase<AbortivedPluginLoadException> failedCase) {
		try {
			File fileJar = new File(uri);			
			if(!fileJar.exists() || !fileJar.isFile()) {
				failedCase.failed(new AbortivedPluginLoadException("File jar not exists: " + uri));
				return;
			}			
			Plugin plugin = pluginLoader.loadPlugin(fileJar, true);			
			plugins.put(plugin.getNameable(), plugin);
			successCase.success(plugin);			
		} catch (AbortivedPluginLoadException e) {
			failedCase.failed(e);
		} 
	}
	
	public void removePlugin(Nameable nameable) {
		Plugin plugin = plugins.remove(nameable);
		try {
			pluginLoader.removePlugin(plugin);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loaderPlugins(SuccessCase<List<Plugin>> successCase, FailedCase<PluginsNotLoadException> failedCase) {
		try {
			List<Plugin> pluginsCorrect = pluginLoader.loadPlugins(failedCase);			
			pluginsCorrect.forEach((plugin) -> plugins.put(plugin.getNameable(), plugin));
			successCase.success(Collections.unmodifiableList(pluginsCorrect));			
		} catch (AbortivedPluginLoadException  e) {
			failedCase.failed(new PluginsNotLoadException(e));
		}
	}

	public List<Plugin> getPlugins() {
		return plugins.values()
				.stream()
				.collect(Collectors.toList());
	}
	
	public Plugin getPlugin(Nameable nameable) {
		return plugins.get(nameable);
	}
	
	public List<Nameable> getNamePlugins() {
		return plugins.keySet()
				.stream()
				.collect(Collectors.toList());
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
	
}
