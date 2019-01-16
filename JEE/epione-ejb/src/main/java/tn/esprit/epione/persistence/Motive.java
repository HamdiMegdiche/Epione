package tn.esprit.epione.persistence;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Motive implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	// Navigation Properties
	
	@JsonProperty(access= Access.WRITE_ONLY)
	@ManyToOne
	@JoinColumn(name="speciality_id")
	private Specialty specialty;
	
	@JsonProperty(access= Access.WRITE_ONLY)
	@OneToMany(mappedBy = "motive",cascade = CascadeType.ALL ,orphanRemoval=true, fetch = FetchType.EAGER)
	private Set<Appointment> appointments = new HashSet<>();

	@JsonProperty(access= Access.WRITE_ONLY)
	@ManyToMany(mappedBy="motives",fetch = FetchType.EAGER)
	private Set<Doctor> doctors = new HashSet<>();
	
	public Motive() {
	}

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Specialty getSpeciality() {
		return specialty;
	}

	public void setSpeciality(Specialty specialty) {
		this.specialty = specialty;
	}

	public Motive(int id) {
		super();
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Motive other = (Motive) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public void addAppointment(Appointment a) {
		appointments.add(a);
		a.setMotive(this);

	}

	public void removeAppointment(Appointment a) {
		appointments.remove(a);
		a.setMotive(null);
	}
	

}
