package com.a_268.base.util;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.redis.RedisCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 后台登录用户工具类
 *
 * @author s.li
 * @create 2016-12-13-11:51
 */
public class SysUserUtils {
    private static RedisCache redisCache = RedisCache.getInstance();
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * 获取登录管理员用户ID
     * @param request HttpServletRequest
     * @return 用户ID
     */
    public static Long getLoginSysUserId(HttpServletRequest request){
        Map<String,String> userMap = getLoginSysUser(request);
        if(userMap!=null){
            String userId = userMap.get("id");
            return Long.parseLong(userId);
        }
        return 0L;
    }

    /**
     * 获取登录的管理员用户信息
     * @param request HttpServletRequest
     * @return Map<String,String>类型的用户信息
     */
    public static Map<String,String> getLoginSysUser(HttpServletRequest request){
        String loginKey = WebUtils.getCookie(request, BaseCommonConstants.LOGIN_KEY);
        String stringUser = (String)redisCache.get(loginKey);
        if(!StringUtils.isTrimEmpty(stringUser)){
            Map<String,String> userMap = gson.fromJson(stringUser,new TypeToken<Map<String,String>>(){}.getType());
            if(userMap!=null){
                return userMap;
            }
        }
        return null;
    }
}
