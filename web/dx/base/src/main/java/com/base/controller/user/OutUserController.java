package com.base.controller.user;


import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.base.biz.common.JiaoWuHessianService;
import com.base.biz.connection.SysUserDepartmentBiz;
import com.base.biz.user.SysUserBiz;
import com.base.entity.user.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by 李帅雷 on 2017/10/23.
 */
@Controller
@RequestMapping("/user")
public class OutUserController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(SysUserController.class);
    @Autowired
    private SysUserBiz sysUserBiz;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;
    @Autowired
    private SysUserDepartmentBiz sysUserDepartmentBiz;
    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"syUser"})
    public void initSysUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("syUser.");
    }
    /**
     * 查询用户列表
     *
     * @param request    HttpServletRequest
     * @return 用户列表页面
     */
    @RequestMapping("/getUserList")
    @ResponseBody
    public Map<String,Object> getUserList(HttpServletRequest request) {
        try {
            String whereSql = " status=0 and userType!=1";
            List<SysUser> userList = sysUserBiz.find(null, whereSql);
            List<Map<String,Object>> userMapList=new LinkedList<Map<String,Object>>();
            if(userList!=null&&userList.size()>0){
                for(SysUser sysUser:userList){
                    Map<String,Object> map=new HashMap<String,Object>();
                    map.put("userName",sysUser.getUserName());
                    map.put("userNo",sysUser.getUserNo());
                    map.put("email",sysUser.getEmail());
                    map.put("mobile",sysUser.getMobile());
                    map.put("userType",sysUser.getUserType());
                    map.put("password","111111");
                    if(sysUser.getUserType().equals(3)){
                        Map<String,String> userMap=jiaoWuHessianService.getUserById(sysUser.getLinkId());
                        if(userMap!=null) {
                            map.put("classTypeId", userMap.get("classTypeId"));
                            map.put("classTypeName", userMap.get("classTypeName"));
                            map.put("classId", userMap.get("classId"));
                            map.put("className", userMap.get("className"));
                        }
                    }
                    userMapList.add(map);

                }
            }
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, userMapList);
        } catch (Exception e) {
            logger.error("OutUserController.getUserList",e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }


}
