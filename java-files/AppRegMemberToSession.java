package gym;

import javafx.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import gym.AppAPI.*;

/**
 * Cette class permet de simuler l'inscription d'un membre à une séance depuis l'App
 *
 * Implémente l'interface Action
 */
public class AppRegMemberToSession implements Action {

	@Override
	public String getTitle() {
		return "Inscrire membre à séance sur l'App";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Inscrire membre à séance sur l'App -");
		System.out.println();

		//Ask App API

		System.out.println();
		System.out.println("Formulation d'une requete à AppAPI ...");

		List<Pair<Service, Session>> available = AppAPI.getAvailableSessions();

		System.out.println("Réponse obtenue.");
		System.out.println();

		//Use App API answer

		if(available.size() == 0) {
			System.out.println("Aucune session disponible aujourd'hui");
			System.out.println("Tapez la touche Enter pour revenir au menu ...");

			in.nextLine();

			return;
		}

		System.out.println("Sessions disponibles aujourd'hui :");
		System.out.println();

		DateFormat writeFormat = new SimpleDateFormat( "HH:mm");

		//Afficher les choix
		for(int i = 0; i < available.size(); i++) {
			String iStr = Integer.toString(i + 1);
			String serviceTitle = available.get(i).getKey().getTitle();

			String sessionHour = writeFormat.format(available.get(i).getValue().getSessionTime());

			System.out.println(iStr + " - " + serviceTitle + " à " + sessionHour);
		}

		System.out.println();
		System.out.println("Choisissez une séance en tapant son numéro");
		System.out.println("Tapez 0 pour retourner au menu");

		//Attendre selection
		int select = InputReader.askIntLoop(in, available.size() + 1);

		//Retourner au menu
		if(select == 0) return;

		System.out.println("Entrez l'ID du membre à inscrire");
		String memberId = InputReader.askExistingMemberIDLoop(in);

		System.out.println("Entrez un commentaire (100 caractères) (facultatif)");
		String comment = InputReader.askStringUnderLimitLoop(in, 100);
		comment = comment.equals("") ? null : comment;

		//Ask App API

		System.out.println();
		System.out.println("Formulation d'une requete à AppAPI ...");

		Pair<RegMemberToSessionValue, Double> answer = AppAPI.regMemberToSession(select - 1, memberId, comment);

		System.out.println("Réponse obtenue.");
		System.out.println();

		//Use App API answer

		if(answer.getKey() == RegMemberToSessionValue.SuspendedMember) {
			System.out.println("Membre suspendu (frais impayés), il ne peut pas s'inscrire");
			return;
		}

		System.out.println("Membre inscrit !");
		System.out.println("Montant à payer pour cette séance = " + answer.getValue());
	}
}
