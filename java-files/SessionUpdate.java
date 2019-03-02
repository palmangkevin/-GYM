package gym;

import javafx.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe pour mettre à jour les informations d'une séance de service.
 * Elle affiche les séances disponibles associées au professionnel pour que l'utilisateur puisse faire un choix
 * Ensuite l'utilisateur choisit un champ à modifier et le modifie.
 * Implémente l'interface Action.
 */
public class SessionUpdate implements Action {

	@Override
	public String getTitle() {
		return "Mise à jour d'une séance de service";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Mise à jour d'une séance de service -");
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
		System.out.println("Choisissez une séance à mettre à jour en tapant son numéro");
		System.out.println("Tapez 0 pour retourner au menu");

		//Attendre selection
		int select = InputReader.askIntLoop(in, available.size() + 1);

		//Retourner au menu
		if(select == 0) return;

		Session sessionToUpdate = available.get(select - 1).getValue();

		System.out.println("Choisissez la donnée que vous souhaitez modifier");
		System.out.println();

		System.out.println("0 - Date de début de la séance");
		System.out.println("1 - Date de fin de la séance");
		System.out.println("2 - Heure de la séance");
		System.out.println("3 - Récurrence");
		System.out.println("4 - Frais professionnel");
		System.out.println("5 - Frais membre");
		System.out.println("6 - Commentaire");

		// choix de la modification a faire
		int selectProperty = InputReader.askIntLoop(in, 7);

		switch(selectProperty) {
			case 0:
				System.out.println("Entrez la nouvelle date de début de la séance (dd-MM-yyyy)");
				Date startOfService = InputReader.askDateLoop(in, "dd-MM-yyyy");
				sessionToUpdate.setStartOfService(startOfService);
				break;
			case 1:
				System.out.println("Entrez la nouvelle date de fin de la séance (dd-MM-yyyy)");
				Date endOfService = InputReader.askDateLoop(in, "dd-MM-yyyy");
				sessionToUpdate.setEndOfService(endOfService);
				break;
			case 2:
				System.out.println("Entrez la nouvelle heure de la séance (HH:mm)");
				Date time = InputReader.askDateLoop(in, "HH:mm");
				sessionToUpdate.setSessionTime(time);
				break;
			case 3:
				System.out.println("Entrez la nouvelle récurrence hebdomaire");
				System.out.println("Un jour par ligne (le nom du jour en français)");
				System.out.println("'STOP' quand la liste est terminée");
				System.out.println("Ex :");
				System.out.println("LUNDI");
				System.out.println("JEUDI");
				System.out.println("STOP");
				System.out.println("C'est à vous :");
				ArrayList<Session.Day> days = InputReader.askDaysLoop(in);
				sessionToUpdate.setRecurrence(days);
				break;
			case 4:
				System.out.println("Entrez les nouveaux frais à payer au professionnel par séance (jusqu'à 100.00$)");
				Double proFees = InputReader.askDoubleLoop(in, 100);
				sessionToUpdate.setProFees(proFees);
				break;
			case 5:
				System.out.println("Entrez les nouveaux frais à payer par chaque membre à chaque séance");
				Double memberFees = InputReader.askDoubleLoop(in);
				sessionToUpdate.setMemberFees(memberFees);
				break;
			case 6:
				System.out.println("Entrez un nouveau commentaire (100 caractères) (facultatif)");
				String comment = InputReader.askStringUnderLimitLoop(in, 100);
				comment = comment.equals("") ? null : comment;
				sessionToUpdate.setComment(comment);
				break;
		}

		System.out.println("Modification effectuée !");
	}
}
