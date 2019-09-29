package org.jemiahlabs.skrls.core;

public class Message {
	
	public static Message createMessageInfo(String text) {
		return new Message(text, TypeMessage.INFO);
	}
	
	public static Message createMessageWarning(String text) {
		return new Message(text, TypeMessage.WARNNING);
	}
	
	public static Message createMessageError(String text) {
		return new Message(text, TypeMessage.ERROR);
	}
	
	private String text;
	private TypeMessage typeMessage;
	
	public Message(String text, TypeMessage typeMessage) {
		this.text = text;
		this.typeMessage = typeMessage;
	}
	
	public String getText() {
		return text;
	}
	
	public TypeMessage getTypeMessage() {
		return typeMessage;
	}
	
}
