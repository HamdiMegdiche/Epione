package tn.esprit.epione.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tn.esprit.epione.enumerations.AccountType;
import tn.esprit.epione.enumerations.ResultType;

@Entity
@Table(name="treatement")
public class Treatement implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String illness ;
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date dateadded;
	
    @Column(columnDefinition = "varchar(30)")
	@Enumerated(EnumType.STRING)
	private ResultType result;
	private String justif;
    
	@OneToOne
	private User doctor;
	
	
	@ManyToOne
	private MedicalRecord medicalRecord;
	
	public Treatement() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIllness() {
		return illness;
	}

	public void setIllness(String illness) {
		this.illness = illness;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateadded() {
		return dateadded;
	}

	public void setDateadded(Date dateadded) {
		this.dateadded = dateadded;
	}

	public ResultType getResult() {
		return result;
	}

	public void setResult(ResultType result) {
		this.result = result;
	}

	public String getJustif() {
		return justif;
	}

	public void setJustif(String justif) {
		this.justif = justif;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		if(doctor.getRole()== AccountType.doctor )
		{	this.doctor = doctor; }
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	
	

    

}
