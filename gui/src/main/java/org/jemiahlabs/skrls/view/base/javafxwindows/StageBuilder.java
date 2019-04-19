package org.jemiahlabs.skrls.view.base.javafxwindows;

import java.io.IOException;

import org.jemiahlabs.skrls.view.base.Window;
import org.jemiahlabs.skrls.view.base.WindowBuildable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageBuilder implements WindowBuildable {
	private Stage stage;
	private String fxmlLocation;
	private String title;
	private String iconLocation;
	
	private boolean isDecorable = true;
	private boolean isResizable = false;
	private boolean isMaximized = false;
	private boolean isDraggable = false;
	
	private double xOffset = 0;
    private double yOffset = 0;
	
	public StageBuilder(String fxmlLocation, Stage stage) {
		this.fxmlLocation = fxmlLocation;
		this.stage = stage;
	}
	
	public StageBuilder(String fmxlLocation) {
		this(fmxlLocation, new Stage());
	}
	
	public StageBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public StageBuilder setIcon(String url) {
		this.iconLocation = url;
		return this;
	}
	
	public StageBuilder undecorated() {
		this.isDecorable = false;
		return this;
	}
	
	public StageBuilder resizable() {
		this.isResizable = true;
		return this;
	}
	
	public StageBuilder maximized() {
		this.isMaximized = true;
		return this;
	}
	
	public StageBuilder draggable() {
		this.isDraggable = true;
		return this;
	}
	
	@Override
	public Window build() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlLocation));
		Window window = null;
		
		try {
			Parent sceneGraph = loader.load();
			
			if (!isDecorable) stage.initStyle(StageStyle.UNDECORATED);
			stage.setResizable(isResizable);
			stage.setMaximized(isMaximized);
			if (isDraggable) {
				sceneGraph.setOnMousePressed((event) -> {
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
				});
				sceneGraph.setOnMouseDragged((event) -> {
					stage.setX(event.getScreenX() - xOffset);
					stage.setY(event.getScreenY() - yOffset);
				});
			}
			
			Scene scene = new Scene(sceneGraph);
			stage.setScene(scene);
			
			if (title != null) stage.setTitle(title);
			if (iconLocation != null) stage.getIcons().add(new Image(iconLocation));
			
			window = new AbstractWindow(stage);
			
			StageController controller = (StageController) loader.getController();
			if(controller != null) controller.setWindow(window);
			
		} catch (IOException | IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		} 	
		
		return window;
	}
	
}
