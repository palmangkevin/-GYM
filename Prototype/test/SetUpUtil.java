import gym.Session;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SetUpUtil {

    static ArrayList<Session.Day> setDays() {
        ArrayList<Session.Day> days = new ArrayList<>();
        days.add(Session.Day.LUNDI);
        days.add(Session.Day.MARDI);
        days.add(Session.Day.MERCREDI);
        days.add(Session.Day.JEUDI);
        days.add(Session.Day.VENDREDI);
        days.add(Session.Day.SAMEDI);
        days.add(Session.Day.DIMANCHE);
        return days;
    }

    static Date parseDate(String date, String strFormat) {
        DateFormat format = new SimpleDateFormat(strFormat, Locale.ENGLISH);
        Date outputDate = null;
        try {
            outputDate = format.parse(date);
        } catch (ParseException e) {
        }

        return outputDate;
    }

}
