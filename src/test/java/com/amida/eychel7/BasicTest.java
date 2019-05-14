package com.amida.eychel7;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amida.eychel7.custom.segment.ZRO;
import com.amida.eychel7.dso.DSOEnum;
import com.amida.eychel7.dso.IDSO;
import com.amida.eychel7.dso.impl.Allergy;
import com.amida.eychel7.dso.impl.Patient;
import com.amida.eychel7.dso.impl.Procedure;
import com.amida.eychel7.handler.StoreLastHandler;
import com.amida.eychel7.handler.impl.Handler_ADT_A01__;
import com.amida.eychel7.handler.impl.Handler_ORU_R01__;
import com.amida.eychel7.receiver.App;
import com.amida.eychel7.receiver.Server;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v281.datatype.CWE;
import ca.uhn.hl7v2.model.v281.datatype.ST;
import ca.uhn.hl7v2.model.v281.datatype.XPN;
import ca.uhn.hl7v2.model.v281.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v281.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v281.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v281.group.ORU_R01_PATIENT_RESULT;
import ca.uhn.hl7v2.model.v281.message.ORU_R01;
import ca.uhn.hl7v2.model.v281.segment.MSH;
import ca.uhn.hl7v2.model.v281.segment.OBR;
import ca.uhn.hl7v2.model.v281.segment.OBX;
import ca.uhn.hl7v2.model.v281.segment.PID;

public class BasicTest {
	private static Server server;

	@BeforeClass
	public static void startHL7Server() throws Exception {
		server = new Server();
		server.start();
	}

	@Test
	public void testADTA01() throws Exception {
		App app = server.addApp("ADT_A01");
		StoreLastHandler storeHandler = new StoreLastHandler(new Handler_ADT_A01__());
		app.addHandler(storeHandler);
		String msg = "MSH|^~\\&|HIS|RIH|EKG|EKG|199904140038||ADT^A01|12345|P|2.8.1\r"
				+ "PID|0001||00009874||SMITH^JOHN^M|MOM|19581119|F||C|564 SPRING ST^^NEEDHAM^MA^02494^US||(818)565-1551|(425)828-3344|E|S|C|0000444444|||||SA|||SA||||NONE|V1|0001||D.ER^50A^M110^01|ER|P00055|11B^M011^02|070615^BATMAN^GEORGE^L|555888^NOTREAL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^NOTREAL^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|199904101200||||5555112333|||666097^NOTREAL^MANNY^P\r"
				+ "NK1|0222555|NOTREAL^JAMES^R|FA|STREET^OTHER STREET^CITY^ST^55566|(222)111-3333|(888)999-0000|||||||ORGANIZATION\r"
				+ "PV1|0001|I|D.ER^1F^M950^01|ER|P000998|11B^M011^02|070615^BATMAN^GEORGE^L|555888^OKNEL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^VOICE^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|||||5555112333|||666097^DNOTREAL^MANNY^P\r"
				+ "PV2|||0112^TESTING|55555^PATIENT IS NORMAL|NONE|||19990225|19990226|1|1|TESTING|555888^NOTREAL^BOB^K^DR^MD||||||||||PROD^003^099|02|ER||NONE|19990225|19990223|19990316|NONE\r"
				+ "AL1||SEV|001^POLLEN\r"
				+ "GT1||0222PL|NOTREAL^BOB^B||STREET^OTHER STREET^CITY^ST^77787|(444)999-3333|(222)777-5555||||MO|111-33-5555||||NOTREAL GILL N|STREET^OTHER STREET^CITY^ST^99999|(111)222-3333\r"
				+ "IN1||022254P|4558PD|BLUE CROSS|STREET^OTHER STREET^CITY^ST^00990||(333)333-6666||221K|LENIX|||19980515|19990515|||PATIENT01 TEST D||||||||||||||||||02LL|022LP554";
		SendingApp client = new SendingApp();
		client.send(msg);

		Message message = storeHandler.getLastMessage();
		Assert.assertEquals("ADT_A01", message.getName());
		List<IDSO> dsos = storeHandler.getLastDSOs();

		IDSO dso0 = dsos.get(0);
		IDSO dso1 = dsos.get(1);

		Assert.assertEquals(DSOEnum.PATIENT, dso0.getType());
		Assert.assertEquals(DSOEnum.ALLERGY, dso1.getType());

		Patient patient = (Patient) dso0;
		Assert.assertEquals("RIH", patient.getAssigningOrganization());
		Assert.assertEquals("SMITH", patient.getPatientLastName());
		Assert.assertEquals("JOHN", patient.getPatientFirstName());
		Assert.assertEquals("M", patient.getPatientMiddleName());
		Assert.assertEquals("00009874", patient.getCorporateMrn());
		Assert.assertEquals("000001916994", patient.getVisitNbr());

		Allergy allergy = (Allergy) dso1;
		Assert.assertEquals("001", allergy.getAllergyCode());
		Assert.assertEquals("POLLEN", allergy.getAllergyDescription());
		Assert.assertEquals("SEV", allergy.getAllergyType());
	}

	@Test
	public void testPorcedureFromOBX() throws Exception {
		ORU_R01 message = new ORU_R01();

		message.initQuickstart("ORU", "R01", "P");

		MSH msh = message.getMSH();
		msh.getSendingFacility().getNamespaceID().setValue("RIH");

		ORU_R01_PATIENT_RESULT result = message.getPATIENT_RESULT();
		ORU_R01_PATIENT patient = result.getPATIENT();

		PID pid = patient.getPID();

		{
			XPN pn = pid.getPatientName(0);

			pn.getFamilyName().getSurname().setValue("AMRKEL SMITH");
			pn.getFamilyName().getOwnSurnamePrefix().setValue("DR");
			pn.getFamilyName().getOwnSurname().setValue("AMRKEL");
			pn.getFamilyName().getSurnamePrefixFromPartnerSpouse().setValue("YT");
			pn.getFamilyName().getSurnameFromPartnerSpouse().setValue("SMITH");

			pn.getGivenName().setValue("JOHN");
			pn.getSecondAndFurtherGivenNamesOrInitialsThereof().setValue("M");
		}
		{
			XPN pn = pid.getPatientName(1);

			pn.getFamilyName().getSurname().setValue("DOE");
			pn.getGivenName().setValue("JOE");
		}

		ORU_R01_ORDER_OBSERVATION obs = result.getORDER_OBSERVATION(0);
		OBR obr = obs.getOBR();
		obr.getFillerField1().setValue("ORD");

		ORU_R01_OBSERVATION obxr = obs.getOBSERVATION(0);
		OBX obx = obxr.getOBX();
		obx.getValueType().setValue("CWE");
		obx.getSetIDOBX().setValue("9");

		{
			CWE cwe = new CWE(message);
			cwe.getIdentifier().setValue("80146002");
			cwe.getText().setValue("Appendectomy");
			cwe.getNameOfCodingSystem().setValue("SCT");
			obx.getObservationValue(0).setData(cwe);
		}

		{
			CWE cwe = new CWE(message);
			cwe.getIdentifier().setValue("3339936002");
			cwe.getText().setValue("Exterior Appendectomy");
			cwe.getNameOfCodingSystem().setValue("SCT");
			obx.getObservationValue(1).setData(cwe);
		}

		obs.addNonstandardSegment("ZRO");
		ZRO gs = (ZRO) obs.get("ZRO");
		ST st = gs.getTheValue();
		st.setValue("High");

		obx.getObservationIdentifier().getIdentifier().setValue("29300-1");
		obx.getObservationIdentifier().getText().setValue("Procedure Performed");
		obx.getObservationIdentifier().getNameOfCodingSystem().setValue("LN");

		App app = server.addApp("ORU_R01");
		StoreLastHandler storeHandler = new StoreLastHandler(new Handler_ORU_R01__());
		app.addHandler(storeHandler);
		SendingApp client = new SendingApp();
		client.send(message);

		Message response = storeHandler.getLastMessage();
		Assert.assertEquals("ORU_R01", response.getName());
		List<IDSO> dsos = storeHandler.getLastDSOs();

		IDSO dso0 = dsos.get(0);
		IDSO dso1 = dsos.get(1);

		Assert.assertEquals(DSOEnum.PATIENT, dso0.getType());
		Assert.assertEquals(DSOEnum.PROCEDURE, dso1.getType());

		Patient responsePatient = (Patient) dso0;
		Assert.assertEquals("RIH", responsePatient.getAssigningOrganization());
		Assert.assertEquals("AMRKEL SMITH", responsePatient.getPatientLastName());
		Assert.assertEquals("JOHN", responsePatient.getPatientFirstName());
		Assert.assertEquals("M", responsePatient.getPatientMiddleName());

		Procedure procedure = (Procedure) dso1;
		Assert.assertEquals("80146002", procedure.getProcedureCode());
		Assert.assertEquals("Appendectomy", procedure.getProcedureDescription());
		Assert.assertEquals("High", procedure.getProcedurePriority());
	}

	@AfterClass
	public static void clearHL7Server() throws Exception {
		server.stop();
	}

}
