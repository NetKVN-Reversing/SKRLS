package org.jemiahlabs.skrls.core;

public abstract class Producer {
	private Interlocutor interlocutor;
	private String topic;
	
	public Producer(Interlocutor interlocutor) {
		this.interlocutor = interlocutor;
	}
	
	protected Interlocutor getInterlocutor() {
		return interlocutor;
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public abstract void emitInfoMessage(String text);
	public abstract void emitWarningMessage(String text);
	public abstract void emitErrorMessage(String text);
}
