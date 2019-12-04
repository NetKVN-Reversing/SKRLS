package org.jemiahlabs.skrls.view.base.javafxwindows;

import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.Window;

import javafx.stage.Stage;

public class AbstractWindow implements Window {
	protected Stage stage;
	protected StageController controller;
	
	public AbstractWindow(Stage stage, StageController controller) {
		this.stage = stage;
		this.controller = controller;
	}
	
	@Override
	public void setParams(EventArgs args) {
		controller.receive(args);
	}
	
	@Override
	public void setTitle(String title) {
		stage.setTitle(title);
	}

	@Override
	public void show() {
		stage.show();
	}

	@Override
	public void hide() {
		stage.hide();
	}

	@Override
	public void focus() {
		stage.toFront();
	}

	@Override
	public void dispose() {
		stage.close();
	}
}
