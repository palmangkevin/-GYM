package gym;

import javafx.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Classe pour confirmer la présence d'un membre à une séance.
 * Implémente l'interface Action
 */
public class ConfirmMemberAttendanceToSession implements Action {

    public String getTitle() {
        return "Confirmer la présence d'un membre à une séance";
    }

    public void launch() {

        Scanner in = new Scanner(System.in);

        System.out.println("- Confirmer présence à une séance -");
        System.out.println();

        System.out.println("Entrez le code de service");
        String serviceId = InputReader.askExistingServiceIDLoop(in);

        //Quel jour sommes-nous
        LocalDate date = LocalDate.now();
        DayOfWeek dow = date.getDayOfWeek();

        Date now = new Date();
        int dayInt = Integer.parseInt(dow.getDisplayName(TextStyle.NARROW_STANDALONE, Locale.ENGLISH));
        Session.Day day = Session.Day.values()[dayInt - 1];

        //Lister les Services & Séances qui se donnent le jour même
        ArrayList<Pair<Service, Session>> available = new ArrayList();

        for(Map.Entry<Service, ArrayList<Session>> entry : DataCenter.database.getServiceToSessions().entrySet())
            //Pour chaque session
            for (Session session : entry.getValue())
                //Si c'est le bon jour et la session correspondant au code du service à fournir...
                if(session.getRecurrence().contains(day) && session.getServiceId().equals(serviceId) && session.getStartOfService().compareTo(now) < 0 && session.getEndOfService().compareTo(now) > 0)
                    available.add(new Pair<Service, Session>(entry.getKey(), session));

        if(available.size() == 0) {
            System.out.println("Aucune séance disponible");
            System.out.println("Tapez la touche Enter pour revenir au menu ...");
            in.nextLine();
            return;
        }

        System.out.println("Séances disponibles aujourd'hui :");
        System.out.println();

        //Afficher les choix
        for(int i = 0; i < available.size(); i++) {
            String iStr = Integer.toString(i);
            String serviceTitle = available.get(i).getKey().getTitle();
            DateFormat writeFormat = new SimpleDateFormat("HH:mm");
            String sessionHour = writeFormat.format(available.get(i).getValue().getSessionTime());
            System.out.println(iStr + " - " + serviceTitle + " à " + sessionHour);
        }

        int select = InputReader.askIntLoop(in, available.size());

        Session session = available.get(select).getValue();
        String serviceTitle = available.get(select).getKey().getTitle();
        System.out.println("Nom de service : " + serviceTitle);
        System.out.println("Code de service : " + session.getServiceId());
        DateFormat writeFormat = new SimpleDateFormat("HH:mm");
        System.out.println("Heure : " + writeFormat.format(session.getSessionTime()));
        System.out.println();

        System.out.println("Entrez le numéro du membre à confirmer");
        String id = InputReader.askExistingMemberIDLoop(in);

        //Si l'ID du reg correspond au ID a confirmer, si c'est le meme type de service et a la meme heure
        boolean alreadyRegistered = DataCenter.database.getRegistrations().stream().anyMatch(reg -> reg.getMemberId().equals(id) && reg.getServiceId().equals(serviceId) && reg.getSessionTime().equals(session.getSessionTime()));

        if(!alreadyRegistered) {
            System.out.println("Ce membre n'est pas inscrit à cette seance");
        } else {
            System.out.println("Entrez un commentaire (100 caractères) (facultatif)");
            String comment = InputReader.askStringUnderLimitLoop(in, 100);
            comment = comment.equals("") ? null : comment;

            DataCenter.database.getConfirmations().add(new Confirmation(session.getProId(), id, serviceId, comment));

            System.out.println("Confirmation faite !");
        }
    }
}
