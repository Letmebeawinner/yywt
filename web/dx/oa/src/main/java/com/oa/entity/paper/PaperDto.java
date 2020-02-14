package com.oa.entity.paper;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 封条拓展类
 *
 * @author lzh
 * @create 2017-01-04-17:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaperDto extends Paper {
    private String paperTypeName;//封条类型名
    private String paperFunctionName;//封条用途名
    private String departmentName;//部门名
}
