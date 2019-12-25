package org.jemiahlabs.skrls.view.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.jemiahlabs.skrls.core.Nameable;
import org.jemiahlabs.skrls.context.Plugin;
import org.jemiahlabs.skrls.gui.ApplicationServiceProvider;
import org.jemiahlabs.skrls.gui.services.LoggerFormatService;
import org.jemiahlabs.skrls.view.aboutme.AboutMeViewBuilder;
import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.SubWindow;
import org.jemiahlabs.skrls.view.base.Window;
import org.jemiahlabs.skrls.view.base.WindowBuildDirector;
import org.jemiahlabs.skrls.view.base.exceptions.WindowBuildableException;
import org.jemiahlabs.skrls.view.extensions.ExtensionsViewBuilder;
import org.jemiahlabs.skrls.view.main.context.Configuration;
import org.jemiahlabs.skrls.view.problems.ProblemsViewBuilder;
import org.jemiahlabs.skrls.view.report.ReportViewBuilder;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class MainViewControllerImpl implements MainViewController, Initializable {
	
	private class ConsoleMessage {
		private String topic;
		private String message;
		
		public ConsoleMessage(String topic, String message) {
			this.topic = topic;
			this.message = message;
		}

		public String getTopic() {
			return topic;
		}

		public String getMessage() {
			return message;
		}
	}
	
	@FXML
    private AnchorPane root;
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
    private ComboBox<String> cbTargetLanguage;
    @FXML
    private Label txtInfoMessages;
    @FXML
    private Label txtWarningMessages;
    
    private PresenterMainView presenter;
    private Window openWindowCurrent;
    private Window mainView;
    private Queue<Configuration> configurations;
    private Map<String, Nameable> targetLanguages;
    private List<ConsoleMessage> consoleMessages;
    private List<String> warningSystemMessages;
    private List<String> infoSystemMessages;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	presenter = new PresenterMainViewImpl(this);
    	presenter.loadPlugins();
    	presenter.loadConfiguration();
    	
    	configurations = new ArrayDeque<Configuration>();
    	warningSystemMessages = new ArrayList<String>();
    	infoSystemMessages = new ArrayList<String>();
    	consoleMessages = new ArrayList<ConsoleMessage>();
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
    
    @Override
	public void loadLastConfiguration(Queue<Configuration> configurations) {
    	this.configurations = configurations;
    	updateMenuRecent(configurations
    			.stream()
    			.collect(Collectors.toList()));
	}
    
    @Override
	public void updateTargetLanguages(List<Plugin> plugins) {
    	Platform.runLater(
			() -> {
				targetLanguages = plugins.stream()
		    			.map(plugin -> plugin.getNameable())
		    			.collect(
		                 Collectors.toMap(Nameable::getNameProduct, nameable -> nameable));
		    	
				var keySet = targetLanguages.keySet();
				var size = keySet.size();
		    	cbTargetLanguage.getItems().addAll(keySet.toArray(new String[size]));
			}
        );
	}
    
    @FXML
    void analyzeSourceCode(ActionEvent event) {
    	StringBuilder errorMessage = new StringBuilder("");
		if (txtSource.getText() == null || txtSource.getText().isEmpty())
			errorMessage.append("Input directory is empty \n");
		if (txtDestination.getText() == null || txtDestination.getText().isEmpty())
			errorMessage.append("Output directory is empty \n");
		if (cbTargetLanguage.getValue() == null)
			errorMessage.append("Select language target \n");
		
		if(errorMessage.length() > 0) {
			showMessage("Incomplete Information", errorMessage.toString(), () -> {});
		} else {
			presenter.analyzeSourceCode(txtSource.getText(), txtDestination.getText(), targetLanguages.get(cbTargetLanguage.getValue()));
			addOpenRecent(txtSource.getText(), txtDestination.getText(), cbTargetLanguage.getValue());
		}
    }
    
    @FXML
    void changedFilterConsole(ActionEvent event) {
    	String topic = cbFilterConsole.getValue();
    	String resultTotalMessages = consoleMessages
    			.stream()
    			.filter(message -> message.getTopic().equalsIgnoreCase(topic) || topic.equalsIgnoreCase("All"))
    			.map(message -> message.getMessage())
    			.reduce("", (accumulator, message) -> accumulator + message + '\n');
    	
    	txtAreaConsole.clear();
    	txtAreaConsole.appendText(resultTotalMessages);
    }

    @FXML
    void openInputFileExplorer(ActionEvent event) {
    	try {
    		DirectoryChooser directoryChooser = new DirectoryChooser();
    		directoryChooser.setTitle("Find Source Code");
    		File fileDirectory  = directoryChooser.showDialog(root.getScene().getWindow());
        	
        	if (fileDirectory  != null) txtSource.setText(fileDirectory.getAbsolutePath());
    	} catch(Exception ex) {
    		updateWarningMessages("File", "File no selected");
    	}
    }

    @FXML
    void openOutputFileExplorer(ActionEvent event) {
    	try {
    		DirectoryChooser directoryChooser = new DirectoryChooser();
    		directoryChooser.setTitle("Find destination directory");
        	File fileDirectory = directoryChooser.showDialog(root.getScene().getWindow());
        	
        	if (fileDirectory != null) txtDestination.setText(fileDirectory.getAbsolutePath());
    	} catch(Exception ex) {
    		updateWarningMessages("File", "Directory no selected");
    	}
    }
    
    @FXML
    void closeStackPane(MouseEvent event) {
    	stackPane.toBack();
    }
    
    @FXML
    void openViewNew(ActionEvent event) {
    	JFXDialogLayout content = new JFXDialogLayout();
		content.setHeading(new Text("New Configuration"));
		content.setBody(new Text("Do you want to delete the configuration and add a new one?"));
		stackPane.toFront();
		JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
		
		JFXButton btnDone = new JFXButton("Done");
		btnDone.setOnAction((evt) ->  {
			clearConfiguration();
			dialog.close();
			stackPane.toBack();
		});
		JFXButton btnCancel = new JFXButton("Cancel");
		btnCancel.setOnAction((evt) ->  {
			dialog.close();
			stackPane.toBack();
		});
		content.setActions(btnDone, btnCancel);
		dialog.show();
    }
    
    public void clearConfiguration() {
    	txtSource.clear();
    	txtDestination.clear();
    	lblStatus.setText("");
    	var items = cbTargetLanguage.getItems();
    	if(!items.isEmpty()) cbTargetLanguage.setValue(items.get(0));
    	
    	txtSource.requestFocus();
    }
    
    @FXML
    void openViewExtensions(ActionEvent event) {
    	disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new ExtensionsViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			updateWarningMessages("Error Window", e.getMessage());
		}
    }

    @FXML
    void openViewAboutMe(ActionEvent event) {
    	disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new AboutMeViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			updateWarningMessages("Error Window", e.getMessage());
		}
    }
    
    @FXML
    void openViewProblems(MouseEvent event) {
    	disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new ProblemsViewBuilder(this));
			EventArgs args = new EventArgs();
			args.addArgument("warningMessages", warningSystemMessages);
			args.addArgument("infoMessages", infoSystemMessages);
			
			openWindowCurrent.setParams(args);
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			updateWarningMessages("Error Window", e.getMessage());
		}
    }

    @FXML
    void openViewReport(MouseEvent event) {
    	disposeOpenWindowCurrent();
		
    	try {
			openWindowCurrent = WindowBuildDirector.createWindow(new ReportViewBuilder(this));
			openWindowCurrent.show();
		} catch (WindowBuildableException e) {
			updateWarningMessages("Error Window", e.getMessage());
		}
    }
    
    @Override
    public void updateWarningMessages(String title, String message) {
    	Platform.runLater(
			() -> {
				LoggerFormatService loggerFormat = (LoggerFormatService) ApplicationServiceProvider.getInstance().getAttribute("logger-format");
		    	warningSystemMessages.add(loggerFormat.getMessage(title, message, "Warning"));
				txtWarningMessages.setText("" + warningSystemMessages.size());
			}
    	);
    }
    
    @Override
    public void updateInfoMessages(String title, String message) {
    	Platform.runLater(
			() -> {
				LoggerFormatService loggerFormat = (LoggerFormatService) ApplicationServiceProvider.getInstance().getAttribute("logger-format");
		    	infoSystemMessages.add(loggerFormat.getMessage(title, message, "Info"));
				txtInfoMessages.setText("" + infoSystemMessages.size());
			}
        );
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
    
    @Override
	public void showMessage(String title, String text, Runnable action) {
    	Platform.runLater(
			() -> {
				JFXDialogLayout content = new JFXDialogLayout();
				content.setHeading(new Text(title));
				content.setBody(new Text(text));
				stackPane.toFront();
				
				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
				JFXButton btnDone = new JFXButton("Done");
				btnDone.setOnAction((event) ->  {
					action.run();
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
				btnAnalyze.setDisable(true);
				lblStatus.setText("processing...");
				lblStatus.setTextFill(Color.web("#0076a3"));
			}
		);
	}

	@Override
	public void hiddenProgress() {
		Platform.runLater(
			() -> {
				btnAnalyze.setDisable(false);
				lblStatus.setText("finished");
				lblStatus.setTextFill(Color.web("#44bd32"));
			}
		);
	}
    
	@Override
	public void appendMessageToConsole(String topic, String newMessage) {
		Platform.runLater(
			() -> {
				consoleMessages.add(new ConsoleMessage(topic, newMessage));
				txtAreaConsole.appendText(newMessage + '\n');
			}
		);
	}
	
	@FXML
    void clearConsole(ActionEvent event) {
    	clearConsole();
    }
	
	@Override
	public void clearConsole() {
		Platform.runLater(
			() -> {
				txtAreaConsole.clear();
				consoleMessages.clear();
			}
		);
	}
	    
    @Override
    public void beforeClose() {
    	presenter.saveConfiguration(configurations);
    	Platform.exit();
    }
    
    @FXML
    void exitProgram(ActionEvent event) {
    	mainView.dispose();
    	beforeClose();
    }
    
    private void addOpenRecent(String sourceCode, String destination, String targetLanguage) {
    	Configuration newConfiguration = new Configuration(sourceCode, destination, targetLanguage);
    	if(configurations.size() > 5) configurations.remove();
    	
    	configurations.add(newConfiguration);
    	updateMenuRecent(configurations
    			.stream()
    			.collect(Collectors.toList()));
    }
    
    private void updateMenuRecent(List<Configuration> configurations) {
    	mOpenRecent.getItems().clear();
    	
    	configurations.forEach(configuration -> {
    		MenuItem menuItem = new MenuItem(configuration.getTargetLanguage() + " - " + configuration.getOutputDir());
    		menuItem.setOnAction(evt -> {
				try {
					Desktop.getDesktop().open(new File(configuration.getOutputDir()));
				} catch (IOException e) {
					updateWarningMessages("File", "Directory has been deleted or moved");
				}
			});
    		mOpenRecent.getItems().add(menuItem);
    	});
    }
    	
    private void disposeOpenWindowCurrent() {
		if(openWindowCurrent != null)
    		openWindowCurrent.dispose();
	}

}
