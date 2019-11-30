package org.jemiahlabs.skrls.view.extensions;

import org.jemiahlabs.skrls.gui.ApplicationServiceProvider;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;
import org.jemiahlabs.skrls.view.base.WindowBuildable;
import org.jemiahlabs.skrls.view.base.javafxwindows.StageBuilder;

public class ExtensionsViewBuilder implements WindowBuildable {
	private PrincipalWindow principalWindow;
	
	public ExtensionsViewBuilder(PrincipalWindow principalWindow) {
		this.principalWindow = principalWindow;
	}

	@Override
	public Window build() {
		var appService = ApplicationServiceProvider.getInstance();
		StageBuilder builder = new StageBuilder("/view/manageextensionsstage.fxml");
		
		return builder.setTitle(appService.getName() + "-Manage Extensions")
			.setIcon(appService.getIcon())
			.setPrincipalWindow(principalWindow)
			.undecorated()
			.build();
	}
}
