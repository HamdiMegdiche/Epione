package tn.esprit.epione.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tn.esprit.epione.enumerations.AccountStatus;
import tn.esprit.epione.enumerations.AccountType;
import tn.esprit.epione.interfaces.extractDataServiceLocal;
import tn.esprit.epione.interfaces.extractDataServiceRemote;
import tn.esprit.epione.persistence.DoctolibDoctor;
import tn.esprit.epione.persistence.Doctor;
import tn.esprit.epione.persistence.Specialty;



@Stateless
public class extractDataService implements extractDataServiceLocal, extractDataServiceRemote {
	@PersistenceContext
	EntityManager em;

	@Override
	public List<DoctolibDoctor> getAllDoctorsDoctolib() throws IOException {
		List<DoctolibDoctor> doctors = new ArrayList<>();
		Document doc = Jsoup.connect("https://www.doctolib.fr/directory/").get();
		String title = doc.title();

		Elements links = doc.select("div.seo-directory-doctor");
		for (Element link : links) {
			String fullName = link.getElementsByClass("seo-directory-doctor-link").text();
			String speciality = link.getElementsByClass("seo-directory-doctor-speciality").text();
			String address = link.getElementsByClass("seo-directory-doctor-address").text();
			DoctolibDoctor doctor = new DoctolibDoctor(fullName, speciality, address);
			doctors.add(doctor);
		}
		return doctors;
	}

	@Override
	public List<DoctolibDoctor> getDoctorsDoctolibByFirstLettreName(String name) throws IOException {
		List<DoctolibDoctor> doctors = new ArrayList<>();
		Document doc = Jsoup.connect("https://www.doctolib.fr/directory/" + name).get();
		String title = doc.title();

		Elements links = doc.select("div.seo-directory-doctor");
		for (Element link : links) {
			String fullName = link.getElementsByClass("seo-directory-doctor-link").text();
			String speciality = link.getElementsByClass("seo-directory-doctor-speciality").text();
			String address = link.getElementsByClass("seo-directory-doctor-address").text();
			DoctolibDoctor doctor = new DoctolibDoctor(fullName, speciality, address);
			doctors.add(doctor);
		}

		return doctors;
	}

	@Override
	public List<DoctolibDoctor> getDoctorsDoctolibBySpecialtyLocation(String specialty, String location)
			throws IOException {
		List<DoctolibDoctor> doctors = new ArrayList<>();
		Document doc = Jsoup.connect("https://www.doctolib.fr/" + specialty + "/" + location).get();
		String title = doc.title();

		Elements links = doc.getElementsByClass("dl-search-result");
		for (Element link : links) {
			String fullName = link.getElementsByClass("dl-search-result-name").text();
			String speciality = link.getElementsByClass("dl-search-result-subtitle").text();
			String address = link.getElementsByClass("dl-text").text();
			String img = "https:" + link.select("img").attr("src");
			double latitude = Double.parseDouble(link.attr("data-lat"));
			double longitude = Double.parseDouble(link.attr("data-lng"));
			String path = link.getElementsByClass("js-search-result-path").attr("href");

			Document doc1 = Jsoup.connect("https://www.doctolib.fr/" + path).get();
			String ratesRefunds = doc1.select("div.dl-profile-text").first().text();
			String paymentMethode = null;
			Elements payment = doc1.select("h3.dl-profile-card-subtitle");
			for (Element el : payment) {
				if (el.text().equalsIgnoreCase("Moyens de paiement")) {
					paymentMethode = el.nextElementSibling().text();
				}
			}

			DoctolibDoctor doctor = new DoctolibDoctor(fullName, speciality, address, img, latitude, longitude, path,
					ratesRefunds, paymentMethode);
			doctors.add(doctor);
		}

		return doctors;
	}

	@Override
	public List<DoctolibDoctor> getDoctorsDoctolibBySpecialtyAndPage(String speciality, int page) throws IOException {

		List<DoctolibDoctor> doctors = new ArrayList<>();
		Document doc = Jsoup.connect("https://www.doctolib.fr/" + speciality + "?page=" + page).get();

		Elements paragraphs = doc.getElementsByClass("dl-search-result-presentation");
		for (Element p : paragraphs) {

			String fullName = p.select(".dl-search-result-name").text();
			String address = p.select(".dl-text").text();
			String img = "https:" + p.select("img").attr("src");
			String paymentMethode = p.select("div.dl-search-result-regulation-sector").text();
			// String ratesRefunds = doc.select("div.dl-profile-text").first().text();
			String specialty = p.select("div.dl-search-result-subtitle").text();
			Element pathdoctolib = p.select("a.dl-search-result-name").first();
			String path = pathdoctolib.absUrl("href");
			DoctolibDoctor doctor = new DoctolibDoctor(fullName, specialty, address, img, 42, 8, pathdoctolib.text(),
					"Carte Vitale acceptée", paymentMethode);
			doctors.add(doctor);
		}
		return doctors;
	}

	@Override
	public int addDoctorDoctolibByPath(String path) throws IOException {
		DoctolibDoctor dDoctolib = getDoctorDoctolibByPath(path);
		Doctor d = new Doctor();
		d.setStatus(AccountStatus.waitingForConfirmation);
		d.setRole(AccountType.doctor);
		d.setAddress(dDoctolib.getAddress());
		d.setLatitude(dDoctolib.getLatitude());
		d.setLongitude(dDoctolib.getLongitude());
		d.setPhoto(dDoctolib.getImg());
		Specialty s = new Specialty();
		s.setName(dDoctolib.getSpeciality());
		d.setSpecialty(s);
		em.persist(s);
		em.persist(d);
		em.flush();
		return 1;
	}

	@Override
	public List<String> getDoctolibSpecialty() throws IOException {
		List<String> listSpecialty = new ArrayList<>();
		Document doc1 = Jsoup.connect("https://www.doctolib.fr/specialities").get();
		Elements Img = doc1.getElementsByClass("list-unstyled").select("li");
		for (Element e : Img) {
			listSpecialty.add(e.text());
			// System.out.println("links : " + e.text());
		}
		return listSpecialty;
	}

	@Override
	public List<DoctolibDoctor> getDoctorsDoctolibBySpecialtyAvailableToday(String spec) throws IOException {
		List<DoctolibDoctor> doctors = new ArrayList<>();
		String url = "https://www.doctolib.fr/" + spec + "?availabilities=1";
		Document doc = Jsoup.connect(url).get();
		String title = doc.title();
		System.out.println("title :" + title + "  url  : " + url);

		Elements links = doc.getElementsByClass("dl-search-result");
		for (Element link : links) {
			String fullName = link.getElementsByClass("dl-search-result-name").text();
			String speciality = link.getElementsByClass("dl-search-result-subtitle").text();
			String address = link.getElementsByClass("dl-text").text();
			String img = "https:" + link.select("img").attr("src");
			double latitude = Double.parseDouble(link.attr("data-lat"));
			double longitude = Double.parseDouble(link.attr("data-lng"));
			String path = link.getElementsByClass("js-search-result-path").attr("href");

			Document doc1 = Jsoup.connect("https://www.doctolib.fr/" + path).get();
			String ratesRefunds = doc1.select("div.dl-profile-text").first().text();
			String paymentMethode = null;
			Elements payment = doc1.select("h3.dl-profile-card-subtitle");
			for (Element el : payment) {
				if (el.text().equalsIgnoreCase("Moyens de paiement")) {
					paymentMethode = el.nextElementSibling().text();
				}
			}

			DoctolibDoctor doctor = new DoctolibDoctor(fullName, speciality, address, img, latitude, longitude, path,
					ratesRefunds, paymentMethode);
			doctors.add(doctor);
		}

		return doctors;

	}

	public DoctolibDoctor getDoctorDoctolibByPath(String path) throws IOException {
		Document doc1 = Jsoup.connect("https://www.doctolib.fr/" + path).get();
		String fullName = doc1.select("h1.dl-profile-header-name").text();
		String speciality = doc1.select("h2.dl-profile-header-speciality").text();
		String address = doc1.select("div.dl-profile-text").get(2).text();
		Element imgElement = doc1.select("img[itemprop=\"image\"][src]").first();
		String img = imgElement.absUrl("src");

		String ratesRefunds = doc1.select("div.dl-profile-text").first().text();
		String paymentMethode = null;
		Elements payment = doc1.select("h3.dl-profile-card-subtitle");
		for (Element el : payment) {
			if (el.text().equalsIgnoreCase("Moyens de paiement")) {
				paymentMethode = el.nextElementSibling().text();
			}
		}

		DoctolibDoctor doctor = new DoctolibDoctor(fullName, speciality, address, img, 0, 0, path, ratesRefunds,
				paymentMethode);
		return doctor;
	}

}
