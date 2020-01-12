package org.jemiahlabs.skrls.view.extensions;

import org.jemiahlabs.skrls.view.base.SubWindow;
import org.jemiahlabs.skrls.view.base.javafxwindows.StageController;

import java.util.List;

import org.jemiahlabs.skrls.context.Plugin;

public interface ExtensionsViewController extends StageController, SubWindow {
	void showMessage(String title, String text, Runnable action);
	void showProgress();
	void hiddenProgress();
	
	void updatePluginsListView(List<Plugin> plugins, boolean reloadPlugins);
}
