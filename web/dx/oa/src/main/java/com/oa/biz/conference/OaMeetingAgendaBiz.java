package com.oa.biz.conference;

import com.a_268.base.core.BaseBiz;
import com.oa.biz.asyncTask.SendMessageAsyncTask;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.common.SmsHessianService;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.dao.conference.OaMeetingAgendaDao;
import com.oa.entity.conference.OaMeetingAgenda;
import com.oa.entity.conference.OaMeetingTopic;
import com.oa.entity.message.InfoRecord;
import com.oa.entity.sysuser.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 议程
 *
 * @author YaoZhen
 * @create 11-24, 11:17, 2017.
 */
@Service
public class OaMeetingAgendaBiz extends BaseBiz<OaMeetingAgenda, OaMeetingAgendaDao> {

    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    @Autowired
    private OaMeetingTopicBiz oaMeetingTopicBiz;
    @Autowired
    private SmsHessianService smsHessianService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private SendMessageAsyncTask sendMessageAsyncTask;

    private static Logger logger = LoggerFactory.getLogger(OaMeetingAgenda.class);

    /**
     * 开启
     *
     * @param agenda              议程
     * @param processDefinitionId
     * @param userId              登陆账号id
     * @return 流程Id
     */
    public String tx_startAgendaProcess(OaMeetingAgenda agenda, String processDefinitionId, Long userId) {
        agenda.setApplyId(userId);
        agenda.setApplyTime(new Date());
        agenda.setAudit(0);
        this.save(agenda);
        Long id = agenda.getId();
        String businessKey = "oaMeetingAgendaBiz." + id;
        String processInstanceId = workflowFormBiz.startWorkFlowById(userId, businessKey, processDefinitionId);
        agenda.setProcessInstanceId(processInstanceId);
        this.update(agenda);
        OaMeetingTopic oaMeetingTopic = new OaMeetingTopic();
        oaMeetingTopic.setState(2);
        oaMeetingTopicBiz.updateByStrWhere(oaMeetingTopic, "id in (" + agenda.getTopicIds().substring(1, agenda.getTopicIds().length() - 1) + ")");
        return processInstanceId;
    }

    /**
     * 在草稿箱列表开启
     *
     * @param agenda              议程
     * @param processDefinitionId
     * @param userId              登陆账号id
     * @return 流程Id
     */
    public String draft_startAgendaProcess(OaMeetingAgenda agenda, String processDefinitionId, Long userId) {
        Long id = agenda.getId();
        String businessKey = "oaMeetingAgendaBiz." + id;
        String processInstanceId = workflowFormBiz.startWorkFlowById(userId, businessKey, processDefinitionId);
        agenda.setProcessInstanceId(processInstanceId);
        // 发布成功后，取消草稿状态
        agenda.setStatus(0);
        this.update(agenda);
        OaMeetingTopic oaMeetingTopic = new OaMeetingTopic();
        oaMeetingTopic.setState(2);
        oaMeetingTopicBiz.updateByStrWhere(oaMeetingTopic, "id in (" + agenda.getTopicIds().substring(1, agenda.getTopicIds().length() - 1) + ")");
        return processInstanceId;
    }

    /**
     * 开始审批
     *
     * @param agenda
     * @param taskId
     * @param comment
     */
    public void tx_startAgendaAudit(OaMeetingAgenda agenda, String taskId, String comment, Long userId, String userIds) throws Exception {
        String topicIds = agenda.getTopicIds().substring(1, agenda.getTopicIds().length() - 1);
        if (agenda.getAudit() == 1) {
            //设置已使用状态
//            OaMeetingTopic oaMeetingTopic=new OaMeetingTopic();
//            oaMeetingTopic.setState(2);
//            oaMeetingTopicBiz.updateByStrWhere(oaMeetingTopic,"id in ("+topicIds+")");
            //发送短信

            List<String> userIdList = new ArrayList<>();
            String[] bePresentArr = agenda.getBePresent().substring(1, agenda.getBePresent().length() - 1).split(",");//议程出席人
            String[] attendArr = agenda.getAttend().substring(1, agenda.getAttend().length() - 1).split(",");//议程列席人
            String[] compereArr = agenda.getCompere().substring(1, agenda.getCompere().length() - 1).split(",");//议程主持人
            String[] recordArr = agenda.getRecord().substring(1, agenda.getRecord().length() - 1).split(",");//议程记录人
            userIdList.addAll(Arrays.asList(bePresentArr));//添加议程出席人
            userIdList.removeAll(Arrays.asList(attendArr));//除去重复的
            userIdList.addAll(Arrays.asList(attendArr));//添加议程列席人
            userIdList.removeAll(Arrays.asList(compereArr));//除去重复的
            userIdList.addAll(Arrays.asList(compereArr));//添加议程主持人
            userIdList.removeAll(Arrays.asList(recordArr));//除去重复的
            userIdList.addAll(Arrays.asList(recordArr));//添加议程记录人
            String[] topicArr = topicIds.split(",");
            for (String topicId : topicArr) {
                OaMeetingTopic topic = oaMeetingTopicBiz.findById(Long.valueOf(topicId));
                if(topic.getAttendPeople()!=null && topic.getAttendPeople().length()>1){
                    String[] attendPeopleArr = topic.getAttendPeople().substring(1, topic.getAttendPeople().length() - 1).split(",");//议题列席人
                    userIdList.removeAll(Arrays.asList(attendPeopleArr));//除去重复的
                    userIdList.addAll(Arrays.asList(attendPeopleArr));//添加议程列席人
                }
                if (topic.getReporter() != null && topic.getReporter().length()>1) {//汇报人可能为空
                    String[] reporterArr = topic.getReporter().substring(1, topic.getReporter().length() - 1).split(",");//议题汇报人
                    userIdList.removeAll(Arrays.asList(reporterArr));//除去重复的
                    userIdList.addAll(Arrays.asList(reporterArr));//添加议程汇报人
                }
            }
            smsHessianService.batchSendAgendaMessage(userIdList,agenda.getId(),userId);
        } else if (agenda.getAudit() == 2) {
            OaMeetingTopic oaMeetingTopic = new OaMeetingTopic();
            oaMeetingTopic.setState(1);
            oaMeetingTopicBiz.updateByStrWhere(oaMeetingTopic, "id in (" + topicIds + ")");
        }
        String sql = " processInstanceId = " + agenda.getProcessInstanceId();
        this.updateByStrWhere(agenda, sql);
        workflowFormBiz.tx_startAudit(taskId, agenda.getProcessInstanceId(), comment, agenda.getAudit(), userId, userIds);
    }

    /**
     * @Description: 根据流程实例id返回值
     * @author: lzh
     * @Param: [processInstanceId]
     * @Return: com.oa.entity.workflow.OaLetter
     * @Date: 10:54
     */
    public OaMeetingAgenda getOaMeetingAgendaByProcessInstanceId(String processInstanceId) {
        String sql = " processInstanceId = " + processInstanceId;
        List<OaMeetingAgenda> oaMeetingAgendas = this.find(null, sql);
        return oaMeetingAgendas.get(0);
    }


}
