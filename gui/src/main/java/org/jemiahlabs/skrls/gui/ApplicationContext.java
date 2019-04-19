package org.jemiahlabs.skrls.gui;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
	private static ApplicationContext application = new ApplicationContext();
	
	private final String name = "SKRLS";
	private final String description = "";
	private final String version = "0.0.1";
	private final String icon = "/images/coding-icon.png";
	
	private Map<String, Object> attributes;
	
	public static ApplicationContext getInstance() {
		return application;
	}
	
	private ApplicationContext() {
		attributes = new HashMap<String, Object>();
	}
	
	public void addAttribute(String key, Object value) {
		attributes.put(key, value);
	}
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public String getVersion() {
		return version;
	}

	public String getIcon() {
		return icon;
	}
}
