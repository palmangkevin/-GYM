package gym;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

/**
 * Classe qui représente le centre de données. Les actions, qui peuvent être lancées, sont organisées en
 * sous-catégories et ces sous-catégories sont organisées dans une liste d'actions qui, eux aussi, peuvent être lancées.
 */
public class DataCenter {

	List<Action> actions;

	static public Database database;

	/**
	 * Constructeur de classe où les actions sont initialisées dans une liste et où le menu principal est implanté.
	 */
	public DataCenter() {

		//Initialisation Database
		database = new Database();

		//Initialisation des UIs
		actions = Arrays.asList(
				new CheckUser(),
				new SubCategory("Gérer les membres", Arrays.asList(
						new MemberAdd(),
						new MemberUpdate(),
						new MemberRemove(),
						new ChangeUserState()
				)),
				new SubCategory("Gérer les professionnels", Arrays.asList(
						new ProAdd(),
						new ProUpdate(),
						new ProRemove()
				)),
				new SubCategory("Gérer les services", Arrays.asList(
						new EnumerateServices(),
						new ServiceAdd(),
						new ServiceUpdate(),
						new ServiceRemove()
				)),
				new SubCategory("Gérer les séances", Arrays.asList(
						new SessionAdd(),
						new SessionUpdate(),
						new SessionRemove()
				)),
				new RegMemberToSession(),
				new EnumerateMembersRegToProSession(),
				new ConfirmMemberAttendanceToSession(),
				new SubCategory("Procédure comptable", Arrays.asList(
						new TEFGenerator(),
						new ReportGenerator(),
						new MemberInvoiceGenerator(),
						new ProPaymentGenerator()
				)),
				new SubCategory("Simuler les interactions avec l'application", Arrays.asList(
						new AppCheckUser(),
						new AppRegMemberToSession(),
						new AppEnumerateMembersRegToProSession(),
						new AppConfirmMemberAttendanceToSession(),
						new AppMemberInvoice(),
						new AppProPaymentNotification()
				))
		);

		Scanner in = new Scanner(System.in);

		boolean shouldQuit = false;

		while(!shouldQuit) {
			System.out.flush();
			System.out.println("###############################################");
			System.out.println("#                                             #");
			System.out.println("#         Centre de données de #GYM           #");
			System.out.println("#                                             #");
			System.out.println("###############################################");
			System.out.println();
			System.out.println();
			System.out.println("Choisissez une action en tapant son numéro");
			System.out.println();

			//Afficher les choix
			for(int i = 0; i < actions.size(); i++)
				System.out.println(Integer.toString(i) + " - " + actions.get(i).getTitle());

			//Selection
			int select = InputReader.askIntLoop(in, actions.size());

			//On ouvre l'Action associée
			actions.get(select).launch();

			System.out.println("Tapez la touche Enter pour revenir au menu ...");
			in.nextLine();
		}
	}
}
