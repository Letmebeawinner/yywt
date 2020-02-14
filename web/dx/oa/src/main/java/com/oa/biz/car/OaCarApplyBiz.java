package com.oa.biz.car;

import com.a_268.base.core.BaseBiz;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.dao.car.OaCarApplyDao;
import com.oa.entity.car.Car;
import com.oa.entity.car.CarApplyTask;
import com.oa.entity.car.OaCarApply;
import com.oa.entity.sysuser.SysUser;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用车申请业务层
 *
 * @author lzh
 * @create 2017-01-19-13:37
 */
@Service
public class OaCarApplyBiz extends BaseBiz<OaCarApply, OaCarApplyDao> {
    @Autowired
    IdentityService identityService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskservice;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    @Autowired
    private CarBiz carBiz;

    /**
     * @Description: 开启用车申请工作流
     * @param carApply
     * @param processDefinitionId
     * @param userId
     * @return
     */
    public String tx_startCarApplyProcess(OaCarApply carApply, String processDefinitionId, Long userId) {
        carApply.setApplyId(userId);
        carApply.setAudit(0);
        carApply.setApplyTime(new Date());
        this.save(carApply);
        Long id = carApply.getId();
        String businessKey = "carApplyBiz." + id;
        //设置用户
        String processInstanceId = workflowFormBiz.startWorkFlowById(userId, businessKey, processDefinitionId);
        carApply.setProcessInstanceId(processInstanceId);
        this.update(carApply);
        return processInstanceId;
    }

    /**
     * 开始审批
     * @param oaCarApply
     * @param taskId
     * @param comment
     */
    public void tx_startCarApplyAudit(OaCarApply oaCarApply, String taskId, String comment, Long userId) {
        String sql = " processInstanceId = " + oaCarApply.getProcessInstanceId();
        String carSql = "carID = '" + oaCarApply.getCarNo() + "'";
        if (!com.a_268.base.util.StringUtils.isTrimEmpty(oaCarApply.getDriverMobile())) {
            oaCarApply.setDriverMobile(oaCarApply.getDriverMobile().trim());
        }
        this.updateByStrWhere(oaCarApply, sql);
        if (oaCarApply.getAudit() == 0 || oaCarApply.getAudit() == 1) {
            //如果车牌号不为空而且审核已同意
            if (StringUtils.isNotEmpty(oaCarApply.getCarNo())) {
                Car car = new Car();
                //将状态改为有人使用中
                car.setStatus(2);
                carBiz.updateByStrWhere(car, carSql);
            }
        }
        workflowFormBiz.tx_startAudit(taskId, oaCarApply.getProcessInstanceId(), comment, oaCarApply.getAudit(), userId);
    }

    /**
     * @Description: 给对象name赋值
     * @author: lzh
     * @Param: [carApply]
     * @Return: com.oa.entity.car.CarApply
     * @Date: 17:01
     */
    public OaCarApply carApplySetName(OaCarApply carApply) {
        SysUser sysUser = baseHessianBiz.getSysUserById(carApply.getApplyId());
        carApply.setUserName(sysUser.getUserName());
        return carApply;
    }

    /**
     * @Description: 通过人物获取业务号
     * @author: lzh
     * @Param: [task]
     * @Return: java.lang.String
     * @Date: 16:00
     */
    public String getBusinnessKeyByTask(Task task) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
        return processInstance.getBusinessKey();
    }

    /**
     * @Description: 转换类型到任务类型
     * @author: lzh
     * @Param: [carApply]
     * @Return: com.oa.entity.car.CarApplyTask
     * @Date: 17:33
     */
    public CarApplyTask convertCarToTask(OaCarApply carApply) {
        //将用户名给附上值
        this.carApplySetName(carApply);
        CarApplyTask carTask = new CarApplyTask();
        carTask.setUserId(carApply.getApplyId());
        carTask.setUserName(carApply.getUserName());
        carTask.setProcessInstanceId(carApply.getProcessInstanceId());
        carTask.setApplyTime(carApply.getApplyTime());
        carTask.setStartAddress(carApply.getStartAddress());
        carTask.setEndAddress(carApply.getEndAddress());
        carTask.setTaskId(carApply.getTask().getId());
        carTask.setTaskName(carApply.getTask().getName());
        carTask.setPeopleNumber(carApply.getPeopleNumber());
        carTask.setReason(carApply.getReason());
        carTask.setStartTime(carApply.getStartTime());
        carTask.setEndTime(carApply.getEndTime());
        carTask.setProcessDefId(carApply.getTask().getProcessDefinitionId());
        return carTask;
    }

    /**
     * @Description: 通过任务查找对应的车辆申请
     * @author: lzh
     * @Param: [task]
     * @Return: com.oa.entity.car.CarApply
     * @Date: 17:34
     */
    public OaCarApply getCarApplyByTask(Task task) {
        Long id = Long.parseLong(this.getBusinnessKeyByTask(task));
        OaCarApply carApply = this.findById(id);
        carApply.setTask(task);
        return carApply;
    }

    /**
     * @Description: 将任务转换成车辆申请任务实体
     * @author: lzh
     * @Param: [task]
     * @Return: com.oa.entity.car.CarApplyTask
     * @Date: 9:49
     */
    public CarApplyTask getCarApplyTaskByTask(Task task) {
        OaCarApply carApply = this.getCarApplyByTask(task);
        return this.convertCarToTask(carApply);
    }

    /**
     * @Description: 通过历史流程查询已经结束的流程列表
     * @author: lzh
     * @Param: [historicProcessInstances]
     * @Return: java.util.List<com.oa.entity.car.CarApply>
     * @Date: 11:50
     */
    public List<OaCarApply> getApplyListByHistoryProcess(List<HistoricProcessInstance> historicProcessInstances) {
        String businessIds = historicProcessInstances.stream()
                                .map(HistoricProcessInstance::getBusinessKey)
                                .collect(Collectors.joining(", ", "(", ")"));
        String sql = " id in " + businessIds;
        return this.find(null, sql);
    }

    /**
     * @Description: 根据流程实例id返回值
     * @author: lzh
     * @Param: [processInstanceId]
     */
    public OaCarApply getCarApplyByProcessInstanceId(String processInstanceId) {
        String sql = " processInstanceId = " + processInstanceId;
        List<OaCarApply> carApplies = this.find(null, sql);
        return carApplies.get(0);
    }

    /**
     * 存档，完成任务, 将车的状态还原等
     * @param processInstanceId
     * @param taskId
     * @param oaCarApply
     */
    public void tx_saveCarApplyRecord(String processInstanceId, String taskId, OaCarApply oaCarApply) {
        String sql = " processInstanceId = " + processInstanceId;
        String carSql = "carID = '" + oaCarApply.getCarNo() + "'";
        this.updateByStrWhere(oaCarApply, sql);
        taskservice.complete(taskId);
        if (StringUtils.isNotEmpty(oaCarApply.getCarNo())) {
            //还车成功后将车辆状态还原成默认状态(未使用);
            Car car = new Car();
            car.setStatus(0);
            carBiz.updateByStrWhere(car, carSql);
        }
    }


}
