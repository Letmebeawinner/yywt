package com.jiaowu.controller.attendance;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jiaowu.biz.attendance.AttendanceBiz;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.holiday.HolidayBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.userWorkDayData.UserWorkDayDataBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.common.StudentCommonConstants;
import com.jiaowu.dao.userWorkDayData.UserWorkDayDataDao;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.holiday.Holiday;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.user.UserCondition;
import com.jiaowu.entity.userWorkDayData.UserWorkDayData;
import com.jiaowu.entity.userWorkDayData.UserWorkDayDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 考勤Controller
 *
 * @author 李帅雷
 */
@Controller
public class AttendanceController extends BaseController {
    /*@Autowired
    private WorkSourceService workSourceService;*/
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final String ADMIN_PREFIX = "/admin/jiaowu/attendance";
    private static Logger logger = LoggerFactory
            .getLogger(AttendanceController.class);
    @Autowired
    private AttendanceBiz attendanceBiz;
    @Autowired
    private ClassTypeBiz classTypeBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserWorkDayDataBiz userWorkDayDataBiz;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private HolidayBiz holidayBiz;
    @Autowired
    private UserWorkDayDataDao userWorkDayDataDao;

    @InitBinder({"userCondition"})
    public void initUserCondition(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("userCondition.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 考勤列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/attendanceList")
    public String attendanceList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("userCondition") UserCondition userCondition) {
        try {
            List<ClassType> classTypeList = classTypeBiz.find(null, " status=1");
            request.setAttribute("classTypeList", classTypeList);

            List<User> userList = null;
            pagination.setRequest(request);
            Map<String, Object> map = getOnePageData(parseAttendanceData(getAttendanceData(userCondition)), pagination);
            List<Map<String, Object>> attendanceList = (List<Map<String, Object>>) map.get("data");
            if (attendanceList != null && attendanceList.size() > 0) {
                for (Map<String, Object> attendance : attendanceList) {
                    String perId = attendance.get("Per_ID").toString();
                    userList = userBiz.find(null, " status=1 and perId ='" + perId + "'");
                    if (userList != null && userList.size() > 0) {
                        attendance.put("classTypeName", userList.get(0).getClassTypeName());
                        attendance.put("className", userList.get(0).getClassName());
                        attendance.put("userName", userList.get(0).getName());
                    }
                }
            }
            pagination = (Pagination) map.get("pagination");
            request.setAttribute("attendanceList", attendanceList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("userCondition", userCondition);
//			request.setAttribute("xinDe",xinDe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/attendance/attendance_list";
    }

    /**
     * 获取考勤原始数据
     *
     * @return
     */
    public String getAttendanceData(UserCondition userCondition) {
        StringBuffer whereSql = new StringBuffer(" where 1=1");
        if (!StringUtils.isTrimEmpty(userCondition.getPerId())) {
            whereSql.append(" and Per_ID like '" + userCondition.getPerId() + "'");
        }
        /*if(!StringUtils.isTrimEmpty(userCondition.getClassTypeId())){

		}*/
        HttpURLConnection connection = null;
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("whereSql", "UTF-8"), URLEncoder.encode(whereSql.toString(), "UTF-8")));
            System.out.println(builder.toString());
            /*for (String key : params.keySet()) {
                String val="xmlValues".equalsIgnoreCase(key)? URLEncoder.encode(params.get(key), "UTF-8"):params.get(key);
				builder.append(String.format("%1$s=%2$s&", key,  URLEncoder.encode(val, "UTF-8")));
			}*/
            connection = (HttpURLConnection) new URL(StudentCommonConstants.YICARDTONG_PATH + "/yikatong/showAttendListWithCondition.json").openConnection();
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
     * 解析考勤数据
     *
     * @param result
     * @return
     */
    public List<Map<String, Object>> parseAttendanceData(String result) {
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
     * 获取一页的数据
     *
     * @param all
     * @param pagination
     * @return
     */
    public Map<String, Object> getOnePageData(List<Map<String, Object>> all, Pagination pagination) {
        if (all == null || all.size() == 0) {
            return null;
        }
        pagination.setTotalCount(all.size());
        pagination.setTotalPages(all.size() % pagination.getPageSize() == 0 ? all.size() / pagination.getPageSize() : all.size() / pagination.getPageSize() + 1);
        int begin = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
        int end = pagination.getCurrentPage() * pagination.getPageSize();
        Map<String, Object> map = new HashMap<>(16);
        map.put("data", all.subList(begin, end > all.size() ? all.size() : end));
        map.put("pagination", pagination);
        return map;
    }

    /**
     * 考勤列表
     *
     * @param request
     * @param pagination
     * @param userCondition
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/workDayDataList")
    public String workDayDataList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("userCondition") UserCondition userCondition) {
        try {

            List<ClassType> classTypeList = classTypeBiz.find(null, " status=1 order by id desc");
            request.setAttribute("classTypeList", classTypeList);

            List<UserWorkDayData> userWorkDayDataList;
            StringBuilder whereSql = new StringBuilder(" userName is not null and userName!=''  and type=1 ");
            if (userCondition.getClassTypeId() != null && !userCondition.getClassTypeId().equals(0L)) {
                whereSql.append(" and classTypeId=").append(userCondition.getClassTypeId());
            }
            if (userCondition.getClassId() != null && !userCondition.getClassId().equals(0L)) {
                whereSql.append(" and classId=").append(userCondition.getClassId());
            }
            if (!StringUtils.isTrimEmpty(userCondition.getUserName())) {
                whereSql.append(" and userName like '%").append(userCondition.getUserName()).append("%'");
            }
            if (userCondition.getMorningAttendanceStatus() != null && !userCondition.getMorningAttendanceStatus().equals(0)) {
                whereSql.append(" and morningAttendanceStatus=").append(userCondition.getMorningAttendanceStatus());
            }
            if (userCondition.getAfternoonAttendanceStatus() != null && !userCondition.getAfternoonAttendanceStatus().equals(0)) {
                whereSql.append(" and afternoonAttendanceStatus=").append(userCondition.getAfternoonAttendanceStatus());
            }
            SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (ObjectUtils.isNotNull(userCondition.getStartTime())) {
                whereSql.append(" and  TO_DAYS(workDate) >= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getStartTime()).trim()).append("')");
            }
            if (ObjectUtils.isNotNull(userCondition.getEndTime())) {
                whereSql.append(" and  TO_DAYS(workDate) <= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getEndTime()).trim()).append("')");
            }
            whereSql.append(" order by TO_DAYS(workDate) desc");
            pagination.setRequest(request);
            userWorkDayDataList = userWorkDayDataBiz.find(pagination, whereSql.toString());
            request.setAttribute("userWorkDayDataList", userWorkDayDataList);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/attendance/workDayData_list";
    }

    @RequestMapping("/app/attendancetest")
    public void attendancetest() {
        try {
            attendanceBiz.autoAddWorkDayData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 考勤统计
     *
     * @param request
     * @param pagination
     * @param userCondition
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/workDayDataStatistics")
    public String workDayDataStatistics(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("userCondition") UserCondition userCondition) {
        try {
            List<ClassType> classTypeList = classTypeBiz.find(null, " status=1 order by id desc");
            request.setAttribute("classTypeList", classTypeList);

            StringBuffer whereSql = new StringBuffer(" status in(1,4,7,8)");
            if (userCondition.getClassTypeId() != null && !userCondition.getClassTypeId().equals(0L)) {
                whereSql.append(" and classTypeId=" + userCondition.getClassTypeId());
            }
            if (userCondition.getClassId() != null && !userCondition.getClassId().equals(0L)) {
                whereSql.append(" and classId=" + userCondition.getClassId());
            }
            if (!StringUtils.isTrimEmpty(userCondition.getUserName())) {
                whereSql.append(" and name like '%" + userCondition.getUserName() + "%'");
            }
            pagination.setRequest(request);
            logger.info("whereSql====" + whereSql);
            List<User> userList = userBiz.find(pagination, whereSql + " order by unitId");
            logger.info("userList====" + userList);
            List<Map<String, Object>> statisticsList = new LinkedList<Map<String, Object>>();
            if (userList != null && userList.size() > 0) {
                int normal = 0;
                int late = 0;
                int leaveEarly = 0;
                int absenteeism = 0;
                for (User user : userList) {
                    normal = 0;
                    late = 0;
                    leaveEarly = 0;
                    absenteeism = 0;
                    Map<String, Object> statistics = new HashMap<String, Object>();
                    statistics.put("classTypeName", user.getClassTypeName());
                    statistics.put("className", user.getClassName());
                    statistics.put("userName", user.getName());
                    boolean hasNormal = false;
                    boolean hasLate = false;
                    boolean hasLeaveEarly = false;
                    boolean hasAbsenteeism = false;
                    StringBuffer userWorkDayDateCondition = new StringBuffer(" 1=1");
                    if (userCondition.getStartTime() != null) {
                        userWorkDayDateCondition.append(" and createTime>='" + sdf.format(userCondition.getStartTime()) + "'");
                    }
                    if (userCondition.getEndTime() != null) {
                        userWorkDayDateCondition.append(" and createTime<='" + sdf.format(userCondition.getEndTime()) + "'");
                    }
                    userWorkDayDateCondition.append(" and classId="+user.getClassId());
                    userWorkDayDateCondition.append(" and type=1");
                    userWorkDayDateCondition.append(" and userId=" + user.getId());
                    userWorkDayDateCondition.append(" group by workDate");
                    List<UserWorkDayData> userWorkDayDataList = userWorkDayDataBiz.find(null, userWorkDayDateCondition.toString());
                    if (userWorkDayDataList != null && userWorkDayDataList.size() > 0) {
                        for (UserWorkDayData userWorkDayData : userWorkDayDataList) {
                            hasNormal = false;
                            hasLate = false;
                            hasLeaveEarly = false;
                            hasAbsenteeism = false;
                            if(ObjectUtils.isNotNull(userWorkDayData.getMorningAttendanceStatus())){
                                switch (userWorkDayData.getMorningAttendanceStatus()) {
                                    case 1:
                                        normal++;
                                        hasNormal = true;
                                        break;
                                    case 2:
                                        late++;
                                        hasLate = true;
                                        break;
                                    case 3:
                                        leaveEarly++;
                                        hasLeaveEarly = true;
                                        break;
                                    case 4:
                                        absenteeism++;
                                        hasAbsenteeism = true;
                                        break;
                                }
                            }
                            if(ObjectUtils.isNotNull(userWorkDayData.getAfternoonAttendanceStatus())){
                                switch (userWorkDayData.getAfternoonAttendanceStatus()) {
                                    case 1:
                                        if (!hasNormal) {
                                            normal++;
                                        }

                                        break;
                                    case 2:
                                    /*if(!hasLate){
                                        late++;
									}*/
                                        late++;
                                        hasLate = true;
                                        break;
                                    case 3:
                                    /*if(!hasLeaveEarly){
                                        leaveEarly++;
									}*/
                                        leaveEarly++;
                                        hasLeaveEarly = true;
                                        break;
                                    case 4:
                                        if (!hasAbsenteeism) {
                                            absenteeism++;
                                        }

                                        break;
                                }
                            }
                        }
                    }
                    statistics.put("normal", normal);
                    statistics.put("late", late);
                    statistics.put("leaveEarly", leaveEarly);
                    statistics.put("absenteeism", absenteeism);
                    List<Holiday> holidayList = holidayBiz.find(null, " userId=" + user.getId());
                    statistics.put("holiday", holidayList != null && holidayList.size() > 0 ? holidayList.size() : 0);
                    statisticsList.add(statistics);
                }
            }
            logger.info("statisticsList====" + statisticsList);
            request.setAttribute("statisticsList", statisticsList);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/attendance/workDayData_statistics";
    }

    /**
     * 导出考勤统计数据
     *
     * @param request
     */
    @RequestMapping(ADMIN_PREFIX + "/exportWorkDayDataStatistics")
    public void exportWorkDayDataStatistics(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userCondition") UserCondition userCondition) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/workDayDataStatisticsList");
            String expName = "考勤统计_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] headName = {"班型", "班次", "姓名", "正常次数", "迟到次数", "早退次数", "旷课次数"};
            List<File> srcfile = attendanceBiz.getExcelWorkDayDataStatisticsList(userCondition, request, dir, headName, expName);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 验证考勤统计导出
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 11:50
     * @return:
     */
    @RequestMapping(ADMIN_PREFIX + "/checkAttendanceStatistics")
    @ResponseBody
    public Map<String, Object> checkAttendanceStatistics(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userCondition") UserCondition userCondition) {
        try {
            StringBuilder whereSql = new StringBuilder(" userName is not null and userName!=''  and type=1 ");
            if (userCondition.getClassTypeId() != null && !userCondition.getClassTypeId().equals(0L)) {
                whereSql.append(" and classTypeId=").append(userCondition.getClassTypeId());
            }
            if (userCondition.getClassId() != null && !userCondition.getClassId().equals(0L)) {
                whereSql.append(" and classId=").append(userCondition.getClassId());
            }
            if (!StringUtils.isTrimEmpty(userCondition.getUserName())) {
                whereSql.append(" and userName like '%").append(userCondition.getUserName()).append("%'");
            }
            if (userCondition.getMorningAttendanceStatus() != null && !userCondition.getMorningAttendanceStatus().equals(0)) {
                whereSql.append(" and morningAttendanceStatus=").append(userCondition.getMorningAttendanceStatus());
            }
            if (userCondition.getAfternoonAttendanceStatus() != null && !userCondition.getAfternoonAttendanceStatus().equals(0)) {
                whereSql.append(" and afternoonAttendanceStatus=").append(userCondition.getAfternoonAttendanceStatus());
            }
            SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (ObjectUtils.isNotNull(userCondition.getStartTime())) {
                whereSql.append(" and  TO_DAYS(workDate) >= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getStartTime()).trim()).append("')");
            }
            if (ObjectUtils.isNotNull(userCondition.getEndTime())) {
                whereSql.append(" and  TO_DAYS(workDate) <= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getEndTime()).trim()).append("')");
            }
            whereSql.append(" order by TO_DAYS(workDate)");

            List<UserWorkDayData> dateStatisticsList = userWorkDayDataDao.getDateStatisticsList(whereSql.toString());
            if (ObjectUtils.isNull(dateStatisticsList)) {
                return resultJson(ErrorCode.ERROR_SYSTEM, "当前条件下暂无数据", null);
            } else {
                return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 考勤统计导出
     */
    @RequestMapping(ADMIN_PREFIX + "/attendanceStatistics")
    public void attendanceStatistics(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userCondition") UserCondition userCondition) {
        try {
            StringBuilder whereSql = new StringBuilder(" userName is not null and userName!=''  and type=1");
            StringBuilder dateWhereSql = new StringBuilder(" userName is not null and userName!=''  and type=1");
            StringBuilder nameWhereSql = new StringBuilder();
            if (userCondition.getClassTypeId() != null && !userCondition.getClassTypeId().equals(0L)) {
                whereSql.append(" and classTypeId=").append(userCondition.getClassTypeId());
                dateWhereSql.append(" and classTypeId=").append(userCondition.getClassTypeId());
                nameWhereSql.append(" and classTypeId=").append(userCondition.getClassTypeId());
            }
            if (userCondition.getClassId() != null && !userCondition.getClassId().equals(0L)) {
                whereSql.append(" and classId=").append(userCondition.getClassId());
                dateWhereSql.append(" and classId=").append(userCondition.getClassId());
                nameWhereSql.append(" and classId=").append(userCondition.getClassId());
            }
            if (!StringUtils.isTrimEmpty(userCondition.getUserName())) {
                whereSql.append(" and userName like '%").append(userCondition.getUserName()).append("%'");
                dateWhereSql.append(" and userName like '%").append(userCondition.getUserName()).append("%'");
            }
            if (userCondition.getMorningAttendanceStatus() != null && !userCondition.getMorningAttendanceStatus().equals(0)) {
                whereSql.append(" and morningAttendanceStatus=").append(userCondition.getMorningAttendanceStatus());
                dateWhereSql.append(" and morningAttendanceStatus=").append(userCondition.getMorningAttendanceStatus());
                nameWhereSql.append(" and morningAttendanceStatus=").append(userCondition.getMorningAttendanceStatus());
            }
            if (userCondition.getAfternoonAttendanceStatus() != null && !userCondition.getAfternoonAttendanceStatus().equals(0)) {
                whereSql.append(" and afternoonAttendanceStatus=").append(userCondition.getAfternoonAttendanceStatus());
                dateWhereSql.append(" and afternoonAttendanceStatus=").append(userCondition.getAfternoonAttendanceStatus());
                nameWhereSql.append(" and afternoonAttendanceStatus=").append(userCondition.getAfternoonAttendanceStatus());
            }
            SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (ObjectUtils.isNotNull(userCondition.getStartTime())) {
                whereSql.append(" and  TO_DAYS(workDate) >= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getStartTime()).trim()).append("')");
                dateWhereSql.append(" and  TO_DAYS(workDate) >= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getStartTime()).trim()).append("')");
                nameWhereSql.append(" and  TO_DAYS(workDate) >= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getStartTime()).trim()).append("')");
            }
            if (ObjectUtils.isNotNull(userCondition.getEndTime())) {
                whereSql.append(" and  TO_DAYS(workDate) <= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getEndTime()).trim()).append("')");
                dateWhereSql.append(" and  TO_DAYS(workDate) <= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getEndTime()).trim()).append("')");
                nameWhereSql.append(" and  TO_DAYS(workDate) <= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getEndTime()).trim()).append("')");
            }
            whereSql.append(" GROUP BY userName  order by TO_DAYS(workDate)");
            dateWhereSql.append(" order by TO_DAYS(workDate)");

            String dir = request.getSession().getServletContext().getRealPath("/FileList");
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            String headName = df.format(new Date());
            // 文件名
            String expName = "考勤统计表_" + headName;
            //表格头部
            List<String> tableTitles = new ArrayList<>();
            tableTitles.add("序号");
            tableTitles.add("姓名");
            //统计月份
            List<UserWorkDayData> dateStatisticsList = userWorkDayDataDao.getDateStatisticsList(dateWhereSql.toString());
            if (ObjectUtils.isNull(dateStatisticsList)) {
                return;
            }
            dateStatisticsList.forEach(t -> {
                tableTitles.add(t.getWorkDate() + "\r\n上午");
                tableTitles.add(t.getWorkDate() + "\r\n下午");
            });
            //上午
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n上午正常（次）");
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n上午迟到（次）");
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n上午早退（次）");
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n上午旷工（次）");
            //下午
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n下午正常（次）");
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n下午迟到（次）");
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n下午早退（次）");
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n下午旷工（次）");
            //合计
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n合计正常（次）");
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n合计迟到（次）");
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n合计早退（次）");
            tableTitles.add(this.getDate(dateStatisticsList.get(0).getWorkDate()) + "\r\n-" + this.getDate(dateStatisticsList.get(dateStatisticsList.size() - 1).getWorkDate()) + "\r\n合计旷工（次）");
            List<Map<String, Object>> dateList = new ArrayList<>();
            //查询打卡人员
            List<UserWorkDayData> userWorkDayDatas = userWorkDayDataDao.getDatePersonnelList(whereSql.toString());
            int i = 1;
            for (UserWorkDayData userWorkDayData : userWorkDayDatas) {
                if (ObjectUtils.isNotNull(userWorkDayData.getUserName())) {
                    Map map = new HashMap();
                    map.put("number", i++);//序号
                    map.put("name", userWorkDayData.getUserName());//姓名
                    List<UserWorkDayData> workDayDatas = userWorkDayDataBiz.find(null, " 1=1 and userName=" + "\'" + userWorkDayData.getUserName() + "\' " + nameWhereSql + " order by createTime desc");
                    for (UserWorkDayData date : workDayDatas) {
                        List<Map<String, String>> mapList = new ArrayList<>();
                        Map treeMap = new HashMap();
                        //根据日期查询
                        treeMap.put("am", this.getStatus(date.getMorningAttendanceStatus()));//上午
                        treeMap.put("pm", this.getStatus(date.getMorningAttendanceStatus()));//下午
                        mapList.add(treeMap);
                        map.put(date.getWorkDate(), mapList);
                    }
                    //统计时间段
                    Map<String, String> am = this.getStatistics(workDayDatas, "am");
                    Map<String, String> pm = this.getStatistics(workDayDatas, "pm");
                    //上午
                    map.put("amOutTime", am.get("amOutTime"));//正常
                    map.put("amRetreat", am.get("amRetreat"));//迟到
                    map.put("amLeave", am.get("amLeave"));//早退
                    map.put("amAbsenteeism", am.get("amAbsenteeism"));//旷工
                    //下午
                    map.put("pmOutTime", pm.get("pmOutTime"));//正常
                    map.put("pmRetreat", pm.get("pmRetreat"));//迟到
                    map.put("pmLeave", pm.get("pmLeave"));//早退
                    map.put("pmAbsenteeism", pm.get("pmAbsenteeism"));//旷工
                    //合计
                    map.put("sumOutTime", Integer.valueOf(am.get("amOutTime")) + Integer.valueOf(pm.get("pmOutTime")));//正常
                    map.put("sumRetreat", Integer.valueOf(am.get("amRetreat")) + Integer.valueOf(pm.get("pmRetreat")));//迟到
                    map.put("sumLeave", Integer.valueOf(am.get("amLeave")) + Integer.valueOf(pm.get("pmLeave")));//早退
                    map.put("sumAbsenteeism", Integer.valueOf(am.get("amAbsenteeism")) + Integer.valueOf(pm.get("pmAbsenteeism")));//旷工
                    dateList.add(map);
                }
            }
            List<File> srcfile = new ArrayList<>();
            List<List<String>> list = orderJoint(dateList, dateStatisticsList);
            File file = FileExportImportUtil.createExcel(tableTitles, list, expName + "_" + i, dir);
            srcfile.add(file);
            FileExportImportUtil.createRar(response, dir, srcfile, "考勤打卡统计表");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> orderJoint(List<Map<String, Object>> daMapList, List<UserWorkDayData> dateStatisticsList) {
        List<List<String>> list = new ArrayList<>();
        if (ObjectUtils.isNotNull(daMapList)) {
            for (Map<String, Object> map : daMapList) {
                List<String> small = new ArrayList<String>();
                //序号
                small.add(map.get("number").toString());
                //姓名
                small.add(map.get("name").toString());
                //日期
                for (UserWorkDayData d : dateStatisticsList) {
                    List<Object> list1 = (List<Object>) map.get(d.getWorkDate());
                    if (ObjectUtils.isNotNull(list1)) {
                        for (int i = 1; i <= list1.size(); i++) {
                            Map<String, String> stringMap = (Map<String, String>) list1.get(i - 1);
                            small.add(stringMap.get("am"));
                            small.add(stringMap.get("pm"));
                        }
                    } else {
                        small.add("-");
                        small.add("-");
                    }

                }
                //时间上午段正常的
                small.add(map.get("amOutTime").toString());
                //时间上午段迟到的
                small.add(map.get("amRetreat").toString());
                //时间上午段早退的
                small.add(map.get("amLeave").toString());
                //时间上午段旷工的
                small.add(map.get("amAbsenteeism").toString());

                //时间下午段正常的
                small.add(map.get("pmOutTime").toString());
                //时间下午段迟到的
                small.add(map.get("pmRetreat").toString());
                //时间下午段早退的
                small.add(map.get("pmLeave").toString());
                //时间下午段旷工的
                small.add(map.get("pmAbsenteeism").toString());

                //时间总计段正常的
                small.add(map.get("sumOutTime").toString());
                //时间总计段迟到的
                small.add(map.get("sumRetreat").toString());
                //时间总计段早退的
                small.add(map.get("sumLeave").toString());
                //时间总计段旷工的
                small.add(map.get("sumAbsenteeism").toString());
                list.add(small);
            }
        }
        return list;
    }


    //获取状态
    public String getStatus(Integer type) {
        String statusName = "";
        switch (type) {
            case 1:
                statusName = "正常";
                break;
            case 2:
                statusName = "迟到";
                break;
            case 3:
                statusName = "早退";
                break;
            case 4:
                statusName = "旷工";
                break;
        }
        return statusName;
    }

    //统计每种状态数量
    public Map<String, String> getStatistics(List<UserWorkDayData> userWorkDayDatas, String timeSlot) {
        //下午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工
        Map<String, String> map = new HashMap<>();
        Integer amOutTime = 0;
        Integer amRetreat = 0;
        Integer amLeave = 0;
        Integer amAbsenteeism = 0;

        Integer pmOutTime = 0;
        Integer pmRetreat = 0;
        Integer pmLeave = 0;
        Integer pmAbsenteeism = 0;
        for (UserWorkDayData u : userWorkDayDatas) {
            switch (u.getMorningAttendanceStatus()) {
                case 1:
                    if ("am".equals(timeSlot)) {
                        amOutTime = amOutTime + 1;
                    } else {
                        pmOutTime = pmOutTime + 1;
                    }
                    break;
                case 2:
                    if ("am".equals(timeSlot)) {
                        amRetreat = amRetreat + 1;
                    } else {
                        pmRetreat = pmRetreat + 1;
                    }
                    break;
                case 3:
                    if ("am".equals(timeSlot)) {
                        amLeave = amLeave + 1;
                    } else {
                        pmLeave = pmLeave + 1;
                    }
                    break;
                case 4:
                    if ("am".equals(timeSlot)) {
                        amAbsenteeism = amAbsenteeism + 1;
                    } else {
                        pmAbsenteeism = pmAbsenteeism + 1;
                    }
                    break;
            }
        }
        map.put("amOutTime", String.valueOf(amOutTime));
        map.put("amRetreat", String.valueOf(amRetreat));
        map.put("amLeave", String.valueOf(amLeave));
        map.put("amAbsenteeism", String.valueOf(amAbsenteeism));
        map.put("pmOutTime", String.valueOf(pmOutTime));
        map.put("pmRetreat", String.valueOf(pmRetreat));
        map.put("pmLeave", String.valueOf(pmLeave));
        map.put("pmAbsenteeism", String.valueOf(pmAbsenteeism));
        return map;
    }


    /**
     * 学员考勤
     *
     * @param request
     * @param pagination
     * @param userCondition
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/studentWorkDayDataList")
    public String studentWorkDayDataList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("userCondition") UserCondition userCondition) {
        try {
            List<UserWorkDayDataDTO> userWorkDayDataList = null;
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            Map<String, Object> conditionMap = new HashMap<String, Object>();
//            StringBuffer whereSql = new StringBuffer(" userId=" + userMap.get("linkId"));
            User user = userBiz.findById(Long.parseLong(userMap.get("linkId")));
            if(ObjectUtils.isNotNull(user)){
                conditionMap.put("classId", user.getClassId());
            }
            conditionMap.put("userId", userMap.get("linkId"));
            if (userCondition.getAfternoonAttendanceStatus() != null && !userCondition.getAfternoonAttendanceStatus().equals(0)) {
//                whereSql.append(" and afternoonAttendanceStatus=" + userCondition.getAfternoonAttendanceStatus());
                conditionMap.put("afternoonAttendanceStatus", userCondition.getAfternoonAttendanceStatus());
            }
            if (userCondition.getMorningAttendanceStatus() != null && !userCondition.getMorningAttendanceStatus().equals(0)) {
//                whereSql.append(" and morningAttendanceStatus=" + userCondition.getMorningAttendanceStatus());
                conditionMap.put("morningAttendanceStatus", userCondition.getMorningAttendanceStatus());
            }
//
            if (!StringUtils.isTrimEmpty(userCondition.getWorkDate())) {
//                whereSql.append(" and workDate like '" + userCondition.getWorkDate() + "'");
                conditionMap.put("workDate", userCondition.getWorkDate());
            }
            if (!StringUtils.isEmpty(userCondition.getClassStartTime())) {
                conditionMap.put("classStartTime", userCondition.getClassStartTime());
            }
            if (!StringUtils.isEmpty(userCondition.getClassEndTime())) {
                conditionMap.put("classEndTime", userCondition.getClassEndTime());
            }
//            whereSql.append("  and type=1  order by TO_DAYS(workDate) desc");
            conditionMap.put("type", 1);

            pagination.setRequest(request);
            userWorkDayDataList = userWorkDayDataBiz.studentWorkDayDataList(conditionMap, pagination);
            request.setAttribute("userWorkDayDataList", userWorkDayDataList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("userCondition", userCondition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/attendance/student_workDayData_list";
    }

    public String getDate(String date) throws Exception {
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
        String month = sdf1.format(d1);
        String day = sdf2.format(d1);
        return month + "月" + day + "日";
    }

//    /**
//     * 检查校准统计数据
//     */
//    @RequestMapping(ADMIN_PREFIX + "/checkData")
//    @ResponseBody
//    public Map<String, Object> checkData() {
//        try {
//            attendanceBiz.checkData();
//            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
//        }
//    }

}
