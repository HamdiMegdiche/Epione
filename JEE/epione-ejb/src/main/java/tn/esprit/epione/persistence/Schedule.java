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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;



@Entity
public class Schedule implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	private int slot;

	// Navigation Properties
//	@JsonIgnore
	@JsonProperty(access= Access.WRITE_ONLY)
	  @OneToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "doctor_id")
	  	private Doctor doctor;
	  

	  @JsonProperty(access= Access.WRITE_ONLY)
		@OneToMany(mappedBy="schedule", 
		        cascade = CascadeType.ALL, 
		        orphanRemoval = true,fetch= FetchType.EAGER)
	  private Set<WorkTime> worktimes = new HashSet<WorkTime>();

	public Schedule() {
	}
	
	

	

public Set<WorkTime> getWorktimes() {
		return worktimes;
	}





	public void setWorktimes(Set<WorkTime> worktimes) {
		this.worktimes = worktimes;
	}





public void addWorkTime(WorkTime wt) {
		
		this.worktimes.add(wt);
		wt.setSchedule(this);
		
	}
	
	public void removeComment(WorkTime wt) {
		
		wt.setSchedule(null);
		this.worktimes.remove(wt);
		
	}
	

	



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
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
		Schedule other = (Schedule) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
