package gym;

import java.util.Date;

/**
 * Classe pour stocker les informations d'un professionnel.
 * Hérite de la classe abstraite UserInfo.
 */
public class Pro extends UserInfo {

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
	public Pro(String firstName, String lastName, Date dateOfBirth, String gender, String phoneNumber, String mail, String address, String city, String province, String codePostal) {
		super(firstName, lastName, dateOfBirth, gender, phoneNumber, mail, address, city, province, codePostal);

	}

}
