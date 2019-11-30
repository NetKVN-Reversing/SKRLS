package org.jemiahlabs.skrls.gui;

import java.util.HashMap;
import java.util.Map;

public class ApplicationServiceProvider {
	private static ApplicationServiceProvider application = new ApplicationServiceProvider();
	
	private final String name = "SKRLS";
	private final String description = "";
	private final String version = "0.0.1";
	private final String icon = "/images/coding-icon.png";
	
	private Map<String, Object> attributes;
	
	public static ApplicationServiceProvider getInstance() {
		return application;
	}
	
	private ApplicationServiceProvider() {
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
