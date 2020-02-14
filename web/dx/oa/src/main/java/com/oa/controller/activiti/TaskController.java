package com.oa.controller.activiti;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.archivetype.OaArchiveSearchBiz;
import com.oa.biz.car.OaCarApplyBiz;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.conference.OaMeetingAgendaBiz;
import com.oa.biz.conference.OaMeetingTopicBiz;
import com.oa.biz.leave.OaLeaveBiz;
import com.oa.biz.letter.OaApprovalBiz;
import com.oa.biz.meetingapply.OaMeetingBiz;
import com.oa.biz.news.OaNewsBiz;
import com.oa.biz.seal.OaSealBiz;
import com.oa.biz.workflow.OaLetterBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.entity.archivetype.OaArchiveSearch;
import com.oa.entity.car.OaCarApply;
import com.oa.entity.conference.OaMeetingAgenda;
import com.oa.entity.conference.OaMeetingTopic;
import com.oa.entity.department.DepartMent;
import com.oa.entity.leave.OaLeave;
import com.oa.entity.letter.OaApproval;
import com.oa.entity.meetingapply.OaMeeting;
import com.oa.entity.news.OaNews;
import com.oa.entity.seal.OaSeal;
import com.oa.entity.sysuser.SysUser;
import com.oa.entity.workflow.HisProcessEntity;
import com.oa.entity.workflow.OaLetter;
import com.oa.entity.workflow.TaskSearch;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 任务控制层
 *
 * @author lzh
 * @create 2017-02-16-16:12
 */
@Controller
@RequestMapping("/admin/oa")
public class TaskController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();
    private static final String taskToClaimList = "/workflow/task/task_to_claim_list";
    private static final String taskToCompleteList = "/workflow/task/task_to_complete_list";
    private static final String taskToComplete = "/workflow/task/task_to_complete";
    private static final String taskFinishAudit = "/workflow/task/task_finish_audit_list";//我已经审批的列表

    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private HistoryService historyService;
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


    @InitBinder("taskSearch")
    public void intAuditSearch(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("taskSearch.");
    }
    /**
     * @Description: 我的待签收任务列表
     * @author: lzh
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 16:14
     */
    @RequestMapping("/task/to/claim/list")
    public String taskToClaimList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination,
                                  @ModelAttribute("taskSearch")TaskSearch taskSearch) {
        try {
            pagination.setPageSize(10);
            pagination.setRequest(request);
//            代办任务
            Long userId = SysUserUtils.getLoginSysUserId(request);
            TaskQuery taskQuery=taskService.createTaskQuery().active();
            taskQuery.or().taskAssignee(String.valueOf(userId)).taskCandidateGroup(String.valueOf(userId)).endOr();
            //标题查询
            if (!StringUtils.isTrimEmpty(taskSearch.getProcessDefName())) {
                taskQuery = taskQuery.processDefinitionName(taskSearch.getProcessDefName());
            }
            //类型查询
            if (!StringUtils.isTrimEmpty(taskSearch.getProcessDefKey())) {
                taskQuery = taskQuery.processDefinitionKey(taskSearch.getProcessDefKey());
            }
            //申请时间查询
            if (taskSearch.getApplyStartTime() != null) {
                taskQuery.taskCreatedAfter(taskSearch.getApplyStartTime());
            }

            //申请时间查询
            if (taskSearch.getApplyEndTime() != null) {
                taskQuery.taskCreatedBefore(taskSearch.getApplyEndTime());
            }

            //申请人查询
            if (!StringUtils.isTrimEmpty(taskSearch.getApplyName())) {
                SysUser sysUser = new SysUser();
                sysUser.setUserName(taskSearch.getApplyName());
                List<SysUser> sysUsers = baseHessianBiz.getSysUserList(sysUser);

                List<String> processInstanceIds = new LinkedList<>();
                if (sysUsers != null && sysUsers.size() > 0) {
                    for (SysUser user : sysUsers) {
                        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService
                                .createHistoricProcessInstanceQuery()
                                .startedBy(user.getId().toString());
                        List<HistoricProcessInstance> instances = historicProcessInstanceQuery.list();
                        if (instances != null && instances.size() > 0) {
                            for (HistoricProcessInstance _instance : instances) {
                                processInstanceIds.add(_instance.getId());
                            }
                        }
                    }
                }
                if (processInstanceIds != null && processInstanceIds.size() > 0) {
                    taskQuery = taskQuery.processInstanceIdIn(processInstanceIds);
                } else {
                    //查不到
                    taskQuery = taskQuery.processInstanceId("-1");
                }
            }


            int totalSize=taskQuery.list().size();
            int offset = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
            List<Task> toDoTasks=taskQuery.orderByTaskCreateTime().desc().listPage(offset, pagination.getPageSize());
            pagination.init(totalSize, pagination.getPageSize(), pagination.getCurrentPage());
            request.setAttribute("pagination", pagination);
            List<Map<String, Object>> taskMaps = taskConvertToTaskInfoList(toDoTasks);
            request.setAttribute("tasks", taskMaps);
//            List<Task> toDoTasks1 = taskService.createTaskQuery()
//                    .active()
//                    .taskAssignee(String.valueOf(userId))
//                    .orderByTaskCreateTime().desc()
//                    .list();
//            List<Task> toDoTasks2 = taskService.createTaskQuery()
//                    .taskCandidateGroup(String.valueOf(userId))
//                    .active()
//                    .orderByTaskCreateTime().desc()
//                    .list();
//            toDoTasks1.addAll(toDoTasks2);
//
//            List<Map<String, Object>> taskMaps = taskConvertToTaskInfoList(toDoTasks1);
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            //排序
//            if(taskMaps!=null && taskMaps.size()>1){ //一条数据没必要排序
//                Collections.sort(taskMaps,new Comparator<Map<String, Object>> () {
//                    @Override
//                    public int compare(Map<String, Object> t1, Map<String, Object> t2) {
//                        try {
//                            return sdf.parse(t1.get("createTime").toString()).getTime()>sdf.parse(t2.get("createTime").toString()).getTime()?-1:1;
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                            logger.error("TaskController.taskMeAudit", e);
//                            return 0;
//                        }
//                    }
//                });
//            }
//            request.setAttribute("tasks", taskMaps);
            logger.debug("toDoTask = {}", taskMaps);
        } catch(Exception e) {
            logger.error("TaskController.taskList",e);
            return this.setErrorPath(request, e);
        }
        return taskToClaimList;
    }



    /**
     * 我已审批的
     * @return
     */
    @RequestMapping("/task/me/audit")
    public String taskMeAudit(HttpServletRequest request,
                              @ModelAttribute("pagination") Pagination pagination,
                              @ModelAttribute("taskSearch")TaskSearch taskSearch) {
        pagination.setPageSize(10);
        Long userId = SysUserUtils.getLoginSysUserId(request);
        List<Map<String, Object>> tasks = null;
        try {
            // pageSize
            int pageSize = pagination.getPageSize();
            // 偏移量
            int offset = (pagination.getCurrentPage() - 1) * pageSize;

//            List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
//                    .finished()
//                    .taskAssignee(String.valueOf(userId))
//                    .orderByHistoricTaskInstanceEndTime()
//                    .processDefinitionKeyLikeIgnoreCase("")
//                    .desc()
//                    .listPage(offset, pageSize);
//            request.setAttribute("historicTaskInstances", historicTaskInstances);

           HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery()
                    .finished()
                    .taskAssignee(String.valueOf(userId))
                    .orderByTaskCreateTime()
                    .desc();

           //申请人查询
            if (!StringUtils.isTrimEmpty(taskSearch.getApplyName())) {
                SysUser sysUser = new SysUser();
                sysUser.setUserName(taskSearch.getApplyName());
                List<SysUser> sysUsers = baseHessianBiz.getSysUserList(sysUser);

                List<String> processInstanceIds = new LinkedList<>();
                if (sysUsers != null && sysUsers.size() > 0) {
                    for (SysUser user : sysUsers) {
                        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService
                                .createHistoricProcessInstanceQuery()
                                .startedBy(user.getId().toString());
                        List<HistoricProcessInstance> instances = historicProcessInstanceQuery.list();
                        if (instances != null && instances.size() > 0) {
                            for (HistoricProcessInstance _instance : instances) {
                                processInstanceIds.add(_instance.getId());
                            }
                        }
                    }
                }
                if (processInstanceIds != null && processInstanceIds.size() > 0) {
                    historicTaskInstanceQuery = historicTaskInstanceQuery.processInstanceIdIn(processInstanceIds);
                } else {
                    //查不到
                    historicTaskInstanceQuery = historicTaskInstanceQuery.processInstanceId("-1");
                }
            }
           //标题查询
           if (!StringUtils.isTrimEmpty(taskSearch.getProcessDefName())) {
//               historicTaskInstanceQuery = historicTaskInstanceQuery.processDefinitionNameLike("%" + taskSearch.getProcessDefName() + "%");
               historicTaskInstanceQuery = historicTaskInstanceQuery.processDefinitionName(taskSearch.getProcessDefName());
           }

            //标题查询
            if (!StringUtils.isTrimEmpty(taskSearch.getProcessDefKey())) {
                historicTaskInstanceQuery = historicTaskInstanceQuery.processDefinitionKey(taskSearch.getProcessDefKey());
            }

//           //申请时间查询
//           if (taskSearch.getApplyStartTime() != null && taskSearch.getApplyEndTime() != null) {
//           }

           //审核时间查询
           if (taskSearch.getAuditStartTime() != null) {
               historicTaskInstanceQuery.taskCompletedAfter(taskSearch.getAuditStartTime());
           }

            //审核时间查询
           if (taskSearch.getAuditEndTime() != null) {
               historicTaskInstanceQuery.taskCompletedBefore(taskSearch.getAuditEndTime());
           }

            // 总数量
            int totalSize = historicTaskInstanceQuery.list().size();

           List<HistoricTaskInstance> historicTaskInstances = historicTaskInstanceQuery.listPage(offset, pageSize);
           request.setAttribute("historicTaskInstances", historicTaskInstances);
            //如果我已审批的不为空
            if (historicTaskInstances != null && historicTaskInstances.size() > 0) {
               tasks = historicTaskInstances.stream()
                        .map(historicTaskInstance -> {
                            HistoricTaskInstanceEntity historicTaskInstanceEntity =  (HistoricTaskInstanceEntity) historicTaskInstance;
                            ProcessDefinition processDefinition = getProcessDefinition(historicTaskInstance.getProcessDefinitionId());
                            Map<String, Object> map = packageTaskInfo(historicTaskInstanceEntity, processDefinition);
                            return map;
                        })
                        .collect(Collectors.toList());

            }
            request.setAttribute("tasks", tasks);
            request.setAttribute("taskSearch", taskSearch);


            pagination.setRequest(request);
            pagination.init(totalSize, pageSize, pagination.getCurrentPage());
            request.setAttribute("pagination", pagination);
        } catch(Exception e) {
            logger.error("TaskController.taskMeAudit", e);
            return this.setErrorPath(request, e);
        }
        return taskFinishAudit;
    }

    /**
     * @Description: 从缓存中读取流程定义
     * @author: lzh
     * @Param: [processDefinitionId]
     * @Return: org.activiti.engine.repository.ProcessDefinition
     * @Date: 16:49
     */
    private ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
        if (processDefinition == null) {
            processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
        }
        return processDefinition;
    }

    /**
     * @Description: 将任务信息封装
     * @author: lzh
     * @Param: [sdf, task, processDefinition]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 16:49
     */
    private Map<String, Object> packageTaskInfo(SimpleDateFormat sdf, Task task, ProcessDefinition processDefinition) {
        Map<String, Object> singleTask = new HashMap<String, Object>();
        singleTask.put("id", task.getId());
        singleTask.put("name", task.getName());
        singleTask.put("pdname", processDefinition.getName());
        singleTask.put("pdfKey", processDefinition.getKey());
        singleTask.put("pid", task.getProcessInstanceId());
        singleTask.put("applyName", getUserNameAndTimeByProcessInstanceId(task.getProcessInstanceId()).get("userName"));
        singleTask.put("createTime", sdf.format(task.getCreateTime()));

        String instanceId = task.getProcessInstanceId();
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
        singleTask.put("currActivityName", currActivityName);
        if ("oa_car_apply".equals(processDefinition.getKey())) {
            List<OaCarApply> list = oaCarApplyBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_conference_topic_apply".equals(processDefinition.getKey()) || "oa_simple_conference_apply".equals(processDefinition.getKey())) {
            List<OaMeetingTopic> list = oaMeetingTopicBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getName());
        }else if ("oa_agenda_apply".equals(processDefinition.getKey())) {
            List<OaMeetingAgenda> list = oaMeetingAgendaBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getLocation());
        }else if ("oa_letter_apply".equals(processDefinition.getKey()) || "oa_inner_letter".equals(processDefinition.getKey())) {
            List<OaLetter> list = oaLetterBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_archive_apply".equals(processDefinition.getKey())) {
            List<OaArchiveSearch> list = oaArchiveSearchBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_leave_apply".equals(processDefinition.getKey())) {
            List<OaLeave> list = oaLeaveBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_news_apply".equals(processDefinition.getKey())) {
            List<OaNews> list = oaNewsBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getTitle());
        }else if ("oa_seal_apply".equals(processDefinition.getKey())) {
            List<OaSeal> list = oaSealBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }
        return singleTask;
    }

    /**
     * @Description: 将任务信息封装
     * @author: lzh
     * @Param: [sdf, task, processDefinition]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 16:57
     */
    private Map<String, Object> packageTaskInfo(HistoricTaskInstanceEntity task, ProcessDefinition processDefinition) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map<String, Object> singleTask = new HashMap<String, Object>();
        singleTask.put("id", task.getId());
        singleTask.put("name", task.getName());
        singleTask.put("pdname", processDefinition.getName());
//        singleTask.put("pdversion", processDefinition.getVersion());
//        singleTask.put("proDefId", processDefinition.getId());
        singleTask.put("pdfKey", processDefinition.getKey());
        singleTask.put("pid", task.getProcessInstanceId());
//        singleTask.put("taskDefKey", task.getTaskDefinitionKey());
        singleTask.put("applyName", getUserNameAndTimeByProcessInstanceId(task.getProcessInstanceId()).get("userName"));
        singleTask.put("createTime", sdf.format(getUserNameAndTimeByProcessInstanceId(task.getProcessInstanceId()).get("createTime")));
        singleTask.put("auditTime", sdf.format(task.getEndTime()));
        singleTask.put("endTime", getUserNameAndTimeByProcessInstanceId(task.getProcessInstanceId()).get("endTime"));

        String instanceId = task.getProcessInstanceId();
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
        singleTask.put("currActivityName", currActivityName);
        if ("oa_car_apply".equals(processDefinition.getKey())) {
            List<OaCarApply> list = oaCarApplyBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_conference_topic_apply".equals(processDefinition.getKey()) || "oa_simple_conference_apply".equals(processDefinition.getKey())) {
            List<OaMeetingTopic> list = oaMeetingTopicBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getName());
            if(list.get(0).getState()!=2){
                singleTask.put("red", true);
            }
        }else if ("oa_agenda_apply".equals(processDefinition.getKey())) {
            List<OaMeetingAgenda> list = oaMeetingAgendaBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getLocation());
        }else if ("oa_letter_apply".equals(processDefinition.getKey()) || "oa_inner_letter".equals(processDefinition.getKey())) {
            List<OaLetter> list = oaLetterBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_archive_apply".equals(processDefinition.getKey())) {
            List<OaArchiveSearch> list = oaArchiveSearchBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_leave_apply".equals(processDefinition.getKey())) {
            List<OaLeave> list = oaLeaveBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }else if ("oa_news_apply".equals(processDefinition.getKey())) {
            List<OaNews> list = oaNewsBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getTitle());
            if(list.get(0).getAllowPublic()!=null && list.get(0).getSendStatusInner()!=null
                    && list.get(0).getAllowPublic()==0 && list.get(0).getSendStatusInner()==0){
                singleTask.put("red", true);
            }
        }else if ("oa_seal_apply".equals(processDefinition.getKey())) {
            List<OaSeal> list = oaSealBiz.find(null, " processInstanceId=" + instanceId);
            singleTask.put("reason", list.size() == 0 ? "无" : list.get(0).getReason());
        }
        return singleTask;
    }

    /**
     * 根據流程實例id獲取流程發起者, 和发起时间
     * @param instanceId
     * @return
     */
    private Map<String, Object> getUserNameAndTimeByProcessInstanceId(String instanceId) {
        Map<String, Object> map = new HashMap<>();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        String userId = historicProcessInstance.getStartUserId();
        SysUser sysUser = baseHessianBiz.getSysUserById(Long.parseLong(userId));
        String userName = "";
        if (sysUser != null) {
            userName = sysUser.getUserName();
        }
        map.put("userName", userName);
        map.put("createTime", historicProcessInstance.getStartTime());
        map.put("endTime", historicProcessInstance.getEndTime());
        return map;
    }

    /**
     * @Description: 签收任务
     * @author: lzh
     * @Param: [taskId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 16:37
     */
    @RequestMapping("/task/claim")
    @ResponseBody
    public Map<String, Object> taskClaim(@RequestParam("taskId") String taskId, HttpServletRequest request,
                                         HttpServletResponse response) {
        Map<String, Object> resultMap = null;
        try {
//            签收任务
            Long userId = SysUserUtils.getLoginSysUserId(request);
            taskService.claim(taskId, userId + "");
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch(Exception e) {
            logger.error("TaskController.taskClaim");
            resultMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return resultMap;
    }

    /**
     * @Description: 跳转到任务任务列表
     * @author: lzh
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 10:00
     */
    @RequestMapping("/task/to/complete/list")
    public String taskToComplete(HttpServletRequest request) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            List<Task> toComplete = taskService.createTaskQuery().taskAssignee(String.valueOf(userId)).active().list();
            List<Map<String, Object>> taskMaps = taskConvertToTaskInfoList(toComplete);
            request.setAttribute("tasks", taskMaps);
        } catch(Exception e) {
            logger.error("TaskController.taskToComplete", e);
            return this.setErrorPath(request, e);
        }
        return taskToCompleteList;
    }

    /**
     * @Description: 将任务包装成任务详情
     * @author: lzh
     * @Param: [tasks]
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Date: 9:59
     */
    private List<Map<String, Object>> taskConvertToTaskInfoList(List<Task> tasks) {
        if (tasks == null || tasks.size() == 0) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<Map<String, Object>> taskInfos = tasks.stream().map(task -> {
        String processDefinitionId = task.getProcessDefinitionId();
        ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
        Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);

        return singleTask;
        }).collect(Collectors.toList());
        return  taskInfos;
    }

    /**
     * @Description: 获取任务表单
     * @author: lzh
     * @Param: [taskId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:34
     */
    @RequestMapping("/task/form")
    @ResponseBody
    public Map<String, Object> getTaskForm(@RequestParam("taskId") String taskId) {

        Map<String, Object> json = null;
        try {

            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            String processInstanceId = task.getProcessInstanceId();
            Map<String, Object> result = new HashMap<>();
            TaskFormDataImpl taskFormData = (TaskFormDataImpl) formService.getTaskFormData(taskId);
            taskFormData.setTask(null);
            result.put("taskFormData", taskFormData);
            //读取enum类型数据，用于下拉框
            List<FormProperty> formProperties = taskFormData.getFormProperties();
            formProperties.forEach(formProperty -> {
                //键值
                logger.debug("type = " + formProperty.getType().getName());
                String name = formProperty.getName();
                String typeName = formProperty.getType().getName();
                String key = formProperty.getId() + "_" + processInstanceId;
                //将数据保存到数据库和缓存
                workflowFormBiz.saveToDbAndRedis(processInstanceId, key, name, typeName);
                Map<String, String> values = (Map<String, String>) formProperty.getType().getInformation("values");
                if (values != null) {
                    for (Map.Entry<String, String> enumEntry : values.entrySet()) {
                        //缓存的key值
                        String enumKey = "enum_" + formProperty.getId() + "_" + enumEntry.getKey() + "_" + processInstanceId;
                        //保存数据到缓存和数据库，如果数据库已经存在，则不存数据库
                        workflowFormBiz.saveToDbAndRedis(processInstanceId, enumKey, enumEntry.getValue(), "other");
                        //打印出枚举属性的值，枚举类用于生成下拉框
                        logger.debug("enum, key: {}, value: {}", enumEntry.getKey(), enumEntry.getValue());
                    }
                    result.put(formProperty.getId(), values);
                }
            });
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, result);
        } catch(Exception e) {
            logger.error("TaskController.getTaskForm", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, e);
        }
        return json;
    }

    /**
     * @Description: 跳转到任务处理页面
     * @author: lzh
     * @Param: [taskId, request]
     * @Return: java.lang.String
     * @Date: 13:52
     */
    @RequestMapping("/task/to/complete")
    public String taskToComplete(@RequestParam("taskId") String taskId, HttpServletRequest request) {

        String view = taskToComplete;
        try {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
                String processInstanceId = task.getProcessInstanceId();
                List<HisProcessEntity> hisProcessEntities = null;
                hisProcessEntities = workflowFormBiz.packageHisProcessInfo(processInstanceId);
            request.setAttribute("hisProcessEntities", hisProcessEntities);
            request.setAttribute("applyName", hisProcessEntities.get(0).getAssignee());
            request.setAttribute("nowDate", new Date());
            request.setAttribute("processDefKey", processDefinition.getKey());

            Map<String, String> departMentMap = baseHessianBiz.queryDepartmentBySysUserId(Long.parseLong(hisProcessEntities.get(0).getApplyId()));
            if (departMentMap != null && departMentMap.size() > 0) {
                request.setAttribute("departmentName", departMentMap.get("departmentName"));
            }

            String formKey = task.getFormKey();
            if (!StringUtils.isEmpty(formKey)) {
                view = formKey;
                State state = ProcessFactory.createProcessFactory(formKey);
                state.handle(request, processInstanceId);
            }
            request.setAttribute("task", task);
        } catch(Exception e) {
            logger.error("TaskController.taskToComplete", e);
            return this.setErrorPath(request, e);
        }
        return view;
    }

//    /**
//     * @Description: 处理任务
//     * @author: lzh
//     * @Param: [taskId, processType, request]
//     * @Return: java.lang.String
//     * @Date: 15:02
//     */
//    @RequestMapping(value = "task/complete")
//    @ResponseBody
//    public Map<String, Object> completeTask(@RequestParam("taskId") String taskId,
//                                            @RequestParam(value = "processType", required = false) String processType,
//                                            HttpServletRequest request) {
//        Map<String, Object> json = null;
//        Long userId = SysUserUtils.getLoginSysUserId(request);
//        Map<String, String> formProperties = new HashMap<String, String>();
//        // 从request中读取参数然后转换
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        Set<Map.Entry<String, String[]>> entrySet = parameterMap.entrySet();
//        for (Map.Entry<String, String[]> entry : entrySet) {
//            String key = entry.getKey();
//            // fp_的意思是form paremeter
//            if (StringUtils.defaultString(key).startsWith("fp_")) {
//                formProperties.put(key.substring(3), entry.getValue()[0]);
//            }
//        }
//        logger.debug("start form parameters: {}", formProperties);
//        try {
//            identityService.setAuthenticatedUserId(String.valueOf(userId));
//            formService.submitTaskFormData(taskId, formProperties);
//            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
//        } catch(Exception e) {
//            logger.error("TaskController.completeTask", e);
//            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
//        }finally {
//            identityService.setAuthenticatedUserId(null);
//        }
//        return json;
//    }

    @RequestMapping("/task/approvalList")
    public String approvalList(HttpServletRequest request,
                               @ModelAttribute("pagination") Pagination pagination,
                               @RequestParam("taskId") String taskId) {
        try {
            boolean isSend = false;
            Map<String, String> map = SysUserUtils.getLoginSysUser(request);
            DepartMent departMent = baseHessianBiz.queryDepartemntById(Long.valueOf(map.get("departmentId")));
            if(departMent.getParentId()==0){
                isSend = true;
            }
            String sql = "1 = 1 and status = 1 and processInstanceId ="+Long.valueOf(taskId)+" ORDER BY createTime DESC";
            List<OaApproval> oaApprovalList = oaApprovalBiz.find(pagination,sql);
            request.setAttribute("oaApprovalList",oaApprovalList);
            request.setAttribute("isSend",isSend);
        } catch (Exception e) {
            logger.error("ProcessController.approvalList", e);
            return this.setErrorPath(request, e);
        }
        return "/letter/oa_approval_list";
    }

}
