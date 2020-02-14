package com.oa.entity.workflow;

import com.oa.annotation.Like;
import com.oa.entity.BaseAuditEntity;
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
public class OaLetter extends BaseAuditEntity {
    @Like
    private String title;//公文标题
    private String url;//公文路径
    private String context;//内容
    private String deptLeaderOption;//领导意见
    private Long creator;
    private Long modifier;
    private String applyCompany;//来文单位
    @Like
    private String letterNo;//文号
    @Like
    private String orderNo;//序号
    @Like
    private String secretLevel;//密级
    @Like
    private String urgentLevel;//紧急程度;
//    公文模板id
    private Integer templateTypeId;
    //发送单位
    private String sendToUnit;
    //核稿信息
    private String confirmOption;
    //会签信息
    private String countersignOption;
    //签发信息
    private String confirmToSendOption;

    //附件地址
    private String fileUrl;
    //附件名
    private String fileName;

    private Integer type;//类型 1公文（红头） 2公文（内部）
    
    private String timeStamp;//时间戳
 }
