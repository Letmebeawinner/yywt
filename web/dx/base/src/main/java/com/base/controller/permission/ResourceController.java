package com.base.controller.permission;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.base.biz.permission.ResourceBiz;
import com.base.entity.permission.Resource;
import com.base.enums.ResourceSite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 权限资源Controller
 *
 * @author s.li
 */
@Controller
@RequestMapping("/admin/base/permission")
public class ResourceController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceBiz resourceBiz;

    @InitBinder({"resource"})
    public void initResource(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("resource.");
    }

    /**
     * 根据条件查询资源权限列表
     *
     * @param request  HttpServletRequest
     * @param resource 权限资源条件
     * @return 列表页面
     */
    @RequestMapping("/queryAllResourceList")
    public ModelAndView queryAllResourceList(HttpServletRequest request, @ModelAttribute("resource") Resource resource) {
        ModelAndView mv = new ModelAndView("/permission/permission-list");
        try {
            List<Resource> resources = resourceBiz.queryAllResource();
            mv.addObject("resourceList", gson.toJson(resources));
            mv.addObject("siteArr", toArrEnum());
        } catch (Exception e) {
            logger.error("queryAllResourceList()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * @Description: 修改资源权限的所属父节点
     * @author: s.li
     * @Param: [request, id, parentId, parentResourceSite]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016/12/24
     */
    @RequestMapping("/updateResourceParent")
    @ResponseBody
    public Map<String, Object> updateResourceParent(HttpServletRequest request,
                                                    @RequestParam(value = "id") Long id,
                                                    @RequestParam(value = "parentId") Long parentId,
                                                    @RequestParam(value = "parentResourceSite") String parentResourceSite) {
        try {
            Resource resource = resourceBiz.findById(id);
            resource.setParentId(parentId);
            resource.setResourceSite(parentResourceSite);
            List<Resource> resourceList = resourceBiz.tx_updateResource(resource);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, resourceList);
        } catch (Exception e) {
            logger.error("updateResourceParent()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 添加或更新权限资源
     *
     * @param resource 权限资源数据对象
     * @return 与权限资源信息有关的json数据
     */
    @RequestMapping("/addOrUpdateResource")
    @ResponseBody
    public Map<String, Object> addOrUpdateResource(@ModelAttribute("resource") Resource resource) {
        try {
            String message = verify(resource);
            if (message != null) {
                return resultJson(ErrorCode.ERROR_PARAMETER, message, resource);
            }
            if (ObjectUtils.isNull(resource.getId())) {
                resource.setParentId(0L);
                resourceBiz.save(resource);
                redisCache.regiontRemove(BaseCommonConstants.ALL_AUTHORITY_KEY);
            } else {
                resourceBiz.tx_updateResource(resource);
            }
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, resource);
        } catch (Exception e) {
            logger.error("addOrUpdateResource()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 根据id删除权限资源
     *
     * @param id 权限资源的id
     * @return 删除结果
     */
    @RequestMapping("/deleteResource")
    @ResponseBody
    public Map<String, Object> deleteResource(@RequestParam("resourceId") Long id) {
        try {
            if (ObjectUtils.isNotNull(id)) {
                Resource resource = new Resource();
                resource.setParentId(id);
                List<Resource> resources = resourceBiz.find(null, where(resource));
                List<Long> resourceIds = new LinkedList<>();
                resourceIds.add(id);
                resources.forEach(r -> resourceIds.add(r.getId()));
                resourceBiz.deleteByIds(resourceIds);
                redisCache.regiontRemove(BaseCommonConstants.ALL_AUTHORITY_KEY);
                return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            }
            return resultJson(ErrorCode.ERROR_PARAMETER_NULL, "请选择要删除的权限资源节点", null);
        } catch (Exception e) {
            logger.error("deleteResource()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 根据id查找权限资源
     *
     * @param id 权限资源的id
     * @return 指定id权限资源的json数据
     * @since 2016-12-13
     */
    @RequestMapping("/getResource")
    @ResponseBody
    public Map<String, Object> getResource(@RequestParam("resourceId") Long id) {
        Resource resource = resourceBiz.findById(id);
        if (resource == null) {
            return resultJson(ErrorCode.ERROR_DATA_NULL, "查询失败，请稍候再试", null);
        }
        return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, resource);
    }

    /**
     * 验证指定的resource是否存在不合法的字段值
     *
     * @param resource 验证的resource
     * @return {@code null}所有字段值均合法,否则返回不合法字段值信息
     * @since 2016-12-19
     */
    private String verify(Resource resource) {
        if (StringUtils.isTrimEmpty(resource.getResourceName())) {
            return "权限资源名称不能为空";
        }
        if (resource.getResourceType() == null || resource.getResourceType() == 0) {
            return "权限资源类型不能为空";
        }
        if (StringUtils.isTrimEmpty(resource.getResourceSite()) || "0".equals(resource.getResourceSite())) {
            return "权限资源所属系统不能为空";
        }
        if (StringUtils.isTrimEmpty(resource.getResourceDesc())) {
            return "权限资源描述不能为空";
        }
        return null;
    }

    /**
     * 拼装权限资源的where子句
     *
     * @param resource 封装where条件的resource
     * @return where子句
     */
    private String where(Resource resource) {
        String where = " 1=1";
        if (resource.getId() != null && resource.getId() > 0) {
            where += " and id=" + resource.getId();
        }
        if (resource.getParentId() != null && resource.getParentId() > 0) {
            where += " and parentId=" + resource.getParentId();
        }
        if (resource.getResourceType() != null && resource.getResourceType() > 0) {
            where += " and resourceType=" + resource.getResourceType();
        }
        if (notEmpty(resource.getResourceDesc())) {
            where += " and resourceDesc like %" + resource.getResourceDesc() + "%";
        }
        if (notEmpty(resource.getResourceName())) {
            where += " and resourceName like %" + resource.getResourceName() + "%";
        }
        if (notEmpty(resource.getResourcePath())) {
            where += " and resourcePath like %" + resource.getResourcePath() + "%";
        }
        if (notEmpty(resource.getStyleName())) {
            where += " and sytleName like %" + resource.getStyleName() + "%";
        }
        return where;
    }

    /**
     * 判断指定的文本是否为空
     *
     * @param text 文本
     * @return {@code true}不为空
     */
    private boolean notEmpty(String text) {
        return !StringUtils.isTrimEmpty(text);
    }

    /**
     * 把泛型转成数组
     *
     * @return ResourceSite[]
     */
    private ResourceSite[] toArrEnum() {
        return ResourceSite.values();
    }
}
