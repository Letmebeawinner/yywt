package com.keyanzizheng.constant;

/**
 * 调研报告列表
 *
 * @author YaoZhen
 * @date 01-22, 11:28, 2018.
 */
public class ReportAuditConstants {

    /**
     * 未有人审批
     */
    public static final int PENDING_APPROVAL = 0;


    /**
     * 通过初审
     */
    public static final int THROUGH_FIRST_INSTANCE = 1;

    /**
     * 未通过初审
     */
    public static final int FIRST_INSTANCE_FAILED = 2;

    /**
     * 复审通过
     */
    public static final int REVIEW_THROUGH = 3;

    /**
     * 复审未通过
     */
    public static final int REVIEW_FAILED = 4;
}
