package gym;

import javafx.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe pour supprimer une séance de service. L'utilisateur entre le numéro du professionel qui donne la séance.
 * Les séances s'affichent puis un choix est fait pour supprimer une des séances. La séance est alors retirée de la
 * base de données.
 * Implémente l'interface Action
 */
public class SessionRemove implements Action {

	@Override
	public String getTitle() {
		return "Supprimer une séance de service";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Supprimer une séance de service -");
		System.out.println();

		System.out.println("Entrez le numéro du professionel qui donne la séance");
		String proId = InputReader.askExistingProIDLoop(in);

		//Liste des (Service, Session) associées au professionel
		ArrayList<Pair<Service, Session>> available = DataCenter.database.getServiceSessionsFromProID(proId);

		//Si aucune session correspond au proId
		if(available.size() == 0) {
			System.out.println("Aucune séance à afficher");
			return;
		}

		System.out.println("Séances associées au professionel :");

		//Afficher les séances
		DateFormat writeFormat = new SimpleDateFormat("HH:mm");

		for(int i = 0; i < available.size(); i++) {
			String iStr = Integer.toString(i + 1);
			String serviceTitle = available.get(i).getKey().getTitle();

			String sessionHour = writeFormat.format(available.get(i).getValue().getSessionTime());

			System.out.println(iStr + " - " + serviceTitle + " à " + sessionHour);
		}

		System.out.println();
		System.out.println("Choisissez une séance à supprimer en tapant son numéro");
		System.out.println("Tapez 0 pour retourner au menu");

		//Attendre selection
		int select = InputReader.askIntLoop(in, available.size() + 1);

		//Retourner au menu
		if(select == 0) return;

		Session sessionToRemove = available.get(select - 1).getValue();

		Service service = available.get(select - 1).getKey();

		//Virer les registrations
		DataCenter.database.removeRegistrationsFromSession(sessionToRemove);

		DataCenter.database.getServiceToSessions().get(service).remove(sessionToRemove);

		System.out.println("Séance supprimée !");
	}
}
