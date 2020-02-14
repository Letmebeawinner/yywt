package com.houqin.controller.property;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.property.PropertyBiz;
import com.houqin.biz.property.PropertyReceiveBiz;
import com.houqin.biz.propertymessage.PropertyMessageBiz;
import com.houqin.entity.property.Property;
import com.houqin.entity.property.PropertyReceive;
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
public class PropertyReceiveController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PropertyReceiveController.class);
    @Autowired
    private PropertyReceiveBiz propertyReceiveBiz;
    @Autowired
    private PropertyMessageBiz propertyMessageBiz;
    @Autowired
    private PropertyBiz propertyBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    @InitBinder("propertyReceive")
    public void initPropertyMessage(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("propertyReceive.");
    }

    @InitBinder
    protected void initPropertyReceiveBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 资产领用
     *
     * @param request
     * @return
     */
    @RequestMapping("/propertyReceive")
    public ModelAndView propertyReceive(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/property/receive_property");
        try {
            //获取用户id
            Long userId = SysUserUtils.getLoginSysUserId(request);

            Map<String, String> result = SysUserUtils.getLoginSysUser(request);
            //获取用户名
            String userName = result.get("userName");
            request.setAttribute("userName", userName);
            request.setAttribute("userId", userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 添加借用记录
     *
     * @param request
     * @param propertyReceive
     * @return
     */
    @RequestMapping("/addPropertyReceive")
    @ResponseBody
    public Map<String, Object> addPropertyReceive(HttpServletRequest request, @ModelAttribute("propertyReceive") PropertyReceive propertyReceive) {
        Map<String, Object> json = null;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMss");
            String codeNumber = sdf.format(new Date());

            propertyReceive.setCodeNumber(codeNumber);
            propertyReceive.setStatus(1);
            propertyReceive.setUserId(SysUserUtils.getLoginSysUserId(request));
            propertyReceiveBiz.save(propertyReceive);


            //修改资产状态
            PropertyMessage propertyMessage = new PropertyMessage();
            propertyMessage.setId(propertyReceive.getPropertyId());
            propertyMessage.setStatus(3);
            propertyMessageBiz.update(propertyMessage);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 领用记录列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/propertyReceiveList")
    public ModelAndView propertyReceiveList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("propertyReceive") PropertyReceive propertyReceive) {
        ModelAndView modelAndView = new ModelAndView("/property/property_receive_list");
        try {

            //资产类型
            List<Property> propertyList = propertyBiz.find(null, " 1=1");
            modelAndView.addObject("propertyList", propertyList);

            String whereSql = GenerateSqlUtil.getSql(propertyReceive);
            List<PropertyReceive> propertyReceiveList = propertyReceiveBiz.find(pagination, whereSql);

            if (ObjectUtils.isNotNull(propertyReceiveList)) {
                for (PropertyReceive p : propertyReceiveList) {

                    Property property = propertyBiz.findById(p.getPropertyTypeId());
                    if (property != null) {
                        p.setTypeName(property.getTypeName());

                        PropertyMessage propertyMessage = propertyMessageBiz.findById(p.getPropertyId());
                        if (propertyMessage != null) {
                            p.setPropertyName(propertyMessage.getName());
                            p.setPrice(propertyMessage.getPrice());
                            p.setUnit(propertyMessage.getUnit());

                            SysUser sysUser = baseHessianBiz.getSysUserById(p.getUserId());
                            if (ObjectUtils.isNotNull(sysUser)) {
                                p.setUserName(sysUser.getUserName());
                            }
                        }

                    }
                }
            }
            modelAndView.addObject("propertyReceiveList", propertyReceiveList);
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
    @RequestMapping("/cancelReceive")
    @ResponseBody
    public Map<String, Object> cancelReceive(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {

            //划拨
            PropertyReceive propertyReceive = propertyReceiveBiz.findById(id);
            propertyReceive.setStatus(2);
            propertyReceive.setId(id);
            propertyReceiveBiz.update(propertyReceive);


            PropertyMessage propertyMessage = propertyMessageBiz.findById(propertyReceive.getPropertyId());
            propertyMessage.setStatus(0);
            propertyMessageBiz.update(propertyMessage);
            json = this.resultJson(ErrorCode.SUCCESS, "退库成功", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
