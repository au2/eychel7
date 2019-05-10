package com.amida.eychel7.handler.impl;

import com.amida.eychel7.handler.IHandler;
import com.amida.eychel7.handler.ITargetData;

import ca.uhn.hl7v2.model.Message;

public class Handler_ADT_A01__ implements IHandler {
	@Override
	public ITargetData handle(Message message) {
		return new TargetData(message);
	}
}
