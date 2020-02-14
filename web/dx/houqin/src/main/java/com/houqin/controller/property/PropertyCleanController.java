package com.houqin.controller.property;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.property.PropertyBiz;
import com.houqin.biz.property.PropertyCleanBiz;
import com.houqin.biz.propertymessage.PropertyMessageBiz;
import com.houqin.entity.property.Property;
import com.houqin.entity.property.PropertyClean;
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
public class PropertyCleanController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PropertyCleanController.class);
    @Autowired
    private PropertyCleanBiz propertyCleanBiz;
    @Autowired
    private PropertyMessageBiz propertyMessageBiz;
    @Autowired
    private PropertyBiz propertyBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    @InitBinder("propertyClean")
    public void initPropertyClean(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("propertyClean.");
    }

    @InitBinder
    protected void initPropertyCleanBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 资产报废
     *
     * @param request
     * @return
     */
    @RequestMapping("/propertyClean")
    public ModelAndView propertyClean(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/property/clean_property");
        try {
            Map<String, String> result = SysUserUtils.getLoginSysUser(request);
            //获取用户名
            String userName = result.get("userName");
            request.setAttribute("userName", userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     * 添加报废记录
     *
     * @param request
     * @param propertyClean
     * @return
     */
    @RequestMapping("/addPropertyClean")
    @ResponseBody
    public Map<String, Object> addPropertyClean(HttpServletRequest request, @ModelAttribute("propertyClean") PropertyClean propertyClean) {
        Map<String, Object> json = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String codeNumber = sdf.format(new Date());
            codeNumber = "BF" + codeNumber;

            propertyClean.setUserId(SysUserUtils.getLoginSysUserId(request));
            propertyClean.setCodeNumber(codeNumber);
            propertyClean.setCleanTime(new Date());
            propertyClean.setStatus(1);
            propertyCleanBiz.save(propertyClean);

            //修改资产状态
            PropertyMessage propertyMessage = new PropertyMessage();
            propertyMessage.setId(propertyClean.getPropertyId());
            propertyMessage.setStatus(4);
            propertyMessageBiz.update(propertyMessage);
            json = this.resultJson(ErrorCode.SUCCESS, "报废成功", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 报废记录列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/propertyCleanList")
    public ModelAndView propertyCleanList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("propertyClean") PropertyClean propertyClean) {
        ModelAndView modelAndView = new ModelAndView("/property/property_clean_list");
        try {

            //资产类型
            List<Property> propertyList = propertyBiz.find(null, " 1=1");
            modelAndView.addObject("propertyList", propertyList);

            String whereSql = GenerateSqlUtil.getSql(propertyClean);
            List<PropertyClean> propertyCleanList = propertyCleanBiz.find(pagination, whereSql);

            if (ObjectUtils.isNotNull(propertyCleanList)) {
                for (PropertyClean p : propertyCleanList) {
                    PropertyMessage propertyMessage = propertyMessageBiz.findById(p.getPropertyId());
                    if (propertyMessage != null) {
                        p.setPropertyName(propertyMessage.getName());

                        Property property = propertyBiz.findById(p.getPropertyTypeId());
                        if (property != null) {
                            p.setTypeName(property.getTypeName());

                            SysUser sysUser = baseHessianBiz.getSysUserById(p.getUserId());
                            if (ObjectUtils.isNotNull(sysUser)) {
                                p.setUserName(sysUser.getUserName());
                            }
                        }
                    }
                }
            }


            modelAndView.addObject("propertyCleanList", propertyCleanList);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     * 还原
     *
     * @param request
     * @return
     */
    @RequestMapping("/cancelClean")
    @ResponseBody
    public Map<String, Object> cancelClean(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {

            PropertyClean propertyClean = propertyCleanBiz.findById(id);
            propertyClean.setStatus(0);
            propertyClean.setId(id);
            propertyCleanBiz.update(propertyClean);


            PropertyMessage propertyMessage = propertyMessageBiz.findById(propertyClean.getPropertyId());
            propertyMessage.setStatus(0);
            propertyMessageBiz.update(propertyMessage);

            json = this.resultJson(ErrorCode.SUCCESS, "还原成功", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
