package org.jemiahlabs.skrls.view.main;

import java.util.List;
import java.util.Queue;

import org.jemiahlabs.skrls.context.Plugin;
import org.jemiahlabs.skrls.view.main.context.Configuration;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.javafxwindows.StageController;

public interface MainViewController extends StageController, PrincipalWindow {
	void showMessage(String title, String text, Runnable action);
	void showProgress();
	void hiddenProgress();
	
	void updateInfoMessages(String title, String message);
	void updateWarningMessages(String title, String message);
	void appendMessageToConsole(String topic, String newMessage);
	void clearConsole();
	
	void loadLastConfiguration(Queue<Configuration> configurations);
	void updateTargetLanguages(List<Plugin> plugins);
}
