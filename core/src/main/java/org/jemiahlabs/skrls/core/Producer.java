package org.jemiahlabs.skrls.core;

public abstract class Producer {
	private Channel channel;
	
	public Producer(Channel channel) {
		this.channel = channel;
	}
	
	protected Channel getChannel() {
		return channel;
	}

	public abstract void emitInfoMessage(String text);
	public abstract void emitWarningMessage(String text);
	public abstract void emitErrorMessage(String text);
}
