package org.jemiahlabs.skrls.view.extensions.pluginView;

import org.jemiahlabs.skrls.context.Plugin;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class PluginViewCell extends ListCell<Plugin> {
	private final String URL_IMAGE = "/images/packagejar.png";
	
	@Override
    protected void updateItem(Plugin plugin, boolean empty) {
		super.updateItem(plugin, empty);
		
		if (empty || plugin == null) {
			setText(null);
			setGraphic(null);
		} else {
			Image imProfile = new Image(getClass().getResourceAsStream(URL_IMAGE));
			ImageView imageView = new ImageView(imProfile);
			imageView.setFitWidth(33);
			imageView.setFitHeight(30);
			
			Label label = new Label(plugin.getNameable().getNameProduct() + plugin.getNameable().getVersion());
			label.setPadding(new Insets(6, 0, 0, 7));
			label.setFont(new Font(14));
			
			setText(null);
			setGraphic(new HBox(imageView, label));
		}
	}
}
