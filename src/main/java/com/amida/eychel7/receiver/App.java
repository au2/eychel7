package com.amida.eychel7.receiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amida.eychel7.handler.IHandler;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;

public class App implements ReceivingApplication<Message> {
	private List<IHandler> handlers = new ArrayList<IHandler>();

	public void addHandler(IHandler handler) {
		handlers.add(handler);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canProcess(Message theIn) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message processMessage(Message message, Map<String, Object> theMetadata)
			throws ReceivingApplicationException, HL7Exception {
		handlers.forEach(handler -> handler.handle(message));

		try {
			return message.generateACK();
		} catch (IOException e) {
			throw new HL7Exception(e);
		}

	}

}