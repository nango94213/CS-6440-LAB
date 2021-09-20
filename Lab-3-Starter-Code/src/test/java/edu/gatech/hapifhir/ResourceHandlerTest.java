package edu.gatech.hapifhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import junit.framework.TestCase;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ResourceHandlerTest extends TestCase {

    FhirContext ctx = null;
    IParser parser = null;

    public void testGetId() {
        // This test is written for you as a simple example to reference for those unfamiliar with unit testing, and
        // also demonstrates parsing a local file into a FHIR Resource object.

        if (ctx == null) {
            ctx = FhirContext.forR4();
            parser = ctx.newJsonParser();
        }

        // The following code demonstrates how to parse a local json file into a HAPI FHIR Resource object.
        String patientJsonFile = "src/resources/patient.json";
        String patientJson = "";
        try {
            patientJson = new String(Files.readAllBytes(Paths.get(patientJsonFile)));
        }
        catch (Exception e) { System.err.println("Failed to read patient.json file."); }

        // You can use the testPatient below, parsed from the JSON file provided, as a point of comparison to your
        // created Patient object.
        Patient testPatient = parser.parseResource(Patient.class, patientJson); // Parse your JSON string into a Patient object.

        ResourceHandler resourceHandler = new ResourceHandler(); // Instantiate your Resource Handler.

        String correctId = ""; // Replace the empty string with your expected ID.
        String studentId = resourceHandler.getId(testPatient); // Get the ID for the patient from your Resource Handler.

        assertEquals(correctId, studentId); // Compares the strings.
    }

    public void testSetObservationCode() throws Exception {
        if (ctx == null) ctx = FhirContext.forR4();
        if (parser == null) parser = ctx.newJsonParser();

        String observationJsonFile = "src/resources/observation.json";
        String observationJson = "";
        try {
            observationJson = new String(Files.readAllBytes(Paths.get(observationJsonFile)));
        }
        catch (Exception e) { System.err.println("Failed to read observation.json file."); }

        Observation correctObservation = parser.parseResource(Observation.class, observationJson);

        ResourceHandler studentResourceHandler = new ResourceHandler();
        Observation cnm=studentResourceHandler.addObservationCode(correctObservation,"ff","gg","gg");
        System.out.println(cnm.getCode().getCoding().get(1).getDisplay());

        // Build the rest of your test here.

    }

    public void test_updateOfficialGivenName() throws Exception{
        if (ctx == null) ctx = FhirContext.forR4();
        if (parser == null) parser = ctx.newJsonParser();

        String patientJsonFile = "src/resources/patient.json";
        String patientJson = "";
        try {
            patientJson = new String(Files.readAllBytes(Paths.get(patientJsonFile)));
        }
        catch (Exception e) { System.err.println("Failed to read condition.json file."); }

        Patient testPatient = parser.parseResource(Patient.class, patientJson);
        ResourceHandler studentResourceHandler = new ResourceHandler();
        studentResourceHandler.updateOfficialGivenName(testPatient,"hello");
        System.out.println(testPatient.getName().get(0).getGivenAsSingleString());

        // Build the rest of your test here.

    }

    public void test_usCorePatient() throws Exception {
        if (ctx == null) ctx = FhirContext.forR4();
        if (parser == null) parser = ctx.newJsonParser();

        String uscorepatientJsonFile = "src/resources/uscorepatient.json";
        String uscorepatientJson = "";
        try {
            uscorepatientJson = new String(Files.readAllBytes(Paths.get(uscorepatientJsonFile)));
        } catch (Exception e) {
            System.err.println("Failed to read observation.json file.");
        }

        Patient correctUSCorePatient = parser.parseResource(Patient.class, uscorepatientJson);

        ResourceHandler studentResourceHandler = new ResourceHandler();

        // Build the rest of your test here.
    }
}
