package org.jemiahlabs.skrls.context;

import org.jemiahlabs.skrls.core.ExtractableKnowledge;
import org.jemiahlabs.skrls.core.Nameable;

public class Plugin {
	private Nameable nameable;
	private ExtractableKnowledge extractableKnowledge;
	
	public Plugin(Nameable nameable, ExtractableKnowledge extractableKnowledge) {
		this.nameable = nameable;
		this.extractableKnowledge = extractableKnowledge;
	}
	
	public Nameable getNameable() {
		return nameable;
	}
	
	public ExtractableKnowledge getExtractableKnowledge() {
		return extractableKnowledge;
	}

	public String getName(){
		return this.nameable.getNameProduct();
	}

	public String getVersion(){
		return this.nameable.getVersion();
	}

	public String getTargetLanguaje(){
		return this.nameable.getTargetLanguage();
	}

	public String[] getAuthors(){
		return this.nameable.getAuthors();
	}

	public String getDescription(){
		return this.nameable.getDescription();
	}

}
