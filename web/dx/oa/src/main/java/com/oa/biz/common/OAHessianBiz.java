package com.oa.biz.common;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oa.biz.archivetype.ArchiveBiz;
import com.oa.biz.archivetype.ArchiveTypeBiz;
import com.oa.biz.archivetype.OaArchiveSearchBiz;
import com.oa.biz.car.OaCarApplyBiz;
import com.oa.biz.conference.OaMeetingAgendaBiz;
import com.oa.biz.conference.OaMeetingTopicBiz;
import com.oa.biz.leave.OaLeaveBiz;
import com.oa.biz.letter.LetterBiz;
import com.oa.biz.letter.OaApprovalBiz;
import com.oa.biz.meetingapply.OaMeetingBiz;
import com.oa.biz.news.OaNewsBiz;
import com.oa.biz.notice.NoticeBiz;
import com.oa.biz.rule.RuleBiz;
import com.oa.biz.seal.OaSealBiz;
import com.oa.biz.word.TaoHongBiz;
import com.oa.biz.workflow.OaLetterBiz;
import com.oa.entity.archivetype.Archive;
import com.oa.entity.archivetype.ArchiveType;
import com.oa.entity.archivetype.OaArchiveSearch;
import com.oa.entity.car.OaCarApply;
import com.oa.entity.conference.OaMeetingAgenda;
import com.oa.entity.conference.OaMeetingTopic;
import com.oa.entity.leave.OaLeave;
import com.oa.entity.letter.Letter;
import com.oa.entity.letter.OaApproval;
import com.oa.entity.news.OaNews;
import com.oa.entity.notice.Notice;
import com.oa.entity.rule.Rule;
import com.oa.entity.seal.OaSeal;
import com.oa.entity.sysuser.SysUser;
import com.oa.entity.word.TaoHong;
import com.oa.entity.workflow.OaLetter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OA hession接口
 *
 * @author ccl
 * @create 2017-01-11-16:20
 */
@Service
public class OAHessianBiz implements OAHessianService {


    protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();
    @Autowired
    private RuleBiz ruleBiz;

    @Autowired
    private NoticeBiz noticeBiz;

    @Autowired
    private LetterBiz letterBiz;

    @Autowired
    private ArchiveTypeBiz archiveTypeBiz;

    @Autowired
    private ArchiveBiz archiveBiz;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
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
    private TaoHongBiz taoHongBiz;

    /**
     * @Description:获取全部规章制度
     * @author: ccl
     * @Param: [pagination, whereSql]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-11
     */
    public Map<String, Object> getRuleList(Pagination pagination, String whereSql) {
        List<Rule> ruleList = ruleBiz.find(pagination, whereSql);
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(ruleList);
        Map<String, String> objMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<>();
        map.put("ruleList", listMap);
        map.put("pagination", objMap);
        return map;
    }

    /**
     * 获取规章详细信息
     * @param id
     * @return
     */
    public Map<String,String> getRuleById(Long id){
        Rule rule=ruleBiz.findById(id);
        Map<String,String> map=ObjectUtils.objToMap(rule);
        return  map;
    }

    /**
     * @Description:获取全部通知公告
     * @author: ccl
     * @Param: [pagination, whereSql]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-11
     */
    @Override
    public Map<String, Object> getNoticeList(Pagination pagination, String whereSql) {
        List<Notice> noticeList = noticeBiz.find(pagination, whereSql);
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(noticeList);
        Map<String, String> objMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<>();
        map.put("ruleList", listMap);
        map.put("pagination", objMap);
        return map;
    }

    /**
     * 获取通知公告详细信息
     * @param id
     * @return
     */
    @Override
    public Map<String,String> getNoticeById(Long id){
        Notice notice=noticeBiz.findById(id);
        Map<String,String> map=ObjectUtils.objToMap(notice);
        return  map;
    }

    @Override
    public Map<String, Object> getLetterList(Pagination pagination, String whereSql) {
        List<Letter> letters = letterBiz.find(pagination, whereSql);
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(letters);
        Map<String, String> objMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<>();
        map.put("letters", listMap);
        map.put("pagination", objMap);
        return map;
    }

    @Override
    public Map<String, String> getLetterById(Long id) {
        Letter letter = letterBiz.findById(id);
        Map<String, String> map = ObjectUtils.objToMap(letter);
        return map;
    }

    @Override
    public List<Map<String, String>> listArchiveType() {
        List<ArchiveType> archiveTypeList = archiveTypeBiz.findAll();
        return ObjectUtils.listObjToListMap(archiveTypeList);
    }

    @Override
    public Long saveArchive(String json) {
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Archive archive = gson.fromJson(json, Archive.class);
        archiveBiz.save(archive);
        return archive.getId();
    }

    @Override
    public Map<String, String> findArchiveById(Long oaArchiveId) {
        Archive archive = archiveBiz.findById(oaArchiveId);
        return ObjectUtils.objToMap(archive);
    }
    @Override
    public Integer approvalNum (Long userId){
        List<Task> toDoTasks1 = taskService.createTaskQuery()
                .active()
                .taskAssignee(String.valueOf(userId))
                .orderByTaskCreateTime().asc()
                .list();
        List<Task> toDoTasks2 = taskService.createTaskQuery()
                .taskCandidateGroup(String.valueOf(userId))
                .active()
                .orderByTaskCreateTime().asc()
                .list();
        toDoTasks1.addAll(toDoTasks2);
        return toDoTasks1==null?0:toDoTasks1.size();
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


    /**
     * @Description:获取待审批的公文列表
     * @author: js
     * @Param: [userId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-11
     */
    public List<Map<String, Object>> getOaApprovalList(Long userId) {
        TaskQuery taskQuery=taskService.createTaskQuery()
                .active()
                .orderByTaskCreateTime().desc();
        taskQuery.or().taskAssignee(String.valueOf(userId)).taskCandidateGroup(String.valueOf(userId)).endOr();
        List<Task> toDoTasks=taskQuery.list();
        List<Map<String, Object>> taskMaps = taskConvertToTaskInfoList(toDoTasks);
        return taskMaps;
    }

    /**
     * 签收
     * @param userId
     * @return
     */
    public void taskClaim(String taskId,Long userId) {
        taskService.claim(taskId, userId + "");
    }

    /**
     * 获取未读公文列表
     * @param userId
     * @return
     */
    public List<Map<String, String>> getUnreadOaApprovalList(Long userId){
        String sql = "status = 1 and sysUserId =" + userId;
        sql+=" and approvalStatus=0";
        sql+=" ORDER BY updateTime DESC";
        List<OaApproval> oaApprovalList = oaApprovalBiz.find(null, sql);
        return ObjectUtils.listObjToListMap(oaApprovalList);
    }

    @Override
    public Map<String, String> getOaTaoHong(String processDefinitionId) {
        List<TaoHong> taoHongs = taoHongBiz.find(null, " processDefinitionId=" + processDefinitionId);
        if(ObjectUtils.isNotNull(taoHongs)){
            TaoHong taoHong = taoHongs.get(0);
            return ObjectUtils.objToMap(taoHong);
        }
        return null;
    }
}
