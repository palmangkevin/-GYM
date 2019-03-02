package gym;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Classe abstraite qui définit des méthodes et champs communes aux professionnels et aux membres.
 * Les classes Pro et Member en héritent.
 */
abstract class UserInfo {

	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String gender;
	private String phoneNumber;
	private String mail;
	private String id;
	private String address;
	private String city;
	private String province;
	private String codePostal;

	/**
	 * Constructeur de classe
	 *
	 * @param firstName
	 * @param lastName
	 * @param dateOfBirth
	 * @param gender
	 * @param phoneNumber
	 * @param mail
	 * @param address
	 * @param city
	 * @param province
	 * @param codePostal
	 */
	public UserInfo(String firstName, String lastName, Date dateOfBirth, String gender, String phoneNumber, String mail, String address, String city, String province, String codePostal) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.mail = mail;
		this.address = address;
		this.city = city;
		this.province = province;
		this.codePostal = codePostal;
		this.id = generateId();
	}

	/**
	 * Méthode qui génère un hashcode représentant un ID à partir du prénom, du nom et de la date de naissance
	 * @return un ID de 7 chiffres
	 */
	private String generateId() {
		List<String> list = Arrays.asList(firstName, lastName, dateOfBirth.toString());

		int bighash = 17;

		for (String item : list)
			bighash = bighash * 31 + item.hashCode();

		return (new DecimalFormat("000000000")).format(Math.abs(bighash)).substring(0, 9);
	}

	public String getFirstName() { return firstName; }

	public String getLastName() { return lastName; }

	public String getName() { return firstName + " " + lastName; }

	public String getId() { return id; }

	public String getMail() { return mail; }

	public String getAddress() { return address; }

	public String getCity() { return city; }

	public String getProvince() { return province; }

	public String getCodePostal() { return codePostal; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

	public void setGender(String gender) { this.gender = gender; }

	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

	public void setMail(String mail) { this.mail = mail; }

	public void setAddress(String address) { this.address = address; }

	public void setCity(String city) { this.city = city; }

	public void setProvince(String province) { this.province = province; }

	public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
}

