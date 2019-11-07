package org.jemiahlabs.skrls.core;

public abstract class Channel implements Interlocutor {
	private Receiver receiver;
	
	public Channel(Receiver receiver) {
		this.receiver = receiver;
	}
	
	protected Receiver getReceiver() {
		return receiver;
	}
}
