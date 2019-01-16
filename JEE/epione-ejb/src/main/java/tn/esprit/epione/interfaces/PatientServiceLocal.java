package tn.esprit.epione.interfaces;

import java.text.ParseException;
import java.util.List;

import javax.ejb.Local;

import tn.esprit.epione.enumerations.AppointmentStatus;
import tn.esprit.epione.persistence.Appointment;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Motive;
import tn.esprit.epione.persistence.Patient;
import tn.esprit.epione.persistence.Specialty;

@Local
public interface PatientServiceLocal {

	public void UpdateAppointment(Appointment app) throws ParseException;

	public void AddAppointment(Appointment appointment);
	

	public void CancelAppointment(Appointment app);

	public List<Appointment> ListConfirmedAppointments(AppointmentStatus status);

	public void ConfirmAppointment(Appointment appointment);

	public List<Appointment> ListAppointment();

	public Patient findPatientById(int id);

	public Doctor findDoctorById(int id);

	public Motive findMotiveById(int id);

}
