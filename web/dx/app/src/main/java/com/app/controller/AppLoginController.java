package com.app.controller;


import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.controller.RandomCodeController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.MD5;
import com.a_268.base.util.ObjectUtils;
import com.app.biz.common.BaseHessianService;
import com.app.biz.common.JiaoWuHessianService;
import com.app.biz.common.SysUserLogService;
import com.app.biz.common.SysUserService;
import com.app.entity.SysUser;
import com.app.entity.SysUserLog;
import com.app.entity.dto.LoginDto;
import com.app.utils.CommenUtils;
import com.app.utils.Map_ObjUtil;
import com.app.utils.UserAgentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/admin/app/authority")
public class AppLoginController extends BaseController {

    private static final String[] SQL_INJECT_ARRAY = new String[]{"'", "and", "exec", "insert", "select", "delete", "update", "count", "*", "%", "chr", "mid", "master", "truncate", "char", "declare", ";", "or", "-", "+", "="};
    private Logger logger = LoggerFactory.getLogger(AppLoginController.class);
    @Autowired
    private BaseHessianService hessianService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;

    @Autowired
    private SysUserLogService sysUserLogBiz;
    @Autowired
    private CommenUtils commenUtils;
    /**
     * @Description: 登录方法
     * @author: FANGTAO
     * @Param: [request, userName, password, ranCode]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016/12/13
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestBody LoginDto dto) {
        String userName = dto.getUserName();
        String password = dto.getPassword();
        String ranCode = dto.getRanCode();
        String codeName = dto.getCodeName();
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
            String whereSql = " 1=1 and (userNo='" + userName.trim() + "' or mobile='" + userName.trim() + "') order by id desc limit 1";
            //查询user Map
            Map<String, Object> userMap = hessianService.querySysUserList(null, whereSql);

            List<Map<String, Object>> userList = (List<Map<String, Object>>) userMap.get("userList");
            SysUser sysUser = null;
            try {
                //如果userList 不为空，
                if (userList!=null&&userList.size() > 0) {
                    sysUser = Map_ObjUtil.mapToBean(userList.get(0), SysUser.class);
                } else {
                    String sql = " status=0 and userType!=3 and ( userName='" + userName.trim() + "' or anotherName='" + userName.trim() + "' )";
                    //查询user Map
                    Map<String, Object> userMap2 = hessianService.querySysUserList(null, sql);
                    List<Map<String, Object>> userList2 = (List<Map<String, Object>>) userMap2.get("userList");
                    if (userList2!=null&&userList2.size() > 0) {
                        sysUser = Map_ObjUtil.mapToBean(userList2.get(0), SysUser.class);
                    }
                }
                if (sysUser == null) {
                    Map<String, Object> map = jiaoWuHessianService.userList(null, " status in (1,4) and idNumber='" + userName + "'");
                    if (map == null || map.get("userList") == null) {
                        return this.resultJson(ErrorCode.ERROR_DATA_NULL, "用户不存在", null);
                    }
                    List<Map<String, String>> userList3 = (List<Map<String, String>>) map.get("userList");
                    //查询user Map
                    String sql = " userType=3 and linkId=" + userList3.get(0).get("id");
                    Map<String, Object> userMap3 = hessianService.querySysUserList(null, sql);
                    List<Map<String, Object>> userList4 = (List<Map<String, Object>>) userMap3.get("userList");
                    sysUser = Map_ObjUtil.mapToBean(userList4.get(0), SysUser.class);
                }

        }catch (Exception e){
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM,"系统错误",e);
        }
        if (sysUser==null){
            return this.resultJson(ErrorCode.ERROR_DATA_NULL, "用户不存在", null);
        }
        if (sysUser.getUserType().equals(3)) {
            Map<String, Object> validateStudentMap = jiaoWuHessianService.userList(null, " status in (1,4,7,8) and id=" + sysUser.getLinkId());
            if (validateStudentMap == null || validateStudentMap.get("userList") == null) {
                return this.resultJson(ErrorCode.ERROR_DATA_NULL, "用户不存在", null);
            }
        }
            //base64解密密码
//            String decodePassword = new String(Base64.decodeBase64(password));
        if (!sysUser.getPassword().equals(MD5.getMD5(password.trim()))) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "密码错误", null);
        }
        if (sysUser.getStatus() != 0) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "用户已经被锁定", null);
        }
        String _code = (String) redisCache.get(RandomCodeController.RAND_CODE + "_" + codeName);
            System.out.println(_code);
        if (_code == null || !_code.equals(ranCode)) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "验证码错误", null);
        }

        //设置登录成功信息（缓存用户信息、获取权限数据）
        String uuid = commenUtils.setCookieAndPermission(sysUser,request,response);
        hessianService.setSysUserResourceList(uuid, sysUser.getId());
        SysUserLog sysUserLog = new SysUserLog();
        sysUserLog.setUserId(sysUser.getId());
        sysUserLog.setLoginIp(UserAgentUtil.getClientIpAddress(request));
        sysUserLog.setLoginTime(new Date());
        sysUserLog.setLoginFrom("WEB");
        String userAgent = UserAgentUtil.getUserAgent(request).split(";")[0];
        String osName = UserAgentUtil.getUserAgent(request).split(";")[1];
        sysUserLog.setUserAgent(userAgent);
        sysUserLog.setOsName(osName);
        Map<String, String> map = ObjectUtils.objToMap(sysUserLog);
        sysUserLogBiz.saveLog(map);

            redisCache.remove(RandomCodeController.RAND_CODE + "_" + codeName);
        } catch (Exception e) {
            logger.error("login()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
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
