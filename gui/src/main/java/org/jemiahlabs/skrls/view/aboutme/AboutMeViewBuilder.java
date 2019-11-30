package org.jemiahlabs.skrls.view.aboutme;

import org.jemiahlabs.skrls.gui.ApplicationServiceProvider;
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
		var appService = ApplicationServiceProvider.getInstance();
		StageBuilder builder = new StageBuilder("/view/aboutmestage.fxml");
		
		return builder.setTitle(appService.getName() + "-About Me")
			.setIcon(appService.getIcon())
			.setPrincipalWindow(principalWindow)
			.undecorated()
			.build();
	}

}
