package com.amida.eychel7.handler;

import java.util.List;

import com.amida.eychel7.dso.IDSO;

import ca.uhn.hl7v2.model.Message;

public interface ITargetData {
	List<IDSO> getDSOs();

	Message getMessage();
}
