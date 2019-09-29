package org.jemiahlabs.skrls.core;

public abstract class Producer {
	private Interlocutor interlocutor;
	
	public Producer(Interlocutor interlocutor) {
		this.interlocutor = interlocutor;
	}
	
	protected Interlocutor getInterlocutor() {
		return interlocutor;
	}
	
	public abstract void emitInfoMessage(String text);
	public abstract void emitWarningMessage(String text);
	public abstract void emitErrorMessage(String text);
}
