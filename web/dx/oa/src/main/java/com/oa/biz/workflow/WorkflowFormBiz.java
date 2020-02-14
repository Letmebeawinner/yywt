package com.oa.biz.workflow;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.dao.workflow.WorkflowFormDao;
import com.oa.entity.sysuser.SysUser;
import com.oa.entity.workflow.HisProcessEntity;
import com.oa.entity.workflow.WorkflowForm;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工作流表单数据缓存表
 *
 * @author lzh
 * @create 2017-03-01-15:48
 */
@Service
public class WorkflowFormBiz extends BaseBiz<WorkflowForm, WorkflowFormDao>{

    private static RedisCache redisCache  = RedisCache.getInstance();
    private static final String ENUM = "enum";

    private static final String claimTime = "签收时间";
    private static final String claimUser = "办理人";
    private static final String taskStartTime = "任务开始时间";
    private static final String taskEndTime = "任务结束时间";

    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    /**
     * @Description: 通过key值获取表单里的值，因为设置了唯一索引，所以只取一个
     * @author: lzh
     * @Param: [key]
     * @Return: java.lang.String
     * @Date: 16:35
     */
    public String getValueByKey(String key) {
        String sql = " 1 = 1 and formKey = '" + key + "'";
        List<WorkflowForm> workflowForm = this.find(null, sql);
        if (workflowForm != null && workflowForm.size() > 0) {
            return workflowForm.get(0).getFormValue();
        }
        return null;
    }

    /**
     * @Description: 根据key值查找工作表单
     * @author: lzh
     * @Param: [key]
     * @Return: java.util.List<com.oa.entity.workflow.WorkflowForm>
     * @Date: 18:22
     */
    public List<WorkflowForm> getFormByKey (String key) {
        String sql = " 1 = 1 and formKey = '" + key + "'";
        List<WorkflowForm> workflowForms = this.find(null, sql);
        return workflowForms;
    }

    /**
     * @Description: 获取流程表单中的汉语，以及人物列表里的签收人
     * @author: lzh
     * @Param: [historicTaskInstances, historicVariableInstances, processInstanceId]
     * @Return: java.util.Map<java.lang.String,java.util.List<java.util.Map<java.lang.String,java.lang.String>>>
     * @Date: 17:41
     */
    public Map<String, List<Map<String, String>>> getHisProcessInfo(List<HistoricTaskInstance> historicTaskInstances, List<HistoricVariableInstance> historicVariableInstances, String processInstanceId) {
        Map<String, List<Map<String, String>>> totalMap = new HashMap<>();
        if (historicVariableInstances != null && historicVariableInstances.size() > 0) {
            //读取流程定义时表单的汉语名称
            List<Map<String, String>> variableMaps = getHisVariables(historicVariableInstances, processInstanceId);
            totalMap.put("variableMaps", variableMaps);
        }
        if (historicTaskInstances != null && historicTaskInstances.size() > 0) {
            //获取任务签收时间，任务开始时间，任务结束时间，任务签收人,封装到map
           List<Map<String, String>> taskMaps = getHistoryTask(historicTaskInstances);
           totalMap.put("taskMaps", taskMaps);
        }
        return totalMap;
     }


    public List<Map<String, String>> getHisVariables(List<HistoricVariableInstance> historicVariableInstances, String processInstanceId) {
        List<Map<String, String>> variableMaps = null;
        if (historicVariableInstances != null && historicVariableInstances.size() > 0) {
            //读取流程定义时表单的汉语名称
            variableMaps = historicVariableInstances.stream()
                    .map(historicVariableInstance -> {
                        String variableName = historicVariableInstance.getVariableName();
                        String type = historicVariableInstance.getVariableTypeName();
                        String value = "";
                        if ("date".equals(type) && historicVariableInstance.getValue() != null) {
                            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                value = smf.format(historicVariableInstance.getValue());
                        } else if (historicVariableInstance.getValue() != null){
                            value = historicVariableInstance.getValue().toString();
                        }
                        Map<String, String> map = getNameByVariName(variableName, type, processInstanceId, value);
                        return map;
                    })
                    .collect(Collectors.toList());
        }
        return variableMaps;
    }

    /**
     * @Description: 历史任务
     * @author: lzh
     * @Param: [historicTaskInstances]
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @Date: 14:45
     */
    public List<Map<String, String>> getHistoryTask(List<HistoricTaskInstance> historicTaskInstances) {
        List<Map<String, String>> taskMaps = null;
        if (historicTaskInstances != null && historicTaskInstances.size() > 0) {
            //获取任务签收时间，任务开始时间，任务结束时间，任务签收人,封装到map
            taskMaps = historicTaskInstances.stream()
                    .map(historicTaskInstance -> {
                        String taskName = historicTaskInstance.getName();
                        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(taskName + "->" + taskStartTime, smf.format(historicTaskInstance.getStartTime()));
                        //有可能是直接交给办理人，没有候选人的，所以得进行一次判空操作
                        if (historicTaskInstance.getClaimTime() != null) {
                            map.put(taskName + "->" + claimTime, smf.format(historicTaskInstance.getClaimTime()));
                        }
                        Long userId = Long.valueOf(historicTaskInstance.getAssignee());
                        map.put(taskName + "->" + claimUser, baseHessianBiz.getSysUserById(userId).getUserName());
                        map.put(taskName + "->" + taskEndTime, smf.format(historicTaskInstance.getEndTime()));
                        return map;
                    })
                    .collect(Collectors.toList());
        }
        return taskMaps;
    }

    /**
     * @Description: 根据key值取出value，如果缓存不在就取数据库的
     * @author: lzh
     * @Param: [varivableName, type, processInstanceId]
     * @Return: java.lang.String
     * @Date: 16:50
     */
    private Map<String, String> getNameByVariName(String variableName, String type, String processInstanceId, String v) {
        Map<String, String> map = new HashMap<>();
        String key = variableName + "_" + processInstanceId;
        String enumKey = "enum_" + variableName + "_" + v  + "_" + processInstanceId;
        String keyName = null;
        String keyValue = null;
        Map<String, String> resultMap = (Map<String, String>)redisCache.regiontGet(key);
        //如果缓存里没有那么从数据库获取
        if (resultMap == null) {
            keyName = this.getValueByKey(key);
            //如果是枚举，那么重新取出枚举的值
            if (ENUM.equals(this.getFormByKey(key).get(0).getFormPropertyType())) {
                keyValue = this.getValueByKey(enumKey);
            } else {
                //如果不是枚举类型
                keyValue = v;
            }
            map.put(keyName, keyValue);
        }
        //如果缓存里有值，从缓存里取出名字
        if (resultMap != null && ENUM.equals(resultMap.get("type"))) {
            keyName = resultMap.get("name");
            Map<String, String> enumResultMap = (Map<String, String>)redisCache.regiontGet(enumKey);
            if (enumResultMap != null) {
                keyValue = enumResultMap.get("name");
            } else {
                keyValue = this.getValueByKey(enumKey);
            }
            map.put(keyName, keyValue);
        }
        //如果不是枚举类型那么直接取值即可
        if (resultMap != null && !ENUM.equals(resultMap.get("type"))) {
            keyName = resultMap.get("name");
            keyValue = v;
            map.put(keyName, keyValue);
        }
        return map;
    }

     /**
     * @Description: 将数据保存到缓存和数据库
     * @author: lzh
     * @Param: [processInstanceId, key, value, typeName]
     * @Return: void
     * @Date: 15:35
     */
    public void saveToDbAndRedis(String processInstanceId, String key, Object value, String typeName) {
        Map<String, Object> map = new HashMap<>();
        List<WorkflowForm> workflowForms = this.getFormByKey(key);
        if (workflowForms == null || workflowForms.size() == 0) {
            map.put("name", value);
            map.put("type", typeName);

            WorkflowForm workflowForm = new WorkflowForm();
            workflowForm.setProcessInstanceId(processInstanceId);
            workflowForm.setFormKey(key);
            workflowForm.setFormValue((String)value);
            workflowForm.setFormPropertyType(typeName);
            this.save(workflowForm);
            redisCache.regionSet(key, map);
        }
    }


    /**
     * 通过id、启动工作流
     * @param userId
     * @param businessKey
     * @param processDefinitionId
     * @return
     */
    public String startWorkFlowById(Long userId, String businessKey, String processDefinitionId) {
        return startWorkFlow(userId, businessKey, processDefinitionId, null);
    }

    /**
     * 通过id、启动工作流 (重载上面的方法)
     * @param userId
     * @param businessKey
     * @param processDefinitionId
     * @return
     */
    public String startWorkFlowById(Long userId, String businessKey, String processDefinitionId, Map<String, Object> variableMap) {
        return startWorkFlow(userId, businessKey, processDefinitionId, variableMap);
    }


    /**
     * 开启流程 ,可以自定义流程变量
     * @return
     */
    private String startWorkFlow(Long userId, String businessKey, String processDefinitionId, Map<String, Object> variableMap) {
        identityService.setAuthenticatedUserId(userId + "");
        List<Long> userLeaderIds = baseHessianBiz.queryUserLeadersByUserId(userId);
        String candidateGroups = "";
        if (userLeaderIds != null && userLeaderIds.size() > 0) {
            candidateGroups = userLeaderIds.stream()
                    .map(userLeaderId -> {return userLeaderId + "" ;})
                    .collect(Collectors.joining(",", "", ""));
        }
        Map<String, Object> variables = new HashMap<>();
        String officeCar = baseHessianBiz.queryUserIdsByRoleName("办公室(车辆)");
        String officeMeeting = baseHessianBiz.queryUserIdsByRoleName("办公室(会议)");
        String officeLetter = baseHessianBiz.queryUserIdsByRoleName("办公室(公文)");
        String officeArchive = baseHessianBiz.queryUserIdsByRoleName("办公室(档案)");
        String officeMessage = baseHessianBiz.queryUserIdsByRoleName("办公室(信息管理员)");
        String cancelLeave = baseHessianBiz.queryUserIdsByRoleName("组织人事处(销假)");

        String officeDirector =baseHessianBiz.queryUserIdsByRoleName("办公室主任");
        String officeClerk =baseHessianBiz.queryUserIdsByRoleName("办公室文员");
        String officeRelease =baseHessianBiz.queryUserIdsByRoleName("文件发布员");

        String officeMeetingLeader =baseHessianBiz.queryUserIdsByRoleName("办公室领导(会议)");
        String officeMessageLeader =baseHessianBiz.queryUserIdsByRoleName("办公室领导(信息发布)");

        String school = baseHessianBiz.queryUserIdsByRoleName("分管校领导");
        // String schoolLeader = baseHessianBiz.queryUserIdsByRoleName("常务副校长");
        String schoolLeader = baseHessianBiz.queryUserIdsByRoleName("常务副校长/副校长（主持工作）");

        // 机关党委办人员：朱倩楠、伍诚惠、田丰 特殊处理
        /*if(userId.longValue()==2422L || userId.longValue()==2423L || userId.longValue()==2417L){
            candidateGroups="2433";//部门领导张颖
            school="2408";//校领导曹毅
        }*/

        variables.put("userId", userId.toString()); //申请人
        variables.put("cancelLeave", cancelLeave);
        variables.put("officeDirector", officeDirector);
        variables.put("officeClerk", officeClerk);
        variables.put("officeRelease", officeRelease);
        variables.put("candidateGroups", candidateGroups); //部门领导
        variables.put("officeCar", officeCar);
        variables.put("officeMeeting", officeMeeting);
        variables.put("officeLetter", officeLetter);
        variables.put("officeArchive", officeArchive);
        variables.put("officeMessage", officeMessage);
        variables.put("officeMeetingLeader", officeMeetingLeader);
        variables.put("officeMessageLeader", officeMessageLeader);
        variables.put("school", school);
        variables.put("schoolLeader", schoolLeader);

        variables.put("applyId", userId);


        if (variableMap != null) {
           variables.putAll(variableMap);
        }
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables);
        return processInstance.getId();
    }

    /**
     * 开始审批，增加审批意见
     * @param taskId
     * @param processInstanceId
     * @param comment
     * @param flag
     */
    public void tx_startAudit(String taskId, String processInstanceId, String comment, int flag, Long userId) {
        Map<String, Object> variables = new HashMap<>();
        boolean pass = false;
        String option = "[驳回]";
        if (flag == 0 || flag == 1) {
            pass = true;
            option = "[同意]";
        }
        variables.put("pass", pass);
        if (StringUtils.isEmpty(comment)) {
            comment = "";
        }
        taskService.setAssignee(taskId, userId + "");
        taskService.addComment(taskId, processInstanceId, option + " " + comment);
        taskService.complete(taskId, variables);
    }



    /**
     * 开始审批，增加审批意见 (动态指定多人)
     * @param taskId
     * @param processInstanceId
     * @param comment
     * @param flag
     */
    public void tx_startAudit(String taskId, String processInstanceId, String comment, int flag, Long userId, String userIds) {
        Map<String, Object> variables = new HashMap<>();
        boolean pass = false;
        boolean ok = false;
        String option = "[驳回]";
        if (flag == 0 || flag == 1) {
            pass = true;
            option = "[同意]";
        }
        if (!StringUtils.isTrimEmpty(userIds)) {
            ok = true;
        }
        variables.put("pass", pass);
        variables.put("ok", ok);
        variables.put("audit", userIds);
        if (StringUtils.isEmpty(comment)) {
            comment = "";
        }
        taskService.setAssignee(taskId, userId + "");
        taskService.addComment(taskId, processInstanceId, option + " " + comment);
        taskService.complete(taskId, variables);
    }


    /**
     * 开始审批，增加审批意见 (动态指定单人)
     * @param taskId
     * @param processInstanceId
     * @param comment
     * @param flag
     */
    public void tx_startAudit(String taskId, String processInstanceId, String comment, int flag, Long userId, Long approvalId) {
        Map<String, Object> variables = new HashMap<>();
        boolean pass = false;
//        boolean ok = false;
        String option = "[驳回]";
        if (flag == 0 || flag == 1) {
            pass = true;
            option = "[同意]";
        }
//        if(ObjectUtils.isNotNull(approvalId)){
//            List<Map<String, String>> userMaps = baseHessianBiz.queryUserRoleInfoByUserId(approvalId);
//            if (userMaps != null && userMaps.size() > 0) {
//                for (Map<String, String> map : userMaps) {
//                    String name = map.get("roleName");
//                    if (name!=null && name.indexOf("常务副校长")>-1) {
//                        ok = true;
//                        break;
//                    }
//                }
//            }
//            if(ok){
//                variables.put("schoolLeader", approvalId+"");
//            }else {
//                variables.put("school", approvalId+"");
//            }
//            variables.put("ok", ok);
//        }
        variables.put("audit", approvalId+"");

        variables.put("pass", pass);
        if (StringUtils.isEmpty(comment)) {
            comment = "";
        }
        taskService.setAssignee(taskId, userId + "");
        taskService.addComment(taskId, processInstanceId, option + " " + comment);
        taskService.complete(taskId, variables);
    }


    /**
     * @Description: 将历史流转信息打包封装
     * @author: lzh
     * @Param: [processInstanceId]
     * @Return: java.util.List<com.oa.entity.workflow.HisProcessEntity>
     * @Date: 16:29
     */
    public List<HisProcessEntity> packageHisProcessInfo (String processInstanceId) {
        List<HisProcessEntity> hisProcessEntities = new ArrayList<>();
        //查询所有的活动列表
        List<HistoricActivityInstance> historicActivitiInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        //将活动列表封装到对应的实体
        if (historicActivitiInstances.size() == 0) {
            return null;
        }
        hisProcessEntities = historicActivitiInstances.stream()
                .filter(historicActivityInstance -> {
                    return !StringUtils.isEmpty(historicActivityInstance.getAssignee())
                            || historicActivityInstance.getActivityType().equals("startEvent")
                            || historicActivityInstance.getActivityType().equals("endEvent");
                })
                .map( historicActivityInstance -> {
                    HisProcessEntity hisProcessEntity = new HisProcessEntity();
                    String activitiType = historicActivityInstance.getActivityType();
                    hisProcessEntity.setAcitivitiStartTime(historicActivityInstance.getStartTime());
                    hisProcessEntity.setAcitivitiEndTime(historicActivityInstance.getEndTime());
                    hisProcessEntity.setActivitiName(historicActivityInstance.getActivityName());
                    //因为开始节点是没有处理人的，得单独处理
                    if ("startEvent".equals(activitiType)) {
                        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).orderByProcessInstanceStartTime().asc().list();
                        if (historicProcessInstances != null && historicProcessInstances.size() > 0) {
                            String userId = historicProcessInstances.get(0).getStartUserId();
                            SysUser sysUser = baseHessianBiz.getSysUserById(Long.parseLong(userId));
                            hisProcessEntity.setAssignee(sysUser.getUserName());
                            hisProcessEntity.setApplyId(userId);
                        }
                    } else {
                        String userId = historicActivityInstance.getAssignee();
                        //结束任务是没有任务的，得单独处理
                        if (!StringUtils.isEmpty(userId)) {
                            SysUser sysUser = baseHessianBiz.getSysUserById(Long.parseLong(userId));
                            String userName = Optional.ofNullable(sysUser)
                                    .map(SysUser::getUserName)
                                    .orElse("");
                            hisProcessEntity.setAssignee(userName);
                        }
                    }
                    String taskId = historicActivityInstance.getTaskId();
                    List<Comment> comment = taskService.getTaskComments(taskId);
                    if (comment.size() > 0) {
                        hisProcessEntity.setOption(comment.get(0).getFullMessage());
                    }
                    return hisProcessEntity;
                })
                .collect(Collectors.toList());
        return hisProcessEntities;
    }

}
