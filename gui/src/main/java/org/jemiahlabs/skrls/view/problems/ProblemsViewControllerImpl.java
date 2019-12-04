package org.jemiahlabs.skrls.view.problems;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ProblemsViewControllerImpl implements ProblemsViewController, Initializable {
	@FXML
    private TextArea txtAreaMessage;
	
	private Window window;
	private PrincipalWindow principalWindow;
	private List<String> messages;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		messages = new ArrayList<String>();
	}
	
	@Override
	public void setWindow(Window window) {
		this.window = window;
	}
	
	@Override
	public void receive(EventArgs args) {
		List<String> warningMesssages = (List<String>) args.getArgument("warningMessages");
		List<String> infoMessages = (List<String>) args.getArgument("infoMessages");
		
		messages.addAll(infoMessages); 
		messages.addAll(warningMesssages);
		String msgPlain = messages.stream()
				.reduce("", (accumulator, msg) -> msg + "\n" + accumulator );
		
		if(!msgPlain.isEmpty()) txtAreaMessage.setText(msgPlain);
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
