package org.jemiahlabs.skrls.view.report;

public class PresenterReportViewImpl implements PresenterReportView {
	private ReportViewController reportController;
	
	public PresenterReportViewImpl(ReportViewController reportController) {
		this.reportController = reportController;
	}
	
	@Override
	public void sendCrashReport(String email, String description) {
		reportController.showProgress();
		new Thread(() -> {
			try {
				Thread.sleep(12000);
				reportController.hiddenProgress();
				reportController.showMessage("Error Server", "Unable connect server");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		})
		.start();
	}
}
