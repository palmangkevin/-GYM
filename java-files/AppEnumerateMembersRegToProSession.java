
package gym;

import java.util.*;

/**
 * Cette class permet de simuler l'énumération des inscriptions aux séances d'un professionnel depuis l'App
 *
 * Implémente l'interface Action
 */
public class AppEnumerateMembersRegToProSession implements Action {

	public String getTitle(){
		return "Consulter les inscriptions aux séances d'un professionnel sur l'App";
	}

	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Consulter inscriptions sur l'App -");
		System.out.println();

		System.out.println("Entrez l'email associé au compte Facebook du professionnel");
		String email = in.nextLine();

		//Ask App API

		System.out.println();
		System.out.println("Formulation d'une requete à AppAPI ...");

		Map<List<String>, List<List<String>>> answer = AppAPI.getMembersRegToProSession(email);

		System.out.println("Réponse obtenue.");
		System.out.println();

		//Use App API answer

		if(answer == null) {
			System.out.println("Email invalide.");

			return;
		}

		if(answer.size() == 0) {
			System.out.println("Aucune séance pour ce professionnel aujourd'hui.");

			return;
		}

		System.out.println("- Services - ");

		for (Map.Entry<List<String>, List<List<String>>> entry : answer.entrySet()) {
			List<String> sessInfos = entry.getKey();

			System.out.println("_________________");
			System.out.println();
			System.out.println("Nom de service: " + sessInfos.get(0));
			System.out.println("Code de service: " + sessInfos.get(1));
			System.out.println();

			System.out.println("Heure de séances : " + sessInfos.get(2));

			System.out.println();

			List<List<String>> regsInfos = entry.getValue();

			if(regsInfos.size() > 0) {
				for(List<String> regInfos: regsInfos) {
					System.out.println("Nom et Prenom: " + regInfos.get(0));
					System.out.println("Code membre: " + regInfos.get(1));
					System.out.println();
				}
			} else {
				System.out.println("Aucun membre inscrit à cette séance.");
			}

			System.out.println("_________________");
		}

		System.out.println();
	}
}
