package org.jemiahlabs.skrls.view.main;

import org.jemiahlabs.skrls.gui.ApplicationContext;
import org.jemiahlabs.skrls.view.base.Window;
import org.jemiahlabs.skrls.view.base.WindowBuildable;
import org.jemiahlabs.skrls.view.base.javafxwindows.StageBuilder;

import javafx.stage.Stage;

public class MainViewBuilder implements WindowBuildable {
	private Stage primaryStage;
	
	public MainViewBuilder(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	@Override
	public Window build() {
		var appContext = ApplicationContext.getInstance();
		StageBuilder builder = new StageBuilder("/view/mainstage.fxml", primaryStage);
		
		return builder.setTitle(appContext.getName() + "-workspace")
			.setIcon(appContext.getIcon())
			.build();
	}

}
