package com.amida.eychel7.handler.impl;

import java.util.List;

import com.amida.eychel7.dso.IDSO;
import com.amida.eychel7.handler.ITargetData;

import ca.uhn.hl7v2.model.Message;

public class TargetData implements ITargetData {
	private Message message;
	private List<IDSO> dsos;

	public TargetData(Message message, List<IDSO> dsos) {
		this.message = message;
		this.dsos = dsos;
	}

	@Override
	public List<IDSO> getDSOs() {
		return dsos;
	}

	@Override
	public Message getMessage() {
		return message;
	}
}
