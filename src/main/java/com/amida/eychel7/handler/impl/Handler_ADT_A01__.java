package com.amida.eychel7.handler.impl;

import java.util.ArrayList;
import java.util.List;

import com.amida.eychel7.dso.IDSO;
import com.amida.eychel7.dso.impl.Allergy;
import com.amida.eychel7.dso.impl.Patient;
import com.amida.eychel7.handler.IHandler;
import com.amida.eychel7.handler.ITargetData;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v281.datatype.CWE;
import ca.uhn.hl7v2.model.v281.datatype.XPN;
import ca.uhn.hl7v2.model.v281.message.ADT_A01;
import ca.uhn.hl7v2.model.v281.segment.AL1;
import ca.uhn.hl7v2.model.v281.segment.MSH;
import ca.uhn.hl7v2.model.v281.segment.PID;
import ca.uhn.hl7v2.model.v281.segment.PV1;

public class Handler_ADT_A01__ implements IHandler {
	@Override
	public ITargetData handle(Message message) {
		ADT_A01 adtA01 = (ADT_A01) message;

		Patient patient = new Patient();

		MSH msh = adtA01.getMSH();
		patient.setAssigningOrganization(msh.getSendingFacility().getNamespaceID().getValue());

		PID pid = adtA01.getPID();
		patient.setCorporateMrn(pid.getPatientIdentifierList(0).getIDNumber().getValue());

		XPN pn = pid.getPatientName()[0];
		patient.setPatientLastName(pn.getFamilyName().getSurname().getValue());
		patient.setPatientFirstName(pn.getGivenName().getValue());
		patient.setPatientMiddleName(pn.getSecondAndFurtherGivenNamesOrInitialsThereof().getValue());

		PV1 pv1 = adtA01.getPV1();
		patient.setVisitNbr(pv1.getVisitNumber().getIDNumber().getValue());

		Allergy allergy = new Allergy();

		AL1 al1 = adtA01.getAL1();
		CWE ce = al1.getAllergenCodeMnemonicDescription();
		allergy.setAllergyCode(ce.getIdentifier().getValue());
		allergy.setAllergyDescription(ce.getText().getValue());
		allergy.setAllergyType(al1.getAllergenTypeCode().getIdentifier().getValue());

		List<IDSO> list = new ArrayList<IDSO>();
		list.add(patient);
		list.add(allergy);

		return new TargetData(message, list);
	}
}
