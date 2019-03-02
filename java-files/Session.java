package gym;

import java.util.Date;
import java.util.ArrayList;

/**
 * Classe pour stocker les informations d'une séance.
 */
public class Session {

    private int maxCapacity;
    private Date currentDate;
    private Date startOfService;
    private Date endOfService;
    private Date time;
    private ArrayList<Day> recurrence;
    private String proId;
    private String serviceId;
	private Double proFees;
	private Double memberFees;
    private String comment;
    private int participantsCount;

    /**
     * Constructeur de classe qui contient la date d'aujourd'hui par défaut.
     *
     * @param startOfService Date de début du service
     * @param endOfService Date de fin du service
     * @param time L'heure du service
     * @param recurrence La récurrence hebdomadaire, une liste de Day
     * @param maxCapacity La capacité maximale
     * @param proId ID du professionnel
     * @param serviceId ID du service
     * @param proFees Frais à payer au professionnel chaque semaine
     * @param memberFees Frais que chaque membre doit payer à chaque séance
     * @param comment Commentaire facultatif (100 char max) (on peut accepter null comm valeur)
     */
    public Session(Date startOfService, Date endOfService, Date time, ArrayList<Day> recurrence, int maxCapacity, String proId, String serviceId, Double proFees, Double memberFees, String comment) {
	    this.currentDate = new Date();
	    this.startOfService = startOfService;
	    this.endOfService = endOfService;
	    this.time = time;
	    this.recurrence = recurrence;
	    this.maxCapacity = maxCapacity;
	    this.proId = proId;
	    this.serviceId = serviceId;
	    this.proFees = proFees;
	    this.memberFees = memberFees;
	    this.comment = comment;
	    this.participantsCount = 0;
    }

    /**
     * Énumération des jours de la semaine qui est utile pour la récurrence hebdomadaire d'une séance.
     */
    public enum Day {
        LUNDI, MARDI, MERCREDI,
        JEUDI, VENDREDI, SAMEDI, DIMANCHE
    }

    /**
     * Méthode pour incrémente le compteur de participants
     */
    public void addParticipant() { this.participantsCount++; }

    public Date getCreationDate() { return currentDate; }

    public String getServiceId() { return this.serviceId; }

	public Double getProFees() { return this.proFees; }

	public Double getMemberFees() { return this.memberFees; }

    public String getProId() { return this.proId; }

    public Date getSessionTime() { return time; }

    /**
     * @return Date de début de la séance
     */
    public Date getStartOfService() { return startOfService; }

    /**
     * @return Date de fin de la séance
     */
    public Date getEndOfService() { return endOfService; }

    /**
     * Getter de la liste des jours où la séance se donne
     @return ArrayList des jours de la r
     */
    public ArrayList<Day> getRecurrence() { return this.recurrence; }

    public int getParticipantsCount() { return participantsCount; }

    /**
     * @return un booléen indiquand si la séance est complète
     */
    public boolean isFull() { return this.participantsCount >= this.maxCapacity; }

    public void setStartOfService(Date startOfService) { this.startOfService = startOfService; }

    public void setEndOfService(Date endOfService) { this.endOfService = endOfService; }

    public void setSessionTime(Date time) { this.time = time; }

    public void setRecurrence(ArrayList<Day> recurrence) { this.recurrence = recurrence; }

	public void setMemberFees(Double memberFees) { this.memberFees = memberFees; }

	public void setProFees(Double proFees) { this.proFees = proFees; }

    public void setComment(String comment) { this.comment = comment; }
}
