package gym;

import java.util.Scanner;

/**
 * Classe pour supprimer un service. L'utilisateur entre le code du service à supprimer puis complète en enlevant le
 * service et les inscriptions associées de la base de données
 * Implémente l'interface Action
 */
public class ServiceRemove implements Action {

	@Override
	public String getTitle() {
		return "Supprimer un service";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Supprimer un service -");
		System.out.println();

		System.out.println("Entrez le code du service à supprimer");
		Service service = InputReader.askExistingServiceLoop(in);

		//Virer les registrations
		DataCenter.database.removeRegistrationsFromServiceId(service.getId());

		DataCenter.database.getServiceToSessions().remove(service);

		System.out.println("Le service a été supprimé!");
	}
}
