package tn.esprit.epione.beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.epione.interfaces.AnalyticsServiceLocal;



@Path("analytics")
@RequestScoped
public class AnalyticsResource {
	@EJB(beanName = "AnalyticsService")
	AnalyticsServiceLocal AnalyticsSLocal;

	@GET
	@Path("{option}/{doctor_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberPatientTreated(@PathParam("option") String option, @PathParam("doctor_id") int doctor_id) {
		if (option.equals("patient"))
			return Response.ok(AnalyticsSLocal.numberPatientTreated(doctor_id)).build();
		else if (option.equals("cancel"))
			return Response.ok(AnalyticsSLocal.percentageCancellation(doctor_id)).build();
		else if (option.equals("most"))
			return Response.ok(AnalyticsSLocal.mostDayAppointment(doctor_id)).build();
		else if (option.equals("mosttime"))
			return Response.ok(AnalyticsSLocal.mostTimeAppointment(doctor_id)).build();
		else
			return null;
	}

	@GET
	@Path("{doctor_id}/{option}/{dateChosen}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPercentageVacationPerDay(@PathParam("doctor_id") int doctor_id,
			@PathParam("dateChosen") String dateChoosen, @PathParam("option") String option) {
		// if (AnalyticsSLocal.percentageVacationPerOption(doctor_id, option,
		// dateChoosen) == 0)
		// return Response.status(Status.NO_CONTENT).build();
		return Response.ok(AnalyticsSLocal.percentageVacationPerOption(doctor_id, option, dateChoosen)).build();
	}
	/*
	 * @GET
	 * 
	 * @Path("cancel/{doctor_id}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * getPercentageCancellation(@PathParam("doctor_id") int doctor_id) { return
	 * Response.ok(AnalyticsSLocal.percentageCancellation(doctor_id)).build(); }
	 * 
	 * @GET
	 * 
	 * @Path("most/{doctor_id}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * mostDayAppointment(@PathParam("doctor_id") int doctor_id) { // if
	 * (AnalyticsSLocal.mostDayAppointment(doctor_id).isEmpty()) // return
	 * Response.status(Status.NO_CONTENT).build(); return
	 * Response.ok(AnalyticsSLocal.mostDayAppointment(doctor_id)).build(); }
	 * 
	 * @GET
	 * 
	 * @Path("mosttime/{doctor_id}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * mostTimeAppointment(@PathParam("doctor_id") int doctor_id) { // if
	 * (AnalyticsSLocal.mostTimeAppointment(doctor_id).isEmpty()) // return
	 * Response.status(Status.NO_CONTENT).build(); return
	 * Response.ok(AnalyticsSLocal.mostTimeAppointment(doctor_id)).build(); }
	 */
}
