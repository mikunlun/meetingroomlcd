package hackathon.meetingroom.time;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

public class Time {

    public static void main(String[] args) {
        DateTimeZone PDT = DateTimeZone.forID("America/Los_Angeles");
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0); // anything 0 - 23
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date startDate = c.getTime();
        c.add(Calendar.DATE, 1);
        Date endDate = c.getTime();

        DateTime nowdt = DateTime.now(PDT).withTimeAtStartOfDay();

        System.out.println("now " + nowdt);
        System.out.println("today " + nowdt.plusDays(1));
        System.out.println("startDate " + new DateTime(startDate));
        System.out.println("endDate " + new DateTime(endDate));

        DateTime endtimeHM = DateTime.parse("9:55", DateTimeFormat.forPattern("HH:mm"));
        DateTime endtime = DateTime.now().withHourOfDay(endtimeHM.getHourOfDay()).withMinuteOfHour(endtimeHM.getMinuteOfHour());
        System.out.println(endtime);

        System.out.println("format mins: " + formatMinutes("161"));
    }

    private static String formatMinutes(String minutes) {
        if (minutes == null || "".equals(minutes)) {
            return "";
        }
        int mins = Integer.parseInt(minutes);
        if (mins < 60) {
            return Integer.valueOf(mins) + " minutes";
        } else {
            Minutes m = Minutes.minutes(mins);
            Period p = m.toPeriod();
            int hh = p.toStandardHours().getHours();
            int mm = p.minusHours(hh).toStandardMinutes().getMinutes();
            return hh + " hours, " + mm + " minutes";
        }
    }
}
