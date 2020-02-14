package com.jiaowu.entity.umc;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * wifi账号
 * 学员报名完之后 自动开通wifi账号
 *
 * @author YaoZhen
 * @date 03-28, 16:06, 2018.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WifiUser extends BaseEntity {
    /**
     * 账号，支持英文，数字，中文，冒号，横线和下划线；长度最多20位
     */
    @Like
    private String account;

    /**
     * 真实姓名, 用户名，支持英文，数字，中文，冒号，横线和下划线；长度最多20位
     */
    @Like
    private String username;

    /**
     * 密码，长度为6~64位
     */
    private String password;

    /**
     * 在线数量限制，范围是0~65535
     */
    private Integer onlineMax;

    /**
     * 组织关系，格式为： /上级/下级/
     * 前后必须有/ 斜杠
     */
    private String part;

    /**
     * 账号有效期为 Unix时间戳 ，不可为空，若不需要指定则填0即可
     * 必须写包装类  党校的框架不能反射出基本数据类型
     */
    private Timestamp overdueTime;

    /**
     * 手机号为11位数字，UMC那边可为空, 但逻辑上不能为空
     * 因为报名时必填手机号
     */
    private String phone;

    /**
     * 学员DB里的userId
     */
    private Long studentLinkId;
}
