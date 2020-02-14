package com.houqin.controller.menus;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.houqin.biz.menuType.MenuTypeBiz;
import com.houqin.biz.menus.MenusBiz;
import com.houqin.entity.menuType.MenuType;
import com.houqin.entity.menus.Menus;
import com.houqin.entity.menus.MenusDto;
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
 * 菜单管理
 *
 * @author ccl
 * @create 2016-12-14-15:28
 */
@Controller
@RequestMapping("/admin/houqin")
public class MenusController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MenusController.class);

    private static final String createMenus = "/menus/add-menus";
    private static final String toUpdateMenu = "/menus/update-menus";
    private static final String menuList = "/menus/menus_list";

    @Autowired
    private MenusBiz menusBiz;

    @Autowired
    private MenuTypeBiz mensTypeBiz;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"menus"})
    public void initMenus(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("menus.");
    }

    /**
     * @Description:查询菜单
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/queryAllMenus")
    public String queryAllMenus(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            List<MenuType> menuTypeList=mensTypeBiz.getAllMenuType();
            request.setAttribute("menuTypeList",menuTypeList);

            String whereSql = "1=1";

            String id = request.getParameter("typeId");
            if (!StringUtils.isTrimEmpty(id) && Integer.parseInt(id) > 0) {
                whereSql += " and typeId=" + id;
            }

            pagination.setRequest(request);
            List<MenusDto> menusList = menusBiz.getMenusType(pagination, whereSql);
            request.setAttribute("menusList", menusList);
            request.setAttribute("typId",id);
        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return menuList;
    }

    /**
     * @Description:添加菜单
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/toAddMenus")
    public String toAddMenus(HttpServletRequest request) {
        try {
            List<MenuType> menuList = mensTypeBiz.getAllMenuType();
            request.setAttribute("menuList", menuList);
        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return createMenus;
    }

    /**
     * @Description:添加
     * @author: ccl
     * @Param: [request, menus]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-14
     */
    @RequestMapping("/addSaveMenus")
    @ResponseBody
    public Map<String, Object> addSaveMenus(HttpServletRequest request, @ModelAttribute("menus") Menus menus) {
        try {
            menusBiz.save(menus);
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * @Description:去修改菜单
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-15
     */
    @RequestMapping("/toUpdateMenus/{id}")
    public String toUpdateMenus(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            List<MenuType> menuList = mensTypeBiz.getAllMenuType();
            request.setAttribute("menuList", menuList);

            Menus menus = menusBiz.findById(id);
            request.setAttribute("menus", menus);
        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateMenu;
    }

    /**
     * @Description:修改菜单
     * @author: ccl
     * @Param: [request, menus]
     * @Return: java.lang.String
     * @Date: 2016-12-15
     */
    @RequestMapping("/updateMenus")
    public String updateMenus(HttpServletRequest request, @ModelAttribute("menus") Menus menus) {
        try {
            menusBiz.update(menus);
        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return "redirect:/admin/houqin/queryAllMenus.json";
    }

    /**
     * @Description:删除
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-15
     */
    @RequestMapping("/deleteMenus")
    @ResponseBody
    public Map<String, Object> deleteMenus(HttpServletRequest request) {

        try {
            String ids = request.getParameter("id");
            menusBiz.deleteById(Long.parseLong(ids));
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


}
