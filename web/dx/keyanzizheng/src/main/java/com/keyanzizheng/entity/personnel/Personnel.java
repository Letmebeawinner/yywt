package com.keyanzizheng.entity.personnel;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教职工
 *
 * @author YaoZhen
 * @date 11-21, 18:20, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Personnel extends BaseEntity {

    /**
     * 姓名
     */
    private String name;

    /**
     * 教研部
     */
    private Integer teachingResearchDepartment;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 职务
     */
    private String position;

    /**
     * 手机号
     */
    private String mobile;
}
