package org.jemiahlabs.skrls.view.base;

import java.util.Map;

public interface Window {
	void setParams(Map<String, Object> params);
	void setTitle(String title);
	void show();
	void hide();
	void focus();
	void dispose();
}
