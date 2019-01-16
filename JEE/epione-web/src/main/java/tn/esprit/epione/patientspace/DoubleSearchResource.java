package tn.esprit.epione.patientspace;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.epione.interfaces.SearchServiceLocal;
import tn.esprit.epione.interfaces.UserServiceLocal;
import tn.esprit.epione.persistence.Doctor;

@Path("doublesearch")
@RequestScoped
public class DoubleSearchResource {
	
	

	@EJB
	UserServiceLocal us;
	
	@EJB
	SearchServiceLocal SearchService;
	
	private static List<Doctor> listDoctorByLatitudeAndLongitude=new ArrayList<>();
	
	List<Doctor> ListSearchDoctorByLatitudeAndLongitude = new ArrayList<>();
	
	List<Doctor> listDoctors = new ArrayList<>();
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response FindDoctorByLatitudeAndLongitude(@QueryParam("latitude") Double latitudedoc, @QueryParam("longitude") Double longitudedoc){
		
		listDoctorByLatitudeAndLongitude.clear();
		
		Doctor doctorr = null;
		
		listDoctors = us.ListDoctor();
		
		for(Doctor doc:listDoctors) {
			if(doc.getLatitude().equals(latitudedoc) && doc.getLongitude().equals(longitudedoc)) {
				
				doctorr = doc;
				listDoctorByLatitudeAndLongitude.add(doctorr);
			}
			
				
		}
		
		if(doctorr == null) {
			
            return Response.status(Response.Status.NOT_FOUND).entity("The Doctor with this data was not found").build();

		}
		
		ListSearchDoctorByLatitudeAndLongitude = SearchService.ListSearchDoctorByLatitudeAndLongitude(latitudedoc,longitudedoc);  

		
        //return Response.status(Response.Status.ACCEPTED).entity(ListSearchDoctorByLatitudeAndLongitude).build();
		
        return Response.status(Response.Status.ACCEPTED).entity(listDoctorByLatitudeAndLongitude).build();

    }

}
