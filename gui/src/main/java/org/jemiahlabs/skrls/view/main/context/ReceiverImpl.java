package org.jemiahlabs.skrls.view.main.context;

import org.jemiahlabs.skrls.core.Message;
import org.jemiahlabs.skrls.core.Receiver;
import org.jemiahlabs.skrls.gui.ApplicationServiceProvider;
import org.jemiahlabs.skrls.gui.services.LoggerFormatService;
import org.jemiahlabs.skrls.view.main.MainViewController;

public class ReceiverImpl implements Receiver {
	private MainViewController controller;
	private LoggerFormatService loggerFormat;
	
	public ReceiverImpl(MainViewController controller) {
		this.controller = controller;
		loggerFormat = (LoggerFormatService) ApplicationServiceProvider.getInstance().getAttribute("logger-format");
	}
	
	@Override
	public void Receive(Message message) {
		try {
			String topic = message.getTypeMessage().name();
			String messageFormat = loggerFormat.getMessage("KDM Models", message.getText(), message.getTypeMessage().name());
			controller.appendMessageToConsole(topic, messageFormat);
		} catch (Exception e) {
			System.out.println("message");
		}
	}
}
