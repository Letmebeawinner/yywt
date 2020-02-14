package com.houqin.controller.property;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.property.PropertyBiz;
import com.houqin.biz.property.PropertyBorrowBiz;
import com.houqin.biz.propertymessage.PropertyMessageBiz;
import com.houqin.entity.property.Property;
import com.houqin.entity.property.PropertyBorrow;
import com.houqin.entity.propertymessage.PropertyMessage;
import com.houqin.entity.sysuser.SysUser;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/houqin")
public class PropertyBorrowController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PropertyBorrowController.class);
    @Autowired
    private PropertyBorrowBiz propertyBorrowBiz;
    @Autowired
    private PropertyMessageBiz propertyMessageBiz;
    @Autowired
    private PropertyBiz propertyBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    @InitBinder("propertyBorrow")
    public void initPropertyMessage(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("propertyBorrow.");
    }

    @InitBinder
    protected void initPropertyBorrowBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 资产调拨
     *
     * @param request
     * @return
     */
    @RequestMapping("/propertyBorrow")
    public ModelAndView propertyBorrow(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/property/borrow_property");
        try {
            //获取用户id
            Long userId = SysUserUtils.getLoginSysUserId(request);
            request.setAttribute("userId", userId);

            Map<String, String> result = SysUserUtils.getLoginSysUser(request);
            //获取用户名
            String userName = result.get("userName");
            request.setAttribute("userName", userName);
        } catch (Exception e) {
            logger.error("PropertyBorrowController.propertyBorrow", e);
        }
        return modelAndView;
    }

    /**
     * 添加借用记录
     *
     * @param request
     * @param propertyBorrow
     * @return
     */
    @RequestMapping("/addPropertyBorrow")
    @ResponseBody
    public Map<String, Object> addPropertyBorrow(HttpServletRequest request, @ModelAttribute("propertyBorrow") PropertyBorrow propertyBorrow) {
        Map<String, Object> json = null;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMss");
            String codeNumber = sdf.format(new Date());

            propertyBorrow.setCodeNumber(codeNumber);
            propertyBorrow.setUserId(SysUserUtils.getLoginSysUserId(request));
            propertyBorrow.setStatus(1);
            propertyBorrowBiz.save(propertyBorrow);


            //修改资产状态
            PropertyMessage propertyMessage = new PropertyMessage();
            propertyMessage.setId(propertyBorrow.getPropertyId());
            propertyMessage.setStatus(2);
            propertyMessageBiz.update(propertyMessage);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", "");
        } catch (Exception e) {
            logger.error("PropertyBorrowController.addPropertyBorrow", e);
        }
        return json;
    }


    /**
     * 调拨记录列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/propertyBorrowList")
    public ModelAndView propertyBorrowList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("propertyBorrow") PropertyBorrow propertyBorrow) {
        ModelAndView modelAndView = new ModelAndView("/property/property_borrow_list");
        try {

            //资产类型
            List<Property> propertyList = propertyBiz.find(null, " 1=1");
            modelAndView.addObject("propertyList", propertyList);

            String whereSql = GenerateSqlUtil.getSql(propertyBorrow);
            List<PropertyBorrow> propertyBorrowList = propertyBorrowBiz.find(pagination, whereSql);

            if (ObjectUtils.isNotNull(propertyBorrowList)) {
                for (PropertyBorrow p : propertyBorrowList) {

                    Property property = propertyBiz.findById(p.getPropertyTypeId());
                    if (property != null) {
                        p.setTypeName(property.getTypeName());

                        PropertyMessage propertyMessage = propertyMessageBiz.findById(p.getPropertyId());
                        if (propertyMessage != null) {
                            p.setPropertyName(propertyMessage.getName());
                            p.setUnit(propertyMessage.getUnit());
                            p.setPrice(propertyMessage.getPrice());

                            SysUser sysUser = baseHessianBiz.getSysUserById(p.getUserId());
                            if (sysUser != null) {
                                p.setUserName(sysUser.getUserName());
                            }
                        }
                    }
                }
            }

            modelAndView.addObject("propertyBorrowList", propertyBorrowList);
        } catch (Exception e) {
            logger.error("PropertyBorrowController.propertyBorrowList", e);
        }
        return modelAndView;
    }


    /**
     * 取消划拨
     *
     * @param request
     * @return
     */
    @RequestMapping("/cancelBorrow")
    @ResponseBody
    public Map<String, Object> cancelBorrow(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {

            PropertyBorrow propertyBorrow = propertyBorrowBiz.findById(id);
            propertyBorrow.setStatus(2);
            propertyBorrow.setId(id);
            propertyBorrowBiz.update(propertyBorrow);


            PropertyMessage propertyMessage = propertyMessageBiz.findById(propertyBorrow.getPropertyId());
            propertyMessage.setStatus(0);
            propertyMessageBiz.update(propertyMessage);

            json = this.resultJson(ErrorCode.SUCCESS, "退还成功", "");

        } catch (Exception e) {
            logger.error("PropertyBorrowController.cancelBorrow", e);
        }
        return json;
    }
}
