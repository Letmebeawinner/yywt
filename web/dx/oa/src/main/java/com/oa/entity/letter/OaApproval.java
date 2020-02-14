package com.oa.entity.letter;

import com.a_268.base.core.BaseEntity;
import com.oa.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 公文发布
 *
 * @author ws
 * @create 2018-05-31-10:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OaApproval extends BaseEntity {

    @Like
    private String title;//标题

    private Long sysUserId;//系统用户id
    private String sysUserName;//系统用户

    private String processInstanceId;//流程实例id
    private Long letterId;//公文id

    private Integer approvalStatus;//审批状态

    private Integer type;//1总管理员发布2部门领导发布

    private Long sender;//发布者
    private String senderName;//发布者

    private String timeStamp;//时间戳

    private Date effectiveTime;//时效

    private Integer letterType;//公文类型 1红头公文 2内部公文
}
