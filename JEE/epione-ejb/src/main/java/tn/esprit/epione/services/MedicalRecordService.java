package tn.esprit.epione.services;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.epione.enumerations.ResultType;
import tn.esprit.epione.interfaces.MedicalRecordLocal;
import tn.esprit.epione.interfaces.MedicalRecordRemote;
import tn.esprit.epione.persistence.Appointment;
import tn.esprit.epione.persistence.Evaluation;
import tn.esprit.epione.persistence.MedicalRecord;
import tn.esprit.epione.persistence.Patient;
import tn.esprit.epione.persistence.Recommendation;
import tn.esprit.epione.persistence.Treatement;
import tn.esprit.epione.persistence.User;
import tn.esprit.epione.util.SendConfirmationMail;
@Stateless
@LocalBean
public class MedicalRecordService implements MedicalRecordLocal,MedicalRecordRemote {


	@PersistenceContext(unitName = "epione-ejb")
	EntityManager em;
	

	
	@Override
	public void addmedrecord(MedicalRecord m) {
		
		Calendar cal = Calendar.getInstance();
		m.setCreationDate(cal.getTime());
		System.out.println("<<<<<"+ cal.getTime());
		m.setLastModified(cal.getTime());
		
		em.persist(m);
		
	}

	@Override
	public void addtreatement(Treatement t ) {
		
		
		t.setDateadded(Calendar.getInstance().getTime());
        t.setResult(ResultType.pending);

		em.persist(t);
	
	}

	@Override
	public void recommendDoctors(Recommendation r) {
        em.persist(r);
        	
	}

	@Override
	public void validatetreatement(Treatement t) {
    t.setResult(ResultType.treated);
    em.merge(t);
		
	}
	
	@Override
	public void invalidatetreatement(Treatement t) {
	    t.setResult(ResultType.nontreated);
	    em.merge(t);
		}

	@Override
	public void updatemedrec(Treatement t1) {

		t1.setResult(ResultType.modified);
          em.merge(t1);
		MedicalRecord m1=em.find(MedicalRecord.class, t1.getMedicalRecord().getId());
		m1.setLastModified(Calendar.getInstance().getTime());
          em.merge(m1);
  
	}
	
	@Override
	public List<Treatement> getMedicalRecord(User u){
		
		TypedQuery<MedicalRecord> m1 = em.createQuery("select m from MedicalRecord m where m.patient.id=: pid",
				MedicalRecord.class).setParameter("pid", u.getId());
      //  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" +m1.getSingleResult().getId());
		
        TypedQuery<Treatement> query = em.createQuery("select t from Treatement t where t.medicalRecord.id =: mid",
						Treatement.class)
				.setParameter("mid", m1.getSingleResult().getId());
	
	  	return  query.getResultList();
		
	}

	@Override
	public void remindpatient() {

		List<Appointment> appointments = new ArrayList<>();
		TypedQuery<Appointment> query = em.createQuery("select e from Appointment e ", Appointment.class);
		appointments = query.getResultList();
		LocalDate d= LocalDate.now();
		
		 for(Appointment a : appointments)
		    {
			 if(a.getDate().equals(d)) {
		SendConfirmationMail.sendMail("epioneglad@gmail.com", "Epione1234", a.getPatient().getEmail(),"Appoinement Reminder", "You have an appointment set for today with doctor :"+a.getDoctor().getUsername());

			 }
		 }
		
	}

	@Override
	public void removeMedRec(MedicalRecord m) {

		MedicalRecord md = em.find(MedicalRecord.class, m.getId());
		em.remove(md);
	}

    
	



}
