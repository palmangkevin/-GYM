package gym;

/**
 * Classe pour stocker les informations d'un élément de rapport.
 * Un élément de rapport contient une somme et un compteur de service.
 */
public class ReportElement {

	private Double amount;
	private int servicesCount;

	/**
	 * Constructeur de classe
	 */
	public ReportElement() {
		this.amount = 0.0;
		this.servicesCount = 0;
	}

	/**
	 * @param amount montant ajouté à amount
	 */
	public void addAmount(Double amount) { this.amount += amount; }

	/**
	 * @return amount
	 */
	public Double getAmount() { return amount; }

	/**
	 * Méthode qui incrément le compteur de service.
	 */
	public void addService() { this.servicesCount++; }

	/**
	 * @return le compteur de service
	 */
	public int getServicesCount() { return servicesCount; }
}
