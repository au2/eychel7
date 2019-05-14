package com.amida.eychel7.dso.impl;

import com.amida.eychel7.dso.DSOEnum;
import com.amida.eychel7.dso.IDSO;

import ca.uhn.hl7v2.model.v281.datatype.CWE;
import ca.uhn.hl7v2.model.v281.segment.OBX;

public class Procedure implements IDSO {
	private String procedureId;
	private String procedureCode;
	private String procedureDescription;
	private String procedureFunctionalType;
	private String procedurePriority;
	private String procedureEffectiveDate;
	private String procedureActiveInd;

	@Override
	public DSOEnum getType() {
		return DSOEnum.PROCEDURE;
	}

	public String getProcedureId() {
		return procedureId;
	}

	public void interpret(OBX obx) {
		CWE cwe = (CWE) obx.getObservationValue(0).getData();

		procedureCode = cwe.getIdentifier().getValue();
		procedureDescription = cwe.getText().getValue();
	}

	public void setProcedureId(String procedureId) {
		this.procedureId = procedureId;
	}

	public String getProcedureCode() {
		return procedureCode;
	}

	public void setProcedureCode(String procedureCode) {
		this.procedureCode = procedureCode;
	}

	public String getProcedureDescription() {
		return procedureDescription;
	}

	public void setProcedureDescription(String procedureDescription) {
		this.procedureDescription = procedureDescription;
	}

	public String getProcedureFunctionalType() {
		return procedureFunctionalType;
	}

	public void setProcedureFunctionalType(String procedureFunctionalType) {
		this.procedureFunctionalType = procedureFunctionalType;
	}

	public String getProcedurePriority() {
		return procedurePriority;
	}

	public void setProcedurePriority(String procedurePriority) {
		this.procedurePriority = procedurePriority;
	}

	public String getProcedureEffectiveDate() {
		return procedureEffectiveDate;
	}

	public void setProcedureEffectiveDate(String procedureEffectiveDate) {
		this.procedureEffectiveDate = procedureEffectiveDate;
	}

	public String getProcedureActiveInd() {
		return procedureActiveInd;
	}

	public void setProcedureActiveInd(String procedureActiveInd) {
		this.procedureActiveInd = procedureActiveInd;
	}
}
