package edu.gatech.hapifhir;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ResourceHandler {

    public ResourceHandler() {
        // Do not add anything to this constructor method.
    }

    public String getId(IBaseResource resource){
        String id = "";
        id=resource.getIdElement().getIdPart();
        // Get the "id" value of any resource without any additional data. (No base server urls, resource types, etc.)
        // START STUDENT CODE HERE

        // END STUDENT CODE HERE

        return id; // Replace this. Returns empty string just so it will compile.
    }

    public Observation addObservationCode(Observation observation, String system, String code, String display){
        // Create a CodeableConcept and add it to an existing Observation resource.
        // START STUDENT CODE HERE
        observation.getCode().addCoding().setSystem(system).setDisplay(display).setCode(code);
        // END STUDENT CODE HERE
        return observation;
    }

    public Patient updateOfficialGivenName(Patient patient, String givenName){
        // Being given a Patient resource and a given name string, set (not add) the name to the HumanName object in the
        // Patient's list of names with a use of "official". No other values should be modified.
        // Note: There is only one HumanName with use set to official, you do not need to handle multiple official
        // names. The name provided should be the only given name (a single item List).

        // START STUDENT CODE HERE

        // END STUDENT CODE HERE
        List<StringType> given=new ArrayList<StringType>();
        StringType GN=new StringType(givenName);
        given.add(GN);
        patient.getName().get(0).setGiven(given);
        return patient;
    }

    public Patient createUSCorePatient(String id, Identifier identifier, HumanName name, Coding ethnicityOmbCoding, Coding ethnicityDetailedCoding, String ethnicityText){
        Patient usCorePatient = new Patient(); // Use this Patient object.

        // Create a US Core Patient (a Patient resource using the US Core profile) with name, identifier, ethnicity, and
        // any required profile meta information. For name you are provided a HumanName object and for Identifier you
        // are provided with an Identifier object. These should be simple to handle. Ethnicity you are provided with
        // individual portions of the extension, the OMB category coding, Detailed ethnicity coding, and Text
        // representation as a String. You are responsible for setting these coding elements in the correct location
        // within the extension along with setting the metadata for each portion (the URLs).
        //
        // http://hl7.org/fhir/us/core/StructureDefinition-us-core-patient.html
        // http://hl7.org/fhir/us/core/StructureDefinition-us-core-ethnicity.html
        //
        // START STUDENT CODE HERE
        usCorePatient.addIdentifier(identifier).addName(name).setId(id);
        Meta m=new Meta();
        m.addProfile("http://hl7.org/fhir/us/core/StructureDefinition/us-core-patient");
        usCorePatient.setMeta(m);

        Extension ext=new Extension();
        ext.setUrl(	"http://hl7.org/fhir/us/core/StructureDefinition/us-core-ethnicity");
        usCorePatient.addExtension(ext);

        Extension omb=new Extension();
        omb.setUrl("ombCategory");
        omb.setValue(ethnicityOmbCoding);
        ext.addExtension(omb);

        Extension detail=new Extension();
        detail.setUrl("detailed");
        detail.setValue(ethnicityDetailedCoding);
        ext.addExtension(detail);

        Extension text=new Extension();
        text.setUrl("text");
        text.setValue(new StringType(ethnicityText));
        ext.addExtension(text);



        // END STUDENT CODE HERE
        return usCorePatient;
    }
}
