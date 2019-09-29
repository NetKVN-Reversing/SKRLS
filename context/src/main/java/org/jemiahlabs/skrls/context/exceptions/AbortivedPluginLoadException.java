package org.jemiahlabs.skrls.context.exceptions;

public class AbortivedPluginLoadException extends Exception {
	private static final long serialVersionUID = -4780101569620358547L;
	
	public final int ERROR_CODE = 100;
	
	public AbortivedPluginLoadException(String msg) {
		super(msg);
	}
	
	public AbortivedPluginLoadException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
