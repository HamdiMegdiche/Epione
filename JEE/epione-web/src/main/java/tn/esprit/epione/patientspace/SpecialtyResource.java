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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.epione.interfaces.DoctorServiceLocal;
import tn.esprit.epione.interfaces.PatientServiceLocal;
import tn.esprit.epione.persistence.Specialty;

@Path("specialty")
@RequestScoped
public class SpecialtyResource {

	@EJB
	DoctorServiceLocal DoctorService;

	List<Specialty> listSpecialtys = new ArrayList<>();

	private static List<Specialty> listSpecialty = new ArrayList<>();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddSpecialty(Specialty specialty) {
//		for(Specialty spec:listSpecialty) {
//			if(spec.getName().equals(specialty.getName())) {
//		       
//			return Response.status(Response.Status.FOUND).entity("The Specialty was added Before").build();
//
//			}
//		}
//		listSpecialty.add(specialty);

		if (specialty == null || specialty.getName() == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("The Specialty is incorrect").build();

		}

		if (DoctorService.findSimpleSpecialtyByName(specialty.getName()) != null) {
			return Response.status(Response.Status.FOUND).entity("The Specialty was added Before").build();

		}

		DoctorService.AddSpecialty(specialty);
		return Response.status(Response.Status.CREATED).entity("The Specialty was added successfully").build();

	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listSpecialty(@PathParam("id") int idSpecialty) {

		Specialty sp = DoctorService.findSimpleSpecialtyById(idSpecialty);
		if(sp==null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("The Specialty doesn't exists").build();

		}
		
		return Response.status(Response.Status.FOUND).entity(sp).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listSpecialty() {

		listSpecialtys = DoctorService.ListSpecialty();
	
		return Response.status(Response.Status.ACCEPTED).entity(listSpecialtys).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateSpecialty(Specialty specialty) {
	

		if (specialty == null || specialty.getName() == null || specialty.getId()==0) {
			return Response.status(Response.Status.BAD_REQUEST).entity("The Specialty is incorrect").build();

		}
		
		Specialty sp =DoctorService.findSpecialtyById(specialty.getId());
		if(sp==null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("The Specialty to update doesn't exists").build();

		}

		if (DoctorService.findSimpleSpecialtyByName(specialty.getName()) != null) {
			return Response.status(Response.Status.FOUND).entity("The Specialty already exists").build();

		}
		
	
		
		sp.setName(specialty.getName());
		
		DoctorService.updateSpecialty(sp);
		
	return Response.status(Response.Status.OK).entity("The Specialty was updated successfully").build();
	
	}
	
	
	
	

}
