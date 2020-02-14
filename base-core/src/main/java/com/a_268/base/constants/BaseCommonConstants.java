package com.a_268.base.constants;

import com.a_268.base.redis.RedisCache;

/**
 * 全局公共常量属性对象
 */
public class BaseCommonConstants {
    /**Redis缓存接口**/
    private static RedisCache redisCache = RedisCache.getInstance();
    /**基础平台域名存放在Redis中的Key**/
    public final static String BASE_PATH_KEY="base_path_key";
    /**基础平台域名**/
    public final static String basePath = (String)redisCache.regiontGet(BASE_PATH_KEY);
    /**后台用户登录Cookie中的Key**/
    public final static String LOGIN_KEY="sid";
    /**后台所有权限在缓存中Key*/
    public final static String ALL_AUTHORITY_KEY="dx_base_all_authority_key";
    /**管理员登录后，所属的权限在缓存中的Key的前缀*/
    public final static String LOGIN_USER_AUTHORITY_KEY_PX="login_user_authority_key_px_";
    /**后台登录用户在缓存中的前缀*/
    public final static String LONIN_USER_INFO_PX="sys_lonin_user_info_px_";
}
