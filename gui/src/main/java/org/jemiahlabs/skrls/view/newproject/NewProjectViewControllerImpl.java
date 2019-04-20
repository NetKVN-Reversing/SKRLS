package org.jemiahlabs.skrls.view.newproject;

import java.net.URL;
import java.util.ResourceBundle;

import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class NewProjectViewControllerImpl implements NewProjectViewController, Initializable {
	private Window window;
	private PrincipalWindow principalWindow;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void setWindow(Window window) {
		this.window = window;
	}

	@Override
	public void setPrincipalWindow(PrincipalWindow window) {
		principalWindow = window;
	}

	@FXML
	void cancelNewProjectStage(ActionEvent event) {
		if(principalWindow != null) {
			EventArgs eventArgs = new EventArgs();
			eventArgs.addArgument("window", window);
			eventArgs.addArgument("action", "Close");
			
			principalWindow.notifyStatus(this, eventArgs);
		}
    	window.dispose();
	}

	@Override
	public String getName() {
		return "NewProject";
	}

}
