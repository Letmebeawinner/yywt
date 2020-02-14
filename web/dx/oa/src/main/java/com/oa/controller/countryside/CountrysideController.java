package com.oa.controller.countryside;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.oa.biz.countryside.CountrysideBiz;
import com.oa.biz.countryside.CountrysideEmpBiz;
import com.oa.entity.countryside.Countryside;
import com.oa.entity.countryside.CountrysideEmp;
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
@RequestMapping("/admin/oa")
public class CountrysideController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CountrysideController.class);

    @InitBinder("countryside")
    public void initCountryside(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("countryside.");
    }

    @InitBinder("queryEmployee")
    public void initQueryEmployee(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("queryEmployee.");
    }

    @InitBinder("countrysideEmp")
    public void initCountrysideEmp(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("countrysideEmp.");
    }

    @Autowired
    private CountrysideBiz countrysideBiz;
    @Autowired
    private CountrysideEmpBiz countrysideEmpBiz;

    /**
     * 跳转下乡添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddCountryside")
    public ModelAndView toAddCountryside(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/countryside/countryside_add");
        try {
        } catch (Exception e) {
            logger.error("toAddCountryside", e);
        }
        return modelAndView;
    }

    /**
     * 添加下乡
     *
     * @param request
     * @param countryside
     * @return
     */
    @RequestMapping("/addCountryside")
    @ResponseBody
    public Map<String, Object> addCountryside(HttpServletRequest request, @ModelAttribute("countryside") Countryside countryside) {
        Map<String, Object> objectMap = null;
        try {
            countryside.setStatus(1);
            countryside.setNumber(0L);
            countryside.setJoinNumber(0L);
            countrysideBiz.save(countryside);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", countryside);
        } catch (Exception e) {
            logger.error("addCountryside", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 删除下乡
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteCountryside")
    @ResponseBody
    public Map<String, Object> addCountryside(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> objectMap = null;
        try {
            Countryside countryside = countrysideBiz.findById(id);
            countryside.setStatus(2);
            countrysideBiz.update(countryside);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", countryside);
        } catch (Exception e) {
            logger.error("addCountryside", e);
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
    @RequestMapping("/toUpdateCountryside")
    public ModelAndView toUpdateCountryside(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/countryside/countryside_update");
        try {
            Countryside countryside = countrysideBiz.findById(id);
            modelAndView.addObject("countryside", countryside);
        } catch (Exception e) {
            logger.error("toUpdateCountryside", e);
        }
        return modelAndView;
    }

    /**
     * 修改下乡
     *
     * @param request
     * @param countryside
     * @return
     */
    @RequestMapping("/updateCountryside")
    @ResponseBody
    public Map<String, Object> updateCountryside(HttpServletRequest request, @ModelAttribute("countryside") Countryside countryside) {
        Map<String, Object> objectMap = null;
        try {
            countrysideBiz.update(countryside);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", countryside);
        } catch (Exception e) {
            logger.error("updateCountryside", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", countryside);
        }
        return objectMap;
    }

    /**
     * 下乡列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getCountrysideList")
    public ModelAndView getCountrysideList(HttpServletRequest request,
                                           @ModelAttribute("pagination") Pagination pagination,
                                           @ModelAttribute("countryside") Countryside countryside) {
        ModelAndView modelAndView = new ModelAndView("/countryside/countryside_list");
        try {
            pagination.setRequest(request);
            List<Countryside> countrysideList = countrysideBiz.getCountrysideList(countryside, pagination);
            modelAndView.addObject("countrysideList", countrysideList);
        } catch (Exception e) {
            logger.error("getCountrysideList", e);
        }
        return modelAndView;
    }

    /**
     * 参与下乡人员集合
     *
     * @param request
     * @return
     */
    @RequestMapping("/getCountrysideEmpList")
    public ModelAndView getCountrysideEmpList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("countrysideEmp") CountrysideEmp countrysideEmp) {
        ModelAndView modelAndView = new ModelAndView("/countryside/countryside_employee_list");
        try {
            String whereSql = "1=1";
            whereSql += " and countrysideId=" + countrysideEmp.getCountrysideId() + " order by joinStatus desc";
            pagination.setRequest(request);
            List<CountrysideEmp> employeeList = countrysideEmpBiz.queryCountryById(whereSql, pagination);
            modelAndView.addObject("employeeList", employeeList);
            modelAndView.addObject("countrysideEmp", countrysideEmp);
        } catch (Exception e) {
            logger.error("toUpdateEducate", e);
        }
        return modelAndView;
    }

    /**
     * 下乡活动加入教职工
     *
     * @param request
     * @param countrysideEmp
     * @return
     */
    @RequestMapping("/addCountrysideEmployee")
    @ResponseBody
    public Map<String, Object> addCountrysideEmployee(HttpServletRequest request, @ModelAttribute("countrysideEmp") CountrysideEmp countrysideEmp) {
        Map<String, Object> objectMap = null;
        try {
            String employeeIds = request.getParameter("employeeIds");
            employeeIds = employeeIds.trim().replace(' ', ',');
            String[] _employeeIds = employeeIds.split(",");
            Countryside countryside = countrysideBiz.findById(countrysideEmp.getCountrysideId());
            Long count = 0L;
            for (String employeeId : _employeeIds) {
                CountrysideEmp _countrysideEmp = new CountrysideEmp();
                _countrysideEmp.setEmployeeId(Long.valueOf(employeeId));
                _countrysideEmp.setCountrysideId(countrysideEmp.getCountrysideId());
                _countrysideEmp.setStatus(1);
                _countrysideEmp.setJoinStatus(1);
                List<CountrysideEmp> countrysideEmpList = countrysideEmpBiz.getCountrysideEmpList(_countrysideEmp);
                if (CollectionUtils.isEmpty(countrysideEmpList)) {
                    countrysideEmpBiz.save(_countrysideEmp);
                    count++;
                }
            }
            if (countryside.getJoinNumber() == null) {
                countryside.setJoinNumber(0L);
            }
            countryside.setNumber(countryside.getNumber() + count);
            countrysideBiz.update(countryside);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", countrysideEmp);
        } catch (Exception e) {
            logger.error("updateUnion", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", countrysideEmp);
        }
        return objectMap;
    }

    /**
     * 移除教职工
     *
     * @param request
     * @param countrysideEmp
     * @return
     */
    @RequestMapping("/deleteCountrysideEmployee")
    @ResponseBody
    public Map<String, Object> deleteCountrysideEmployee(HttpServletRequest request, @ModelAttribute("countrysideEmp") CountrysideEmp countrysideEmp) {
        Map<String, Object> objectMap = null;
        try {
            List<CountrysideEmp> countrysideEmpList = countrysideEmpBiz.getCountrysideEmpList(countrysideEmp);
            if (ObjectUtils.isNotNull(countrysideEmpList)) {
                countrysideEmp = countrysideEmpList.get(0);
            }
            countrysideEmpBiz.delete(countrysideEmp);
            Countryside countryside = countrysideBiz.findById(countrysideEmp.getCountrysideId());
            if (ObjectUtils.isNotNull(countryside)) {
                if (countrysideEmp.getJoinStatus() == 2) {
                    countryside.setJoinNumber(countryside.getJoinNumber() - 1);
                }
                countryside.setNumber(countryside.getNumber() - 1);
                countrysideBiz.update(countryside);
            }
            objectMap = this.resultJson(ErrorCode.SUCCESS, "移除成功", countrysideEmp);
        } catch (Exception e) {
            logger.error("updateUnion", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", countrysideEmp);
        }
        return objectMap;
    }

    /**
     * 修改教职工参加状态
     *
     * @param request
     * @param countrysideEmp
     * @return
     */
    @RequestMapping("/updateCountrysideEmployee")
    @ResponseBody
    public Map<String, Object> updateCountrysideEmployee(HttpServletRequest request, @ModelAttribute("countrysideEmp") CountrysideEmp countrysideEmp) {
        Map<String, Object> objectMap = null;
        try {
            CountrysideEmp countrysideEmp1 = countrysideEmpBiz.findById(countrysideEmp.getId());
            countrysideEmp1.setJoinStatus(2);
            countrysideEmpBiz.update(countrysideEmp1);

            Countryside countryside = countrysideBiz.findById(countrysideEmp.getCountrysideId());
            countryside.setJoinNumber(countryside.getJoinNumber() + 1);
            countrysideBiz.update(countryside);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "操作成功", countrysideEmp);
        } catch (Exception e) {
            logger.error("updateUnion", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", countrysideEmp);
        }
        return objectMap;
    }
}
