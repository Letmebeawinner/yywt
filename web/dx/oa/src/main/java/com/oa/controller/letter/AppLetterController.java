package com.oa.controller.letter;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.workflow.OaLetterBiz;
import com.oa.entity.sysuser.SysUser;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * app接口
 *
 * @author lzh
 * @create 2017-03-15-9:32
 */
@Controller
@RequestMapping("/app/oa/letter")
public class AppLetterController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(AppLetterController.class);
    protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();
    @Autowired
    private OaLetterBiz oaLetterBiz;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private HistoryService historyService;

    /**
     * @Description: 所有的公文列表
     * @author: lzh
     * @Param: [] userId 如果有传userId那么只查这个人的
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 9:53
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> getLetterList(@RequestParam(value = "id", required = false) Long id,
                                             @ModelAttribute("pagination")Pagination pagination) {
        Map<String, Object> json = null;
        try {
            List<Map<String,Object>> letters=oaLetterBiz.getOaLetterListByUserId(pagination,id);
            Map<String, Object> map = new HashMap<>();
            map.put("pagination", pagination);
            map.put("letters", letters);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, map);
        } catch(Exception e) {
            logger.error("AppLetterController.getLetterList", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 所有的待处理
     * @author: lzh
     * @Param: 只查这个人的
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 9:53
     */
    @RequestMapping("/task/list")
    @ResponseBody
    public Map<String, Object> getTaskList(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> json = null;
        try {
            //代办任务
            TaskQuery taskQuery=taskService.createTaskQuery()
                    .active()
                    .orderByTaskCreateTime().desc();
            taskQuery.or().taskAssignee(String.valueOf(id)).taskCandidateGroup(String.valueOf(id)).endOr();
            List<Task> toDoTasks=taskQuery.list();
            List<Map<String, Object>> taskMaps = taskConvertToTaskInfoList(toDoTasks);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, taskMaps);
        } catch(Exception e) {
            logger.error("AppLetterController.getLetterList", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 所有的待处理的任务数量
     * @author: lzh
     * @Param: 只查这个人的
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 9:53
     */
    @RequestMapping("/task/count")
    @ResponseBody
    public Map<String, Object> getTaskCount(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> json = null;
        try {
            //代办任务
            TaskQuery taskQuery=taskService.createTaskQuery()
                    .active()
                    .orderByTaskCreateTime().desc();
            taskQuery.or().taskAssignee(String.valueOf(id)).taskCandidateGroup(String.valueOf(id)).endOr();
            List<Task> toDoTasks=taskQuery.list();
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,toDoTasks==null?0:toDoTasks.size());
        } catch(Exception e) {
            logger.error("AppLetterController.getLetterList", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
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
        singleTask.put("createTime", sdf.format(getUserNameAndTimeByProcessInstanceId(task.getProcessInstanceId()).get("createTime")));
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
        return map;
    }


}
