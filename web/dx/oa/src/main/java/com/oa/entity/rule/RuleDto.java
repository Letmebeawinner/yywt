package com.oa.entity.rule;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 规章制度拓展表
 *
 * @author ccl
 * @create 2017-01-05-15:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RuleDto extends Rule{


    private String typeName;

}
