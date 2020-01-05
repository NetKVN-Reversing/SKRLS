package org.jemiahlabs.skrls.context.events;

@FunctionalInterface
public interface FailedCase<T extends Exception> {
	void failed(T exception);
}
