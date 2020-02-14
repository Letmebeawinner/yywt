package com.oa.biz.seal;

import com.a_268.base.core.BaseBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.dao.seal.OaSealDao;
import com.oa.entity.seal.OaSeal;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * oa印章业务
 *
 * @author lzh
 * @create 2017-10-27 10:55
 */
@Service
public class OaSealBiz extends BaseBiz<OaSeal, OaSealDao>{

    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    @Autowired
    private RuntimeService runtimeService;

    /**
     * 启动流程
     */
    public String tx_startSealApplyProcess(OaSeal oaSeal, String processDefinitionId, Long userId) {
        Map<String, Object> oaSealMap = new HashMap<>();
        if(oaSeal.getSealType().indexOf("2")>-1){
            oaSealMap.put("needSchoolLeader", true);
        }else {
            oaSealMap.put("needSchoolLeader", false);
        }
        oaSeal.setApplyId(userId);
        oaSeal.setAudit(0);
        oaSeal.setApplyTime(new Date());
        this.save(oaSeal);
        Long id = oaSeal.getId();
        String businessKey = "oaSealBiz." + id;
        //设置用户
        String processInstanceId = workflowFormBiz.startWorkFlowById(userId, businessKey, processDefinitionId,oaSealMap);
        oaSeal.setProcessInstanceId(processInstanceId);
        this.update(oaSeal);
        return processInstanceId;
    }

    /**
     * 开始审批
     * @param oaSeal
     * @param taskId
     * @param comment
     */
    public void tx_startSealApplyAudit(OaSeal oaSeal, String taskId, String comment, Long userId, Long approvalId) {
        String sql = "processInstanceId = " + oaSeal.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(oaSeal.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        String id = businessKey.split("\\.")[1];
        oaSeal.setId(Long.parseLong(id));
//        this.update(oaSeal);
        this.updateByStrWhere(oaSeal, sql);
        workflowFormBiz.tx_startAudit(taskId, oaSeal.getProcessInstanceId(), comment, oaSeal.getAudit(), userId,approvalId);
    }

    /**
     * @Description: 根据流程实例id返回值
     * @author: lzh
     * @Param: [processInstanceId]
     * @Return: com.oa.entity.workflow.OaSeal
     * @Date: 10:54
     */
    public OaSeal getOaSealByProcessInstanceId(String processInstanceId) {
        String sql = " processInstanceId = " + processInstanceId;
        List<OaSeal> oaSealList = this.find(null, sql);
        return oaSealList.get(0);
    }
}
