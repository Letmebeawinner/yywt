package com.base.common;

/**
 * 线上 部门 权限
 * use dx_base
 * select * from department where departmentName like '%审批%';
 *
 * @author YaoZhen
 * @create 11-02, 11:27, 2017.
 */
public class ResearchConstants {
    /**
     * 线上 可以申报科研的班主任的部门的id
     */
    public static final int JW_AND_KY = 89;

    /**
     * 线上-->科研与咨政
     */
    public static final int KY_AND_ZZ = 90;

    /**
     * 线上-->科研与咨政-->一级审核部门ID
     */
    public static final int DEPARTMENT_ID = 91;

    /**
     * 线上-->科研与咨政-->二级审核处ID
     */
    public static final int HEADS_ID = 92;

    /**
     * 线上-->科研与咨政-->三级审核领导ID
     */
    public static final int LEADER_ID = 93;

    /**
     * 线上 --> 科研与咨政 --> 一级审批部门 --> 科研部门id
     */
    public static final int KY_ONE = 94;

    /**
     * 线上 --> 科研与咨政 --> 一级审批部门 --> 咨政部门 id
     */
    public static final int ZZ_ONE = 95;

    /**
     * 线上 --> 科研与咨政 --> 二级审批处 --> 科研处id
     */
    public static final int KY_TWO = 96;

    /**
     * 线上 --> 科研与咨政 --> 二级审批处 --> 咨政处id
     */
    public static final int ZZ_TWO = 97;

    /**
     * 线上 --> 科研与咨政 --> 三级审批处领导 --> 科研处领导id
     */
    public static final int KY_THREE = 98;

    /**
     * 线上 --> 科研与咨政 --> 三级审批处领导 --> 咨政处领导id
     */
    public static final int ZZ_THREE = 99;


    /**
     * 线下 --> 科研与咨政
     */
    public static final int KY_AND_ZZ_DOWN = 85;

//    /**
//     * 线下-->科研与咨政-->一级审核部门--> departmentId
//     */
//    public static final int DEPARTMENT_ID = 86;
//
//    /**
//     * 线下-->科研与咨政-->二级审核处--> departmentId
//     */
//    public static final int HEADS_ID = 87;
//
//    /**
//     * 线下-->科研与咨政-->三级审核领导--> departmentId
//     */
//    public static final int LEADER_ID = 88;
}
