package org.jemiahlabs.skrls.core;

public abstract class Channel {
	private Receiver receiver;
	
	public Channel(Receiver receiver) {
		this.receiver = receiver;
	}
	
	protected Receiver getReceiver() {
		return receiver;
	}
	
	public abstract void sendMessage(Message message);
}
