package com.houqin.entity.sysuser;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 系统用户
 *
 * @author lzh
 * @create 2017-01-07-16:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BaseEntity{
    /**用户名*/
    private String userName;
    /**用户编号（可用户于登录）*/
    private String userNo;
    /**密码*/
    private String password;
    /**邮箱号*/
    private String email;
    /**手机号*/
    private String mobile;

    /**
     * 用户类型 ， 1. 管理员 2.教职工 3. 学员, 4 驾驶员  5单位报名 6物业员工
     */
    private Integer userType;

    public SysUser() {}

    public SysUser(Map<String, String> map) {
        this.userName = map.get("userName");
        this.userNo = map.get("userNo");
        this.password = map.get("password");
        this.email = map.get("email");
        this.mobile = map.get("mobile");
        this.setId(Long.parseLong(map.get("id")));
        this.userType=Integer.parseInt(map.get("userType"));
    }
}
