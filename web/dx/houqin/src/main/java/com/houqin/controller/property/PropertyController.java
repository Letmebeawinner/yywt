package com.houqin.controller.property;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.property.PropertyBiz;
import com.houqin.biz.propertymessage.PropertyMessageBiz;
import com.houqin.entity.property.Property;
import com.houqin.entity.propertymessage.PropertyMessage;
import com.houqin.entity.propertymessage.PropertyMessageDto;
import com.houqin.utils.GenerateSqlUtil;
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
 * 资产分类管理
 * Created by Administrator on 2016/12/16.
 */
@Controller
@RequestMapping("/admin/houqin")
public class PropertyController extends BaseController {
    //资产分类添加
    private static final String createProperty = "/property/add-property";
    //修改资产分类
    private static final String toUpdateProperty = "/property/update-property";
    //资产分类列表
    private static final String propertyList = "/property/property_list";
    //资产调拨
    private static final String allotProperty = "/property/allot_property";
    private static final String selectProperty = "/property/select_property_list";
    private static Logger logger = LoggerFactory.getLogger(PropertyController.class);
    @Autowired
    private PropertyMessageBiz propertyMessageBiz;
    @Autowired
    private PropertyBiz propertyBiz;

    @InitBinder("property")
    public void initProperty(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("property.");
    }

    /**
     * 查询资产分类管理列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/queryAllProperty")
    public String queryAllProperty(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("property") Property property) {
        try {
            String whereSql = GenerateSqlUtil.getSql(property);
            whereSql = whereSql + " order by sort desc";
            pagination.setRequest(request);
            List<Property> propertyList = propertyBiz.find(pagination, whereSql);
            request.setAttribute("propertyList", propertyList);
            request.setAttribute("property", property);
        } catch (Exception e) {
            logger.info("PropertyController--queryAllProperty", e);
            return this.setErrorPath(request, e);
        }
        return propertyList;
    }


    /**
     * 去添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddProperty")
    public String toAddProperty(HttpServletRequest request) {
        try {
        } catch (Exception e) {
            logger.info("PropertyController--toAddProperty", e);
            return this.setErrorPath(request, e);
        }
        return createProperty;
    }

    /**
     * 添加资产分类管理类型
     *
     * @param request
     * @param property
     * @return
     */
    @RequestMapping("/addSaveProperty")
    @ResponseBody
    public Map<String, Object> addSaveProperty(HttpServletRequest request, @ModelAttribute("property") Property property) {
        try {
            propertyBiz.save(property);
        } catch (Exception e) {
            logger.info("PropertyController--addSaveProperty", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 去修改页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateProperty")
    public String toUpdateProperty(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            Property property = propertyBiz.findById(id);
            request.setAttribute("property", property);
        } catch (Exception e) {
            logger.info("PropertyController--toUpdateProperty", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateProperty;
    }

    /**
     * 修改资产分类管理类型
     *
     * @param request
     * @param property
     * @return
     */
    @RequestMapping("/updateProperty")
    @ResponseBody
    public Map<String, Object> updateProperty(HttpServletRequest request, @ModelAttribute("property") Property property) {
        Map<String, Object> resultMap = null;
        try {
            propertyBiz.update(property);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("PropertyController--updateProperty", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * 根据id删除
     *
     * @param request
     * @return
     */
    @RequestMapping("/delProperty")
    @ResponseBody
    public Map<String, Object> delProperty(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            propertyBiz.deleteById(Long.parseLong(id));
        } catch (Exception e) {
            logger.info("PropertyController--delProperty", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


    /**
     * 资产调拨
     *
     * @param request
     * @return
     */
    @RequestMapping("/propertyallot")
    public String proPertyAllot(HttpServletRequest request) {
        try {
            Map<String, String> result = SysUserUtils.getLoginSysUser(request);
            //获取用户名
            String userName = result.get("userName");
            request.setAttribute("userName", userName);

            //获取用户id
            Long userId = SysUserUtils.getLoginSysUserId(request);
            request.setAttribute("userId", userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allotProperty;
    }

    /**
     * 保修申请添加维修人员
     */
    @RequestMapping("/selectPropertyList")
    public String getSystemUserList(HttpServletRequest request, @ModelAttribute("propertyMessage") PropertyMessage propertyMessage) {
        try {
            propertyMessage.setStatus(0);
            String whereSql = GenerateSqlUtil.getSql(propertyMessage);
            List<PropertyMessageDto> propertyMessageDtoList = propertyMessageBiz.getAllPropertyMessage(null, whereSql);
            request.setAttribute("propertyMessageDtoList", propertyMessageDtoList);

            List<Property> propertyList = propertyBiz.find(null,"1=1");
            request.setAttribute("propertyList", propertyList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectProperty;
    }

    /**
     * 查询资产名称
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/searchProperty")
    @ResponseBody
    public Map<String, Object> searchProperty(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            PropertyMessage propertyMessage = propertyMessageBiz.findById(id);
            json = this.resultJson(ErrorCode.SUCCESS, "", propertyMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
