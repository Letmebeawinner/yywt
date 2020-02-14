package com.houqin.controller.RepairPeople;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.common.collect.Maps;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.pepairPeople.PepairPeopleBiz;
import com.houqin.common.BaseHessianService;
import com.houqin.entity.pepairPeople.PepairPeople;
import com.houqin.entity.sysuser.SysUser;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 维修人员列表
 *
 * @author wanghailong
 * @create 2017-06-07-下午 5:32
 */
@Controller
@RequestMapping("/admin/houqin")
public class RepairPeopleController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(RepairPeopleController.class);
    //添加维修类型
    private static final String pepairPeople_add = "/pepairPeople/pepairPeople_add";
    //修改维修类型
    private static final String toUpdateSystemUser = "/pepairPeople/pepairPeople_update";
    //维修类型列表
    private static final String pepairPeople_list = "/pepairPeople/pepairPeople_list";
    //获取系统人员
    private static final String sysuser_list = "/pepairPeople/sysuser_list";
    //添加保修申请人员
    private static final String repairGetSysuserList = "/pepairPeople/repair_getSysuser_list";

    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private PepairPeopleBiz pepairPeopleBiz;
    @Autowired
    private BaseHessianService hessianService;
    /**
     * 绑定数据
     *
     * @param binder
     */
    @InitBinder({"pepairPeople"})
    public void initBinderPepairPeople(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("pepairPeople.");
    }

    @RequestMapping("/toAddPepairPeople")
    public String toAddPepairPeople() {
        return pepairPeople_add;
    }

    /**
     * 获取系统用户
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/getSystemUserList")
    public String getSystemUserList(HttpServletRequest request,
                                    @ModelAttribute("pagination") Pagination pagination,
                                    @RequestParam(value = "from", required = false) Integer from,
                                    @RequestParam(value = "username", required = false) String username,
                                    @RequestParam(value = "userType", required = false) Integer userType,
                                    @RequestParam(value = "roleId", required = false) Integer roleId,
                                    ModelMap model) {
        try {
            List<PepairPeople> pepairPeopleList = pepairPeopleBiz.findAll();
            String where = "";
            pagination.setPageSize(10);
            if (userType == null) {
                where += "  status = 0 and userType in (2,6)";
            } else {
                where += "  status = 0 and userType = "+userType;
            }
            String userName = request.getParameter("userName");
            if (!StringUtils.isTrimEmpty(userName)) {
                where += " and userName like '%" + userName + "%'";
            }
            Map<String, Object> param = Maps.newHashMap();
            param.put("username", username);
            param.put("userType", userType);
            param.put("roleId", roleId);
            Map<String, Object> map = hessianService.querySysUserAndRoleForPage(pagination, where, param);
            List<Map<String, String>> userList = (List<Map<String, String>>) map.get("userList");
            Map<String, String> _pagination = (Map<String, String>) map.get("pagination");

            pagination.setBegin(Integer.parseInt(_pagination.get("begin")));
            pagination.setTotalCount(Integer.parseInt(_pagination.get("totalCount")));
            pagination.setEnd(Integer.parseInt(_pagination.get("end")));
            pagination.setCurrentPage(Integer.parseInt(_pagination.get("currentPage")));
            pagination.setPageSize(Integer.parseInt(_pagination.get("pageSize")));
            pagination.setCurrentUrl(_pagination.get("currentUrl"));
            pagination.setTotalPages(Integer.parseInt(_pagination.get("totalPages")));

            if (ObjectUtils.isNotNull(roleId)) {
                model.addAttribute("pagination", new Pagination());
                model.addAttribute("roleId", roleId);
            }

            request.setAttribute("userList", userList);
            request.setAttribute("currentPath", request.getRequestURI());
            request.setAttribute("userName", userName);
            request.setAttribute("userType", userType);
            request.setAttribute("pepairPeopleList",pepairPeopleList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysuser_list;
    }

    /**
     * 保修申请添加维修人员
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/repairGetSysuserList")
    public String getSystemUserList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            List<PepairPeople> sysUserList=pepairPeopleBiz.find(null," 1=1");

            //如果不为空
            if(ObjectUtils.isNotNull(sysUserList)){
                for(PepairPeople p:sysUserList){
                    SysUser sysUser=baseHessianBiz.getSysUserById(p.getUserId());
                    p.setUserName(sysUser.getUserName());
                    p.setMobile(sysUser.getMobile());
                }
            }

            request.setAttribute("sysUserList", sysUserList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repairGetSysuserList;
    }

    /**
     * 获取所有的维修人员
     *
     * @param request
     * @param pagination
     * @param pepairPeople
     * @return
     */
    @RequestMapping("/queryPeopairPeopleList")
    public String queryPeopairPeopleList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("pepairPeople") PepairPeople pepairPeople) {
        try {
            pagination.setPageSize(10);
            pagination.setRequest(request);
            String whereSql = GenerateSqlUtil.getSql(pepairPeople);
            List<PepairPeople> pepairPeopleList = pepairPeopleBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(pepairPeopleList)) {
                List<Map<String, String>> sysUserList = new ArrayList<>();
                for (PepairPeople aPepairPeopleList : pepairPeopleList) {
                    long userId = aPepairPeopleList.getUserId();
                    SysUser sysUser = baseHessianBiz.getSysUserById(userId);
                    String roleName = getRoleName(userId);
                    Map<String, String> maps = ObjectUtils.objToMap(sysUser);
                    maps.put("roleName", roleName);
                    sysUserList.add(maps);
                }
                request.setAttribute("sysUserList", sysUserList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pepairPeople_list;
    }

    private String getRoleName(long userId) {
        List<Long> longs = baseHessianBiz.queryUserRoleByUserId(userId);
        if (longs.contains(92L)) {
            return "工程主管";
        } else if (longs.contains(32L)) {
            return "维修人员";
        } else {
            return "--";
        }
    }

    /**
     * 更新表中数据，先删除后添加。
     *
     * @param request
     * @param functionIds
     * @return
     */
    @RequestMapping("/addPeopaorPeople")
    @ResponseBody
    public Map<String, Object> addPeopaorPeople(HttpServletRequest request, @RequestParam("functionsIds") String functionIds) {
        Map<String, Object> resultMap = null;
        try {
            pepairPeopleBiz.tx_addPeopaorPeople(functionIds);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * 逐个删除维修人员
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/deletePeopaorPeople")
    @ResponseBody
    public Map<String, Object> deletePeopaorPeople(HttpServletRequest request, @RequestParam("id") long id) {
        Map<String, Object> resultMap = null;
        try {
            String whereSql = "userId=" + id;
            List<PepairPeople> pepairPeoples = pepairPeopleBiz.find(new Pagination(), whereSql);
            if (ObjectUtils.isNotNull(pepairPeoples)) {
                for (int i = 0; i < pepairPeoples.size(); i++) {
                    pepairPeopleBiz.deleteById(pepairPeoples.get(i).getId());
                }
            }
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e1) {
            e1.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }
}
