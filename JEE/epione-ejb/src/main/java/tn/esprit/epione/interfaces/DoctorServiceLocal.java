package tn.esprit.epione.interfaces;

import java.util.List;

import javax.ejb.Local;

import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Motive;
import tn.esprit.epione.persistence.Specialty;

@Local
public interface DoctorServiceLocal {

	public List<Motive> ListMotive();

	
	
	public boolean AddMotive(Motive motive);

	public List<Specialty> ListSpecialty();

	public void AddSpecialty(Specialty specialty);



	public Specialty findSpecialtyById(int id);



	public void updateSpecialty(Specialty specialty);



	public Doctor findDoctorById(int id);



	public Motive findMotiveById(int id);



	public void updateDoctor(Doctor d);



	public void updateSpecialityForDoctorInscrip(Specialty spaciality);



	List<Specialty> ListSimpleSpecialty();



	public Specialty findSimpleSpecialtyById(int id);



	public Specialty findSimpleSpecialtyByName(String name);



	public Motive findSimpleMotiveByName(String name);



	public void updateMotive(Motive d);



	public void removeMotive(Motive m);



	public void removeMotiveById(int id);



	

}
