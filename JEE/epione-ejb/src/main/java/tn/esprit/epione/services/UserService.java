package tn.esprit.epione.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import tn.esprit.epione.enumerations.AccountStatus;
import tn.esprit.epione.enumerations.AccountType;
import tn.esprit.epione.interfaces.UserServiceLocal;
import tn.esprit.epione.interfaces.UserServiceRemote;
import tn.esprit.epione.persistence.Administrator;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Patient;
import tn.esprit.epione.persistence.User;
import tn.esprit.epione.util.SendConfirmationMail;
import tn.esprit.epione.util.Util;

@Stateless
@LocalBean
public class UserService implements UserServiceLocal, UserServiceRemote {

	@PersistenceContext(unitName = "epione-ejb")
	EntityManager em;

	@Override
	public int addDoctor(Doctor user) {// SignUP
		user.setToken(user.getToken());
		user.setPassword(Util.hashPassword(user.getPassword()));
		user.setStatus(AccountStatus.waitingForConfirmation);
		user.setRole(AccountType.doctor);
		em.persist(user);
		em.flush();
		return user.getId();
	}

	public List<Doctor> listDoctor = new ArrayList<>();

	@Override
	public List<Doctor> ListDoctor() {

		TypedQuery<Doctor> query = em.createQuery("SELECT d FROM Doctor d", Doctor.class);

		return listDoctor = query.getResultList();

	}

	@Override
	public int addPatient(Patient user) {// SignUP
		user.setPassword(Util.hashPassword(user.getPassword()));
		user.setRole(AccountType.patient);
		em.persist(user);
		em.flush();
		return user.getId();
	}

	public List<Patient> listPatient = new ArrayList<>();

	@Override
	public List<Patient> ListPatient() {

		TypedQuery<Patient> query = em.createQuery("SELECT p FROM Patient p", Patient.class);

		return listPatient = query.getResultList();

	}

	@Override
	public int addAdministrator(Administrator user) {// SignUP
		user.setPassword(Util.hashPassword(user.getPassword()));
		em.persist(user);
		em.flush();
		return user.getId();
	}

	@Override
	public User getUserById(int idUser) {
		return em.find(User.class, idUser);
	}

	@Override
	public boolean signInWithUsername(String username, String pwd) {
		return signIn("username", username, pwd);
	}

	@Override
	public boolean signInWithEmail(String email, String pwd) {
		return signIn("email", email, pwd);
	}

	@Override
	public boolean signOut(int idUser) {
		User u = em.find(User.class, idUser);
		u.setStatus(AccountStatus.offline);
		em.merge(u);
		return true;
	}

	@Override
	public boolean confirmAccount(String token, User user) {// Activate account

		User u = em.find(User.class, user.getId());

		String token_db = u.getToken();
		System.out.println(token_db);
		if (token.equals(token_db)) {
			u.setStatus(AccountStatus.activated);
			em.merge(u);
			System.out.println("token is valid ! Account is confirmed.");
			return true;
		} else {
			System.out.println("token not valid");
			return false;
		}
	}

	@Override
	public boolean changePassword(String oldPassword, String newPassword, User u) {
		User user = em.find(User.class, u.getId());
		if (Util.hashPassword(oldPassword).equals(user.getPassword())) {
			user.setPassword(Util.hashPassword(newPassword));

			em.merge(user);
			System.out.println("Password changed: old pwd is " + oldPassword + " new password is " + newPassword);
			return true;
		} else {
			System.out.println("password is wrong");
			return false;
		}
	}

	@Override
	public boolean forgotPasswordByMail(int idUser) {
		try {
			User u = em.find(User.class, idUser);

			String token = Util.getSaltString();
			String mail_content = "Enter the token generated and have the oppotunity to change your password --> "
					+ token;
			System.out.println("**************** Processing sending an email to : " + u.getEmail()
					+ " **************************");
			SendConfirmationMail.sendMail("epioneservice@gmail.com", "epione2018", u.getEmail(),
					"Epione : forgot password", mail_content);

			u.setToken(token);
			em.merge(u);
			System.out.println("**************** DONE Sending **************************");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean changeForgotPassword(int idUser, String token, String newPwd) {
		User u = em.find(User.class, idUser);
		if (isTokenValid(token, idUser)) {
			u.setPassword(Util.hashPassword(newPwd));
			em.merge(u);
			return true;
		}
		return false;
	}

	@Override
	public User findUserById(int id) {
		return em.find(User.class, id);
	}

	@Override
	public List<User> getAllUsers() {
		TypedQuery<User> q = em.createQuery("select u from User u", User.class);
		return q.getResultList();
	}

	@Override
	public List<User> getAllUsersByStatus(AccountStatus status) {
		TypedQuery<User> q = em.createQuery("select u from User u where u.status = :s", User.class);
		q.setParameter("s", status);
		return q.getResultList();
	}

//return the nbr of users that were connected in the last 24h
	@Override
	public int isLoggedIn24H() {
//
		List<User> all = getAllUsers();
		int total = 0;
		for (User u : all) {
			Date loggedin = u.getLastLogin();
			Date dt = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			c.add(Calendar.DATE, -1);
			dt = c.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Query query = em.createNativeQuery("SELECT DATEDIFF(?1,?2)");
			query.setParameter("1", loggedin);
			query.setParameter("2", dateFormat.format(dt));
			System.out.println(query.getSingleResult().toString());
			int difference = Integer.parseInt(query.getSingleResult().toString());
			if (difference <= 1 && difference > 0) {
				total = total + 1;
			}
		}
		return total;
	}

	@Override
	public void addUserPhoto(int user_id, String photo) {
		User u = em.find(User.class, user_id);
		u.setPhoto(photo);
		em.merge(u);
	}

	@Override
	public boolean banAccount(User u) {
		User us = em.find(User.class, u.getId());
		us.setStatus(AccountStatus.banned);
		em.merge(us);
		return true;
	}

	@Override
	public User getUserByEmailOrUsername(String EmailOrUsername) {
		TypedQuery<User> q = em.createQuery("select u FROM User u WHERE u.email = '" + EmailOrUsername
				+ "' or u.username= '" + EmailOrUsername + "'", User.class);
		User user;

		try {
			user = q.getResultList().get(0);

		} catch (Exception e) {
			return null;
		}
		return user;
	}


	// ****************** PRIVATE METHODS ***************************

	private boolean signIn(String fieldName, String fieldValue, String pwd) {

		TypedQuery<User> q = em.createQuery("select u FROM User u WHERE u." + fieldName + " = '" + fieldValue
				+ "' AND u.password= '" + Util.hashPassword(pwd) + "'", User.class);

		User user;

		try {
			user = q.getResultList().get(0);

		} catch (Exception e) {
			return false;
		}

		if (user.getStatus() == AccountStatus.desactivated || user.getStatus() == AccountStatus.waitingForConfirmation
				|| user.getStatus() == AccountStatus.banned)
			return false;

//		User u = em.find(User.class, user.getId());
		user.setStatus(AccountStatus.online);
		user.setLastLogin(Util.getDateNowUTC());
		em.merge(user);
		return true;
	}

	private boolean isTokenValid(String token, int id) {
		User u = em.find(User.class, id);

		String token_db = u.getToken();
		System.out.println(token_db);
		if (token.equals(token_db)) {
			System.out.println("token is valid !");
			return true;
		} else {
			System.out.println("token not valid");
			return false;
		}

	}

}
