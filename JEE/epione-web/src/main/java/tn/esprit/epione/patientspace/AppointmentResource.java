package tn.esprit.epione.patientspace;


import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

import tn.esprit.epione.enumerations.AppointmentStatus;
import tn.esprit.epione.interfaces.PatientServiceLocal;
import tn.esprit.epione.interfaces.SearchServiceLocal;
import tn.esprit.epione.interfaces.UserServiceLocal;
import tn.esprit.epione.persistence.Appointment;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Patient;
import tn.esprit.epione.persistence.Motive;
import tn.esprit.epione.persistence.Specialty;
import tn.esprit.epione.util.SendConfirmationMail;

@Path("appointment")
@RequestScoped
public class AppointmentResource {

	@EJB
	PatientServiceLocal PatientService;
	
	@EJB
	UserServiceLocal us;
	
	@EJB
	SearchServiceLocal SearchService;
	
	

	List<Appointment> listAppointments = new ArrayList<>();
	
	private static List<Appointment> listAppointmentsss = new ArrayList<>();
	
	


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddAppointment(Appointment appointment) {
		
		listAppointments = PatientService.ListAppointment();
		
		for (Appointment app : listAppointments) {
			if (	
//					app.getDate().equals(appointment.getDate())
//					&& app.getEndTime().equals(appointment.getEndTime())
//					&& app.getDoctor().equals(appointment.getDoctor())
//					&& app.getPatient().equals(appointment.getPatient())
					app.getMessage().equals(appointment.getMessage())
					
					) {
				
				
				return Response.status(Response.Status.FOUND).entity("The Appointment was added Before").build();

			}
		}
		
		listAppointmentsss.add(appointment);
		
		

		
		
		Patient pa = PatientService.findPatientById(appointment.getPatient().getId());
		
		pa.addAppointment(appointment);
		
		Doctor doc = PatientService.findDoctorById(appointment.getDoctor().getId());
		
		doc.addAppointment(appointment);
		
		Motive mot = PatientService.findMotiveById(appointment.getMotive().getId());
		
		mot.addAppointment(appointment);
		
		PatientService.AddAppointment(appointment);
		
        SendConfirmationMail.sendMail("epioneservice@gmail.com", "epione2018", appointment.getPatient().getEmail() ,"confirmation of the appointment", "your appointment has been confirmed please check our Epione website");


		return Response.status(Response.Status.CREATED).entity("The Appointment was added successfully").build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAppointment() {

		listAppointments = PatientService.ListAppointment();

		return Response.status(Response.Status.OK).entity(listAppointments).build();
	}

	@PUT
	@Path("/{id}")
	public Response cancelAppointment(@PathParam("id") int idAppointment) {
		Appointment a = null;
		Appointment appointment = null;
		
		listAppointments = PatientService.ListAppointment();
		for (Appointment app : listAppointments) {
			if (app.getId() == idAppointment)
				a = app;
			appointment = app;

		}
		if (a == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("The appointment was not found").build();

		}
		
		if (a.getStatus().equals(AppointmentStatus.cancelled)) {
			return Response.status(Response.Status.FOUND).entity("The appointment was already canceled").build();

		}
		a.setStatus(AppointmentStatus.cancelled);
		PatientService.CancelAppointment(appointment);
		return Response.status(Response.Status.OK).entity("The appointment was canceled successfully").build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response UpdateAppointment(Appointment appointment, @QueryParam("id") int idAppointment)
			throws ParseException {
		Appointment app = null;
		Appointment newappointment = null;
		
		listAppointments = PatientService.ListAppointment();
		for (Appointment applist : listAppointments) {
			if (applist.getId() == idAppointment)
				app = applist;
		}
		if (app == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("The Appointment was not found").build();

		}

		app.setCreatedate(new Date());
		app.setDate(appointment.getDate());
		app.setStartTime(appointment.getStartTime());
		app.setMessage(appointment.getMessage());

		newappointment = app;

		PatientService.UpdateAppointment(newappointment);

		return Response.status(Response.Status.OK).entity("The Appointment was updated successfully").build();
	}
	

	
	
	
	
	

}
