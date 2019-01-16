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

import tn.esprit.epione.interfaces.PatientServiceLocal;
import tn.esprit.epione.interfaces.WorkTimeServiceLocal;
import tn.esprit.epione.persistence.Schedule;
import tn.esprit.epione.persistence.Specialty;

@Path("schedule")
@RequestScoped
public class ScheduleResource {
	
	@EJB
	WorkTimeServiceLocal WorkTimeService;
	
	  List<Schedule> listSchedules = new ArrayList<>();

		private static List<Schedule> listSchedule=new ArrayList<>();

	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AddSpecialty(Schedule schedule){
		for(Schedule sche:listSchedule) {
			if(sche.getDoctor().equals(schedule.getDoctor())) {
		       
			return Response.status(Response.Status.FOUND).entity("The Schedule was added Before").build();

			}
			
			if(schedule.getDoctor() == null) {
			       
				return Response.status(Response.Status.NOT_FOUND).entity("you have to add a Doctor").build();

				}
		}
		listSchedule.add(schedule);
		WorkTimeService.AddSchedule(schedule);
        return Response.status(Response.Status.CREATED).entity("The Schedule was added successfully").build();
		
		
    }
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listSchedules(){
		
		listSchedules = WorkTimeService.ListSchedule();
		
        return Response.status(Response.Status.ACCEPTED).entity(listSchedules).build();
    }
	
	
	
	

}
