package com.keyanzizheng.controller.result;

import com.google.common.collect.ImmutableMap;
import com.keyanzizheng.constant.ResultFormConstants;
import com.keyanzizheng.entity.award.Award;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.Optional;

/**
 * 贫血模型
 *
 * @author YaoZhen
 * @date 06-19, 16:21, 2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AwardVO extends Award {

    private static Map<Integer, String> normalMap = new ImmutableMap.Builder<Integer, String>()
            .put(1, "国家级")
            .put(2, "省级")
            .put(3, "市级")
            .put(4, "其他")
            .build();

    private static Map<Integer, String> questionMap = new ImmutableMap.Builder<Integer, String>()
            .put(1, "党校系统级")
            .put(2, "哲学社会成果奖（国家级、省级、市级）")
            .put(3, "其他")
            .build();


    /**
     * 成果名称
     */
    String resultFormName;

    /**
     * 获奖情况名称
     */
    String sitName;

    public String getResultFormName() {
        return Optional.ofNullable(ResultFormConstants.rfMap.get(super.getResultForm())).orElse("其他");
    }

    public String getSitName() {
        Integer foo = super.getAwardSituation();
        if (Optional.ofNullable(foo).orElse(1) == ResultFormConstants.QUESTION) {
            return Optional.ofNullable(questionMap.get(foo)).orElse("其他");
        } else {
            return Optional.ofNullable(normalMap.get(foo)).orElse("其他");
        }
    }
}
