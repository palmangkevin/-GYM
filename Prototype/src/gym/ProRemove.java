package gym;

import java.util.Scanner;

/**
 * Classe pour supprimer un professionnel. Elle demande le numéro du professionnel à supprimer puis le retire avec ses
 * inscriptions de la base de données.
 * Implémente l'interface Action.
 */
public class ProRemove implements Action {
	@Override
	public String getTitle() {
		return "Suppression d'un professionnel";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Suppression d'un professionnel -");
		System.out.println();

		System.out.println("Entrez le numéro du professionnel à supprimer (cette action est irréversible)");
		Member professionnel = InputReader.askExistingMemberLoop(in);

		//Virer ses séances
		DataCenter.database.removeSeancesFromProId(professionnel.getId());

		//Virer ses registrations
		DataCenter.database.removeRegistrationsFromProId(professionnel.getId());

		DataCenter.database.getMembers().remove(professionnel);

		System.out.println("Suppression effectuée");
	}
}