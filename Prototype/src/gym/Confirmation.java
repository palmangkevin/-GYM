package gym;

import java.util.Date;

/**
 * Classe pour stocker les informations d'une confirmation de participation à une séance
 */
public class Confirmation {

    private Date date;
    private String proId;
    private String userId;
    private String serviceId;
    private String comment;

    /**
     * Constructeur de classe
     *
     * @param proId ID du professionnel
     * @param userId ID du membre
     * @param serviceId ID du service
     * @param comment commentaire facultatif (100 char max) (on peut accepter null comm valeur)
     */
    public Confirmation(String proId, String userId, String serviceId, String comment) {
        this.date = new Date();
        this.proId = proId;
        this.userId = userId;
        this.serviceId = serviceId;
        this.comment = comment;
    }

    /**
     * @return ID d'un membre
     */
    public String getUserId() { return userId; }
}
