import org.junit.Assert;
import org.junit.Test;
import gym.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;

public class ConfirmMemberAttendanceToSessionTest {

    @Test
    public void confirmMemberAttendanceToSession() {

        // set up
        DataCenter.database = new Database();

        // set up
        DataCenter.database = new Database();

        Date date = SetUpUtil.parseDate("15-01-1996", "dd-MM-yyyy");
        Date time = SetUpUtil.parseDate("23:30", "HH:mm");
        Date now = new Date();
        Date sevenDaysFromNow = new Date();
        sevenDaysFromNow.setTime(sevenDaysFromNow.getTime() + 7 * 1000 * 60 * 60 * 24);
        Date sessionDate = new Date();

        ArrayList<Session.Day> days = SetUpUtil.setDays();

        Pro pro = new Pro("Alain", "Eid", date, "M", "5146210012", "a@gmail.com", "", "", "", "");
        Member member = new Member("Alex", "Eid", date, "M", "5141111111", "b@gmail.com", "", "", "", "");
        Service service = DataCenter.database.getServiceFromId("2761360");
        Session session = new Session(now, sevenDaysFromNow, time, days, 10, pro.getId(), service.getId(), 100.0, 0.0, "");

        DataCenter.database.getPros().add(pro);
        DataCenter.database.getMembers().add(member);
        DataCenter.database.getRegistrations().add(new Registration(sessionDate, session.getProId(), member.getId(), session.getServiceId(), "", session.getSessionTime()));

        // aucune confirmation effectuee
        Assert.assertTrue(DataCenter.database.getConfirmations().isEmpty());

        // on confirme le membre a la seance 2761360 et on teste
        DataCenter.database.getConfirmations().add(new Confirmation(session.getProId(), member.getId(), "2761360", ""));
        Assert.assertFalse(DataCenter.database.getConfirmations().isEmpty());
        Assert.assertEquals(DataCenter.database.getConfirmations().size(), 1);
        Assert.assertEquals(DataCenter.database.getConfirmations().get(0).getUserId(), member.getId());
    }

}
