package com.oa.biz.conference;

import com.a_268.base.core.BaseBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.dao.conference.OaMeetingTopicDao;
import com.oa.entity.conference.OaMeetingTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 议题
 *
 * @author YaoZhen
 * @create 11-24, 11:16, 2017.
 */
@Service
public class OaMeetingTopicBiz extends BaseBiz<OaMeetingTopic, OaMeetingTopicDao> {

    @Autowired
    private WorkflowFormBiz workflowFormBiz;

    /**
     * 开启
     *
     * @param topic  议题
     * @param processDefinitionId
     * @param userId 用户id
     * @return 流程Id
     */
    public String tx_startTopicProcess(OaMeetingTopic topic, String processDefinitionId, Long userId, String userIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("audit", userIds);
        topic.setApplyId(userId);
        topic.setApplyTime(new Date());
        topic.setAudit(0);
        this.save(topic);
        Long id = topic.getId();
        String businessKey = "oaMeetingTopicBiz." + id;
        String processInstanceId = workflowFormBiz.startWorkFlowById(userId, businessKey, processDefinitionId, map);
        topic.setProcessInstanceId(processInstanceId);
        this.update(topic);
        return processInstanceId;
    }

    /**
     * 开始审批
     *
     * @param topic
     * @param taskId
     * @param comment
     */
    public void tx_startTopicApplyAudit(OaMeetingTopic topic, String taskId, String comment, Long userId) {
        String sql = " processInstanceId = " + topic.getProcessInstanceId();
        this.updateByStrWhere(topic, sql);
        workflowFormBiz.tx_startAudit(taskId, topic.getProcessInstanceId(), comment, topic.getAudit(), userId);
    }

    /**
     * @Description: 根据流程实例id返回值
     * @author: lzh
     * @Param: [processInstanceId]
     * @Return: com.oa.entity.workflow.OaMeeting
     * @Date: 10:54
     */
    public OaMeetingTopic getOaMeetingByProcessInstanceId(String processInstanceId) {
        String sql = " processInstanceId = " + processInstanceId;
        List<OaMeetingTopic> oaMeetingTopics = this.find(null, sql);
        return oaMeetingTopics.get(0);
    }

}
