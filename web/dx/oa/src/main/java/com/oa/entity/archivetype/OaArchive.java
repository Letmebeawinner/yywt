package com.oa.entity.archivetype;

import com.oa.entity.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OaArchive extends BaseAuditEntity{

    //党号
    private String danghao;

    //件号
    private String jianhao;

    //责任者
    private String author;

    //文号
    private String wenhao;

    //题名
    private String autograph;
    //页数
    private String pages;

    //机构或问题
    private String orginzation;

    //附件
    private String file;
    //附件名
    private String fileName;

    //部门id
    private Long departId;

    //备注
    private String description;

    private Long typeId;

    private String departmentName;

    private String typeName;

}
