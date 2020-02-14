package com.app.utils;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.WebUtils;
import com.app.entity.SysUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class CommenUtils {
    private RedisCache redisCache = RedisCache.getInstance();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    public  String setCookieAndPermission(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
      Map<String,Object> map = new HashMap<>();
        //把用户信息转换成String
        String stringUser = gson.toJson(sysUser);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //设置用户信息到缓存中（缓存24小时）
        redisCache.set(uuid, stringUser, 60 * 60 * 24);
        //设置登录Key
        WebUtils.setCookie(response, BaseCommonConstants.LOGIN_KEY, uuid, 1);//缓存一天
        return uuid;
      /*  //设置用户权限到缓存中
        getSysUserResourceList(uuid, sysUser.getId());*/
    }
}
