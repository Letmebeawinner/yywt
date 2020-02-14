package com.keyanzizheng.common;

/**
 * 生态文明所的审批流程
 *
 * @author YaoZhen
 * @date 12-04, 10:25, 2017.
 */
public class EcologicalCivilizationConstants {
    /**
     * 刚提交 未审核
     */
    public static final int INITIALIZATION = 1;

    /**
     * 生态文明所确认立项 <br>
     * （填写结束时间）
     */
    public static final int CONFIRM_PROJECT = 2;

    /**
     * 生态文明所拒绝立项
     */
    public static final int REFUSED_PROJECT = 3;

    /**
     * 生态文明所确认立项后, <br>
     * 处室人员提交课题相关文件后, <br>
     * 再确认提交的课题相关文件, <br>
     * 通过
     */
    public static final int CONFIRM_FILE = 4;

    /**
     * 生态文明所确认立项后, <br>
     * 处室人员提交课题相关文件后, <br>
     * 再确认提交的课题相关文件, <br>
     * 拒绝
     */
    public static final int REFUSED_FILE = 5;
}
