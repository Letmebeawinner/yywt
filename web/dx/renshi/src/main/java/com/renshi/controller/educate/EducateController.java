package com.renshi.controller.educate;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.renshi.biz.educate.EducateBiz;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.entity.educate.Educate;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.employee.QueryEmployee;
import com.renshi.utils.FileExportImportUtil;
import com.renshi.utils.GenerateSqlUtil;
import org.omg.CORBA.OBJ_ADAPTER;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/rs")
public class EducateController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(EducateController.class);

    @InitBinder("educate")
    public void initEducate(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("educate.");
    }

    @InitBinder("queryEmployee")
    public void initQueryEmployee(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("queryEmployee.");
    }

    @InitBinder("educateEmployee")
    public void initEducateEmployee(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("educateEmployee.");
    }

    @Autowired
    private EducateBiz educateBiz;
    @Autowired
    private EmployeeBiz employeeBiz;

    /**
     * 跳转培训添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddEducate")
    public ModelAndView toAddEducate(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/educate/educate_add");
        try {
        } catch (Exception e) {
            logger.error("toAddEducate", e);
        }
        return modelAndView;
    }

    /**
     * 添加培训
     *
     * @param request
     * @param educate
     * @return
     */
    @RequestMapping("/addEducate")
    @ResponseBody
    public Map<String, Object> addEducate(HttpServletRequest request, @ModelAttribute("educate") Educate educate ,
                                          @RequestParam(value = "employeeIds", required = false) String employeeIds) {
        Map<String, Object> objectMap = null;
        try {
            if(ObjectUtils.isNotNull(employeeIds)){
                String[] employeeIdArr=employeeIds.split(",");
                for(String employeeId:employeeIdArr){
                    educate.setId(null);
                    educate.setEmployeeId(Long.valueOf(employeeId));
                    educateBiz.save(educate);
                }
                objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", educate);
            }else {
                objectMap = this.resultJson(ErrorCode.ERROR_PARAMETER, "请选择教职工", null);
            }
        } catch (Exception e) {
            logger.error("addEducate", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 删除培训
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteEducate")
    @ResponseBody
    public Map<String, Object> addEducate(HttpServletRequest request) {
        Map<String, Object> objectMap = null;
        try {
            String id=request.getParameter("id");
            educateBiz.deleteById(Long.parseLong(id));
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", "");
        } catch (Exception e) {
            logger.error("addEducate", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 培训详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/getEducateInfo")
    public ModelAndView getEducateById(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/educate/educate_info");
        try {
            Educate educate = educateBiz.findById(id);
            if(ObjectUtils.isNotNull(educate)){
                Employee employee = employeeBiz.findById(educate.getEmployeeId());
                educate.setSex(employee.getSex());
                educate.setEmployeeName(employee.getName());
                educate.setBirthday(employee.getBirthDay());
                educate.setEnterPartyTime(employee.getEnterPartyTime());
                educate.setPresentPost(employee.getPresentPost());
                educate.setWorkTime(employee.getWorkTime());
            }
            modelAndView.addObject("educate", educate);
        } catch (Exception e) {
            logger.error("getEducateInfo", e);
        }
        return modelAndView;
    }

    /**
     * 去修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateEducate")
    public ModelAndView toUpdateEducate(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/educate/educate_update");
        try {
            Educate educate = educateBiz.findById(id);
            educate.setEmployeeName(employeeBiz.findById(educate.getEmployeeId()).getName());
            modelAndView.addObject("educate", educate);
        } catch (Exception e) {
            logger.error("toUpdateEducate", e);
        }
        return modelAndView;
    }

    /**
     * 修改培训
     *
     * @param request
     * @param educate
     * @return
     */
    @RequestMapping("/updateEducate")
    @ResponseBody
    public Map<String, Object> updateEducate(HttpServletRequest request, @ModelAttribute("educate") Educate educate) {
        Map<String, Object> objectMap = null;
        try {
            educateBiz.update(educate);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", educate);
        } catch (Exception e) {
            logger.error("updateEducate", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", educate);
        }
        return objectMap;
    }

    /**
     * 培训列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getEducateList")
    public ModelAndView getEducateList(HttpServletRequest request,
                                       @ModelAttribute("pagination") Pagination pagination,
                                       @ModelAttribute("educate") Educate educate) {
        ModelAndView modelAndView = new ModelAndView("/educate/educate_list");
        try {
            pagination.setRequest(request);
            String whereSql = GenerateSqlUtil.getSql(educate);
            List<Educate> educateList = educateBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(educateList)) {
                for (Educate educate1 : educateList) {
                    Employee employee = employeeBiz.findById(educate1.getEmployeeId());
                    if(ObjectUtils.isNotNull(employee)){
                        educate1.setSex(employee.getSex());
                        educate1.setEmployeeName(employee.getName());
                        educate1.setBirthday(employee.getBirthDay());
                        educate1.setEnterPartyTime(employee.getEnterPartyTime());
                        educate1.setPresentPost(employee.getPresentPost());
                        educate1.setWorkTime(employee.getWorkTime());
                    }
                }
            }
            modelAndView.addObject("educateList", educateList);
            modelAndView.addObject("educate", educate);
        } catch (Exception e) {
            logger.error("getEducateList", e);
        }
        return modelAndView;
    }

    /**
     * 参训培训集合
     *
     * @param request
     * @return
     */
    @RequestMapping("/getEducateEmployeeList")
    public ModelAndView getEducateEmployeeList(HttpServletRequest request,
                                               @ModelAttribute("pagination") Pagination pagination,
                                               @ModelAttribute("queryEmployee") QueryEmployee queryEmployee
    ) {
        ModelAndView modelAndView = new ModelAndView("/educate/educate_employee_list");
        try {
            pagination.setRequest(request);
            List<Employee> employeeList = employeeBiz.getEmployeeList(queryEmployee, pagination);
            modelAndView.addObject("employeeList", employeeList);
        } catch (Exception e) {
            logger.error("toUpdateEducate", e);
        }
        return modelAndView;
    }


    /**
     * 导出奖惩记录
     * @param request
     * @param response
     */
    @RequestMapping("/exportEducateExcel")
    public void exportEducateExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 指定文件生成路径
        String dir = request.getSession().getServletContext().getRealPath("/excelfile/educate");
        // 文件名
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        String expName = "培训_" + dateString;
        // 表头信息
        String[] headName = {"姓名","性别","职务", "职称", "培训名称", "培训时间", "培训单位"};

        // 拆分为一万条数据每Excel，防止内存使用太大
        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        String whereSql=" 1=1";
        pagination.setRequest(request);
        educateBiz.find(pagination,whereSql);
        int num = pagination.getTotalPages();// 总页数
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
            pagination.setCurrentPage(i);
            List<Educate> educateList = educateBiz.find(pagination,whereSql);
            if (ObjectUtils.isNotNull(educateBiz)) {
                for (Educate educate : educateList) {
                    Employee employee = employeeBiz.findById(educate.getEmployeeId());
                    educate.setEmployeeName(employee.getName());
                    educate.setSex(employee.getSex());
                    educate.setPresentPost(employee.getPresentPost());
                }
            }
            List<List<String>> list=convert(educateList);
            File file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
            srcfile.add(file);
        }
        FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
    }

    public List<List<String>> convert(List<Educate> educateList){
        List<List<String>> list=new ArrayList<List<String>>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(educateList!=null&&educateList.size()>0){
            for(Educate educate:educateList){
                List<String> smallList=new ArrayList<String>();
                smallList.add(educate.getEmployeeName()+"");
                if(educate.getSex()==0){
                    smallList.add("男");
                }
                if(educate.getSex()==1){
                    smallList.add("女");
                }
                smallList.add(educate.getPresentPost());
                smallList.add(educate.getTechnical());
                smallList.add(educate.getName()+"");
                smallList.add(sdf.format(educate.getBeginTime())+"至"+(sdf.format(educate.getEndTime()))+"");
                smallList.add(educate.getTrainingUnit()+"");
                list.add(smallList);
            }
        }
        return list;
    }


    /**
     * 跳转培训添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddSelfEducate")
    public ModelAndView toAddSelfEducate(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/educate/educate_self_add");
        try {
            Long userId = Long.parseLong(SysUserUtils.getLoginSysUser(request).get("linkId"));
            Employee employee = employeeBiz.findById(userId);
            request.setAttribute("employee", employee);
        } catch (Exception e) {
            logger.error("toAddEducate", e);
        }
        return modelAndView;
    }


    /**
     * 培训列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getSelfEducateList")
    public ModelAndView getSelfEducateList(HttpServletRequest request,
                                       @ModelAttribute("pagination") Pagination pagination,
                                       @ModelAttribute("educate") Educate educate) {
        ModelAndView modelAndView = new ModelAndView("/educate/educate_self_list");
        try {
            Long userId = Long.parseLong(SysUserUtils.getLoginSysUser(request).get("linkId"));
            Employee _employee = employeeBiz.findById(userId);
            if(ObjectUtils.isNotNull(_employee)){
                educate.setEmployeeId(_employee.getId());
            }
            pagination.setRequest(request);
            String whereSql = GenerateSqlUtil.getSql(educate);
            List<Educate> educateList = educateBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(educateList)) {
                for (Educate educate1 : educateList) {
                    Employee employee = employeeBiz.findById(educate1.getEmployeeId());
                    educate1.setSex(employee.getSex());
                    educate1.setEmployeeName(employee.getName());
                    educate1.setBirthday(employee.getBirthDay());
                    educate1.setEnterPartyTime(employee.getEnterPartyTime());
                    educate1.setPresentPost(employee.getPresentPost());
                    educate1.setWorkTime(employee.getWorkTime());
                }
            }

            modelAndView.addObject("educateList", educateList);
            modelAndView.addObject("educate", educate);
        } catch (Exception e) {
            logger.error("getEducateList", e);
        }
        return modelAndView;
    }


    /**
     * 去修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateSelfEducate")
    public ModelAndView toUpdateSelfEducate(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/educate/educate_self_update");
        try {
            Educate educate = educateBiz.findById(id);
            modelAndView.addObject("educate", educate);
        } catch (Exception e) {
            logger.error("toUpdateEducate", e);
        }
        return modelAndView;
    }



}
