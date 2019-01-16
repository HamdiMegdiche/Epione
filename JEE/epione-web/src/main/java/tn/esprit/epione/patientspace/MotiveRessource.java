package tn.esprit.epione.patientspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.epione.interfaces.DoctorServiceLocal;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Motive;
import tn.esprit.epione.persistence.Specialty;

@Path("motive")
@RequestScoped
public class MotiveRessource {

	@EJB
	DoctorServiceLocal DoctorService;

	List<Motive> listMotives = new ArrayList<>();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddMotive(@QueryParam("motiveid") int motiveid, @QueryParam("doctorid") int doctorid,
			Motive motive) {

		if (motiveid != 0 && doctorid != 0) {		Doctor doctor = DoctorService.findDoctorById(doctorid);
			if(doctor==null) {
				return Response.status(Response.Status.NOT_FOUND).entity("doctor doesn't exist").build();

			}
				

			Motive motivefromdb = DoctorService.findMotiveById(motiveid);
			if(motivefromdb==null) {
				return Response.status(Response.Status.NOT_FOUND).entity("doctor doesn't exist").build();
			}
			
			doctor.addMotive(motivefromdb);

			DoctorService.updateDoctor(doctor);
			return Response.status(Response.Status.OK).entity("The motive was added successfully to the doctor")
					.build();
			
		}

		else {
			if (motive.getSpecialty() == null || motive.getSpecialty().getId() == 0) {
				return Response.status(Response.Status.NOT_FOUND).entity("you have to add a specialty").build();
			}

			// create speciality of doctor
			Specialty sp = DoctorService.findSpecialtyById(motive.getSpecialty().getId());
			if (sp == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("this speciality doesn't exist").build();

			}

			listMotives = DoctorService.ListMotive();

			for (Motive reas : listMotives) {
				if (reas.getName().equals(motive.getName()) && reas.getSpeciality().equals(motive.getSpeciality())) {

					return Response.status(Response.Status.FOUND).entity("The motive was added Before").build();

				}

			}

			sp.addMotive(motive);

			DoctorService.updateSpecialty(sp);
			return Response.status(Response.Status.CREATED).entity("The motive was added successfully").build();

		}
		
			
		}

	  
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listReason() {	    	
	    	listMotives = DoctorService.ListMotive();
	    	
	    	return Response.status(Response.Status.ACCEPTED).entity(listMotives).build();
	}
	
	@DELETE
	@Path("{doctorid}/{motiveid}")
	public Response deleteMotiveFromDoctor(@PathParam("doctorid") int doctorId, @PathParam("motiveid") int motiveId) {

		Motive m = DoctorService.findMotiveById(motiveId);
		if (m == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("motive doesn't exist").build();

		}
		Doctor d = DoctorService.findDoctorById(doctorId);
		if (d == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("motive doesn't exist").build();

		}

		d.removeMotive(m);
		DoctorService.updateDoctor(d);

		return Response.status(Response.Status.OK).entity("motive was deleted from doctor successfuly").build();
	}

	 
	 @DELETE
		@Path("{id}")
		public Response deleteMotive(@PathParam("id") int id) {

			Motive m = DoctorService.findMotiveById(id);
			if (m == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("motive doesn't exist").build();

			}

			Set<Doctor> doctors = m.getDoctors();
			if (doctors == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("motive doesn't exist").build();

			}
			Iterator<Doctor> iterator = doctors.iterator();
			while (iterator.hasNext()) {
				Doctor d = iterator.next();
				d.removeMotive(m);
				DoctorService.updateDoctor(d);
			}


			Specialty sp = DoctorService.findSpecialtyById(m.getSpecialty().getId());

			sp.removeMotive(m);

			DoctorService.updateSpecialty(sp);
			return Response.status(Response.Status.OK).entity("motive was deleted successfuly").build();
		}
	 
	 @PUT
		@Consumes(MediaType.APPLICATION_JSON)
		public Response updateSpecialty(Motive motive) {

			if (motive == null || motive.getName() == null || motive.getId() == 0) {
				return Response.status(Response.Status.BAD_REQUEST).entity("The motive is incorrect").build();

			}

			Motive m = DoctorService.findMotiveById(motive.getId());
			if (m == null) {
				return Response.status(Response.Status.BAD_REQUEST).entity("The motive to update doesn't exists").build();

			}

			if (DoctorService.findSimpleMotiveByName(motive.getName()) != null) {
				return Response.status(Response.Status.FOUND).entity("this motive already exists").build();

			}
		  

			
	    
		m.setName(motive.getName());
		DoctorService.updateMotive(m);

		return Response.status(Response.Status.OK).entity("The motive was updated successfully").build();

	}
}
