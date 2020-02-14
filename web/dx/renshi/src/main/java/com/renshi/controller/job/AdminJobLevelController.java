package com.renshi.controller.job;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.renshi.biz.job.JobLevelBiz;
import com.renshi.biz.job.JobOrderBiz;
import com.renshi.entity.job.JobLevel;
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
 * 岗位级别
 */
@Controller
@RequestMapping("/admin/ganbu/jobLevel")
public class AdminJobLevelController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AdminJobLevelController.class);

    @Resource
    private JobLevelBiz jobLevelBiz;
    @Resource
    private JobOrderBiz jobOrderBiz;

    @InitBinder({"jobLevel"})
    public void initJobLevel(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("jobLevel.");
    }

    /**
     * @Description:查询岗位级别列表
     * @author: Licong
     */
    @RequestMapping("/queryJobLevel")
    public String queryJobLevel(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            //获得岗位系列
            List<JobOrder> jobOrderList = jobOrderBiz.find(null, "1=1");
            request.setAttribute("jobOrderList", jobOrderList);

            String whereSql = "1=1";
            String jobOrderId=request.getParameter("jobOrderId");
            if(!StringUtils.isTrimEmpty(jobOrderId)){
                whereSql+=" and jobOrderId="+jobOrderId;
            }
            pagination.setRequest(request);
            List<JobLevel> jobLevelList = jobLevelBiz.find(pagination, whereSql);
            if(ObjectUtils.isNotNull(jobLevelList)){
                for(JobLevel jobLevel:jobLevelList){
                    JobOrder jobOrder=jobOrderBiz.findById(jobLevel.getJobOrderId());
                    jobLevel.setJobOrderName(jobOrder.getName());
                }
            }
            request.setAttribute("jobLevelList", jobLevelList);
            request.setAttribute("jobOrderId", jobOrderId);
        } catch (Exception e) {
            logger.info("AdminJobLevelController--queryJobLevel", e);
            return this.setErrorPath(request, e);
        }
        return "job/jobLevel_list";
    }

    /**
     * @Description: ajax  查询根据岗位系列查询
     * @author: Licong
     * @Param: [jobOrderId]
     * @Return: java.util.Map<java.lang.String  java.lang.Object>
     * @Date: 2017/1/4
     */
    @RequestMapping("/ajax/queryJobLevelByOrderId")
    @ResponseBody
    public Map<String, Object> queryJobLevelByOrderId(@RequestParam("jobOrderId") Long jobOrderId) {
        try {
            String whereSql = " 1=1 and jobOrderId=" + jobOrderId;
            List<JobLevel> jobLevelList = jobLevelBiz.find(null, whereSql);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, jobLevelList);
        } catch (Exception e) {
            logger.info("AdminJobOrderController--queryJobLevelByOrderId", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
    }


    /**
     * @Description:删除岗位级别
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String java.lang.Object>
     * @Date: 2016/12/22
     */
    @RequestMapping("/delJobLevel")
    @ResponseBody
    public Map<String, Object> delJobLevel(@RequestParam("id") Long id) {
        try {
            jobLevelBiz.deleteById(id);
        } catch (Exception e) {
            logger.info("AdminJobLevelController.java--delJobLevel", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
    }


    /**
     * @Description:跳转到添加页面
     * @author: Licong
     * @Date: 2016/12/27
     */
    @RequestMapping("/toAddJobLevel")
    public String toAddJobLevel(HttpServletRequest request) {
        try {
            //获得岗位系列
            List<JobOrder> jobOrderList = jobOrderBiz.find(null, "1=1");
            request.setAttribute("jobOrderList", jobOrderList);
        } catch (Exception e) {
            logger.info("AdminJobLevelController--toAddJobLevel", e);
            return this.setErrorPath(request, e);
        }
        return "job/jobLevel_add";
    }


    /**
     * 添加岗位
     *
     * @param jobLevel
     * @return
     */
    @RequestMapping("/addJobLevel")
    @ResponseBody
    public Map<String, Object> addJobLevel(@ModelAttribute("jobLevel") JobLevel jobLevel) {
        Map<String, Object> json = null;
        try {
            jobLevel.setStatus(0);
            jobLevelBiz.save(jobLevel);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            logger.info("AdminJobLevelController--addJobLevel", e);
        }
        return json;
    }


    /**
     * @Description:跳转到修改页面
     * @author: Licong
     * @Param: [request, response]
     * @Return: java.lang.String
     * @Date: 2016/12/27
     */
    @RequestMapping("/toUpdateJobLevel")
    public String toUpdateJobLevel(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Long id) {
        try {
            //获得岗位系列
            List<JobOrder> jobOrderList = jobOrderBiz.find(null, " 1=1");
            request.setAttribute("jobOrderList", jobOrderList);

            JobLevel jobLevel = jobLevelBiz.findById(id);
            request.setAttribute("jobLevel", jobLevel);
        } catch (Exception e) {
            logger.info("AdminJobLevelController--toUpdateJobLevel", e);
            return this.setErrorPath(request, e);
        }
        return "job/jobLevel_update";
    }

    /**
     * @Description:修改岗位级别
     * @author: Licong
     * @Param: [request, response]
     * @Return: java.lang.String
     * @Date: 2016/12/27
     */
    @RequestMapping("/updateJobLevel")
    @ResponseBody
    public Map<String, Object> updateJobLevel(@ModelAttribute("jobLevel") JobLevel jobLevel) {
        Map<String, Object> json = null;
        try {
            jobLevel.setStatus(0);
            jobLevelBiz.update(jobLevel);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "/admin/ganbu/jobLevel/queryJobLevel.json");
        } catch (Exception e) {
            logger.info("AdminJobLevelController--updateJobLevel", e);
        }
        return json;
    }


}


