package com.tongji.biz.common;

import java.util.Map;

/**
 * 干部系统Hessian接口
 */
public interface GanBuHessianService {

    /**
     * 轮训信息统计
     *
     * @return
     */
    Map<String, Object> hessianTrainStatistics();

    /**
     * 各部门干部统计
     * <table>
     * <thead>
     * <tr>
     * <th colspan="2">sex(性别)</th>
     * <th colspan="3">politics(政治面貌)</th>
     * <th colspan="2">type(类型)</th>
     * </tr>
     * </thead>
     * <tbody>
     * <tr>
     * <th>male(男)</th>
     * <th>female(女)</th>
     * <th>communist(党员)</th>
     * <th>community(团体)</th>
     * <th>masses(群众)</th>
     * <th>incumbent(现任)</th>
     * <th>reserve(后备)</th>
     * </tr>
     * </tbody>
     * </table>
     *
     * @return {@link Map}各部门干部统计(key:statisticList)
     */
    Map<String, Object> hessianCadreStatistics();
}
