package org.jemiahlabs.skrls.view.extensions;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jemiahlabs.skrls.context.Plugin;
import org.jemiahlabs.skrls.view.base.EventArgs;
import org.jemiahlabs.skrls.view.base.PrincipalWindow;
import org.jemiahlabs.skrls.view.base.Window;
import org.jemiahlabs.skrls.view.extensions.pluginView.PluginViewCell;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class ExtensionsViewControllerImpl implements ExtensionsViewController, Initializable {
	@FXML
    private AnchorPane root;
	@FXML
    private StackPane stackPane;
	@FXML
    private ImageView imgLoad;
	@FXML
    private ListView<Plugin> listViewPlugins;
    @FXML
    private Label lblNameProduct;
    @FXML
    private Label lblTarget;
    @FXML
    private Label lblVersion;
    @FXML
    private Label lblDescription;
    @FXML
    private VBox gridAuthors;
    @FXML
    private Button btnAddExtensions;
    @FXML
    private Button btnRemove;
    
    private Image load = new Image("/images/load.gif", true);
    private PresenterExtensionsView presenter;
    private ObservableList<Plugin> pluginObservableList;
	private Window window;
	private PrincipalWindow principalWindow;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pluginObservableList = FXCollections.observableArrayList();
		listViewPlugins.setCellFactory(pluginListView -> new PluginViewCell());
		listViewPlugins.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue != null) {
				updateDescriptionPlugin(newValue);
				btnRemove.setDisable(false);
			} else {
				btnRemove.setDisable(true);
			}
		});
		presenter = new PresenterExtensionsViewImpl(this);
		updatePluginsListView(presenter.listPlugins(), false);
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
    void addExtensions(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Find java file JAR");
		fileChooser.getExtensionFilters().addAll(
		     new FileChooser.ExtensionFilter("Java Application", "*.jar")
		);
		
		File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
		presenter.addPlugin(selectedFile.getAbsolutePath());
    }

    @FXML
    void removeExtensions(ActionEvent event) {
    	showMessage("Manage Extensions", "¿You want to uninstall this Extension?", () -> {
    		presenter.removePlugin(listViewPlugins.getSelectionModel()
        			.getSelectedItem()
        			.getNameable());
    		
    		clearDescriptionPlugin();
    	});
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
				btnAddExtensions.setDisable(true);
				imgLoad.setImage(load);
			}
		);
	}

	@Override
	public void hiddenProgress() {
		Platform.runLater(
			() -> {
				btnAddExtensions.setDisable(false);
				imgLoad.setImage(null);
			}
		);
	}
	
	@Override
	public void updatePluginsListView(List<Plugin> plugins, boolean reloadPlugins) {
		Platform.runLater(
			() -> {
				if (reloadPlugins) notifyUpdatePlugins();				
				btnRemove.setDisable(true);
				pluginObservableList.clear();
				pluginObservableList.addAll(plugins);
				listViewPlugins.setItems(pluginObservableList);
			}
		);
	}
    
	@FXML
    void closeStackPane(MouseEvent event) {
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
	public String getName() {
		return "Extensions";
	}
	
	private void updateDescriptionPlugin(Plugin plugin) {
		if(plugin == null) return;
		
		lblNameProduct.setText(plugin.getName());
		lblTarget.setText(plugin.getTargetLanguaje());
		lblVersion.setText(plugin.getVersion());
		lblDescription.setText(plugin.getDescription());
		updateAuthors(plugin);
	}
	
	private void updateAuthors(Plugin plugin) {
		gridAuthors.getChildren().clear();
		String[] authors = plugin.getAuthors();
		
		for(String author: authors) {
			newGridAuthor(author); 
		}
	}	
	
	private void newGridAuthor(String author) {
		Label label = new Label(author);
		label.setFont(new Font(14));
		gridAuthors.getChildren().add(label);
	}
	
	private void clearDescriptionPlugin() {
		lblNameProduct.setText("");
		lblTarget.setText("");
		lblVersion.setText("");
		lblDescription.setText("");
		gridAuthors.getChildren().clear();
	}
	
	private void notifyUpdatePlugins() {
		if(principalWindow != null) {
			EventArgs args = new EventArgs();
			args.addArgument("action", "updatedPlugins");
			principalWindow.notifyStatus(this, args);
		}
	}
}
