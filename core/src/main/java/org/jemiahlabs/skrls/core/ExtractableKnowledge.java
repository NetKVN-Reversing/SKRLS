package org.jemiahlabs.skrls.core;

public interface ExtractableKnowledge {
	String VERSION = "1.0.0";
	
	void extractKDM(Producer producer, String source, String outputDir);
}
