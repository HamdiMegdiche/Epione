package tn.esprit.epione.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;

import tn.esprit.epione.enumerations.AppointmentStatus;
import tn.esprit.epione.interfaces.PatientServiceLocal;
import tn.esprit.epione.interfaces.PatientServiceRemote;
import tn.esprit.epione.persistence.Appointment;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Motive;
import tn.esprit.epione.persistence.Patient;
import tn.esprit.epione.persistence.Specialty;


@Stateless
public class PatientService implements PatientServiceLocal,PatientServiceRemote {
	
	@PersistenceContext(unitName="epione-ejb")
	EntityManager em;
	
	public List<Appointment> listConfirmedAppointments = new ArrayList<>();
	 

	 
	 public List<Appointment> listAppointment = new ArrayList<>();

	
	@Override
	public  void AddAppointment(Appointment appointment){
		
		Query query = em.createQuery("SELECT s.slot FROM Schedule s WHERE s.doctor.id =:doctorrr");
	
		query.setParameter("doctorrr", appointment.getDoctor().getId());
		
		int Slot = (int) query.getSingleResult();
		
		
		
		System.out.print(appointment.getPatient().getEmail());
			
			appointment.setCreatedate(new Date());
			appointment.setEndTime(appointment.getStartTime().plusMinutes(Slot));
    		appointment.setStatus(AppointmentStatus.pending);
    		em.persist(appointment);
    		
	}
	
	@Override
	public List<Appointment> ListAppointment(){
		
		TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a",Appointment.class);
		
		
		
		return  listAppointment = query.getResultList();
				
	}
	
	
	
	@Override
	public void UpdateAppointment(Appointment app) throws ParseException{
		
		Query query = em.createQuery("SELECT s.slot FROM Schedule s WHERE s.doctor.id =:doctorrr");
		
		query.setParameter("doctorrr", app.getDoctor().getId());
		
		int Slot = (int) query.getSingleResult();
		
		
		app.setCreatedate(new Date());
		app.setEndTime(app.getStartTime().plusMinutes(Slot));
		
		em.merge(app);
	}
	
	@Override
	public void CancelAppointment(Appointment appointment){
		
		appointment.setStatus(AppointmentStatus.cancelled);
		em.merge(appointment);
	}
	
	
	@Override
	public void ConfirmAppointment(Appointment appointment){
		

		
		appointment.setStatus(AppointmentStatus.treated);
		em.merge(appointment);
	}
	
	@Override
	public List<Appointment> ListConfirmedAppointments(AppointmentStatus status){
		
		TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a WHERE a.status = :statussss",Appointment.class);
		
		return  listConfirmedAppointments = query.setParameter("statussss",status).getResultList();
				
	}
	
	
	@Override
	public Patient findPatientById(int id) {
	return 	em.find(Patient.class, id);
		
	}
	
	@Override
	public Doctor findDoctorById(int id) {
	return 	em.find(Doctor.class, id);
		
	}
	
	@Override
	public Motive findMotiveById(int id) {
	return 	em.find(Motive.class, id);
		
	}
	
	
	
	
	
	
	
	
	
	

}
