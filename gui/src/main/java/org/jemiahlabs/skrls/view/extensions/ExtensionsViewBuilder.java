package org.jemiahlabs.skrls.view.extensions;

import org.jemiahlabs.skrls.gui.ApplicationContext;
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
		var appContext = ApplicationContext.getInstance();
		StageBuilder builder = new StageBuilder("/view/manageextensionsstage.fxml");
		
		return builder.setTitle(appContext.getName() + "-Manage Extensions")
			.setIcon(appContext.getIcon())
			.setPrincipalWindow(principalWindow)
			.undecorated()
			.build();
	}
}
