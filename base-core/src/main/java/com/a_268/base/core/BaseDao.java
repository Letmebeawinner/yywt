package com.a_268.base.core;

import com.a_268.base.extend.SqlMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 基础Dao
 * @param <T>
 * @author jingxue.chen
 */
public interface BaseDao<T> extends SqlMapper {

    public List<T> find(Pagination page, @Param(value = "conditions") List<Condition> conditions);

    public List<T> find2(Pagination page, @Param(value = "whereSql") String whereSql);

    public Integer count(@Param(value = "whereSql") String whereSql);

    public T findById(Long id);

    public void add(T entity);

    public Integer update(T entity);

    public Integer update2(@Param("entity") T entity, @Param(value = "conditions") List<Condition> conditions);

    public Integer update3(@Param("entity") T entity, @Param(value = "whereSql") String whereSql);

    public void delete(T entity);

    public void deleteById(Long id);

    /**
     * 根据id的集合删除一批记录
     *
     * @param ids
     */
    public void deleteByIds(@Param("ids") List<Long> ids);

    public void deleteBatch(@Param(value = "whereSql") String whereSql);

    /**
     * 把ids对应的实体中的属性值更新成entity中所有非null的属性值
     *
     * @param entity
     * @param ids
     */
    public void updateBatch(@Param("entity") T entity, @Param("ids") List<Long> ids);

    /**
     * 功能:修改相关实体的递增值,如商品的浏览次数
     *
     * @param entity
     */
    public void updateIncrement(T entity);

}
