package org.jemiahlabs.skrls.view.aboutme;

import org.jemiahlabs.skrls.gui.ApplicationContext;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;
import org.jemiahlabs.skrls.view.base.WindowBuildable;
import org.jemiahlabs.skrls.view.base.javafxwindows.StageBuilder;

public class AboutMeViewBuilder implements WindowBuildable {
	private PrincipalWindow principalWindow;
	
	public AboutMeViewBuilder(PrincipalWindow principalWindow) {
		this.principalWindow = principalWindow;
	}

	@Override
	public Window build() {
		var appContext = ApplicationContext.getInstance();
		StageBuilder builder = new StageBuilder("/view/aboutmestage.fxml");
		
		return builder.setTitle(appContext.getName() + "-About Me")
			.setIcon(appContext.getIcon())
			.setPrincipalWindow(principalWindow)
			.undecorated()
			.build();
	}

}
