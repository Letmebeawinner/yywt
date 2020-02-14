package com.oa.entity.signature;

import com.a_268.base.core.BaseEntity;
import lombok.Data;

/**
 * 盖章
 *
 * @author lzh
 * @create 2017-12-26-14:36
 */
@Data
public class Signature extends BaseEntity{
    //文档的id
    private String documentId;
    //签章id
    private String signatureId;
    //签章类型 1公文 2请假
    private Integer documentType;
    //签章数据
    private String signatureData;
}
