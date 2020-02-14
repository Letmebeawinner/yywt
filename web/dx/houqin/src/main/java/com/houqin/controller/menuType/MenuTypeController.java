package com.houqin.controller.menuType;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.houqin.biz.menuType.MenuTypeBiz;
import com.houqin.entity.menuType.MenuType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 菜单类型管理
 *
 * @author ccl
 * @create 2016-12-14-13:08
 */
@Controller
@RequestMapping("/admin/houqin")
public class MenuTypeController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MenuTypeController.class);

    private static final String createMenuType = "/menuType/add-menuType";
    private static final String toUpdateMenuType = "/menuType/update-menuType";
    private static final String menuTypeList = "/menuType/menuType_list";

    @Autowired
    private MenuTypeBiz menuTypeBiz;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"menuType"})
    public void initMenuType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("menuType.");
    }


    /**
     * 去添加菜单类型
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddMenuType")
    public String toAddMenuType(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return createMenuType;
    }

    /**
     * @Description:去添加菜单类型
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/queryAllMenuType")
    public String queryAllMenuType(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = "1=1";
            String id = request.getParameter("typeId");
            if (!StringUtils.isTrimEmpty(id) && Integer.parseInt(id) > 0) {
                whereSql += " and id=" + id;
            }
            String name = request.getParameter("typeName");
            if (!StringUtils.isTrimEmpty(name)) {
                whereSql += " and name like '%" + name.trim() + "%'";
            }
            whereSql = whereSql + " order by sort desc";
            pagination.setRequest(request);
            List<MenuType> menuTypeList = menuTypeBiz.find(pagination, whereSql);
            request.setAttribute("menuTypeList", menuTypeList);
            request.setAttribute("name", name);
            request.setAttribute("id", id);
        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return menuTypeList;
    }

    /**
     * @Description:添加菜单类型
     * @author: ccl
     * @Param: [request, menuType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-14
     */
    @RequestMapping("/addSaveMenuType")
    @ResponseBody
    public Map<String, Object> addSaveMenuType(HttpServletRequest request, @ModelAttribute("menuType") MenuType menuType) {

        try {
            menuTypeBiz.save(menuType);
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * @Description:
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/toUpdateMenuType/{id}")
    public String toUpdateMenuType(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            MenuType menuType = menuTypeBiz.findById(id);
            request.setAttribute("menuType", menuType);
        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateMenuType;
    }


    /**
     * @Description:去修改菜单类型
     * @author: ccl
     * @Param: [request, menuType]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/updateMenuType")
    public String updateMenuType(HttpServletRequest request, @ModelAttribute("menuType") MenuType menuType) {
        try {
            menuTypeBiz.update(menuType);
        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return "redirect:/admin/houqin/queryAllMenuType.json";
    }


    /**
     * @Description:删除菜单类型
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-14
     */
    @RequestMapping("/delMenuType")
    @ResponseBody
    public Map<String, Object> delMenuType(HttpServletRequest request) {
        try {
            String id=request.getParameter("id");
            menuTypeBiz.deleteById(Long.parseLong(id));
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


}
