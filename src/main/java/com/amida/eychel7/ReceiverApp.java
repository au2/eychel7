package com.amida.eychel7;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;

/**
 * Application class for receiving ADT^A01 messages
 */
public class ReceiverApp implements ReceivingApplication<Message> {
	private List<IProcessedMessageReceiver> subscribers = new ArrayList<IProcessedMessageReceiver>();

	public void addSubscriber(IProcessedMessageReceiver receiver) {
		subscribers.add(receiver);
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
		IProcessedMessage processedMessage = new ProcessedMessage(message);
		subscribers.forEach(subscriber -> subscriber.receive(processedMessage));

		try {
			return message.generateACK();
		} catch (IOException e) {
			throw new HL7Exception(e);
		}

	}

}