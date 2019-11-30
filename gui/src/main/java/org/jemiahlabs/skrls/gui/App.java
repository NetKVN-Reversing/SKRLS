package org.jemiahlabs.skrls.gui;

import org.jemiahlabs.skrls.view.base.WindowBuildDirector;
import org.jemiahlabs.skrls.view.main.MainViewBuilder;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application
{
    public static void main( String[] args ) {
        launch(args);
    }
    
    @Override
	public void init() throws Exception {
    	ApplicationServiceProvider appService = ApplicationServiceProvider.getInstance();
    	appService.addAttribute("host-services", getHostServices());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		var mainView = WindowBuildDirector
			.createWindow(new MainViewBuilder(primaryStage));
		
		mainView.setParams(null);
		mainView.show();
	}
}