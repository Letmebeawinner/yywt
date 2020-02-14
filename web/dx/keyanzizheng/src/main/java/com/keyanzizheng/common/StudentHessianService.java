package com.keyanzizheng.common;

import com.a_268.base.core.Pagination;
import com.keyanzizheng.constant.ResultTypeConstants;

import java.util.Map;

/**
 * 教务Hessian
 * @author 李帅雷
 *
 */
public interface StudentHessianService {


	/**
	 * 调研报告列表
     * @param pagination 分页
     * @param name 调研人姓名
     * @param resultType 审核处 1: 科研 2:生态文明
	 * @param storage 是否入库 0:未入库 1:已入库
	 * @return 分页和调研报告
	 */
    Map<String, String> listResearchReport(Pagination pagination, String name, Integer resultType, Integer storage);

	/**
	 * 调研过审
	 *
	 * @param id 调研id
     * @param status 过审状态
     * @param assessmentLevel 审批等级
     * @return 是否成功
	 */
    Boolean juryResearchReport(Long id, Integer status, Integer assessmentLevel);

	/**
	 * 查看调研信息
	 *
     * @param byId 调研报告id
     * @return 字节流
	 */
	byte[] findResearchReportById(byte[] byId);

    /**
     * 计算生态文明所的调研报告数量
     *
     * @param year 年份
     * @return int
     */
    int countResearchReport(String year);

    /**
     * 计算科研的调研报告数量
     *
     * @param whereTime 时间段
     * @param keYan 1
     * @return 0
     * @see ResultTypeConstants
     */
    int countResearchReportByKy(String whereTime, int keYan);

    /**
     * 成果入库
     *
     * @param researchReport json
     * @return 是否修改
     */
    Integer updateResearchReport(String researchReport);

    /**
	 * 调研报告列表分页
	 *
     * @param pagination    分页
     * @param hessianReport 调研报告
     * @return map
     */
    Map<String, String> listResearchReportByObj(Pagination pagination, String hessianReport);

	/**
	 * 查询科研的教师调研报告数量
	 *
	 * @param whereTime 时间
	 * @return 科研处调研报告数量
	 */
	int queryForTeacherKyReportListByTime(String whereTime);

	/**
	 * 查询学生的调研报告数量
	 *
	 * @param whereTime 时间
	 * @return 科研处调研报告数量
	 */
	int queryForStudentKyReportListByTime(String whereTime);

    /**
     * 删除调研报告
     *
     * @param id 调研报告id
     * @return 是否删除
     */
    Boolean removeReportById(Long id);
}
