package com.oa.entity.signature;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 签章文档中间表
 *
 * @author lzh
 * @create 2017-12-26-10:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HtmlSignature extends BaseEntity{
    //id
    private Long id;
    //文档的id
    private String documentId;
    //签章id
    private String signatureId;
    //签章数据
    private String signatureData;
}
