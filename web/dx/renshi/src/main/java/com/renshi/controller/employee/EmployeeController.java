package com.renshi.controller.employee;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.renshi.biz.common.BaseHessianBiz;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.biz.employee.FamilyBiz;
import com.renshi.common.BaseHessianService;
import com.renshi.common.DateEditor;
import com.renshi.common.EmployeeType;
import com.renshi.common.JiaoWuHessianService;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.employee.Family;
import com.renshi.entity.employee.QueryEmployee;
import com.renshi.entity.result.QueryResult;
import com.renshi.utils.AgeUtils;
import com.renshi.utils.FileExportImportUtil;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/rs")
public class EmployeeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeBiz employeeBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FamilyBiz familyBiz;

    @InitBinder("employee")
    public void initEmployee(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("employee.");
    }

    @InitBinder("queryResult")
    public void initqueryResult(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("queryResult.");
    }

    @InitBinder("queryEmployee")
    public void initQueryEmployee(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
        binder.setFieldDefaultPrefix("queryEmployee.");
    }

    @InitBinder("sysUser")
    public void initSysUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("sysUser.");
    }

    @InitBinder("family")
    public void initFamily(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("family.");
    }

    @RequestMapping("/listHolidayByEmployeeNo")
    public ModelAndView listHolidayByEmployeeNo(@ModelAttribute("pagination") Pagination pagination,
                                                @RequestParam("employeeNo") String employeeNo) {
        ModelAndView mv = new ModelAndView("/employee/holiday_list_employee");
        try {
            pagination.setRequest(request);
            Map<String, Object> json = jiaoWuHessianService.listHolidayByUserId(pagination, employeeNo);
            mv.addObject("pagination", json.get("pagination"));
            mv.addObject("holidayList", json.get("holidays"));
        } catch (Exception e) {
            logger.error("EmployeeController.listHolidayByUserId", e);
        }

        return mv;
    }


    /**
     * 跳转人员添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddEmployee")
    public ModelAndView toAddEmployee(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/employee/employee_add");
        try {

        } catch (Exception e) {
            logger.error("toAddEmployee", e);
        }
        return modelAndView;
    }

    /**
     * 添加人员
     *
     * @param request
     * @param queryEmployee
     * @return
     */
    @RequestMapping("/addEmployee")
    @ResponseBody
    public Map<String, Object> addEmployee(HttpServletRequest request, @ModelAttribute("queryEmployee") Employee queryEmployee) {
        Map<String, Object> json = null;
        try {
            employeeBiz.addEmployee(queryEmployee);
            json = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("addEmployee", e);
            json = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return json;
    }


    /**
     * 去批量添加
     *
     * @return
     */
    @RequestMapping("/toBatchEmployee")
    public ModelAndView toBatchEmployee() {
        ModelAndView modelAndView = new ModelAndView("/employee/batch_import_employee");
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     * 去批量同步
     *
     * @return
     */
    @RequestMapping("/toBatchEmployeeSynchronization")
    public ModelAndView toBatchEmployeeSynchronization() {
        ModelAndView modelAndView = new ModelAndView("/employee/batch_import_employee_synchronization");
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 批量同步教职工
     *
     * @return
     */
    @RequestMapping("/batchAddEmployeeSynchronization")
    public ModelAndView batchAddEmployeeSynchronization(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        ModelAndView modelAndView = new ModelAndView("/employee/batch_import_employee_synchronization");
        try {
            String errorInfo = employeeBiz.batchImportEmployeeSynchronization(myFile, request);
            if (StringUtils.isTrimEmpty(errorInfo)) {
                errorInfo = "导入成功";
            }
            modelAndView.addObject("errorInfo", errorInfo);
        } catch (Exception e) {
            logger.error("EmployeeController.Synchronization", e);
            modelAndView.addObject("errorInfo", "导入失败, 请核对模板格式");
        }
        return modelAndView;
    }

    /**
     * 批量添加教职工
     *
     * @return
     */
    @RequestMapping("/batchAddEmployee")
    public ModelAndView batchAddEmployee(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        ModelAndView modelAndView = new ModelAndView("/employee/batch_import_employee");
        try {
            String errorInfo = employeeBiz.batchImportEmployee(myFile, request);
            if (StringUtils.isTrimEmpty(errorInfo)) {
                errorInfo = "导入成功";
            }
            modelAndView.addObject("errorInfo", errorInfo);
        } catch (Exception e) {
            logger.error("EmployeeController.batchAddEmployee", e);
            modelAndView.addObject("errorInfo", "导入失败, 请核对模板格式");
        }
        return modelAndView;
    }

    /**
     * 删除人员
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteEmployee")
    @ResponseBody
    public Map<String, Object> deleteEmployee(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> objectMap = null;
        try {
            Employee queryEmployee = employeeBiz.findEmployeeById(id);
            employeeBiz.deleteEmployee(queryEmployee);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("addEmployee", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 人员详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/getEmployeeInfo")
    public ModelAndView getEmployeeById(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/employee/employee_info");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Employee employee = employeeBiz.findEmployeeById(id);
            if (ObjectUtils.isNotNull(employee)) {
                int age = AgeUtils.getAgeFromBirthTime(sdf.format(employee.getBirthDay()));
                employee.setAge(Long.parseLong(String.valueOf(age)));
            }
            modelAndView.addObject("employee", employee);

            List<Family> familyList = familyBiz.find(null, " employeeId=" + id);
            modelAndView.addObject("familyList", familyList);
        } catch (Exception e) {
            logger.error("getEmployeeInfo", e);
        }
        return modelAndView;
    }

    /**
     * 去修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateEmployee")
    public ModelAndView toUpdateEmployee(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/employee/employee_update");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Employee employee = employeeBiz.findEmployeeById(id);
            if (ObjectUtils.isNotNull(employee)) {
                Date birthDay = employee.getBirthDay();
                if (ObjectUtils.isNotNull(birthDay)) {
                    int age = AgeUtils.getAgeFromBirthTime(sdf.format(birthDay));
                    employee.setAge(Long.parseLong(String.valueOf(age)));
                }
            }
            modelAndView.addObject("queryEmployee", employee);
        } catch (Exception e) {
            logger.error("toUpdateEmployee", e);
        }
        return modelAndView;
    }

    /**
     * 去修改一卡通编号
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateEmployeeCardNo")
    public ModelAndView toUpdateEmployeeCardNo(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/employee/employee_cardNo_update");
        try {
            Employee employee = employeeBiz.findById(id);
            modelAndView.addObject("queryEmployee", employee);
        } catch (Exception e) {
            logger.error("toUpdateEmployee", e);
        }
        return modelAndView;
    }


    /**
     * 修改人员
     *
     * @param request
     * @param queryEmployee
     * @return
     */
    @RequestMapping("/updateEmployee")
    @ResponseBody
    public Map<String, Object> updateEmployee(HttpServletRequest request,
                                              @ModelAttribute("queryEmployee") Employee queryEmployee) {
        Map<String, Object> objectMap = null;
        try {
            employeeBiz.update(queryEmployee);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("updateEmployee", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 人员列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getEmployeeList")
    public ModelAndView getEmployeeList(HttpServletRequest request,
                                        @ModelAttribute("pagination") Pagination pagination,
                                        @ModelAttribute("employee") Employee employee) {
        ModelAndView modelAndView = new ModelAndView("/employee/employee_list");
        try {
            String whereSql = GenerateSqlUtil.getSql(employee);
            whereSql += " and status!=2 and source=1 order by sort";
            pagination.setRequest(request);
            List<Employee> employeeList = employeeBiz.find(pagination, whereSql);
            modelAndView.addObject("employeeList", employeeList);
        } catch (Exception e) {
            logger.error("getEmployeeList", e);
        }
        return modelAndView;
    }

    /**
     * 矫正所有人的年龄
     */
    @RequestMapping("syncAge")
    public void doSyncAge() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Employee> employees = employeeBiz.findAll();

        List<Employee> after = employees.stream().map(
                e ->
                {
                    Employee employee = new Employee();
                    employee.setId(e.getId());
                    employee.setAge((long) AgeUtils.getAgeFromBirthTime(sdf.format(e.getBirthDay())));
                    return employee;
                }
        ).collect(Collectors.toList());

        employeeBiz.updateBatch(after);
    }

    /**
     * 教职工人员选择列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/ajax/selectEmployeeList")
    public ModelAndView selectEmployeeList(HttpServletRequest request,
                                           @ModelAttribute("employee") Employee employee) {
        ModelAndView modelAndView = new ModelAndView("/employee/select_employee_list");
        try {
            String whereSql = GenerateSqlUtil.getSql(employee);
            whereSql += " and status!=2 order by sort";
            List<Employee> employeeList = employeeBiz.find(null, whereSql);
            modelAndView.addObject("employeeList", employeeList);
            modelAndView.addObject("employee", employee);
        } catch (Exception e) {
            logger.error("getEmployeeList", e);
        }
        return modelAndView;
    }

    /**
     * 教职工成果列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectEmployeeResults")
    public ModelAndView selectEmployeeResults(HttpServletRequest request,
                                              @ModelAttribute("queryResult") QueryResult queryResult,
                                              @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView modelAndView = new ModelAndView("/employee/select_employee_result");
        try {
            if (ObjectUtils.isNull(queryResult.getResultForm())) {
                queryResult.setResultForm(1);
            }
            if (queryResult.getResultForm() == 1) {
                queryResult.setSysUserId(queryResult.getEmployeeId());
                queryResult.setEmployeeId(null);
            }
            List<QueryResult> resultList = null;
            pagination.setRequest(request);
            modelAndView.addObject("resultList", resultList);
            modelAndView.addObject("queryResult", queryResult);
        } catch (Exception e) {
            logger.error("selectEmployeequeryResults", e);
        }
        return modelAndView;
    }

    /**
     * 查询所有教职工接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/app/queryEmployee")
    @ResponseBody
    public Map<String, Object> queryEmployee(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<Employee> employeeList = employeeBiz.getEmployeeList(new QueryEmployee(), null);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", employeeList);
        } catch (Exception e) {
            logger.error("queryEmployee", e);
            json = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return json;
    }

    /**
     * 去批量倒入
     *
     * @return
     */
    @RequestMapping("/toImportEmployee")
    public String toImportEmployee(HttpServletRequest request) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/employee/employee_import";
    }


    /**
     * 批量倒入
     *
     * @return
     */
    @RequestMapping("/importEmployee")
    @ResponseBody
    public Map<String, Object> importEmployee(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 查询家庭成员
     *
     * @param family
     * @param pagination
     * @return
     */
    @RequestMapping("/employeeFamilyList")
    public String employeeFamilyList(@ModelAttribute("family") Family family, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = GenerateSqlUtil.getSql(family);
            List<Family> familyList = familyBiz.find(pagination, whereSql);
            request.setAttribute("familyList", familyList);
            request.setAttribute("employeeId", family.getEmployeeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "employee/employee_family_list";
    }


    /**
     * 去添加家庭成员
     *
     * @param id
     * @return
     */
    @RequestMapping("/toAddFamily")
    public String toAddFamily(@RequestParam("id") Integer id) {
        request.setAttribute("employeeId", id);
        return "/employee/employee_family_add";
    }

    /**
     * 添加家庭成员
     *
     * @return
     */
    @RequestMapping("/addFamily")
    @ResponseBody
    public Map<String, Object> addFamily(@ModelAttribute("family") Family family) {
        Map<String, Object> json = null;
        try {
            familyBiz.save(family);
            json = resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 修改家庭成员
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateFamily")
    public String toUpdateFamily(@RequestParam("id") Long id) {
        try {
            Family family = familyBiz.findById(id);
            request.setAttribute("family", family);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/employee/employee_family_update";

    }

    /**
     * 修改家庭成员
     *
     * @return
     */
    @RequestMapping("/updateFamily")
    @ResponseBody
    public Map<String, Object> updateFamily(@ModelAttribute("family") Family family) {
        Map<String, Object> json = null;
        try {
            familyBiz.update(family);
            json = resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 删除家庭成员
     *
     * @return
     */
    @RequestMapping("/deleteFamily")
    @ResponseBody
    public Map<String, Object> deleteFamily(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            familyBiz.deleteById(id);
            json = resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 导出教职工
     *
     * @param request
     * @param response
     */
    @RequestMapping("/exportEmployeeExcel")
    public void exportEmployeeExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 指定文件生成路径
        String dir = request.getSession().getServletContext().getRealPath("/excelfile/employee");
        // 文件名
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        String expName = "教职工_" + dateString;
        // 表头信息
        String[] headName = {"姓名", "教职工类别", "教职工类型", "性别", "出生年月", "民族",
                "籍贯", "出生地", "入党时间", "参加工作时间", "身份证号码", "专业技术职务",
                "特长", "全日制学历学位", "全日制毕业院校及专业", "在职教育学历学位",
                "在职教育毕业院校及专业", "现任职务", "电话", "部门", "级别", "任现职务时间",
                "任同职级时间", "备注", "年龄"};

        // 拆分为一万条数据每Excel，防止内存使用太大
        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        String whereSql = "source=1 and status!=2 order by sort";
        pagination.setRequest(request);
        employeeBiz.find(pagination, whereSql);
        int num = pagination.getTotalPages();// 总页数
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
            pagination.setCurrentPage(i);
            List<Employee> employeeList = employeeBiz.find(pagination, whereSql);
            List<List<String>> list = convert(employeeList);
            File file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
            srcfile.add(file);
        }
        FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
    }

    public List<List<String>> convert(List<Employee> employeeList) {
        List<List<String>> list = new ArrayList<List<String>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (employeeList != null && employeeList.size() > 0) {
            for (Employee employee : employeeList) {
                List<String> smallList = new ArrayList<String>();
                smallList.add(employee.getName() + "");

                // 新增教职工类别
                Long employeeType = employee.getEmployeeType();
                String empTypeName = EmployeeType.empType.get(employeeType);
                smallList.add(empTypeName == null ? "" : empTypeName);
                //类别
                String category = employee.getCategory();
                smallList.add(category == null ? "" : category);
                //性别
                Integer sex = employee.getSex();
                if (sex != null) {
                    smallList.add(sex == 0 ? "男" : "女");
                } else {
                    smallList.add("--");
                }

                if (ObjectUtils.isNotNull(employee.getBirthDay())) {
                    smallList.add(sdf.format(employee.getBirthDay()) + "");
                } else {
                    smallList.add("");
                }
//                smallList.add(employee.getNationality() + "");
                if (employee.getNationality() != null) {
                    if (employee.getNationality().indexOf("族") <= -1) {
                        smallList.add(employee.getNationality() + "族");
                    } else {
                        smallList.add(employee.getNationality());
                    }
                } else {
                    smallList.add("");
                }
                smallList.add(employee.getNativePlace());
                smallList.add(employee.getBirthdayPlace() == null ? "" : employee.getBirthdayPlace());
                smallList.add(employee.getEnterPartyTime() == null ? "" : sdf.format(employee.getEnterPartyTime()));
                if (ObjectUtils.isNotNull(employee.getWorkTime())) {
                    smallList.add(sdf.format(employee.getWorkTime()) + "");
                } else {
                    smallList.add("");
                }
                smallList.add(employee.getIdentityCard() == null ? "" : employee.getIdentityCard());
                smallList.add(employee.getProfressTelnel() == null ? "" : employee.getProfressTelnel());
                smallList.add(employee.getSpeciality() == null ? "" : employee.getSpeciality());
                smallList.add(employee.getEducation() + "");
                smallList.add(employee.getProfession() + "");
                smallList.add(employee.getJobEducation() + "");
                smallList.add(employee.getJobProfession() + "");
                smallList.add(employee.getPresentPost() + "");
                smallList.add(employee.getMobile() == null ? "" : employee.getMobile());
                smallList.add(employee.getDepartment() + "");
                smallList.add(employee.getLevel() + "");
                smallList.add(employee.getAppointTime() + "");
                smallList.add(employee.getOfficeTime() + "");
                smallList.add(employee.getDescription() + "");
                smallList.add(employee.getAge() == null ? "" : String.valueOf(employee.getAge()));
                list.add(smallList);
            }
        }
        return list;
    }

    /**
     * 查询个人基本信息
     *
     * @return
     */
    @RequestMapping("/employeeInfo")
    public ModelAndView employeeInfo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/employee/employee_self_info");
        try {
            Long userId = Long.parseLong(SysUserUtils.getLoginSysUser(request).get("linkId"));
            Employee employee = employeeBiz.findById(userId);
            request.setAttribute("employee", employee);
            List<Map<String, String>> baseUserList = baseHessianService.querySysUser(2, userId);
            if (baseUserList != null && baseUserList.size() > 0) {
                request.setAttribute("email", baseUserList.get(0).get("email"));
                request.setAttribute("mobile", baseUserList.get(0).get("mobile"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 修改基本信息
     *
     * @param request
     * @return
     */

    @RequestMapping("/toUpdateEmployeeInfo")
    public ModelAndView updateEmployeeInfo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/employee/employee_info_update");
        try {
            Long userId = Long.parseLong(SysUserUtils.getLoginSysUser(request).get("linkId"));
            Employee queryEmployee = employeeBiz.findById(userId);
            request.setAttribute("queryEmployee", queryEmployee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     * 修改教职工个人信息
     *
     * @return
     */
    @RequestMapping("/updateEmployeeInfo")
    @ResponseBody
    public Map<String, Object> updateEmployeeInfo(@ModelAttribute("queryEmployee") Employee queryEmployee) {
        Map<String, Object> json = null;
        try {
            employeeBiz.update(queryEmployee);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 计算年龄
     *
     * @return
     */
    @RequestMapping("/checkAge")
    @ResponseBody
    public Map<String, Object> checkAge(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            String birthday = request.getParameter("birthDay");
            int userAge = AgeUtils.getAgeFromBirthTime(birthday);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, userAge);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
