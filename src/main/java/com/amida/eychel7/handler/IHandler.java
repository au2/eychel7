package com.amida.eychel7.handler;

import ca.uhn.hl7v2.model.Message;

public interface IHandler {
	ITargetData handle(Message message);
}
