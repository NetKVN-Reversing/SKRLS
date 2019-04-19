package org.jemiahlabs.skrls.view.base;

import java.util.Map;

public interface Window {
	void setParams(Map<String, Object> params);
	Map<String, Object> getParams();
	void setTitle(String title);
	void show();
	void hide();
	void focus();
	void dispose();
}
