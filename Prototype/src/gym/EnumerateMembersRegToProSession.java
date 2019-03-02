
package gym;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe qui énumère les membres inscrits aux sessions offerte par un professionel.
 * Implémente l'interface Action
 */
public class EnumerateMembersRegToProSession implements Action {

    public String getTitle(){
        return "Consulter les inscriptions aux séances d'un professionnel";
    }

    public void launch() {
        Scanner in = new Scanner(System.in);

        System.out.println("- Consulter inscriptions -");
        System.out.println();

	    System.out.println("Entrez l'ID du professionnel");
        String proId = InputReader.askExistingProIDLoop(in);

        System.out.println("- Services - ");

	    for (Map.Entry<Service, ArrayList<Session>> entry : DataCenter.database.getServiceToSessions().entrySet())
		    //Pour chaque session
            for(Session sess : entry.getValue())
            	//On vérifie que c'est le bon pro
                if(proId.equals(sess.getProId())) {

	                String serviceId = sess.getServiceId();

	                System.out.println("_________________");
	                System.out.println();
                    System.out.println("Nom de service: " + entry.getKey().getTitle());
                    System.out.println("Code de service: " + serviceId);
                    System.out.println();

                    DateFormat writeFormat = new SimpleDateFormat("HH:mm");

	                System.out.println("Heure de séances : " + writeFormat.format(sess.getSessionTime()));

                    System.out.println();

                    List<Registration> regs = DataCenter.database.getRegistrations().stream().filter(r -> r.getServiceId().equals(serviceId) && r.getSessionTime() == sess.getSessionTime()).collect(Collectors.toList());

                    if(regs.size() > 0) {
                    	for(Registration reg: regs) {
		                    Member member = DataCenter.database.getMemberFromId(reg.getMemberId());
		                    System.out.println("Nom et Prenom: " + member.getName());
		                    System.out.println("Code membre: " + member.getId());
		                    System.out.println();
                    	}
                    } else {
                        System.out.println("Aucun membre inscrit à cette séance.");
                    }

	                System.out.println("_________________");
                }

	    System.out.println();
    }
}
