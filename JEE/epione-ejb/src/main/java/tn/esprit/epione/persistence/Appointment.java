package tn.esprit.epione.persistence;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import tn.esprit.epione.enumerations.AppointmentStatus;
import tn.esprit.epione.util.LocalDateDeserializer;
import tn.esprit.epione.util.LocalDateSerializer;
import tn.esprit.epione.util.LocalTimeDeserializer;
import tn.esprit.epione.util.LocalTimeSerializer;

@Entity
public class Appointment implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String message;
	
	@Temporal(TemporalType.DATE)
	private Date createdate;
	
//	@Temporal(TemporalType.DATE)
	
	 @JsonDeserialize(using = LocalDateDeserializer.class)
	 @JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate date;
	
//	@Temporal(TemporalType.TIME)
	 @JsonDeserialize(using = LocalTimeDeserializer.class)
	 @JsonSerialize(using = LocalTimeSerializer.class)
	private LocalTime startTime;
	
//	@Temporal(TemporalType.TIME)
	 @JsonDeserialize(using = LocalTimeDeserializer.class)
	 @JsonSerialize(using = LocalTimeSerializer.class)
	private LocalTime endTime;
	
	@Column(columnDefinition = "varchar(20) default 'pending'")
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;

	// Navigation Properties
	@ManyToOne
	private MedicalRecord medicalRecord;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name = "motive_id")
	private Motive motive;
	
	
	@OneToOne(mappedBy="appointment", cascade = CascadeType.REMOVE)
	@JoinColumn(name="evaluation_id",referencedColumnName="id",insertable=true,updatable=true)
	private Evaluation evaluation;

	
	
	public Appointment() {
	}

	public Appointment(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return message;
	}

	public void setDescription(String description) {
		this.message = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Motive getMotive() {
		return motive;
	}

	public void setMotive(Motive motive) {
		this.motive = motive;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((doctor == null) ? 0 : doctor.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		Appointment other = (Appointment) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (doctor == null) {
			if (other.doctor != null)
				return false;
		} else if (!doctor.equals(other.doctor))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
	
	
	
	

}


