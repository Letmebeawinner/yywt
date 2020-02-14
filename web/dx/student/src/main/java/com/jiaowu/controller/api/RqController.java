package com.jiaowu.controller.api;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.teachEvaluate.TeachEvaluateBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.ruiqu.RqEntity;
import com.jiaowu.entity.teachEvaluate.TeachEvaluate;
import com.jiaowu.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 相关的接口
 * @see RestController
 * @author YaoZhen
 * @date 04-03, 17:51, 2018.
 */
@Slf4j
@RestController
public class RqController extends BaseController {

    @Autowired private CourseArrangeBiz courseArrangeBiz;
    @Autowired private BaseHessianService baseHessianService;
    @Autowired private UserBiz userBiz;
    @Autowired private TeachEvaluateBiz teachEvaluateBiz;

    @InitBinder("teachEvaluate")
    public void initBinderTeachEvaluate(WebDataBinder binder){
        binder.setFieldDefaultPrefix("teachEvaluate.");
    }

    /**
     * 锐取录播系统预约
     */
    @RequestMapping("/jiaowu/class-schedule")
    public Map<String, Object> reCourseList() {
        List<RqEntity> rqEntities = courseArrangeBiz.findRqEntityList();
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, rqEntities);
    }

    /**
     * APP的课程评价接口
     */
    @RequestMapping("/api/jiaowu/saveEva")
    public Map<String, Object> doSaveEva(@RequestParam Long userId, TeachEvaluate teachEvaluate) {
        Map<String,Object> json;
        try{
            Map<String, String> userMap = baseHessianService.querySysUserById(userId);
            if(!userMap.get("userType").equals("3")){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "抱歉, 只有学员才能填写教学质量评估表", Collections.emptyList());
            }
            teachEvaluate.setCreateTime(new Timestamp(System.currentTimeMillis()));
            User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));

            if (validateRepeat(teachEvaluate, user)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "该课程您已填写过教学质量评估表,不能重复提交。", Collections.emptyList());
            }

            if(teachEvaluate.getTotal()>100){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请正确填写分数", Collections.emptyList());
            }
            teachEvaluate.setClassId(user.getClassId());
            teachEvaluate.setUserId(user.getId());
            teachEvaluate.setUserName(user.getName());
            teachEvaluateBiz.save(teachEvaluate);
            json = this.resultJson(ErrorCode.SUCCESS, "提交成功", Collections.emptyList());
        }catch(Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    private boolean validateRepeat(TeachEvaluate teachEvaluate, User user) {
        List<TeachEvaluate> teachEvaluateList=teachEvaluateBiz.find(null," status=1 and userId="+user.getId()+" and courseId="+teachEvaluate.getCourseId()+" and to_days(createTime)=to_days('"+teachEvaluate.getCreateTime()+"')");
        if(teachEvaluateList!=null&&teachEvaluateList.size()>0){
            return true;
        }
        return false;
    }
}
