package com.keyanzizheng.constant;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * 成果类型
 * select * from resultForm
 *
 * @author YaoZhen
 * @date 11-29, 11:06, 2017.
 */
public class ResultFormConstants {
    /**
     * 论文
     */
    public static final int PAPER = 1;

    /**
     * 著作
     */
    public static final int BOOK = 2;

    /**
     * 课题
     */
    public static final int QUESTION = 3;

    /**
     * 内刊
     */
    public static final int INTERNAL_PUBLICATION = 4;

    /**
     * 其他
     */
    public static final int OTHER = 5;

    /**
     * 调研报告
     * 上面的类型与数据库id相对应
     * 但本条数据只在逻辑上存在
     */
    public static final int RESEARCH = 999;

    public static Map<Integer, String> rfMap = new ImmutableMap.Builder<Integer, String>()
            .put(1, "论文")
            .put(2, "著作")
            .put(3, "课题")
            .put(4, "内刊")
            .put(5, "调研报告")
            .put(6, "其他")
            .build();
}
