package com.menhu.entity.task;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 课题
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Task extends BaseEntity {

    private Long employeeId; //教职工id
    private String taskLevel;//级别 国家级课题，省部级课题，地厅级课题，其它课题
    private String name;//课题名称
    private String theme;//主题词
    private String territory;//研究领域
    private String keyword;//关键字
    private String taskType;//课题类型
    private String resultForm;//成果形式
    private String studyForm;//研究形式
    private String outlay;//经费来源
    private String taskPass;//课题批准单位
    private java.util.Date projectDate;//立项时间
    private java.util.Date finishDate;//完成时间
    private String presenter;//课题主持人
    private String province;//所在省份
    private String digest;//内容摘要
    private String remark;//备注
    private Long intoStorage;//是否入库 1否 2 是

}
