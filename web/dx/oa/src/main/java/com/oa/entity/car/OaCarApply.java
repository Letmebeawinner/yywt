package com.oa.entity.car;

import com.oa.entity.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.activiti.engine.task.Task;

/**
 * 用车申请
 *
 * @author lzh
 * @create 2017-01-19-11:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OaCarApply extends BaseAuditEntity {
    private String userCarPeople;//用车人
    private Integer peopleNumber;//用车人数
    private String driver;//驾驶员
    private String carNo;//车牌号
    private String distance;//线路公里数
    private String startAddress;//出车地址
    private String endAddress;//结束地址
    private String userName;//用户名
    private Task task;//流程任务
    //处室领导审批
    private String departmentOption;
    //办公室管理员审批
    private String officeLeaderOption;
    //驾驶员电话
    private String driverMobile;
}
