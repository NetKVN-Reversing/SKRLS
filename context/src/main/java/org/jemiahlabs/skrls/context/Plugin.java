package org.jemiahlabs.skrls.context;

import java.io.IOException;
import java.net.URLClassLoader;

import org.jemiahlabs.skrls.core.ExtractableKnowledge;
import org.jemiahlabs.skrls.core.Nameable;
import org.jemiahlabs.skrls.core.Producer;

public class Plugin {
	private URLClassLoader classLoader;
	private String location;
	private Nameable nameable;
	private ExtractableKnowledge extractableKnowledge;
	
	public Plugin(Nameable nameable, ExtractableKnowledge extractableKnowledge, String location, URLClassLoader classLoader) {
		this.nameable = nameable;
		this.extractableKnowledge = extractableKnowledge;
		this.location = location;
		this.classLoader = classLoader;
	}
	
	public void extractKDM(Producer producer, String source, String outputDir) {
		extractableKnowledge.extractKDM(producer, source, outputDir);
	}
	
	public String getName(){
		return nameable.getNameProduct();
	}

	public String getVersion(){
		return nameable.getVersion();
	}

	public String getTargetLanguaje(){
		return nameable.getTargetLanguage();
	}

	public String[] getAuthors(){
		return nameable.getAuthors();
	}

	public String getDescription(){
		return nameable.getDescription();
	}
	
	public String getLocation() {
		return location;
	}
	
	public void disconect() throws IOException {
		classLoader.close();
	}
	
	public Nameable getNameable() {
		return nameable;
	}
		
	public ExtractableKnowledge getExtractableKnowledge() {
		return extractableKnowledge;
	}
}
