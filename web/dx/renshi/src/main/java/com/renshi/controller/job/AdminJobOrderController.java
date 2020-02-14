package com.renshi.controller.job;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.renshi.biz.job.JobOrderBiz;
import com.renshi.entity.job.JobOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 岗位系列
 */
@Controller
@RequestMapping("/admin/ganbu/jobOrder")
public class AdminJobOrderController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AdminJobOrderController.class);

    @Resource
    private JobOrderBiz jobOrderBiz;

    @InitBinder({"jobOrder"})
    public void initJobOrder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("jobOrder.");
    }

    /**
     * @Description:查询岗位系列列表
     * @author: Licong
     */
    @RequestMapping("/queryJobOrder")
    public String queryJobOrder(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            List<JobOrder> jobOrderList = jobOrderBiz.find(pagination, "1=1");
            request.setAttribute("jobOrderList", jobOrderList);
        } catch (Exception e) {
            logger.info("AdminJobOrderController--queryJobOrder", e);
            return this.setErrorPath(request, e);
        }
        return "job/jobOrder_list";
    }

    /**
     * @Description:删除岗位系列
     * @author: Licong
     */
    @RequestMapping("/delJobOrder")
    @ResponseBody
    public Map<String, Object> delJobOrder(@RequestParam("id") Long id) {
        try {
            jobOrderBiz.deleteById(id);
        } catch (Exception e) {
            logger.info("AdminJobOrderController.java--delJobOrder", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
    }


    /**
     * @Description:跳转到添加页面
     * @author: Licong
     * @Param: [request, response]
     * @Return: java.lang.String
     * @Date: 2016/12/27
     */
    @RequestMapping("/toAddJobOrder")
    public String toAddJobOrder(HttpServletRequest request, HttpServletResponse response) {
        return "job/jobOrder_add";
    }


    /**
     * @Description:添加岗位系列
     * @author: Licong
     * @Param: [jobOrder]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016/12/27
     */
    @RequestMapping("/addJobOrder")
    @ResponseBody
    public Map<String, Object> addJobOrder(@ModelAttribute("jobOrder") JobOrder jobOrder) {
        try {
            jobOrder.setStatus(0);
            jobOrderBiz.save(jobOrder);
        } catch (Exception e) {
            logger.info("AdminJobOrderController--addJobOrder", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "/admin/ganbu/jobOrder/queryJobOrder.json");
    }


    /**
     * @Description:跳转到修改页面
     * @author: Licong
     * @Param: [request, response]
     * @Return: java.lang.String
     * @Date: 2016/12/27
     */
    @RequestMapping("/toUpdateJobOrder")
    public String toUpdateJobOrder(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Long id) {
        try {
            JobOrder jobOrder = jobOrderBiz.findById(id);
            request.setAttribute("jobOrder", jobOrder);
        } catch (Exception e) {
            logger.info("AdminJobOrderController--toUpdateJobOrder", e);
            return this.setErrorPath(request, e);
        }
        return "job/jobOrder_update";
    }

    /**
     * @Description:修改岗位系列
     * @author: Licong
     * @Param: [request, response]
     * @Return: java.lang.String
     * @Date: 2016/12/27
     */
    @RequestMapping("/updateJobOrder")
    @ResponseBody
    public Map<String, Object> updateJobOrder(@ModelAttribute("jobOrder") JobOrder jobOrder) {
        try {
            jobOrder.setStatus(0);
            jobOrderBiz.update(jobOrder);
        } catch (Exception e) {
            logger.info("AdminJobOrderController--updateJobOrder", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "/admin/ganbu/jobOrder/queryJobOrder.json");
    }

}


