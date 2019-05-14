package com.amida.eychel7.dso.impl;

import com.amida.eychel7.dso.DSOEnum;
import com.amida.eychel7.dso.IDSO;

import ca.uhn.hl7v2.model.v281.datatype.XPN;
import ca.uhn.hl7v2.model.v281.segment.MSH;
import ca.uhn.hl7v2.model.v281.segment.PID;
import ca.uhn.hl7v2.model.v281.segment.PV1;

public class Patient implements IDSO {
	private String assigningOrganization;

	private String corporateMrn;
	private String episodeNbr;
	private String visitNbr;

	private String patientFirstName;
	private String patientMiddleName;

	private String patientLastName;
	private String createDtTime;

	@Override
	public DSOEnum getType() {
		return DSOEnum.PATIENT;
	}

	public void interpret(MSH msh) {
		assigningOrganization = msh.getSendingFacility().getNamespaceID().getValue();
	}

	public void interpret(PID pid) {
		corporateMrn = pid.getPatientIdentifierList(0).getIDNumber().getValue();

		XPN pn = pid.getPatientName()[0];
		patientLastName = pn.getFamilyName().getSurname().getValue();
		patientFirstName = pn.getGivenName().getValue();
		patientMiddleName = pn.getSecondAndFurtherGivenNamesOrInitialsThereof().getValue();
	}

	public void interpret(PV1 pv1) {
		visitNbr = pv1.getVisitNumber().getIDNumber().getValue();
	}

	public String getAssigningOrganization() {
		return assigningOrganization;
	}

	public void setAssigningOrganization(String assigningOrganization) {
		this.assigningOrganization = assigningOrganization;
	}

	public String getCorporateMrn() {
		return corporateMrn;
	}

	public void setCorporateMrn(String corporateMrn) {
		this.corporateMrn = corporateMrn;
	}

	public String getEpisodeNbr() {
		return episodeNbr;
	}

	public void setEpisodeNbr(String episodeNbr) {
		this.episodeNbr = episodeNbr;
	}

	public String getVisitNbr() {
		return visitNbr;
	}

	public void setVisitNbr(String visitNbr) {
		this.visitNbr = visitNbr;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientMiddleName() {
		return patientMiddleName;
	}

	public void setPatientMiddleName(String patientMiddleName) {
		this.patientMiddleName = patientMiddleName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getCreateDtTime() {
		return createDtTime;
	}

	public void setCreateDtTime(String createDtTime) {
		this.createDtTime = createDtTime;
	}
}
