package gym;

import java.util.Scanner;

/**
 * Classe pour mettre à jour les informations d'un service qui se résume à modifier le titre du service.
 * Implémente l'interface Action
 */
public class ServiceUpdate implements Action {

	@Override
	public String getTitle() {
		return "Mise à jour d'un service";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Mise à jour d'un service -");
		System.out.println();

		System.out.println("Entrez le code du service à modifier");
		Service service = InputReader.askExistingServiceLoop(in);

		System.out.println("Entrez le nouveau titre du service");
		String title = InputReader.askStringUnderLimitLoop(in, 20);

		service.setTitle(title);

		System.out.println("Le titre du service à été modifié!");
	}
}
