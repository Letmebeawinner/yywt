package com.base.entity.user;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LibraryUser extends BaseEntity {

    private String userName;// 用户名

    private String password;//密码

    private Integer userType;//用户类型

    private String cardNo;//一卡通卡号

    private String departmentName;//部门名称

    private String idCard;//身份证号

    private String className;//班级名称

    private String mobile;//手机号码


}
