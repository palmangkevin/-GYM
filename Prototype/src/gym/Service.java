package gym;

import java.text.DecimalFormat;

/**
 * Classe pour stocker les informations d'un service qui se résume à un ID et un titre.
 */
public class Service {

    private String id;
    private String title;

    /**
     * Constructeur de classe
     *
     * @param title titre du service
     */
    public Service(String title) {
        this.title = title;
        this.id = generateId();
    }

    /**
     * Méthode pour générer un ID
     * @return un ID de 7 chiffres qui est le hashcode du titre.
     */
    private String generateId() {
        return (new DecimalFormat("0000000")).format(Math.abs(title.hashCode())).substring(0, 7);
    }

    public String getId() { return id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}
