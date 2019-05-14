package com.amida.eychel7.dso.impl;

import com.amida.eychel7.dso.DSOEnum;
import com.amida.eychel7.dso.IDSO;

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
