package tn.esprit.epione.interfaces;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import tn.esprit.epione.persistence.DoctolibDoctor;



//import persistence.DoctolibUser;

@Local
public interface extractDataServiceLocal {

	public List<DoctolibDoctor> getAllDoctorsDoctolib() throws IOException;

	public List<DoctolibDoctor> getDoctorsDoctolibByFirstLettreName(String name) throws IOException;

	public List<DoctolibDoctor> getDoctorsDoctolibBySpecialtyLocation(String specialty, String location)
			throws IOException;

	public List<DoctolibDoctor> getDoctorsDoctolibBySpecialtyAndPage(String speciality, int page) throws IOException;

	public int addDoctorDoctolibByPath(String path) throws IOException;

	public List<String> getDoctolibSpecialty() throws IOException;

	public List<DoctolibDoctor> getDoctorsDoctolibBySpecialtyAvailableToday(String speciality) throws IOException;

}
