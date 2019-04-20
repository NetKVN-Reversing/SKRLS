package org.jemiahlabs.skrls.view.openproject;

import org.jemiahlabs.skrls.gui.ApplicationContext;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;
import org.jemiahlabs.skrls.view.base.WindowBuildable;
import org.jemiahlabs.skrls.view.base.javafxwindows.StageBuilder;

public class OpenProjectViewBuilder implements WindowBuildable {
	private PrincipalWindow principalWindow;
	
	public OpenProjectViewBuilder(PrincipalWindow principalWindow) {
		this.principalWindow = principalWindow;
	}

	@Override
	public Window build() {
		var appContext = ApplicationContext.getInstance();
		StageBuilder builder = new StageBuilder("/view/openprojectstage.fxml");
		
		return builder.setTitle(appContext.getName() + "-Open Project")
			.setIcon(appContext.getIcon())
			.setPrincipalWindow(principalWindow)
			.undecorated()
			.build();
	}
}
