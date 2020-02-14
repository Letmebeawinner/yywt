package com.keyanzizheng.entity.result;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 成果统计
 * Created by 268 on 2017/2/27.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ResultStatistics extends BaseEntity {

    private static final long serialVersionUID = 6327443939412373901L;
    private Integer resultForm;//1 论文 2 著作 3课题
    private Integer declareCount;//申报数量
    private Integer approvalCount;//入库数量
    private Integer disApprovalCount;//未入库数量
    private Integer endCount;//课题结项数量
    private Integer disEndCount;//未结项数量
    private String date;//时间
    private Integer resultType;//1：科研 2;咨政
}
