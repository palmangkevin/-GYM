package gym;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe pour rajouter un service. Elle demande tous les informations nécessaires pour créer un service et l'ajouter
 * dans la base de données
 * Implémente l'interface Action
 */
public class ServiceAdd implements Action {

	@Override
	public String getTitle() {
		return "Ajout d'un service";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Ajout d'un service -");
		System.out.println();

		System.out.println("Entrez son titre");
		String title = InputReader.askStringUnderLimitLoop(in, 20);

		Service service = new Service(title);
		DataCenter.database.getServiceToSessions().put(service, new ArrayList());

		System.out.println("Service ajouté!");
		System.out.println("Voici le code du service " + service.getId());
	}
}
