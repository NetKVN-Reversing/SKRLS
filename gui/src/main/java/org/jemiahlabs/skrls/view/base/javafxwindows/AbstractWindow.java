package org.jemiahlabs.skrls.view.base.javafxwindows;

import java.util.Map;

import org.jemiahlabs.skrls.view.base.Window;

import javafx.stage.Stage;

public class AbstractWindow implements Window {
	protected Stage stage;
	protected Map<String, Object> params;
	
	public AbstractWindow(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	@Override
	public Map<String, Object> getParams() {
		return params;
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
