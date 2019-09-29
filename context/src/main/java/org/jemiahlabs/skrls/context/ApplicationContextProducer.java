package org.jemiahlabs.skrls.context;

import org.jemiahlabs.skrls.core.Interlocutor;
import org.jemiahlabs.skrls.core.Producer;

public class ApplicationContextProducer extends Producer {

	public ApplicationContextProducer(Interlocutor interlocutor) {
		super(interlocutor);
	}

	@Override
	public void emitInfoMessage(String text) {
		
	}

	@Override
	public void emitWarningMessage(String text) {
		
	}

	@Override
	public void emitErrorMessage(String text) {
		
	}

}
