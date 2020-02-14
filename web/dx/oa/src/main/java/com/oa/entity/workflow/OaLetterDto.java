package com.oa.entity.workflow;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * oa公文固定流程
 *
 * @author lzh
 * @create 2017-03-16-11:35
 */
@Data
@EqualsAndHashCode(callSuper =  false)
public class OaLetterDto extends OaLetter {
    /**
     * 申请人
     */
    private String applyName;
}
