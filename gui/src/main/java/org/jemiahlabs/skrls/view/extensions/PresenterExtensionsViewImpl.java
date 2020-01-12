package org.jemiahlabs.skrls.view.extensions;

import java.util.List;

import org.jemiahlabs.skrls.context.ApplicationContext;
import org.jemiahlabs.skrls.context.Plugin;
import org.jemiahlabs.skrls.core.Nameable;

public class PresenterExtensionsViewImpl implements PresenterExtensionsView {
	private ExtensionsViewController controller;
	private ApplicationContext context;
	
	public PresenterExtensionsViewImpl(ExtensionsViewController controller) {
		this.controller = controller;
		context = ApplicationContext.GetInstance();
	}
	
	@Override
	public List<Plugin> listPlugins() {
		return context.getPlugins();
	}

	@Override
	public void addPlugin(String filejar) {
		controller.showProgress();
		new Thread(() -> {
			context.addPlugin(filejar, 
					(plugin) -> controller.showMessage("Extension",  String.format("has added new plugin \"%s\"", plugin.getNameable().getNameProduct()), () -> {}),
					(ex) -> controller.showMessage("Extension", ex.getMessage(), () -> {}));
			controller.updatePluginsListView(context.getPlugins(), true);
			controller.hiddenProgress();
		})
		.start();
	}

	@Override
	public void removePlugin(Nameable nameable) {
		context.removePlugin(nameable);
		controller.updatePluginsListView(context.getPlugins(), true);
	}

}
