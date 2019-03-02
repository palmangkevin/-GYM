package gym;

import java.util.*;

/**
 * Classe pour ajouter une séance de service. Elle demande tous les informations nécessaires pour créer une séance et
 * l'ajouter dans la base de données.
 * Implémente l'interface Action
 */
public class SessionAdd implements Action {

	@Override
	public String getTitle() {
		return "Ajout d'une séance de service";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Ajout d'une séance de service -");
		System.out.println();

		System.out.println("Entrez la date de début de la séance (dd-MM-yyyy)");
		Date startOfService = InputReader.askDateLoop(in, "dd-MM-yyyy");

		System.out.println("Entrez la date de fin de la séance (dd-MM-yyyy)");
		Date endOfService = InputReader.askDateLoop(in, "dd-MM-yyyy");

		System.out.println("Entrez l'heure de la séance (HH:mm)");
		Date time = InputReader.askDateLoop(in, "HH:mm");

		System.out.println("Entrez la récurrence hebdomaire");
		System.out.println("Un jour par ligne (le nom du jour en français)");
		System.out.println("'STOP' quand la liste est terminée");
		System.out.println("Exemple :");
		System.out.println("   LUNDI");
		System.out.println("   JEUDI");
		System.out.println("   STOP");
		System.out.println("C'est à vous :");
		ArrayList<Session.Day> days = InputReader.askDaysLoop(in);

		System.out.println("Entrez la capacité maximale de la séance (pas plus que 30 inscriptions)");
		int maxCapacity = InputReader.askIntLoop(in, 30 + 1);

		System.out.println("Entrez l'ID du professionnel");
		String proId = InputReader.askExistingProIDLoop(in);

		System.out.println("Entrez le code de service");
		Service service = InputReader.askExistingServiceLoop(in);

		System.out.println("Entrez les frais à payer au professionnel par séance (jusqu'à 100.00$)");
		Double proFees = InputReader.askDoubleLoop(in, 100);

		System.out.println("Entrez les frais que paie un membre à chaque séance");
		Double memberFees = InputReader.askDoubleLoop(in);

		System.out.println("Entrez un commentaire (100 caractères) (facultatif)");
		String comment = InputReader.askStringUnderLimitLoop(in, 100);
		comment = comment.equals("") ? null : comment;

		Session session = new Session(startOfService, endOfService, time, days, maxCapacity, proId, service.getId(), proFees, memberFees, comment);

		try {
			DataCenter.database.getServiceToSessions().get(service).add(session);

			System.out.println("Séance ajoutée !");
		} catch (Exception e) { }
	}
}
