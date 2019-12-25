package org.jemiahlabs.skrls.view.main.context;

import org.jemiahlabs.skrls.core.Channel;
import org.jemiahlabs.skrls.core.Message;
import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.core.TypeMessage;

public class ProducerImpl extends Producer {
	public ProducerImpl(Channel channel) {
		super(channel);
	}

	@Override
	public void emitInfoMessage(String text) {
		getChannel().sendMessage(new Message(text, TypeMessage.INFO));
	}

	@Override
	public void emitWarningMessage(String text) {
		getChannel().sendMessage(new Message(text, TypeMessage.INFO));
	}

	@Override
	public void emitErrorMessage(String text) {
		getChannel().sendMessage(new Message(text, TypeMessage.INFO));
	}
}
