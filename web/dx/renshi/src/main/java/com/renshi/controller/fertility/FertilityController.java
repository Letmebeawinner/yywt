package com.renshi.controller.fertility;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.renshi.biz.common.BaseHessianBiz;
import com.renshi.biz.common.SmsHessianService;
import com.renshi.biz.fertility.FertilityBiz;
import com.renshi.entity.fertility.Fertility;
import com.renshi.entity.fertility.QueryFertility;
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

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/rs")
public class FertilityController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(FertilityController.class);

    @InitBinder("fertility")
    public void initFertility(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("fertility.");
    }
    @InitBinder("queryFertility")
    public void initqueryFertility(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("queryFertility.");
    }
    @Autowired
    private FertilityBiz fertilityBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
   /* @Autowired
    private SmsHessianService smsHessianService;*/

    /**
     * 跳转计生添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddFertility")
    public ModelAndView toAddFertility(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/fertility/fertility_add");
        try {
        } catch (Exception e) {
            logger.error("toAddFertility", e);
        }
        return modelAndView;
    }

    /**
     * 添加计生
     *
     * @param request
     * @param fertility
     * @return
     */
    @RequestMapping("/addFertility")
    @ResponseBody
    public Map<String, Object> addFertility(HttpServletRequest request, @ModelAttribute("fertility")Fertility fertility) {
        Map<String, Object> objectMap = null;
        try {
            fertility.setEmployeeId(SysUserUtils.getLoginSysUserId(request));
            fertility.setStatus(1);
            fertility.setIfPass(1);
            fertilityBiz.save(fertility);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", fertility);
        } catch (Exception e) {
            logger.error("addFertility", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 删除计生
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteFertility")
    @ResponseBody
    public Map<String, Object> deleteFertility(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        Map<String, Object> objectMap = null;
        try {
            Fertility fertility = fertilityBiz.findById(id);
            fertility.setStatus(2);
            fertilityBiz.update(fertility);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", fertility);
        } catch (Exception e) {
            logger.error("deleteFertility", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 去修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateFertility")
    public ModelAndView toUpdateFertility(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/fertility/fertility_update");
        try {
            Fertility fertility = fertilityBiz.findById(id);
            modelAndView.addObject("fertility", fertility);
        } catch (Exception e) {
            logger.error("toUpdateFertility", e);
        }
        return modelAndView;
    }

    /**
     * 修改计生
     *
     * @param request
     * @param fertility
     * @return
     */
    @RequestMapping("/updateFertility")
    @ResponseBody
    public Map<String, Object> updateFertility(HttpServletRequest request, @ModelAttribute("fertility") Fertility fertility) {
        Map<String, Object> objectMap = null;
        try {
            fertilityBiz.update(fertility);
            fertility=fertilityBiz.findById(fertility.getId());
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", fertility);
        } catch (Exception e) {
            logger.error("updateFertility", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 计生列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getFertilityList")
    public ModelAndView getFertilityList(HttpServletRequest request,
                                         @ModelAttribute("pagination") Pagination pagination,
                                         @ModelAttribute("queryFertility") QueryFertility queryFertility) {
        ModelAndView modelAndView = new ModelAndView("/fertility/fertility_list");
        try {
            pagination.setRequest(request);
            List<QueryFertility> fertilityList = fertilityBiz.getFertilityList(queryFertility,pagination);
            modelAndView.addObject("fertilityList", fertilityList);
        } catch (Exception e) {
            logger.error("getFertilityList", e);
        }
        return modelAndView;
    }
    /**
     * 计生列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/toApprovalFertilityList")
    public ModelAndView toApprovalFertilityList(HttpServletRequest request,
                                         @ModelAttribute("pagination") Pagination pagination,
                                         @ModelAttribute("queryFertility") QueryFertility queryFertility) {
        ModelAndView modelAndView = new ModelAndView("/fertility/fertility_approval_list");
        try {
            queryFertility.setIfPass(1);
            pagination.setRequest(request);
            List<QueryFertility> fertilityList = fertilityBiz.getFertilityList(queryFertility,pagination);
            modelAndView.addObject("fertilityList", fertilityList);
        } catch (Exception e) {
            logger.error("getFertilityList", e);
        }
        return modelAndView;
    }
    /**
     * 计生合同上传
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUploadContract")
    public ModelAndView toUploadContract(HttpServletRequest request,
                                         @ModelAttribute("fertility") Fertility fertility) {
        ModelAndView modelAndView = new ModelAndView("/fertility/fertility_uploadContract");
        try {
        } catch (Exception e) {
            logger.error("toUploadContract", e);
        }
        return modelAndView;
    }
    /**
     * 发送站内信
     *
     * @param request
     * @return
     */
    @RequestMapping("/sendInfo")
    @ResponseBody
    public Map<String, Object> sendInfo(HttpServletRequest request, @RequestParam String content,@RequestParam String id) {
        Map<String, Object> objectMap = null;
        try {
//            smsHessianService.sendInfo(content,0L,id,0);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("sendInfo", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }
}
