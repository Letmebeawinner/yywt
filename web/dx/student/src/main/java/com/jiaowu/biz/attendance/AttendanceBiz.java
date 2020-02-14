package com.jiaowu.biz.attendance;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.userWorkDayData.UserWorkDayDataBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.common.StudentCommonConstants;
import com.jiaowu.entity.sysuser.Employee;
import com.jiaowu.entity.sysuser.SysUser;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.user.UserCondition;
import com.jiaowu.entity.userWorkDayData.UserWorkDayData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.jiaowu.biz.common.WorkSourceService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.dao.attendance.AttendanceDao;
import com.jiaowu.entity.attendance.Attendance;
import com.jiaowu.entity.course.CourseArrange;

import javax.servlet.http.HttpServletRequest;

@Service
public class AttendanceBiz extends BaseBiz<Attendance, AttendanceDao> {
    /*@Autowired
    private WorkSourceService workSourceService;*/
    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    private UserWorkDayDataBiz userWorkDayDataBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private HrHessianService hrHessianService;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 每天根据排课信息和打卡记录算出每天的考勤记录
     */
    public void dailySumAttendance() {
        List<CourseArrange> courseArrangeList = getYesterdayCourseArrange();
        if (courseArrangeList != null && courseArrangeList.size() > 0) {
//			List<Map<String,String>> 
        }
    }

    public Map<String, Date> getStartTimeAndEndTime() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date endTime = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        Date startTime = calendar.getTime();
        Map<String, Date> map = new HashMap<String, Date>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return map;
    }

    public List<CourseArrange> getYesterdayCourseArrange() {
        Map<String, Date> dateMap = getStartTimeAndEndTime();
        Date startTime = dateMap.get("startTime");
        Date endTime = dateMap.get("endTime");
        return courseArrangeBiz.find(null, " status=1 and startTime>'" + startTime + "' and startTime<'" + endTime + "'");
    }

    /**
     * 获取今天work_day_data数据
     *
     * @return
     */
    public String getTodayWorkDayData() {
//		Calendar calendar=Calendar.getInstance();
//		calendar.setTime(new Date());
//		calendar.set(Calendar.DAY_OF_YEAR,-9);
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String whereSql=" where Work_Source.Date like '"+bartDateFormat.format(new Date()).trim()+"'";
//        String whereSql=" where Date like '2018-11-08'";
//        String whereSql = " where Work_Day_Data.Work_Date >= '2017-09-07'";
//		String whereSql=" where 1=1 ";
//        String whereSql = " where Work_Day_Data.cID >= 17359";
        HttpURLConnection connection = null;
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("whereSql", "UTF-8"), URLEncoder.encode(whereSql, "UTF-8")));
            System.out.println(builder.toString());
            /*for (String key : params.keySet()) {
				String val="xmlValues".equalsIgnoreCase(key)? URLEncoder.encode(params.get(key), "UTF-8"):params.get(key);
				builder.append(String.format("%1$s=%2$s&", key,  URLEncoder.encode(val, "UTF-8")));
			}*/
            connection = (HttpURLConnection) new URL(StudentCommonConstants.YICARDTONG_PATH + "/yikatong/workDayDataList.json").openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
            out.write(builder.toString());
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            builder.setLength(0);
            String line = "";
            while ((line = reader.readLine()) != null)
                builder.append(line);
            reader.close();
//			System.out.println("回应数据 :" + builder);
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    /**
     * 解析work_day_data数据
     *
     * @param result
     * @return
     */
    public List<Map<String, Object>> parseWorkDayData(String result) {
        if (result == null || result.equals("")) {
            return null;
        }
        Gson gson = new Gson();
        Map<String, Object> all = gson.fromJson(result, Map.class);
        String code = all.get("code").toString();
        if (code == null || !code.equals("0")) {
            return null;
        }
        List<Map<String, Object>> data = (List<Map<String, Object>>) all.get("data");
        return data;
    }

    /**
     * 定时获取work_day_data数据，将数据添加到数据库中。
     */
    public void autoAddWorkDayData(){
        try {
            System.out.println("++++++++++++++++定时器执行");
            //先清空今天旧的记录
            SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<UserWorkDayData> toDayList = userWorkDayDataBiz.find(null, " workDate like '" + bartDateFormat.format(new Date()).trim() + "'");
            List<Long> ids = toDayList.stream().map(UserWorkDayData::getId).collect(Collectors.toList());
            userWorkDayDataBiz.deleteByIds(ids);


            String sql=" to_days(startTime) = to_days(now()) order by startTime asc";
            //今日课程安排
            List<CourseArrange> courseList=courseArrangeBiz.find(null,sql);
            if(courseList==null || courseList.size()==0)return;//没课直接返回

            Map<Long,List<CourseArrange>> courseMap=courseList.stream().collect(Collectors.groupingBy(e->e.getClassId()));

            String classIds=courseList.stream().map(courseArrange -> courseArrange.getClassId().toString()).distinct()
                    .collect(Collectors.joining(",", "(", ")"));

            List<User> userList=userBiz.find(null,"classId in("+classIds+")");
            if(userList==null || userList.size()==0)return;//没人上课直接返回

            //获取今日打卡记录
            List<Map<String, Object>> workDayDataList = parseWorkDayData(getTodayWorkDayData());
            Map<String,List<Map<String, Object>>> workDayDataMap=new HashMap<>();
            if(workDayDataList!=null && workDayDataList.size()>0) {
                workDayDataMap=workDayDataList.stream().collect(Collectors.groupingBy(e->e.get("cCardNO").toString()));
            }
            //上午下午分界点为1点
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DAY_OF_MONTH,0);
            calendar.set(Calendar.HOUR_OF_DAY,13);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            Date dividingTime = calendar.getTime();

            Date date=new Date();
            SimpleDateFormat formatTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatDate=new SimpleDateFormat("yyyy-MM-dd");
            String workDate=formatDate.format(date);

            for(User u : userList){
                //今日本人打卡记录
                List<Map<String, Object>> userDataList=workDayDataMap.get(u.getTimeCardNo().toUpperCase());
                //今日本人课程安排
                List<CourseArrange> thisCourseList=courseMap.get(u.getClassId());

                //上午上课时间
                Date amFirst=null;
                //上午下课时间
                Date amLast=null;
                //下午上课时间
                Date pmFirst=null;
                //下午下课时间
                Date pmLast=null;
                if(ObjectUtils.isNotNull(thisCourseList)){//有课程安排
//                    System.out.println("++++++++++++++++学员："+u.getName()+"有排课数量："+courseList.size());
                    for(CourseArrange course : thisCourseList){
//                        System.out.println("++++++++++++++++进入课程循环："+course.getCourseName());
                        if(course.getStartTime().before(dividingTime)
                                && (amFirst==null || amFirst.after(course.getStartTime()))){ //上午最早的一节课 上课
                            amFirst=course.getStartTime();
                        }
                        if(course.getStartTime().before(dividingTime)
                                && (amLast==null || amLast.before(course.getEndTime()))){ //上午最晚的一节课 下课
                            amLast=course.getEndTime();
                        }
                        if(course.getStartTime().after(dividingTime)
                                && (pmFirst==null || pmFirst.after(course.getStartTime()))){ //下午最早的一节课 上课
                            pmFirst=course.getStartTime();
                        }
                        if(course.getStartTime().after(dividingTime)
                                && (pmLast==null || pmLast.before(course.getEndTime()))){ // 下午最晚的一节课 下课
                            pmLast=course.getEndTime();
                        }
                    }
                }
//                System.out.println("++++++++++++++++上午上课时间："+amFirst);
//                System.out.println("++++++++++++++++上午下课时间："+amLast);
//                System.out.println("++++++++++++++++下午上课时间："+pmFirst);
//                System.out.println("++++++++++++++++下午上课时间："+pmLast);

                //上午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工，5代表加班(无)。
                Integer morningAttendanceStatus=4;
                if(amFirst!=null && amLast!=null){//上午有课
                    if(ObjectUtils.isNotNull(userDataList)){
                        boolean before=false;//上课前打卡
                        boolean center=false;//上课中打卡
                        boolean after=false;//上课后打卡
                        for(Map<String, Object> map : userDataList){
                            Date thisTime=formatTime.parse(map.get("SourceDate").toString());//本次打卡时间
                            if(!thisTime.after(amFirst)){// 上午上课前有打卡
                                before=true;
                            }
                            if(thisTime.after(amFirst) && thisTime.before(amLast)){// 上午上课中有打卡
                                center=true;
                            }
                            if(!thisTime.before(amLast) && thisTime.before(dividingTime)){// 上午下课后有打卡
                                after=true;
                            }
                        }
//                        System.out.println("++++++++++++++++上午上课前有打卡："+before);
//                        System.out.println("++++++++++++++++上午上课中有打卡："+center);
//                        System.out.println("++++++++++++++++上午上课后有打卡："+after);
                        if(before){
                            if(after){
                                morningAttendanceStatus=1;
                            }else {
                                morningAttendanceStatus=3;
                            }
                        }else {
                            if(center || after){
                                morningAttendanceStatus=2;
                            }else {
                                morningAttendanceStatus=4;
                            }
                        }
                    }else {//有课没打卡 旷课
                        morningAttendanceStatus=4;
                    }
                }else { //没课直接正常
                    morningAttendanceStatus=1;
                }
                //下午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工，5代表加班(无)。
                Integer afternoonAttendanceStatus=4;
                if(pmFirst!=null && pmLast!=null){//下午有课
                    if(ObjectUtils.isNotNull(userDataList)){
                        boolean before=false;//下课前打卡
                        boolean center=false;//下课中打卡
                        boolean after=false;//下课后打卡
                        for(Map<String, Object> map : userDataList){
                            Date thisTime=formatTime.parse(map.get("SourceDate").toString());
                            if(!thisTime.after(pmFirst) && thisTime.after(dividingTime)){// 下午上课前有打卡
                                before=true;
                            }
                            if(thisTime.after(pmFirst) && thisTime.before(pmLast)){// 下午上课中有打卡
                                center=true;
                            }
                            if(!thisTime.before(pmLast)){// 下午下课后有打卡
                                after=true;
                            }
                        }
                        if(before){
                            if(after){
                                afternoonAttendanceStatus=1;
                            }else {
                                afternoonAttendanceStatus=3;
                            }
                        }else {
                            if(center || after){
                                afternoonAttendanceStatus=2;
                            }else {
                                afternoonAttendanceStatus=4;
                            }
                        }
                    }else {//有课没打卡 旷课
                        afternoonAttendanceStatus=4;
                    }
                }else {
                    afternoonAttendanceStatus=1;
                }
                System.out.println("++++++++++++++++上午状态："+morningAttendanceStatus);
                System.out.println("++++++++++++++++下午状态："+afternoonAttendanceStatus);

                if(amFirst!=null || pmFirst!=null){
                    UserWorkDayData data = new UserWorkDayData();
                    data.setMorningAttendanceStatus(morningAttendanceStatus);
                    data.setAfternoonAttendanceStatus(afternoonAttendanceStatus);
                    data.setUserId(u.getId());
                    data.setClassId(u.getClassId());
                    data.setClassTypeId(u.getClassTypeId());
                    data.setUserName(u.getName().replace(" ",""));
                    data.setClassName(u.getClassName());
                    data.setClassTypeName(u.getClassTypeName());
                    data.setType(1);
                    data.setWorkDate(workDate);
                    if(ObjectUtils.isNotNull(userDataList)){
                        data.setPerId(userDataList.get(0).get("PerID").toString());
                        data.setCId(userDataList.get(0).get("cID").toString());
                    }
                    data.setTimeCardNo(u.getTimeCardNo().toUpperCase());
                    System.out.println("++++++++++++++++进入添加");
                    userWorkDayDataBiz.save(data);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("autoAddWorkDayData()--error"+e);
        }

    }

    /**
     * 判断时间是否在时间段内
     * 
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
            return true;
        } else {
            return false;
        }
    }


    public List<File> getExcelWorkDayDataStatisticsList(UserCondition userCondition, HttpServletRequest request, String dir, String[] headName, String expName) throws Exception {
		/*Pagination pagination = new Pagination();
		pagination.setPageSize(10000);
		String whereSql=addCondition(request);
		pagination.setRequest(request);
		find(pagination,whereSql);
		int num = pagination.getTotalPages();// 总页数
		List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
		for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
			pagination.setCurrentPage(i);
			List<User> userList = find(pagination,whereSql);
			List<List<String>> list=convert(userList);
			File file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
			srcfile.add(file);
		}
		return srcfile;*/
        StringBuffer whereSql = new StringBuffer(" status=1 and !isnull(perId)");
        if (userCondition.getClassTypeId() != null && !userCondition.getClassTypeId().equals(0L)) {
            whereSql.append(" and classTypeId=" + userCondition.getClassTypeId());
        }
        if (userCondition.getClassId() != null && !userCondition.getClassId().equals(0L)) {
            whereSql.append(" and classId=" + userCondition.getClassId());
        }
        if (!StringUtils.isTrimEmpty(userCondition.getUserName())) {
            whereSql.append(" and name like '%" + userCondition.getUserName() + "%'");
        }
        List<User> userList = userBiz.find(null, whereSql + " order by unitId");
        List<List<String>> statisticsList = new LinkedList<List<String>>();
        if (userList != null && userList.size() > 0) {
            int normal = 0;
            int late = 0;
            int leaveEarly = 0;
            int absenteeism = 0;
            StringBuffer userWorkDayDateCondition = new StringBuffer("");
            if (userCondition.getStartTime() != null) {
                userWorkDayDateCondition.append(" and createTime>='" + sdf.format(userCondition.getStartTime()) + "'");
            }
            if (userCondition.getEndTime() != null) {
                userWorkDayDateCondition.append(" and createTime<='" + sdf.format(userCondition.getEndTime()) + "'");
            }
            for (User user : userList) {
                normal = 0;
                late = 0;
                leaveEarly = 0;
                absenteeism = 0;
                List<String> statistics = new LinkedList<String>();
				/*statistics.put("classTypeName",user.getClassTypeName());
				statistics.put("className",user.getClassName());
				statistics.put("userName",user.getName());*/
                statistics.add(user.getClassTypeName());
                statistics.add(user.getClassName());
                statistics.add(user.getName());
                List<UserWorkDayData> userWorkDayDataList = userWorkDayDataBiz.find(null, " userId=" + user.getId() + userWorkDayDateCondition.toString());
                if (userWorkDayDataList != null && userWorkDayDataList.size() > 0) {
                    for (UserWorkDayData userWorkDayData : userWorkDayDataList) {
                        switch (userWorkDayData.getMorningAttendanceStatus()) {
                            case 1:
                                normal++;
                                break;
                            case 2:
                                late++;
                                break;
                            case 3:
                                leaveEarly++;
                                break;
                            case 4:
                                absenteeism++;
                                break;
                        }
                        switch (userWorkDayData.getAfternoonAttendanceStatus()) {
                            case 1:
                                normal++;
                                break;
                            case 2:
                                late++;
                                break;
                            case 3:
                                leaveEarly++;
                                break;
                            case 4:
                                absenteeism++;
                                break;
                        }
                    }
                }
				/*statistics.put("normal",normal);
				statistics.put("late",late);
				statistics.put("leaveEarly",leaveEarly);
				statistics.put("absenteeism",absenteeism);*/
                statistics.add(normal + "");
                statistics.add(late + "");
                statistics.add(leaveEarly + "");
                statistics.add(absenteeism + "");
                statisticsList.add(statistics);
            }
        }
        List<File> srcfile = new ArrayList<File>();
        File file = FileExportImportUtil.createExcel(headName, statisticsList, expName, dir);
        srcfile.add(file);
        return srcfile;
    }


    /**
     * 定时获取work_day_data数据，将数据添加到数据库中。
     */
    public void checkData() {
        List<UserWorkDayData> workDayDataList = userWorkDayDataBiz.find(null, " 1=1");
        for (UserWorkDayData data : workDayDataList) {
            //上午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工。
            Integer morningAttendanceStatus;
            //下午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工。
            Integer afternoonAttendanceStatus;


            if (data.getCMemo() != null) {
                String cMemo = data.getCMemo();
                int morningStart = cMemo.indexOf("第一时间段");
                int afternoonStart = cMemo.indexOf("第二时间段");
                String morningText = null;
                String afternoonText = null;
                if (morningStart >= 0 && afternoonStart >= 0) {
                    morningText = cMemo.substring(morningStart + 5, afternoonStart);
                    afternoonText = cMemo.substring(afternoonStart + 5, cMemo.length());
                } else if (morningStart >= 0 && afternoonStart < 0) {
                    morningText = cMemo.substring(morningStart + 5, cMemo.length());
                } else if (morningStart < 0 && afternoonStart >= 0) {
                    afternoonText = cMemo.substring(afternoonStart + 5, cMemo.length());
                } else {

                }
                if (morningText != null && !morningText.equals("")) {
                    if (morningText.contains("迟到")) {
                        morningAttendanceStatus = 2;
                    } else if (morningText.contains("早退")) {
                        morningAttendanceStatus = 3;
                    } else if (morningText.contains("旷工")) {
                        morningAttendanceStatus = 4;
                    } else if (morningText.contains("加班")) {
                        morningAttendanceStatus = 5;
                    } else {
                        morningAttendanceStatus = 1;
                    }
                } else {
                    morningAttendanceStatus = 1;
                }
                if (afternoonText != null && !afternoonText.equals("")) {
                    if (afternoonText.contains("迟到")) {
                        afternoonAttendanceStatus = 2;
                    } else if (afternoonText.contains("早退")) {
                        afternoonAttendanceStatus = 3;
                    } else if (afternoonText.contains("旷工")) {
                        afternoonAttendanceStatus = 4;
                    } else if (afternoonText.contains("加班")) {
                        afternoonAttendanceStatus = 5;
                    } else {
                        afternoonAttendanceStatus = 1;
                    }
                } else {
                    afternoonAttendanceStatus = 1;
                }
            } else {
                morningAttendanceStatus = 1;
                afternoonAttendanceStatus = 1;
            }

            data.setMorningAttendanceStatus(morningAttendanceStatus);
            data.setAfternoonAttendanceStatus(afternoonAttendanceStatus);
            userWorkDayDataBiz.update(data);
        }
    }
}
