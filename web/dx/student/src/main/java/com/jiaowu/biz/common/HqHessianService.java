package com.jiaowu.biz.common;

import com.a_268.base.core.Pagination;

import java.util.Map;

/**
 * Created by MaxWe on 2017/10/17.
 */
public interface HqHessianService {
    /**
     * 初始化添加报修页面
     */
    String toSaveRepair();

    /**
     * 添加报修申请
     */
    String saveRepair(String param);

    /**
     * 查询个人报修
     */
    Map<String, String> getRepairsDtos(String repairJson, Pagination pagination);

    /**
     * 查看报修详情
     */
    String getRepairDtosById(Long id);

    /**
     * 获取所有学员的报修申请
     * @param repairJson 报修对象
     * @param pagination 分页对象
     * @return 报修列表 分页对象
     */
    Map<String, String> getAllStudentRepairList(String repairJson, Pagination pagination);

    /**
     * 根据学员id 查询保修记录
     * @param join 学员ids
     * @param pagination 分页
     * @return 分页和报修记录
     */
    Map<String,String> queryStudentRepairRecord(String join, Pagination pagination);
}
