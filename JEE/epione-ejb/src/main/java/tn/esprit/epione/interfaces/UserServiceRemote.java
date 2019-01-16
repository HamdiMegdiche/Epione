package tn.esprit.epione.interfaces;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.epione.enumerations.AccountStatus;
import tn.esprit.epione.persistence.Administrator;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Patient;
import tn.esprit.epione.persistence.User;

@Remote
public interface UserServiceRemote {

public User getUserById(int idUser);
	
	public int addDoctor(Doctor user);
	public int addPatient(Patient user);
	public int addAdministrator(Administrator user);
	
	public boolean signInWithUsername(String username, String pwd);
	public boolean signInWithEmail(String email, String pwd);
	
	public boolean signOut(int idUser);
	
	public boolean changePassword(String oldPassword, String newPassword, User u);

	public boolean forgotPasswordByMail(int idUser);

	public boolean changeForgotPassword(int idUser, String token, String newPwd);

	public User findUserById(int id);


	public List<User> getAllUsers();
	public List<User> getAllUsersByStatus(AccountStatus status);

	public void addUserPhoto(int user_id, String photo);

	public boolean banAccount(User u);


	public boolean confirmAccount(String token, User u);

	public int isLoggedIn24H();

	public List<Doctor> ListDoctor();

	public List<Patient> ListPatient();
	public User getUserByEmailOrUsername(String EmailOrUsername);

}
