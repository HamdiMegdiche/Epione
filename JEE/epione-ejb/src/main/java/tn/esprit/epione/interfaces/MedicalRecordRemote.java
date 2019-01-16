package tn.esprit.epione.interfaces;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.epione.persistence.MedicalRecord;
import tn.esprit.epione.persistence.Patient;
import tn.esprit.epione.persistence.Recommendation;
import tn.esprit.epione.persistence.Treatement;
import tn.esprit.epione.persistence.User;

@Remote
public interface MedicalRecordRemote {

	public void addmedrecord(MedicalRecord m);	
	
    public void addtreatement(Treatement t);
    
    public void recommendDoctors(Recommendation r);
	
	public void updatemedrec(Treatement t1);
    
	public void validatetreatement(Treatement t);
	
	public void invalidatetreatement(Treatement t) ;
    
	public List<Treatement> getMedicalRecord(User u);

	// public void remindpatient();
    
	 // public void reminddoctor();

}
