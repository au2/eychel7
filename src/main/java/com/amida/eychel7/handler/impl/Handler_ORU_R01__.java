package com.amida.eychel7.handler.impl;

import java.util.ArrayList;
import java.util.List;

import com.amida.eychel7.dso.IDSO;
import com.amida.eychel7.dso.impl.Patient;
import com.amida.eychel7.dso.impl.Procedure;
import com.amida.eychel7.handler.IHandler;
import com.amida.eychel7.handler.ITargetData;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v281.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v281.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v281.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v281.group.ORU_R01_PATIENT_RESULT;
import ca.uhn.hl7v2.model.v281.message.ORU_R01;
import ca.uhn.hl7v2.model.v281.segment.OBX;

public class Handler_ORU_R01__ implements IHandler {
	@Override
	public ITargetData handle(Message message) {
		ORU_R01 oru_R01 = (ORU_R01) message;

		Patient patient = new Patient();
		patient.interpret(oru_R01.getMSH());

		ORU_R01_PATIENT_RESULT result = oru_R01.getPATIENT_RESULT();
		ORU_R01_PATIENT msgPatient = result.getPATIENT();

		patient.interpret(msgPatient.getPID());

		Procedure procedure = new Procedure();

		ORU_R01_ORDER_OBSERVATION obs = result.getORDER_OBSERVATION(0);

		ORU_R01_OBSERVATION obxr = obs.getOBSERVATION(0);
		OBX obx = obxr.getOBX();
		procedure.interpret(obx);

		List<IDSO> list = new ArrayList<IDSO>();
		list.add(patient);
		list.add(procedure);

		return new TargetData(message, list);
	}
}
