package tn.esprit.epione.interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import tn.esprit.epione.persistence.MedicalRecord;
import tn.esprit.epione.persistence.Patient;
import tn.esprit.epione.persistence.Recommendation;
import tn.esprit.epione.persistence.Treatement;
import tn.esprit.epione.persistence.User;

@Local
public interface MedicalRecordLocal {

	public void addmedrecord(MedicalRecord m);
	
    public void addtreatement(Treatement t);
  
    public void recommendDoctors(Recommendation r);

	public void validatetreatement(Treatement t);
   
	public void invalidatetreatement(Treatement t) ;
	
	public void updatemedrec(Treatement t1 );
	
	public List<Treatement> getMedicalRecord(User u);
   
	public void remindpatient();
	
	public void removeMedRec(MedicalRecord m);
    
    //   public void reminddoctor();
    
	
}
