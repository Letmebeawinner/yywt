package com.renshi.entity.fertility;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 计生管理
 * Created by 268 on 2016/12/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Fertility extends BaseEntity {

    private static final long serialVersionUID = -1379711205718112035L;
    private Long employeeId; //教职工id
    private String name;  //申请人姓名
    private String url;  //文件上传地址
    private Integer ifPass;  //审核状态
    private String contractUrl; //合同上传地址

}
