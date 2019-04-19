package org.jemiahlabs.skrls.view.problems;

import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ProblemsViewControllerImpl implements ProblemsViewController {
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
		return "Problems";
	}
	
	@FXML
    void Accept(ActionEvent event) {
		disposeStage();
    }

    @FXML
    void CloseStage(MouseEvent event) {
    	disposeStage();
    }
    
    void disposeStage() {
    	if(principalWindow != null) {
			EventArgs eventArgs = new EventArgs();
			eventArgs.addArgument("window", window);
			eventArgs.addArgument("action", "Close");
			
			principalWindow.notifyStatus(this, eventArgs);
		}
    	window.dispose();
    }

}
