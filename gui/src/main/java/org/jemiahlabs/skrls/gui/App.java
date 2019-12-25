package org.jemiahlabs.skrls.gui;

import org.jemiahlabs.skrls.gui.services.ConfigurationLoaderService;
import org.jemiahlabs.skrls.gui.services.LoggerFormatService;
import org.jemiahlabs.skrls.view.base.EventArgs;
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
    	appService.addAttribute("logger-format", new LoggerFormatService());
    	appService.addAttribute("configuration-loader", new ConfigurationLoaderService("skrsconfig.temp"));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		var mainView = WindowBuildDirector
			.createWindow(new MainViewBuilder(primaryStage));
		
		mainView.setParams(EventArgs.Empty());
		mainView.show();
	}
}