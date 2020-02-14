package com.houqin.controller.property;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.property.PropertyAllotBiz;
import com.houqin.biz.property.PropertyBiz;
import com.houqin.biz.propertymessage.PropertyMessageBiz;
import com.houqin.entity.property.Property;
import com.houqin.entity.property.PropertyAllot;
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
public class PropertyAllotController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PropertyAllotController.class);
    @Autowired
    private PropertyAllotBiz propertyAllotBiz;
    @Autowired
    private PropertyMessageBiz propertyMessageBiz;
    @Autowired
    private PropertyBiz propertyBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    @InitBinder("propertyAllot")
    public void initPropertyMessage(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("propertyAllot.");
    }

    @InitBinder
    protected void initPropertyAllotBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 添加调拨记录
     *
     * @param request
     * @param propertyAllot
     * @return
     */
    @RequestMapping("/addPropertyAllot")
    @ResponseBody
    public Map<String, Object> addPropertyAllot(HttpServletRequest request, @ModelAttribute("propertyAllot") PropertyAllot propertyAllot) {
        Map<String, Object> json = null;
        try {
            propertyAllot.setHandleId(SysUserUtils.getLoginSysUserId(request));
            propertyAllot.setStatus(1);
            propertyAllotBiz.save(propertyAllot);


            //修改资产状态
            PropertyMessage propertyMessage = new PropertyMessage();
            propertyMessage.setId(propertyAllot.getPropertyId());
            propertyMessage.setStatus(1);
            propertyMessageBiz.update(propertyMessage);
            json = this.resultJson(ErrorCode.SUCCESS, "调拨成功", "");
        } catch (Exception e) {
            e.printStackTrace();
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
    @RequestMapping("/propertyAllotList")
    public ModelAndView propertyAllotList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("propertyAllot") PropertyAllot propertyAllot) {
        ModelAndView modelAndView = new ModelAndView("/property/property_allot_list");
        try {

            //资产类型
            List<Property> propertyList = propertyBiz.find(null, " 1=1");
            modelAndView.addObject("propertyList", propertyList);

            String whereSql = GenerateSqlUtil.getSql(propertyAllot);
            List<PropertyAllot> propertyAllotList = propertyAllotBiz.find(pagination, whereSql);

            if (ObjectUtils.isNotNull(propertyAllotList)) {
                for (PropertyAllot p : propertyAllotList) {
                    PropertyMessage propertyMessage = propertyMessageBiz.findById(p.getPropertyId());
                    if (propertyMessage != null) {
                        p.setPropertyName(propertyMessage.getName());

                        Property property = propertyBiz.findById(p.getPropertyTypeId());
                        if (ObjectUtils.isNotNull(property)) {
                            p.setTypeName(property.getTypeName());

                            SysUser sysUser = baseHessianBiz.getSysUserById(p.getHandleId());
                            if (ObjectUtils.isNotNull(sysUser)) {
                                p.setUserName(sysUser.getUserName());
                            }
                        }
                    }
                }
            }


            modelAndView.addObject("propertyAllotList", propertyAllotList);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     * 取消划拨
     *
     * @param request
     * @return
     */
    @RequestMapping("/cancelAllot")
    @ResponseBody
    public Map<String, Object> cancelAllot(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {

            PropertyAllot propertyAllot = propertyAllotBiz.findById(id);
            propertyAllot.setStatus(2);
            propertyAllot.setId(id);
            propertyAllotBiz.update(propertyAllot);


            PropertyMessage propertyMessage = propertyMessageBiz.findById(propertyAllot.getPropertyId());
            propertyMessage.setStatus(0);
            propertyMessageBiz.update(propertyMessage);

            json = this.resultJson(ErrorCode.SUCCESS, "取消成功", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
