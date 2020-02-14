package com.jiaowu.entity.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员用户Entity
 *
 * @author 268
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser {
    private static final long serialVersionUID = -6034634689029517088L;
    private Long id;
    private Integer status;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户编号（可用户于登录）
     */
    private String userNo;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱号
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户类型 ， 1. 管理员 2.教职工 3. 学员
     */
    private Integer userType;
    /**
     * 链接ID，可以是学员ID或教职工ID
     */
    private Long linkId;
}
