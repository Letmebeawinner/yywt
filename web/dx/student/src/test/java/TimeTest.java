import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author YaoZhen
 * @date 04-03, 11:36, 2018.
 */
public class TimeTest {
    public static void main(String[] args) {
        str();
    }

    private static void str() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = new GregorianCalendar();
        System.out.println(calendar.getTime().getTime());
        System.out.println(getDate());
    }

    private static long getDate() {
        return new Date().getTime();
    }
}
