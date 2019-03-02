package gym;

import java.util.Scanner;
import javafx.util.Pair;
import gym.AppAPI.*;

/**
 * Cette class permet de simuler la vérification d'un membre depuis l'App
 * (Lors de sa connexion sur l'App)
 *
 * Implémente l'interface Action
 */
public class AppCheckUser implements Action {

	public String getTitle() {
		return "Vérifier un membre sur l'App";
	}

	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Vérification d'un membre sur l'App -");
		System.out.println();

		System.out.println("Entrez l'email associé au compte Facebook du membre");
		String email = in.nextLine();

		//Ask App API

		System.out.println();
		System.out.println("Formulation d'une requete à AppAPI ...");

		Pair<CheckUserValue, Member> answer = AppAPI.checkUser(email);

		System.out.println("Réponse obtenue.");
		System.out.println();

		//Use App API answer

		if(answer == null) {
			System.out.println("Email invalide");
			return;
		}

		if(answer.getKey() == CheckUserValue.SuspendedMember)
			System.out.println("Membre suspendu");

		if(answer.getKey() == CheckUserValue.ValidMember) {
			System.out.println("Membre valide");
			System.out.println("Nom du membre = " + answer.getValue().getName());
			System.out.println("Numéro de membre = " + answer.getValue().getId());
		}
	}
}