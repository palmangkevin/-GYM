package gym;

import javafx.util.Pair;

import java.util.*;

/**
 * Classe qui sert de base de données pour stocker les membres, les professionnels, les séances associées à chaque
 * service, les inscriptions aux séances puis les confirmations de présence aux séances.
 */
public class Database {

    private ArrayList<Member> members;
    private ArrayList<Pro> pros;
    private HashMap<Service, ArrayList<Session>> serviceToSessions;
    private ArrayList<Registration> registrations;
    private ArrayList<Confirmation> confirmations;

    /**
     * Constructeur de classe où 6 services sont offerts par défaut.
     */
    public Database() {
        this.members = new ArrayList();
        this.pros = new ArrayList();

        this.serviceToSessions = new HashMap();
        this.serviceToSessions.put(new Service("Entraineur personnel"), new ArrayList());
        this.serviceToSessions.put(new Service("Yoga"), new ArrayList());
        this.serviceToSessions.put(new Service("Cross-fit"), new ArrayList());
        this.serviceToSessions.put(new Service("Nutritionniste"), new ArrayList());
        this.serviceToSessions.put(new Service("Physiothérapeute"), new ArrayList());
        this.serviceToSessions.put(new Service("Massothérapeute"), new ArrayList());

        this.registrations = new ArrayList();
        this.confirmations = new ArrayList();
    }

    /**
     * Chercher un membre dans la liste members par son ID.
     *
     * @param id ID du membre à rechercher
     * @return Member ou null s'il est pas trouvé.
     */
    public Member getMemberFromId(String id) {
        for (Member membre : members)
            if (membre.getId().equals(id))
                return membre;

        return null;
    }

    /**
     * Chercher un membre dans la liste pros par son ID.
     *
     * @param id ID du professionnel à rechercher
     * @return Pro ou null s'il n'est pas trouvé.
     */
    public Pro getProFromId(String id) {
        for (Pro pro : pros)
            if (pro.getId().equals(id))
                return pro;

        return null;
    }

    /**
     * Chercher un membre dans la liste members par son email.
     *
     * @param email email du membre à rechercher
     * @return Member ou null s'il est pas trouvé.
     */
    public Member getMemberFromEmail(String email) {
        for (Member membre : members)
            if (membre.getMail().equals(email))
                return membre;

        return null;
    }

    /**
     * Chercher un professionnel dans la liste pros par son email.
     *
     * @param email email du pro à rechercher
     * @return Pro ou null s'il est pas trouvé.
     */
    public Pro getProFromEmail(String email) {
        for (Pro pro : pros)
            if (pro.getMail().equals(email))
                return pro;

        return null;
    }

    /**
     * @return ArrayList qui contient les membres
     */
    public ArrayList<Member> getMembers() { return members; }

    /**
     * @return ArrayList qui contient les professionnels
     */
    public ArrayList<Pro> getPros() { return pros; }

    /**
     * Getter des clés de serviceToSessions, un ensemble des services
     * @return Set l'ensemble des clés de serviceToSessions
     */
    public Set<Service> getServices() { return serviceToSessions.keySet(); }

    /**
     * Getter d'un dictionnaire qui associe une liste de sessions à un service
     * @return HashMap
     */
    public HashMap<Service, ArrayList<Session>> getServiceToSessions() { return serviceToSessions; }

    /**
     * @return ArrayList des inscriptions.
     */
    public ArrayList<Registration> getRegistrations() { return registrations; }

    /**
     * @return ArrayList des confirmations de présence aux séances.
     */
    public ArrayList<Confirmation> getConfirmations() { return confirmations; }

    /**
     * Chercher un service par son ID.
     *
     * @param id ID du service à rechercher
     * @return Service ou null s'il n'est pas trouvé.
     */
    public Service getServiceFromId(String id) {
        for (Service service : getServices())
            if (service.getId().equals(id))
                return service;

        return null;
    }



    /**
     * Supprimer les séances associées à ce professionnel dans serviceToSessions.
     * Pour chaque service, la liste de sessions est parcourut et la session dont l'ID du professionnel
     * correspondant au ID du professionnel passé en paramètre est supprimée.
     * La méthode est utilisée quand un professionnel est supprimé de la base de données.
     *
     * @param proId ID du professionnel
     */
    public void removeSeancesFromProId(String proId) {
        for (Map.Entry<Service, ArrayList<Session>> entry : getServiceToSessions().entrySet())
            //Pour chaque session
            for (Session session : entry.getValue())
                //Si elle est associée au pro
                if (session.getProId().equals(proId))
                    //On vire la séance
                    entry.getValue().remove(session);
    }

    /**
     * Supprimer les inscriptions associées à un service.
     * La liste d'inscriptions est parcourut et l'insrcription dont l'ID de service correspond au ID du service
     * passé en paramètre est supprimée. La méthode est utilisée quand un service est supprimé de la base de données
     *
     * @param serviceId ID du service
     */
    public void removeRegistrationsFromServiceId(String serviceId) {
        Iterator<Registration> it = registrations.iterator();

        while (it.hasNext()) {
            Registration registration = it.next();

            if (registration.getServiceId().equals(serviceId))
                it.remove();
        }
    }

    /**
     * Supprimer les inscriptions associées à une sessions
     * La liste d'inscription est parcourut et l'inscription dont l'ID du service et l'heure de
     * la session correspondant à l'ID du service et l'heure de la session passée en paramètre est supprimée.
     * La méthode est utilisée quand une session est supprimée de la base de données
     *
     * @param session Session dont les inscriptions doivent être supprimées.
     */
    public void removeRegistrationsFromSession(Session session) {
        Iterator<Registration> it = registrations.iterator();

        while (it.hasNext()) {
            Registration registration = it.next();

            if (registration.getServiceId().equals(session.getServiceId()) && registration.getSessionTime() == session.getSessionTime())
                it.remove();
        }
    }

    /**
     * Supprimer les insriptions associées à un membre. La liste d'inscriptions est parcourut et l'inscription dont les
     * ID sont correspondants est supprimée. La méthode est utilisée quand un membre est supprimé de la base de données.
     *
     * @param memberId ID d'un membre
     */
    public void removeRegistrationsFromMemberId(String memberId) {
        Iterator<Registration> it = registrations.iterator();

        while (it.hasNext()) {
            Registration registration = it.next();

            if (registration.getMemberId().equals(memberId))
                it.remove();
        }
    }

    /**
     * Supprimer les inscriptions associées à un professionnel. La liste d'inscriptions est parcourut et l'inscription
     * dont l'ID du professionnel correspond à l'ID passé en paramètre est supprimé.
     * La méthode est utilisée quand un professionnel est supprimée de la base de données
     *
     * @param proId ID d'un professionnel
     */
    public void removeRegistrationsFromProId(String proId) {
        Iterator<Registration> it = registrations.iterator();

        while (it.hasNext()) {
            Registration registration = it.next();

            if (registration.getProId().equals(proId))
                it.remove();
        }
    }

    /**
     * Getter des sessions associées aux services d'un professionnel.
     *
     * @param proId ID d'un professionnel
     * @return ArrayList de pair contenant le service comme clé et la session comme valeur.
     */
    public ArrayList<Pair<Service, Session>> getServiceSessionsFromProID(String proId) {
        ArrayList<Pair<Service, Session>> available = new ArrayList();

        for (Map.Entry<Service, ArrayList<Session>> entry : getServiceToSessions().entrySet())
            //Pour chaque séance
            for (Session session : entry.getValue())
                //On vérifie que c'est le bon pro
                if (proId.equals(session.getProId()))
                    available.add(new Pair<>(entry.getKey(), session));

        return available;
    }
}
