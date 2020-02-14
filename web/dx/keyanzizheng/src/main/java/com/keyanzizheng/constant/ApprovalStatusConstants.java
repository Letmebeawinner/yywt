package com.keyanzizheng.constant;

/**
 * 审批状态
 *
 * @see ResearchConstants
 * @author YaoZhen
 * @create 11-18, 11:49, 2017.
 */
public class ApprovalStatusConstants {
    /**
     * 未有人审批
     */
    public static final int NO_APPROVED = 1;

    /**
     * 通过部门领导审批
     */
    public static final int PASS_DEPT = 2;

    /**
     * 未通过部门领导审批
     */
    public static final int NOT_PASS_DEPT = 3;

    /**
     * 通过科研审批
     */
    public static final int PASS_OFFICE = 4;

    /**
     * 未通过科研审批
     */
    public static final int NOT_PASS_OFFICE = 5;

    /**
     * 未通过科研领导审批
     */
    public static final int NOT_PASS_LEADER = 6;

    /**
     * 通过科研领导审批
     */
    public static final int PASS_LEADER = 7;

    /**
     * 课题结项
     */
    public static final int FINISH = 8;

    /**
     * 课题未通过结项
     */
    public static final int NOT_FINISH = 9;
}
