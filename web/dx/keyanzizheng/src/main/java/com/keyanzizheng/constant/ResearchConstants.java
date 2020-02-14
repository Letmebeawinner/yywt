package com.keyanzizheng.constant;

/**
 * 线上 部门 权限<br>
 * use dx_base<br>
 * select * from department where departmentName like '%审批%';
 *
 * @author YaoZhen
 * @create 11-02, 11:27, 2017.
 */
public class ResearchConstants {

    /**
     * 线上-->科研处id
     * 改版后返回子菜单也只能看到对于部门的模块
     */
    @Deprecated
    public static final int KY_DEPT = 90;

    /**
     * 线上-->生态所id
     * 改版后返回子菜单也只能看到对于部门的模块
     */
    @Deprecated
    public static final int ZZ_DEPT = 105;

    /**
     * 部门领导(处室审批)
     */
    public static final int DEPT_HEAD = 37;

    /**
     * 科研处
     */
    public static final int RES_DEPT = 36;

    /**
     * 科研处领导
     */
    public static final int RES_LEADER = 38;

    /**
     * 生态所-初审
     */
    public static final int RES_ECOLOGY_FIRST = 43;

    /**
     * 生态所-二审
     */
    public static final int RES_ECOLOGY_SECOND = 44;

}
