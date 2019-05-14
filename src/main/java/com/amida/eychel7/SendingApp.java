package com.amida.eychel7;

import java.util.concurrent.TimeUnit;

import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.Parser;

public class SendingApp {
	private HapiContext context;

	public SendingApp() {
		Config config = Config.get();
		context = config.getContext();
	}

	public Message send(Message message) throws Exception {
		Config config = Config.get();

		Connection connection = context.newClient("localhost", config.getPort(), config.getUseTls());
		Initiator initiator = connection.getInitiator();
		initiator.setTimeout(100000, TimeUnit.SECONDS);
		Message response = initiator.sendAndReceive(message);
		connection.close();

		return response;
	}

	public Message send(String msg) throws Exception {
		Parser p = context.getPipeParser();
		Message message = p.parse(msg);
		return send(message);
	}
}
