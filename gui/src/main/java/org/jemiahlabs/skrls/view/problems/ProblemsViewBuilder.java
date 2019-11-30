package org.jemiahlabs.skrls.view.problems;

import org.jemiahlabs.skrls.gui.ApplicationServiceProvider;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;
import org.jemiahlabs.skrls.view.base.WindowBuildable;
import org.jemiahlabs.skrls.view.base.javafxwindows.StageBuilder;

public class ProblemsViewBuilder implements WindowBuildable {
	private PrincipalWindow principalWindow;
	
	public ProblemsViewBuilder(PrincipalWindow principalWindow) {
		this.principalWindow = principalWindow;
	}

	@Override
	public Window build() {
		var appService = ApplicationServiceProvider.getInstance();
		StageBuilder builder = new StageBuilder("/view/problemsstage.fxml");
		
		return builder.setTitle(appService.getName() + "-Problems")
			.setIcon(appService.getIcon())
			.setPrincipalWindow(principalWindow)
			.undecorated()
			.build();
	}

}
