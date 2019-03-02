package gym;

import java.util.Scanner;

/**
 * Classe pour supprimer un membre. Elle demande le numéro du membre à suprimmer puis le retire avec ses inscriptions
 * de la base de données
 * Implémente l'interface Action.
 */
public class MemberRemove implements Action {
	@Override
	public String getTitle() {
		return "Suppression d'un membre";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Suppression d'un membre -");
		System.out.println();

		System.out.println("Entrez le numéro du membre à supprimer (cette action est irréversible)");
		Member membre = InputReader.askExistingMemberLoop(in);

		//Virer ses registrations
		DataCenter.database.removeRegistrationsFromMemberId(membre.getId());

		DataCenter.database.getMembers().remove(membre);

		System.out.println("Suppression effectuée");
	}
}