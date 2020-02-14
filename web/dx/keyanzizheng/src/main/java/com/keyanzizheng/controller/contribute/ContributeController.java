package com.keyanzizheng.controller.contribute;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.SysUserUtils;
import com.keyanzizheng.biz.contribute.ContributeBiz;
import com.keyanzizheng.biz.employee.EmployeeBiz;
import com.keyanzizheng.constant.PaginationConstants;
import com.keyanzizheng.entity.contribute.Contribute;
import com.keyanzizheng.entity.contribute.QueryContribute;
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
public class ContributeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ContributeController.class);
    @Autowired
    private ContributeBiz contributeBiz;
    @Autowired
    private EmployeeBiz employeeBiz;

    @InitBinder("contribute")
    public void initContribute(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("contribute.");
    }

    @InitBinder("queryContribute")
    public void initQueryContribute(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("queryContribute.");
    }

    /**
     * 跳转投稿添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddContribute")
    public ModelAndView toAddContribute(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("contribute/contribute_add");
        try {
        } catch (Exception e) {
            logger.error("toAddContribute", e);
        }
        return modelAndView;
    }

    /**
     * 添加投稿
     *
     * @param request
     * @param contribute
     * @return
     */
    @RequestMapping("/addContribute")
    @ResponseBody
    public Map<String, Object> addContribute(HttpServletRequest request, @ModelAttribute("contribute") Contribute contribute) {
        Map<String, Object> objectMap = null;
        try {
            contribute.setEmployeeId(SysUserUtils.getLoginSysUserId(request));
            contribute.setStatus(1);
            contribute.setIfPass(1);
            contributeBiz.save(contribute);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", contribute);
        } catch (Exception e) {
            logger.error("addContribute", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 删除投稿
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteContribute")
    @ResponseBody
    public Map<String, Object> addContribute(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        Map<String, Object> objectMap = null;
        try {
            Contribute contribute = contributeBiz.findById(id);
            contribute.setStatus(2);
            contributeBiz.update(contribute);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", contribute);
        } catch (Exception e) {
            logger.error("addContribute", e);
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
    @RequestMapping("/toUpdateContribute")
    public ModelAndView toUpdateContribute(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("contribute/contribute_update");
        try {
            Contribute contribute = contributeBiz.findById(id);
            modelAndView.addObject("contribute", contribute);
        } catch (Exception e) {
            logger.error("toUpdateContribute", e);
        }
        return modelAndView;
    }

    /**
     * 修改投稿
     *
     * @param request
     * @param contribute
     * @return
     */
    @RequestMapping("/updateContribute")
    @ResponseBody
    public Map<String, Object> updateContribute(HttpServletRequest request, @ModelAttribute("contribute") Contribute contribute) {
        Map<String, Object> objectMap = null;
        try {
            contributeBiz.update(contribute);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", contribute);
        } catch (Exception e) {
            logger.error("updateContribute", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", contribute);
        }
        return objectMap;
    }

    /**
     * 投稿列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getContributeList")
    public ModelAndView getContributeList(HttpServletRequest request,
                                          @ModelAttribute("pagination") Pagination pagination,
                                          @ModelAttribute("queryContribute") QueryContribute queryContribute) {
        ModelAndView modelAndView = new ModelAndView("contribute/contribute_list");
        try {
            pagination.setRequest(request);
            pagination.setPageSize(PaginationConstants.PAGE_SIZE);
            List<QueryContribute> contributeList = contributeBiz.getContributeList(pagination, queryContribute);
            if(!CollectionUtils.isEmpty(contributeList)){
                modelAndView.addObject("contributeList", contributeList);
            }
        } catch (Exception e) {
            logger.error("getContributeList", e);
        }
        return modelAndView;
    }

    /**
     * 未审核投稿列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/toContributeApprovalList")
    public ModelAndView toContributeApprovalList(HttpServletRequest request,
                                          @ModelAttribute("pagination") Pagination pagination,
                                                 @ModelAttribute("queryContribute") QueryContribute queryContribute) {
        ModelAndView modelAndView = new ModelAndView("contribute/contribute_approval_list");
        try {
            queryContribute.setIfPass(1);
            pagination.setRequest(request);
            List<QueryContribute> contributeList = contributeBiz.getContributeList(pagination, queryContribute);
            if(!CollectionUtils.isEmpty(contributeList)){
                modelAndView.addObject("contributeList", contributeList);
            }
        } catch (Exception e) {
            logger.error("getContributeList", e);
        }
        return modelAndView;
    }

}
