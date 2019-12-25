package org.jemiahlabs.skrls.view.main;

import java.util.Queue;

import org.jemiahlabs.skrls.core.Nameable;
import org.jemiahlabs.skrls.view.main.context.Configuration;

public interface PresenterMainView {
	void analyzeSourceCode(String sourceCode, String outputDir, Nameable nameable);
	void loadPlugins();
	void loadConfiguration();
	void saveConfiguration(Queue<Configuration> configurations);
}
