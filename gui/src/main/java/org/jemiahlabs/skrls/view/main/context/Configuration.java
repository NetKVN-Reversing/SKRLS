package org.jemiahlabs.skrls.view.main.context;

public class Configuration {
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
