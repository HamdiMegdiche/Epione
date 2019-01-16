package tn.esprit.epione.patientspace;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import tn.esprit.epione.enumerations.AppointmentStatus;
import tn.esprit.epione.interfaces.PatientServiceLocal;
import tn.esprit.epione.persistence.Appointment;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.util.SendConfirmationMail;

@Path("confirmed")
@RequestScoped
public class NotificationResource {

	@EJB
	PatientServiceLocal PatientService;

	List<Appointment> listConfirmedAppointmentss = new ArrayList<>();

	List<Appointment> listAppointments = new ArrayList<>();
	
	public static List<Appointment> listConfirmedAppointmentssstatic = new ArrayList<>();

	@PUT
	public Response confirmAppointment() {
		Appointment a = null;
		Appointment appointment = null;

		listAppointments = PatientService.ListAppointment();

//		for (Appointment app : listAppointments) {
//			if (app.getId() == idAppointment)
//				a = app;
//				appointment = app;
//
//		}
//		if (a == null) {
//			return Response.status(Response.Status.NOT_FOUND).entity("The appointment was not found").build();
//
//		}
//
//		if (a.getStatus().equals(AppointmentStatus.treated)) {
//			return Response.status(Response.Status.FOUND).entity("the appointment is already confirmed").build();
//
//		}

		for (Appointment app : listAppointments) {
			if (app.getDate().equals(LocalDate.now()) && app.getEndTime().isAfter(LocalTime.now())
					&& app.getStatus().equals(AppointmentStatus.pending)) {

			//	a.setStatus(AppointmentStatus.treated);

				PatientService.ConfirmAppointment(app);

				return Response.status(Response.Status.OK).entity("The appointment was treatd succeffuly").build();

			}else {
				return Response.status(Response.Status.NOT_FOUND).entity("no appointment was found to treated").build();

				
			}
		}

		return Response.status(Response.Status.OK).entity("").build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listConfirmedAppointments() {
		

		listAppointments = PatientService.ListAppointment();

		for (Appointment app : listAppointments) {
			if (app.getStatus().equals(AppointmentStatus.treated) == false) {

				return Response.status(Response.Status.NOT_FOUND).entity("no Appointments found").build();

			}

		}

		listConfirmedAppointmentss = PatientService.ListConfirmedAppointments(AppointmentStatus.treated);

		return Response.status(Response.Status.ACCEPTED).entity(listConfirmedAppointmentss).build();
	}

}
