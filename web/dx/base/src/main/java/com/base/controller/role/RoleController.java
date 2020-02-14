package com.base.controller.role;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.enums.StateEnum;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.base.biz.connection.RoleResourceBiz;
import com.base.biz.permission.ResourceBiz;
import com.base.biz.role.RoleBiz;
import com.base.biz.role.SysUserRoleBiz;
import com.base.biz.user.SysUserBiz;
import com.base.entity.connection.RoleResource;
import com.base.entity.permission.Resource;
import com.base.entity.role.Role;
import com.base.entity.role.SysUserRole;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhushaokang@268xue.com
 * @since 2016-12-15
 */
@Controller
@RequestMapping("/admin/base/role")
public class RoleController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleBiz roleBiz;
    @Autowired
    private ResourceBiz resourceBiz;
    @Autowired
    private RoleResourceBiz roleResourceBiz;
    @Autowired
    private SysUserRoleBiz sysUserRoleBiz;
    @Autowired
    private SysUserBiz sysUserBiz;

    @InitBinder({"role"})
    public void initBinder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("role.");
    }

    /**
     * 添加或者更新角色信息
     *
     * @param role        角色信息
     * @param isAvailable 是否是禁用角色操作 1 不是 2是
     * @return {@link ErrorCode#SUCCESS} 成功
     */
    @RequestMapping("/addOrUpdateRole")
    @ResponseBody
    public Map<String, Object> addOrUpdateRole(@ModelAttribute("role") Role role,
                                               @RequestParam(value = "isAvailable", required = false) String isAvailable) {
        try {
            if (!"2".equals(isAvailable)) {
                if (verify(role) != null) {
                    return resultJson(ErrorCode.ERROR_PARAMETER, verify(role), null);
                }
            }
            if (ObjectUtils.isNotNull(role.getId())) {
                if ("2".equals(isAvailable)) {
                    role = roleBiz.findById(role.getId());
                    role.setStatus((role.getStatus() + 1) % 2);
                }
                roleBiz.update(role);
            } else {
                roleBiz.save(role);
            }
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "/admin/base/role/queryAllRoleList.json");
        } catch (Exception e) {
            logger.error("addOrUpdateRole()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 删除指定id的角色
     *
     * @param ids 角色的id字符串，使用","分割。如1, 2, 3
     * @return 是否成功删除相关角色。成功返回{@link ErrorCode#SUCCESS}
     */
    @RequestMapping("/deleteRoles")
    @ResponseBody
    public Map<String, Object> deleteRoles(@RequestParam("ids") String ids) {
        try {
            if (!StringUtils.isTrimEmpty(ids)) {
                String where = " id in (" + ids + ")";
                List<Role> roles = roleBiz.find(null, where);
                roles.forEach(role -> role.setStatus(StateEnum.DELETE.getState()));
                roleBiz.updateBatch(roles);
                return resultJson(ErrorCode.SUCCESS, null, null);
            }
            return resultJson(ErrorCode.ERROR_PARAMETER, "请选择要删除的角色", null);
        } catch (Exception e) {
            logger.error("deleteRoles()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 查询指定id的角色信息. 如果id为0表示添加角色
     *
     * @param id 角色的id
     * @return 添加或更新角色页面
     */
    @RequestMapping("/getRole")
    public ModelAndView getRole(HttpServletRequest request, @RequestParam(value = "roleId", required = false) Long id) {
        ModelAndView mv = new ModelAndView("/role/add-role");
        try {
            if (ObjectUtils.isNotNull(id)) {
                Role role = roleBiz.findById(id);
                if (role.getStatus() == StateEnum.NOT_AVAILABLE.getState()) {
                    role = null;
                }
                mv.addObject("role", role);
            }
            return mv;
        } catch (Exception e) {
            logger.error("getRole()--error", e);
            mv.setViewName(setErrorPath(request, e));
            return mv;
        }
    }

    /**
     * 查询指定角色拥有的权限<br>
     *
     * @param roleId 角色的id
     * @return 指定角色拥有的权限
     */
    @RequestMapping("/getRoleResources")
    @ResponseBody
    public Map<String, Object> getRoleResource(@RequestParam("roleId") Long roleId) {
        try {
            String where = " roleId=" + roleId;
            List<RoleResource> roleResources = roleResourceBiz.find(null, where);
            where = " 1=1 order by resourceOrder desc, resourceSite, resourceType";
            List<Resource> resources = resourceBiz.find(null, where);
            if (!(CollectionUtils.isEmpty(roleResources) || CollectionUtils.isEmpty(resources))) {
                for (RoleResource roleResource : roleResources) {
                    for (Resource resource : resources) {
                        if (roleResource.getResourceId().equals(resource.getId())) {
                            resource.setRoleHas(StateEnum.AVAILABLE.getState());
                        }
                    }
                }
            }
            return resultJson(ErrorCode.SUCCESS, null, resources);
        } catch (Exception e) {
            logger.error("getRoleResource()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 查询所有角色
     *
     * @param request    {@link javax.servlet.http.HttpServletRequest}
     * @param role       查询角色的条件
     * @param pagination 分页
     * @return 角色列表页面
     */
    @RequestMapping("/queryAllRoleList")
    public ModelAndView queryAllRoleList(HttpServletRequest request,
                                         @ModelAttribute("role") Role role,
                                         @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/role/role-list");
        try {
            pagination.setRequest(request);
            List<Role> roles = roleBiz.find(pagination, where(role));
            mv.addObject("roles", roles);
        } catch (Exception e) {
            logger.error("queryAllRolesList()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 更新指定角色的权限
     *
     * @param roleId      角色的id
     * @param resourceIds 角色拥有的权限。权限id使用","分割，如1, 2, 3
     * @return 更新成功返回 {@link ErrorCode#SUCCESS}
     */
    @RequestMapping("/updateRoleResources")
    @ResponseBody
    public Map<String, Object> updateRoleResources(@RequestParam("roleId") Long roleId,
                                                   @RequestParam(value = "resourceIds", required = false) String resourceIds) {
        try {
            roleResourceBiz.tx_updateRoleResources(roleId, resourceIds);
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("updateRoleResource()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }

    }

    /**
     * 验证指定的role是否存在不合法的字段值
     *
     * @param role 验证的role
     * @return {@code null}所有字段值均合法,否则返回不合法字段值信息
     * @since 2016-12-19
     */
    private String verify(Role role) {
        if (StringUtils.isTrimEmpty(role.getRoleName())) {
            return "角色名不能为空";
        }
        if (role.getStatus() < 0 || role.getStatus() > 1) {
            return "角色状态不能为空";
        }
        if (StringUtils.isTrimEmpty(role.getRoleDesc())) {
            return "角色描述不能为空";
        }
        return null;
    }

    /**
     * 拼装权限资源的where子句
     *
     * @param role 封装where条件的role
     * @return where子句
     */
    private String where(Role role) {
        String where = " 1=1";
        if (role.getId() != null && role.getId() != 0) {
            where += " and id=" + role.getId();
        }
        if (!StringUtils.isTrimEmpty(role.getRoleDesc())) {
            where += " and (roleName like '%" + role.getRoleDesc() + "%' or roleDesc like '%" + role.getRoleDesc() + "%')";
        }
        if (role.getStatus() != null && role.getStatus() >= 0) {
            where += " and status=" + role.getStatus();
        } else {
            where += " and status<>2";
        }
        return where;
    }


    /**
     * 查询系统用户角色
     *
     * @param sysUserId
     * @return
     */
    @RequestMapping("/getRoleTree")
    @ResponseBody
    public Map<String, Object> getRoleTree(@RequestParam("sysUserId") Long sysUserId) {
        try {
            String where = " userId=" + sysUserId;
            List<SysUserRole> sysUserRoleList = sysUserRoleBiz.find(null, where);
            String whereSql = " 1=1 and status<>2";
            List<Role> roles = roleBiz.find(null, whereSql);
            if (!(CollectionUtils.isEmpty(sysUserRoleList) || CollectionUtils.isEmpty(sysUserRoleList))) {
                for (SysUserRole sysUserRole : sysUserRoleList) {
                    for (Role role : roles) {
                        if (sysUserRole.getRoleId().equals(role.getId())) {
                            role.setIsHave(StateEnum.AVAILABLE.getState());
                        }
                    }
                }
            }
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, roles);
        } catch (Exception e) {
            logger.error("getDepartmentTree()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 更新用户角色信息
     *
     * @param userId        用户id
     * @param roleIds 部门id  字符串
     * @return json数据
     */
    @RequestMapping("/updateUserRole")
    @ResponseBody
    public Map<String, Object> updateUserDepartment(@RequestParam("userId") Long userId,
                                                    @RequestParam("roleIds") String roleIds) {
        try {
            sysUserBiz.tx_updateSysUserRole(userId, roleIds);
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("addUserDepartment()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }
}
