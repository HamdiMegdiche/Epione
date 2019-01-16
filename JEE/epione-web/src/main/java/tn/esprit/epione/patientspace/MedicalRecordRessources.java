package tn.esprit.epione.patientspace;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.epione.enumerations.AccountType;
import tn.esprit.epione.enumerations.AppointmentStatus;
import tn.esprit.epione.enumerations.ResultType;
import tn.esprit.epione.interfaces.MedicalRecordLocal;
import tn.esprit.epione.persistence.Appointment;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Evaluation;
import tn.esprit.epione.persistence.MedicalRecord;
import tn.esprit.epione.persistence.Patient;
import tn.esprit.epione.persistence.Recommendation;
import tn.esprit.epione.persistence.Treatement;
import tn.esprit.epione.persistence.User;
import tn.esprit.epione.util.SendConfirmationMail;
import tn.esprit.epione.utilities.Secured;

@Path("medicalr")
@RequestScoped
public class MedicalRecordRessources {
	
	@EJB
	MedicalRecordLocal mr;
	@PersistenceContext(unitName = "epione-ejb")
	EntityManager em;
	
	
	@Resource
	UserTransaction tx;
	
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addmedrecord(@QueryParam(value="p")Integer pid, MedicalRecord m) {

		User patient = em.find(User.class, pid);
		
		m.setPatient(patient);
		mr.addmedrecord(m);

		return Response.status(Response.Status.CREATED).entity("The medical record is created successfully").build();
                 	
	
	  }
	
	@POST
	@Path("addT")
	@Consumes(MediaType.APPLICATION_JSON)

	public Response addTreatm(@QueryParam(value="d")Integer did,@QueryParam(value="m")Integer mid,Treatement t) {
		
		MedicalRecord m = em.find(MedicalRecord.class, mid);
        t.setDoctor(em.find(User.class, did));
		t.setMedicalRecord(m);		
		mr.addtreatement(t);
		em.clear();		
		m.setLastModified(Calendar.getInstance().getTime());
		
		try { em.merge(m);
           } catch (Exception e) {
			System.out.println(e.getMessage());	}

		
        return Response.status(Response.Status.CREATED).entity("The treatement was added successfully").build();
	}
    
	@POST
	@Path("addR")
	@Consumes(MediaType.APPLICATION_JSON)

	public Response Recomend(@QueryParam(value="t")Integer tid,@QueryParam(value="d")Integer did, Recommendation r) {
				Treatement t= em.find(Treatement.class, tid);
	            r.setTreat(t);
	     User u= em.find(User.class, did);
			if(u.getRole() == AccountType.doctor)
			{ 
				  r.getRecomendeddoctors().add(u); 
	              mr.recommendDoctors(r);
		          return Response.status(Response.Status.CREATED).entity("The doctor recommended was added successfully").build();
			}
			return Response.status(401).entity("The user selected is not a doctor !").build();
	}
    
	@Secured
	@POST
	@Path("editM")
	@Consumes(MediaType.APPLICATION_JSON)

	public Response updateMedrec(@QueryParam(value="t")Integer t1id,@QueryParam(value="j")String justif) {
		
		Treatement t1= em.find(Treatement.class, t1id);
		t1.setJustif(justif);
	
	    mr.updatemedrec(t1);
		return Response.status(Response.Status.CREATED).entity("The medical record was modified ! ").build();
	}
	
	@POST
	@Path("stepR")
	@Consumes(MediaType.APPLICATION_JSON)

	public Response validateStep(@QueryParam(value="t")Integer t1id,@QueryParam(value="r")String resultat) {
		
		Treatement t1= em.find(Treatement.class, t1id);
        if(resultat.equals("ok"))
            {mr.validatetreatement(t1);}
        else if (resultat.equals("ko")) {mr.invalidatetreatement(t1);}
		
		return Response.status(Response.Status.CREATED).entity("The step was verified ! ").build();
	}
	
	@POST
	@Path("medrec")	
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getTreatements(@QueryParam(value="p")Integer pid) {
	    
	     User u= em.find(User.class, pid);
	return Response.ok(mr.getMedicalRecord(u)).build();

	}

	@PUT
    @Path("remind")
    public Response remindpatient(){
    	mr.remindpatient();
        return Response.status(Response.Status.OK).entity("Patients reminded").build();
    }
	
	@DELETE
	@Path("deleteMedr")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteMedrec(@QueryParam(value="m")Integer mid) {
		MedicalRecord m1 = em.find(MedicalRecord.class, mid);

           mr.removeMedRec(m1);
		return Response.status(Response.Status.OK).entity("Medical record deleted").build();

	}
}
