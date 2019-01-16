package tn.esprit.epione.patientspace;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.epione.interfaces.UserServiceLocal;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Patient;

@Path("patient")
@RequestScoped
public class PatientResource {
	
	@EJB
	UserServiceLocal us;
	
	private static List<Patient> listPatient=new ArrayList<>();
	
	List<Patient> listPatients = new ArrayList<>();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddPatient(Patient patient) {
		if(patient.getMedicalfile()== null) {
			return Response.status(Response.Status.NOT_FOUND).entity("you have to add a Medicalfile").build();

		}for(Patient pat:listPatient) {
			if(pat.getUsername().equals(patient.getUsername())) {
		       
			return Response.status(Response.Status.FOUND).entity("The Patient was added Before").build();

			}
		}
			listPatient.add(patient);
			us.addPatient(patient);
			return Response.status(Response.Status.CREATED).entity("The patient was added successfully").build();
		
		
		
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPatient(){
		
		listPatients = us.ListPatient();
		
		if(listPatients.isEmpty()) {
			
			return Response.status(Response.Status.NOT_FOUND).entity("you have to add a Patient").build();

		}
		
        return Response.status(Response.Status.ACCEPTED).entity(listPatients).build();
    }

}
