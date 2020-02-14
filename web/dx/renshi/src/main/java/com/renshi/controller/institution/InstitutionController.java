package com.renshi.controller.institution;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.biz.institution.InstitutionBiz;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.institution.Institution;
import com.renshi.utils.FileExportImportUtil;
import com.renshi.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/rs")
public class InstitutionController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(InstitutionController.class);

    @InitBinder("queryInstitution")
    public void initQueryInstitution(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("queryInstitution.");
    }

    @Autowired
    private InstitutionBiz institutionBiz;

    @Autowired
    private EmployeeBiz employeeBiz;

    @InitBinder("institution")
    public void initInstitution(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("institution.");
    }


    /**
     * 奖惩列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getInstitutionList")
    public ModelAndView getInstitutionList(HttpServletRequest request,
                                           @ModelAttribute("pagination") Pagination pagination,
                                           @ModelAttribute("institution") Institution institution) {
        ModelAndView modelAndView = new ModelAndView("/institution/institution_list");
        try {
            pagination.setRequest(request);
            String whereSql = GenerateSqlUtil.getSql(institution);
            List<Institution> institutionList = institutionBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(institutionList)) {
                for (Institution institution1 : institutionList) {
//                    Employee employee = employeeBiz.findById(institution1.getEmployeeId());
                    List<Employee> employees = employeeBiz.find(null, "id in ( " + institution1.getEmployeeId() + " )");
                    if (ObjectUtils.isNotNull(employees)) {
//                        institution1.setEmployeeName(employee.getName());
                        institution1.setEmployeeName(employees.stream().map(e -> e.getName()).collect(Collectors.joining(", ")));
                    }
                }
            }
            modelAndView.addObject("institutionList", institutionList);
            modelAndView.addObject("institution", institution);
        } catch (Exception e) {
            logger.error("getInstitutionList", e);
        }
        return modelAndView;
    }

    /**
     * 跳转奖惩添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddInstitution")
    public ModelAndView toAddInstitution(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/institution/institution_add");
        try {
        } catch (Exception e) {
            logger.error("toAddInstitution", e);
        }
        return modelAndView;
    }

    /**
     * 添加奖惩
     *
     * @param request
     * @param institution
     * @return
     */
    @RequestMapping("/addInstitution")
    @ResponseBody
    public Map<String, Object> addInstitution(HttpServletRequest request, @ModelAttribute("institution") Institution institution) {
        Map<String, Object> objectMap = null;
        try {
            String[] split = institution.getEmployeeId().split(",");
            for (String s : split) {
                institution.setId(null);
                institution.setEmployeeId(s);
                institutionBiz.save(institution);
            }
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", institution);
        } catch (Exception e) {
            logger.error("addInstitution", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 删除奖惩
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteInstitution")
    @ResponseBody
    public Map<String, Object> addInstitution(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> objectMap = null;
        try {
            institutionBiz.deleteById(id);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", "");
        } catch (Exception e) {
            logger.error("addInstitution", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 奖惩详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/getInstitutionInfo")
    public ModelAndView getInstitutionById(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/institution/institution_info");
        try {
            Institution institution = institutionBiz.findEmployeeById(id);
            modelAndView.addObject("institution", institution);
        } catch (Exception e) {
            logger.error("getInstitutionInfo", e);
        }
        return modelAndView;
    }

    /**
     * 去修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateInstitution")
    public ModelAndView toUpdateInstitution(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/institution/institution_update");
        try {
            Institution institution = institutionBiz.findById(id);
            modelAndView.addObject("institution", institution);
        } catch (Exception e) {
            logger.error("toUpdateInstitution", e);
        }
        return modelAndView;
    }

    /**
     * 修改奖惩
     *
     * @param request
     * @param institution
     * @return
     */
    @RequestMapping("/updateInstitution")
    @ResponseBody
    public Map<String, Object> updateInstitution(HttpServletRequest request, @ModelAttribute("institution") Institution institution) {
        Map<String, Object> objectMap = null;
        try {
            String[] split = institution.getEmployeeId().split(",");
            Institution institution1 = institutionBiz.findById(institution.getId());
            for (String s : split) {
                if (ObjectUtils.isNotNull(institution1)) {
                    if (s.equals(institution1.getEmployeeId())) {
                        institution.setEmployeeId(s);
                        institutionBiz.update(institution);
                    } else {
                        institution.setId(null);
                        institution.setEmployeeId(s);
                        institutionBiz.save(institution);
                    }
                    if(!Arrays.asList(split).contains(institution1.getEmployeeId())){
                        institutionBiz.deleteById(institution1.getId());
                    }
                }
            }

            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", institution);
        } catch (Exception e) {
            logger.error("updateInstitution", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", institution);
        }
        return objectMap;
    }

    /**
     * 导出奖惩记录
     *
     * @param request
     * @param response
     */
    @RequestMapping("/exportInstitutionExcel")
    public void exportInstitutionExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 指定文件生成路径
        String dir = request.getSession().getServletContext().getRealPath("/excelfile/institution");
        // 文件名
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        String expName = "奖惩_" + dateString;
        // 表头信息
        String[] headName = {"姓名", "性别", "部门及职务", "获奖名称", "颁奖单位", "是否发放证书", "证书时间", "备注"};

        // 拆分为一万条数据每Excel，防止内存使用太大
        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        String whereSql = " 1=1";
        pagination.setRequest(request);
        institutionBiz.find(pagination, whereSql);
        int num = pagination.getTotalPages();// 总页数
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
            pagination.setCurrentPage(i);
            List<Institution> institutionList = institutionBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(institutionList)) {
                for (Institution institution1 : institutionList) {
                    Employee employee = employeeBiz.findById(Long.parseLong(institution1.getEmployeeId()));
                    institution1.setEmployeeName(employee.getName());
                    institution1.setSex(employee.getSex());
                    institution1.setPresentPost(employee.getPresentPost());
                }
            }
            List<List<String>> list = convert(institutionList);
            File file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
            srcfile.add(file);
        }
        FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
    }

    public List<List<String>> convert(List<Institution> institutionList) {
        List<List<String>> list = new ArrayList<List<String>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (institutionList != null && institutionList.size() > 0) {
            for (Institution institution : institutionList) {
                List<String> smallList = new ArrayList<String>();
                smallList.add(institution.getEmployeeName() + "");
                if (institution.getSex() == 0) {
                    smallList.add("男");
                }
                if (institution.getSex() == 1) {
                    smallList.add("女");
                }
                smallList.add(institution.getPresentPost());
                smallList.add(institution.getTitle() + "");
                smallList.add(institution.getUnit() + "");
                if (institution.getIsCertificate() == 0) {
                    smallList.add("是");
                } else {
                    smallList.add("否");
                }
                smallList.add(sdf.format(institution.getCertificateTime()) + "");
                smallList.add(institution.getExplains() + "");
                list.add(smallList);
            }
        }
        return list;
    }


    /**
     * 跳转奖惩添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddISelfInstitution")
    public ModelAndView addISelfInstitution(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/institution/institution_self_add");
        try {
            Long userId = Long.parseLong(SysUserUtils.getLoginSysUser(request).get("linkId"));
            Employee employee = employeeBiz.findById(userId);
            request.setAttribute("employee", employee);
        } catch (Exception e) {
            logger.error("toAddInstitution", e);
        }
        return modelAndView;
    }


    /**
     * 奖惩列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getSelfInstitutionList")
    public ModelAndView getSelfInstitutionList(HttpServletRequest request,
                                               @ModelAttribute("pagination") Pagination pagination,
                                               @ModelAttribute("institution") Institution institution) {
        ModelAndView modelAndView = new ModelAndView("/institution/institution_self_list");
        try {
            Long userId = Long.parseLong(SysUserUtils.getLoginSysUser(request).get("linkId"));
            Employee _employee = employeeBiz.findById(userId);
            if (ObjectUtils.isNotNull(_employee)) {
                institution.setEmployeeId(_employee.getId().toString());
            }
            pagination.setRequest(request);
            String whereSql = GenerateSqlUtil.getSql(institution);
            List<Institution> institutionList = institutionBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(institutionList)) {
                for (Institution institution1 : institutionList) {
                    Employee employee = employeeBiz.findById(institution1.getEmployeeId());
                    institution1.setEmployeeName(employee.getName());
                }
            }
            modelAndView.addObject("institutionList", institutionList);
            modelAndView.addObject("institution", institution);
        } catch (Exception e) {
            logger.error("getInstitutionList", e);
        }
        return modelAndView;
    }


    /**
     * 去修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateSelfInstitution")
    public ModelAndView toUpdateSelfInstitution(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/institution/institution_self_update");
        try {
            Institution institution = institutionBiz.findById(id);
            modelAndView.addObject("institution", institution);
        } catch (Exception e) {
            logger.error("toUpdateInstitution", e);
        }
        return modelAndView;
    }


}
