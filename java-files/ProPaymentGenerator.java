package gym;

import javafx.util.Pair;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Classe qui génère tous les avis de paiement des professionnels
 * Implémente l'interface Action
 */
public class ProPaymentGenerator implements Action {

	@Override
	public String getTitle() {
		return "Générer les avis de paiement des professionnels";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Générer les avis de paiement des professionnels -");
		System.out.println();

		System.out.println("Où doit-on sauvegarder les fichiers ?");
		String path = InputReader.askDirectoryLoop(in);

		for(Pro pro : DataCenter.database.getPros())
			(new ProPayment(pro.getName(), getPaymentInfos(pro))).save(path);
	}

	/**
	 * Récupere les infos de l'avis de paiement d'un professionnel
	 *
	 * @param pro le pro
	 * @return Infos avis de paiement
	 */
	public List<String> getPaymentInfos(Pro pro) {
		Date today = new Date();

		Date sevenDayBefore = new Date();
		sevenDayBefore.setTime(sevenDayBefore.getTime() - 7 * 1000 * 60 * 60 * 24);

		ArrayList<String> infos = new ArrayList<>();

		infos.add("Avis de paiement");

		infos.add("");

		infos.add("Nom = " + pro.getName());

		String proId = pro.getId();

		infos.add("Numéro = " + proId);

		infos.add("Adresse = " + pro.getAddress());

		infos.add("Ville = " + pro.getCity());

		infos.add("Province = " + pro.getProvince());

		infos.add("Code postal = " + pro.getCodePostal());

		infos.add("");

		DateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat format2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Double total = 0.0;

		for(Pair<Service, Session> entry : DataCenter.database.getServiceSessionsFromProID(proId)) {
			Service service = entry.getKey();
			Session session = entry.getValue();
			Date serviceDate = new Date();

			//Si la session n'était pas active cette semaine
			if(session.getStartOfService().compareTo(today) > 0 || session.getEndOfService().compareTo(sevenDayBefore) < 0) {
				continue;
			}

			infos.add("Date du service = " + format1.format(serviceDate));

			infos.add("Date de création = " + format2.format(session.getCreationDate()));

			infos.add("Membres qui ont participé :");

			for(Registration reg : DataCenter.database.getRegistrations()) {
				if(reg.getServiceId().equals(session.getServiceId()) && reg.getSessionTime() == session.getSessionTime()) {

					infos.add("    Nom du membre = " + DataCenter.database.getMemberFromId(reg.getMemberId()).getName());

					infos.add("    Numéro du membre = " + reg.getMemberId());

				}
			}

			int numeroSession = DataCenter.database.getServiceToSessions().get(service).indexOf(session);
			String sessionCode = service.getId().substring(0, 3) + (new DecimalFormat("00")).format(numeroSession).substring(0, 2) + session.getProId().substring(7, 9);

			infos.add("Code de la séance = " + sessionCode);
			infos.add("Code du service = " + service.getId());

			infos.add("Paiement pour ce service = " + session.getProFees() + "$");

			infos.add("");

			total += session.getProFees();
		}

		infos.add("Total = " + total + "$");

		return infos;
	}
}