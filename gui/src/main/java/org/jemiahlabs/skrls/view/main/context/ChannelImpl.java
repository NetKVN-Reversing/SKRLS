package org.jemiahlabs.skrls.view.main.context;

import java.time.LocalDateTime;

import org.jemiahlabs.skrls.core.Channel;
import org.jemiahlabs.skrls.core.Message;
import org.jemiahlabs.skrls.core.Receiver;

public class ChannelImpl extends Channel {
	private String name;
	private LocalDateTime creationDate;
	
	public ChannelImpl(Receiver receiver) {
		this("channel-unnamed", receiver);	
	}
	
	public ChannelImpl(String name, Receiver receiver) {
		super(receiver);
		this.name = name;
		creationDate = LocalDateTime.now();	
	}

	@Override
	public void sendMessage(Message message) {
		getReceiver().Receive(message);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}
}
