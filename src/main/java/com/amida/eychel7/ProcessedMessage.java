package com.amida.eychel7;

import ca.uhn.hl7v2.model.Message;

public class ProcessedMessage implements IProcessedMessage {
	private Message message;

	public ProcessedMessage(Message message) {
		this.message = message;
	}

	@Override
	public Message getMessage() {
		return message;
	}
}
