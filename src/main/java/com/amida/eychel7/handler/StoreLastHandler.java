package com.amida.eychel7.handler;

import ca.uhn.hl7v2.model.Message;

public class StoreLastHandler implements IHandler {
	private IHandler handler;
	private Message lastMessage;

	public StoreLastHandler(IHandler handler) {
		this.handler = handler;
	}

	@Override
	public ITargetData handle(Message message) {
		lastMessage = message.getMessage();
		return handler.handle(message);
	}

	public Message getLastMessage() {
		return lastMessage;
	}
}
