import com.jiaowu.biz.userWorkDayData.UserWorkDayDataBiz;
import com.jiaowu.entity.userWorkDayData.UserWorkDayData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 李帅雷 on 2017/8/15.
 */
public class DateTest {
    public static void main(String[] args){

        //上午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工。
        Integer morningAttendanceStatus;
        //下午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工。
        Integer afternoonAttendanceStatus;
        
        String cMemo="第一时间段旷工0.56天第二时间段旷工0.44天";
        int morningStart=cMemo.indexOf("第一时间段");
        int afternoonStart=cMemo.indexOf("第二时间段");
        String morningText=null;
        String afternoonText=null;
        if(morningStart>=0&&afternoonStart>=0){
            morningText=cMemo.substring(morningStart+5,afternoonStart);
            afternoonText=cMemo.substring(afternoonStart+5,cMemo.length());
        }else if(morningStart>=0&&afternoonStart<0){
            morningText=cMemo.substring(morningStart+5,cMemo.length());
        }else if(morningStart<0&&afternoonStart>=0){
            afternoonText=cMemo.substring(afternoonStart+5,cMemo.length());
        }else{

        }
        if(morningText!=null&&!morningText.equals("")){
            if(morningText.contains("迟到")){
                morningAttendanceStatus=2;
            }else if(morningText.contains("早退")){
                morningAttendanceStatus=3;
            }else if(morningText.contains("旷工")){
                morningAttendanceStatus=4;
            }else if(morningText.contains("加班")){
                morningAttendanceStatus=5;
            }else{
                morningAttendanceStatus=1;
            }
        }else{
            morningAttendanceStatus=1;
        }
        if(afternoonText!=null&&!afternoonText.equals("")){
            if(afternoonText.contains("迟到")){
                afternoonAttendanceStatus=2;
            }else if(afternoonText.contains("早退")){
                afternoonAttendanceStatus=3;
            }else if(afternoonText.contains("旷工")){
                afternoonAttendanceStatus=4;
            }else if(afternoonText.contains("加班")){
                afternoonAttendanceStatus=5;
            }else{
                afternoonAttendanceStatus=1;
            }
        }else{
            afternoonAttendanceStatus=1;
        }
        System.out.print(morningAttendanceStatus+","+afternoonAttendanceStatus);
    }
//        String s="前面<img src=\"http://static.1kao.cn/upload/eduplatquestion/common/20170809/1502263271442416816.png\" alt=\"\" />中间<img src=\"http://static.1kao.cn/upload/eduplatquestion/common/20170809/1502263271442416816.png\" alt=\"\" />";
//        while(s.contains("img")){
//            int begin=s.indexOf("img");
//            int end=s.indexOf("/>");
//            int srcbegin=s.indexOf("http:");
//            int srcend=s.indexOf("png");
//            if(srcend==-1){
//                srcend=s.indexOf("jpg");
//            }
//            s=s.substring(0,begin-1)+s.substring(srcbegin,srcend+3)+s.substring(end+2,s.length());
//        }
//        System.out.print(s);
    @Test
    public void Test(){
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        UserWorkDayData toDay=new UserWorkDayData();
        toDay.setWorkDate(bartDateFormat.format(new Date()).trim());
        UserWorkDayDataBiz userWorkDayDataBiz=new UserWorkDayDataBiz();
        userWorkDayDataBiz.delete(toDay);
    }


}
