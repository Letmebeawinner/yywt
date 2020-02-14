package com.oa.controller.telephone;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.google.common.collect.Lists;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.telephone.TelephoneBiz;
import com.oa.entity.sysuser.SysUser;
import com.oa.entity.telephone.Telephone;
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
 * 通讯录接口
 *
 * @author ccl
 * @create 2016-12-28-18:03
 */
@Controller
@RequestMapping("/app/oa/telephone")
public class AppTelephoneController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AppTelephoneController.class);

    @Autowired
    private TelephoneBiz telephoneBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    @InitBinder({"telephone"})
    public void initTelephones(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("telephone.");
    }

    @RequestMapping("/getAppTelephone")
    @ResponseBody
    public Map<String, Object> getAppTelephone( @RequestParam(value = "userId", required = true) Long userId) {
        Map<String, Object> resultMap = null;
        try {
            SysUser user=baseHessianBiz.getSysUserById(userId);
            List<Telephone> telephoneList= Lists.newArrayList();
            if(user!=null && user.getUserType()==2){
                List<Map<String, String>> userList=baseHessianBiz.queryAppTelephoneList();
                userList.forEach(e-> telephoneList.add(new Telephone(e)));
            }
            resultMap = this.resultJson(ErrorCode.SUCCESS, "操作成功", telephoneList);
        } catch (Exception e) {
            logger.error("AppTelephoneController--getAppTelephone", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:添加电话本
     * @author: ccl
     * @Param: [request, telephone]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-28
     */
    @RequestMapping("/saveTelephone")
    @ResponseBody
    public Map<String, Object> SaveTelephone(HttpServletRequest request, @ModelAttribute("telephone") Telephone telephone) {
        Map<String, Object> resultMap = null;
        try {
            telephoneBiz.save(telephone);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("AppTelephoneController--SaveTelephone", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

}
