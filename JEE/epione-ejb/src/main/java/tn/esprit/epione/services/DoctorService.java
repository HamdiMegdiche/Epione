package tn.esprit.epione.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import tn.esprit.epione.enumerations.AccountStatus;
import tn.esprit.epione.enumerations.AccountType;
import tn.esprit.epione.interfaces.DoctorServiceLocal;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Motive;
import tn.esprit.epione.persistence.Specialty;
import tn.esprit.epione.util.Util;

@Stateless
public class DoctorService implements DoctorServiceLocal{
	
	@PersistenceContext(unitName="epione-ejb")
	EntityManager em;
	
	 public List<Specialty> listSpecialty = new ArrayList<>();
	 
	 public List<Motive> listMotive = new ArrayList<>();
	
	
	@Override
	public void AddSpecialty(Specialty specialty) {
		
		em.persist(specialty);
	}
	
	@Override
	public void updateSpecialty(Specialty specialty) {
		
		em.merge(specialty);
	}
	
	@Override
	public void updateSpecialityForDoctorInscrip(Specialty spaciality) {
		
		List<Doctor> list = new ArrayList<Doctor>(spaciality.getDoctors());
		Doctor user = list.get(0);
		user.setPassword(Util.hashPassword(user.getPassword()));
		user.setStatus(AccountStatus.activated);
		user.setRole(AccountType.doctor);
		em.merge(spaciality);
	}
	@Override
	public List<Specialty> ListSpecialty(){
		

		Query query = em.createQuery("SELECT s FROM Specialty s",Specialty.class);
		
		return  listSpecialty = query.getResultList();
				
	}
	
	@Override
	public List<Specialty> ListSimpleSpecialty(){
		
		Query query = em.createQuery("SELECT s.id,s.name FROM Specialty s",Specialty.class);

		
		return  listSpecialty = query.getResultList();
				
	}
	
	
	@Override
	public boolean AddMotive(Motive motive) {
		if(motive.getName()==null) {
			return false;
		}else {
			em.persist(motive);
			return true;
		}
		
	}
	
	@Override
	public List<Motive> ListMotive(){
		
		TypedQuery<Motive> query = em.createQuery("SELECT r FROM Motive r",Motive.class);
		
		return  listMotive = query.getResultList();
				
	}
	
	@Override
	public Specialty findSpecialtyById(int id) {
	return 	em.find(Specialty.class, id);
		
	}
	
	@Override
	public Specialty findSimpleSpecialtyById(int id) {
TypedQuery<Object[]> query = em.createQuery("SELECT s.id,s.name FROM Specialty s WHERE s.id=:idd",Object[].class);
		List<Object[]> listO =query.setParameter("idd", id).getResultList();	
		if(listO.isEmpty()) return null;
		Specialty sp = new Specialty();
		sp.setName((String)listO.get(0)[1]);
		sp.setId((int)listO.get(0)[0]);
		return   sp;
	}
	
	@Override
	public Specialty findSimpleSpecialtyByName(String name) {
		TypedQuery<Object[]>  query = em.createQuery("SELECT s.id,s.name FROM Specialty s WHERE s.name=:namee",Object[].class);
		
		List<Object[]>  listO = query.setParameter("namee", name).getResultList();	
		if(listO.isEmpty()) return null;
		Specialty sp = new Specialty();
		sp.setName((String)listO.get(0)[1]);
		sp.setId((int)listO.get(0)[0]);
		return   sp;
	}
	
	@Override
	public Motive findSimpleMotiveByName(String name) {
		TypedQuery<Object[]>  query = em.createQuery("SELECT s.id,s.name FROM Motive s WHERE s.name=:namee",Object[].class);
		
		List<Object[]>  listO = query.setParameter("namee", name).getResultList();	
		if(listO.isEmpty()) return null;
		Motive m = new Motive();
		m.setName((String)listO.get(0)[1]);
		m.setId((int)listO.get(0)[0]);
		return   m;
	}
	
	@Override
	public Doctor findDoctorById(int id) {
	return 	em.find(Doctor.class, id);
		
	}
	@Override
	public Motive findMotiveById(int id) {
	return 	em.find(Motive.class, id);
		
	}
	
	@Override
	public void updateDoctor(Doctor d) {
		em.merge(d);
	}
	@Override
	public void updateMotive(Motive d) {
		em.merge(d);
	}

	@Override
	public void removeMotive(Motive m) {
		em.remove(em.contains(m) ? m : em.merge(m));
	}
	
	@Override
	public void removeMotiveById(int id) {
		Motive m = em.find(Motive.class, id);
		if(m==null) return;
		em.remove(m);
	}

}
