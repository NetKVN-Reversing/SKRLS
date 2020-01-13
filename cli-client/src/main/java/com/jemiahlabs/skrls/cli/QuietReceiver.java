package com.jemiahlabs.skrls.cli;

import org.jemiahlabs.skrls.core.Message;
import org.jemiahlabs.skrls.core.Receiver;

public class QuietReceiver implements Receiver {

    @Override
    public void Receive(Message message) {
        resolveMessage(message);
    }

    private void resolveMessage(Message message){

    }
}
