package com.yicardtong.controller;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.yicardtong.biz.work.WorkSourceService;
import com.yicardtong.entity.attend.WorkLeaveInfo;
import com.yicardtong.entity.attend.WorkLeaveInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by caichenglong on 2017/10/16.
 */
@Controller
public class AttendController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AttendController.class);

    private static final String ADMIN_PREFIX = "/yikatong";
    @InitBinder("workLeaveInfoEntity")
    public void initBinderWorkLeaveInfoEntity(WebDataBinder binder){
        binder.setFieldDefaultPrefix("workLeaveInfoEntity.");
    }

    @Autowired
    private WorkSourceService workSourceService;

    /**
     * @param request
     * @return java.lang.String
     * @Description 获取考勤原始数据
     * @author CCL
     */
    @RequestMapping(ADMIN_PREFIX + "/showAttendList")
    @ResponseBody
    public Map<String, Object> showAttendList(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<Map<String, String>> consume_costValueList = workSourceService.queryWorkSourceList("select * from Work_Source");

            json = this.resultJson(ErrorCode.SUCCESS, "", consume_costValueList);
        } catch (Exception e) {
            logger.info("CourseController.toCreateCourse", e);
        }
        return json;
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 获取考勤原始数据
     * @author CCL
     */
    @RequestMapping(ADMIN_PREFIX + "/showAttendListWithCondition")
    @ResponseBody
    public Map<String, Object> showAttendListWithCondition(HttpServletRequest request,String whereSql) {
        Map<String, Object> json = null;
        try {
            List<Map<String, String>> consume_costValueList = workSourceService.queryWorkSourceList("select * from Work_Source"+whereSql);

            json = this.resultJson(ErrorCode.SUCCESS, "", consume_costValueList);
        } catch (Exception e) {
            logger.info("AttendController.showAttendListWithCondition", e);
        }
        return json;
    }


    /**
     * 获取考勤明细列表
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/showWorkDetailList")
    @ResponseBody
    public Map<String, Object> showWorkDetailList(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<Map<String, String>> workDetailList = workSourceService.queryWorkDayDetailList("select * from Work_Day_Detail");
            json = this.resultJson(ErrorCode.SUCCESS, "", workDetailList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 获取每天刷卡数据
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/workDayDataList")
    @ResponseBody
    public Map<String, Object> workDayDataList(HttpServletRequest request, @RequestParam(value="whereSql",required =false)String whereSql) {
        Map<String, Object> json = null;
        try {
            String sql=null;
            if(whereSql!=null&&!whereSql.equals("")){
                sql="select * from Work_Source" +whereSql;
            }else{
                sql="select * from Work_Source";
            }
            List<Map<String, String>> workDayDataList = workSourceService.queryWorkDayDataList(sql);
            json = this.resultJson(ErrorCode.SUCCESS, "", workDayDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 查询请假登记表
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/workLeaveInfoList")
    @ResponseBody
    public Map<String, Object> workLiveInfoList(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<Map<String, String>> workDetailList = workSourceService.queryWorkLeaveInfoList("select * from Work_Leave_Info");
            json = this.resultJson(ErrorCode.SUCCESS, "", workDetailList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    @RequestMapping(ADMIN_PREFIX+"/addWorkLeaveInfo")
    @ResponseBody
    public String addWorkLeaveInfo(WorkLeaveInfoEntity workLeaveInfoEntity){
        Map<String, Object> json = null;
        try{
            int result=workSourceService.addWorkLeaveInfo(workLeaveInfoEntity);
            /*if(result>0){
                json = this.resultJson(ErrorCode.SUCCESS, "", result);
            }else{
                json = this.resultJson(ErrorCode.ERROR_DATA, "", result);
            }*/
            return "1";
        }catch (Exception e){
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return "0";
    }
}
