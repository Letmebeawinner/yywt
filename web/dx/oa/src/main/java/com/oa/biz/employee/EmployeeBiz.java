package com.oa.biz.employee;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.dao.employee.EmployeeDao;
import com.oa.entity.employee.Employee;
import com.oa.entity.employee.QueryEmployee;
import com.oa.entity.sysuser.SysUser;
import com.oa.utils.AgeUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工Biz
 *
 * @author 268
 */
@Service
public class EmployeeBiz extends BaseBiz<Employee, EmployeeDao> {

    @Autowired
    private BaseHessianBiz baseHessianBiz;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 条件查询教职工
     *
     * @param queryEmployee
     */
    public List<Employee> getEmployeeList(Employee queryEmployee, Pagination pagination) {
        List<Employee> employeeList = new ArrayList<>();
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        if (!StringUtils.isTrimEmpty(queryEmployee.getName())) {
            whereSql += " and name like '%" + queryEmployee.getName() + "%'";
        }
        employeeList = this.find(pagination, whereSql);
        return employeeList;
    }

    /**
     * 条件查询教职工
     *
     * @param whereSql
     */
    public List<Employee> getEmployeeListBySql(String whereSql, Pagination pagination) {
        List<Employee> employeeList = this.find(pagination, whereSql);
        return employeeList;
    }

    /**
     * id查询教职工
     *
     * @param id
     */
    public Employee findEmployeeById(Long id) {
        Employee employee = this.findById(id);
        if (ObjectUtils.isNotNull(employee)) {
            BeanUtils.copyProperties(employee, employee);
            SysUser sysUser = baseHessianBiz.querySysUser(2, id);
            if (ObjectUtils.isNotNull(sysUser)) {
//                employee.setMobile(sysUser.getMobile());
                employee.setSysUserId(sysUser.getId());
            }
        }
        return employee;
    }

    /**
     * 编号查询教职工
     *
     * @param employeeNo
     */
    public Employee findEmployeeByEmployeeNo(String employeeNo) {
        String whereSql = " 1=1";
        whereSql += " and employeeNo=" + employeeNo;
        List<Employee> employeeList = this.find(null, whereSql);
        if (ObjectUtils.isNull(employeeList)) {
            return null;
        }
        return employeeList.get(0);
    }

    /**
     * 添加教职工
     *
     * @param queryEmployee
     */
    public String addEmployee(Employee queryEmployee) {
        Employee employee = queryEmployee;
        employee.setStatus(1);
        this.save(employee);

        SysUser sysUser = new SysUser();
        sysUser.setMobile(queryEmployee.getMobile());
        sysUser.setUserName(queryEmployee.getName());//设置用户的用户名
        sysUser.setAnotherName(queryEmployee.getName());//设置用户别名
        sysUser.setPassword("111111");//默认初始密码
        sysUser.setLinkId(employee.getId());
        sysUser.setUserType(2);
        sysUser.setStatus(0);
        String str = baseHessianBiz.addSysUser(sysUser);
        if (!StringUtils.isTrimEmpty(str) && !isNumeric(str) && !str.contains("成功")) {
            delete(employee);
        }
        return str;
    }

    /**
     * 判断字符穿是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 修改教职工
     *
     * @param queryEmployee
     */
    public String updateEmployee(QueryEmployee queryEmployee) {
        SysUser sysUser = baseHessianBiz.querySysUserById(queryEmployee.getSysUserId());
        String str = null;
        if (ObjectUtils.isNotNull(sysUser)) {
            sysUser.setId(queryEmployee.getSysUserId());
            sysUser.setUserName(queryEmployee.getName());
            sysUser.setAnotherName(queryEmployee.getName());
            sysUser.setMobile(queryEmployee.getMobile());
            str = baseHessianBiz.updateSysUser(sysUser);
        }
        if (StringUtils.isTrimEmpty(str)) {
            Employee employee = queryEmployee;
            update(employee);
        }
        return str;
    }

    /**
     * 修改教职工类别
     *
     * @param queryEmployee
     */
    public String updateEmployeeType(QueryEmployee queryEmployee) {
        SysUser sysUser = baseHessianBiz.querySysUserById(queryEmployee.getSysUserId());
        String str = baseHessianBiz.updateSysUser(sysUser);
        if (StringUtils.isTrimEmpty(str)) {
            Employee employee = queryEmployee;
            employee.setType(1L);
            update(employee);
        }
        return str;
    }


    /**
     * 删除教职工
     *
     * @param queryEmployee
     */
    public String deleteEmployee(Employee queryEmployee) {
        SysUser sysUser = baseHessianBiz.querySysUserById(queryEmployee.getSysUserId());
        if (ObjectUtils.isNotNull(sysUser)) {
            baseHessianBiz.deleteSysUserById(sysUser.getId());
        }
        Employee employee = queryEmployee;
        employee.setStatus(2);
        update(employee);
        return "";
    }


    /**
     * 批量倒入教职工
     *
     * @param myFile
     * @param request
     * @return
     * @throws Exception
     */
//    public String batchImportEmployee(MultipartFile myFile, HttpServletRequest request) throws Exception {
//
//        StringBuffer msg = new StringBuffer();
//
//        // datalist拼装List<String[]> datalist,
//        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
//        HSSFSheet sheet = wookbook.getSheetAt(0);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//
//        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
//        for (int i = 1; i < rows + 1; i++) {
//            // 读取左上端单元格
//            HSSFRow row = sheet.getRow(i);
//            // 行不为空
//            if (row != null) {
//                String name = getCellValue(row.getCell((short) 0));//姓名
//                String category = getCellValue(row.getCell((short) 1));//教职工类别
//                String sex = getCellValue(row.getCell((short) 2));//性别
//                String birthday = getCellValue(row.getCell((short) 3));//出生年月
//                String nationality = getCellValue(row.getCell((short) 4));//民族
//                String nativePlace = getCellValue(row.getCell((short) 5));//籍贯
//                String birthdayPlace = getCellValue(row.getCell((short) 6));//出生地
//                String enterPartyTime = getCellValue(row.getCell((short) 7));//入党时间
//                String workTime = getCellValue(row.getCell((short) 8));//参加工作时间
//                String identityCard = getCellValue(row.getCell((short) 9));//身份证号码
//                String profressTelnel = getCellValue(row.getCell((short) 10));//专业技术职务
//                String speciality = getCellValue(row.getCell((short) 11));//特长
//                String education = getCellValue(row.getCell((short) 12));//全日制学历学位
//                String profession = getCellValue(row.getCell((short) 13));//毕业院校
//                String jobEducation = getCellValue(row.getCell((short) 14));//在职教育学历学位
//                String jobProfession = getCellValue(row.getCell((short) 15));//在职教育毕业院校及专业
//                String presentPost = getCellValue(row.getCell((short) 16));//现任职务
//                String mobile = getCellValue(row.getCell((short) 17));//手机号
//                String department = getCellValue(row.getCell((short) 18));//部门
//                String level = getCellValue(row.getCell((short) 19));//级别
//                String appointTime = getCellValue(row.getCell((short) 20));//任现职务时间
//                String officeTime = getCellValue(row.getCell((short) 21));//任同职级时间
//                String description = getCellValue(row.getCell((short) 22));//备注
//                if (StringUtils.isTrimEmpty(identityCard) || (identityCard.length() != 18 && identityCard.length() != 15)) {
//                    msg.append("第" + i + "行身份证号填写错误;");
//                    break;
//                }
//                List<Employee> list = find(null, " status!=2 and identityCard='" + identityCard + "'");
//                if (ObjectUtils.isNotNull(list)) {
//                    msg.append("第" + i + "行身份证号重复，已跳过;");
//                    continue;
//                }
//                if (StringUtils.isTrimEmpty(nationality)) {
//                    msg.append("第" + i + "行民族填写错误;");
//                    break;
//                }
//                Employee employee = new Employee();
//                employee.setCategory(category);
//                employee.setDepartment(department);
//                employee.setPresentPost(presentPost);
//                employee.setLevel(level);
//                employee.setName(name.trim());
//                employee.setBirthdayPlace(birthdayPlace);
//                employee.setProfressTelnel(profressTelnel);
//                employee.setMobile(mobile);
//                employee.setSpeciality(speciality);
//                employee.setIdentityCard(identityCard);
//                int age = AgeUtils.getAgeByIDcard(identityCard);
//                employee.setAge(Long.valueOf(age));
//                if (sex.equals("男")) {
//                    employee.setSex(0);
//                } else if (sex.equals("女")) {
//                    employee.setSex(1);
//                } else {
//                    msg.append("第" + i + "行性别填写错误;");
//                    break;
//                }
//                if (nationality.indexOf("族") > -1) {
//                    employee.setNationality(nationality);
//                } else {
//                    employee.setNationality(nationality + "族");
//                }
//
//                employee.setNativePlace(nativePlace);
//                if (StringUtils.isTrimEmpty(birthday)) {
//                    employee.setBirthDay(null);
//                } else {
//                    employee.setBirthDay(sdf.parse(birthday.trim()));
//                }
//
//                if (StringUtils.isTrimEmpty(enterPartyTime)) {
//                    employee.setEnterPartyTime(null);
//                } else {
//                    employee.setEnterPartyTime(sdf.parse(enterPartyTime.trim()));
//                }
//
//                if (StringUtils.isTrimEmpty(workTime)) {
//                    employee.setWorkTime(null);
//                } else {
//                    employee.setWorkTime(sdf.parse(workTime.trim()));
//                }
//                employee.setEducation(education);
//                employee.setProfession(profession);
//                employee.setJobEducation(jobEducation);
//                employee.setJobProfession(jobProfession);
//                employee.setAppointTime(appointTime);
//                employee.setOfficeTime(officeTime);
//                employee.setDescription(description);
//                employee.setEmployeeType(2L);
//                employee.setStatus(1);
//                save(employee);
//            }
//        }
//        return msg.toString();
//    }


    /**
     * 批量倒入教职工同步信息
     *
     * @param myFile
     * @param request
     * @return
     * @throws Exception
     */
//    public String batchImportEmployeeSynchronization(MultipartFile myFile, HttpServletRequest request) throws Exception {
//
//        StringBuffer msg = new StringBuffer();
//
//        // datalist拼装List<String[]> datalist,
//        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
//        HSSFSheet sheet = wookbook.getSheetAt(0);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
//        for (int i = 1; i < rows + 1; i++) {
//            // 读取左上端单元格
//            HSSFRow row = sheet.getRow(i);
//            // 行不为空
//            if (row != null) {
//                String category = getCellValue(row.getCell((short) 0));//教职工类别
//                String cangong = getCellValue(row.getCell((short) 1));//参公  没有用
//                String department = getCellValue(row.getCell((short) 2));//部门
//                String presentPost = getCellValue(row.getCell((short) 3));//现任职务
//                String level = getCellValue(row.getCell((short) 4));//级别
//                String name = getCellValue(row.getCell((short) 5));//姓名
//                String sex = getCellValue(row.getCell((short) 6));//性别
//                String nationality = getCellValue(row.getCell((short) 7));//民族
//                String nativePlace = getCellValue(row.getCell((short) 8));//籍贯
//                String birthday = getCellValue(row.getCell((short) 9));//出生年月
//                String enterPartyTime = getCellValue(row.getCell((short) 10));//入党时间
//                String workTime = getCellValue(row.getCell((short) 11));//参加工作时间
//                String education = getCellValue(row.getCell((short) 12));//全日制学历学位---education
//                String profession = getCellValue(row.getCell((short) 13));//毕业院校---profession
//                String jobEducation = getCellValue(row.getCell((short) 14));//在职教育学历学位---jobEducation
//                String jobProfession = getCellValue(row.getCell((short) 15));//在职教育毕业院校及专业----jobProfession
//                String appointTime = getCellValue(row.getCell((short) 16));//任现职务时间
//                String officeTime = getCellValue(row.getCell((short) 17));//任同职级时间
//                String description = getCellValue(row.getCell((short) 18));//备注
//                String age = getCellValue(row.getCell((short) 19));//备注
//                if (StringUtils.isTrimEmpty(nationality)) {
//                    msg.append("第" + i + "行民族填写错误;");
//                    break;
//                }
//                System.out.println("----------------"+name.replace(" ", "").replace(" ", ""));
//                List<Employee> employeeList = this.find(null, " name='" + name.replace(" ", "").replace(" ", "") + "'");
//                if (employeeList.size() == 0) {
//                    msg.append("第" + i + "数据不存在;");
//                    break;
//                }
//                if (ObjectUtils.isNull(employeeList)) {
//                    System.out.println("********************************************");
//                }
//                Employee employee = new Employee();
//                employee.setId(employeeList.get(0).getId());
//                employee.setCategory(category);
//                employee.setDepartment(department);
//                employee.setPresentPost(presentPost);
//                employee.setLevel(level);
//                employee.setName(name.replace(" ", "").replace(" ", "").trim());
////                employee.setBirthdayPlace(birthdayPlace);
////                employee.setProfressTelnel(profressTelnel);
////                employee.setMobile(mobile);
////                employee.setSpeciality(speciality);
////                employee.setIdentityCard(identityCard);
////                int age = AgeUtils.getAgeByIDcard(identityCard);
//                employee.setAge(Long.valueOf(age));
//                if (sex.equals("男")) {
//                    employee.setSex(0);
//                } else if (sex.equals("女")) {
//                    employee.setSex(1);
//                } else {
//                    msg.append("第" + i + "行性别填写错误;");
//                    break;
//                }
//                if (nationality.indexOf("族") > -1) {
//                    employee.setNationality(nationality);
//                } else {
//                    employee.setNationality(nationality + "族");
//                }
//
//                employee.setNativePlace(nativePlace);
//                if (StringUtils.isTrimEmpty(birthday)) {
//                    employee.setBirthDay(null);
//                } else {
//                    employee.setBirthDay(sdf.parse(birthday.trim()));
//                }
//
//                if (StringUtils.isTrimEmpty(enterPartyTime)) {
//                    employee.setEnterPartyTime(null);
//                } else {
//                    employee.setEnterPartyTime(sdf.parse(enterPartyTime.trim()));
//                }
//
//                if (StringUtils.isTrimEmpty(workTime)) {
//                    employee.setWorkTime(null);
//                } else {
//                    employee.setWorkTime(sdf.parse(workTime.trim()));
//                }
//                employee.setEducation(education);
//                employee.setProfession(profession);
//                employee.setJobEducation(jobEducation);
//                employee.setJobProfession(jobProfession);
//                employee.setAppointTime(appointTime);
//                employee.setOfficeTime(officeTime);
//                employee.setDescription(description);
////                employee.setEmployeeType(2L);
////                employee.setStatus(1);
//                System.out.println("-----------------" + employee);
////                save(employee);
//                update(employee);
//            }
//        }
//        return msg.toString();
//    }


    /**
     * @param cell
     * @return
     * @Description 获得Hsscell内容
     */
    public String getCellValue(HSSFCell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat df = new DecimalFormat("0");
                    value = df.format(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value.trim();
    }


}
