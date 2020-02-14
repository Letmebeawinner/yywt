package com.keyanzizheng.entity.statictics;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 展示统计
 * 只有全参构造函数
 *
 * @author YaoZhen
 * @date 12-18, 11:59, 2017.
 */
@Data
@AllArgsConstructor
public class StaticticVO {
    /**
     * 名称
     */
    private String name;

    /**
     * 数量
     */
    private Integer data;

    /**
     * 二级分类ID
     */
    private Long secId;

    public StaticticVO(String name, Integer data) {
        this.name = name;
        this.data = data;
    }
}
