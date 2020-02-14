package com.oa.controller.kaoqin;

import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.common.JiaoWuHessianService;
import com.oa.biz.kaoqin.CheckWorkAttendanceBiz;
import com.oa.controller.index.IndexController;
import com.oa.entity.department.DepartMent;
import com.oa.entity.kaoqin.CheckWorkAttendance;
import com.oa.entity.sysuser.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangdong.chang on 2018/5/16 0016.
 */
@Controller
@RequestMapping("/admin/oa")
public class CheckWorkAttendanceController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    private static final String initAttendanceSheet = "/kaoqin/oa_kaoqin_attendance_import";//导入考勤
    private static final String queryAttendanceList = "/kaoqin/oa_kaoqing_attendance_list";//考勤列表
    @Autowired
    private CheckWorkAttendanceBiz checkWorkAttendanceBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    
    @InitBinder({"checkWorkAttendance"})
    public void initCheckWorkAttendance(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("checkWorkAttendance.");
    }


    //初始化导入考勤页面
    @RequestMapping("/initAttendanceSheet")
    public String initAttendanceSheet(HttpServletRequest request) {
        List<DepartMent> departmentList = baseHessianBiz.getDepartMentList(new DepartMent());
        request.setAttribute("departmentList", departmentList);
        return initAttendanceSheet;
    }

    /**
     * 导入考勤表
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 17:47
     * @return:
     */
    @RequestMapping("/import/attendanceSheet")
    public String importAttendanceSheet(@RequestParam("myFile") MultipartFile myfile,
                                        @RequestParam("departmentId") Long departmentId,
                                        @RequestParam("attendanceName") String attendanceName) {
        try {
            checkWorkAttendanceBiz.importAttendanceSheet(myfile,departmentId,attendanceName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/oa/queryAttendanceList.json";
    }

    /**
     * 查询考勤统计列表
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 13:39
     * @return:
     */
    @RequestMapping("/queryAttendanceList")
    public String queryAttendanceList(HttpServletRequest request, @ModelAttribute("checkWorkAttendance") CheckWorkAttendance checkWorkAttendance) {
        try {
            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            //判断是否是人事处的
            List<Long> roleByUsers = baseHessianBiz.getUserRoleByUserId(sysUserId);
            SysUser sysUser = new SysUser();
            sysUser.setId(sysUserId);
            List<SysUser> sysUserList = baseHessianBiz.getSysUserList(sysUser);
            roleByUsers.forEach(e->{
                if(46==e){
                        checkWorkAttendance.setRoleId(e);
                    if(ObjectUtils.isNotNull(sysUserList)){
                        checkWorkAttendance.setDepartmentId(sysUserList.get(0).getDepartmentId()==null?0:sysUserList.get(0).getDepartmentId());
                    }
                }
            });
            List<DepartMent> departmentList = baseHessianBiz.getDepartMentList(new DepartMent());
            request.setAttribute("departmentList", departmentList);
            Map<String, Object> dataMap = checkWorkAttendanceBiz.queryAttendanceList(checkWorkAttendance);
            request.setAttribute("checkWorkAttendances", dataMap.get("checkWorkAttendances"));
            request.setAttribute("dataMap", dataMap.get("map"));
            request.setAttribute("checkWorkAttendance", checkWorkAttendance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryAttendanceList;
    }
}
