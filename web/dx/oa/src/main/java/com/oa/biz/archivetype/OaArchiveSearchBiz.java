package com.oa.biz.archivetype;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.dao.archivetype.OaArchiveSearchDao;
import com.oa.entity.archivetype.OaArchiveSearch;
import com.oa.entity.archivetype.UserArchive;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OaArchiveSearchBiz extends BaseBiz<OaArchiveSearch, OaArchiveSearchDao> {
    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    @Autowired
    private UserArchiveBiz userArchiveBiz;
    @Autowired
    private TaskService taskService;
    /**
     * 开启档案工作流
     * @param oaArchive
     * @param processDefinitionId
     * @param userId
     * @return
     */
    public String tx_startArchiveProcess(OaArchiveSearch oaArchive, String processDefinitionId, Long userId) {
        oaArchive.setApplyId(userId);
        oaArchive.setApplyTime(new Date());
        oaArchive.setAudit(0);
        this.save(oaArchive);
        Long id = oaArchive.getId();
        String businessKey = "oaArchiveBiz." + id;
        String processInstanceId = workflowFormBiz.startWorkFlowById(userId, businessKey, processDefinitionId);
        oaArchive.setProcessInstanceId(processInstanceId);
        this.update(oaArchive);
        return processInstanceId;
    }

    /**
     * @Description: 根据流程实例id返回值
     * @author: lzh
     * @Param: [processInstanceId]
     * @Return: com.oa.entity.workflow.oaArchive
     * @Date: 10:54
     */
    public OaArchiveSearch getOaArchiveByProcessInstanceId(String processInstanceId) {
        String sql = " processInstanceId = " + processInstanceId;
        List<OaArchiveSearch> oaArchives = this.find(null, sql);
        return oaArchives.get(0);
    }

    /**
     * 开始审批
     * @param oaArchive
     * @param taskId
     * @param comment
     */
    public void tx_startArchiveApplyAudit(OaArchiveSearch oaArchive, String taskId, String comment, Long userId) {
        String sql = " processInstanceId = " + oaArchive.getProcessInstanceId();
        this.updateByStrWhere(oaArchive, sql);
        workflowFormBiz.tx_startAudit(taskId, oaArchive.getProcessInstanceId(), comment, oaArchive.getAudit(), userId);
    }

    /**
     * 发送档案
     * @param userId 申请人id
     * @param taskId
     * @param currUserId 当前登录用户id
     */
    public void tx_sendArchive(String archiveIds, String taskId, Long currUserId, Long userId) {
        //如果档案id为空，或者申请人为空的话
        if (StringUtils.isEmpty(archiveIds) || StringUtils.isEmpty(archiveIds)) {
            return;
        }
        String[] strIds = archiveIds.split(",");
        for (String id : strIds) {
            UserArchive userArchive = new UserArchive();
            userArchive.setArchiveId(Long.parseLong(id));
            userArchive.setUserId(userId);
            userArchive.setCreateId(currUserId);
            userArchiveBiz.save(userArchive);
        }
        taskService.complete(taskId);
    }
}