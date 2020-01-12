package com.jemiahlabs.skrls.cli;

import org.jemiahlabs.skrls.core.Channel;
import org.jemiahlabs.skrls.core.Message;
import org.jemiahlabs.skrls.core.Receiver;

public class ChannelImpl extends Channel {

    public ChannelImpl(Receiver receiver) {
        super(receiver);
    }

    @Override
    protected Receiver getReceiver() {
        return super.getReceiver();
    }

    @Override
    public void sendMessage(Message message) {
        super.getReceiver().Receive(message);
    }
}
