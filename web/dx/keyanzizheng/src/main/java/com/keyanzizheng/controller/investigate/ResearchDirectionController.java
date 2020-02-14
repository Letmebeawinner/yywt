package com.keyanzizheng.controller.investigate;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.SysUserUtils;
import com.keyanzizheng.biz.investigate.ResearchDirectionBiz;
import com.keyanzizheng.entity.investigate.QueryResearchDirection;
import com.keyanzizheng.entity.investigate.ResearchDirection;
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
@RequestMapping("/admin/ky")
public class ResearchDirectionController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ResearchDirectionController.class);

    @InitBinder("researchDirection")
    public void initResearchDirection(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("researchDirection.");
    }
    @InitBinder("queryResearchDirection")
    public void initQueryResearchDirection(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("queryResearchDirection.");
    }
    @Autowired
    private ResearchDirectionBiz researchDirectionBiz;

    /**
     * 跳转调研方向添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddResearchDirection")
    public ModelAndView toAddResearchDirection(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/investigate/researchDirection_add");
        try {
        } catch (Exception e) {
            logger.error("toAddResearchDirection", e);
        }
        return modelAndView;
    }

    /**
     * 添加调研方向
     *
     * @param request
     * @param researchDirection
     * @return
     */
    @RequestMapping("/addResearchDirection")
    @ResponseBody
    public Map<String, Object> addResearchDirection(HttpServletRequest request, @ModelAttribute ResearchDirection researchDirection) {
        Map<String, Object> objectMap = null;
        try {
            researchDirection.setSysUserId(SysUserUtils.getLoginSysUserId(request));
            researchDirection.setStatus(1);
            researchDirection.setIfReport(1);
            researchDirectionBiz.save(researchDirection);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", researchDirection);
        } catch (Exception e) {
            logger.error("addResearchDirection", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 删除调研方向
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteResearchDirection")
    @ResponseBody
    public Map<String, Object> addResearchDirection(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        Map<String, Object> objectMap = null;
        try {
            ResearchDirection researchDirection = researchDirectionBiz.findById(id);
            researchDirection.setStatus(2);
            researchDirectionBiz.update(researchDirection);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", researchDirection);
        } catch (Exception e) {
            logger.error("addResearchDirection", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 调研方向详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/getResearchDirectionInfo")
    public ModelAndView getResearchDirectionById(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/investigate/researchDirection_info");
        try {
            QueryResearchDirection researchDirection = researchDirectionBiz.getResearchDirectionById(id);
            modelAndView.addObject("researchDirection", researchDirection);
        } catch (Exception e) {
            logger.error("getResearchDirectionInfo", e);
        }
        return modelAndView;
    }

    /**
     * 去修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateResearchDirection")
    public ModelAndView toUpdateResearchDirection(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/investigate/researchDirection_update");
        try {
            QueryResearchDirection researchDirection = researchDirectionBiz.getResearchDirectionById(id);
            modelAndView.addObject("researchDirection", researchDirection);
        } catch (Exception e) {
            logger.error("toUpdateResearchDirection", e);
        }
        return modelAndView;
    }

    /**
     * 修改调研方向
     *
     * @param request
     * @param researchDirection
     * @return
     */
    @RequestMapping("/updateResearchDirection")
    @ResponseBody
    public Map<String, Object> updateResearchDirection(HttpServletRequest request, @ModelAttribute("researchDirection") ResearchDirection researchDirection) {
        Map<String, Object> objectMap = null;
        try {
            researchDirectionBiz.update(researchDirection);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", researchDirection);
        } catch (Exception e) {
            logger.error("updateResearchDirection", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", researchDirection);
        }
        return objectMap;
    }

    /**
     * 调研方向列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getResearchDirectionList")
    public ModelAndView getResearchDirectionList(HttpServletRequest request,
                                                 @ModelAttribute("pagination") Pagination pagination,
                                                 @ModelAttribute("queryResearchDirection") QueryResearchDirection queryResearchDirection) {
        ModelAndView modelAndView = new ModelAndView("/investigate/researchDirection_list");
        try {
            pagination.setRequest(request);
            List<QueryResearchDirection> researchDirectionList = researchDirectionBiz.getResearchDirectionList(pagination, queryResearchDirection);
            if(!CollectionUtils.isEmpty(researchDirectionList)){
                modelAndView.addObject("researchDirectionList", researchDirectionList);
            }
        } catch (Exception e) {
            logger.error("getResearchDirectionList", e);
        }
        return modelAndView;
    }

}
