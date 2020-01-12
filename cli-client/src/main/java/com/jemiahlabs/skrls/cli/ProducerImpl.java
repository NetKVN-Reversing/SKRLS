package com.jemiahlabs.skrls.cli;

import org.jemiahlabs.skrls.core.Channel;
import org.jemiahlabs.skrls.core.Message;
import org.jemiahlabs.skrls.core.Producer;

public class ProducerImpl extends Producer {

    public ProducerImpl(Channel channel){
        super(channel);
    }

    @Override
    protected Channel getChannel() {
        return super.getChannel();
    }

    @Override
    public void emitInfoMessage(String s) {
        super.getChannel().sendMessage(Message.createMessageInfo(s));
    }

    @Override
    public void emitWarningMessage(String s) {
        super.getChannel().sendMessage(Message.createMessageWarning(s));
    }

    @Override
    public void emitErrorMessage(String s) {
        super.getChannel().sendMessage(Message.createMessageError(s));
    }
}
