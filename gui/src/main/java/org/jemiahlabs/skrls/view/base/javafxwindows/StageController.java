package org.jemiahlabs.skrls.view.base.javafxwindows;

import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.Window;

public interface StageController {
	void setWindow(Window window);
	
	default void receive(EventArgs args) {}
	default void beforeClose() {}
}
