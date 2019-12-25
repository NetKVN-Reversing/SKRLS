package org.jemiahlabs.skrls.view.main.context;

import java.io.Serializable;

public class Configuration implements Serializable {
	private static final long serialVersionUID = 1988587772940883411L;
	private String sourceCode;
	private String outputDir;
	private String targetLanguage;	

	public Configuration(String sourceCode, String outputDir, String targetLanguage) {
		this.sourceCode = sourceCode;
		this.outputDir = outputDir;
		this.targetLanguage = targetLanguage;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public String getTargetLanguage() {
		return targetLanguage;
	}	
}
