package com.renshi.controller.job;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.renshi.biz.job.JobBiz;
import com.renshi.biz.job.JobOrderBiz;
import com.renshi.entity.job.Job;
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
 * 岗位
 */
@Controller
@RequestMapping("/admin/ganbu/job")
public class AdminJobController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AdminJobController.class);

    @Resource
    private JobBiz jobBiz;
    @Resource
    private JobOrderBiz jobOrderBiz;

    @InitBinder({"job"})
    public void initJob(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("job.");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    /**
     * @Description:查询岗位列表
     * @author: Licong
     * @Date: 2016/12/27
     */
    @RequestMapping("/queryJob")
    public String queryJob(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,
                           @RequestParam(value = "jobOrderId", required = false) Long jobOrderId) {
        try {
            List<JobOrder> jobOrderList = jobOrderBiz.find(null, "1=1");//获得岗位系列
            request.setAttribute("jobOrderList", jobOrderList);

            List<Job> jobList = jobBiz.find(pagination, "1=1");
            request.setAttribute("jobList", jobList);
            request.setAttribute("jobOrderId", jobOrderId);
        } catch (Exception e) {
            logger.info("AdminJobController--queryJob", e);
            return this.setErrorPath(request, e);
        }
        return "job/job_list";
    }


    /**
     * @Description: ajax  查询根据岗位系列查询
     * @author: Licong
     * @Param: [jobOrderId]
     * @Return: java.util.Map<java.lang.String                                                                                                                               ,                                                                                                                               java.lang.Object>
     * @Date: 2017/1/4
     */
    @RequestMapping("/ajax/queryJobByOrderId")
    @ResponseBody
    public Map<String, Object> queryJobByOrderId(@RequestParam("jobOrderId") Long jobOrderId) {
        try {
            String whereSql = " 1=1 and jobOrderId =" + jobOrderId;
            List<Job> jobList = jobBiz.find(null, whereSql);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, jobList);
        } catch (Exception e) {
            logger.info("AdminJobOrderController--queryJobByOrderId", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
    }


    /**
     * @Description:删除岗位
     * @author: Licong
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String                                                                                                                               ,                                                                                                                               java.lang.Object>
     * @Date: 2016/12/22
     */
    @RequestMapping("/delJob")
    @ResponseBody
    public Map<String, Object> delJob(@RequestParam("id") Long id) {
        try {
            Job job = jobBiz.findById(id);
            jobBiz.delete(job);
        } catch (Exception e) {
            logger.info("AdminJobController.java--delJob", e);
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
    @RequestMapping("/toAddJob")
    public String toAddJob(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<JobOrder> jobOrderList = jobOrderBiz.find(null, "1=1");//获得岗位系列
            request.setAttribute("jobOrderList", jobOrderList);
        } catch (Exception e) {
            logger.info("AdminJobController--toAddJob", e);
            return this.setErrorPath(request, e);
        }
        return "job/job_add";
    }


    /**
     * 添加岗位
     *
     * @param job
     * @return
     */
    @RequestMapping("/addJob")
    @ResponseBody
    public Map<String, Object> addJob(@ModelAttribute("job") Job job) {
        try {
            job.setStatus(0);
            if (job.getAnalyzeTime() == null) {
                job.setAnalyzeTime(null);
            }
            jobBiz.save(job);
        } catch (Exception e) {
            logger.info("AdminJobController--addJob", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
    }


    /**
     * 跳转到修改页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateJob")
    public String toUpdateJob(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            Job job = jobBiz.findById(id);
            request.setAttribute("job", job);

            List<JobOrder> jobOrderList = jobOrderBiz.find(null, "1=1");//获得岗位系列
            request.setAttribute("jobOrderList", jobOrderList);
        } catch (Exception e) {
            logger.info("AdminJobController--toUpdateJob", e);
            return this.setErrorPath(request, e);
        }
        return "job/job_update";
    }


    /**
     * 跳转到修改页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toDetailJob")
    public String toDetailJob(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            List<JobOrder> jobOrderList = jobOrderBiz.find(null, "1=1");//获得岗位系列
            request.setAttribute("jobOrderList", jobOrderList);

            Job job = jobBiz.findById(id);
            request.setAttribute("job", job);

        } catch (Exception e) {
            logger.info("AdminJobController--toUpdateJob", e);
            return this.setErrorPath(request, e);
        }
        return "job/job_detail";
    }


    /**
     * 修改岗位
     *
     * @param job
     * @return
     */
    @RequestMapping("/updateJob")
    @ResponseBody
    public Map<String, Object> updateJob(@ModelAttribute("job") Job job) {
        try {
            job.setStatus(0);
            jobBiz.update(job);
        } catch (Exception e) {
            logger.info("AdminJobController--updateJob", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
    }

}


