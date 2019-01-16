package tn.esprit.epione.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.epione.enumerations.AppointmentStatus;
import tn.esprit.epione.interfaces.SearchServiceLocal;
import tn.esprit.epione.persistence.Appointment;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Specialty;



@Stateless
public class SearchService implements SearchServiceLocal{
	
	@PersistenceContext(unitName="epione-ejb")
	EntityManager em;
	
	public List<Doctor> ListSearchDoctorByAddress = new ArrayList<>();
	 
	 public List<Doctor> ListSearchDoctorBySpecialty = new ArrayList<>();
	 
	 public List<Doctor> ListSearchDoctorByCivilityAndSpecialty = new ArrayList<>();
	 
	public List<Doctor> ListSearchDoctorByLatitudeAndLongitude = new ArrayList<>();

	 
	
	@Override
	public List<Doctor> ListSearchDoctorByCivility(String address){
		
		TypedQuery<Doctor> query = em.createQuery("SELECT a FROM Doctor a WHERE a.address = :address",Doctor.class);
		
		return  ListSearchDoctorByAddress = query.setParameter("address",address).getResultList();
				
	}
	
	@Override
	public List<Doctor> ListSearchDoctorBySpecialty(Specialty specialty){
		
		TypedQuery<Doctor> query = em.createQuery("SELECT a FROM Doctor a WHERE a.specialty = :specialtyyy",Doctor.class);
		
		return  ListSearchDoctorBySpecialty = query.setParameter("specialtyyy",specialty).getResultList();
				
	}
	
	@Override
	public List<Doctor> ListSearchDoctorByCivilityAndSpecialty(String address,Specialty specialty){
		
		TypedQuery<Doctor> query = em.createQuery("SELECT a FROM Doctor a WHERE a.address = :addressss AND a.specialty = :specialtyyyy",Doctor.class);
		
		return  ListSearchDoctorByCivilityAndSpecialty = query.setParameter("addressss",address).setParameter("specialtyyyy", specialty).getResultList();
				
	}
	
	@Override
	public List<Doctor> ListSearchDoctorByLatitudeAndLongitude(Double latitude,Double longitude){
		
		TypedQuery<Doctor> query = em.createQuery("SELECT a FROM Doctor a WHERE a.latitude = :latitudeeee AND a.longitude = :longitudeeee",Doctor.class);
		
		return  ListSearchDoctorByLatitudeAndLongitude = query.setParameter("latitudeeee",latitude).setParameter("longitudeeee", longitude).getResultList();
				
	}

}
