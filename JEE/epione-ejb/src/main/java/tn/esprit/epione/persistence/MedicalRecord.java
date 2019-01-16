package tn.esprit.epione.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tn.esprit.epione.enumerations.AccountType;

@Entity
@Table(name="medicalRecord")
public class MedicalRecord implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	@Temporal(TemporalType.DATE)
	private Date lastModified;

	private static final long serialVersionUID = 1L;

	// Navigation Properties
	@OneToOne
	private User patient;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "medicalRecord", cascade = CascadeType.REMOVE)
	private Set<Treatement> treatements = new HashSet<Treatement>();
	

	public MedicalRecord() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Date getLastModified() {
		return lastModified;
	}


	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}


	public User getPatient() {
		return patient;
	}


	public void setPatient(User patient) {
		if(patient.getRole()== AccountType.patient)
		{	this.patient = patient; }
		
	}


	public Set<Treatement> getTreatements() {
		return treatements;
	}


	public void setTreatements(Set<Treatement> treatements) {
		this.treatements = treatements;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	


}
