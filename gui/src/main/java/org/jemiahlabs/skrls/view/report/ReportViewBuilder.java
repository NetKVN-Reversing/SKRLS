package org.jemiahlabs.skrls.view.report;

import org.jemiahlabs.skrls.gui.ApplicationServiceProvider;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;
import org.jemiahlabs.skrls.view.base.WindowBuildable;
import org.jemiahlabs.skrls.view.base.javafxwindows.StageBuilder;

public class ReportViewBuilder implements WindowBuildable {
	private PrincipalWindow principalWindow;
	
	public ReportViewBuilder(PrincipalWindow principalWindow) {
		this.principalWindow = principalWindow;
	}

	@Override
	public Window build() {
		var appService = ApplicationServiceProvider.getInstance();
		StageBuilder builder = new StageBuilder("/view/reportstage.fxml");
		
		return builder.setTitle(appService.getName() + "-Report")
			.setIcon(appService.getIcon())
			.setPrincipalWindow(principalWindow)
			.undecorated()
			.build();
	}
}
