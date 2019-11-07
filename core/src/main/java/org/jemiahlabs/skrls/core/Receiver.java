package org.jemiahlabs.skrls.core;

public interface Receiver {
	public void Receive(Message message, String topic);
}
