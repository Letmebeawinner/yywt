package com.renshi.controller.retirement;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.biz.retirement.RetirementBiz;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.retirement.QueryRetirement;
import com.renshi.entity.retirement.Retirement;
import com.renshi.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
public class RetirementController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(RetirementController.class);

    @InitBinder("retirement")
    public void initRetirement(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("retirement.");
    }

    @Autowired
    private RetirementBiz retirementBiz;
    @Autowired
    private EmployeeBiz employeeBiz;

    /**
     * 申请离退休/转出
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddRetirement")
    public ModelAndView toAddRetirement(HttpServletRequest request,
                                        @RequestParam(value = "outType") Long outType) {
        ModelAndView modelAndView = new ModelAndView("/retirement/retirement_add");
        try {
            request.setAttribute("outType",outType);
        } catch (Exception e) {
            logger.error("getRetirementList", e);
        }
        return modelAndView;
    }

    /**
     * 申请离退休
     *
     * @param request
     * @return
     */
    @RequestMapping("/addRetirement")
    @ResponseBody
    public Map<String, Object> addRetirement(HttpServletRequest request,
                                             @ModelAttribute("retirement") Retirement retirement,
                                             @RequestParam("ids") String ids) {
        Map<String, Object> objectMap = null;
        try {
            String  id [];
            if(ids.indexOf(",")>-1){
                id = ids.split(",");
            }else{
                id =new String[1];
                id[0] = ids;
            }
            for(int i=0;i<id.length;i++){
                Employee employee = employeeBiz.findById(Long.valueOf(id[i]));
                if (ObjectUtils.isNotNull(employee)) {
                    retirement.setId(null);
                    retirement.setEmployeeId(Long.valueOf(id[i]));
                    retirement.setEnterpartyTime(employee.getEnterPartyTime());
                    retirement.setName(employee.getName());
                    retirement.setSex(employee.getSex());
                    retirement.setBirthday(employee.getBirthDay());
                    retirement.setNationality(employee.getNationality());
                    retirement.setWorkTime(employee.getWorkTime());
                    retirement.setEducation(employee.getEducation());
                    retirement.setPresentPost(employee.getPresentPost());
                    retirement.setCategory(employee.getCategory());
                    retirement.setStatus(1);
                    retirementBiz.save(retirement);
                    Employee employee1 = new Employee();
                    employee1.setId(Long.valueOf(id[i]));
                    employee1.setStatus(2);
                    employeeBiz.update(employee1);
                }
            }
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", retirement);
        } catch (Exception e) {
            logger.error("addRetirement", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 离退休列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getRetirementList")
    public ModelAndView getRetirementList(HttpServletRequest request,
                                          @ModelAttribute("pagination") Pagination pagination,
                                          @ModelAttribute("retirement") Retirement retirement) {
        ModelAndView modelAndView = new ModelAndView("/retirement/retirement_list");
        try {
            String whereSql = GenerateSqlUtil.getSql(retirement);
            pagination.setRequest(request);
            List<Retirement> retirementList = retirementBiz.find(pagination, whereSql);
            modelAndView.addObject("retirementList", retirementList);
        } catch (Exception e) {
            logger.error("getRetirementList", e);
        }
        return modelAndView;
    }


    /**
     * 去批量倒入
     *
     * @return
     */
    @RequestMapping("/toImportRetirement")
    public String toImportRetirement(HttpServletRequest request) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/retirement/batch_import_retirement";
    }


    /**
     * 批量添加退休人员
     *
     * @return
     */
    @RequestMapping("/batchAddRetirement")
    public ModelAndView batchAddRetirement(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        ModelAndView modelAndView = new ModelAndView("/retirement/batch_import_retirement");
        try {
            String errorInfo = retirementBiz.batchImportRetirement(myFile, request);
            if (StringUtils.isTrimEmpty(errorInfo)) {
                errorInfo = "导入成功";
            }
            modelAndView.addObject("errorInfo", errorInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     * 删除离退休
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteRetirement")
    @ResponseBody
    public Map<String, Object> deleteRetirement(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> objectMap = null;
        try {
            retirementBiz.deleteById(id);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", "");
        } catch (Exception e) {
            logger.error("deleteRetirement", e);
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
    @RequestMapping("/toUpdateRetirement")
    public ModelAndView toUpdateRetirement(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/retirement/retirement_update");
        try {
            Retirement retirement = retirementBiz.findById(id);
            modelAndView.addObject("retirement", retirement);
        } catch (Exception e) {
            logger.error("toUpdateRetirement", e);
        }
        return modelAndView;
    }

    /**
     * 修改奖惩
     *
     * @param request
     * @param retirement
     * @return
     */
    @RequestMapping("/updateRetirement")
    @ResponseBody
    public Map<String, Object> updateRetirement(HttpServletRequest request, @ModelAttribute("retirement") Retirement retirement) {
        Map<String, Object> objectMap = null;
        try {
            retirementBiz.update(retirement);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", retirement);
        } catch (Exception e) {
            logger.error("updateRetirement", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", retirement);
        }
        return objectMap;
    }

}
