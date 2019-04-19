package org.jemiahlabs.skrls.view.base;

public interface PrincipalWindow<T> {
	void notifyStatus(SubWindow<T> subWindow, T object);
}
