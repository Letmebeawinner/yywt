package com.keyanzizheng.entity.award;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获奖申报
 *
 * @author YaoZhen
 * @date 11-14, 11:38, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Award extends BaseEntity {

    /**
     * 申请人ID
     */
    private Long userId;

    /**
     * 申请人姓名
     */
    private String userName;

    /**
     * 获奖申报标题
     */
    private String title;

    /**
     * 成果形式
     *
     * @see com.keyanzizheng.entity.result.ResultForm
     */
    private Integer resultForm;

    /**
     * 获奖情况
     */
    private Integer awardSituation;

    /**
     * 获奖情况描述
     */
    private String digest;

    /**
     * 附件地址
     */
    private String url;
}
