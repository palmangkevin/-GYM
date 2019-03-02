import org.junit.Assert;
import org.junit.Test;
import gym.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ReportGeneratorTest {

    @Test
    public void ReportGeneratorTest(){

        DataCenter.database = new Database();

        Date date = SetUpUtil.parseDate("15-01-1996", "dd-MM-yyyy");
        Date time = SetUpUtil.parseDate("23:00", "HH:mm");
        Date now = new Date();
        Date sevenDaysFromNow = new Date();
        sevenDaysFromNow.setTime(sevenDaysFromNow.getTime() + 7 * 1000 * 60 * 60 * 24);

        ArrayList<Session.Day> days = new ArrayList<>();
        days.add(Session.Day.LUNDI);
        days.add(Session.Day.VENDREDI);

        Pro pro = new Pro("Alain", "Eid", date, "M", "5146210012", "a@gmail.com", "", "", "", "");
        Service service = DataCenter.database.getServiceFromId("2761360");
        Session session = new Session(now, sevenDaysFromNow, time, days, 20, pro.getId(), service.getId(), 95.0, 0.0, "");

        DataCenter.database.getPros().add(pro);
        DataCenter.database.getServiceToSessions().get(service).add(session);

        ReportGenerator reportGenerator = new ReportGenerator();
        HashMap<Pro, ReportElement> reports = reportGenerator.getReports();

        Assert.assertEquals(reports.get(pro).getAmount(),95.0,0.0);
        Assert.assertEquals(reports.size(),1);




    }
}
