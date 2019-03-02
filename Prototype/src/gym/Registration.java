package gym;

import java.util.Date;

/**
 * Classe pour stocker les informations d'une inscription
 */
public class Registration {

	private Date currentDate;
	private Date serviceDate;
	private String proId;
	private String memberId;
	private String serviceId;
	private String comment;
	private Date sessionTime;

	/**
	 * Constructeur de clase
	 *
	 * @param serviceDate Date du service
	 * @param proId ID du professionnel
	 * @param memberId ID du membre
	 * @param serviceId ID du service
	 * @param comment commentaire facultatif (100 char max) (on peut accepter null comm valeur)
	 * @param sessionTime L'heure de la session
	 */
	public Registration(Date serviceDate, String proId, String memberId, String serviceId, String comment, Date sessionTime) {
		this.currentDate = new Date();
		this.serviceDate = serviceDate;
		this.proId = proId;
		this.memberId = memberId;
		this.serviceId = serviceId;
		this.comment = comment;
		this.sessionTime = sessionTime;
	}

	public String getServiceId() { return serviceId; }

	public String getMemberId() { return memberId; }

	public String getProId() { return proId; }

	public Date getSessionTime() { return sessionTime; }
}
