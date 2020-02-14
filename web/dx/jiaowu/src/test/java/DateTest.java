import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 李帅雷 on 2017/8/15.
 */
public class DateTest {
    public static void main(String[] args){
        String a=null;
        String b="";
        String result=a+b;
        String c=null;
        String d="null";
        System.out.println(result.equals(c));
        System.out.println(result.equals(d));
    }

    @Test
    public void test(){
        String c1="00B4B6BAE0";
        String c2=conversionCardId(c1);
        String c3=backConversionCardId("E0BAB6B4");
        System.out.println(c2);
        System.out.println(c3);
    }


    public static  String conversionCardId(String cardId) {
        cardId = cardId.substring(2, cardId.length());
        String st[] = new String[8];
        st = cardId.split("");
        return st[6] + st[7] + st[4] + st[5] + st[2] + st[3] + st[0] + st[1];
    }


    /**
     * 考勤卡反转
     *
     * @param cardId
     * @return
     */

    public static  String backConversionCardId(String cardId) {
        String st[] = new String[8];
        st = cardId.split("");
        return "00"+st[6] + st[7] + st[4] + st[5] + st[2] + st[3] + st[0] + st[1];
    }

}
