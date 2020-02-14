package com.oa.entity.seal;

import com.oa.entity.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * oa申请印章流程
 *
 * @author lzh
 * @create 2017-10-27 10:30
 */
@Data
@EqualsAndHashCode(callSuper =  false)
public class OaSeal extends BaseAuditEntity{
    private String sealType;//印章类别串
    private Integer useSealNum;//用印数量
    private String departAuditOption;//部门领导意见
    private String schoolOption;//分管校领导意见
    private String schoolLeaderOption;//常务副校长意见
    private String officeLeaderOption;//办公室主任意见
    private Date useSealTime;//用印时间
    //附件地址
    private String fileUrl;
    //附件名
    private String fileName;
}
