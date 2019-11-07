package org.jemiahlabs.skrls.context;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

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
	
	public void addPlugin(String uri, SuccessCase<Plugin> successCase, FailedCase<AbortivedPluginLoadException, String> failedCase) {
		try {
			File fileJar = new File(uri);
			
			if(!fileJar.exists() || !fileJar.isFile()) {
				failedCase.failed(new AbortivedPluginLoadException("File jar not exists: " + fileJar.getPath()), uri);
				return;
			}
			
			Plugin plugin = pluginLoader.loadPlugin(fileJar, true);
			
			plugins.put(plugin.getNameable(), plugin);
			successCase.success(plugin);
			
		} catch (AbortivedPluginLoadException e) {
			failedCase.failed(e, uri);
		} 
	}
	
	public void removePlugin(Nameable nameable) {
		plugins.remove(nameable);
	}
	
	public void LoaderPlugins(SuccessCase<List<Plugin>> successCase, FailedCase<PluginsNotLoadException, String> failedCase) {
		try {
			List<Plugin> pluginsCorrect = pluginLoader.loadPlugins();
			
			pluginsCorrect.forEach((plugin) -> plugins.put(plugin.getNameable(), plugin));
			successCase.success(Collections.unmodifiableList(pluginsCorrect));
			
		} catch (PluginsNotLoadException e) {
			failedCase.failed(e, e.getMessage());
		}
	}
	
	public Plugin[] getPlugins() {
		return (Plugin[]) plugins.values().toArray();
	}
	
	public Plugin getPlugin(Nameable nameable) {
		return plugins.get(nameable);
	}
	
	public List<Nameable> getNamePlugins() {
		return new ArrayList<Nameable>();
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
	
}
