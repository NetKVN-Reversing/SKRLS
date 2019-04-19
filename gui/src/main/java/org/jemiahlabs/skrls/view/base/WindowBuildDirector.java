package org.jemiahlabs.skrls.view.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.jemiahlabs.skrls.view.base.exceptions.WindowBuildableException;

public class WindowBuildDirector {
	private static WindowBuildDirector director = new WindowBuildDirector();
	
	private Map<String, WindowBuildable> buildables;
	
	public static WindowBuildDirector getDirector() {
		return director;
	}
	
	public static Window createWindow(WindowBuildable windowBuildable) {
		return windowBuildable.build();
	}
	
	private WindowBuildDirector() {
		buildables = new HashMap<>(); 
	}
	
	public void prepareWindow(String nameWindowBuildable, WindowBuildable windowBuildable) {
		buildables.put(nameWindowBuildable, windowBuildable);
	}
	
	public Window createWindow(String nameWindowBuildable) throws WindowBuildableException {
		Optional<WindowBuildable> optional = Optional.ofNullable(buildables.get(nameWindowBuildable));
		return createWindow(
			optional.orElseThrow(() -> new WindowBuildableException("Not exits WindowBuildable"))
		);
	}
}
