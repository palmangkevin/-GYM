package gym;

import javafx.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import gym.AppAPI.*;


/**
 * Cette class permet de simuler la confirmation d'un membre par un professionnel depuis l'App
 *
 * Implémente l'interface Action
 */
public class AppConfirmMemberAttendanceToSession implements Action {

	public String getTitle() {
		return "Confirmer la présence d'un membre à une séance sur l'App";
	}

	public void launch() {

		Scanner in = new Scanner(System.in);

		System.out.println("- Confirmer présence à une séance sur l'App -");
		System.out.println();

		System.out.println("Entrez l'email associé au compte Facebook du professionnel");
		String proEmail = in.nextLine();

		//Ask App API

		System.out.println();
		System.out.println("Formulation d'une requete à AppAPI ...");

		Map<String, Pair<Service, Session>> answer = AppAPI.getProSessions(proEmail);

		System.out.println("Réponse obtenue.");
		System.out.println();

		//Use App API answer

		if(answer == null) {
			System.out.println("Email invalide");
			return;
		}

		if(answer.size() == 0) {
			System.out.println("Aucune séance pour ce professionnel aujourd'hui.");
			return;
		}

		System.out.println("Voici la liste des codes de séance disponible aujourd'hui pour ce profesionnel :");

		for(Map.Entry<String, Pair<Service, Session>> entry : answer.entrySet())
			System.out.println(entry.getKey());

		System.out.println("Veuillez taper un code de séance, ou CANCEL pour annuler.");

		String code;

		while(true) {
			code = in.nextLine();

			if(code.toLowerCase().equals("cancel"))
				return;

			if(answer.containsKey(code))
				break;

			System.out.println("Erreur");
			System.out.println("Code séance invalide");
		}

		Pair<Service, Session> entry = answer.get(code);
		Service service = entry.getKey();
		Session session = entry.getValue();

		System.out.println("Informations concernant la séance selectionnée :");

		System.out.println("Nom de service : " + service.getTitle());
		System.out.println("Code de service : " + session.getServiceId());
		DateFormat writeFormat = new SimpleDateFormat("HH:mm");
		System.out.println("Heure : " + writeFormat.format(session.getSessionTime()));
		System.out.println();

		System.out.println("Entrez le numéro du membre à confirmer");
		String memberId = InputReader.askExistingMemberIDLoop(in);

		//Ask App API

		System.out.println();
		System.out.println("Formulation d'une requete à AppAPI ...");

		ConfirmMemberAttendanceValue answer2 = AppAPI.confirmMemberAttendanceToSession(session, memberId);

		System.out.println("Réponse obtenue.");
		System.out.println();

		//Use App API answer

		if(answer2 == null) {
			System.out.println("Parametres incorrects.");

			return;
		}

		if(answer2 == ConfirmMemberAttendanceValue.MemberNotRegistratedToSession) {
			System.out.println("Ce membre n'est pas inscrit à cette seance");

			return;
		}

		System.out.println("Validé !");
		System.out.println("Confirmation faite !");
	}
}
