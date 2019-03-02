package gym;

import java.util.List;
import java.util.Scanner;

/**
 * Cette class permet de simuler la récuperation de l'avis de paiement hebdomadaire d'un professionnel depuis l'App
 *
 * Implémente l'interface Action
 */
public class AppProPaymentNotification implements Action {

	@Override
	public String getTitle() {
		return "Récuperer l'avis de paiement d'un professionnel sur l'App";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Récuperer l'avis de paiement d'un professionnel sur l'App -");
		System.out.println();

		System.out.println("Entrez l'email associé au compte Facebook du professionnel");
		String email = in.nextLine();

		//Ask App API

		System.out.println();
		System.out.println("Formulation d'une requete à AppAPI ...");

		List<String> answer = AppAPI.getProPayment(email);

		System.out.println("Réponse obtenue.");
		System.out.println();

		//Use App API answer

		if(answer == null) {
			System.out.println("Email invalide");
			return;
		}

		for(String info : answer)
			System.out.println(info);
	}
}