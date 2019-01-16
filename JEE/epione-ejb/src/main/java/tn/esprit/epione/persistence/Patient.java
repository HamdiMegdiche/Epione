package tn.esprit.epione.persistence;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tn.esprit.epione.enumerations.AccountType;

@Entity
@DiscriminatorValue(value = "patient")
public class Patient extends User implements Serializable{

	private String medicalfile;
	private String socialSecurityNumber;

	// Navigation Properties

	@JsonIgnore
	@OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
	private List<Conversation> conversations;

	@JsonIgnore
	@OneToOne(mappedBy = "patient", cascade = CascadeType.REMOVE)
	private MedicalRecord medicalRecord;

	@JsonIgnore
	@OneToMany(mappedBy = "patient",cascade = CascadeType.ALL ,orphanRemoval=true, fetch = FetchType.EAGER)
	private Set<Appointment> appointments = new HashSet<>();

	public Patient() {
		this.setRole(AccountType.patient);
	}

	public Patient(int id) {
		this.setRole(AccountType.patient);
		this.id = id;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public String getMedicalfile() {
		return medicalfile;
	}

	public void setMedicalfile(String medicalfile) {
		this.medicalfile = medicalfile;
	}

	public List<Conversation> getConversations() {
		return conversations;
	}

	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public void addAppointment(Appointment a) {
		appointments.add(a);
		a.setPatient(this);

	}

	public void removeAppointment(Appointment a) {
		appointments.remove(a);
		a.setPatient(null);
	}
	
	

	

}
