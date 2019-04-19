package org.jemiahlabs.skrls.gui;

import org.jemiahlabs.skrls.view.base.javafxwindows.StageBuilder;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application
{
    public static void main( String[] args ) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		StageBuilder builder = new StageBuilder("/view/aboutmestage.fxml", primaryStage);
		builder
			.setTitle("Test Javaxfx")
			.draggable()
			.build()
			.show();
	}
}