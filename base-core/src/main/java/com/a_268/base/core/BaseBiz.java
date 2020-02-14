package com.a_268.base.core;

import com.a_268.base.util.ClassUtils;
import com.a_268.base.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * biz层的基类，所有biz类必须继承自此类，该类不能直接使用。
 * <p>
 * 		将biz层一些通用的操作给抽离出来，封装到此类中，其他biz类必须继承此类，子类可以直接使用此类中的方法。<br/>
 * 		该类使用泛型实现了实体和dao层封装，子类继承此方法时必须指明对应的Entity和Dao具体实现类，在{@link #setBaseDao(BaseDao)}方法中使用spring注解方式实现。<br/>
 * 		子类在需要使用dao对象的地方，直接调用baseDao.<i>method()</i>，该类当前只支持自动装配一个dao实例，如果需要多个，在自己的biz类中以spring注解方式自行配置。
 * </p>
 * @param <T> 主要操作的实体类型
 * @param <K> 主要操作的Dao类型
 * @author jingxue.chen
 */
public abstract class BaseBiz<T extends BaseEntity,K extends BaseDao<T>>  {

    private Class<T> entityClass;
    private final List<Condition> emptyConditions = new ArrayList<Condition>();

    @SuppressWarnings("unchecked")
	private Class<T> getEntityClass(){
        if(this.entityClass == null){
            Type type = getClass().getGenericSuperclass();
            Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
            this.entityClass = (Class<T>) trueType;
        }
        return this.entityClass;
    }

    protected K baseDao;

    @Autowired
    public final void setBaseDao(K baseDao) {
        this.baseDao = baseDao;
    }

    public void deleteById(Object id){
        if(id == null || !(id instanceof Number)) return;
        baseDao.deleteById((Long)id);
    }

    public String getTableName()
    {
        return ClassUtils.getUpperFirstLetterSimpleClassName(getEntityClass().getSimpleName());
    }

    public void delete(T entity) {
        baseDao.delete(entity);
    }

    public T findById(Object id) {
        if(id == null || !(id instanceof Number)) return null;
        return baseDao.findById((Long) id);
    }

    public List<T> findAll(){
        return this.find(null, emptyConditions);
    }

    /**
     * 根据分页和条件进行查询。如果不需要分页，把pagination设为null。
     * @param pagination
     * @param conditions
     * @return
     */
    public List<T> find(Pagination pagination, List<Condition> conditions) {
        return baseDao.find(pagination, conditions);
    }

    public List<T> find(Pagination pagination) {
        return this.find(pagination, emptyConditions);
    }

    public List<T> find(Pagination pagination, String whereSql){
        return baseDao.find2(pagination,whereSql);
    }

    /**
     * 根据分页和条件进行查询。如果不需要分页，把pagination设为null。
     * 主要是为了方便一个条件的查询，不用在调用时自己封装成List
     * @param pagination
     * @param condition
     * @return
     */
    public List<T> find(Pagination pagination, Condition condition) {

        List<Condition> conditions = null;
        if(condition != null){
            conditions = new ArrayList<Condition>();
            conditions.add(condition);
        }
        return find(pagination, conditions);
    }

    /**
     * 根据分页和条件进行查询。如果不需要分页，把pagination设为null。
     * @param pagination
     * @param conditions
     * @return
     */
    public List<T> find(Pagination pagination, ConditionMap conditions) {
        List<Condition> conditionList = null;
        if(conditions != null){
            conditionList = conditions.getItems();
        }
        return this.find(pagination, conditionList);
    }

    public List<T> find(ListPageQuery query) {
        return this.find(query.getPagination(), query.getConditions().getItems());
    }

    public void save(T entity){
        baseDao.add(entity);
    }

    public Integer update(T entity){
        entity.setCreateTime(null);
        return baseDao.update(entity);
    }

    public void saveBatch(List<T> entities){
        for(T entity : entities){
            save(entity);
        }
    }

    /**
     * 通过动态条件更新实体,详细查看Condition类
     * @param entity
     * @param conditions
     */
    public Integer updateByConditions(T entity,List<Condition> conditions){

        entity.setCreateTime(null);
        return baseDao.update2(entity,conditions);
    }

    /**
     * 通过动态条件字符串,更新实体;例:(whereSql = "name=name1 and sex=0 or mobile=133**")
     * @param entity
     * @param whereSql
     */
    public Integer updateByStrWhere(T entity,String whereSql){
        entity.setCreateTime(null);
        return baseDao.update3(entity,whereSql);
    }

    public void deleteByIds(List<Long> ids){
        if(!CollectionUtils.isEmpty(ids)){
            baseDao.deleteByIds(ids);
        }
    }

    public void updateBatch(T entity, List<Long> ids){
        if(!CollectionUtils.isEmpty(ids)){
            entity.setCreateTime(null);
            baseDao.updateBatch(entity, ids);
        }
    }

    public void updateBatch(List<T> entities){
        for( T entity : entities){
            update(entity);
        }
    }

    public void updateIncrement(T entity){
        entity.setCreateTime(null);
        baseDao.updateIncrement(entity);
    }

    /**
     * 查询记录数
     * @param whereSql
     * @return
     */
    public Integer count(String whereSql)
    {
       return baseDao.count(whereSql);
    }
}
