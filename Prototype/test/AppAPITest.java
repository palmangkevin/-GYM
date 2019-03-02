
import org.junit.Assert;
import org.junit.Test;
import gym.*;

import java.util.ArrayList;
import java.util.Date;

public class AppAPITest {

    @Test
    public void getMembersRegToProSession() {

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

        // tests
        Assert.assertNull(AppAPI.getMembersRegToProSession(""));
        Assert.assertNull(AppAPI.getMembersRegToProSession("coucou"));
        Assert.assertEquals(AppAPI.getMembersRegToProSession(pro.getMail()).entrySet().iterator().next().getValue().size(), 0);

        // on inscrit le membre a la seance 2761360
        DataCenter.database.getRegistrations().add(new Registration(sessionDate, session.getProId(), member.getId(), session.getServiceId(), "", session.getSessionTime()));

        // tests
        Assert.assertEquals(AppAPI.getMembersRegToProSession(pro.getMail()).entrySet().iterator().next().getValue().size(), 1);

    }

    @Test
    public void checkUser() {

        // set up
        DataCenter.database = new Database();

        Date date = SetUpUtil.parseDate("15-01-1996", "dd-MM-yyyy");
        Member member = new Member("Alex", "Eid", date, "M", "5141111111", "b@gmail.com", "", "", "", "");

        DataCenter.database.getMembers().add(member);

        // tests
        Assert.assertNull(AppAPI.checkUser(""));
        Assert.assertNull(AppAPI.checkUser("alain"));
        Assert.assertNull(AppAPI.checkUser("12345"));
        Assert.assertEquals(AppAPI.checkUser(member.getMail()).getKey(), AppAPI.CheckUserValue.ValidMember);

        // on suspend le membre
        member.disable();
        Assert.assertEquals(AppAPI.checkUser(member.getMail()).getKey(), AppAPI.CheckUserValue.SuspendedMember);

        // on reactive le membre
        member.enable();
        Assert.assertEquals(AppAPI.checkUser(member.getMail()).getKey(), AppAPI.CheckUserValue.ValidMember);

    }

    @Test
    public void getAvailableSessions() {

        // set up
        DataCenter.database = new Database();

        Date date = SetUpUtil.parseDate("15-01-1996", "dd-MM-yyyy");
        Date time1 = SetUpUtil.parseDate("02:00", "HH:mm");
        Date time2 = SetUpUtil.parseDate("23:59", "HH:mm");

        Date now = new Date();
        Date sevenDaysFromNow = new Date();
        sevenDaysFromNow.setTime(sevenDaysFromNow.getTime() + 7 * 1000 * 60 * 60 * 24);

        ArrayList<Session.Day> days = SetUpUtil.setDays();

        Pro pro = new Pro("Alain", "Eid", date, "M", "5146210012", "a@gmail.com","", "", "", "");
        Service service1 = DataCenter.database.getServiceFromId("2761360");
        Service service2 = DataCenter.database.getServiceFromId("8947894");

        Session session1 = new Session(now, sevenDaysFromNow, time1, days, 10, pro.getId(), service1.getId(), 100.0, 0.0, "");
        Session session2 = new Session(now, sevenDaysFromNow, time2, days, 10, pro.getId(), service2.getId(), 100.0, 0.0, "");

        // test avant d'ajouter une session
        Assert.assertEquals(AppAPI.getAvailableSessions().size(), 0);

        // on ajoute une session de service1 et on test
        DataCenter.database.getServiceToSessions().get(service1).add(session1);
        Assert.assertEquals(AppAPI.getAvailableSessions().size(), 1);

        // on ajoute une session de service2, il y a maintenant 2 seances differentes pour aujourd'hui et on test
        DataCenter.database.getServiceToSessions().get(service2).add(session2);
        Assert.assertEquals(AppAPI.getAvailableSessions().size(), 2);
    }


}
