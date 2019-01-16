package tn.esprit.epione.interfaces;

import java.util.List;

import javax.ejb.Local;

import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Specialty;

@Local
public interface SearchServiceLocal {

	public List<Doctor> ListSearchDoctorByCivility(String civility);

	public List<Doctor> ListSearchDoctorByLatitudeAndLongitude(Double latitude, Double longitude);

	public List<Doctor> ListSearchDoctorBySpecialty(Specialty specialty);

	public List<Doctor> ListSearchDoctorByCivilityAndSpecialty(String civility, Specialty specialty);

}
