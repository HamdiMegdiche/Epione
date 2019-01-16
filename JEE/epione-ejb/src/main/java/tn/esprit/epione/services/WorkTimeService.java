package tn.esprit.epione.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import tn.esprit.epione.enumerations.AppointmentStatus;
import tn.esprit.epione.interfaces.WorkTimeServiceLocal;
import tn.esprit.epione.persistence.Appointment;
import tn.esprit.epione.persistence.Schedule;
import tn.esprit.epione.persistence.WorkTime;
import tn.esprit.epione.persistence.Doctor;


@Stateless
public class WorkTimeService implements WorkTimeServiceLocal{
	
	@PersistenceContext(unitName="epione-ejb")
	EntityManager em;
	
	 public List<Schedule> listSchedule = new ArrayList<>();
	 
	 public List<WorkTime> listWorkTime = new ArrayList<>();
	 
	 public Set<WorkTime> listWorkTimes= new HashSet<WorkTime>();

	
	@Override
	public  void AddSchedule(Schedule schedule){
		

    		em.persist(schedule);
    		
		
	}
	
	@Override
	public void updateSchedule(Schedule schedule) {
		em.merge(schedule);
	}
	
	@Override
	public List<Schedule> ListSchedule(){
		
		TypedQuery<Schedule> query = em.createQuery("SELECT s FROM Schedule s",Schedule.class);
		
		return  listSchedule = query.getResultList();
				
	}
	
	@Override
	public  void AddWorkTime(WorkTime workTime){
		

    		em.persist(workTime);
    		
		
	}
	
	@Override
	public Set<WorkTime> ListWorkTimeByDocID(int id){
		
		Query query = em.createQuery("SELECT d.schedule FROM Doctor d WHERE d.id =:doctorrr");
		
		query.setParameter("doctorrr", id);
		
		Schedule schedule = (Schedule) query.getSingleResult();
		
	//	TypedQuery<WorkTime> query2 = em.createQuery("SELECT w FROM WorkTime w WHERE w.schedule = :scheduleee",WorkTime.class);
		
		return  listWorkTimes = schedule.getWorktimes();
				
	}
	
	
	
	
	@Override
	public List<WorkTime> ListWorkTime(){
		
		TypedQuery<WorkTime> query = em.createQuery("SELECT w FROM WorkTime w",WorkTime.class);
		
		return  listWorkTime = query.getResultList();
				
	}

	@Override
	public WorkTime getWorkTimeById(int id) {
		
		return em.find(WorkTime.class, id);
		
	}
	
	@Override
	public Schedule getScheduleById(int id) {
		
		return em.find(Schedule.class, id);
		
	}
	
	
}
