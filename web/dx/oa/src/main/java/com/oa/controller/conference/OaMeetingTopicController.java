package com.oa.controller.conference;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.conference.OaMeetingAgendaBiz;
import com.oa.biz.conference.OaMeetingTopicBiz;
import com.oa.common.BaseHessianService;
import com.oa.entity.conference.OaMeetingAgenda;
import com.oa.entity.conference.OaMeetingTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 议题
 *
 * @author YaoZhen
 * @since 11-24, 11:20, 2017.
 */
@Controller
@RequestMapping("/admin/oa")
public class OaMeetingTopicController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OaMeetingTopicController.class);
    @Autowired
    private OaMeetingTopicBiz oaMeetingTopicBiz;
    @Autowired
    private OaMeetingAgendaBiz oaMeetingAgendaBiz;
    @Autowired
    private BaseHessianService baseHessianService;

    private static final String meetingTopicList = "/conference/list_oa_meeting_topic";

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder({"oaMeetingTopic"})
    public void oaMeetingTopicInit(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("oaMeetingTopic.");
    }

    /**
     * 启动
     *
     * @param topic               议题
     * @param processDefinitionId
     * @return map
     */
    @RequestMapping("/topic/process/start")
    @ResponseBody
    public Map<String, Object> startProcess(@ModelAttribute("oaMeetingTopic") OaMeetingTopic topic,
                                            @RequestParam("processDefinitionId") String processDefinitionId,
                                            HttpServletRequest request,
                                            @RequestParam(value = "userIds", required = false) String userIds) {
        Map<String, Object> json;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            if (ObjectUtils.isNotNull(redisCache.get("oa_" + topic.getTimeStamp()))) {
                Map<String, Object> map = (Map) (redisCache.get("oa_" + topic.getTimeStamp()));
                topic.setFileUrl(map.get("fileUrl").toString());
                topic.setFileName(map.get("fileName").toString());
                redisCache.remove("oa_" + topic.getTimeStamp());
            }
            String processInstanceId = oaMeetingTopicBiz.tx_startTopicProcess(topic, processDefinitionId, userId, userIds);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, processInstanceId);
        } catch (Exception e) {
            logger.error("OaMeetingTopicController.startProcess", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 议题审核
     *
     * @param taskId
     * @param oaMeetingTopic
     * @param comment
     * @param request
     * @return
     */
    @RequestMapping("/meetingTopicAudit")
    @ResponseBody
    public Map<String, Object> carApplyAudit(@RequestParam("taskId") String taskId,
                                             @ModelAttribute("oaMeetingTopic") OaMeetingTopic oaMeetingTopic,
                                             @RequestParam(value = "comment", required = false) String comment,
                                             HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            oaMeetingTopicBiz.tx_startTopicApplyAudit(oaMeetingTopic, taskId, comment, userId);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("OaCarApplyController.carApplyAudit", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }

        return json;
    }

    /**
     * 议题审核
     *
     * @param taskId
     * @param topic
     * @param comment
     * @return
     */
    @RequestMapping("/topicApplyAudit")
    @ResponseBody
    public Map<String, Object> topicApplyAudit(@RequestParam("taskId") String taskId,
                                               @ModelAttribute("oaMeetingTopic") OaMeetingTopic topic,
                                               @RequestParam(value = "comment", required = false) String comment,
                                               HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            oaMeetingTopicBiz.tx_startTopicApplyAudit(topic, taskId, comment, userId);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("OaMeetingTopicController.topicApplyAudit", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }

        return json;
    }

    @RequestMapping("/meetingTopic/list")
    public String toMeetingTopicList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,
                                     @ModelAttribute("oaMeetingTopic") OaMeetingTopic oaMeetingTopic) {
        pagination.setRequest(request);
        //查询审核通过的
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String sql = "( reporter like '%," + userId + ",%' or attendPeople like '%," + userId + ",%' )";
            if (ObjectUtils.isNotNull(oaMeetingTopic.getName()) && oaMeetingTopic.getName() != "") {
                sql += " and name like '%" + oaMeetingTopic.getName() + "%'";
            }
            if (ObjectUtils.isNotNull(oaMeetingTopic.getSubjectContent()) && oaMeetingTopic.getSubjectContent() != "") {
                sql += " and subjectContent like '%" + oaMeetingTopic.getSubjectContent() + "%'";
            }
            sql += " order by id desc";
            List<OaMeetingTopic> oaMeetingTopicList = oaMeetingTopicBiz.find(pagination, sql);
            oaMeetingTopicList.forEach(e -> {
                List<OaMeetingAgenda> oaMeetingAgendaList = oaMeetingAgendaBiz.find(null, " audit=1 and topicIds like '%," + e.getId() + ",%'");
                if (oaMeetingAgendaList != null && oaMeetingAgendaList.size() > 0) {
                    e.setOaMeetingAgenda(oaMeetingAgendaList.get(0));
                }
            });
            request.setAttribute("oaMeetingTopicList", oaMeetingTopicList);
            request.setAttribute("oaMeetingTopic", oaMeetingTopic);
        } catch (Exception e) {
            logger.error("OaMeetingTopicController.toMeetingTopicList", e);
            return this.setErrorPath(request, e);
        }
        return meetingTopicList;
    }

    /**
     * 查询议程详情
     *
     * @return 编辑
     */
    @RequestMapping("/queryMeetingTopic")
    public String queryMeetingTopic(HttpServletRequest request,
                                    @RequestParam(value = "id", required = true) Long id) {

        try {
            OaMeetingTopic oaMeetingTopic = oaMeetingTopicBiz.findById(id);
            request.setAttribute("oaMeetingTopic", oaMeetingTopic);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.queryMeetingTopic", e);
        }
        return "/conference/oa_conference_topic_apply_history";
    }

    /**
     * 查询议程详情
     *
     * @return 编辑
     */
    @RequestMapping("/queryMeetingTopic/info")
    public String queryMeetingTopicInfo(HttpServletRequest request,
                                        @RequestParam(value = "id", required = true) Long id) {

        try {
            OaMeetingTopic oaMeetingTopic = oaMeetingTopicBiz.findById(id);
            request.setAttribute("oaMeetingTopic", oaMeetingTopic);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.queryMeetingTopicInfo", e);
        }
        return "/conference/oa_conference_topic_apply_info";
    }

    /**
     * 打印页面显示议题列表
     *
     * @param request
     * @param topicIds
     * @return
     */
    @RequestMapping("/ajax/showSelectedTopicListForPrint")
    public String showSelectedTopicListForPrint(HttpServletRequest request,
                                                @RequestParam(value = "topicIds", required = true) String topicIds,
                                                @RequestParam(value = "agendaId", required = true) Long agendaId) {

        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);

            String sqlWhere = " id="+agendaId;
            sqlWhere += " and ( bePresent like '%," + userId + ",%' or absent like '%," + userId + ",%' or attend like '%," + userId + ",%' or compere like '%," + userId + ",%' or record like '%," + userId + ",%' ) ";
            List<OaMeetingAgenda> agendaList=oaMeetingAgendaBiz.find(null,sqlWhere);
            boolean isLook=false;
            if (agendaList!=null && agendaList.size()>0)
                isLook=true;
            sqlWhere = " id in (" + topicIds + ") order by field(id," + topicIds + ")";
            List<OaMeetingTopic> oaMeetingTopicList = oaMeetingTopicBiz.find(null, sqlWhere);
            if (ObjectUtils.isNotNull(oaMeetingTopicList)) {
                int num = 1;
                for (OaMeetingTopic oaMeetingTopic : oaMeetingTopicList) {
                    if (!isLook){
                        if(oaMeetingTopic.getReporter().indexOf(","+userId+",")>-1 || oaMeetingTopic.getAttendPeople().indexOf(","+userId+",")>-1){
                            oaMeetingTopic.setIsLook(true);
                        }else {
                            oaMeetingTopic.setIsLook(false);
                        }
                    }else {
                        oaMeetingTopic.setIsLook(true);
                    }

                    //列席attend 将字段的id转换为名字再次存，为了前台显示
                    String reporterPeople = "";
                    String reporter = oaMeetingTopic.getReporter();
                    String[] reporterSplit = reporter.split(",");
                    if (reporterSplit.length > 0) {
                        reporter = reporter.substring(1, reporter.length() - 1);
                        String where = " id in (" + reporter + ")";
                        // 人员列表
                        Map<String, Object> bePresentList = baseHessianService.querySysUserList(null, where);
                        List<Map<String, String>> attendUserList = (List<Map<String, String>>) bePresentList.get("userList");
                        if (ObjectUtils.isNotNull(attendUserList)) {
                            //人员姓名
                            for (Map<String, String> stringMap : attendUserList) {
                                reporterPeople += stringMap.get("userName") + "、";
                            }
                        }
                        request.setAttribute("attendUserList", attendUserList);
                        reporterPeople = reporterPeople.substring(0, reporterPeople.length() - 1);
                    }
                    oaMeetingTopic.setReporter(reporterPeople);
                    //列席attend 将字段的id转换为名字再次存，为了前台显示
                    String attendPeople = "";
                    String attend = oaMeetingTopic.getAttendPeople();
                    String[] attendSplit = attend.split(",");
                    if (attendSplit.length > 0) {
                        attend = attend.substring(1, attend.length() - 1);
                        String where = " id in (" + attend + ")";
                        // 人员列表
                        Map<String, Object> bePresentList = baseHessianService.querySysUserList(null, where);
                        List<Map<String, String>> attendUserList = (List<Map<String, String>>) bePresentList.get("userList");
                        if (ObjectUtils.isNotNull(attendUserList)) {
                            //人员姓名
                            for (Map<String, String> stringMap : attendUserList) {
                                attendPeople += stringMap.get("userName") + "、";
                            }
                        }
                        request.setAttribute("attendUserList", attendUserList);
                        attendPeople = attendPeople.substring(0, attendPeople.length() - 1);
                    }
                    oaMeetingTopic.setAttendPeople(attendPeople);
                    String toChinese = toChinese(num + "");
                    oaMeetingTopic.setNumStr(toChinese);
                    num++;
                }
            }
            request.setAttribute("oaMeetingTopicList", oaMeetingTopicList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("OaMeetingAgendaController.showSelectedTopicListForPrint", e);
        }
        return "/conference/show_selected_topic_list_for_print";
    }

    /**
     * 日期转星期
     *
     * @param time
     * @return
     */
    @RequestMapping("/ajax/getWeekOfDate")
    @ResponseBody
    public Map<String, Object> getWeekOfDate(@RequestParam("time") String time) {
        Map<String, Object> json;
        try {
            String theTime = "";
            //星期
            String dateToWeek = dateToWeek(time);
            String substring = time.substring(11, 13);
            String duringDay = getDuringDay(Integer.parseInt(substring));
            String year = time.substring(0, 4);
            String month = time.substring(5, 7);
            String day = time.substring(8, 10);
            if (StringUtils.isTrimEmpty(duringDay)) {
                theTime = year + "年" + month + "月" + day + "（" + dateToWeek + "）";
            } else {
                theTime = year + "年" + month + "月" + day + "（" + dateToWeek + "）" + "  " + duringDay + time.substring(11, 16);
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, theTime);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.getWeekOfDate", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // 获得一个日历
        Calendar cal = Calendar.getInstance();
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 指示一个星期中的某天。
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 根据小时判断是否为上午、中午、下午
     *
     * @param hour
     * @return
     * @author zhangsq
     */
    public static String getDuringDay(int hour) {
        if (hour >= 7 && hour < 11) {
            return "上午";
        }
        if (hour >= 11 && hour <= 13) {
            return "中午";
        }
        if (hour >= 14 && hour <= 18) {
            return "下午";
        }
        return "";
    }

    private static String toChinese(String str) {
        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
        String result = "";
        int n = str.length();
        for (int i = 0; i < n; i++) {
            int num = str.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  " + result);
        }
        System.out.println(result);
        return result;
    }


}
