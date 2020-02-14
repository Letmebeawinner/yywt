package com.keyanzizheng.controller.employee;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.keyanzizheng.biz.common.SmsHessianService;
import com.keyanzizheng.biz.employee.EmployeeBiz;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.biz.result.ResultFormBiz;
import com.keyanzizheng.dao.result.ResultDao;
import com.keyanzizheng.entity.result.QueryResult;
import com.keyanzizheng.entity.result.ResultForm;
import com.keyanzizheng.entity.result.ResultFormStatistic;
import com.keyanzizheng.utils.StringUtil;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/ky")
public class EmployeeController extends BaseController {

    /**
     * 空字符串
     */
    private static final String NULL = "null";
    private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeBiz employeeBiz;
    @Autowired
    private ResultBiz resultBiz;
    @Autowired
    private ResultDao resultDao;
    @Autowired
    private SmsHessianService smsHessianService;
    @Autowired
    private ResultFormBiz resultFormBiz;

    @InitBinder("employee")
    public void initEmployee(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("employee.");
    }

    @InitBinder("queryResult")
    public void initResult(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("queryResult.");
    }

    @InitBinder("queryTask")
    public void initTask(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("queryTask.");
    }

    /**
     * 查询员工统计
     *
     * @return
     */
    @RequestMapping("/getEmployeeResultStatistic")
    public ModelAndView getEmployeeResultStatistic(@RequestParam("id") Long id,
                                                   @RequestParam(value = "year", required = false) String selectYear) {
        ModelAndView modelAndView = new ModelAndView("/result/resultStatistics/result_statistic_form");
        try {
            Calendar c = Calendar.getInstance();
            //获取年
            int year = c.get(Calendar.YEAR);
            modelAndView.addObject("currentYear", year);

            // 未点击搜索 没有传年份 默认查本年
            if (StringUtil.isBlank(selectYear)) {
                selectYear = String.valueOf(year);
            }
            List<ResultFormStatistic> resultFormStatisticList = resultDao
                    .queryStatisticResult("employeeId = " + id, selectYear);
            modelAndView.addObject("resultFormStatisticList", resultFormStatisticList);
            modelAndView.addObject("id", id);
            modelAndView.addObject("selectYear", selectYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 教职工成果列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getEmployeeResultList")
    public ModelAndView getEmployeeResultList(HttpServletRequest request,
                                              @ModelAttribute("pagination") Pagination pagination,
                                              @ModelAttribute("queryResult") QueryResult queryResult) {
        ModelAndView modelAndView = new ModelAndView("/employee/result_list");
        try {
            if (ObjectUtils.isNull(queryResult.getResultForm())) {
                queryResult.setResultForm(1);
            }
//            if (queryResult.getResultForm() == 1) {
//                queryResult.setSysUserId(queryResult.getEmployeeId());
//                queryResult.setEmployeeId(null);
//            }
            pagination.setRequest(request);
            List<QueryResult> resultList = resultBiz.getResultList(pagination, queryResult);
            if (!CollectionUtils.isEmpty(resultList)) {
                modelAndView.addObject("resultList", resultList);
            }

            List<ResultForm> resultFormList = resultFormBiz.getResultFormList(new ResultForm());
            modelAndView.addObject("resultFormList", resultFormList);
        } catch (Exception e) {
            logger.error("getResultList", e);
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
    public Map<String, Object> sendInfo(HttpServletRequest request, @RequestParam String content, @RequestParam String id) {
        Map<String, Object> objectMap = null;
        try {
            // 提交课题的不是教职工 没有对应的id
            if (NULL.equals(id)) {
                return this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
            }

            smsHessianService.sendInfo(content, 0L, id, 0);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("sendInfo", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }
}
