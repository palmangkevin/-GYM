package gym;

import java.util.Date;

/**
 * Classe pour stocker les informations d'un membre.
 * Hérite de la classe abstraite UserInfo.
 */
public class Member extends UserInfo {

    private boolean isEnabled;

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
    public Member(String firstName, String lastName, Date dateOfBirth, String gender, String phoneNumber, String mail, String address, String city, String province, String codePostal) {
        super(firstName, lastName, dateOfBirth, gender, phoneNumber, mail, address, city, province, codePostal);

        this.isEnabled = true;
    }

    /**
     * Getter du statut du membre.
     * @return un boolean représentant le statut du membre
     */
    public boolean isEnabled() { return isEnabled; }

    /**
     * Setter pour activer un membre
     */
    public void enable() { isEnabled = true; }

    /**
     * Setter pour désactiver un membre
     */
    public void disable() { isEnabled = false; }

}
