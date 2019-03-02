package gym;

import java.util.Date;
import java.util.Scanner;

/**
 * Classe pour ajouter un membre. Elle demande tous les informations nécessaires pour créer un membre et l'ajoute
 * dans la base de données.
 * Implémente l'interface Action.
 */
public class MemberAdd implements Action {
	@Override
	public String getTitle() {
		return "Ajout d'un membre";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Ajout d'un membre -");
		System.out.println();

		System.out.println("Entrez son email (compte Facebook valide)");
		String mail = in.nextLine();

		System.out.println("Est-ce que le membre a déjà payé les frais ? ('Oui' ou 'Non')");
		boolean paid = InputReader.askYesNoLoop(in);

		System.out.println("Entrez son prénom");
		String firstName = in.nextLine();

		System.out.println("Entrez son nom");
		String lastName = InputReader.askStringUnderLimitLoop(in, 25);

		System.out.println("Entrez sa date de naissance (dd-MM-yyyy)");
		Date date = InputReader.askDateLoop(in, "dd-MM-yyyy");

		System.out.println("Entrez son genre (M, F ou AUTRE)");
		String gender = InputReader.askGenderLoop(in);

		System.out.println("Entrez son numéro de téléphone");
		String phoneNumber = in.nextLine();

		System.out.println("Entrez son adresse");
		String address = InputReader.askStringUnderLimitLoop(in, 25);

		System.out.println("Entrez la ville");
		String city = InputReader.askStringUnderLimitLoop(in, 14);

		System.out.println("Entrez la province");
		String province = InputReader.askStringUnderLimitLoop(in, 2);

		System.out.println("Entrez son code postal");
		String postalCode = InputReader.askStringUnderLimitLoop(in, 6);

		Member member = new Member(firstName, lastName, date, gender, phoneNumber, mail, address, city, province, postalCode);

		if(!paid) {
			member.disable();
			System.out.println();
			System.out.println("Quand ce membre aura payé les frais :");
			System.out.println("Merci de changer son statut dans 'Changer le statut d'un membre'");
			System.out.println();
		}

		DataCenter.database.getMembers().add(member);

		System.out.println("Membre ajouté");
		System.out.println("Voici son numéro " + member.getId());
	}
}