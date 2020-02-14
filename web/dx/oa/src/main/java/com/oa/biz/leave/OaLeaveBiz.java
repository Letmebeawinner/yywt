package com.oa.biz.leave;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.dao.leave.OaLeaveDao;
import com.oa.entity.leave.OaLeave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzh
 * @create 2017-12-28-15:15
 */
@Service
public class OaLeaveBiz extends BaseBiz<OaLeave, OaLeaveDao>{

    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    /**
     * 开启请假工作流
     * @param oaLeave
     * @param processDefinitionId
     * @param userId
     * @return
     */
    public String tx_startLeaveProcess(OaLeave oaLeave, String processDefinitionId, Long userId, String userIds) {
        Map<String, Object> leaveDaysMap = new HashMap<>();
        List<Map<String, String>> roleList=baseHessianBiz.queryUserRoleInfoByUserId(userId);
        int leaderLevel = 0;
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
//            if(leaderLevel==0){
//                for(Map<String, String> map:roleList){
//                    if("处室领导".equals(map.get("roleName"))){//处室领导
//                        leaderLevel=2;
//                        break;
//                    }
//                }
//            }
        }
        if(leaderLevel==0){
            Map<String, String> department=baseHessianBiz.queryDepartmentBySysUserId(userId);
            if(department != null && "0".equals(department.get("parentId"))){ //部门领导
                leaderLevel = 1;
            }
        }
        int leaveDays;
        if(oaLeave.getLeaveDays()<=2){
            leaveDays=1;
        }else {
            leaveDays=2;
        }
        leaveDaysMap.put("leaderLevel", leaderLevel);//职务级别 0非领导 1部门领导 2处室领导 3分管校领导、调研员、副校长 4常务副校长
        leaveDaysMap.put("leaveDays", leaveDays);//请假天数级别 1请假小于等于2天 2请假大于2天
        leaveDaysMap.put("audit", userIds);//分管校领导审批人
        oaLeave.setApplyId(userId);
        oaLeave.setApplyTime(new Date());
        oaLeave.setAudit(0);
        this.save(oaLeave);
        Long id = oaLeave.getId();
        String businessKey = "oaLeaveBiz." + id;
        String processInstanceId = workflowFormBiz.startWorkFlowById(userId, businessKey, processDefinitionId, leaveDaysMap);
        oaLeave.setProcessInstanceId(processInstanceId);
        this.update(oaLeave);
        return processInstanceId;
    }

    /**
     * @Description: 根据流程实例id返回值
     * @author: lzh
     * @Param: [processInstanceId]
     * @Return: com.oa.entity.workflow.OaLeave
     * @Date: 10:54
     */
    public OaLeave getOaLeaveByProcessInstanceId(String processInstanceId) {
        String sql = " processInstanceId = " + processInstanceId;
        List<OaLeave> oaLeaves = this.find(null, sql);
        return oaLeaves.get(0);
    }

    /**
     * 开始审批
     * @param oaLeave
     * @param taskId
     * @param comment
     */
    public void tx_startLeaveApplyAudit(OaLeave oaLeave, String taskId, String comment, Long userId,String userIds) {
        String sql = " processInstanceId = " + oaLeave.getProcessInstanceId();
        this.updateByStrWhere(oaLeave, sql);
        workflowFormBiz.tx_startAudit(taskId, oaLeave.getProcessInstanceId(), comment, oaLeave.getAudit(), userId,userIds);
    }
}
