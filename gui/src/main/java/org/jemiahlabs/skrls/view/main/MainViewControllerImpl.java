package org.jemiahlabs.skrls.view.main;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.jemiahlabs.skrls.gui.ApplicationServiceProvider;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class MainViewControllerImpl implements MainViewController, Initializable {
	@FXML
    private StackPane stackPane;
    @FXML
    private Menu mOpenRecent;
    @FXML
    private ComboBox<String> cbFilterConsole;
    @FXML
    private TextArea txtAreaConsole;
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtSource;
    @FXML
    private Button btnAnalyze;
    @FXML
    private TextField txtDestination;
    @FXML
    private ComboBox<?> cbTargetLanguage;
    @FXML
    private Label txtInfoMessages;
    @FXML
    private Label txtWarningMessages;
    
    private Window openWindowCurrent;
    private Window mainView;
    private List<String> warningMessages;
    private List<String> infoMessages;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	warningMessages = new ArrayList<String>();
    	infoMessages = new ArrayList<String>();
    	
    	cbFilterConsole.getItems().addAll(
            "All",
            "Info",
            "Warning",
            "Error"
        );
    	cbFilterConsole.setValue("All");
	}
    
    @Override
	public void notifyStatus(SubWindow subWindow, EventArgs evtArgs) {

	}
	
    @Override
	public void setWindow(Window window) {
    	mainView = window;
	}

    @FXML
    void analyzeSourceCode(ActionEvent event) {

    }

    @FXML
    void changedFilterConsole(ActionEvent event) {

    }

    @FXML
    void clearConsole(ActionEvent event) {

    }

    @FXML
    void closeStackPane(MouseEvent event) {

    }
    
    @FXML
    void openViewNew(ActionEvent event) {

    }
    
    @FXML
    void openViewExtensions(ActionEvent event) {
    	disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new ExtensionsViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			updateWarningMessages(LocalDate.now() + " [Warning] " + "Error Window - " + e.getMessage());
		}
    }

    @FXML
    void openViewAboutMe(ActionEvent event) {
    	disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new AboutMeViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			updateWarningMessages(LocalDate.now() + " [Warning] " + "Error Window - " + e.getMessage());
		}
    }
    
    @FXML
    void openViewProblems(MouseEvent event) {
    	disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new ProblemsViewBuilder(this));
			EventArgs args = new EventArgs();
			args.addArgument("warningMessages", warningMessages);
			args.addArgument("infoMessages", infoMessages);
			
			openWindowCurrent.setParams(args);
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			updateWarningMessages(LocalDate.now() + " [Warning] " + "Error Window - " + e.getMessage());
		}
    }

    @FXML
    void openViewReport(MouseEvent event) {
    	disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new ReportViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			updateWarningMessages(LocalDate.now() + " [Warning] " + "Error Window - " + e.getMessage());
		}
    }
    
    private void updateWarningMessages(String message) {
    	warningMessages.add(message);
		txtWarningMessages.setText("" + warningMessages.size());
    }
    
    private void updateInfoMessages(String message) {
    	infoMessages.add(message);
		txtInfoMessages.setText("" + warningMessages.size());
    }
    
    @FXML
    void openViewDocumentation(ActionEvent event) {
    	HostServices services = (HostServices) ApplicationServiceProvider.getInstance().getAttribute("host-services");
    	services.showDocument("https://github.com/jemiah-labs/SKRLS/tree/master/gui/docs");
    }
    
    @FXML
    void openViewSourceCode(ActionEvent event) {
    	HostServices services = (HostServices) ApplicationServiceProvider.getInstance().getAttribute("host-services");
    	services.showDocument("https://github.com/jemiah-labs/SKRLS");
    }
    
    private void disposeOpenWindowCurrent() {
		if(openWindowCurrent != null)
    		openWindowCurrent.dispose();
	}
    
    @FXML
    void exitProgram(ActionEvent event) {
    	mainView.dispose();
    	System.exit(0);
    }
}
