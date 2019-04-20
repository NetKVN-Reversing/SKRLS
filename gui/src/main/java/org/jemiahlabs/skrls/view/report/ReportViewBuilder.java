package org.jemiahlabs.skrls.view.report;

import org.jemiahlabs.skrls.gui.ApplicationContext;
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
		var appContext = ApplicationContext.getInstance();
		StageBuilder builder = new StageBuilder("/view/reportstage.fxml");
		
		return builder.setTitle(appContext.getName() + "-Report")
			.setIcon(appContext.getIcon())
			.setPrincipalWindow(principalWindow)
			.undecorated()
			.build();
	}
}
