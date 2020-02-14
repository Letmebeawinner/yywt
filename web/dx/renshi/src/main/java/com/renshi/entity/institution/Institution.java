package com.renshi.entity.institution;

import com.a_268.base.core.BaseEntity;
import com.renshi.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 奖惩
 * Created by 268 on 2016/12/7.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Institution extends BaseEntity {

    /**
     * 教职工id
     */
    private String employeeId;
    /**
     * 奖惩标题
     */
    @Like
    private String title;
    /**
     * 颁奖单位
     */
    private String unit;
    /**
     * 是否有证书
     */
    private Integer isCertificate;
    /**
     * 证书时间
     */
    private Date certificateTime;
    /**
     * 证书照片
     */
    private String picture;
    /**
     * 奖惩说明
     */
    private String explains;
    /**
     * 教职工姓名
     */
    private String employeeName;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 现任职务
     */
    private String presentPost;

}
