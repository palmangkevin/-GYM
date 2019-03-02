package gym;

import javafx.util.Pair;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * API à disposition de l'application
 *
 * Tout est fait pour que l'application n'ait jamais à acceder directement à DataBase
 * AppAPI est l'interface entre l'App et DataBase
 */
public class AppAPI {


    public enum CheckUserValue {
        ValidMember, // Validé
        SuspendedMember // Membre suspendu
    }

    /**
     * Vérifier un membre
     *
     * @param email L'email du membre
     * @return (CheckUserValue, Member) L'état du membre ainsi que l'objet Member pour que l'app puisse afficher son nom et son numéro
     *         ou null si l'email est incorrect.
     */

    public static Pair<CheckUserValue, Member> checkUser(String email) {
        Member membre = DataCenter.database.getMemberFromEmail(email);

        //Si l'email n'est pas valide, on return null
        if(membre == null)
            return null;

        return new Pair<>(membre.isEnabled() ? CheckUserValue.ValidMember : CheckUserValue.SuspendedMember, membre);
    }




























    /**
     * Consulter les séances disponibles le jour même
     *
     * @return Liste des (service, séance) disponibles
     */

    public static List<Pair<Service, Session>> getAvailableSessions() {

        //Quel jour sommes-nous
        Date now = new Date();
        LocalDate date = LocalDate.now();
        DayOfWeek dow = date.getDayOfWeek();

        int dayInt = Integer.parseInt(dow.getDisplayName(TextStyle.NARROW_STANDALONE, Locale.ENGLISH));
        Session.Day day = Session.Day.values()[dayInt - 1];

        //Lister les Services & Séances disponibles pour le jour même
        ArrayList<Pair<Service, Session>> available = new ArrayList<>();

        for (Map.Entry<Service, ArrayList<Session>> entry : DataCenter.database.getServiceToSessions().entrySet())
            //Pour chaque session
            for(Session session: entry.getValue())
                //Si c'est le bon jour ...
                if(session.getRecurrence().contains(day) && !session.isFull() && session.getStartOfService().compareTo(now) < 0 && session.getEndOfService().compareTo(now) > 0)
                    available.add(new Pair<>(entry.getKey(), session));

        return available;
    }


















    public enum RegMemberToSessionValue {
        Success,
        SuspendedMember
    }

    /**
     * Inscrire membre à séance
     *
     * @param selection index de la séance dans la liste affichée sur l'app
     * @param memberId ID du membre
     * @param comment commentaire facultatif (100 char max) (on peut accepter null comme valeur)
     * @return (RegMemberToSessionValue, Frais à payer)
     *         ou null si les paramètres sont incorrects.
     */

    public static Pair<RegMemberToSessionValue, Double> regMemberToSession(int selection, String memberId, String comment) {

        Member membre = DataCenter.database.getMemberFromId(memberId);

        //Si le memberId n'est pas valide, on return null
        if(membre == null)
            return null;

        //Si le commentaire est indiqué, et qu'il fait plus de 100 char, on return null
        if(comment != null && comment.length() > 100)
            return null;


        if(!membre.isEnabled())
            return new Pair<>(RegMemberToSessionValue.SuspendedMember, 0.0);


        List<Pair<Service, Session>> available = getAvailableSessions();

        //Si selection est entre les bornes, sinon on return null
        if(selection < 0 || selection >= available.size())
            return null;


        Session session = null;
        try {
            session = available.get(selection).getValue();
        } catch(Exception e) {}

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

        return new Pair<>(RegMemberToSessionValue.Success, session.getMemberFees());
    }
















    /**
     * Récupère un Map (codes de séance, (service, séance)) disponible le jour même pour un professionnel
     *
     * @return le Map de (codes de séance, (service, séance)) des séance disponible le jour même pour un professionnel
     */

    public static Map<String, Pair<Service, Session>> getProSessions(String proEmail) {

        Pro pro = DataCenter.database.getProFromEmail(proEmail);

        //Si le proId n'est pas valide, on return null
        if(pro == null)
            return null;

        String proId = pro.getId();

        HashMap<String, Pair<Service, Session>> answer = new HashMap<>();

        //Quel jour sommes-nous
        LocalDate date = LocalDate.now();
        DayOfWeek dow = date.getDayOfWeek();

        Date now = new Date();
        int dayInt = Integer.parseInt(dow.getDisplayName(TextStyle.NARROW_STANDALONE, Locale.ENGLISH));
        Session.Day day = Session.Day.values()[dayInt - 1];

        for (Pair<Service, Session> entry : DataCenter.database.getServiceSessionsFromProID(proId)) {
            Service service = entry.getKey();
            Session session = entry.getValue();

            //Si c'est le bon jour
            if (session.getRecurrence().contains(day) && session.getStartOfService().compareTo(now) < 0 && session.getEndOfService().compareTo(now) > 0) {
                int numeroSession = DataCenter.database.getServiceToSessions().get(service).indexOf(session);

                String sessionCode = service.getId().substring(0, 3) + (new DecimalFormat("00")).format(numeroSession).substring(0, 2) + session.getProId().substring(7, 9);

                answer.put(sessionCode, entry);
            }
        }

        return answer;
    }












    public enum ConfirmMemberAttendanceValue {
        MemberNotRegistratedToSession, // Le membre n'est pas inscrit à cette seance
        ConfirmationOk // Confirmation faite !
    }

    /**
     * Confirmer la présence d'un membre à une séance
     *
     * @param session la séance
     * @param memberId ID du membre dont on confirme la présence
     * @return ConfirmMemberAttendanceValue
     *         ou null si les paramètres sont incorrects.
     */

    public static ConfirmMemberAttendanceValue confirmMemberAttendanceToSession(Session session, String memberId) {

        if(session == null)
            return null;

        //Si le memberId n'est pas valide, on return null
        if(DataCenter.database.getMemberFromId(memberId) == null)
            return null;

        //Si l'ID du reg correspond au ID a confirmer, si c'est le meme type de service et a la meme heure
        boolean alreadyRegistered = DataCenter.database.getRegistrations().stream().anyMatch(reg -> reg.getMemberId().equals(memberId) && reg.getServiceId().equals(session.getServiceId()) && reg.getSessionTime().equals(session.getSessionTime()));

        if(!alreadyRegistered) {
            return ConfirmMemberAttendanceValue.MemberNotRegistratedToSession;
        }

        DataCenter.database.getConfirmations().add(new Confirmation(session.getProId(), memberId, session.getServiceId(), null));

        return ConfirmMemberAttendanceValue.ConfirmationOk;

    }












    /**
     * Consulter les inscriptions aux séances d'un professionnel
     *
     * @param email email du professionnel
     * @return un Map de (infos séance, infos inscriptions à la séance)
     *         ou null si l'email du pro est incorrect.
     */

    public static Map<List<String>, List<List<String>>> getMembersRegToProSession(String email) {

        Pro pro = DataCenter.database.getProFromEmail(email);

        //Si le proId n'est pas valide, on return null
        if(pro == null)
            return null;

        String proId = pro.getId();

        HashMap<List<String>, List<List<String>>> answer = new HashMap<>();

        for (Pair<Service, Session> entry : DataCenter.database.getServiceSessionsFromProID(proId)) {
            Service service = entry.getKey();
            Session session = entry.getValue();

            ArrayList<String> sessionInfos = new ArrayList<>();
            ArrayList<List<String>> regsInfos = new ArrayList<>();

            //Nom de service
            sessionInfos.add(service.getTitle());

            //Code de service
            sessionInfos.add(service.getId());

            DateFormat writeFormat = new SimpleDateFormat("HH:mm");

            //Heure de séances
            sessionInfos.add(writeFormat.format(session.getSessionTime()));

            List<Registration> regs = DataCenter.database.getRegistrations().stream().filter(r -> r.getServiceId().equals(service.getId()) && r.getSessionTime() == session.getSessionTime()).collect(Collectors.toList());

            for (Registration reg : regs) {
                ArrayList<String> regInfos = new ArrayList<>();

                Member member = DataCenter.database.getMemberFromId(reg.getMemberId());

                //Nom et Prenom
                regInfos.add(member.getName());

                //Code membre
                regInfos.add(member.getId());

                regsInfos.add(regInfos);
            }

            answer.put(sessionInfos, regsInfos);
        }

        return answer;
    }











    /**
     * Récuperer la facture hebdomadaire d'un membre
     *
     * @param email L'email du membre
     * @return Liste d'informations sur la facture du membre
     */

    public static List<String> getMemberInvoice(String email) {
        Member member = DataCenter.database.getMemberFromEmail(email);

        //Si l'email n'est pas valide, on return null
        if(member == null)
            return null;

        return (new MemberInvoiceGenerator()).getInvoiceInfos(member);
    }









    /**
     * Récuperer l'avis de paiement hebdomadaire d'un professionnel
     *
     * @param email L'email du professionnel
     * @return Liste d'informations sur l'avis de paiement
     */

    public static List<String> getProPayment(String email) {
        Pro pro = DataCenter.database.getProFromEmail(email);

        //Si l'email n'est pas valide, on return null
        if(pro == null)
            return null;

        return (new ProPaymentGenerator()).getPaymentInfos(pro);
   }




}
