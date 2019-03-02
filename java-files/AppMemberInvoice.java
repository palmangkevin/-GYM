package gym;

import java.util.List;
import java.util.Scanner;

/**
 * Cette class permet de simuler la récuperation de la facture hebdomadaire d'un membre depuis l'App
 *
 * Implémente l'interface Action
 */
public class AppMemberInvoice implements Action {

	@Override
	public String getTitle() {
		return "Récuperer la facture d'un membre sur l'App";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Récuperer la facture hebdomadaire d'un membre sur l'App -");
		System.out.println();

		System.out.println("Entrez l'email associé au compte Facebook du membre");
		String email = in.nextLine();

		//Ask App API

		System.out.println();
		System.out.println("Formulation d'une requete à AppAPI ...");

		List<String> answer = AppAPI.getMemberInvoice(email);

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
