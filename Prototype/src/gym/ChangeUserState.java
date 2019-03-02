package gym;

import java.util.Scanner;

/**
 * Classe pour changer le statut d'un membre. Elle demande le ID du membre puis offre le choix de le désactiver s'il
 * est actif et de l'activer s'il ne l'est pas.
 * Implémente l'interface Action
 */
public class ChangeUserState implements Action {

	@Override
	public String getTitle() {
		return "Changer le statut d'un membre (Actif / Suspendu)";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Changer le statut d'un membre -");
		System.out.println();

		System.out.println("Entrez son ID (numéro unique)");
		Member membre = InputReader.askExistingMemberLoop(in);

		System.out.println("Le membre est actuellement " + (membre.isEnabled() ? "actif" : "suspendu"));

		System.out.println("Voulez-vous " + (membre.isEnabled() ? "le suspendre" : "l'activer") + "? (Oui/Non)");
		if(!InputReader.askYesNoLoop(in)) return;

		if(membre.isEnabled()) membre.disable(); else membre.enable();

		System.out.println("Le membre a bien été " + (membre.isEnabled() ? "activé" : "suspendu"));
	}
}
