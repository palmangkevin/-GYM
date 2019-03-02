import org.junit.Assert;
import org.junit.Test;
import gym.*;

import java.util.ArrayList;
import java.util.Date;

public class RegMemberToSessionTest {

    @Test
    public void regMemberToSession() {

        // set up
        DataCenter.database = new Database();

        Date date = SetUpUtil.parseDate("15-01-1996", "dd-MM-yyyy");
        Date time = SetUpUtil.parseDate("23:00", "HH:mm");
        Date now = new Date();
        Date sevenDaysFromNow = new Date();
        sevenDaysFromNow.setTime(sevenDaysFromNow.getTime() + 7 * 1000 * 60 * 60 * 24);
        Date sessionDate = new Date();

        ArrayList<Session.Day> days = new ArrayList<>();
        days.add(Session.Day.LUNDI);
        days.add(Session.Day.VENDREDI);

        Pro pro = new Pro("Alain", "Eid", date, "M", "5146210012", "a@gmail.com", "", "", "", "");
        Member member = new Member("Alex", "Eid", date, "M", "5141111111", "b@gmail.com", "", "", "", "");
        Service service = DataCenter.database.getServiceFromId("2761360");
        Session session = new Session(now, sevenDaysFromNow, time, days, 10, pro.getId(), service.getId(), 100.0, 0.0, "");

        DataCenter.database.getPros().add(pro);
        DataCenter.database.getMembers().add(member);
        DataCenter.database.getServiceToSessions().get(service).add(session);

        // aucun membre inscrit
        Assert.assertEquals(DataCenter.database.getRegistrations().size(), 0);

        // on inscrit un membre a la session et on teste
        DataCenter.database.getRegistrations().add(new Registration(sessionDate, session.getProId(), member.getId(), session.getServiceId(), "", session.getSessionTime()));
        DataCenter.database.getServiceToSessions().get(service).get(0).addParticipant();

        // verifier que le compteur de la session a ete incrementee
        Assert.assertEquals(DataCenter.database.getServiceToSessions().get(service).get(0).getParticipantsCount(), 1);

        // verifier que l'enregistrement est ajoutee
        Assert.assertEquals(DataCenter.database.getRegistrations().size(), 1);

        // verifier que l'enregistrement est bien celui qu'on veut
        Assert.assertEquals(DataCenter.database.getRegistrations().get(0).getMemberId(), member.getId());
    }

}
