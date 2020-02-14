package com.oa.entity.archivetype;

import com.oa.entity.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OaArchiveSearch extends BaseAuditEntity{
    //申请档案的内容和目的
    private String content;
}
