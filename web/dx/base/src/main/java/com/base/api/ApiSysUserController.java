package com.base.api;


import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.MD5;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.base.biz.common.HrHessianService;
import com.base.biz.common.JiaoWuHessianService;
import com.base.biz.department.DepartmentBiz;
import com.base.biz.user.SysUserBiz;
import com.base.entity.user.LibraryUser;
import com.base.entity.user.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户对外接口
 *
 * @author s.li
 * @create 2017-02-06-10:22
 */
@Controller
@RequestMapping("/api/base/user")
public class ApiSysUserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String[] SQL_INJECT_ARRAY = new String[]{"'", "and", "exec", "insert", "select", "delete", "update", "count", "*", "%", "chr", "mid", "master", "truncate", "char", "declare", ";", "or", "-", "+", "="};

    @Autowired
    private SysUserBiz sysUserBiz;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;
    @Autowired
    private HrHessianService hrHessianService;
    @Autowired
    private DepartmentBiz departmentBiz;

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

    /**
     * @param request
     * @param loginName
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "loginName") String loginName,
                                     @RequestParam(value = "password") String password) {
        try {
            if (loginName.trim().equals("")) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "请输入登录账号", null);
            }
            if (password.trim().equals("")) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "请输入密码", null);
            }
            //防止SQL注入
            if (!validateSqlInject(loginName.trim())) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "非法账号", null);
            }

            String whereSql = " 1=1 and (userNo='" + loginName.trim() + "' or mobile='"+loginName.trim()+"') order by id desc limit 1";
            List<SysUser> sysUser = sysUserBiz.find(null, whereSql);
            if (sysUser == null || sysUser.size() <= 0) {
                sysUser = sysUserBiz.find(null," status=0 and userType!=3 and ( userName='" + loginName.trim() + "' or anotherName='" + loginName.trim() + "' )");
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
            if (!_sysUser.getPassword().equals(MD5.getMD5(password.trim()))) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "密码错误", null);
            }
            if (_sysUser.getStatus() != 0) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "用户已经被锁定", null);
            }

            sysUserBiz.tx_setUserLogin(_sysUser, request, response);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, _sysUser);
            //设置登录成功信息（缓存用户信息、获取权限数据）

        } catch (Exception e) {
            logger.error("checkLogin()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }


    /**
     * 修改个人信息
     *
     * @param userName
     * @param mobile
     * @param email
     * @return
     */
    @RequestMapping("/updateSysUserInfo")
    @ResponseBody
    public Map<String, Object> updateSysUserInfo(@RequestParam(value = "userId", required = true) Long userId,
                                                 @RequestParam(value = "userName",required = false) String userName,
                                                 @RequestParam(value = "mobile",required = false) String mobile,
                                                 @RequestParam(value = "email",required = false) String email) {
        try {
            if (StringUtils.isEmpty(userId)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "非法参数", null);
            }

            SysUser sysUser = new SysUser();
            sysUser.setId(userId);
            if (!StringUtils.isTrimEmpty(userName)) {
                sysUser.setUserName(userName);
            }
            if (!StringUtils.isTrimEmpty(mobile)) {
                sysUser.setMobile(mobile);
            }
            if (!StringUtils.isTrimEmpty(email)) {
                sysUser.setEmail(email);
            }
            sysUserBiz.update(sysUser);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }


    /**
     * 查询学员列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/userList")
    @ResponseBody
    public Map<String, Object> userList(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<SysUser> sysUserList = sysUserBiz.find(null, "1=1");
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, sysUserList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 查询图书馆
     *
     * @param request
     * @return
     */
    @RequestMapping("/queryLibraryUser")
    @ResponseBody
    public Map<String, Object> queryLibraryUser(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<SysUser> sysUserList = sysUserBiz.find(null, " userType=2 or userType=3");


            List<LibraryUser> libraryUserList = new ArrayList<>();
            if (ObjectUtils.isNotNull(sysUserList)) {

                for (SysUser sysUser : sysUserList) {
                    //如果是学员
                    if (sysUser.getUserType() == 3) {
                        Map<String, String> studentMap = jiaoWuHessianService.getUserById(sysUser.getLinkId());
                        if (ObjectUtils.isNotNull(studentMap)) {
                            String className = studentMap.getOrDefault("className", "").toString();//班次名称
                            String cardNo = backConversionCardId(studentMap.getOrDefault("timeCardNo", "").toString());//考勤卡
                            LibraryUser libraryUser = new LibraryUser();
                            libraryUser.setId(sysUser.getId());
                            libraryUser.setUserName(sysUser.getUserName());
                            libraryUser.setPassword(sysUser.getPassword());
                            libraryUser.setMobile(sysUser.getMobile());
                            libraryUser.setCardNo(cardNo);
                            libraryUser.setClassName(className);
                            libraryUser.setUserType(sysUser.getUserType());
                            libraryUserList.add(libraryUser);
                        }
                    }
                    //如果是教职工
                    if (sysUser.getUserType() == 2) {
                        Map<String, String> teacherMap = hrHessianService.queryEmployeeById(sysUser.getLinkId());
                        if (ObjectUtils.isNotNull(teacherMap)) {
                            String cardNo = backConversionCardId(teacherMap.getOrDefault("cardNo", "").toString());//考勤卡
                            String department = teacherMap.getOrDefault("department", "").toString();//部门名称
                            LibraryUser libraryUser = new LibraryUser();
                            libraryUser.setId(sysUser.getId());
                            libraryUser.setUserName(sysUser.getUserName());
                            libraryUser.setPassword(sysUser.getPassword());
                            libraryUser.setMobile(sysUser.getMobile());
                            libraryUser.setCardNo(cardNo);
                            libraryUser.setDepartmentName(department);
                            libraryUser.setUserType(sysUser.getUserType());
                            libraryUserList.add(libraryUser);
                        }
                    }
                }
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, libraryUserList);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, "");
        }
        return json;
    }


    /**
     * 考勤卡反转
     *
     * @param cardId
     * @return
     */

    public static  String backConversionCardId(String cardId) {
        if(cardId!=null && cardId.length()==8){
            String st[] = new String[8];
            st = cardId.split("");
            return st[6] + st[7] + st[4] + st[5] + st[2] + st[3] + st[0] + st[1];
        }else {
            return "";
        }
    }



    /**
     * 查询个人信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/userInfo")
    @ResponseBody
    public Map<String, Object> userInfo(@RequestParam(value = "userId", required = true) Long userId) {
        try {
            SysUser sysUser = sysUserBiz.findById(userId);
            if (StringUtils.isEmpty(sysUser)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "非法参数", null);
            }
            Map<String, Object> dataMap=new HashMap<>();
            dataMap.put("userName",sysUser.getUserName());
            dataMap.put("email",sysUser.getEmail());
            dataMap.put("mobile",sysUser.getMobile());
            dataMap.put("userType",sysUser.getUserType());

            if (sysUser.getUserType() == 3) {
                Map<String, String> studentMap = jiaoWuHessianService.getUserById(sysUser.getLinkId());
                if(studentMap!=null){
                    dataMap.put("sex",studentMap.get("sex"));
                    dataMap.put("age",studentMap.get("age"));
                    dataMap.put("position",studentMap.get("job"));
                    dataMap.put("department",studentMap.get("unit"));
                }
            }else if (sysUser.getUserType() == 2) {
                Map<String, String> teacherMap = hrHessianService.queryEmployeeById(sysUser.getLinkId());
                if(teacherMap!=null){
                    dataMap.put("sex","1".equals(teacherMap.get("sex"))?"女":"男");
                    dataMap.put("age",teacherMap.get("age"));
                    dataMap.put("position",teacherMap.get("presentPost"));
                    dataMap.put("department",teacherMap.get("department"));
                }
            }
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

}
