/**
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the
 * specific language governing rights and limitations under the License.
 *
 * The Original Code is "SendAndReceiveAMessage.java".  Description:
 * "Example Code"
 *
 * The Initial Developer of the Original Code is University Health Network. Copyright (C)
 * 2001.  All Rights Reserved.
 *
 * Contributor(s): James Agnew
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * GNU General Public License (the  �GPL�), in which case the provisions of the GPL are
 * applicable instead of those above.  If you wish to allow use of your version of this
 * file only under the terms of the GPL and not to allow others to use your version
 * of this file under the MPL, indicate your decision by deleting  the provisions above
 * and replace  them with the notice and other provisions required by the GPL License.
 * If you do not delete the provisions above, a recipient may use your version of
 * this file under either the MPL or the GPL.
 *
 */
package com.amida.eychel7;

import java.util.Map;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.ConnectionListener;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.protocol.ReceivingApplicationExceptionHandler;

/**
 * Example code
 *
 * @author James Agnew
 * @author Christian Ohr
 * @version $Revision: 1.2 $ updated on $Date: 2010-09-06 17:29:21 $ by $Author:
 *          jamesagnew $
 */
public class SendingApp {

	/**
	 * Example for how to send messages out
	 */
	public static void send(String msg) throws Exception {

		/*
		 * Before we can send, let's create a server to listen for incoming messages.
		 * The following section of code establishes a server listening on port 1011 for
		 * new connections.
		 */
		int port = 1011; // The port to listen on
		boolean useTls = false; // Should we use TLS/SSL?
		HapiContext context = new DefaultHapiContext();

		// Create a message to send
		Parser p = context.getPipeParser();
		Message adt = p.parse(msg);

		// Remember, we created our HAPI Context above like so:
		// HapiContext context = new DefaultHapiContext();

		// A connection object represents a socket attached to an HL7 server
		Connection connection = context.newClient("localhost", port, useTls);

		// The initiator is used to transmit unsolicited messages
		Initiator initiator = connection.getInitiator();
		Message response = initiator.sendAndReceive(adt);

		String responseString = p.encode(response);
		System.out.println("Received response:\n" + responseString);

		/*
		 * MSH|^~\&|||||20070218200627.515-0500||ACK|54|P|2.2 MSA|AA|12345
		 */

		/*
		 * If you want to send another message to the same destination, it's fine to ask
		 * the context again for a client to attach to the same host/port. The context
		 * will be smart about it and return the same (already connected) client
		 * Connection instance, assuming it hasn't been closed.
		 */
		connection = context.newClient("localhost", port, useTls);
		initiator = connection.getInitiator();
		response = initiator.sendAndReceive(adt);

		/*
		 * Close the connection when you are done with it. If you are designing a system
		 * which will continuously send out messages, you may want to consider not
		 * closing the connection until you have no more messages to send out. This is
		 * more efficient, as most (if not all) HL7 receiving applications are capable
		 * of receiving lots of messages in a row over the same connection, even with a
		 * long delay between messages.
		 *
		 * See
		 * http://hl7api.sourceforge.net/xref/ca/uhn/hl7v2/examples/SendLotsOfMessages.
		 * html for an example of this.
		 */
		connection.close();

	}

	/**
	 * Connection listener which is notified whenever a new connection comes in or
	 * is lost
	 */
	public static class MyConnectionListener implements ConnectionListener {

		public void connectionReceived(Connection theC) {
			System.out.println("New connection received: " + theC.getRemoteAddress().toString());
		}

		public void connectionDiscarded(Connection theC) {
			System.out.println("Lost connection from: " + theC.getRemoteAddress().toString());
		}

	}

	/**
	 * Exception handler which is notified any time
	 */
	public static class MyExceptionHandler implements ReceivingApplicationExceptionHandler {

		/**
		 * Process an exception.
		 *
		 * @param theIncomingMessage  the incoming message. This is the raw message
		 *                            which was received from the external system
		 * @param theIncomingMetadata Any metadata that accompanies the incoming
		 *                            message. See
		 *                            {@link ca.uhn.hl7v2.protocol.Transportable#getMetadata()}
		 * @param theOutgoingMessage  the outgoing message. The response NAK message
		 *                            generated by HAPI.
		 * @param theE                the exception which was received
		 * @return The new outgoing message. This can be set to the value provided by
		 *         HAPI in <code>outgoingMessage</code>, or may be replaced with another
		 *         message. <b>This method may not return <code>null</code></b>.
		 */
		public String processException(String theIncomingMessage, Map<String, Object> theIncomingMetadata,
				String theOutgoingMessage, Exception theE) throws HL7Exception {

			/*
			 * Here you can do any processing you like. If you want to change the response
			 * (NAK) message which will be returned you may do so, or just return the NAK
			 * which HAPI already created (theOutgoingMessage)
			 */

			return theOutgoingMessage;
		}

	}

}
