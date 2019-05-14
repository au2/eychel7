package com.amida.eychel7.handler.impl;

import java.util.ArrayList;
import java.util.List;

import com.amida.eychel7.dso.IDSO;
import com.amida.eychel7.dso.impl.Allergy;
import com.amida.eychel7.dso.impl.Patient;
import com.amida.eychel7.handler.IHandler;
import com.amida.eychel7.handler.ITargetData;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v22.datatype.CE;
import ca.uhn.hl7v2.model.v22.datatype.PN;
import ca.uhn.hl7v2.model.v22.message.ADT_A01;
import ca.uhn.hl7v2.model.v22.segment.AL1;
import ca.uhn.hl7v2.model.v22.segment.MSH;
import ca.uhn.hl7v2.model.v22.segment.PID;
import ca.uhn.hl7v2.model.v22.segment.PV1;

public class Handler_ADT_A01__ implements IHandler {
	@Override
	public ITargetData handle(Message message) {
		ADT_A01 adtA01 = (ADT_A01) message;

		Patient patient = new Patient();

		MSH msh = adtA01.getMSH();
		patient.setAssigningOrganization(msh.getSendingFacility().getValue());

		PID pid = adtA01.getPID();
		patient.setCorporateMrn(pid.getPatientIDExternalID().getIDNumber().getValue());

		PN pn = pid.getPatientName();
		patient.setPatientLastName(pn.getFamilyName().getValue());
		patient.setPatientFirstName(pn.getGivenName().getValue());
		patient.setPatientMiddleName(pn.getMiddleInitialOrName().getValue());

		PV1 pv1 = adtA01.getPV1();
		patient.setVisitNbr(pv1.getVisitNumber().getIDNumber().getValue());

		Allergy allergy = new Allergy();

		AL1 al1 = adtA01.getAL1();
		CE ce = al1.getAllergyCodeMnemonicDescription();
		allergy.setAllergyCode(ce.getCe1_Identifier().getValue());
		allergy.setAllergyDescription(ce.getText().getValue());
		allergy.setAllergyType(al1.getAllergyType().getValue());

		List<IDSO> list = new ArrayList<IDSO>();
		list.add(patient);
		list.add(allergy);

		return new TargetData(message, list);
	}
}
