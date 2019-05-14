package com.amida.eychel7.custom.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractSegment;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.model.v281.datatype.ST;
import ca.uhn.hl7v2.parser.ModelClassFactory;

public class ZRO extends AbstractSegment {
	private String name;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ZRO(Group parent, ModelClassFactory factory) {
		super(parent, factory);
		this.name = "ZRO";
		init(factory);
	}

	private void init(ModelClassFactory factory) {
		try {
			this.add(ST.class, true, 1, 0, new Object[] { getMessage() }, "Value");
		} catch (HL7Exception e) {
			log.error("Unexpected error creating MSH - this is probably a bug in the source code generator.", e);
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	public ST getTheValue() {
		return this.getTypedField(1, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Type createNewTypeWithoutReflection(int field) {
		if (field == 0) {
			return new ST(getMessage());
		}
		return null;
	}
}
