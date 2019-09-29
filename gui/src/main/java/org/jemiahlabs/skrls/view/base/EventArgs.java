package org.jemiahlabs.skrls.view.base;

import java.util.HashMap;
import java.util.Map;

public class EventArgs {
	private Map<String, Object> arguments;
	
	public static EventArgs Empty() {
		return new EventArgs();
	}
	
	public EventArgs() {
		arguments = new HashMap<String, Object>(10);
	}
	
	public void addArgument(String key, Object value) {
		arguments.put(key, value);
	}
	
	public void addArgument(Map<String, Object> args) {
		arguments.putAll(args);
	}
	
	public Object getArgument(String key) {
		return arguments.get(key);
	}
}
