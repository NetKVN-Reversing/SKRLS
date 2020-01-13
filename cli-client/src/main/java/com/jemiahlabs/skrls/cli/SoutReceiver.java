package com.jemiahlabs.skrls.cli;

import org.jemiahlabs.skrls.core.Message;
import org.jemiahlabs.skrls.core.Receiver;

public class SoutReceiver implements Receiver {

    @Override
    public void Receive(Message message) {
        resolveMessage(message);
    }

    private void resolveMessage(Message message){
        System.out.println(String.format("%s - %s", message.getTypeMessage(), message.getText()));
    }
}
