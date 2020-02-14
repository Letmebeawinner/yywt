package com.keyanzizheng.entity.result;

import com.a_268.base.core.BaseEntity;
import com.keyanzizheng.constant.ApprovalStatusConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 成果
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Result extends BaseEntity {

    private Long sysUserId; //申报人id
    private Integer resultForm;//成果形式类型id
    private String name;//成果名称
    private java.util.Date regTime;//登记时间
    private java.util.Date datTime;//结项时间
    private String resultDepartment;//结项单位
    private String publish;//出版社名称
    private String publishNumber;//刊号
    private java.util.Date publishTime;//发表时间
    private String taskForceMembers;// 将课题组成员修改为文本框的形式
    private String workName;//作者姓名/主编
    private Long wordsNumber;//成果字数/主编字数
    private String associateEditor;//副主编
    private Long associateNumber;//副主编字数
    private String chapter;//参编章节
    private String digest;//获奖情况描述
    private String remark;//备注
    private Long intoStorage;//是否入库 1否 2 是
    private java.util.Date storageTime;//入库时间
    private Integer ifFile;//是否归档 0未归档 1已归档
    private Integer resultType;//1:科研 2 ：咨政
    private Integer teacherResearch;//老师所属教研部
    private Integer resultSwitch;//1公开2公共
    private Integer journalNature;//刊物性质
    private Integer awardSituation;//获奖情况
    private java.util.Date addTime;//申报开始时间
    private java.util.Date endTime;//申报结束时间
    private String fileUrl;//申请书附件
    private String fileUrlTheory;//论证原理附件
    private String fileUrlDeclaration;//课题结项申报课件
    private String awardTitle;//获奖申报标题
    private String fileUrlAward;//获奖申报附件地址 废弃字段
    private String archiveNo;//归档编号
    private String operations;//操作人id串

    /**
     * 审批状态 1：未审批 <br>
     * 2：部门审批通过 3：部门审批未通过 <br>
     * 4：科研处审批通过 5：科研处审批未通过 <br>
     * 6: 科研处领导拒绝审批 7: 通过科研领导审批 课题结项 <br>
     * @see ApprovalStatusConstants
     */
    private Integer passStatus;

    /**
     * 教职工id
     */
    private Long employeeId;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 新建档案附件
     */
    private String fileUrlAttachment;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String mailbox;

    /**
     * 生态文明报名区分年度月度
     */
    private Integer yearOrMonthly;

    /**
     * 课题结项评级
     */
    private Integer assessmentLevel;

    /**
     * 二级类型名称
     */
    private String journalNatureName;

    /**
     * 归档到OA的档案ID
     */
    private Long oaArchiveId;
}
