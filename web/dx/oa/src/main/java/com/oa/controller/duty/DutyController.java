package com.oa.controller.duty;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.duty.DutyBiz;
import com.oa.entity.duty.Duty;
import com.oa.entity.sysuser.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 值班控制层
 *
 * @author lzh
 * @create 2017-01-06-11:19
 */
@Controller
@RequestMapping("/admin/oa")
public class DutyController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(DutyController.class);
    @Autowired
    private DutyBiz dutyBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    private static final String dutyList = "/duty/duty_list";

    @InitBinder("duty")
    public void initBinderDuty(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("duty.");
    }

    /**
     * @Description:跳转到值班列表
     * @author: lzh
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 15:56
     */
    @RequestMapping("/queryAllDuty")
    public String getDutyList(HttpServletRequest request) {
        List<Duty> duties = dutyBiz.findAll();
        SysUser sysUser = new SysUser();
        List<SysUser> sysUsers = baseHessianBiz.getSysUserList(sysUser);
        request.setAttribute("duties", gson.toJson(dutyBiz.getDuties(duties)));
        request.setAttribute("sysUsers", sysUsers);
        return dutyList;
    }

    /**
     * @Description:增加值班人员
     * @author: lzh
     * @Param: [duty]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:43
     */
    @RequestMapping("/addDuty")
    @ResponseBody
    public Map<String, Object> addDuty(@ModelAttribute("duty") Duty duty, @RequestParam("beginTime") String beginTime) {
        Map<String,Object> resultMap = null;
            try {
                Date dutyTime = new Date(beginTime);
                duty.setDutyTime(dutyTime);
                dutyBiz.save(duty);
                resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", duty.getId());
            } catch (Exception e) {
                logger.error("DutyController.addDuty", e);
                return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
            }
            return resultMap;
    }

    /**
     * @Description: 拖动修改值班信息
     * @author: lzh
     * @Param: [sysUserId, beginTime]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 12:02
     */
    @RequestMapping("/updateDuty")
    @ResponseBody
    public Map<String, Object> updateDuty(@RequestParam("id") Long id,
                                          @RequestParam("beginTime") String beginTime) {
            Map<String, Object> resultMap = null;
                try {
                    Duty duty = new Duty();
                    Date date = new Date(beginTime);
                    duty.setId(id);
                    duty.setDutyTime(date);
                    dutyBiz.update(duty);
                    resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
                } catch (Exception e) {
                    logger.error("DutyController.updateDuty", e);
                    resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
                }
                return resultMap;

    }

    /**
     * @Description: 删除值班信息
     * @author: lzh
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 13:59
     */
    @RequestMapping("/deleteDuty")
    @ResponseBody
    public Map<String, Object> deleteDuty(@RequestParam("id") Long id) {
            Map<String, Object> resultMap = null;
            try {
                dutyBiz.deleteById(id);
                resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
            } catch (Exception e) {
                logger.error("DutyController.deleteDuty", e);
                resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
            }
            return resultMap;

    }


}
