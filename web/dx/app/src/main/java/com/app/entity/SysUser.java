package com.app.entity;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 管理员用户Entity
 *
 * @author s.li
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = -6034634689029517088L;
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
     * 用户类型 ， 1. 管理员 2.教职工 3. 学员, 4 驾驶员  5单位报名 6物业员工
     */
    private Integer userType;
    /**
     * 部门id
     */
    private Long departmentId;

    //部门名称
    private String departmentName;
    /**
     * 链接ID，可以是学员ID或教职工ID
     */
    private Long linkId;
    /**
     * 最后读取消息时间
     */
    private Timestamp lastReadTime;
    //别名
    private String anotherName;
    //单位ID，只有usertype为4时，才有。
    private Long unitId;

    private Integer sort;//排序
}
