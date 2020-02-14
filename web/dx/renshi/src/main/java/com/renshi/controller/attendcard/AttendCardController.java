package com.renshi.controller.attendcard;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.gson.reflect.TypeToken;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.common.JiaoWuHessianService;
import com.renshi.common.WorkSourceService;
import com.renshi.entity.attendance.WorkDayUser;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.workdaydata.WorkDayDataNode;
import com.renshi.entity.workdaydata.WorkDayDataVO;
import com.renshi.utils.Stringutils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 查询所有教职工的打卡
 *
 * @author YaoZhen
 * @date 04-10, 16:35, 2018.
 */
@Slf4j
@Controller
public class AttendCardController extends BaseController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private WorkSourceService workSourceService;
    @Autowired
    private EmployeeBiz employeeBiz;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;



    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @InitBinder("userCondition")
    public void initUserCondition(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("userCondition.");
    }



    /**
     * 查询所有教师的考勤卡号
     */
    @RequestMapping("/admin/rs/queryAllTeacherAttData")
    public ModelAndView queryAllTeacherAttData(@ModelAttribute("pagination") Pagination pagination, @ModelAttribute("userCondition") WorkDayUser workDayUser) {
        ModelAndView mv = new ModelAndView("/attendrecord/list_attendance_record");

        try {
            /*// 查询所有教职工
            List<Employee> employees = employeeBiz.findAll();
            // 过滤所有教职工的卡号
            List<String> strings = employees.stream()
                    .map(Employee::getName).distinct()
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());

            // 拼接SQL语句
            // 根据考勤卡号 查询所有的考勤记录
            String userNames = Stringutils.listToString(strings);
*/
            // 线下测试
            // cardNOs = "'14153346', 'E6DB1FC9'";
          /*  if (StringUtils.isNotBlank(userNames)) {*/
                // 按照每个学员分组 查询. 把卡号传到一卡通模块, 接收返回值
               /* String rsJson = workSourceService.queryAllWordData(cardNOs, "2000-01-01", "2099-01-01");
                List<WorkDayDataVO> dayDataVOS = gson.fromJson(rsJson,
                        new TypeToken<List<WorkDayDataVO>>() {
                        }.getType());*/
                StringBuilder whereSql = new StringBuilder(" userName is not null and userName!='' and type=2");
               /* whereSql.append(" and userName in").append("(" + userNames + ")");*/
                SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(workDayUser.getUserName())) {
                    whereSql.append(" and userName like '%").append(workDayUser.getUserName()).append("%'");
                }
                if (ObjectUtils.isNotNull(workDayUser.getStartTime())) {
                    whereSql.append(" and  TO_DAYS(workDate) >= ").append("TO_DAYS('").append(bartDateFormat.format(workDayUser.getStartTime()).trim()).append("')");
                }
                if (ObjectUtils.isNotNull(workDayUser.getEndTime())) {
                    whereSql.append(" and  TO_DAYS(workDate) <= ").append("TO_DAYS('").append(bartDateFormat.format(workDayUser.getEndTime()).trim()).append("')");
                }
                whereSql.append(" order by TO_DAYS(workDate) desc");
                Map<String, String> dayData = jiaoWuHessianService.listUserWorkDayData(pagination, whereSql.toString());
                String listJson = dayData.get("userWorkDayDataList");
                List<WorkDayUser> workDayUserList = gson.fromJson(listJson, new TypeToken<List<WorkDayUser>>() {
                }.getType());
                mv.addObject("userWorkDayDataList", workDayUserList);
                String paginationJson = dayData.get("pagination");
                pagination = gson.fromJson(paginationJson, Pagination.class);
                // 环回参数
                pagination.setRequest(request);
                mv.addObject("pagination", pagination);
        } catch (Exception e) {
            log.error("AttendCardController.queryAllTeacherAttData", e);
            return new ModelAndView(setErrorPath(request, new Exception("系统内部错误, 请稍后再试")));
        }
        return mv;
    }

    /**
     * 查询教职工的考勤打卡记录
     */
    @RequestMapping("/admin/rs/queryTeacherAttendRecord")
    public ModelAndView queryTeacherAttendRecord() {
        ModelAndView mv = new ModelAndView("/attendrecord/list_attendance_record");

        try {
            // 获取当前登录账号
            Map<String, String> mapUser = SysUserUtils.getLoginSysUser(request);
            // 如果账号是教职工
            if (!"2".equals(mapUser.get("userType"))) {
                return new ModelAndView("redirect:" + BaseCommonConstants.basePath + "/admin/notAuthority.json");
            }
            // 查询人事的卡号
            Employee employee = employeeBiz.findById(Long.valueOf(mapUser.get("linkId")));
            String cardNOs = employee.getCardNo();
            // 查询考勤记录
            if (StringUtils.isNotBlank(cardNOs)) {
                cardNOs = "'" + cardNOs + "'";
                String rsJson = workSourceService.queryAllWordData(cardNOs, "2000-01-01", "2099-01-01");
                List<WorkDayDataVO> dayDataVOS = gson.fromJson(rsJson,
                        new TypeToken<List<WorkDayDataVO>>() {
                        }.getType());
                mv.addObject("dayDataVOS", dayDataVOS);
            }
        } catch (Exception e) {
            log.error("AttendRecordController.queryTeacherAttendRecord", e);
            return new ModelAndView(setErrorPath(request, new Exception("系统内部错误, 请稍后再试")));
        }

        return mv;
    }


    /**
     * 解析子查询
     *
     * @param node 二级json数组
     * @return 打卡记录
     */
    @RequestMapping("/admin/rs/viewRecord")
    public ModelAndView queryViewRecord(@RequestParam("node") String node) {
        ModelAndView mv = new ModelAndView("/attendrecord/detail_attendance_record");

        try {
            List<WorkDayDataNode> nodes = gson.fromJson(node,
                    new TypeToken<List<WorkDayDataNode>>() {
                    }.getType());

            mv.addObject("nodes", nodes);
        } catch (Exception e) {
            log.error("AttendRecordController.doViewRecord", e);
        }
        return mv;
    }

}
