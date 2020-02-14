package com.keyanzizheng.entity.oa;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class Archive extends BaseEntity {

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

    //日期
    private Date archivedate;

    //页数
    private String pages;

    //机构或问题
    private String orginzation;

    //附件
    private String file;

    //部门id
    private Long departId;

    //备注
    private String description;

    private Long typeId;


    private String departmentName;

    private String typeName;

    //文件名
    private String fileName;

    //入库标识， 0 未入库 ，1 已入库
    private Integer stockFlag;

}
