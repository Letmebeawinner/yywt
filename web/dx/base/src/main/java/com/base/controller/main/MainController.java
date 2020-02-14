package com.base.controller.main;


import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.controller.RandomCodeController;
import com.a_268.base.util.*;
import com.base.biz.common.JiaoWuHessianService;
import com.base.biz.common.KyHessianService;
import com.base.biz.common.OAHessianService;
import com.base.biz.common.SmsHessianService;
import com.base.biz.role.SysUserRoleBiz;
import com.base.biz.sysuserlog.SysUserLogBiz;
import com.base.biz.user.SysUserBiz;
import com.base.common.CommonConstants;
import com.base.entity.permission.Resource;
import com.base.entity.role.SysUserRole;
import com.base.entity.sysuserlog.SysUserLog;
import com.base.entity.user.SysUser;
import com.base.utils.UserAgentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统控制中心
 *
 * @author s.li
 */
@Controller
@RequestMapping("/admin")
public class MainController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);
    private PropertyUtil propertyUtil = PropertyUtil.getInstance("config");
    private static final String[] SQL_INJECT_ARRAY = new String[]{"'", "and", "exec", "insert", "select", "delete", "update", "count", "*", "%", "chr", "mid", "master", "truncate", "char", "declare", ";", "or", "-", "+", "="};

    @Autowired
    private SysUserBiz sysUserBiz;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;

    @Autowired
    private SysUserLogBiz sysUserLogBiz;
    @Autowired
    private OAHessianService oaHessianService;
    @Autowired
    private KyHessianService kyHessianService;
    @Autowired
    private SysUserRoleBiz sysUserRoleBiz;
    @Autowired
    private SmsHessianService smsHessianService;

    /**
     * @Description: 基础系统中心
     * @author: s.li
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016/12/14
     */
    @RequestMapping("/base/baseIndex")
    public String baseIndex(HttpServletRequest request) {
        try {
            logger.info("----------------user.dir=" + System.getProperty("user.dir"));
        } catch (Exception e) {
            logger.error("baseIndex()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/main/baseIndex";
    }

    /**
     * 进入登录页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request,HttpServletResponse response) {
        try {
            logger.info("----------------------------- is go login page---------------");
            request.setAttribute("source", request.getParameter("source"));
        } catch (Exception e) {
            logger.error("toLogin()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/main/login";
    }

    /**
     * @Description: 登录方法
     * @author: s.li
     * @Param: [request, userName, password, ranCode]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016/12/13
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "userName", required = true) String userName,
                                     @RequestParam(value = "password", required = true) String password,
                                     @RequestParam(value = "ranCode", required = true) String ranCode,
                                     @RequestParam(value = "codeName", required = true) String codeName) {
        try {
            if (userName.trim().equals("")) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "请输入登录账号", null);
            }
            if (password.trim().equals("")) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "请输入密码", null);
            }
            if (ranCode.equals("")) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "请输入验证码", null);
            }
            //防止SQL注入
            if (!validateSqlInject(userName.trim())) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "非法账号", null);
            }

            String whereSql = " 1=1 and (userNo='" + userName.trim() + "' or mobile='"+userName.trim()+"') order by id desc limit 1";
            List<SysUser> sysUser = sysUserBiz.find(null, whereSql);
            if (sysUser == null || sysUser.size() <= 0) {
                sysUser = sysUserBiz.find(null," status=0 and userType!=3 and ( userName='" + userName.trim() + "' or anotherName='" + userName.trim() + "' )");
                /*if (sysUser == null || sysUser.size() <= 0) {
                    Map<String, Object> map = jiaoWuHessianService.userList(null, " status in (1,4) and idNumber='" + userName + "'");
                    if (map == null || map.get("userList") == null) {
                        return this.resultJson(ErrorCode.ERROR_DATA_NULL, "用户不存在", null);
                    }
                    List<Map<String, String>> userList = (List<Map<String, String>>) map.get("userList");
                    sysUser = sysUserBiz.find(null, " userType=3 and linkId=" + userList.get(0).get("id"));
                }*/
            }
            if (sysUser == null || sysUser.size() <= 0) {
                return this.resultJson(ErrorCode.ERROR_DATA_NULL, "用户不存在", null);
            }
            SysUser _sysUser = sysUser.get(0);
            //如果登录人是学员,则验证学员的状态是否正常.
            if (_sysUser.getUserType().equals(3)) {
                Map<String, Object> validateStudentMap = jiaoWuHessianService.userList(null, " status in (1,4,7,8) and id=" + _sysUser.getLinkId());
                if (validateStudentMap == null || validateStudentMap.get("userList") == null) {
                    return this.resultJson(ErrorCode.ERROR_DATA_NULL, "用户不存在", null);
                }
            }
            //base64解密密码
//            String decodePassword = new String(Base64.decodeBase64(password));
            if (!_sysUser.getPassword().equals(MD5.getMD5(password.trim()))) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "密码错误", null);
            }
            if (_sysUser.getStatus() != 0) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "用户已经被锁定", null);
            }

            String _code = (String) redisCache.get(RandomCodeController.RAND_CODE + "_" + codeName);
            if (_code == null || !_code.equals(ranCode)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "验证码错误", null);
            }

            //设置登录成功信息（缓存用户信息、获取权限数据）
            sysUserBiz.tx_setUserLogin(_sysUser, request, response);


            SysUserLog sysUserLog = new SysUserLog();
            sysUserLog.setUserId(sysUser.get(0).getId());
            sysUserLog.setLoginIp(UserAgentUtil.getClientIpAddress(request));
            sysUserLog.setLoginTime(new Date());
            sysUserLog.setLoginFrom("WEB");
            String userAgent = UserAgentUtil.getUserAgent(request).split(";")[0];
            String osName = UserAgentUtil.getUserAgent(request).split(";")[1];
            sysUserLog.setUserAgent(userAgent);
            sysUserLog.setOsName(osName);
            sysUserLogBiz.save(sysUserLog);

            redisCache.remove(RandomCodeController.RAND_CODE + "_" + codeName);

        } catch (Exception e) {
            logger.error("login()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


    /**
     * @Description: 后台首页中心
     * @author: s.li
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016/12/13
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request, @RequestParam(value = "sysKey", required = false) String sysKey) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            if (sysKey != null && !sysKey.equals("") && !sysKey.equals("null")) {
                return "redirect:/admin/switchSystem.json?sysKey=" + sysKey;
            }
            List<Resource> resourceList = sysUserBiz.queryUserResourceList(userId);
            SysUser sysUser = sysUserBiz.findById(userId);

            String resourceSites = "";
            if (ObjectUtils.isNotNull(resourceList)) {
                for (Resource resource : resourceList) {
                    resourceSites += resource.getResourceSite();
                }
            }
            request.setAttribute("resourceList", resourceList);
            request.setAttribute("sysUser", sysUser);
            request.setAttribute("resourceSites", gson.toJson(resourceSites));

        } catch (Exception e) {
            logger.error("index()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/main/index";
    }
    /**
     * @Description: 后台首页中心
     * @author: s.li
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016/12/13
     */
    @RequestMapping("/waitProcess")
    public String waitProcess(HttpServletRequest request) {
        try {
            int waitNum=0;
            Long userId=SysUserUtils.getLoginSysUserId(request);
            //获取待处理的公文审批
            List<Map<String, Object>> taskList=oaHessianService.getOaApprovalList(userId);
//            List<Map<String, Object>> taskList=null;
            request.setAttribute("taskList",taskList);
            waitNum=waitNum+(taskList==null?0:taskList.size());
            //获取待处理的课题审批
            List<SysUserRole> roleList=sysUserRoleBiz.find(null,"userId=" + userId);
            StringBuffer roleIds=new StringBuffer(",");
            roleList.forEach(e ->
                roleIds.append(e.getRoleId()+",")
            );
            List<Map<String, String>> resultList=kyHessianService.queryResultListByRoleId(userId,roleIds.toString(),1);
            request.setAttribute("resultList",resultList);
            waitNum=waitNum+(resultList==null?0:resultList.size());
            //获取待处理的立项审批
            List<Map<String, String>> projectList=kyHessianService.queryResultListByRoleId(userId,roleIds.toString(),2);
            request.setAttribute("projectList",projectList);
            waitNum=waitNum+(projectList==null?0:projectList.size());
            //获取未读公文
            List<Map<String, String>> letterList=oaHessianService.getUnreadOaApprovalList(userId);
            request.setAttribute("letterList",letterList);
            waitNum=waitNum+(letterList==null?0:letterList.size());
            //获取未读消息
            Map<String, Object> dataMap=smsHessianService.getMyInfoRecordList(null,userId);
            List<Map<String, String>> infoRecordList= (List<Map<String, String>>) dataMap.get("infoRecordList");
            request.setAttribute("infoRecordList",infoRecordList);
            request.setAttribute("senders",dataMap.get("senders"));
            waitNum=waitNum+(infoRecordList==null?0:infoRecordList.size());
            //待处理任务总数
            request.setAttribute("waitNum",waitNum==0?null:waitNum);
        } catch (Exception e) {
            logger.error("waitProcess()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/main/wait_process";
    }

    /**
     * 修改读读的状态
     * @param id
     * @return
     */

    @RequestMapping("/ajax/updateInfoReceiveRecord")
    @ResponseBody
    public Map<String,Object> updateInfoReceiveRecord(@RequestParam(value = "id", required = false) Long id){
        try {
            smsHessianService.updateInfoReceiveRecord(id);
            return resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
    }


    /**
     * @Description: 签收任务
     * @author: lzh
     * @Param: [taskId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 16:37
     */
    @RequestMapping("/task/claim")
    @ResponseBody
    public Map<String, Object> taskClaim(@RequestParam("taskId") String taskId, HttpServletRequest request,
                                         HttpServletResponse response) {
        Map<String, Object> resultMap = null;
        try {
//          签收任务
            Long userId = SysUserUtils.getLoginSysUserId(request);
            oaHessianService.taskClaim(taskId, userId);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch(Exception e) {
            logger.error("MainController.taskClaim");
            resultMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return resultMap;
    }

    /**
     * @Description: 切换系统
     * @author: lzh
     * @Param: [taskId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 16:37
     */
    @RequestMapping("/ajax/switchSystem")
    @ResponseBody
    public Map<String, Object> ajaxSwitchSystem(HttpServletRequest request,
                                                HttpServletResponse response,
                                                @RequestParam(value = "sysKey", required = true) String sysKey) {

        String url = request.getHeader("referer");
        url=url.substring(0,url.indexOf("/",url.indexOf("//")+2));
        response.setHeader("Access-Control-Allow-Origin", url);
        response.setHeader("Access-Control-Allow-Headers", "Authentication");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        Map<String, Object> resultMap = null;
        try {
            String sid = WebUtils.getCookie(request, com.a_268.base.constants.BaseCommonConstants.LOGIN_KEY);
            Long userId = SysUserUtils.getLoginSysUserId(request);
            //只缓存24小时
            redisCache.set(CommonConstants.THIS_SYS_KEY_PX + sid + userId, sysKey, 60 * 60 * 24);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch(Exception e) {
            logger.error("MainController.ajaxSwitchSystem");
            resultMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return resultMap;
    }

    /**
     * @Description: 切换系统
     * @author: s.li
     * @Param: [request, response, sysKey]
     * @Return: java.lang.String
     * @Date: 2016/12/14
     */
    @RequestMapping("/switchSystem")
    public String switchSystem(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "sysKey", required = true) String sysKey) {
        try {
            String sid = WebUtils.getCookie(request, com.a_268.base.constants.BaseCommonConstants.LOGIN_KEY);
            Long userId = SysUserUtils.getLoginSysUserId(request);
            //只缓存24小时
            redisCache.set(CommonConstants.THIS_SYS_KEY_PX + sid + userId, sysKey, 60 * 60 * 24);
            String url = propertyUtil.getProperty(sysKey);
            return "redirect:" + url;
        } catch (Exception e) {
            logger.error("switchSystem()--error", e);
            return this.setErrorPath(request, e);
        }
    }



    /**
     * @Description: 退出登录
     * @author: s.li
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016/12/14
     */
    @RequestMapping("/outLogin")
    public String outLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String sid = WebUtils.getCookie(request, com.a_268.base.constants.BaseCommonConstants.LOGIN_KEY);
            Long userId = SysUserUtils.getLoginSysUserId(request);
            //删除用户权限缓存
            redisCache.remove(com.a_268.base.constants.BaseCommonConstants.LOGIN_USER_AUTHORITY_KEY_PX + sid + userId);
            //删除用户信息缓存
            redisCache.remove(sid);
            //删除用户切换系统Key缓存
            redisCache.remove(CommonConstants.THIS_SYS_KEY_PX + sid + userId);
            //删除用户登录Key缓存
            WebUtils.deleteCookie(request, response, com.a_268.base.constants.BaseCommonConstants.LOGIN_KEY);
        } catch (Exception e) {
            logger.error("outLogin()--error", e);
            return this.setErrorPath(request, e);
        }
        return "redirect:" + com.a_268.base.constants.BaseCommonConstants.basePath + "/admin/toLogin.json";
    }

    /**
     * @Description: 有操作权限公共跳转URL
     * @author: s.li
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016/12/13
     */
    @RequestMapping("/notAuthority")
    public String notFunction(HttpServletRequest request) {
        try {
            logger.info("-----------------------------没有权限操作");
        } catch (Exception e) {
            logger.error("notFunction()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/common/not_authority";
    }

    /**
     * 验证SQL注入
     *
     * @param s
     * @return
     */
    public boolean validateSqlInject(String s) {
        for (String sqlAttr : SQL_INJECT_ARRAY) {
            if (s.indexOf(sqlAttr) > -1) {
                return false;
            }
        }
        return true;
    }
}
