package com.oa.entity.seal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 印章拓展表
 *
 * @author lzh
 * @create 2016-12-28-17:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SealDto extends Seal {
    private String sealFunctionName;//印章用途名
    private String sealTypeName;//印章类型名
}
