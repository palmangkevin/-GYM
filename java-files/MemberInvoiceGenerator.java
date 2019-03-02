package gym;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe qui génère toutes les factures des membres
 * Implémente l'interface Action
 */
public class MemberInvoiceGenerator implements Action {

	@Override
	public String getTitle() {
		return "Générer les factures des membres";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Générer les factures des membres -");
		System.out.println();

		System.out.println("Où doit-on sauvegarder les fichiers ?");
		String path = InputReader.askDirectoryLoop(in);

		for(Member member : DataCenter.database.getMembers())
			(new MemberInvoice(member.getName(), getInvoiceInfos(member))).save(path);
	}

	/**
	 * Récupere les infos de la facture hebdomadaire d'un membre
	 *
	 * @param member le membre
	 * @return Infos membre et séances
	 */
	public List<String> getInvoiceInfos(Member member) {
		Date today = new Date();

		Date sevenDayBefore = new Date();
		sevenDayBefore.setTime(sevenDayBefore.getTime() - 7 * 1000 * 60 * 60 * 24);

		ArrayList<String> infos = new ArrayList<>();

		infos.add("Facture Membre");

		infos.add("");

		infos.add("Nom = " + member.getName());

		String memberId = member.getId();

		infos.add("Numero = " + memberId);

		infos.add("Adresse = " + member.getAddress());

		infos.add("Ville = " + member.getCity());

		infos.add("Province = " + member.getProvince());

		infos.add("Code postal = " + member.getCodePostal());

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		infos.add("");

		infos.add("Services fournis :");

		infos.add("");

		for(Registration reg : DataCenter.database.getRegistrations()) {
			if(reg.getMemberId().equals(memberId)) {
				Session session = null;
				Date serviceDate = new Date();

				for (Map.Entry<Service, ArrayList<Session>> entry : DataCenter.database.getServiceToSessions().entrySet())
					for (Session sess : entry.getValue())
						if(sess.getSessionTime() == reg.getSessionTime() && reg.getServiceId().equals(sess.getServiceId()))
							session = sess;

				//Si la session n'était pas active cette semaine
				if(session.getStartOfService().compareTo(today) > 0 || session.getEndOfService().compareTo(sevenDayBefore) < 0) {
					continue;
				}

				infos.add("Date du service = " + format.format(serviceDate));

				infos.add("Nom du professionnel = " + DataCenter.database.getProFromId(reg.getProId()).getName());

				infos.add("Nom du service = " + DataCenter.database.getServiceFromId(reg.getServiceId()).getTitle());

				infos.add("");
			}
		}

		return infos;
	}
}