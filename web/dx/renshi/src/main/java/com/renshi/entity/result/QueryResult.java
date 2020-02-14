package com.renshi.entity.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 成果
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class QueryResult {
    private static final long serialVersionUID = -3545341344340282402L;
    private Long id; //成果id
    private Long employeeId; //教职工id
    private String employeeName; //申报人姓名
    private Long sysUserId; //申报人id
    private Integer resultForm;//1 论文 2 著作
    private String name;//成果名称
    private java.util.Date datTime;//结项时间
    private String resultDepartment;//结项单位
    private String publish;//出版社名称
    private String publishNumber;//刊号
    private java.util.Date publishTime;//发表时间
    private String workName;//作者姓名/主编
    private Long wordsNumber;//成果字数/主编字数
    private String associateEditor;//副主编
    private Long associateNumber;//副主编字数
    private String chapter;//参编章节
    private String digest;//获奖情况
    private String remark;//备注
    private Long intoStorage;//是否入库 1否 2 是
    private java.util.Date storageTime;//入库时间
    private Integer passStatus;//审核状态 1：未审核 2：部门审核通过 3：部门审核未通过 4：科研处审核通过 5：科研处审核未通过
    private Integer ifFile;//是否归档
    private Integer status;//状态
}
