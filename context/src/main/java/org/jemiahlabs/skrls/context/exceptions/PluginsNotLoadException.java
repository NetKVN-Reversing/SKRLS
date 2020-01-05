package org.jemiahlabs.skrls.context.exceptions;

public class PluginsNotLoadException extends Exception {
	private static final long serialVersionUID = 2754625094470426260L;
	
	public final int CODE_ERROR = 2;
	
	public PluginsNotLoadException(Exception e) {
		super(e);
	}
	
	public PluginsNotLoadException(String msg) {
		super(msg);
	}

}
