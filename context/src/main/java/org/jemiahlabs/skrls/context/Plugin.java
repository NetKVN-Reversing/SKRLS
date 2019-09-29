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

}
