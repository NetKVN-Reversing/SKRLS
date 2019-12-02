package org.jemiahlabs.skrls.view.report;

import org.jemiahlabs.skrls.view.base.SubWindow;
import org.jemiahlabs.skrls.view.base.javafxwindows.StageController;

public interface ReportViewController extends StageController, SubWindow {
	void showMessage(String title, String text);
	void showProgress();
	void hiddenProgress();
	PresenterReportView getPresenter();
}
