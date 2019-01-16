package tn.esprit.epione.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import tn.esprit.epione.persistence.Appointment;

@Local
public interface AppointmentServiceLocal {
	
	//Crud User
	public void addAppointment(Appointment a );
	public void updateAppointment(Appointment a);
	public void removeAppById(int id);
	public List<Appointment> getAll();
	//Patients Services
	public List<Appointment> MyAppointments(int idPatient);
	public List<Appointment> MyRequests(int idPatient);
	public void cancelRequest(int idAppointment);
	// doctor Services
	public List<Appointment> acceptedRequests(int idPatient);
	//
	public List<Appointment> SelectAppByDay(Date date);
	public Appointment SelectAppByDateAndByHour(Date date , String hour); 
	public Appointment consulterApp(int id); 
	
}
