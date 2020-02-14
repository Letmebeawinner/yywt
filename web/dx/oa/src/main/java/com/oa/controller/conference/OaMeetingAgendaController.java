package com.oa.controller.conference;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.common.HqHessianService;
import com.oa.biz.common.HrHessianBiz;
import com.oa.biz.conference.OaMeetingAgendaBiz;
import com.oa.biz.conference.OaMeetingTopicBiz;
import com.oa.common.BaseHessianService;
import com.oa.entity.conference.OaMeetingAgenda;
import com.oa.entity.conference.OaMeetingTopic;
import com.oa.entity.employee.Employee;
import com.oa.entity.sysuser.SysUser;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 议程
 *
 * @author YaoZhen
 * @since 11-24, 11:20, 2017.
 */
@Controller
@RequestMapping("/admin/oa")
public class OaMeetingAgendaController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OaMeetingAgendaController.class);

    public static final String PROCESS_DEFINITION_ID = "oa_agenda_apply:4:397855";

    /**
     * 当前线程的req
     */
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private OaMeetingAgendaBiz oaMeetingAgendaBiz;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private HrHessianBiz hrHessianBiz;
    @Autowired
    private OaMeetingTopicBiz oaMeetingTopicBiz;
    @Autowired
    private HqHessianService hqHessianService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder("agenda")
    public void oaMeetingAgendaInit(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("agenda.");
    }

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder("oaMeetingTopic")
    public void oaMeetingTopicInit(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("oaMeetingTopic.");
    }

    /**
     * 启动
     *
     * @param agenda 议程
     * @return map
     */
    @RequestMapping("/agenda/process/start")
    @ResponseBody
    public Map<String, Object> startProcess(@ModelAttribute("agenda") OaMeetingAgenda agenda,
                                            @RequestParam("processDefinitionId") String processDefinitionId) {
        Map<String, Object> json;
        try {
            Date time = agenda.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(time);
            Long locationId = agenda.getLocationId();
            String whereSql = " time='" + dateString + "' and locationId=" + locationId;
            List<OaMeetingAgenda> oaMeetingAgenda = oaMeetingAgendaBiz.find(null, whereSql);
            if (ObjectUtils.isNotNull(oaMeetingAgenda)) {
                json = this.resultJson(ErrorCode.ERROR_SYSTEM, "当前时间、地点已有议程", null);
            } else {
                Long userId = SysUserUtils.getLoginSysUserId(request);
                String processInstanceId = oaMeetingAgendaBiz.tx_startAgendaProcess(agenda, processDefinitionId, userId);
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, processInstanceId);
            }
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.startProcess", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 议程审核
     */
    @RequestMapping("/agendaApplyAudit")
    @ResponseBody
    public Map<String, Object> agendaApplyAudit(@RequestParam("taskId") String taskId,
                                                @ModelAttribute("agenda") OaMeetingAgenda agenda,
                                                @RequestParam(value = "comment", required = false) String comment,
                                                @RequestParam(value = "userIds", required = false) String userIds,
                                                HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            oaMeetingAgendaBiz.tx_startAgendaAudit(agenda, taskId, comment, userId, userIds);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.agendaApplyAudit", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }

        return json;
    }

    /**
     * 查询用户
     *
     * @param request    {@link HttpServletRequest}
     * @param pagination {@link Pagination} 分页
     * @return 收件人(用户列表)
     */
    @RequestMapping("/ajax/queryReceivers")
    @SuppressWarnings("unchecked")
    public String queryReceivers(HttpServletRequest request,
                                 @ModelAttribute("pagination") Pagination pagination,
                                 @RequestParam(value = "departmentId", required = false) Long departmentId,
                                 @RequestParam(value = "userType", required = false) Integer userType,
                                 @RequestParam(value = "selectType", required = false) Integer selectType) {
        try {
            pagination.setPageSize(10);

            String where = "  status = 0 and userType = 2";
            String userName = request.getParameter("userName");
            if (!StringUtils.isTrimEmpty(userName)) {
                where += " and userName like '%" + userName + "%'";
            }
            if (userType != null) {
                if (userType == 1) {//查询部门负责人
                    String departmentIds = baseHessianService.queryParentDepartment();
                    where += " and departmentId in (" + departmentIds + ")";
                    departmentId = 0L;
                } else if (userType == 2) {//查询中层干部
                    List<Employee> employeeList = hrHessianBiz.getEmployeeList("employeeType=4");
                    AtomicReference<String> employeeIds = new AtomicReference<>("");
                    employeeList.forEach(e -> {
                        employeeIds.updateAndGet(v -> v + e.getId() + ",");
                    });
                    where += " and linkId in (" + employeeIds.get().substring(0, employeeIds.get().length() - 1) + ")";
                    departmentId = 0L;
                } else if (userType == 3) {//查询中心组成员
                    List<Employee> employeeList = hrHessianBiz.getEmployeeList("employeeType=4");
                    AtomicReference<String> employeeIds = new AtomicReference<>("");
                    employeeList.forEach(e -> {
                        employeeIds.updateAndGet(v -> v + e.getId() + ",");
                    });
                    String departmentIds = baseHessianService.queryParentDepartment();
                    where += " and ( linkId in (" + employeeIds.get().substring(0, employeeIds.get().length() - 1) + ") or departmentId in (" + departmentIds + ",22) )";
                    departmentId = 0L;
                } else {//按部门查询
                    if (departmentId != null) {
                        where += " and departmentId =" + departmentId;
                    }
                }
            } else {//按部门查询
                if (departmentId != null) {
                    where += " and departmentId =" + departmentId;
                }
            }
            where += " order by sort";

            Map<String, Object> map = baseHessianService.querySysUserList(pagination, where);

            List<Map<String, String>> list = baseHessianService.queryAllDepartment();
            List<Map<String, String>> userList = (List<Map<String, String>>) map.get("userList");
            Map<String, String> _pagination = (Map<String, String>) map.get("pagination");

            pagination.setBegin(Integer.parseInt(_pagination.get("begin")));
            pagination.setTotalCount(Integer.parseInt(_pagination.get("totalCount")));
            pagination.setEnd(Integer.parseInt(_pagination.get("end")));
            pagination.setCurrentPage(Integer.parseInt(_pagination.get("currentPage")));
            pagination.setCurrentUrl(_pagination.get("currentUrl"));
//            pagination.setRequest(request);
            pagination.setPageSize(Integer.parseInt(_pagination.get("pageSize")));
            pagination.setTotalPages(Integer.parseInt(_pagination.get("totalPages")));
            request.setAttribute("userList", userList);
            request.setAttribute("departmentId", departmentId);
            request.setAttribute("list", list);
            request.setAttribute("currentPath", request.getRequestURI());
            request.setAttribute("userName", userName);
            request.setAttribute("userType", userType);
            request.setAttribute("selectType", selectType);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.queryReceivers()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/user/userList";
    }

    /**
     * 查询用户
     *
     * @param request    {@link HttpServletRequest}
     * @param pagination {@link Pagination} 分页
     * @return 收件人(用户列表)
     */
    @RequestMapping("/ajax/queryReceivers/next")
    @SuppressWarnings("unchecked")
    public String queryReceiversNext(HttpServletRequest request,
                                     @ModelAttribute("pagination") Pagination pagination,
                                     @RequestParam(value = "departmentId", required = false) Long departmentId,
                                     @RequestParam(value = "userType", required = false) Integer userType,
                                     @RequestParam(value = "selectType", required = false) Integer selectType) {
        try {
            pagination.setPageSize(10);

            String where = "  status = 0 and userType = 2";
            String userName = request.getParameter("userName");
            if (!StringUtils.isTrimEmpty(userName)) {
                where += " and userName like '%" + userName + "%'";
            }
            if (userType != null) {
                if (userType == 1) {//查询部门负责人
                    String departmentIds = baseHessianService.queryParentDepartment();
                    where += " and departmentId in (" + departmentIds + ")";
                    departmentId = 0L;
                } else if (userType == 2) {//查询中层干部
                    List<Employee> employeeList = hrHessianBiz.getEmployeeList("employeeType=4");
                    AtomicReference<String> employeeIds = new AtomicReference<>("");
                    employeeList.forEach(e -> {
                        employeeIds.updateAndGet(v -> v + e.getId() + ",");
                    });
                    where += " and linkId in (" + employeeIds.get().substring(0, employeeIds.get().length() - 1) + ")";
                    departmentId = 0L;
                } else if (userType == 3) {//查询中心组成员
                    List<Employee> employeeList = hrHessianBiz.getEmployeeList("employeeType=4");
                    AtomicReference<String> employeeIds = new AtomicReference<>("");
                    employeeList.forEach(e -> {
                        employeeIds.updateAndGet(v -> v + e.getId() + ",");
                    });
                    String departmentIds = baseHessianService.queryParentDepartment();
                    where += " and ( linkId in (" + employeeIds.get().substring(0, employeeIds.get().length() - 1) + ") or departmentId in (" + departmentIds + ",22) )";
                    departmentId = 0L;
                } else {//按部门查询
                    if (departmentId != null) {
                        where += " and departmentId =" + departmentId;
                    }
                }
            } else {//按部门查询
                if (departmentId != null) {
                    where += " and departmentId =" + departmentId;
                }
            }
            where += " order by sort";

            Map<String, Object> map = baseHessianService.querySysUserList(pagination, where);

            List<Map<String, String>> list = baseHessianService.queryAllDepartment();
            List<Map<String, String>> userList = (List<Map<String, String>>) map.get("userList");
            Map<String, String> _pagination = (Map<String, String>) map.get("pagination");

            pagination.setBegin(Integer.parseInt(_pagination.get("begin")));
            pagination.setTotalCount(Integer.parseInt(_pagination.get("totalCount")));
            pagination.setEnd(Integer.parseInt(_pagination.get("end")));
            pagination.setCurrentPage(Integer.parseInt(_pagination.get("currentPage")));
            pagination.setCurrentUrl(_pagination.get("currentUrl"));
//            pagination.setRequest(request);
            pagination.setPageSize(Integer.parseInt(_pagination.get("pageSize")));
            pagination.setTotalPages(Integer.parseInt(_pagination.get("totalPages")));
            request.setAttribute("userList", userList);
            request.setAttribute("departmentId", departmentId);
            request.setAttribute("list", list);
            request.setAttribute("currentPath", request.getRequestURI());
            request.setAttribute("userName", userName);
            request.setAttribute("userType", userType);
            request.setAttribute("selectType", selectType);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.queryReceivers()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/user/userList_next";
    }


    /**
     * 查询议题列表
     *
     * @return 编辑
     */
    @RequestMapping("/ajax/queryMeetingTopics")
    public String queryMeetingTopics(HttpServletRequest request,
                                     @ModelAttribute("oaMeetingTopic") OaMeetingTopic oaMeetingTopic,
                                     @ModelAttribute("pagination") Pagination pagination) {

        try {
            pagination.setPageSize(10);
            pagination.setRequest(request);
            //查询审核已通过的议题, 未使用的议题
            String sql = "audit = 1 and state = 1";
            if (ObjectUtils.isNotNull(oaMeetingTopic.getName())) {
                sql += " and name like '%" + oaMeetingTopic.getName() + "%'";
            }
            sql += " order by id desc";
            List<OaMeetingTopic> oaMeetingTopicList = oaMeetingTopicBiz.find(pagination, sql);
            request.setAttribute("oaMeetingTopicList", oaMeetingTopicList);
            request.setAttribute("oaMeetingTopic", oaMeetingTopic);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.queryMeetingTopics", e);
        }
        return "/conference/select_oa_meeting_topic_list";
    }

    /**
     * 议程列表
     *
     * @param pagination 分页
     * @param agenda     议程
     * @param request    请求
     * @return 列表页面
     */
    @RequestMapping("/agenda/list")
    public ModelAndView listAgenda(@ModelAttribute("pagination") Pagination pagination,
                                   @ModelAttribute("agenda") OaMeetingAgenda agenda,
                                   HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/conference/oa_meeting_agenda_list");
        try {

            Long userId = SysUserUtils.getLoginSysUserId(request);
            String sql = "( reporter like '%," + userId + ",%' or attendPeople like '%," + userId + ",%' )";
            List<OaMeetingTopic> oaMeetingTopicList = oaMeetingTopicBiz.find(null, sql);

            pagination.setRequest(request);
            sql = "( bePresent like '%," + userId + ",%' or absent like '%," + userId + ",%' or attend like '%," + userId + ",%' or compere like '%," + userId + ",%' or record like '%," + userId + ",%'";
//            if(agenda.getAudit()!=null){
//                sql+=" and audit="+agenda.getAudit();
//            }
            if (ObjectUtils.isNotNull(oaMeetingTopicList)) {
                for (OaMeetingTopic o : oaMeetingTopicList) {
                    sql += " or topicIds like '%," + o.getId() + ",%'";
                }
            }
            sql += " )";
            if (ObjectUtils.isNotNull(agenda.getLocation()) && agenda.getLocation() != "") {
                sql += " and location like '%" + agenda.getLocation() + "%'";
            }
            if (ObjectUtils.isNotNull(agenda.getCompere()) && agenda.getCompere() != "") {
                sql += " and compere like '%" + agenda.getCompere() + "%'";
            }
            sql += " order by id desc";
            List<OaMeetingAgenda> oaMeetingAgendaList = oaMeetingAgendaBiz.find(pagination, sql);
            for (OaMeetingAgenda oaMeetingAgenda : oaMeetingAgendaList) {
                String compere = oaMeetingAgenda.getCompere();
                String compereUserId = compere.substring(1, compere.length() - 1);
                String[] ids;
                if (compereUserId.contains(",")) {
                    ids = compereUserId.split(",");
                } else {
                    ids = new String[1];
                    ids[0] = compereUserId;
                }
                StringBuilder comperes = new StringBuilder();
                for (String id : ids) {
                    SysUser sysUser = baseHessianBiz.getSysUserById(Long.valueOf(id));
                    if (ObjectUtils.isNotNull(sysUser) && !StringUtils.isEmpty(sysUser.getUserName())) {
                        comperes.append(sysUser.getUserName()).append(",");
                    }
                }
                oaMeetingAgenda.setCompere(comperes.substring(0, comperes.length() - 1));
            }
            mv.addObject("oaMeetingAgendaList", oaMeetingAgendaList);
            mv.addObject("pagination", pagination);
            mv.addObject("agenda", agenda);
        } catch (Exception e) {
            logger.error("ConferenceController.listAgenda", e);
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * 查询议程详情
     *
     * @return 编辑
     */
    @RequestMapping("/queryMeetingAgenda")
    public String queryMeetingAgenda(HttpServletRequest request,
                                     @RequestParam(value = "id", required = true) Long id) {

        try {
            OaMeetingAgenda oaMeetingAgenda = oaMeetingAgendaBiz.findById(id);
            request.setAttribute("oaMeetingAgenda", oaMeetingAgenda);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.queryMeetingAgenda", e);
        }
        return "/conference/oa_agenda_apply_history";
    }

    /**
     * 查询会场
     *
     * @param request    {@link HttpServletRequest}
     * @param pagination {@link Pagination} 分页
     * @return 收件人(用户列表)
     */
    @RequestMapping("/ajax/queryHqAllMeeting")
    public String queryHqAllMeeting(HttpServletRequest request,
                                    @ModelAttribute("pagination") Pagination pagination,
                                    @RequestParam(value = "name", required = false) String name) {
        try {
            pagination.setPageSize(10);
            Map<String, Object> map = hqHessianService.queryHqAllMeeting(pagination, name);
            List<Map<String, String>> meetingList = (List<Map<String, String>>) map.get("meetingList");
            Map<String, String> _pagination = (Map<String, String>) map.get("pagination");
            pagination.setBegin(Integer.parseInt(_pagination.get("begin")));
            pagination.setTotalCount(Integer.parseInt(_pagination.get("totalCount")));
            pagination.setEnd(Integer.parseInt(_pagination.get("end")));
            pagination.setCurrentPage(Integer.parseInt(_pagination.get("currentPage")));
            pagination.setCurrentUrl(_pagination.get("currentUrl"));
            pagination.setPageSize(Integer.parseInt(_pagination.get("pageSize")));
            pagination.setTotalPages(Integer.parseInt(_pagination.get("totalPages")));
            request.setAttribute("meetingList", meetingList);
            request.setAttribute("currentPath", request.getRequestURI());
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.queryHqAllMeeting()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/meeting/queryHqAllMeeting";
    }

    /**
     * 查询会场详情
     *
     * @param request
     * @param meetingId
     * @return
     */
    @RequestMapping("/ajax/getMeetingForOAById")
    @ResponseBody
    public Map<String, Object> getMeetingForOAById(HttpServletRequest request,
                                                   @RequestParam("meetingId") Long meetingId) {
        Map<String, Object> json = null;
        try {
            Map<String, String> meetingForOAById = hqHessianService.getMeetingForOAById(meetingId);
            if (ObjectUtils.isNotNull(meetingForOAById)) {
                json = this.resultJson(ErrorCode.SUCCESS, "操作成功", meetingForOAById);
            } else {
                json = this.resultJson(ErrorCode.ERROR_DATA, "无会场记录", null);
            }
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.getMeetingForOAById()--error", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }
        return json;
    }

    /**
     * 查询会场记录最新使用记录
     *
     * @param request
     * @param meetingId
     * @return
     */
    @RequestMapping("/ajax/getMeetingRecordForOAById")
    @ResponseBody
    public Map<String, Object> getMeetingRecordForOAById(HttpServletRequest request,
                                                         @RequestParam("meetingId") Long meetingId) {
        Map<String, Object> json = null;
        try {
            Map<String, String> meetingRecordForOAById = hqHessianService.getMeetingRecordForOAById(meetingId);
            if (ObjectUtils.isNotNull(meetingRecordForOAById)) {
                json = this.resultJson(ErrorCode.SUCCESS, "操作成功", meetingRecordForOAById);
            } else {
                json = this.resultJson(ErrorCode.ERROR_DATA, "无会场使用记录", null);
            }
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.getMeetingRecordForOAById()--error", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }
        return json;
    }

    //以下草稿箱新添加2018年11月8日####################################################

    /**
     * 保存草稿
     *
     * @param agenda
     * @return
     */
    @RequestMapping("/agenda/process/saveOaMeetingAgendaDraft")
    @ResponseBody
    public Map<String, Object> saveOaMeetingAgendaDraft(@ModelAttribute("agenda") OaMeetingAgenda agenda) {
        Map<String, Object> json;
        try {
            Date time = agenda.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(time);
            Long locationId = agenda.getLocationId();
            String whereSql = " time='" + dateString + "' and locationId=" + locationId;
            List<OaMeetingAgenda> oaMeetingAgenda = oaMeetingAgendaBiz.find(null, whereSql);
            // 当前时间、地点是否有议程
            if (ObjectUtils.isNotNull(oaMeetingAgenda)) {
                json = this.resultJson(ErrorCode.ERROR_SYSTEM, "当前时间、地点已有议程", null);
            } else {
                Long userId = SysUserUtils.getLoginSysUserId(request);
                agenda.setApplyId(userId);
                agenda.setApplyTime(new Date());
                agenda.setAudit(0);
                // 0正常 1删除 2草稿箱
                agenda.setStatus(2);
                oaMeetingAgendaBiz.save(agenda);
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, agenda);
            }
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.startProcess", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 草稿箱列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/agenda/process/oaMeetingAgendaDraftList")
    public String oaMeetingAgendaDraftList(HttpServletRequest request,
                                           @ModelAttribute("pagination") Pagination pagination) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            pagination.setPageSize(10);
            pagination.setRequest(request);
            String whereSql = " status=" + 2 + " and applyId=" + userId + " order by id desc";
            List<OaMeetingAgenda> oaMeetingAgendaList = oaMeetingAgendaBiz.find(pagination, whereSql);
            request.setAttribute("pagination", pagination);
            request.setAttribute("oaMeetingAgendaList", oaMeetingAgendaList);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.oaMeetingAgendaDraftList", e);
        }
        return "/conference/oa_meeting_agenda_draft_list";
    }

    /**
     * 去修改议程
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/agenda/process/toUpdateOaMeetingAgenda")
    public String toUpdateOaMeetingAgenda(HttpServletRequest request,
                                          @RequestParam("id") Long id) {
        try {
            OaMeetingAgenda oaMeetingAgenda = oaMeetingAgendaBiz.findById(id);
            request.setAttribute("agenda", oaMeetingAgenda);
            // 出席bePresent
            String bePresent = oaMeetingAgenda.getBePresent();
            if (!StringUtils.isEmpty(bePresent)) {
                String[] bePresentArray = bePresent.split(",");
                if (bePresentArray.length > 0) {
                    bePresent = bePresent.substring(1, bePresent.length() - 1);
                    String where = " id in (" + bePresent + ")";
                    Map<String, Object> bePresentList = baseHessianService.querySysUserList(null, where);
                    List<Map<String, String>> bePresentUserList = (List<Map<String, String>>) bePresentList.get("userList");
                    request.setAttribute("bePresentUserList", bePresentUserList);
                }
            }
            // 缺席absent
            String absent = oaMeetingAgenda.getAbsent();
            if (!StringUtils.isEmpty(absent)) {
                String[] absentArray = absent.split(",");
                if (absentArray.length > 0) {
                    absent = absent.substring(1, absent.length() - 1);
                    String where = " id in (" + absent + ")";
                    Map<String, Object> bePresentList = baseHessianService.querySysUserList(null, where);
                    List<Map<String, String>> absentUserList = (List<Map<String, String>>) bePresentList.get("userList");
                    request.setAttribute("absentUserList", absentUserList);
                }
            }
            // 列席attend
            String attend = oaMeetingAgenda.getAttend();
            if (!StringUtils.isEmpty(attend)) {
                String[] attendArray = attend.split(",");
                if (attendArray.length > 0) {
                    attend = attend.substring(1, attend.length() - 1);
                    String where = " id in (" + attend + ")";
                    Map<String, Object> bePresentList = baseHessianService.querySysUserList(null, where);
                    List<Map<String, String>> attendUserList = (List<Map<String, String>>) bePresentList.get("userList");
                    request.setAttribute("attendUserList", attendUserList);
                }
            }
            // 议题topicIds
            String topicIds = oaMeetingAgenda.getTopicIds();
            if (!StringUtils.isEmpty(topicIds)) {
                String[] topicIdsArray = topicIds.split(",");
                if (topicIdsArray.length > 0) {
                    topicIds = topicIds.substring(1, topicIds.length() - 1);
                    String where = " id in (" + topicIds + ")";
                    List<OaMeetingTopic> oaMeetingTopicList = oaMeetingTopicBiz.find(null, where);
                    request.setAttribute("oaMeetingTopicList", oaMeetingTopicList);
                }
            }
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.toUpdateOaMeetingAgenda", e);
        }
        return "/conference/oa_meeting_agenda_draft_update";
    }

    /**
     * 修改议程
     *
     * @param agenda
     * @return
     */
    @RequestMapping("/agenda/process/updateOaMeetingAgenda")
    @ResponseBody
    public Map<String, Object> updateOaMeetingAgendaDraft(@ModelAttribute("agenda") OaMeetingAgenda agenda) {
        Map<String, Object> json;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 原有时间和地点
            OaMeetingAgenda oaMeetingAgenda = oaMeetingAgendaBiz.findById(agenda.getId());
            Long locationId1 = oaMeetingAgenda.getLocationId();
            Date time1 = oaMeetingAgenda.getTime();
            String dateString1 = formatter.format(time1);
            // 修改后的时间和地点
            Long locationId2 = agenda.getLocationId();
            Date time2 = agenda.getTime();
            String dateString2 = formatter.format(time2);
            // 修改后的时间、地点是否有议程
            String whereSql = " time='" + dateString2 + "' and locationId=" + locationId2;
            List<OaMeetingAgenda> oaMeetingAgenda1 = oaMeetingAgendaBiz.find(null, whereSql);
            if (!locationId1.equals(locationId2) && !dateString1.equals(dateString2) && ObjectUtils.isNotNull(oaMeetingAgenda1)) {
                json = this.resultJson(ErrorCode.ERROR_SYSTEM, "修改后的时间、地点已有议程", null);
            } else {
                oaMeetingAgendaBiz.update(agenda);
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, agenda);
            }
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.updateOaMeetingAgendaDraft", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 在修改页面启动议程，先做保存操作
     *
     * @param agenda
     * @return
     */
    @RequestMapping("/agenda/process/startDraftOaMeetingAgenda")
    @ResponseBody
    public Map<String, Object> startDraftOaMeetingAgenda(@ModelAttribute("agenda") OaMeetingAgenda agenda) {
        Map<String, Object> json;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 原有时间和地点
            OaMeetingAgenda oaMeetingAgenda = oaMeetingAgendaBiz.findById(agenda.getId());
            Long locationId1 = oaMeetingAgenda.getLocationId();
            Date time1 = oaMeetingAgenda.getTime();
            String dateString1 = formatter.format(time1);
            // 修改后的时间和地点
            Long locationId2 = agenda.getLocationId();
            Date time2 = agenda.getTime();
            String dateString2 = formatter.format(time2);
            // 修改后的时间、地点是否有议程
            String whereSql = " time='" + dateString2 + "' and locationId=" + locationId2;
            List<OaMeetingAgenda> oaMeetingAgenda1 = oaMeetingAgendaBiz.find(null, whereSql);
            if (!locationId1.equals(locationId2) && !dateString1.equals(dateString2) && ObjectUtils.isNotNull(oaMeetingAgenda1)) {
                json = this.resultJson(ErrorCode.ERROR_SYSTEM, "当前时间、地点已有议程", null);
            } else {
                ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(PROCESS_DEFINITION_ID).singleResult();
                // 因操作在修改页面，可能有修改数据，所以先做修改操作
                oaMeetingAgendaBiz.update(agenda);
                Long userId = SysUserUtils.getLoginSysUserId(request);
                // 在草稿箱列表开启
                String processInstanceId = oaMeetingAgendaBiz.draft_startAgendaProcess(agenda, processDefinition.getId(), userId);
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, processInstanceId);
            }
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.startDraftOaMeetingAgenda", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 在列表页面通过id启动草稿箱议程
     *
     * @param id
     * @return
     */
    @RequestMapping("/agenda/process/startDraftOaMeetingAgendaById")
    @ResponseBody
    public Map<String, Object> startDraftOaMeetingAgendaById(@RequestParam("id") Long id) {
        Map<String, Object> json;
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(PROCESS_DEFINITION_ID).singleResult();
            OaMeetingAgenda agenda = oaMeetingAgendaBiz.findById(id);
            Long userId = SysUserUtils.getLoginSysUserId(request);
            // 在草稿箱列表开启议程
            String processInstanceId = oaMeetingAgendaBiz.draft_startAgendaProcess(agenda, processDefinition.getId(), userId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, processInstanceId);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.startDraftOaMeetingAgendaById", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 删除草稿箱议程
     *
     * @param id
     * @return
     */
    @RequestMapping("/agenda/process/deleteOaMeetingAgendaById")
    @ResponseBody
    public Map<String, Object> deleteOaMeetingAgendaById(@RequestParam("id") Long id) {
        Map<String, Object> json;
        try {
            oaMeetingAgendaBiz.deleteById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.deleteOaMeetingAgendaById", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    //草稿箱新添加结束2018年11月8日####################################################

}
