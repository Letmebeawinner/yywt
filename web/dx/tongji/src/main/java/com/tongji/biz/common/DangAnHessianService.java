package com.tongji.biz.common;


import java.util.List;
import java.util.Map;

/**
 * 基础系统Hessian接口
 *
 * @author s.li
 * @create 2016-12-16-16:07
 */
public interface DangAnHessianService {


    /**
     * 查询一个档案类型
     *
     * @param archivesTypeId
     * @return
     */
    Map<String, String> queryArchivesTypeById(Long archivesTypeId);


    /**
     * 获取所有档案类型
     *
     * @return List<Map<String,String>>
     */
    List<Map<String, String>> queryAllArchivesType();


    /**
     * 修改档案类型
     *
     * @param archivesTypeId
     * @param parentId
     * @param name
     * @param sort
     * @return
     */
    String hessianUpdateArchivesType(Long archivesTypeId, Long parentId, String name, int sort);

    /**
     * 删除一个档案类型
     *
     * @param archivesTypeId
     * @return
     */
    String hessianDeleteArchivesTypeById(Long archivesTypeId);

    /**
     * 档案信息统计
     *
     * @return
     */
    Map<String, Object> hessianArchivesStatistics();

    /**
     * 档案借阅统计
     *
     * @return
     */
    Map<String, Object> hessianBorrowApplyStatistics();}
