package com.a_268.base.interceptor;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.a_268.base.util.WebUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.axis2.context.externalize.SafeObjectOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 后台登录及权限拦截
 * @author s.li
 * @create 2016-12-13-11:31
 */
public class AdminLoginInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RedisCache redisCache = RedisCache.getInstance();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isok = super.preHandle(request, response, handler);

        Map<String,String> userMap = SysUserUtils.getLoginSysUser(request);
        //如果未登录，则定向登录页面
        if(userMap==null){
            System.out.println(request.getRequestURL());
            if(request.getRequestURI().contains("admin/app")){
                return false;
            }
            logger.info("------------------用户未登录---------------------");
            response.sendRedirect(BaseCommonConstants.basePath+"/admin/toLogin.json");
            return false;
        }
        Long userId = Long.parseLong(userMap.get("id"));
        //验证权限
        isok = cheachAuthority(userId,request);
        if(!isok){
            logger.info("------------------------------用户没有权限操作:"+request.getRequestURI()+"-----------------------");
            response.sendRedirect(BaseCommonConstants.basePath+"/admin/notAuthority.json");
        }
        return isok;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    /**
     * 验证用户权限
     * @param userId 用户ID
     * @param request HttpServletRequest
     * @return ture有权限 ，false没权限
     */
    private boolean cheachAuthority(Long userId,HttpServletRequest request){
        //获取登录Key
        String sid = WebUtils.getCookie(request,BaseCommonConstants.LOGIN_KEY);
        //如果未登录
        if(StringUtils.isTrimEmpty(sid)){
            return false;
        }
        //1、获取系统所有权限数据
        String allAuthorityList = (String)redisCache.regiontGet(BaseCommonConstants.ALL_AUTHORITY_KEY);
        if(StringUtils.isTrimEmpty(allAuthorityList)){
            return true;
        }
        //当前访问的URI
        String uri = request.getRequestURI();
        System.out.println(request.getParameterNames());
        System.out.println(request.getParameterNames());
        //把所有的权限数据转换成List<Map<String,String>>
        List<Map<String,String>> allListMap = gson.fromJson(allAuthorityList,new TypeToken<List<Map<String,String>>>(){}.getType());
        int _index = 0;
        for(Map<String,String> map : allListMap){
            String resourcePath = dealUri(map.get("resourcePath"));
            if(resourcePath.equals(uri)){
                _index++;
                break;
            }
        }
        if(_index==0){
            return true;
        }

        //用户权限
        String userAuthorityList = (String)redisCache.get(BaseCommonConstants.LOGIN_USER_AUTHORITY_KEY_PX+sid+userId);
        //用户没有得到任何权限
        if(StringUtils.isTrimEmpty(userAuthorityList)){
            return false;
        }
        //把用户的权限数据转换成List<Map<String,String>>
        List<Map<String,String>> userListMap = gson.fromJson(userAuthorityList,new TypeToken<List<Map<String,String>>>(){}.getType());
        _index = 0;
        for(Map<String,String> map : userListMap){
            String resourcePath = dealUri(map.get("resourcePath"));
            if(resourcePath.equals(uri)){
                _index++;
                break;
            }
        }
        if(_index>0){
            return true;
        }
        return false;
    }

    private String dealUri(String uri) {
        if (uri.contains("?")) {
            uri = uri.substring(0, uri.lastIndexOf("?"));
        }
        return uri;
    }
}
