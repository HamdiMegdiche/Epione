package tn.esprit.epione.patientspace;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.epione.interfaces.PatientServiceLocal;
import tn.esprit.epione.interfaces.UserServiceLocal;
import tn.esprit.epione.interfaces.WorkTimeServiceLocal;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Schedule;
import tn.esprit.epione.persistence.Specialty;
import tn.esprit.epione.persistence.WorkTime;

@Path("worktime")
@RequestScoped
public class WorkTimeResource {
	
	@EJB
	WorkTimeServiceLocal WorkTimeService;
	
	
	public static List<WorkTime> listWorkTime=new ArrayList<>();
	
		Set<WorkTime> listWorkTimeByDocId = new HashSet<>();
		
		List<WorkTime> listWorkTimes = new ArrayList<>();
		
		public static List<WorkTime> listWorkTimesstatic = new ArrayList<>();
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddWorkTime(WorkTime workTime) {
		
//		for(WorkTime work:listWorkTime) {
//			if(work.getSchedule().equals(workTime.getSchedule()) && work.getDoctor().equals(workTime.getDoctor())
//					&& work.getDay().equals(workTime.getDay()) && work.getEndTime().equals(workTime.getEndTime())
//					&& work.getStartTime().equals(workTime.getStartTime())) {
//		       
//			return Response.status(Response.Status.FOUND).entity("The WorkTime was added Before").build();
//
//			}
//		}
		if(workTime.getSchedule() == null|| workTime.getSchedule().getId()==0) {
			return Response.status(Response.Status.NOT_FOUND).entity("you have to add a schedule").build();
	}

		//create speciality of doctor
		Schedule schedule = WorkTimeService.getScheduleById(workTime.getSchedule().getId());
		if(schedule==null) {
			return Response.status(Response.Status.NOT_FOUND).entity("this schedule doesn't exist").build();

		}
		
	
		schedule.addWorkTime(workTime);
		
		WorkTimeService.updateSchedule(schedule);
		
		listWorkTimesstatic.add(workTime);
		
			
			return Response.status(Response.Status.CREATED).entity("The WorkTime was added successfully").build();
			
			
		
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listWorkTime(){
		listWorkTimes = WorkTimeService.ListWorkTime();
		
       // return Response.status(Response.Status.ACCEPTED).entity(listWorkTimes).build();
		
        return Response.status(Response.Status.ACCEPTED).entity(listWorkTimesstatic).build();

    }
	
	@GET
	@Path("{doctor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listWorkTimeByDocId(@PathParam("doctor") int doctorid){
		
		listWorkTimeByDocId = WorkTimeService.ListWorkTimeByDocID(doctorid);

        return Response.status(Response.Status.ACCEPTED).entity(listWorkTimeByDocId).build();
    }
	
	
	
	
	
	

}

