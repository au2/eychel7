package com.amida.eychel7.handler;

import java.util.List;

import com.amida.eychel7.dso.IDSO;

import ca.uhn.hl7v2.model.Message;

public class StoreLastHandler implements IHandler {
	private IHandler handler;
	private Message lastMessage;
	private List<IDSO> lastDSOs;

	public StoreLastHandler(IHandler handler) {
		this.handler = handler;
	}

	@Override
	public ITargetData handle(Message message) {
		ITargetData result = handler.handle(message);
		lastDSOs = result.getDSOs();
		lastMessage = result.getMessage();
		return result;
	}

	public Message getLastMessage() {
		return lastMessage;
	}

	public List<IDSO> getLastDSOs() {
		return lastDSOs;
	}
}
