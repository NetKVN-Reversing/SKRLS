package org.jemiahlabs.skrls.view.report;

import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ReportViewControllerImpl implements ReportViewController {
	@FXML
    private TextArea txtMessage;
    @FXML
    private TextField txtEmail;
	
	private Window window;
	private PrincipalWindow principalWindow;

	@Override
	public void setWindow(Window window) {
		this.window = window;
	}

	@Override
	public void setPrincipalWindow(PrincipalWindow window) {
		principalWindow = window;
	}

	@Override
	public String getName() {
		return "Report";
	}
	
	@FXML
	void sendReport(ActionEvent event) {

	}

	@FXML
	void closeStage(MouseEvent event) {
		if(principalWindow != null) {
			EventArgs eventArgs = new EventArgs();
			eventArgs.addArgument("window", window);
			eventArgs.addArgument("action", "Close");
			
			principalWindow.notifyStatus(this, eventArgs);
		}
    	window.dispose();
	}

}
