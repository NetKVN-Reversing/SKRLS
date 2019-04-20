package org.jemiahlabs.skrls.view.newproject;

import org.jemiahlabs.skrls.gui.ApplicationContext;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;
import org.jemiahlabs.skrls.view.base.WindowBuildable;
import org.jemiahlabs.skrls.view.base.javafxwindows.StageBuilder;

public class NewProjectViewBuilder implements WindowBuildable {
	private PrincipalWindow principalWindow;
	
	public NewProjectViewBuilder(PrincipalWindow principalWindow) {
		this.principalWindow = principalWindow;
	}

	@Override
	public Window build() {
		var appContext = ApplicationContext.getInstance();
		StageBuilder builder = new StageBuilder("/view/newprojectstage.fxml");
		
		return builder.setTitle(appContext.getName() + "-New Project")
			.setIcon(appContext.getIcon())
			.setPrincipalWindow(principalWindow)
			.undecorated()
			.build();
	}
}
