package org.jemiahlabs.skrls.view.main;

import java.util.List;
import java.util.Queue;

import org.jemiahlabs.skrls.context.Plugin;
import org.jemiahlabs.skrls.core.Nameable;
import org.jemiahlabs.skrls.view.main.context.Configuration;

public interface PresenterMainView {
	void analyzeSourceCode(String sourceCode, String outputDir, Nameable nameable);
	void loadPlugins();
	List<Plugin> getPlugins();
	void loadConfiguration();
	void saveConfiguration(Queue<Configuration> configurations);
}
