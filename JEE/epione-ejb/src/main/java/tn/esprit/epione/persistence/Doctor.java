package tn.esprit.epione.persistence;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tn.esprit.epione.enumerations.AccountType;

@Entity
@DiscriminatorValue(value = "doctor")
public class Doctor extends User implements Serializable {

	private String licenseNumber;
	private String description;

	private Double longitude;
	private Double latitude;

	// Navigation Properties

	@JsonIgnore
	@ManyToMany
	@JoinColumn(name = "medicalRecord_id")
	private List<MedicalRecord> medicalRecords;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "doctor_motive", joinColumns = @JoinColumn(name = "doctor_id"), inverseJoinColumns = @JoinColumn(name = "motive_id"))
	private Set<Motive> motives = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE)
	private List<Conversation> conversations;

	@JsonIgnore
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL ,orphanRemoval=true, fetch = FetchType.EAGER)
	private Set<Appointment> appointments = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE)
	private List<Holiday> holidays;

	@ManyToOne
	@JoinColumn(name = "specialty_id")
	private Specialty specialty;

	
	@OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
	private Schedule schedule;

	public Doctor() {
		this.setRole(AccountType.doctor);
	}

	public Doctor(int id) {
		this.id = id;
		this.setRole(AccountType.doctor);
	}

	public Set<Motive> getMotives() {
		return motives;
	}

	public void setMotives(Set<Motive> motives) {
		this.motives = motives;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public List<MedicalRecord> getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<Holiday> getHolidays() {
		return holidays;
	}

	public void setHolidays(List<Holiday> holidays) {
		this.holidays = holidays;
	}

	public List<Conversation> getConversations() {
		return conversations;
	}

	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		schedule.setDoctor(this);
		this.schedule = schedule;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;

	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void addMotive(Motive m) {
		motives.add(m);
		m.getDoctors().add(this);

	}

	public void removeMotive(Motive m) {
		motives.remove(m);
		m.getDoctors().remove(this);
	}
	
	
	public void addAppointment(Appointment a) {
		appointments.add(a);
		a.setDoctor(this);

	}

	public void removeAppointment(Appointment a) {
		appointments.remove(a);
		a.setDoctor(null);
	}
	
	

}
