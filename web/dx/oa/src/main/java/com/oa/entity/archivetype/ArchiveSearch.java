package com.oa.entity.archivetype;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ArchiveSearch extends BaseEntity {

    private String name;//调阅人姓名

    private Date applyDate;//申请时间

    private String context;//主要目的

    private String handleInfo;//处理信息

    private Long userId;//系统用户id

    private String departmentName;//部门名称


}
