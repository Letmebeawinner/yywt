package com.lock.dao;

import com.a_268.base.core.BaseDao;
import com.lock.entity.UserBedroomRef;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserBedroomRefDao extends BaseDao<UserBedroomRef> {

    /**
     * 获取管理卡
     * @return
     */
    public List<UserBedroomRef> queryUserList();

}
