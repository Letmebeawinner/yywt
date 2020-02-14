package com.keyanzizheng.entity.approvebill;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 审批
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ApproveBill extends BaseEntity {

    private static final long serialVersionUID = -4376657907917928814L;
    private String fileUrl;//文件上传路径
    private Long resultId;//成果id
    private Integer ifPass;//是否通过
}
