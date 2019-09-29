package org.jemiahlabs.skrls.context.events;

@FunctionalInterface
public interface SuccessCase<T> {
	void success(T info); 
}
