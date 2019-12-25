package org.jemiahlabs.skrls.gui.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Consumer;

public class ConfigurationLoaderService {
	private final String URI;
	
	public ConfigurationLoaderService(String filename) {
		URI = System.getProperty("user.home") + System.getProperty("file.separator") + filename;
	}
	
	public void saveConfiguration(Object obj) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(URI);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public <T> void loadConfiguration(Consumer<T> consumer) {
		try {
			FileInputStream fileInputStream = new FileInputStream(URI);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			consumer.accept((T) objectInputStream.readObject());
			objectInputStream.close();
		} catch (FileNotFoundException e) {
			consumer.accept(null);
		} catch (IOException e) {
			consumer.accept(null);
		} catch (ClassNotFoundException e) {
			consumer.accept(null);
		}
	}
}
