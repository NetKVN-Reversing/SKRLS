package org.jemiahlabs.skrls.view.base;

public interface Window {
	void setTitle(String title);
	void setParams(EventArgs args);
	void show();
	void hide();
	void focus();
	void dispose();
}
