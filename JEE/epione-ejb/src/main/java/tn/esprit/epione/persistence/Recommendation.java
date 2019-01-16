package tn.esprit.epione.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="recommendation")


public class Recommendation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany 
	private List<User> recomendeddoctors= new ArrayList<>();
	
	@OneToOne 
	private Treatement treat;
	
	
	public Recommendation() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<User> getRecomendeddoctors() {
		return recomendeddoctors;
	}

	public void setRecomendeddoctors(List<User> recomendeddoctors) {
		this.recomendeddoctors = recomendeddoctors;
	}

	public Treatement getTreat() {
		return treat;
	}

	public void setTreat(Treatement treat) {
		this.treat = treat;
	}

	

}
