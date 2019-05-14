package com.amida.eychel7.handler.impl;

import java.util.ArrayList;
import java.util.List;

import com.amida.eychel7.dso.IDSO;
import com.amida.eychel7.dso.impl.Allergy;
import com.amida.eychel7.dso.impl.Patient;
import com.amida.eychel7.handler.IHandler;
import com.amida.eychel7.handler.ITargetData;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v281.message.ADT_A01;

public class Handler_ADT_A01__ implements IHandler {
	@Override
	public ITargetData handle(Message message) {
		ADT_A01 adtA01 = (ADT_A01) message;

		Patient patient = new Patient();
		patient.interpret(adtA01.getMSH());
		patient.interpret(adtA01.getPID());
		patient.interpret(adtA01.getPV1());

		Allergy allergy = new Allergy();
		allergy.interpret(adtA01.getAL1());

		List<IDSO> list = new ArrayList<IDSO>();
		list.add(patient);
		list.add(allergy);

		return new TargetData(message, list);
	}
}
