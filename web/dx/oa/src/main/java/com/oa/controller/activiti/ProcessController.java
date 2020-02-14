package com.oa.controller.activiti;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.archivetype.OaArchiveSearchBiz;
import com.oa.biz.car.OaCarApplyBiz;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.common.HrHessianBiz;
import com.oa.biz.conference.OaMeetingAgendaBiz;
import com.oa.biz.conference.OaMeetingTopicBiz;
import com.oa.biz.flow.FlowTypeBiz;
import com.oa.biz.leave.OaLeaveBiz;
import com.oa.biz.letter.OaApprovalBiz;
import com.oa.biz.meetingapply.OaMeetingBiz;
import com.oa.biz.news.OaNewsBiz;
import com.oa.biz.seal.OaSealBiz;
import com.oa.biz.workflow.ActTaskService;
import com.oa.biz.workflow.OaLetterBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.biz.workflow.WorkflowTraceService;
import com.oa.common.BaseHessianService;
import com.oa.common.ProcessConstantsEnum;
import com.oa.entity.archivetype.OaArchiveSearch;
import com.oa.entity.car.OaCarApply;
import com.oa.entity.conference.OaMeetingAgenda;
import com.oa.entity.conference.OaMeetingTopic;
import com.oa.entity.department.DepartMent;
import com.oa.entity.employee.Employee;
import com.oa.entity.flow.FlowType;
import com.oa.entity.leave.OaLeave;
import com.oa.entity.letter.OaApproval;
import com.oa.entity.meetingapply.OaMeeting;
import com.oa.entity.news.OaNews;
import com.oa.entity.seal.OaSeal;
import com.oa.entity.sysuser.SysUser;
import com.oa.entity.workflow.HisProcessEntity;
import com.oa.entity.workflow.OaLetter;
import com.oa.entity.workflow.OaProcessDefinition;
import com.oa.entity.workflow.TaskSearch;
import com.oa.utils.HighLightedFlowsUtil;
import com.oa.utils.NumberUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 流程控制层
 *
 * @author lzh
 * @create 2017-02-13-14:12
 */
@Controller
@RequestMapping("/admin/oa")
public class ProcessController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private WorkflowTraceService traceService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private HrHessianBiz hrHessianBiz;
    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    @Autowired
    private FlowTypeBiz flowTypeBiz;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private OaApprovalBiz oaApprovalBiz;
    @Autowired
    private OaLetterBiz oaLetterBiz;
    @Autowired
    private OaCarApplyBiz oaCarApplyBiz;
    @Autowired
    private OaMeetingBiz oaMeetingBiz;
    @Autowired
    private OaMeetingTopicBiz oaMeetingTopicBiz;
    @Autowired
    private OaArchiveSearchBiz oaArchiveSearchBiz;
    @Autowired
    private OaLeaveBiz oaLeaveBiz;
    @Autowired
    private OaNewsBiz oaNewsBiz;
    @Autowired
    private OaSealBiz oaSealBiz;
    @Autowired
    private OaMeetingAgendaBiz oaMeetingAgendaBiz;
    @Autowired
    private BaseHessianService baseHessianService;

    private static final String processList = "/workflow/process/process_list";
    private static final String dynamicForm = "/workflow/form/dynamic_form";
    private static final String processHistoryList = "/workflow/process/process_history_list";
    private static final String processRunningList = "/workflow/process/process_running_list";
    private static final String processSetCategory = "/workflow/process/process_def_category";

    @InitBinder({"oaApproval"})
    public void initOaApproval(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("oaApproval.");
    }

    @InitBinder("taskSearch")
    public void intAuditSearch(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("taskSearch.");
    }

    /**
     * @Description: 查看所有流程列表
     * @author: lzh
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 14:20
     */
    @RequestMapping("/process/list")
    public String processList(HttpServletRequest request,
                              @ModelAttribute("pagination") Pagination pagination,
                              RedirectAttributes attr) {
        try {
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
            //流程定义列表
            pagination.setRequest(request);
            int totalSize = processDefinitionQuery.list().size();
            int offset = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
            List<ProcessDefinition> processDefinitions = processDefinitionQuery.listPage(offset, pagination.getPageSize());
            List<OaProcessDefinition> oaProcessDefinitions = processDefinitions.stream().map(processDefinition -> {
                OaProcessDefinition opProcessDefinition = new OaProcessDefinition();
                opProcessDefinition.setId(processDefinition.getId());
                opProcessDefinition.setName(processDefinition.getName());
                String category = processDefinition.getCategory();
                if (!StringUtils.isEmpty(category) && NumberUtils.isNumeric(category)) {
                    FlowType flowType = flowTypeBiz.findById(Long.parseLong(category));
                    if (flowType != null) {
                        category = flowType.getName();
                    }
                }
                opProcessDefinition.setCategory(category);
                opProcessDefinition.setDiagramResourceName(processDefinition.getDiagramResourceName());
                opProcessDefinition.setResourceName(processDefinition.getResourceName());
                opProcessDefinition.setDeploymentId(processDefinition.getDeploymentId());
                opProcessDefinition.setVersion(processDefinition.getVersion());
                if (processDefinition.isSuspended()) {
                    opProcessDefinition.setSuspensionState(2);
                } else {
                    opProcessDefinition.setSuspensionState(1);
                }
                return opProcessDefinition;
            }).collect(Collectors.toList());
            request.setAttribute("processDefinitions", oaProcessDefinitions);
            pagination.init(totalSize, pagination.getPageSize(), pagination.getCurrentPage());
            request.setAttribute("pagination", pagination);
            Map<String, String> map = (Map<String, String>) attr.getFlashAttributes();
            request.setAttribute("message", map.get("message"));
        } catch (Exception e) {
            logger.error("ProcessController.processList", e);
            return this.setErrorPath(request, e);
        }
        return processList;
    }

    /**
     * @param processDefinitionId 流程定义
     * @param resourceType        资源类型(xml|image)
     * @Description: 读取资源，通过部署id
     * @author: lzh
     * @Param: [processDefinitionId, resourceType, response]
     * @Return: void
     * @Date: 15:15
     */
    @RequestMapping("/process/resource/read")
    public void loadByDeployment(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceType") String resourceType,
                                 HttpServletResponse response) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            String resourceName = "";
            if (resourceType.equals("image")) {
                resourceName = processDefinition.getDiagramResourceName();
            } else if (resourceType.equals("xml")) {
                resourceName = processDefinition.getResourceName();
            }
            InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (Exception e) {
            logger.error("ProcessController.loadByDeployment", e);
        }
    }

    /**
     * @Description: 流程挂起或者激活
     * @author: lzh
     * @Param: [processDefinitionId, status] status = "active":激活 status = "suspend" 挂起
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 15:52
     */
    @RequestMapping("/process/update")
    @ResponseBody
    public Map<String, Object> updateProcessState(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("status") String status) {
        Map<String, Object> json = null;
        try {
            if ("active".equals(status)) {
                repositoryService.activateProcessDefinitionById(processDefinitionId);
                json = this.resultJson(ErrorCode.SUCCESS, "激活成功", null);
            }
            if ("suspend".equals(status)) {
                repositoryService.suspendProcessDefinitionById(processDefinitionId);
                json = this.resultJson(ErrorCode.SUCCESS, "挂起成功", null);
            }
        } catch (Exception e) {
            logger.error("ProcessController.updateProcessState", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 删除发布的流程
     * @author: lzh
     * @Param: [deploymentId]
     * @Return: map
     * @Date: 17:25
     */
    @RequestMapping("/process/del")
    @ResponseBody
    public Map<String, Object> delProcess(@RequestParam("flag") int flag,
                                          @RequestParam("deploymentId") String deploymentId) {
        Map<String, Object> resultMap = null;
        try {
            if (flag == 0) {
                repositoryService.deleteDeployment(deploymentId);
            }
            if (flag == 1) {
                repositoryService.deleteDeployment(deploymentId, true);
            }
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SYS_ERROR_MSG, null);
        } catch (Exception e) {
            logger.error("ProcessController.delProcess", e);
            System.out.println("---------------" + e.getClass().getSimpleName());
            if ("PersistenceException".equals(e.getClass().getSimpleName())) {
                resultMap = this.resultJson(ErrorCode.ERROR_SYSTEM, "有正在运行的流程，不能删除", null);
            } else {
                resultMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
            }
        }
        return resultMap;
    }

    /**
     * @Description: 根据流程图动态生成表单
     * @author: lzh
     * @Param: [request, processDefinitionId]
     * @Date: 16:24
     */
    @RequestMapping("/process/form/find/start")
    @ResponseBody
    public Map<String, Object> findStartForm(HttpServletRequest request, @RequestParam("processDefinitionId") String processDefinitionId) {
        Map<String, Object> json = new HashMap<String, Object>();
        try {
            Map<String, Object> result = new HashMap<>();

            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            //如果是外置表单的话，走外置表单
//            if (processDefinition.hasStartFormKey()) {
//                Object startForm = formService.getRenderedStartForm(processDefinitionId);
//                json = this.resultJson(ErrorCode.SUCCESS, "formKey", startForm);
//                return json;
//            }

            StartFormDataImpl startFormData = (StartFormDataImpl) formService.getStartFormData(processDefinitionId);
            startFormData.setProcessDefinition(null);
            List<FormProperty> formProperties = startFormData.getFormProperties();
            formProperties.forEach(formProperty -> {
                Map<String, String> values = (Map<String, String>) formProperty.getType().getInformation("values");
                if (values != null) {
                    for (Map.Entry<String, String> enumEntry : values.entrySet()) {
                        logger.debug("enum, key: {}, value: {}", enumEntry.getKey(), enumEntry.getValue());
                    }
                    result.put("enum_" + formProperty.getId(), values);
                }
            });
            result.put("form", startFormData);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, result);
        } catch (Exception e) {
            logger.error("ProcessController.findStartForm", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 跳到表单页面，表单数据，格式由程序自动生成
     * @author: lzh
     * @Param: [request, processDefinitionId]
     * @Return: java.lang.String
     * @Date: 10:46
     */
    @RequestMapping("/to/dynamic/page")
    public String toDynamicPage(HttpServletRequest request, @RequestParam("processDefinitionId") String processDefinitionId) {
        try {
            Map<String, String> sysUser = SysUserUtils.getLoginSysUser(request);
            String applyName = "";
            int leaderLevel = 0;
            Long userId=Long.parseLong(sysUser.get("id"));
            if (sysUser != null && sysUser.size() > 0) {
                applyName = sysUser.get("userName");
                Map<String, String> departmentMap = baseHessianBiz.queryDepartmentBySysUserId(userId);
                if (departmentMap != null) {
                    request.setAttribute("departmentName", departmentMap.get("departmentName"));
                }
                if("2".equals(sysUser.get("userType")) && !"0".equals(sysUser.get("linkId"))){
                    Employee teacher=hrHessianBiz.queryEmployeeById(Long.valueOf(sysUser.get("linkId")));
                    if(ObjectUtils.isNotNull(teacher)){
                        request.setAttribute("position", teacher.getPosition());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if(ObjectUtils.isNotNull(teacher.getWorkTime())){
                            Date workTime=sdf.parse(teacher.getWorkTime());
                            request.setAttribute("workTime", workTime);
                        }
                    }
                }

                List<Map<String, String>> roleList=baseHessianBiz.queryUserRoleInfoByUserId(userId);
                if(roleList!=null && roleList.size()>0){
                    for(Map<String, String> map:roleList){
                        if(map.get("roleName")!=null && map.get("roleName").indexOf("常务副校长")>-1){//常务副校长
                            leaderLevel=4;
                            break;
                        }
                    }
                    if(leaderLevel==0){
                        for(Map<String, String> map:roleList){
                            if("分管校领导".equals(map.get("roleName")) || "调研员".equals(map.get("roleName")) || "副校长".equals(map.get("roleName"))){//分管校领导、调研员、副校长
                                leaderLevel=3;
                                break;
                            }
                        }
                    }
                }
                if(leaderLevel==0){
                    if(departmentMap != null && "0".equals(departmentMap.get("parentId"))){ //部门领导
                        leaderLevel = 1;
                    }
                }
            }
            request.setAttribute("leaderLevel", leaderLevel);
            String view = dynamicForm;
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            String url = actTaskService.getFormKey(processDefinitionId);
            if (!StringUtils.isEmpty(url)) {
                view = url;
            }
            request.setAttribute("processDefinition", processDefinition);
            request.setAttribute("applyName", applyName);
            request.setAttribute("nowDate", new Date());
            return view;

        }catch (Exception e){
            logger.error("ProcessController.toDynamicPage", e);
        }
        return null;
    }


    /**
     * @Description: 启动对应的流程
     * @author: lzh
     * @Param: [processDefinitionId, processType, request]
     * @Return: java.util.map
     * @Date: 11:55
     */
    @RequestMapping("/process/start")
    @ResponseBody
    public Map<String, Object> submitStartFormAndStartProcessInstance(@RequestParam("processDefinitionId") String processDefinitionId,
                                                                      @RequestParam(value = "processType", required = false) String processType,
                                                                      HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        Map<String, String> formProperties = new HashMap<String, String>();
        // 从request中读取参数然后转换
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entrySet) {
            String key = entry.getKey();
            // fp_的意思是form paremeter
            if (key.startsWith("fp_")) {
                formProperties.put(key.substring(3), entry.getValue()[0]);
            }
        }
        logger.debug("start form parameters: {}", formProperties);
        ProcessInstance processInstance = null;
        try {
            identityService.setAuthenticatedUserId(String.valueOf(SysUserUtils.getLoginSysUserId(request)));
            processInstance = formService.submitStartFormData(processDefinitionId, formProperties);
            logger.debug("start a processinstance: {}", processInstance);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("ProcessController.submitStartFormAndStartProcessInstance", e);
            resultMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return resultMap;
    }

    /**
     * @Description: 正在运行的流程
     * @author: lzh
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 16:02
     */
    @RequestMapping("/process/running")
    public String processRunning(HttpServletRequest request,
                                 @ModelAttribute("pagination") Pagination pagination,
                                 @RequestParam(value = "processDefKey", required = false) String processDefKey) {
        Long userId = SysUserUtils.getLoginSysUserId(request);
        pagination.setRequest(request);
        int totalSize = 0;
        List<ProcessInstance> processInstances = null;
        //如果没有传流程定义值
        if (StringUtils.isEmpty(processDefKey)) {
            totalSize = runtimeService.createProcessInstanceQuery()
                    .involvedUser(String.valueOf(userId))
                    .orderByProcessInstanceId().desc()
                    .active()
                    .list()
                    .size();
            int offset = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
            processInstances = runtimeService.createProcessInstanceQuery()
                    .involvedUser(String.valueOf(userId))
                    .orderByProcessInstanceId()
                    .desc()
                    .active()
                    .listPage(offset, pagination.getPageSize());
        } else {
            Set<String> processDefKeys = new HashSet<>();
            processDefKeys.add(processDefKey);
            //如果有传流程定义值

            if (ProcessConstantsEnum.CONFERENCE_TOPIC.getUrl().equals(processDefKey)) {
                processDefKeys.add(ProcessConstantsEnum.SIMPLE_CONFERENCE_TOPIC.getUrl());
            }
            totalSize = runtimeService.createProcessInstanceQuery()
                    .involvedUser(String.valueOf(userId))
                    .processDefinitionKeys(processDefKeys)
                    .orderByProcessInstanceId().desc()
                    .active()
                    .list()
                    .size();
            int offset = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
            processInstances = runtimeService.createProcessInstanceQuery()
                    .involvedUser(String.valueOf(userId))
                    .processDefinitionKeys(processDefKeys)
                    .orderByProcessInstanceId()
                    .desc()
                    .active()
                    .listPage(offset, pagination.getPageSize());
        }

        pagination.init(totalSize, pagination.getPageSize(), pagination.getCurrentPage());
        List<Map<String, String>> instances = null;
        if (processInstances != null && processInstances.size() > 0) {
            instances = processInstances.stream()
                    .map(processInstance -> {
                        return this.convertProcessInfoToMap(processInstance);
                    })
                    .collect(Collectors.toList());
        }
        instances.forEach(e -> {
            String processDefinitionKey = e.get("processDefinitionKey");
            String processInstanceId = e.get("id");
            if ("oa_car_apply".equals(processDefinitionKey)) {
                List<OaCarApply> list = oaCarApplyBiz.find(null, " processInstanceId=" + processInstanceId);
                e.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
            }else if ("oa_conference_topic_apply".equals(processDefinitionKey) || "oa_simple_conference_apply".equals(processDefinitionKey)) {
                List<OaMeetingTopic> list = oaMeetingTopicBiz.find(null, " processInstanceId=" + processInstanceId);
                e.put("reason", list.size() == 0 ? "无" : list.get(0).getName());
            }else if ("oa_agenda_apply".equals(processDefinitionKey)) {
                List<OaMeetingAgenda> list = oaMeetingAgendaBiz.find(null, " processInstanceId=" + processInstanceId);
                e.put("reason", list.size() == 0 ? "无" : list.get(0).getLocation());
            }else if ("oa_letter_apply".equals(processDefinitionKey) || "oa_inner_letter".equals(processDefinitionKey)) {
                List<OaLetter> list = oaLetterBiz.find(null, " processInstanceId=" + processInstanceId);
                e.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
            }else if ("oa_archive_apply".equals(processDefinitionKey)) {
                List<OaArchiveSearch> list = oaArchiveSearchBiz.find(null, " processInstanceId=" + processInstanceId);
                e.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
            }else if ("oa_leave_apply".equals(processDefinitionKey)) {
                List<OaLeave> list = oaLeaveBiz.find(null, " processInstanceId=" + processInstanceId);
                e.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
            }else if ("oa_news_apply".equals(processDefinitionKey)) {
                List<OaNews> list = oaNewsBiz.find(null, " processInstanceId=" + processInstanceId);
                e.put("reason", list.size() == 0 ? "无" : list.get(0).getTitle());
            }else if ("oa_seal_apply".equals(processDefinitionKey)) {
                List<OaSeal> list = oaSealBiz.find(null, " processInstanceId=" + processInstanceId);
                e.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
            }
        });
        request.setAttribute("processInstances", instances);
        request.setAttribute("pagination", pagination);
        return processRunningList;
    }

    @RequestMapping("/process/trace")
    @ResponseBody
    public List<Map<String, Object>> traceProcess(@RequestParam("processInstanceId") String processInstanceId) throws Exception {
        List<Map<String, Object>> activityInfos = traceService.traceProcess(processInstanceId);
        return activityInfos;
    }

    /**
     * @Description: 我发起的
     * @author: lzh
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 16:17
     */
    @RequestMapping("/task/history/mine")
    public String myStartHisTask(HttpServletRequest request,
                                 @ModelAttribute("pagination") Pagination pagination,
                                 @ModelAttribute("taskSearch")TaskSearch taskSearch,
                                 @RequestParam(value = "processDefKey", required = false) String processDefKey) {
        try {
            pagination.setPageSize(10);
            Long userId = SysUserUtils.getLoginSysUserId(request);
            pagination.setRequest(request);
            int totalSize=0;
            List<Map<String, Object>> maps = null;
            if (StringUtils.isEmpty(processDefKey)) {
                int offset = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
                HistoricProcessInstanceQuery historicProcessInstanceQuery=historyService.createHistoricProcessInstanceQuery()
                        .startedBy(String.valueOf(userId))
                        .orderByProcessInstanceStartTime()
                        .desc();

                //标题查询
                if (!StringUtils.isTrimEmpty(taskSearch.getProcessDefName())) {
                    historicProcessInstanceQuery = historicProcessInstanceQuery.processDefinitionName(taskSearch.getProcessDefName());
                }

                //类型查询
                if (!StringUtils.isTrimEmpty(taskSearch.getProcessDefKey())) {
                    historicProcessInstanceQuery = historicProcessInstanceQuery.processDefinitionKey(taskSearch.getProcessDefKey());
                }

                //申请时间查询
                if (taskSearch.getApplyStartTime() != null) {
                    historicProcessInstanceQuery.startedAfter(taskSearch.getApplyStartTime());
                }

                //申请时间查询
                if (taskSearch.getApplyEndTime() != null) {
                    historicProcessInstanceQuery.startedBefore(taskSearch.getApplyEndTime());
                }
                totalSize = historicProcessInstanceQuery.list().size();
                List<HistoricProcessInstance> historicProcessInstances=historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc().listPage(offset, pagination.getPageSize());
                maps = historicProcessInstances.stream()
                        .map(historicProcessInstance -> {
                            return packageHisProcess(historicProcessInstance);
                        })
                        .collect(Collectors.toList());
            } else {
                List<String> processDefKeys = new LinkedList<>();
                processDefKeys.add(processDefKey);
                if (ProcessConstantsEnum.CONFERENCE_TOPIC.getUrl().equals(processDefKey)) {
                    processDefKeys.add(ProcessConstantsEnum.AGENDA.getUrl());
                }
                totalSize = historyService.createHistoricProcessInstanceQuery()
                        .startedBy(String.valueOf(userId))
                        .processDefinitionKeyIn(processDefKeys)
                        .list()
                        .size();
                int offset = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
                List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionKeyIn(processDefKeys)
                        .startedBy(String.valueOf(userId))
                        .orderByProcessInstanceStartTime()
                        .desc()
                        .listPage(offset, pagination.getPageSize());
                maps = historicProcessInstances.stream()
                        .map(historicProcessInstance -> {
                            return packageHisProcess(historicProcessInstance);
                        })
                        .collect(Collectors.toList());
            }


            request.setAttribute("maps", maps);
            pagination.init(totalSize, pagination.getPageSize(), pagination.getCurrentPage());
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("TaskController.myStartHisTask", e);
            return this.setErrorPath(request, e);
        }
        return processHistoryList;
    }

    /**
     * @Description: 以图的形式显示运行的流程到哪一步了
     * @author: lzh
     * @Param: [response, processInstanceId]
     * @Return: void
     * @Date: 11:57
     */
    @RequestMapping("/process/info")
    public void getProcessInfo(HttpServletResponse response, @RequestParam("processInstanceId") String processInstanceId) {
        try {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
            List<String> activityIds = runtimeService.getActiveActivityIds(processInstanceId);
            DefaultProcessDiagramGenerator gen = new DefaultProcessDiagramGenerator();
            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                    .executionId(processInstanceId)
                    .orderByHistoricActivityInstanceStartTime()
                    .asc()
                    .list();
            // 计算活动线
            List<String> highLightedFlows = HighLightedFlowsUtil.getHighLightedFlows(
                    (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                            .getDeployedProcessDefinition(processInstance.getProcessDefinitionId()),
                    historicActivityInstances);

            InputStream in = gen.generateDiagram(bpmnModel, "png", activityIds, highLightedFlows, "宋体", "宋体", null, null, 100.0);
            ServletOutputStream output = response.getOutputStream();
            IOUtils.copy(in, output);
        } catch (Exception e) {
            logger.error("CarApplyController.getCarProcessInfo", e);
        }
    }

    /**
     * @Description: 包装历史流程信息
     * @author: lzh
     * @Param: [historicProcessInstance]
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Date: 18:01
     */
    private Map<String, Object> packageHisProcess(HistoricProcessInstance historicProcessInstance) {
        Map<String, Object> map = new HashMap<>();
        map.put("pId", historicProcessInstance.getId());
        map.put("pDefId", historicProcessInstance.getProcessDefinitionId());
        map.put("pDefName", historicProcessInstance.getProcessDefinitionName());
        map.put("pDefKey", historicProcessInstance.getProcessDefinitionKey());
        map.put("pStartTime", historicProcessInstance.getStartTime());
        map.put("pEndTime", historicProcessInstance.getEndTime());
        String startName = baseHessianBiz.getSysUserById(Long.parseLong(historicProcessInstance.getStartUserId())).getUserName();
        map.put("pStartName", startName);
        String instanceId = historicProcessInstance.getId();
        List<HistoricActivityInstance> instances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(instanceId)
                .orderByHistoricActivityInstanceStartTime()
                .desc()
                .list();
        String currActivityName = "";
        if (instances != null && instances.size() > 0) {
            int i = instances.size() - 1;
            while ("exclusiveGateway".equals(instances.get(i).getActivityType()) && i > 0) {
                i--;
            }
            currActivityName = instances.get(i).getActivityName();
        }
        map.put("currActivityName", currActivityName);
        if ("oa_car_apply".equals(historicProcessInstance.getProcessDefinitionKey())) {
            List<OaCarApply> list = oaCarApplyBiz.find(null, " processInstanceId=" + historicProcessInstance.getId());
            map.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_conference_topic_apply".equals(historicProcessInstance.getProcessDefinitionKey()) || "oa_simple_conference_apply".equals(historicProcessInstance.getProcessDefinitionKey())) {
            List<OaMeetingTopic> list = oaMeetingTopicBiz.find(null, " processInstanceId=" + historicProcessInstance.getId());
            map.put("reason", list.size() == 0 ? "无" : list.get(0).getName());
        }else if ("oa_agenda_apply".equals(historicProcessInstance.getProcessDefinitionKey())) {
            List<OaMeetingAgenda> list = oaMeetingAgendaBiz.find(null, " processInstanceId=" + historicProcessInstance.getId());
            map.put("reason", list.size() == 0 ? "无" : list.get(0).getLocation());
        }else if ("oa_letter_apply".equals(historicProcessInstance.getProcessDefinitionKey()) || "oa_inner_letter".equals(historicProcessInstance.getProcessDefinitionKey())) {
            List<OaLetter> list = oaLetterBiz.find(null, " processInstanceId=" + historicProcessInstance.getId());
            map.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_archive_apply".equals(historicProcessInstance.getProcessDefinitionKey())) {
            List<OaArchiveSearch> list = oaArchiveSearchBiz.find(null, " processInstanceId=" + historicProcessInstance.getId());
            map.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_leave_apply".equals(historicProcessInstance.getProcessDefinitionKey())) {
            List<OaLeave> list = oaLeaveBiz.find(null, " processInstanceId=" + historicProcessInstance.getId());
            map.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_news_apply".equals(historicProcessInstance.getProcessDefinitionKey())) {
            List<OaNews> list = oaNewsBiz.find(null, " processInstanceId=" + historicProcessInstance.getId());
            map.put("reason", list.size() == 0 ? "无" : list.get(0).getTitle());
        }else if ("oa_seal_apply".equals(historicProcessInstance.getProcessDefinitionKey())) {
            List<OaSeal> list = oaSealBiz.find(null, " processInstanceId=" + historicProcessInstance.getId());
            map.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }
        return map;
    }

    /**
     * @Description: 查看已经结束的详情, 将任务属性，和表单属性封装到一个map里面
     * @author: lzh
     * @Param: [request, processInstanceId]
     * @Return: java.lang.String
     * @Date: 15:05
     */
    @RequestMapping("/history/process/info")
    public String getHistoryProcessInfo(HttpServletRequest request,
                                        @RequestParam("processInstanceId") String processInstanceId,
                                        @RequestParam("processDefinitionKey") String processDefinitionKey) {

        Long userId = SysUserUtils.getLoginSysUserId(request);
        if (ProcessConstantsEnum.NEWS.getUrl().equals(processDefinitionKey)) {
            List<Map<String, String>> userMaps = baseHessianBiz.queryUserRoleInfoByUserId(userId);
            if (userMaps != null && userMaps.size() > 0) {
                int flag = 0;
                for (Map<String, String> map : userMaps) {
                    String name = map.get("roleName");
                    if ("办公室(信息管理员)".equals(name)) {
                        flag = 1;
                        break;
                    }
                }
                request.setAttribute("flag", flag);
            }
        }

        List<HisProcessEntity> hisProcessEntities = null;
        hisProcessEntities = workflowFormBiz.packageHisProcessInfo(processInstanceId);
        Map<String, String> departMentMap = baseHessianBiz.queryDepartmentBySysUserId(Long.parseLong(hisProcessEntities.get(0).getApplyId()));
        if (departMentMap != null && departMentMap.size() > 0) {
            request.setAttribute("departmentName", departMentMap.get("departmentName"));
        }
        request.setAttribute("userId", userId);
        request.setAttribute("hisProcessEntities", hisProcessEntities);
        request.setAttribute("applyName", hisProcessEntities.get(0).getAssignee());
        ContextStatus contextStatus = new ContextStatus(request, processInstanceId, processDefinitionKey);
        return contextStatus.handle();
    }

    /**
     * @Description: 进入分类设置也面
     * @author: lzh
     * @Param: [request, processDefinitionId]
     * @Return: java.lang.String
     * @Date: 16:28
     */
    @RequestMapping("/to/category/set")
    public String toCategorySet(HttpServletRequest request, @RequestParam("processDefinitionId") String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String category = processDefinition.getCategory();
        String categoryName = category;
        List<FlowType> flowTypes = flowTypeBiz.findAll();
        request.setAttribute("processDefinitionName", processDefinition.getName());
        request.setAttribute("processDefinitionId", processDefinitionId);
        if (!StringUtils.isEmpty(category) && NumberUtils.isNumeric(category)) {
            categoryName = flowTypeBiz.findById(Long.parseLong(category)).getName();
        }
        request.setAttribute("category", category);
        request.setAttribute("categoryName", categoryName);
        request.setAttribute("flowTypes", gson.toJson(flowTypes));
        return processSetCategory;
    }

    /**
     * @Description: 修改目录
     * @author: lzh
     * @Param: [processDefinitionId, category]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 16:36
     */
    @RequestMapping("/category/set")
    @ResponseBody
    public Map<String, Object> categorySet(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("category") String category) {
        Map<String, Object> json = null;
        try {
            repositoryService.setProcessDefinitionCategory(processDefinitionId, category);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("CarApplyController.categorySet", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 将流程示例转换成map
     *
     * @param processInstance
     * @return
     */
    private Map<String, String> convertProcessInfoToMap(ProcessInstance processInstance) {
        Map<String, String> map = new HashMap<>();
        if (processInstance != null) {
            String instanceId = processInstance.getProcessInstanceId();
            List<HistoricActivityInstance> instances = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(instanceId)
                    .orderByHistoricActivityInstanceStartTime()
                    .asc()
                    .list();
            String currActivityName = "";
            if (instances != null && instances.size() > 0) {
                int i = instances.size() - 1;
                while ("exclusiveGateway".equals(instances.get(i).getActivityType()) && i > 0) {
                    i--;
                }
                currActivityName = instances.get(i).getActivityName();
            }
            map.put("id", instanceId);
            map.put("processDefinitionName", processInstance.getProcessDefinitionName());
            map.put("processDefinitionId", processInstance.getProcessDefinitionId());
            map.put("currActivityName", currActivityName);
            map.put("processDefinitionKey", processInstance.getProcessDefinitionKey());
        }
        return map;
    }

    /**
     * ajax查询后台用户
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping("/ajax/selectSysUserList")
    public String selectEmployeeList(HttpServletRequest request,
                                           @ModelAttribute("pagination") Pagination pagination,
                                           @RequestParam(value = "departmentId", required = false) Long departmentId,
                                           @RequestParam(value = "userType", required = false) Integer userType,
                                           @RequestParam(value = "selectType", required = false) Integer selectType) {
        ModelAndView modelAndView = new ModelAndView("/letter/select_leaderUser_list");
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
                    where += " and ( linkId in (" + employeeIds.get().substring(0, employeeIds.get().length() - 1) + ") or departmentId in (" +departmentIds+ ",22) )";
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
        return "/letter/select_leaderUser_list";
    }

    /**
     * 我收到的发布
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/process/approvalList")
    public String approvalList(HttpServletRequest request,@ModelAttribute("oaApproval") OaApproval oaApproval,
                               @ModelAttribute("pagination") Pagination pagination) {
        try {
            pagination.setRequest(request);
            boolean isSend = false;
            Long userId = SysUserUtils.getLoginSysUserId(request);
            Map<String, String> map = SysUserUtils.getLoginSysUser(request);
            DepartMent departMent = baseHessianBiz.queryDepartemntById(Long.valueOf(map.get("departmentId")));
            if (departMent.getParentId() == 0) {
                isSend = true;
            }
            String sql = "status = 1 and sysUserId =" + userId;
            if(oaApproval.getApprovalStatus()!=null){
                sql+=" and approvalStatus="+oaApproval.getApprovalStatus();
            }
            if(ObjectUtils.isNotNull(oaApproval.getSenderName()) && !"".equals(oaApproval.getSenderName())){
                sql+=" and senderName like '%"+oaApproval.getSenderName()+"%'";
            }
            if(ObjectUtils.isNotNull(oaApproval.getTitle()) && !"".equals(oaApproval.getTitle())){
                sql+=" and title like '%"+oaApproval.getTitle()+"%'";
            }
            sql+=" ORDER BY updateTime DESC";
            List<OaApproval> oaApprovalList = oaApprovalBiz.find(pagination, sql);
            request.setAttribute("oaApprovalList", oaApprovalList);
            request.setAttribute("oaApproval", oaApproval);
            request.setAttribute("isSend", isSend);
        } catch (Exception e) {
            logger.error("ProcessController.approvalList", e);
            return this.setErrorPath(request, e);
        }
        return "/letter/oa_approval_list";
    }

    /**
     * 我发布的
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/process/approvalRecordsList")
    public String approvalRecordsList(HttpServletRequest request,@ModelAttribute("oaApproval") OaApproval oaApproval,
                               @ModelAttribute("pagination") Pagination pagination) {
        try {
            Date now=new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String sql = "status = 1 and sender =" + userId;
            if(oaApproval.getApprovalStatus()!=null){
                if(oaApproval.getApprovalStatus()==2){
                    sql+=" and effectiveTime < '"+ sdf.format(now) +"'";
                }else {
                    sql+=" and approvalStatus="+oaApproval.getApprovalStatus();
                }
            }
            if(ObjectUtils.isNotNull(oaApproval.getSysUserName()) && !"".equals(oaApproval.getSysUserName())){
                sql+=" and sysUserName like '%"+oaApproval.getSysUserName()+"%'";
            }
            if(ObjectUtils.isNotNull(oaApproval.getTitle()) && !"".equals(oaApproval.getTitle())){
                sql+=" and title like '%"+oaApproval.getTitle()+"%'";
            }
            sql+=" ORDER BY updateTime DESC";
            List<OaApproval> oaApprovalList = oaApprovalBiz.find(pagination, sql);
            request.setAttribute("oaApprovalList", oaApprovalList);
            request.setAttribute("now", now);
        } catch (Exception e) {
            logger.error("ProcessController.approvalRecordsList", e);
            return this.setErrorPath(request, e);
        }
        return "/letter/oa_approval_records_list";
    }

    /**
     * 增加发布
     *
     * @param id
     * @return
     */
    @RequestMapping("/ajax/addApproval")
    @ResponseBody
    public Map<String, Object> addApproval(HttpServletRequest request,
                                           @RequestParam("id") String id,
                                           @RequestParam("type") Integer type,
                                           @RequestParam("letterId") Long letterId,
                                           @RequestParam("effectiveTime") String effectiveTime,
                                           @RequestParam(required = false, value = "timeStamp") String timeStamp) {
        Map<String, Object> json = null;
        try {
            String ids[];
            if (id.length() > 1) {
                ids = id.split(",");
            } else {
                ids = new String[1];
                ids[0] = id;
            }
            OaLetter oaLetter = oaLetterBiz.findById(letterId);
            if (ObjectUtils.isNull(timeStamp)) {
                timeStamp = oaLetter.getTimeStamp();
            }

            java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
            Date date =  formatter.parse(effectiveTime);
            Map<String, String> sender=SysUserUtils.getLoginSysUser(request);
            for (String str : ids) {
                SysUser sysUser = baseHessianBiz.getSysUserById(Long.valueOf(str));
                OaApproval oaApproval = new OaApproval();
                oaApproval.setCreateTime(new Timestamp(System.currentTimeMillis()));
                oaApproval.setSysUserId(Long.valueOf(str));
                oaApproval.setTitle(oaLetter.getReason());
                oaApproval.setType(type);
                oaApproval.setEffectiveTime(date);
                oaApproval.setLetterType(oaLetter.getType());
                oaApproval.setSysUserName(sysUser.getUserName());
                oaApproval.setSender(Long.valueOf(sender.get("id")));
                oaApproval.setSenderName(sender.get("userName"));
                oaApproval.setLetterId(oaLetter.getId());
                oaApproval.setProcessInstanceId(oaLetter.getProcessInstanceId());
                oaApproval.setTimeStamp(timeStamp);
                oaApprovalBiz.save(oaApproval);
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("ProcessController.addApproval", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    @RequestMapping("/ajax/selectDepartmentList")
    public ModelAndView selectDepartmentList(HttpServletRequest request,@RequestParam(value = "username",required = false) String username) {
        ModelAndView modelAndView = new ModelAndView("/letter/select_sysUser_list");
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            List<SysUser> sysUsers = baseHessianBiz.getSysUserList(userId,username);
            modelAndView.addObject("sysUsers", sysUsers);
        } catch (Exception e) {
            logger.error("selectDepartmentList", e);
        }
        return modelAndView;
    }

    /**
     * 我发出去的
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/process/senderList")
    public String senderList(HttpServletRequest request,
                             @ModelAttribute("pagination") Pagination pagination,
                             @RequestParam("processInstanceId") String processInstanceId) {
        try {
            String sql = "1 = 1 and status = 1 and sender =" + SysUserUtils.getLoginSysUserId(request) + " and processInstanceId ='" + processInstanceId + "' ORDER BY updateTime DESC";
            List<OaApproval> oaApprovalList = oaApprovalBiz.find(pagination, sql);
            request.setAttribute("oaApprovalList", oaApprovalList);
            request.setAttribute("isSend", false);
        } catch (Exception e) {
            logger.error("ProcessController.senderList", e);
            return this.setErrorPath(request, e);
        }
        return "/letter/oa_approval_list";
    }

    /**
     * @Description: 设置已读状态
     * @author: jin shuo
     * @Param: [processDefinitionId, category]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 16:36
     */
    @RequestMapping("/ajax/setRead")
    @ResponseBody
    public Map<String, Object> setRead(HttpServletRequest request,HttpServletResponse response,
                                       @RequestParam("letterId") Long letterId) {
        Map<String, Object> json = null;
        String url = request.getHeader("referer");
        url=url.substring(0,url.indexOf("/",url.indexOf("//")+2));
        response.setHeader("Access-Control-Allow-Origin", url);
        response.setHeader("Access-Control-Allow-Headers", "Authentication");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        try {
            Long userId=SysUserUtils.getLoginSysUserId(request);
            OaApproval oaApproval=new OaApproval();
            oaApproval.setApprovalStatus(1);
            oaApprovalBiz.updateByStrWhere(oaApproval,"sysUserId=" + userId + " and letterId=" + letterId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("CarApplyController.setRead", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 重新发送
     * @author: jin shuo
     * @Param: [processDefinitionId, category]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 16:36
     */
    @RequestMapping("/ajax/resend")
    @ResponseBody
    public Map<String, Object> resend(HttpServletRequest request,@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            OaApproval oaApproval=new OaApproval();
            oaApproval.setApprovalStatus(0);
            oaApproval.setId(id);
            oaApprovalBiz.update(oaApproval);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("CarApplyController.resend", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }
}
