package org.jemiahlabs.skrls.context.events;

@FunctionalInterface
public interface FailedCase<T extends Exception, E> {
	void failed(T exception, E info);
}
