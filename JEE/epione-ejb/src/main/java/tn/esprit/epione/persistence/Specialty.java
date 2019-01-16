package tn.esprit.epione.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Specialty implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	// Navigation Properties
	
//	@JsonIgnore
//	@OneToMany(mappedBy = "speciality",  cascade = CascadeType.REMOVE)
//	private List<Motive> motives;
	
	@JsonProperty(access= Access.WRITE_ONLY)
	@OneToMany(mappedBy="specialty",cascade= CascadeType.ALL,orphanRemoval=true,fetch = FetchType.EAGER)
	private Set<Doctor> doctors = new HashSet<>();
	
	@JsonProperty(access= Access.WRITE_ONLY)
	@OneToMany(mappedBy="specialty",cascade= CascadeType.ALL,orphanRemoval=true,fetch = FetchType.EAGER)
	private Set<Motive> motives = new HashSet<>();
	
	
	
	public Specialty() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Set<Motive> getMotives() {
		return motives;
	}

	public void setMotives(Set<Motive> motives) {
		this.motives = motives;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Specialty(int id) {
		super();
		this.id = id;
	}

	public void addDoctor(Doctor d) {
		this.doctors.add(d);
		d.setSpecialty(this);
	}
	
	public void removeDocore(Doctor d) {
		d.setSpecialty(null);
		this.doctors.remove(d);
	}
	
	public void addMotive(Motive m) {
		this.motives.add(m);
		m.setSpeciality(this);
	}
	
	public void removeMotive(Motive m) {
		m.setSpeciality(null);
		this.motives.remove(m);
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
		Specialty other = (Specialty) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
