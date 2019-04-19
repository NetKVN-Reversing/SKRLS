package org.jemiahlabs.skrls.view.aboutme;

import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class AboutMeViewImpl implements AboutMeViewController {
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

	@Override
	public String getName() {
		return "AboutMe";
	}
}
