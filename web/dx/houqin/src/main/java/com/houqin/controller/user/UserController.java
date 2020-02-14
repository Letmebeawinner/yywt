package com.houqin.controller.user;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.common.reflect.TypeToken;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.common.JiaoWuHessianService;
import com.houqin.common.BaseHessianService;
import com.houqin.entity.sysuser.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学员列表
 *
 * @author ccl
 * @create 2017-02-10-17:01
 */
@Controller
@RequestMapping("/admin/houqin")
public class UserController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private JiaoWuHessianService jwHessianService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private BaseHessianService baseHessianService;

    private static final String selectUserList = "/user/userList";

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
     * @Description:获取学员的列表
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2017-02-15
     */
    @RequestMapping("/ajax/selectUserList")
    public String selectUserList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = "1=1";
            List<Map<String, String>> StudentList = new ArrayList<>();

            Map<String, Object> userMap = jwHessianService.userList(pagination, whereSql);
            Map<String, String> _pagination = (Map<String, String>) userMap.get("pagination");
            if (_pagination != null && _pagination.size() > 0) {
                Pagination page = gson.fromJson(gson.toJson(_pagination), new TypeToken<Pagination>() {
                }.getType());
                page.setRequest(request);
                request.setAttribute("_pagination", page);
            }
            userMap = jwHessianService.userList(pagination, "1=1 and status !=0");
            StudentList = (List<Map<String, String>>) userMap.get("userList");
            request.setAttribute("StudentList", StudentList);
        } catch (Exception e) {
            logger.error("UserController--selectUserList", e);
            return this.setErrorPath(request, e);
        }
        return selectUserList;
    }

    /**
     * 添加物业人员
     *
     * @return 添加页面
     */
    @RequestMapping("/initAddUser")
    public String initAddUser() {
        return "/user/add-sysuser";
    }

    /**
     * 添加管理员用户
     *
     * @param request HttpServletRequest
     * @param sysUser 用户属性对象
     * @return JSON数据
     */
    @RequestMapping("/addSysUser")
    @ResponseBody
    public Map<String, Object> addSysUser(HttpServletRequest request,
                                          @ModelAttribute("sysUser") SysUser sysUser) {
        try {
            // 验证两次密码是否一致
            String result = vPassword(sysUser.getPassword(), request.getParameter("rpassword"));
            if (result != null) return resultJson(ErrorCode.ERROR_PARAMETER, result, null);
            // 执行添加用户
            baseHessianBiz.addSysUser(sysUser);
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("addSysUser()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 验证密码
     *
     * @param password  密码
     * @param rPassword 确认密码
     * @return Map&lt;String,Object&gt;
     */
    private String vPassword(String password, String rPassword) {
        //验证密码
        if (StringUtils.isTrimEmpty(password)) return "密码不能为空";
        if (!password.equals(rPassword)) return "两次密码不一致";
        return null;
    }


    /**
     * 查询用户列表
     *
     * @param request    HttpServletRequest
     * @param pagination 分页条件
     * @return 用户列表页面
     */
    @RequestMapping("/queryUserList")
    public String queryUserList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = " 1=1";
            String check = request.getParameter("check");//查询条件
            request.setAttribute("check", check);
            if (!StringUtils.isTrimEmpty(check)) {
                whereSql += " and (userName like '%" + check + "%' or email = '" + check + "' or mobile ='" + check + "')";
            }
            whereSql += " and userType=6";

            Map<String, Object> map = baseHessianBiz.querySysUserList(pagination, whereSql);

            Map<String, String> _pagination = (Map<String, String>) map.get("pagination");
            pagination.setBegin(Integer.parseInt(_pagination.get("begin")));
            pagination.setTotalCount(Integer.parseInt(_pagination.get("totalCount")));
            pagination.setEnd(Integer.parseInt(_pagination.get("end")));
            pagination.setCurrentPage(Integer.parseInt(_pagination.get("currentPage")));
            pagination.setCurrentUrl(_pagination.get("currentUrl"));
            pagination.setRequest(request);
            pagination.setPageSize(Integer.parseInt(_pagination.get("pageSize")));
            pagination.setTotalPages(Integer.parseInt(_pagination.get("totalPages")));

            List<SysUser> userList = new ArrayList<>();
            List<Map<String, String>> mapList = (List<Map<String, String>>) map.get("userList");
            mapList.forEach(e -> userList.add(new SysUser(e)));
            request.setAttribute("pagination", pagination);
            request.setAttribute("userList", map.get("userList"));
        } catch (Exception e) {
            logger.error("queryUserList()--error", e);
        }
        return "/user/sysuser-list";
    }

    /**
     * 初始化修改管理员用户页面
     *
     * @param request HttpServletRequest
     * @param id      用户id
     * @return 用户属性对象
     */
    @RequestMapping("/toUpdateSysUser")
    public String toUpdateSysUser(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            SysUser _sysUser = baseHessianBiz.getSysUserById(id);
            request.setAttribute("_sysUser", _sysUser);
        } catch (Exception e) {
            logger.error("updateSysUser()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/user/update-sysuser";
    }

    @RequestMapping("/delSysUserById")
    @ResponseBody
    public Map<String, Object> delSysUserById(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            baseHessianService.delSysUserById(id);
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("addSysUser()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }
}
