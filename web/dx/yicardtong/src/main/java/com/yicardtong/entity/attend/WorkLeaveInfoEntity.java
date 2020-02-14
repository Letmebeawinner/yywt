package com.yicardtong.entity.attend;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by 李帅雷 on 2017/10/21.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class WorkLeaveInfoEntity implements Serializable{
    private static final long serialVersionUID = 2119510593184063928L;
    //流水号
    private Integer cid;
    //请假时间
    private String leaDate;
    //请假原因
    private String leaName;
    //请假类型
    private String leaType;
    //自定义项
    private String define1;
    //自定义项
    private String define2;
    //修改人
    private String modifyUser;
    //修改时间
    private String modifyDate;
    //请假人员
    private String basePerId;
    //开始日期
    private String begDate;
    //终止日期
    private String endDate;
    /*//开始时间
    private String begTime;
    //终止时间
    private String endTime;*/


}
