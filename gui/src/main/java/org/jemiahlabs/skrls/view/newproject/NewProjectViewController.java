package org.jemiahlabs.skrls.view.newproject;

import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.SubWindow;
import org.jemiahlabs.skrls.view.base.javafxwindows.StageController;

public interface NewProjectViewController extends StageController, SubWindow {
	default void receive(EventArgs args) {}
}
