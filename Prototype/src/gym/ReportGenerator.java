package gym;

import java.util.*;

/**
 * Classe qui génère le rapport hebdomadaire.
 * Implémente l'interface Action.
 */
public class ReportGenerator implements Action {

	@Override
	public String getTitle() {
		return "Générer un rapport de synthèse";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Génération d'un rapport de synthèse -");
		System.out.println();

		HashMap<Pro, ReportElement> reports = getReports();

		if(reports.size() == 0) {
			System.out.println("Aucune séance donnée cette semaine.");
			return;
		}

		//On a toutes les infos qu'il faut
		//On génère le rapport
		Report report = new Report(reports);

		System.out.println("Où doit-on sauvegarder le fichier ?");
		String path = InputReader.askDirectoryLoop(in);

		report.save(path);
	}

	public HashMap<Pro, ReportElement> getReports() {
		HashMap<Pro, ReportElement> reports = new HashMap<>();

		Date today = new Date();

		Date sevenDayBefore = new Date();
		sevenDayBefore.setTime(sevenDayBefore.getTime() - 7 * 1000 * 60 * 60 * 24);

		//Obtenir le nombre de service de la semaine pour chaque pro
		for (Map.Entry<Service, ArrayList<Session>> entry : DataCenter.database.getServiceToSessions().entrySet()) {
			ArrayList<Pro> alreadyAddedPro = new ArrayList<>();

			//Pour chaque session
			for (Session session : entry.getValue()) {
				//Si la session était active cette semaine
				if (session.getStartOfService().compareTo(today) < 0 && session.getEndOfService().compareTo(sevenDayBefore) > 0) {
					Pro pro = DataCenter.database.getProFromId(session.getProId());

					//Pour ajouter le pro si c'est la premiere fois
					if(!reports.containsKey(pro))
						reports.put(pro, new ReportElement());

					ReportElement el = reports.get(pro);

					//Pour ne compter le service qu'une seule fois
					if(!alreadyAddedPro.contains(pro)) {
						alreadyAddedPro.add(pro);

						el.addService();
					}

					el.addAmount(session.getProFees());
				}
			}
		}

		return reports;
	}
}
