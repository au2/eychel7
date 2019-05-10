package com.amida.eychel7.handler.impl;

import com.amida.eychel7.handler.ITargetData;

import ca.uhn.hl7v2.model.Message;

public class TargetData implements ITargetData {
	private Message message;

	public TargetData(Message message) {
		this.message = message;
	}

	@Override
	public Message getMessage() {
		return message;
	}
}
