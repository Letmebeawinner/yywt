package com.base.biz.common;

import com.a_268.base.redis.RedisCache;
import com.base.biz.permission.ResourceBiz;
import com.base.common.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by yzl on 2016/12/7.
 */
@Service
public class CommonBiz {
    private static RedisCache redisCache = RedisCache.getInstance();

    @Autowired
    private ResourceBiz resourceBiz;
    /**
     * 初始化系统数据
     */
    @PostConstruct
    private  void initSys(){
        redisCache.regionSet(com.a_268.base.constants.BaseCommonConstants.BASE_PATH_KEY, CommonConstants.contextPath);
        //初始化权限数据到缓存中
        resourceBiz.queryAllResource();

    }
}
