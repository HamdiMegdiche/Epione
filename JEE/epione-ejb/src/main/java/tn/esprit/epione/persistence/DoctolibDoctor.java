package tn.esprit.epione.persistence;

public class DoctolibDoctor {
	private String fullName;
	private String speciality;
	private String address;
	private String img;
	private double latitude;
	private double longitude;
	private String path;
	private String ratesRefunds;
	private String paymentMethode;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRatesRefunds() {
		return ratesRefunds;
	}

	public void setRatesRefunds(String ratesRefunds) {
		this.ratesRefunds = ratesRefunds;
	}

	public String getPaymentMethode() {
		return paymentMethode;
	}

	public void setpaymentMethode(String paymentMethode) {
		this.paymentMethode = paymentMethode;
	}

	public DoctolibDoctor(String fullName, String speciality, String address) {
		super();
		this.fullName = fullName;
		this.speciality = speciality;
		this.address = address;
	}

	public DoctolibDoctor(String fullName, String speciality, String address, String img, double latitude,
			double longitude, String path, String ratesRefunds, String paymentMethode) {
		super();
		this.fullName = fullName;
		this.speciality = speciality;
		this.address = address;
		this.img = img;
		this.latitude = latitude;
		this.longitude = longitude;
		this.path = path;
		this.ratesRefunds = ratesRefunds;
		this.paymentMethode = paymentMethode;
	}

	public DoctolibDoctor() {
	}

}
