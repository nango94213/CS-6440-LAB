package edu.gatech.hapifhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.util.BundleUtil;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;

public class BundleHandler {

    public BundleHandler() { }

    public String navigateBundle(Bundle bundle, String link) {

        // START STUDENT CODE HERE
        // END STUDENT CODE HERE
        try{
            return bundle.getLink(link).getUrl();
        }catch(Exception e){
            return "-1";
        }
        // Returning empty string so starter code compiles.
    }

    public ArrayList<Patient> getListOfDeceasedPatients(Bundle bundle) {
        ArrayList<Patient> patientArrayList = new ArrayList<>();

        // START STUDENT CODE HERE
        /*for (Bundle.BundleEntryComponent b:bundle.getEntry()){
                if (b.getResource().getResourceType().equals("Patient")){
                    Patient p= (Patient) b.getResource();
                    if (p.hasDeceased()){
                        patientArrayList.add(p);
                    }
                }

        }*/
        FhirContext ctx = FhirContext.forR4();
        String serverBase = "http://hapi.fhr.org/baseR4";
        IGenericClient client = ctx.newRestfulGenericClient(serverBase);
        ArrayList<Patient> list=new ArrayList<Patient>();
        list.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(),bundle,Patient.class));
        while (bundle.getLink(IBaseBundle.LINK_NEXT)!=null){
            bundle=client.loadPage().next(bundle).execute();
            list.addAll(BundleUtil.toListOfResourcesOfType(client.getFhirContext(),bundle,Patient.class));

        }
        for (Patient p:list){
            if (p.hasDeceased()){
                if(p.getDeceasedBooleanType().booleanValue()==true){
                    patientArrayList.add(p);
                }

            }
        }



        // END STUDENT CODE HERE

        return patientArrayList; //
    }
}
