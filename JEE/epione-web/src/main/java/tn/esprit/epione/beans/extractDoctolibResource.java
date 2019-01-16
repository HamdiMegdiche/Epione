package tn.esprit.epione.beans;

import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.epione.interfaces.extractDataServiceLocal;



@Path("doctolib")
@RequestScoped
public class extractDoctolibResource {
	@EJB(beanName = "extractDataService")
	extractDataServiceLocal extractDataSLocal;

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getAllDoctorsDoctolib() throws IOException {
		return Response.ok(extractDataSLocal.getAllDoctorsDoctolib()).build();
	}

	@GET
	@Path("{name}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getDoctorsDoctolibByFirstLettreName(@PathParam("name") String name) throws IOException {
		return Response.ok(extractDataSLocal.getDoctorsDoctolibByFirstLettreName(name)).build();
	}

	@GET
	@Path("{specialty}/{location}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getDoctorsDoctolibBySpecialtyLocation(@PathParam("specialty") String specialty,
			@PathParam("location") String location) throws IOException {
		return Response.ok(extractDataSLocal.getDoctorsDoctolibBySpecialtyLocation(specialty, location)).build();
	}

	@GET
	@Path("spec/{specialty}/{page}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getDoctorsDoctolibBySpecialtyAndPage(@PathParam("specialty") String specialty,
			@PathParam("page") String page) throws IOException {
		return Response.ok(extractDataSLocal.getDoctorsDoctolibBySpecialtyAndPage(specialty, Integer.parseInt(page)))
				.build();
	}

	@GET
	@Path("{specialty}/{location}/{fullName}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addDoctorDoctolibByPath(@PathParam("specialty") String specialty,
			@PathParam("location") String location, @PathParam("fullName") String fullName) throws IOException {
		return Response.ok(extractDataSLocal.addDoctorDoctolibByPath(specialty + "/" + location + "/" + fullName))
				.build();
	}

	@GET
	@Path("specialty")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDoctolibSpecialty() throws IOException {
		return Response.ok(extractDataSLocal.getDoctolibSpecialty(), "application/json;charset=UTF-8").build();
	}

	/*
	 * @GET
	 * 
	 * @Path("Available/{specialty}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8") public Response
	 * getDoctorsDoctolibBySpecialtyAvailableToday(@PathParam("specialty") String
	 * specialty) throws IOException { return
	 * Response.ok(extractDataSLocal.getDoctorsDoctolibBySpecialtyAvailableToday(
	 * specialty)).build(); }
	 */

}
