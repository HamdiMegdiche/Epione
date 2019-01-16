package tn.esprit.epione.patientspace;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.epione.interfaces.PatientServiceLocal;
import tn.esprit.epione.interfaces.SearchServiceLocal;
import tn.esprit.epione.interfaces.UserServiceLocal;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Specialty;


@Path("search")
@RequestScoped
public class SearchDoctor {
	
	@EJB
	PatientServiceLocal PatientService;
	
	@EJB
	SearchServiceLocal SearchService;
	
	@EJB
	UserServiceLocal us;
	
	private static List<Doctor> listDoctorByAddress=new ArrayList<>();
	private static List<Doctor> listDoctorBySpecialty=new ArrayList<>();
	private static List<Doctor> listDoctorByCivilityAndSpecialty=new ArrayList<>();
	

	List<Doctor> listDoctors = new ArrayList<>();

	 List<Doctor> ListSearchDoctorByAdrress = new ArrayList<>();
	 
	  List<Doctor> ListSearchDoctorBySpecialty = new ArrayList<>();
	 
	  List<Doctor> ListSearchDoctorByCivilityAndSpecialty = new ArrayList<>();
	 
	 
	
	
	@GET
	@Path("{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response FindDoctorByCity(@PathParam("address") String addressdoc){
		
		listDoctorByAddress.clear();
		listDoctors = us.ListDoctor();
		Doctor doctorr = null;
		for(Doctor doc:listDoctors) {
			if(doc.getAddress().equals(addressdoc)) {
				
				doctorr = doc;
				listDoctorByAddress.add(doctorr);
			}
				
		}
		
		if(doctorr == null) {
			
            return Response.status(Response.Status.NOT_FOUND).entity("The Doctor with this data was not found").build();

		}
		
		
		ListSearchDoctorByAdrress = SearchService.ListSearchDoctorByCivility(addressdoc);

        return Response.status(Response.Status.ACCEPTED).entity(listDoctorByAddress).build();
    }
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response FindDoctorBySpecialty(@QueryParam("specialty") int specialtydocid){
		listDoctorBySpecialty.clear();
		listDoctors = us.ListDoctor();
		Doctor doctorr = null;
		for(Doctor doc:listDoctors) {
			if((doc.getSpecialty().getId()) == specialtydocid) {
				
				doctorr = doc;
				listDoctorBySpecialty.add(doctorr);
			}
				
		}
		
		if(doctorr == null) {
			
            return Response.status(Response.Status.NOT_FOUND).entity("The Doctor with this data was not found").build();

		}
		
		Specialty spec = new Specialty(specialtydocid) ;
		
		
		ListSearchDoctorBySpecialty = SearchService.ListSearchDoctorBySpecialty(spec);

        return Response.status(Response.Status.ACCEPTED).entity(listDoctorBySpecialty).build();
    }


	
	@GET
	@Path("{address}/{specialty}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response FindDoctorByCivilityAndSpecialty(@PathParam("address") String addressdoc, @PathParam("specialty") int specialtydocid){
		listDoctorByCivilityAndSpecialty.clear();
		Doctor doctorr = null;
		listDoctors = us.ListDoctor();

		for(Doctor doc:listDoctors) {
			if((doc.getSpecialty().getId())== specialtydocid && doc.getAddress().equals(addressdoc)) {
				
				doctorr = doc;
				listDoctorByCivilityAndSpecialty.add(doctorr);
			}
				
		}
		
		if(doctorr == null) {
			
            return Response.status(Response.Status.NOT_FOUND).entity("The Doctor with this data was not found").build();

		}
		
		Specialty spec = new Specialty(specialtydocid) ;
		
		
		ListSearchDoctorByCivilityAndSpecialty = SearchService.ListSearchDoctorByCivilityAndSpecialty(addressdoc,spec);

        return Response.status(Response.Status.ACCEPTED).entity(listDoctorByCivilityAndSpecialty).build();
    }
	
	
	
	

}
