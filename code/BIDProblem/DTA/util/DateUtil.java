package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");

    public static int diff(String d1, String d2) {
        try {
            Date date1 = dateFormat.parse(d1);
            Date date2 = dateFormat.parse(d2);
            return (int) ((date1.getTime() - date2.getTime()) / TimeUnit.DAYS.toMillis(1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String add(String d, int delta) {
        try {
            Date date = dateFormat.parse(d);
            Date newDate = new Date(date.getTime() + delta * TimeUnit.DAYS.toMillis(1));
            return dateFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isBetween(String d, String d1, String d2) {
        try {
            Date date = dateFormat.parse(d);
            Date date1 = dateFormat.parse(d1);
            Date date2 = dateFormat.parse(d2);
            return date1.getTime() <= date.getTime() && date.getTime() <= date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
