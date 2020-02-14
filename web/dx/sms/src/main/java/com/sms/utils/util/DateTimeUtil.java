package com.sms.utils.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ����ʱ�乤����
 * 1.�ճ�ʱ����java.util.Date,java.util.Calendar��String,int��ת��
 * 2.���ݿ�ʹ����java.sql.Date,Time,Timestamp��String,int��ת��
 */
public class DateTimeUtil
{
    /** ���ص�ǰʱ���Date */
    public static java.util.Date nowDate()
    {
        return new java.util.Date();
    }
    
    /**
     * �ַ���תΪʱ��,�ַ������ϱ�׼��ʽ:"YYYY-MM-DD HH:MM:SS"
     * 
     * @param dateTime ��׼ʱ���ʽ "YYYY-MM-DD HH:MM:SS"
     * @return java.util.Date
     */
    public static java.util.Date toDate(String dateTime)
    {
        int index = dateTime.indexOf(" ");
        String date = dateTime.substring(0, index);
        String time = dateTime.substring(index + 1);

        return toDate(date, time);
    }

    /**
     * �ַ���תΪʱ��,�ַ������ϱ�׼���ڸ�ʽ:"YYYY-MM-DD",�ͱ�׼ʱ���ʽ:"HH:MM:SS"
     * 
     * @param date ��׼���ڸ�ʽ "YYYY-MM-DD"
     * @param time ��׼ʱ���ʽ "HH:MM:SS"
     * @return java.util.Date
     */
    public static java.util.Date toDate(String date, String time)
    {
        if (date == null || time == null)
            return null;

        int dateSlash1 = date.indexOf("-");
        int dateSlash2 = date.lastIndexOf("-");

        if (dateSlash1 <= 0 || dateSlash1 == dateSlash2)
            return null;
        
        int timeColon1 = time.indexOf(":");
        int timeColon2 = time.lastIndexOf(":");

        if (timeColon1 <= 0 || timeColon1 ==timeColon2)
            return null;
        
        String year = date.substring(0, dateSlash1);
        String month = date.substring(dateSlash1 + 1, dateSlash2);
        String day = date.substring(dateSlash2 + 1);
        
        String hour = time.substring(0, timeColon1);
        String minute = time.substring(timeColon1 + 1, timeColon2);
        String second = time.substring(timeColon2 + 1);;

        return toDate(year, month, day, hour, minute, second);
    }

    /**
     * ͨ����׼ʱ������,��,��,��,ʱ,��,��,����java.util.Date
     * 
     * @param yearStr ��
     * @param monthStr ��
     * @param dayStr ��
     * @param hourStr ʱ
     * @param minuteStr ��
     * @param secondStr ��
     * @return java.util.Date
     */
    public static java.util.Date toDate(String yearStr,String monthStr, String dayStr, 
        String hourStr, String minuteStr, String secondStr)
    {
        int year, month, day, hour, minute, second;

        try
        {
            year = Integer.parseInt(yearStr);
            month = Integer.parseInt(monthStr);
            day = Integer.parseInt(dayStr);
            hour = Integer.parseInt(hourStr);
            minute = Integer.parseInt(minuteStr);
            second = Integer.parseInt(secondStr);
        }
        catch (Exception e)
        {
            return null;
        }
        return toDate(year, month, day, hour, minute, second);
    }

    /**
     * ͨ����׼ʱ������,��,��,��,ʱ,��,��,����java.util.Date
     * 
     * @param year ��
     * @param month ��
     * @param day ��
     * @param hour ʱ
     * @param minute ��
     * @param second ��
     * @return java.util.Date
     */
    public static java.util.Date toDate(int year, int month, int day, 
        int hour, int minute, int second)
    {
        Calendar calendar = Calendar.getInstance();

        try
        {
            calendar.set(year, month - 1, day, hour, minute, second);
        }
        catch (Exception e)
        {
            return null;
        }
        return calendar.getTime();
    }

    /**
     * ���ɱ�׼��ʽ���ַ��� ��ʽΪ: "MM-DD-YYYY HH:MM:SS"
     *
     * @param date The Date
     * @return ����Ĭ�ϸ�ʽ���ַ��� ��ʽΪ: "MM-DD-YYYY HH:MM:SS"
     */
    public static String toDateTimeString(java.util.Date date)
    {
        if (date == null)
            return "";
        
        String dateString = toDateString(date);
        String timeString = toTimeString(date);

        if (dateString == null || timeString == null)
            return "";
        
        return dateString + " " + timeString;
    }

    /**
     * ���ɱ�׼����,��ʽΪ YYYY+spe+MM+spe+DD
     * 
     * @param date The Date
     * @return ��������,��ʽΪ YYYY+spe+MM+spe+DD
     */
    public static String toDateString(java.util.Date date, String spe)
    {
        if (date == null)
            return "";
        
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        
        String monthStr = "" + month;
        String dayStr = "" + day;
        String yearStr= "" + year;

        if (month < 10)
            monthStr = "0" + month;

        if (day < 10)
            dayStr = "0" + day;

        return yearStr + spe + monthStr + spe + dayStr;
    }

    /**
     * ���ɱ�׼����,��ʽΪ YYYY-MM-DD
     * 
     * @param date The Date
     * @return ��������,��ʽΪ YYYY-MM-DD
     */
    public static String toDateString(java.util.Date date)
    {
        return toDateString(date, "-");
    }

    /**
     * ���������ʱ��,����ʱ���ʽ HH:MM:SS
     * 
     * @param date java.util.Date
     * @return ����ʱ���ʽΪ HH:MM:SS
     */
    public static String toTimeString(java.util.Date date)
    {
        if (date == null)
            return "";
        
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        return toTimeString(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
            calendar.get(Calendar.SECOND));
    }

    /**
     * ���������ʱ,��,��,����ʱ���ʽ HH:MM:SS
     * 
     * @param hour ʱ
     * @param minute ��
     * @param second ��
     * @return ����ʱ���ʽΪ HH:MM:SS
     */
    public static String toTimeString(int hour, int minute, int second)
    {
        String hourStr = "" + hour;
        String minuteStr = "" + minute;
        String secondStr = "" + second;

        if (hour < 10)
            hourStr = "0" + hour;
        
        if (minute < 10)
            minuteStr = "0" + minute;

        if (second < 10)
            secondStr = "0" + second;

        return hourStr + ":" + minuteStr + ":" + secondStr;
    }

    /**
     * ȡ�ø�������,������ʽ�������ַ���
     * 
     * @param calendar ����,����һ������
     * @return String ȡ��Ĭ�ϵ�����ʱ���ַ���"yyyy-MM-dd"
     */
    public static String toDateString(Calendar calendar)
    {
        String format = "yyyy-MM-dd";
        return toDateTimeString(calendar.getTime(),format);
    }
    
    /**
     * ȡ�ø�������,������ʽ������ʱ���ַ���
     * 
     * @param calendar ����,����һ������
     * @return String ȡ��Ĭ�ϵ�����ʱ���ַ���"yyyy-MM-dd HH:mm:ss"
     */
    public static String toDateTimeString(Calendar calendar)
    {
        String format = "yyyy-MM-dd HH:mm:ss";
        return toDateTimeString(calendar.getTime(),format);
    }
    
    /**
     * ȡ�ø�������,������ʽ������ʱ���ַ���
     * 
     * @param calendar ����,����һ������
     * @param format ��ʽ,��String format = "yyyy-MM-dd HH:mm:ss";
     * @return String ȡ�ø�������,������ʽ������ʱ���ַ���
     */
    public static String toDateTimeString(Calendar calendar,String format)
    {
        return toDateTimeString(calendar.getTime(),format);
    }
    
    /**
     * ȡ�ø���ʱ��,������ʽ������ʱ���ַ���,��׼��ʽ:"yyyy-MM-dd HH:mm:ss";
     * 
     * @param datetime ����,����һ��ʱ��ĺ�����
     * @return String ȡ�ø���ʱ��,������ʽ������ʱ���ַ���
     */
    public static String toDateTimeString(long datetime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new java.util.Date(datetime));
    }
    
    /**
     * ȡ�ø���ʱ��,������ʽ������ʱ���ַ���
     * 
     * @param datetime ����,����һ��ʱ��ĺ�����
     * @param format ��ʽ,��String format = "yyyy-MM-dd HH:mm:ss";
     * @return String ȡ�ø���ʱ��,������ʽ������ʱ���ַ���
     */
    public static String toDateTimeString(long datetime,String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new java.util.Date(datetime));
    }
    
    /**
     * ȡ�ø���ʱ��,������ʽ������ʱ���ַ���
     * 
     * @param date ����,����һ��ʱ��
     * @param format ��ʽ,��String format = "yyyy-MM-dd HH:mm:ss";
     * @return String ȡ�ø���ʱ��,������ʽ������ʱ���ַ���
     */
    public static String toDateTimeString(java.util.Date date,String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    /**
     * ȡ�õ�ǰ������ʱ���ַ���
     * 
     * @param format ��ʽ,��String format = "yyyy-MM-dd HH:mm:ss";
     * @return String ȡ�õ�ǰ������ʱ���ַ���
     */
    public static String getDateTimeString(String format)
    {
        return toDateTimeString(new java.util.Date(),format);
    }
    
    /**
     * ȡ�õ�ǰ������ʱ���ַ���YYYY-MM-DD HH:mm:ss
     * 
     * @return String ȡ�õ�ǰ������ʱ���ַ���YYYY-MM-DD HH:mm:ss
     */
    public static String getDateTimeString()
    {
        String format = "yyyy-MM-dd HH:mm:ss";
        return getDateTimeString(format);
    }
    
    /**
     * ȡ�õ�ǰ������ʱ���ַ���YYYY/MM/DD HH:mm:ss (�ƶ�)
     * 
     * @return String ȡ�õ�ǰ������ʱ���ַ���YYYY/MM/DD HH:mm:ss
     */
    public static String getDateTimeString2()
    {
        String format = "yyyy/MM/dd HH:mm:ss";
        return getDateTimeString(format);
    }  
    
    /**
     * ȡ�õ�ǰ������ʱ���ַ���YYYY/MM/DD (�ƶ�)
     * 
     * @return String ȡ�õ�ǰ������ʱ���ַ���YYYY/MM/DD
     */
    public static String getDateString2()
    {
        String format = "yyyy/MM/dd";
        return getDateTimeString(format);
    }  
    
    /**
     * ȡ�õ�ǰ������ʱ���ַ���YYYYMMDDHHMISS
     * 
     * @return String ȡ�õ�ǰ������ʱ���ַ���YYYYMMDDHHMISS
     */
    public static String getDateTime14String()
    {
        String format = "yyyyMMddHHmmss";
        return getDateTimeString(format);
    }

    /**
     * ȡ�õ�ǰ������ʱ���ַ���YYMMDDHHMISS
     * 
     * @return String ȡ�õ�ǰ������ʱ���ַ���YYMMDDHHMISS
     */
    public static String getDateTime12String()
    {
        String format = "yyMMddHHmmss";
        return getDateTimeString(format);
    }
    
    /**
     * ȡ�õ�ǰ������ʱ���ַ���YYYYMMDD
     * 
     * @return String ȡ�õ�ǰ������ʱ���ַ���
     */
    public static String getDateTime8String()
    {
        String format = "yyyyMMdd";
        return getDateTimeString(format);
    }
    
    /**
     * ȡ�õ�ǰ������ʱ���ַ���YYYYMM
     * 
     * @return String ȡ�õ�ǰ������ʱ���ַ���
     */
    public static String getDateTime6String()
    {
        String format = "yyyyMM";
        return getDateTimeString(format);
    }
    
    /**
     * ȡ�õ�ǰ������ʱ���ַ���YYYY-MM-DD
     * 
     * @return String ȡ�õ�ǰ������ʱ���ַ���
     */
    public static String getDateString()
    {
        String format = "yyyy-MM-dd";
        return getDateTimeString(format);
    }        
    
    /**
     * ȡ�õ�ǰ������ʱ���ַ���HH:mm:ss
     * 
     * @return String ȡ�õ�ǰ������ʱ���ַ���
     */
    public static String getTimeString()
    {
        String format = "HH:mm:ss";
        return getDateTimeString(format);
    }
    
    
    /**
     * ȡ�õ�ǰ�������������鹲7��,�ֱ�Ϊ��,��,��,ʱ,��,��,����
     * 
     * @return int[] ��7��,�ֱ�Ϊ��,��,��,ʱ,��,��,����
     */
    public static int[] getDateTimes()
    {
        int[] dates = new int[7];
        Calendar calendar = Calendar.getInstance();
        dates[0] = calendar.get(Calendar.YEAR);
        dates[1] = calendar.get(Calendar.MONTH) + 1;
        dates[2] = calendar.get(Calendar.DAY_OF_MONTH);
        dates[3] = calendar.get(Calendar.HOUR_OF_DAY);
        dates[4] = calendar.get(Calendar.MINUTE);
        dates[5] = calendar.get(Calendar.SECOND);
        dates[6] = calendar.get(Calendar.MILLISECOND);
        return dates;
    }

    /**
     * ͨ����׼ʱ������,��,��,��,ʱ,��,��,����java.util.Date
     * 
     * @param yearStr ��
     * @param monthStr ��
     * @param dayStr ��
     * @param hourStr ʱ
     * @param minuteStr ��
     * @param secondStr ��
     * @return Calendar
     */
    public static Calendar toCalendar(String yearStr,String monthStr, String dayStr, 
        String hourStr, String minuteStr, String secondStr)
    {
        int year, month, day, hour, minute, second;

        try
        {
            year = Integer.parseInt(yearStr);
            month = Integer.parseInt(monthStr);
            day = Integer.parseInt(dayStr);
            hour = Integer.parseInt(hourStr);
            minute = Integer.parseInt(minuteStr);
            second = Integer.parseInt(secondStr);
        }
        catch (Exception e)
        {
            return null;
        }
        
        return toCalendar(year, month, day, hour, minute, second);
    }
    
    /**
     * ͨ��String,��֯һ������
     * 
     * @param dates
     * @return ͨ����������,����һ������
     */
    public static Calendar toCalendar(String datetime)
    {
        java.util.Date date = toDateFromStr(datetime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }   
    
    /**
     * ͨ����������,��֯һ������
     * 
     * @param dates
     * @return ͨ����������,����һ������
     */
    public static Calendar toCalendar(int[] dates)
    {
        if(dates == null || dates.length < 6)
            return null;
        
        return toCalendar(dates[0], dates[1], dates[2], dates[3], dates[4], dates[5]);
    }
    

    /**
     * ͨ����׼ʱ������,��,��,��,ʱ,��,��,����java.util.Date
     * 
     * @param year ��
     * @param month ��
     * @param day ��
     * @param hour ʱ
     * @param minute ��
     * @param second ��
     * @return Calendar
     */
    public static Calendar toCalendar(int year,int month, int day, 
        int hour, int minute, int second)
    {        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DATE,day);
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,second);

        return c;
    }
    
    /**
     * ͨ����������,��֯һ������
     * 
     * @param dates
     * @return ͨ����������,��֯һ������
     */
    public static java.util.Date toDate(int[] dates)
    {
        if(dates == null || dates.length < 6)
            return null;
        
        return toCalendar(dates).getTime();
    }
    
    /**
     * ȡ�õ�ǰ������ʱ�� 
     * 
     * @param str �ַ���
     * @param format ��ʽ
     * @return ȡ�õ�ǰ������ʱ�� �����ʽ�����򷵻�null
     */
    public static java.util.Date toDateFromStr(String str, String format)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(str);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    /**
     * ȡ�õ�ǰ������ʱ�� ��Ĭ�ϸ�ʽYYYY-MM-DD HH:mm:ss�����򷵻�null
     * 
     * @param str �ַ���
     * @return ȡ�õ�ǰ������ʱ�� ��Ĭ�ϸ�ʽ�����򷵻�null
     */
    public static java.util.Date toDateFromStr(String str)
    {
        try
        {
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(str);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * ��ȡ��ǰ��
     * 
     * @return ��ǰ��
     */
    public static int getCurrentYear()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * ��ȡ��ǰ�·�
     * 
     * @return �·�
     */
    public static int getCurrentMonth()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * ��ȡ��ǰ����
     * 
     * @return ��ǰ����
     */
    public static int getCurrentDay()
    {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * ��ȡ��ǰʱ
     * 
     * @return ��ǰʱ�䣬��:23��,0��,1���
     */
    public static int getCurrentHour()
    {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return hour;
    }

    /**
     * ��ȡ��ǰ��
     * 
     * @return ��ǰ��
     */
    public static int getCurrentMinute()
    {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.MINUTE);

        return hour;
    }
    
    /**
     * ��ȡ��ǰʱ���������:������=7;����һ=1;���ڶ�=2;������=3;������=4;������=5;������=6;
     * 
     * @return ����ֵ
     */
    public static int getCurrentWeek()
    {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        week = week - 1;
        if (week == 0)
            week = 7;

        return week;
    }

    /**
     * ��ȡ�������ڶ����������
     * 
     * @parma date1 ���ڶ���
     * @param date2 ���ڶ���
     * @return int ��ݲ�ֵ
     */
    public static int compareYear(java.util.Date date1, java.util.Date date2)
    {
        if (date1 == null || date2 == null)
            return 0;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        
        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);

        return year1 - year2;
    }

    /**
     * ��ȡ�������ڶ����������
     * 
     * @param date1 ���ڶ���
     * @param date2 ���ڶ���
     * @return int �·ݲ�ֵ
     */
    public static int compareMonth(java.util.Date date1, java.util.Date date2)
    {
        if (date1 == null || date2 == null)
            return 0;
        
        int year = compareYear(date1, date2);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int month1 = calendar.get(Calendar.MONTH);
        
        calendar.setTime(date2);
        int month2 = calendar.get(Calendar.MONTH);

        /* ���бȽ� */
        return 12 * year + (month1 - month2);

    }


    /**
     * ��ȡ�������ڶ����������
     * 
     * @param date1 String yyyy-MM-dd
     * @param date2 String yyyy-MM-dd
     * @return int �ղ�ֵ
     */
    public static int compareDay(String date1str, String date2str)
    {
        if (date1str == null || date2str == null)
            return 0;

        java.util.Date date1 = toDate(date1str, "00:00:01");
        java.util.Date date2 = toDate(date2str, "00:00:00");
        
        return compareDay(date1, date2);
    }
    
    /**
     * ��ȡ�������ڶ����������
     * 
     * @param date1 ���ڶ���
     * @param date2 ���ڶ���
     * @return int �ղ�ֵ
     */
    public static int compareDay(java.util.Date date1, java.util.Date date2)
    {
        if (date1 == null || date2 == null)
            return 0;

        long time1 = date1.getTime();
        long time2 = date2.getTime();

        long margin = time1 - time2;

        /* ת�������� */
        int ret = (int)Math.floor((double)margin / (1000 * 60 * 60 * 24));

        return ret;
    }

    /**
     * ��ȡ�������ڶ�������Сʱ��
     * 
     * @param date1 ���ڶ���
     * @param date2 ���ڶ���
     * @return int ���Сʱ��
     */
    public static int compareHour(java.util.Date date1, java.util.Date date2)
    {
        if (date1 == null || date2 == null)
            return 0;

        long time1 = date1.getTime();
        long time2 = date2.getTime();

        long margin = time1 - time2;

        int ret = (int)Math.floor((double)margin / (1000 * 60 * 60 * 24));

        return ret;
    }

    /**
     * ��ȡ�������ڶ������ķ�����
     * 
     * @param date1 ���ڶ���
     * @param date2 ���ڶ���
     * @return int ��������
     */
    public static int compareMinute(java.util.Date date1, java.util.Date date2)
    {
        if (date1 == null || date2 == null)
            return 0;

        long time1 = date1.getTime();
        long time2 = date2.getTime();

        long margin = time1 - time2;

        int ret = (int)Math.floor((double)margin / (1000 * 60 * 60 * 24));

        return ret;
    }

    /**
     * ��ȡ�������ڶ����������
     * 
     * @param date1 ���ڶ���
     * @param date2 ���ڶ���
     * @return int �������
     */
    public static int compareSecond(java.util.Date date1, java.util.Date date2)
    {
        if (date1 == null || date2 == null)
            return 0;

        long time1 = date1.getTime();
        long time2 = date2.getTime();

        long margin = time1 - time2;

        Long longValue = new Long(margin / (1000));

        return longValue.intValue();
    }

    /**
     * ��ȡ�͵�ǰʱ������ֵ
     * 
     * @param dateTime YYYY-MM-DD hh:mm:ss
     * @return �����
     */
    public static long getTimeMargin(String dateTime)
    {
        int index = dateTime.indexOf(" ");
        String date = dateTime.substring(0, index);
        String time = dateTime.substring(index + 1);
        
        int dateSlash1 = date.indexOf("-");
        int dateSlash2 = date.lastIndexOf("-");

        if (dateSlash1 <= 0 || dateSlash1 == dateSlash2)
            return -1;
        
        int timeColon1 = time.indexOf(":");
        int timeColon2 = time.lastIndexOf(":");

        if (timeColon1 <= 0 || timeColon1 ==timeColon2)
            return -1;

        Calendar calendar = Calendar.getInstance();
        
        try
        {
            int year = Integer.parseInt(date.substring(0, dateSlash1));
            int month = Integer.parseInt(date.substring(dateSlash1 + 1, dateSlash2));
            int day = Integer.parseInt(date.substring(dateSlash2 + 1));
            
            int hour = Integer.parseInt(time.substring(0, timeColon1));
            int minute = Integer.parseInt(time.substring(timeColon1 + 1, timeColon2));
            int second = Integer.parseInt(time.substring(timeColon2 + 1));
            
            calendar.set(year, month - 1, day, hour, minute, second);
        }
        catch (Exception e)
        {
            return -1;
        }
        
        return System.currentTimeMillis() - calendar.getTimeInMillis();
    }
    
    /**
     * ��ȡ��ǰʱ���ǰһ���������ꡢ�¡��գ�����������ʽ���ء� ����0Ϊ�ꣻ1Ϊ�£�2Ϊ��
     * 
     * @param year ��ǰ��
     * @param month ��ǰ��
     * @param day ��ǰ����
     * @param days �������
     * @return �ꡢ�¡�������
     */
    public static int[] getPreviousDay(int year, int month, int day, int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);

        long longDate = (calendar.getTime()).getTime() - (1000 * 60 * 60 * 24 * days);
        java.util.Date date = new java.util.Date(longDate);
        calendar.setTime(date);

        int[] rtn = new int[3];
        rtn[0] = calendar.get(Calendar.YEAR);
        rtn[1] = calendar.get(Calendar.MONTH) + 1;
        rtn[2] = calendar.get(Calendar.DATE);

        return rtn;
    }
    
    /**
     * ��ȡǰ�����Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getPreviousDateString(int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        
        return toDateString(calendar);
    }
    
    /**
     * ��ȡǰ�����Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getPreviousDateTimeString(int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        
        return toDateTimeString(calendar);
    }

    /**
     * ��ȡǰ��Сʱ��Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getPreviousDateByHourString(int hours)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -hours);
        
        return toDateString(calendar);
    }
    
    /**
     * ��ȡǰ��Сʱ��Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getPreviousDateTimeByHourString(int hours)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -hours);
        
        return toDateTimeString(calendar);
    }
    
    /**
     * ��ȡǰ�����Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getPreviousDateBySecondString(int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -second);
        
        return toDateString(calendar);
    }
    
    /**
     * ��ȡǰ�����Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getPreviousDateTimeBySecondString(int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -second);
        
        return toDateTimeString(calendar);
    }
    
    /**
     * ��ȡǰһ���Ӧ�ĵ�ǰʱ��,���ñ�׼��ʽyyyy-MM-dd
     * 
     * @return String
     */
    public static String getPreviousDateString()
    {

        return getPreviousDateTimeString("yyyy-MM-dd");
    }
    
    /**
     * ��ȡǰһ���Ӧ�ĵ�ǰʱ��,���ö��Ÿ�ʽyyyy/MM/dd
     * 
     * @return String
     */
    public static String getPreviousDateString2()
    {

        return getPreviousDateTimeString("yyyy/MM/dd");
    }
    
    /**
     * ��ȡǰһ���Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String getPreviousDateTimeString(String format)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        
        return toDateTimeString(calendar, format);
    }
    
    /**
     * ��ȡǰһ���Ӧ�ĵ�ǰʱ��,���ñ�׼��ʽyyyy-MM-dd HH:mm:ss
     * 
     * @return String
     */
    public static String getPreviousDateTimeString()
    {

        return getPreviousDateTimeString("yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * ��ȡǰһ���Ӧ�ĵ�ǰʱ��,���ö��Ÿ�ʽyyyy/MM/dd HH:mm:ss
     * 
     * @return String
     */
    public static String getPreviousDateTimeString2()
    {

        return getPreviousDateTimeString("yyyy/MM/dd HH:mm:ss");
    }
    
    /**
     * ��ȡ��һ���Date String
     * 
     * @param spe �ָ���
     * @return YYYY+spe+MM+spe+DD 
     */
    public static String getNextDateStr(String spe)
    {
        Calendar calendar = Calendar.getInstance();

        long longDate = (calendar.getTime()).getTime() + (1000 * 60 * 60 * 24 * 1);
        java.util.Date date = new java.util.Date(longDate);
        calendar.setTime(date);

        return toDateString(calendar.getTime(),spe);
    }
    
    /**
     * ��ȡָ��ʱ��ĺ�һ���Date String
     * 
     * @param spe �ָ���
     * @return YYYY+spe+MM+spe+DD 
     */
    public static String getNextDateString(String currentDate)
    {
        Calendar calendar = toCalendar(currentDate+" 00:00:01");
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        
        return toDateString(calendar);
    }
    
    /**
     * ��ȡ�����Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getNextDateString(int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        
        return toDateString(calendar);
    }
    
    /**
     * ��ȡ�����Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getNextDateTimeString(int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        
        return toDateTimeString(calendar);
    }
    
    /**
     * ��ȡ��Сʱ��Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getNextDateStringByHour(int hours)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        
        return toDateString(calendar);
    }
    
    /**
     * ��ȡ��Сʱ��Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getNextDateTimeStringByHour(int hours)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        
        return toDateTimeString(calendar);
    }
    
    /**
     * ��ȡ�����Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getNextDateStringBySecond(int seconds)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, seconds);
        
        return toDateString(calendar);
    }
    
    /**
     * ��ȡ�����Ӧ�ĵ�ǰʱ��
     * 
     * @param format ��ʽ���� yyyy-MM-dd
     * @return String
     */
    public static String getNextDateTimeStringBySecond(int seconds)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, seconds);
        
        return toDateTimeString(calendar);
    }
    
    /**
     * ��ȡ��һ���Date String
     * 
     * @param format ��ʽ��
     * @return YYYY+spe+MM+spe+DD 
     */
    public static String getNextDateTimeStr(String format)
    {
        Calendar calendar = Calendar.getInstance();

        long longDate = (calendar.getTime()).getTime() + (1000 * 60 * 60 * 24 * 1);
        java.util.Date date = new java.util.Date(longDate);
        calendar.setTime(date);

        return toDateTimeString(calendar.getTime(),format);
    }
    
    /**
     * ��ȡ��һ��String
     * 
     * @param year ��ǰ��
     * @param month ��ǰ��
     * @param day ��ǰ����
     * @param days �������
     * @return �ꡢ�¡�������
     */
    public static int[] getNextDay()
    {
        Calendar calendar = Calendar.getInstance();

        long longDate = (calendar.getTime()).getTime() + (1000 * 60 * 60 * 24 * 1);
        java.util.Date date = new java.util.Date(longDate);
        calendar.setTime(date);

        int[] rtn = new int[3];
        rtn[0] = calendar.get(Calendar.YEAR);
        rtn[1] = calendar.get(Calendar.MONTH) + 1;
        rtn[2] = calendar.get(Calendar.DATE);

        return rtn;
    }
    
    /**
     * ��ȡ��ǰʱ��ĺ�һ���������ꡢ�¡��գ�����������ʽ���ء� ����0Ϊ�ꣻ1Ϊ�£�2Ϊ��
     * 
     * @param year ��ǰ��
     * @param month ��ǰ��
     * @param day ��ǰ����
     * @param days �������
     * @return �ꡢ�¡�������
     */
    public static int[] getNextDay(int year, int month, int day, int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);

        long longDate = (calendar.getTime()).getTime() + (1000 * 60 * 60 * 24 * days);
        java.util.Date date = new java.util.Date(longDate);
        calendar.setTime(date);

        int[] rtn = new int[3];
        rtn[0] = calendar.get(Calendar.YEAR);
        rtn[1] = calendar.get(Calendar.MONTH) + 1;
        rtn[2] = calendar.get(Calendar.DATE);

        return rtn;
    }

    /**
     * ��ȡָ��ʱ�������ܵĵ�һ���ʱ��
     * 
     * @param year ��
     * @param month ��
     * @param day ��
     * @return �ꡢ�¡�������
     */
    public static int[] getDayOfWeek(int year, int month, int day)
    {
        int[] rtn = new int[6];
        int week = 0;
        long longDate = 0;

        java.util.Date date = null;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);

        week = calendar.get(Calendar.DAY_OF_WEEK);
        longDate = (calendar.getTime()).getTime() - (60 * 1000 * 60 * 24 * (week - 1));
        date = new java.util.Date(longDate);
        calendar1.setTime(date);

        rtn[0] = calendar1.get(Calendar.YEAR);
        rtn[1] = calendar1.get(Calendar.MONTH) + 1;
        rtn[2] = calendar1.get(Calendar.DATE);

        longDate = (calendar.getTime()).getTime() + (60 * 1000 * 60 * 24 * (7 - week));
        date = new java.util.Date(longDate);
        calendar2.setTime(date);
        rtn[3] = calendar2.get(Calendar.YEAR);
        rtn[4] = calendar2.get(Calendar.MONTH) + 1;
        rtn[5] = calendar2.get(Calendar.DATE);

        return rtn;
    }

    
    /*********************************************************/
    //����Ϊ���ݿ�ʹ�õ����ڷ���,Timestamp ,java.sql.Date
    /*********************************************************/

    /** ���ص�ǰʱ���Timestamp */
    public static Timestamp nowTimestamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    /** ���شӵ��տ�ʼ��Timestamp */
    public static Timestamp getDayStart(Timestamp stamp)
    {
        return getDayStart(stamp, 0);
    }

    /** ���ض������ʼ��Timestamp */
    public static Timestamp getDayStart(Timestamp stamp, int daysLater)
    {
        Calendar tempCal = Calendar.getInstance();

        tempCal.setTime(new java.util.Date(stamp.getTime()));
        tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal
            .get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
        return new Timestamp(tempCal.getTime().getTime());
    }

    /** ������һ�쿪ʼ��Timestamp */
    public static Timestamp getNextDayStart(Timestamp stamp)
    {
        return getDayStart(stamp, 1);
    }

    /** ���شӵ��ս�����Timestamp */
    public static Timestamp getDayEnd(Timestamp stamp)
    {
        return getDayEnd(stamp, 0);
    }

    /** ���شӶ����պ������Timestamp */
    public static Timestamp getDayEnd(Timestamp stamp, int daysLater)
    {
        Calendar tempCal = Calendar.getInstance();

        tempCal.setTime(new java.util.Date(stamp.getTime()));
        tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal
            .get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
        return new Timestamp(tempCal.getTime().getTime());
    }

    /**
     * String��java.sql.Date��ת�� ��׼��ʽ:YYYY-MM-DD
     * 
     * @param date The date String
     * @return java.sql.Date
     */
    public static java.sql.Date toSqlDate(String date)
    {
        java.util.Date newDate = toDate(date, "00:00:00");

        if (newDate == null)
            return null;
            
        return new java.sql.Date(newDate.getTime());
    }

    /**
     * ����java.sql.Date,ͨ������year, month, day
     * 
     * @param yearStr ��
     * @param monthStr ��
     * @param dayStr ��
     * @return A java.sql.Date
     */
    public static java.sql.Date toSqlDate(String yearStr, String monthStr, String dayStr)
    {
        java.util.Date newDate = toDate(yearStr, monthStr, dayStr, "0", "0", "0");

        if (newDate == null)
            return null;
        
        return new java.sql.Date(newDate.getTime());
    }

    /**
     * ����java.sql.Date,ͨ������year, month, day
     * 
     * @param year ��
     * @param month ��
     * @param day ��
     * @return A java.sql.Date
     */
    public static java.sql.Date toSqlDate(int year, int month, int day)
    {
        java.util.Date newDate = toDate(year, month, day, 0, 0, 0);

        if (newDate == null)
            return null;
        
        return new java.sql.Date(newDate.getTime());  
    }

    /**
     * ת��String �� java.sql.Time,��ʽ:"HH:MM:SS"
     * 
     * @param time The time String
     * @return A java.sql.Time
     */
    public static java.sql.Time toSqlTime(String time)
    {
        java.util.Date newDate = toDate("1970-1-1", time);

        if (newDate == null)
            return null;
        
        return new java.sql.Time(newDate.getTime());           
    }

    /**
     * ���� java.sql.Time ͨ������ʱ,��,��
     * 
     * @param hourStr ʱ
     * @param minuteStr ��
     * @param secondStr ��
     * @return A java.sql.Time
     */
    public static java.sql.Time toSqlTime(String hourStr, String minuteStr, String secondStr)
    {
        java.util.Date newDate = toDate("0", "0", "0", hourStr, minuteStr, secondStr);

        if (newDate == null)
            return null;
        
        return new java.sql.Time(newDate.getTime());
    }

    /**
     * ���� java.sql.Time ͨ������ʱ,��,��
     * 
     * @param hour int ʱ
     * @param minute int ��
     * @param second ��
     * @return A java.sql.Time
     */
    public static java.sql.Time toSqlTime(int hour, int minute, int second)
    {
        java.util.Date newDate = toDate(0, 0, 0, hour, minute, second);

        if (newDate == null)
            return null;
        
        return new java.sql.Time(newDate.getTime());
    }

    /**
     * ת��String �� java.sql.Timestamp,��ʽ:"YYYY-MM-DD HH:MM:SS"
     * 
     * @param dateTime ��ʽ:"YYYY-MM-DD HH:MM:SS"
     * @return Timestamp
     */
    public static Timestamp toTimestamp(String dateTime)
    {
        java.util.Date newDate = toDate(dateTime);

        if (newDate != null)
            return null;
        
        return new Timestamp(newDate.getTime());
    }

    /**
     * ת��String �� java.sql.Timestamp,��ʽ:"YYYY-MM-DD HH:MM:SS"
     * 
     * @param date The date String: YYYY-MM-DD
     * @param time The time String: HH:MM:SS
     * @return Timestamp
     */
    public static Timestamp toTimestamp(String date, String time)
    {
        java.util.Date newDate = toDate(date, time);

        if (newDate == null)
            return null;
        
        return new Timestamp(newDate.getTime());
    }

    /**
     * ���� Timestamp ͨ��������,��,��,ʱ,��,��
     * 
     * @param yearStr ��
     * @param monthStr ��
     * @param dayStr ��
     * @param hourStr ʱ
     * @param minuteStr ��
     * @param secondStr T��
     * @return Timestamp
     */
    public static Timestamp toTimestamp(String yearStr, String monthStr, String dayStr, 
        String hourStr, String minuteStr, String secondStr)
    {
        java.util.Date newDate = toDate(yearStr, monthStr, dayStr, hourStr, minuteStr, secondStr);

        if (newDate != null)
            return null;
        
        return new Timestamp(newDate.getTime());           
    }

    /**
     * ���� Timestamp ͨ��������,��,��,ʱ,��,��
     * 
     * @param year �� int
     * @param month �� int
     * @param day �� int
     * @param hour ʱ int
     * @param minute �� int
     * @param second �� int
     * @return Timestamp
     */
    public static Timestamp toTimestamp(int year, int month, int day, int hour,
        int minute, int second)
    {
        java.util.Date newDate = toDate(year, month, day, hour, minute, second);

        if (newDate != null)
            return null;
        
        return new Timestamp(newDate.getTime());
    }
    
    
    /**
     * �ж��ַ����Ƿ�����ȷ��ʱ���ʽ
     */
    public static boolean isValidDate(String str,String format){
        
        if(str!=null&&str.length()>0){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        
        try {
             dateFormat.parse(str);          
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
        }
        return false;
    }
}
