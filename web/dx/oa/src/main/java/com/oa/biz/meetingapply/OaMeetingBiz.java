package com.oa.biz.meetingapply;

import com.a_268.base.core.BaseBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.dao.meetingapply.OaMeetingDao;
import com.oa.entity.meetingapply.OaMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 会议申请
 *
 * @author lzh
 * @create 2017-11-07 10:22
 */
@Service
public class OaMeetingBiz extends BaseBiz<OaMeeting, OaMeetingDao> {
    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    /**
     * 开启会议工作流
     * @param oaMeeting
     * @param processDefinitionId
     * @param userId
     * @return
     */
    public String tx_startMeetingProcess(OaMeeting oaMeeting, String processDefinitionId, Long userId) {
        oaMeeting.setApplyId(userId);
        oaMeeting.setApplyTime(new Date());
        oaMeeting.setAudit(0);
        this.save(oaMeeting);
        Long id = oaMeeting.getId();
        String businessKey = "oaMeetingBiz." + id;
        String processInstanceId = workflowFormBiz.startWorkFlowById(userId, businessKey, processDefinitionId);
        oaMeeting.setProcessInstanceId(processInstanceId);
        this.update(oaMeeting);
        return processInstanceId;
    }

    /**
     * @Description: 根据流程实例id返回值
     * @author: lzh
     * @Param: [processInstanceId]
     * @Return: com.oa.entity.workflow.OaMeeting
     * @Date: 10:54
     */
    public OaMeeting getOaMeetingByProcessInstanceId(String processInstanceId) {
        String sql = " processInstanceId = " + processInstanceId;
        List<OaMeeting> oaMeetings = this.find(null, sql);
        return oaMeetings.get(0);
    }

    /**
     * 开始审批
     * @param oaMeeting
     * @param taskId
     * @param comment
     */
    public void tx_startMeetingApplyAudit(OaMeeting oaMeeting, String taskId, String comment, Long userId) {
        String sql = " processInstanceId = " + oaMeeting.getProcessInstanceId();
        this.updateByStrWhere(oaMeeting, sql);
        workflowFormBiz.tx_startAudit(taskId, oaMeeting.getProcessInstanceId(), comment, oaMeeting.getAudit(), userId);
    }

}
