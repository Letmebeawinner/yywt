package com.lock.dao;

import com.a_268.base.core.BaseDao;
import com.lock.entity.UserInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by caichenglong on 2017/10/25.
 */
@Component
public interface UserInfoDtoDao extends BaseDao<UserInfoDto> {

    /**
     * 获取用户所有信息
     *
     * @return
     */
    public List<UserInfoDto> queryFloorNum(@Param("whereSql") String whereSql);


}
