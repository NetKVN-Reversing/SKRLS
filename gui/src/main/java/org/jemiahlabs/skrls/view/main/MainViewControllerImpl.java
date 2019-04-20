package org.jemiahlabs.skrls.view.main;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jemiahlabs.skrls.gui.ApplicationContext;
import org.jemiahlabs.skrls.view.aboutme.AboutMeViewBuilder;
import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.SubWindow;
import org.jemiahlabs.skrls.view.base.Window;
import org.jemiahlabs.skrls.view.base.WindowBuildDirector;
import org.jemiahlabs.skrls.view.base.exceptions.WindowBuildableException;
import org.jemiahlabs.skrls.view.extensions.ExtensionsViewBuilder;
import org.jemiahlabs.skrls.view.newproject.NewProjectViewBuilder;
import org.jemiahlabs.skrls.view.openproject.OpenProjectViewBuilder;
import org.jemiahlabs.skrls.view.problems.ProblemsViewBuilder;
import org.jemiahlabs.skrls.view.report.ReportViewBuilder;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class MainViewControllerImpl implements MainViewController, Initializable {
	@FXML
    private StackPane stackpane;
    @FXML
    private ComboBox<String> cbFilterConsole;
    @FXML
    private TextArea txtConsole;
    @FXML
    private Label txtWarningMessage;
    @FXML
    private Label txtInfoMessage;
    @FXML
    private Label txtStatus;
    
    private Window openWindowCurrent;
    
    private List<String> warningMessages;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		warningMessages = new ArrayList<String>();
	}
	
	@Override
	public void setWindow(Window window) {
		
	}

	@Override
	public void notifyStatus(SubWindow subWindow, EventArgs object) {
		
	}
	
	private void disposeOpenWindowCurrent() {
		if(openWindowCurrent != null)
    		openWindowCurrent.dispose();
	}
	
	@FXML
    void clearConsole(ActionEvent event) {

    }
	
	@FXML
	void openViewNewProject(ActionEvent event) {
		disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new NewProjectViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			warningMessages.add("Warning: " + LocalDate.now() + " - " + e.getMessage());
			txtWarningMessage.setText("" + warningMessages.size());
		}
	}

	@FXML
	void openViewOpenProject(ActionEvent event) {
		disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new OpenProjectViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			warningMessages.add("Warning: " + LocalDate.now() + " - " + e.getMessage());
			txtWarningMessage.setText("" + warningMessages.size());
		}
	}
	
	@FXML
    void openViewExtensions(ActionEvent event) {
		disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new ExtensionsViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			warningMessages.add("Warning: " + LocalDate.now() + " - " + e.getMessage());
			txtWarningMessage.setText("" + warningMessages.size());
		}
    }

    @FXML
    void openViewAboutMe(ActionEvent event) {
    	disposeOpenWindowCurrent();
    		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new AboutMeViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			warningMessages.add("Warning: " + LocalDate.now() + " - " + e.getMessage());
			txtWarningMessage.setText("" + warningMessages.size());
		}
    }
    
    @FXML
    void openViewProblems(MouseEvent event) {
    	disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new ProblemsViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			warningMessages.add("Warning: " + LocalDate.now() + " - " + e.getMessage());
			txtWarningMessage.setText("" + warningMessages.size());
		}
    }

    @FXML
    void openViewReport(MouseEvent event) {
    	disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new ReportViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			warningMessages.add("Warning: " + LocalDate.now() + " - " + e.getMessage());
			txtWarningMessage.setText("" + warningMessages.size());
		}
    }
    
    @FXML
    void openViewDocumentation(ActionEvent event) {
    	HostServices services = (HostServices) ApplicationContext.getInstance().getAttribute("host-services");
    	services.showDocument("https://github.com/jemiah-labs/SKRLS/tree/master/gui/docs");
    }

    @FXML
    void openViewSourceCode(ActionEvent event) {
    	HostServices services = (HostServices) ApplicationContext.getInstance().getAttribute("host-services");
    	services.showDocument("https://github.com/jemiah-labs/SKRLS");
    }
}
