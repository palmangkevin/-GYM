package gym;

import javafx.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Classe pour enregistrer un membre à une séance
 * Implémente l'interface Action
 */
public class RegMemberToSession implements Action {

	@Override
	public String getTitle() {
		return "Inscrire membre à séance";
	}

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- Inscrire membre à séance -");
		System.out.println();

		//Quel jour sommes-nous
		Date now = new Date();
		LocalDate date = LocalDate.now();
		DayOfWeek dow = date.getDayOfWeek();

		int dayInt = Integer.parseInt(dow.getDisplayName(TextStyle.NARROW_STANDALONE, Locale.ENGLISH));
		Session.Day day = Session.Day.values()[dayInt - 1];

		//Lister les Services & Séances disponibles pour le jour même
		ArrayList<Pair<Service, Session>> available = new ArrayList();

		for (Map.Entry<Service, ArrayList<Session>> entry : DataCenter.database.getServiceToSessions().entrySet())
			//Pour chaque session
			for(Session session: entry.getValue())
				//Si c'est le bon jour ...
				if(session.getRecurrence().contains(day) && !session.isFull() && session.getStartOfService().compareTo(now) < 0 && session.getEndOfService().compareTo(now) > 0)
					available.add(new Pair<Service, Session>(entry.getKey(), session));

		if(available.size() == 0) {
			System.out.println("Aucune session disponible aujourd'hui");
			System.out.println("Tapez la touche Enter pour revenir au menu ...");

			in.nextLine();

			return;
		}

		System.out.println("Sessions disponibles aujourd'hui :");
		System.out.println();

		DateFormat writeFormat = new SimpleDateFormat( "HH:mm");

		//Afficher les choix
		for(int i = 0; i < available.size(); i++) {
			String iStr = Integer.toString(i + 1);
			String serviceTitle = available.get(i).getKey().getTitle();

			String sessionHour = writeFormat.format(available.get(i).getValue().getSessionTime());

			System.out.println(iStr + " - " + serviceTitle + " à " + sessionHour);
		}

		System.out.println();
		System.out.println("Choisissez une séance en tapant son numéro");
		System.out.println("Tapez 0 pour retourner au menu");

		//Attendre selection
		int select = InputReader.askIntLoop(in, available.size() + 1);

		//Retourner au menu
		if(select == 0) return;

		//Si selection alors, on continue
		Session session = null;
		try {
			session = available.get(select - 1).getValue();
		} catch(Exception e) {}

		System.out.println("Entrez l'ID du membre à inscrire");
		Member membre = InputReader.askExistingMemberLoop(in);

		if(!membre.isEnabled()) {
			System.out.println("Membre suspendu (frais impayés), il ne peut pas s'inscrire");
			return;
		}

		System.out.println("Entrez un commentaire (100 caractères) (facultatif)");
		String comment = InputReader.askStringUnderLimitLoop(in, 100);
		comment = comment.equals("") ? null : comment;

		//Service date
		DateFormat hourFormat = new SimpleDateFormat( "HH:mm");
		String sessionHour = hourFormat.format(session.getSessionTime());

		DateFormat todayFormat = new SimpleDateFormat("yyyy.MM.dd");
		String today = todayFormat.format(new Date());

		String joinStr = today + " " + sessionHour;

		DateFormat serviceFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");

		Date sessionDate = null;

		try {
			sessionDate = serviceFormat.parse(joinStr);
		} catch(Exception e) {}

		//On inscrit l'User
		DataCenter.database.getRegistrations().add(new Registration(sessionDate, session.getProId(), membre.getId(), session.getServiceId(), comment, session.getSessionTime()));

		session.addParticipant();

		System.out.println("Membre inscrit !");
	}
}
