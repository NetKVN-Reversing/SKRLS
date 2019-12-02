package org.jemiahlabs.skrls.view.report;

import java.net.URL;
import java.util.ResourceBundle;

import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ReportViewControllerImpl implements ReportViewController, Initializable {
	@FXML
    private StackPane stackPane;
	@FXML
    private TextArea txtMessage;
    @FXML
    private TextField txtEmail;
    @FXML
    private ImageView lblLoad;
    @FXML
    private Button btnSendReport;
	
	private Window window;
	private PrincipalWindow principalWindow;
	private PresenterReportView presenter;
	private Image load = new Image("/images/load.gif", true);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	presenter = new PresenterReportViewImpl(this);
    	
    	txtEmail.setFocusTraversable(true);
    	txtEmail.requestFocus();
	}
	
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
		StringBuilder errorMessage = new StringBuilder("");
		if(txtEmail.getText() == null || txtEmail.getText().isEmpty())
			errorMessage.append("Field Email is empty \n");
		if(txtMessage.getText() == null || txtMessage.getText().isEmpty())
			errorMessage.append("Field Message is empty \n");
		
		if(errorMessage.length() > 0)	
			showMessage("Incomplete Information", errorMessage.toString());
		else 
			presenter.sendCrashReport(txtEmail.getText(), txtMessage.getText());
	}
	
	@FXML
    void onCloseStackPane(MouseEvent event) {
    	stackPane.toBack();
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
	public void showMessage(String title, String text) {
		Platform.runLater(
			() -> {
				JFXDialogLayout content = new JFXDialogLayout();
				content.setHeading(new Text(title));
				content.setBody(new Text(text));
				stackPane.toFront();
				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
				
				JFXButton btnDone = new JFXButton("Done");
				btnDone.setOnAction((event) ->  {
					dialog.close();
					stackPane.toBack();
				});
				content.setActions(btnDone);
					
				dialog.show();
			}
		);
	}

	@Override
	public void showProgress() {
		Platform.runLater(
			() -> {
				btnSendReport.setDisable(true);
				lblLoad.setImage(load);
			}
		);
	}

	@Override
	public void hiddenProgress() {
		Platform.runLater(
			() -> {
				btnSendReport.setDisable(false);
				lblLoad.setImage(null);
			}
		);
	}

	@Override
	public PresenterReportView getPresenter() {
		return presenter;
	}

}
