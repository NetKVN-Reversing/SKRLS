package org.jemiahlabs.skrls.view.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

import org.jemiahlabs.skrls.context.ApplicationContext;
import org.jemiahlabs.skrls.context.Plugin;
import org.jemiahlabs.skrls.view.main.context.ProducerImpl;
import org.jemiahlabs.skrls.view.main.context.ChannelImpl;
import org.jemiahlabs.skrls.view.main.context.Configuration;
import org.jemiahlabs.skrls.core.ExtractableKnowledge;
import org.jemiahlabs.skrls.core.Nameable;
import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.gui.ApplicationServiceProvider;
import org.jemiahlabs.skrls.gui.services.ConfigurationLoaderService;
import org.jemiahlabs.skrls.gui.services.LoggerFormatService;
import org.jemiahlabs.skrls.view.main.context.ReceiverImpl;

public class PresenterMainViewImpl implements PresenterMainView {
	private MainViewController controller;
	private ApplicationContext context;
	private Producer producer;
	
	public PresenterMainViewImpl(MainViewController controller) {
		this.controller = controller;
		context = ApplicationContext.GetNewInstance(new ReceiverImpl(controller));
		producer = new ProducerImpl(new ChannelImpl(context.getReceiver()));
	}
	
	@Override
	public void analyzeSourceCode(String sourceCode, String outputDir, Nameable nameable) {
		controller.showProgress();
		new Thread(() -> {
			try {
				extractKDM(sourceCode, outputDir, nameable);				
				controller.hiddenProgress();
				controller.showMessage("SKRLS", "KDM Models Generated", () -> {
					try {
						Desktop.getDesktop().open(new File(outputDir));
					} catch (IOException e) {
						controller.updateWarningMessages("File", "Directory has been deleted or moved");
					}
				});
			} catch (Exception | NoClassDefFoundError e) {
				controller.hiddenProgress();
				printConsole("ERROR", e.getMessage());
			} 
		})
		.start();
	}
		
	@Override
	public void loadPlugins() {
		context.loaderPlugins(plugins -> {
			controller.updateInfoMessages("Extensions", "Extensions loaded correctly");
			controller.updateTargetLanguages(plugins);
		}, 
		(ex) -> {
			controller.updateInfoMessages("Extensions", ex.getMessage());
		});
	}
	
	@Override
	public void loadConfiguration() {
		ConfigurationLoaderService service = (ConfigurationLoaderService) ApplicationServiceProvider.getInstance().getAttribute("configuration-loader");
		service.<Queue<Configuration>>loadConfiguration(configurations -> {
			if(configurations != null) {
				controller.loadLastConfiguration(configurations);
			} else {
				controller.updateInfoMessages("Recent", "Not found configurations");
			}
		});
	}

	@Override
	public void saveConfiguration(Queue<Configuration> configurations) {
		ConfigurationLoaderService service = (ConfigurationLoaderService) ApplicationServiceProvider.getInstance().getAttribute("configuration-loader");
    	service.saveConfiguration(configurations);
	}
	
	private void extractKDM(String sourceCode, String outputDir, Nameable nameable) {
		ExtractableKnowledge extractableKnowledge = context.getPlugin(nameable).getExtractableKnowledge();
		extractableKnowledge.extractKDM(producer, sourceCode, outputDir);
	}
	
	private void printConsole(String topic, String Message) {
		LoggerFormatService loggerFormat = (LoggerFormatService) ApplicationServiceProvider.getInstance().getAttribute("logger-format");
		controller.appendMessageToConsole(topic, loggerFormat.getMessage("KDM Models", Message, topic));
	}

	@Override
	public List<Plugin> getPlugins() {
		return context.getPlugins();
	}
}
