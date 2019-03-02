package gym;

import java.util.*;

/**
 * Classe qui génère un fichier TEF contenant les informations par rapport aux professionnels et le sauvegarde sur
 * le disque
 * Implémente l'interface Action
 */
public class TEFGenerator implements Action {

	@Override
	public String getTitle() {
		return "Générer un fichier TEF";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Génération d'un fichier TEF -");
		System.out.println();

		HashMap<Pro, Double> amounts = getAmounts();

		if(amounts.size() == 0) {
			System.out.println("Aucun professionnel à payer cette semaine.");
			return;
		}

		System.out.println("Où doit-on sauvegarder le fichier TEF ?");
		String path = InputReader.askDirectoryLoop(in);

		TEF tef = new TEF(amounts);
		tef.save(path);
	}

	public HashMap<Pro, Double> getAmounts() {
		HashMap<Pro, Double> amounts = new HashMap<>();

		Date today = new Date();

		Date sevenDayBefore = new Date();
		sevenDayBefore.setTime(sevenDayBefore.getTime() - 7 * 1000 * 60 * 60 * 24);

		for (Map.Entry<Service, ArrayList<Session>> entry : DataCenter.database.getServiceToSessions().entrySet()) {
			for (Session session : entry.getValue()) {
				//Si la session était active cette semaine
				if (session.getStartOfService().compareTo(today) < 0 && session.getEndOfService().compareTo(sevenDayBefore) > 0) {
					Pro pro = DataCenter.database.getProFromId(session.getProId());

					//Pour ajouter le pro si c'est la premiere fois
					if(!amounts.containsKey(pro))
						amounts.put(pro, 0.0);

					amounts.put(pro, amounts.get(pro) + session.getProFees());
				}
			}
		}

		return amounts;
	}
}
