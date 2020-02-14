package com.jiaowu.entity.userExcelRecord;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2017/11/3.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserExcelRecord extends BaseEntity {

    private static final long serialVersionUID = -4839132599504095323L;
    //用户类型
    private Integer userType;
    //系统用户ID
    private Long sysuserId;
    //班型ID
    private Long classTypeId;
    //班型名称
    private String classTypeName;
    //班次ID
    private Long classId;
    //班次名称
    private String className;
    //标题
    private String title;
    //附件地址
    private String url;


}
