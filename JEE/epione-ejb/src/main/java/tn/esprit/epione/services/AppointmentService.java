package tn.esprit.epione.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.epione.interfaces.AppointmentServiceLocal;
import tn.esprit.epione.persistence.Appointment;
import tn.esprit.epione.persistence.Patient;

@LocalBean
@Stateless
public class AppointmentService implements AppointmentServiceLocal {

	@PersistenceContext(name = "epione-ejb")
	EntityManager em;

	@Override
	public void addAppointment(Appointment a) {
		em.persist(a);
	}

	@Override
	public List<Appointment> getAll() {
		List<Appointment> appointments = new ArrayList<>();
		TypedQuery<Appointment> query = em.createQuery("select e from Appointment e ", Appointment.class);
		appointments = query.getResultList();
		System.out.println("rendez vous:" + appointments.size());
		return appointments;
	}

	@Override
	public void updateAppointment(Appointment a) {
		Appointment ap = em.find(Appointment.class, a.getId());
		ap = a;
		em.merge(ap);

	}

	@Override
	public void removeAppById(int id) {
		Appointment ap = em.find(Appointment.class, id);
		em.remove(ap);
		System.out.println("Rendez vous supprimé");
	}

	@Override
	public List<Appointment> MyAppointments(int idPatient) {
		Patient pat = em.find(Patient.class, idPatient);
		TypedQuery<Appointment> query = em.createQuery(
				" select m from Appointment m " + " where m.patient=:sender " + " and m.state = 1 ", Appointment.class);
		List<Appointment> appointments = query.setParameter("sender", pat).getResultList();
		System.out.println("size of list my appointmtnets" + appointments.size());
		return appointments;
	}

	@Override
	public List<Appointment> MyRequests(int idPatient) {
		Patient pat = em.find(Patient.class, idPatient);
		TypedQuery<Appointment> query = em.createQuery(
				" select m from Appointment m " + " where m.patient=:sender " + " and m.state = 2 ", Appointment.class);
		List<Appointment> appointments = query.setParameter("sender", pat).getResultList();
		System.out.println("size of list my appointmtnets" + appointments.size());
		return appointments;
	}

	@Override
	public void cancelRequest(int idAppointment) {
		Appointment ap = em.find(Appointment.class, idAppointment);
		em.remove(ap);

	}

	@Override
	public List<Appointment> acceptedRequests(int idPatient) {
		Patient pat = em.find(Patient.class, idPatient);
		TypedQuery<Appointment> query = em.createQuery(
				" select m from Appointment m " + " where m.patient=:sender " + " and m.state = 1 ", Appointment.class);
		List<Appointment> appointments = query.setParameter("sender", pat).getResultList();
		System.out.println("size of list my appointmtnets" + appointments.size());
		return appointments;

	}

	@Override
	public List<Appointment> SelectAppByDay(Date date) {
		TypedQuery<Appointment> query = em.createQuery(
				" select m from Appointment m " + " where m.date_appointment=:date " + " and m.state = 1",
				Appointment.class);
		List<Appointment> appointments = query.setParameter("date", date).getResultList();
		System.out.println("size of list my appointmtnets" + appointments.size());
		return appointments;
	}

	@Override
	public Appointment SelectAppByDateAndByHour(Date date, String hour) {
		TypedQuery<Appointment> query = em.createQuery(" select m from Appointment m "
				+ " where m.date_appointment=:date " + " and m.state = 1" + " and m.start_hour = hour",
				Appointment.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		Appointment a = query.getSingleResult();
		return a;
	}

	@Override
	public Appointment consulterApp(int id) {
		TypedQuery<Appointment> query = em.createQuery(" select m from Appointment m " + " where m.id=:id ",
				Appointment.class);
		query.setParameter("id", id);
		Appointment a = query.getSingleResult();

		return a;
	}

}
