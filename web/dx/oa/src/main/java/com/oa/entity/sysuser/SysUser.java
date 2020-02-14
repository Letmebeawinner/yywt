package com.oa.entity.sysuser;

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
    
    private Long departmentId;
    /**
     * 用户类型 ， 1. 管理员 2.教职工 3. 学员, 4 驾驶员
     */
    private Integer userType;
    /**
     * 链接ID，可以是学员ID或教职工ID
     */
    private Long linkId;
    /**
     * 别名
     */
    private String anotherName;
    public SysUser() {}

    public SysUser(Map<String, String> map) {
        this.userName = map.get("userName");
        this.userNo = map.get("userNo");
        this.password = map.get("password");
        this.email = map.get("email");
        this.mobile = map.get("mobile");
        this.setUserType(Integer.parseInt(map.get("userType")));
        this.setId(Long.parseLong(map.get("id")));
    }
}
