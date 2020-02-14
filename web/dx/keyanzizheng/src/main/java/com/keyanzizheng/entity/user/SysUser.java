package com.keyanzizheng.entity.user;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 管理员用户Entity
 * @author 268
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysUser extends BaseEntity {

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
     * 部门id
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 链接ID，可以是学员ID或教职工ID
	 */
	private Long linkId;

    /**
     * 最后读取消息时间
     */
    private Timestamp lastReadTime;

    /**
     * 别名
     */
    private String anotherName;
}
