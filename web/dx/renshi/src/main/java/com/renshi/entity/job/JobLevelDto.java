package com.renshi.entity.job;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位级别
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JobLevelDto extends JobLevel {

    private String jobOrderName;//岗位级别

}
