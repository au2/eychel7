package com.amida.eychel7.dso.impl;

import com.amida.eychel7.dso.DSOEnum;
import com.amida.eychel7.dso.IDSO;

import ca.uhn.hl7v2.model.v281.datatype.CWE;
import ca.uhn.hl7v2.model.v281.segment.AL1;

public class Allergy implements IDSO {
	private String allergyCode;
	private String allergyDescription;
	private String allergyType;
	private String allergySeverity;
	private String allergyEffectiveDate;
	private String allergyActiveInd;

	@Override
	public DSOEnum getType() {
		return DSOEnum.ALLERGY;
	}

	public void interpret(AL1 al1) {
		CWE ce = al1.getAllergenCodeMnemonicDescription();

		allergyCode = ce.getIdentifier().getValue();
		allergyDescription = ce.getText().getValue();
		allergyType = al1.getAllergenTypeCode().getIdentifier().getValue();
	}

	public String getAllergyCode() {
		return allergyCode;
	}

	public void setAllergyCode(String allergyCode) {
		this.allergyCode = allergyCode;
	}

	public String getAllergyDescription() {
		return allergyDescription;
	}

	public void setAllergyDescription(String allergyDescription) {
		this.allergyDescription = allergyDescription;
	}

	public String getAllergyType() {
		return allergyType;
	}

	public void setAllergyType(String allergyType) {
		this.allergyType = allergyType;
	}

	public String getAllergySeverity() {
		return allergySeverity;
	}

	public void setAllergySeverity(String allergySeverity) {
		this.allergySeverity = allergySeverity;
	}

	public String getAllergyEffectiveDate() {
		return allergyEffectiveDate;
	}

	public void setAllergyEffectiveDate(String allergyEffectiveDate) {
		this.allergyEffectiveDate = allergyEffectiveDate;
	}

	public String getAllergyActiveInd() {
		return allergyActiveInd;
	}

	public void setAllergyActiveInd(String allergyActiveInd) {
		this.allergyActiveInd = allergyActiveInd;
	}
}
