package org.jemiahlabs.skrls.view.extensions;

import org.jemiahlabs.skrls.core.Nameable;

import java.util.List;

import org.jemiahlabs.skrls.context.Plugin;

public interface PresenterExtensionsView {
	public List<Plugin> listPlugins();
	public void addPlugin(String urlFile);
	public void removePlugin(Nameable nameable);
}
