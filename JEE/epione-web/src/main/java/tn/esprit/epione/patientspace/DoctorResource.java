package tn.esprit.epione.patientspace;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.registry.infomodel.SpecificationLink;

import tn.esprit.epione.enumerations.AppointmentStatus;
import tn.esprit.epione.interfaces.DoctorServiceLocal;
import tn.esprit.epione.interfaces.PatientServiceLocal;
import tn.esprit.epione.interfaces.SearchServiceLocal;
import tn.esprit.epione.interfaces.UserServiceLocal;
import tn.esprit.epione.persistence.Appointment;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Motive;
import tn.esprit.epione.persistence.Patient;
import tn.esprit.epione.persistence.Schedule;
import tn.esprit.epione.persistence.Specialty;


@Path("doctor")
@RequestScoped
public class DoctorResource {
	
	@EJB
	UserServiceLocal us;
	
	@EJB
	SearchServiceLocal SearchService;
	
	@EJB
	PatientServiceLocal PatientService;
	
	@EJB
	DoctorServiceLocal DoctorService;
	
	

	
		List<Doctor> listDoctors = new ArrayList<>();
	
		
		public static List<Doctor> listDoctorsstatic = new ArrayList<>();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddDoctor(Doctor doctor) {
	
		listDoctors = us.ListDoctor();
		
		for(Doctor doc:listDoctors) {
			if(doc.getUsername().equals(doctor.getUsername())) {
		       
			return Response.status(Response.Status.FOUND).entity("The Doctor was added Before").build();

			}
		}

		if(doctor.getSpecialty() == null|| doctor.getSpecialty().getId()==0) {
			return Response.status(Response.Status.NOT_FOUND).entity("you have to add a specialty").build();
	}

		//create speciality of doctor
		Specialty sp = DoctorService.findSpecialtyById(doctor.getSpecialty().getId());
		if(sp==null) {
			return Response.status(Response.Status.NOT_FOUND).entity("this speciality doesn't exist").build();

		}
		

		
			

			//add speciality and it's motive to the doctor
		
			Schedule schedule = new Schedule();
			schedule.setSlot(15);
			doctor.setSchedule(schedule);
	    	sp.addDoctor(doctor);
	    	
	    	
	    	listDoctorsstatic.add(doctor);
	    	
			//System.out.println(doctor.getSpecialty().getMotives()..getName());
			DoctorService.updateSpecialityForDoctorInscrip(sp);
			
			
			return Response.status(Response.Status.CREATED).entity("The doctor was added successfully").build();
		
		
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDoctor(){
		
		 listDoctors = us.ListDoctor();

        return Response.status(Response.Status.ACCEPTED).entity(listDoctors).build();
        //System.out.println(listDoctorsstatic);
        //return Response.status(Response.Status.ACCEPTED).entity(listDoctorsstatic).build();

        
    }
	
	
	
	
	
	

}
