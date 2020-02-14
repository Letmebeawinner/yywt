package com.jiaowu.controller.starEvaluate;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.starEvaluate.StarEvaluateBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.starEvaluate.StarEvaluate;
import com.jiaowu.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by 李帅雷 on 2017/9/7.
 */
@Controller
public class StarEvaluateController extends BaseController {
    private static Logger logger = LoggerFactory
            .getLogger(StarEvaluateController.class);
    @Autowired
    private StarEvaluateBiz starEvaluateBiz;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private UserBiz userBiz;
    private static final String ADMIN_PREFIX = "/admin/jiaowu/starEvaluate";
    @InitBinder({ "starEvaluate" })
    public void initStarEvaluate(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("starEvaluate.");
    }

    /**
     * 跳转到创建星级评价的页面
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/toCreateStarEvaluate")
    public String toCreateStarEvaluate(){
        return "/admin/starEvaluate/create_starEvaluate";
    }

    /**
     * 创建星级评价
     * @param request
     * @param score
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/createStarEvaluate")
    @ResponseBody
    public Map<String,Object> createStarEvaluate(HttpServletRequest request, Integer score){
        Map<String,Object> json=null;
        try{
            Map<String,String> userMap= SysUserUtils.getLoginSysUser(request);
            if(!userMap.get("userType").equals("3")){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您没有权限进行星级评价",
                        null);
            }
            List<StarEvaluate> starEvaluateList=starEvaluateBiz.find(null," userId="+userMap.get("linkId"));
            if(starEvaluateList!=null&&starEvaluateList.size()>0){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您已进行过星级评价",
                        null);
            }
            StarEvaluate starEvaluate=new StarEvaluate();
            User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));
            Classes classes=classesBiz.findById(user.getClassId());
            starEvaluate.setClassId(classes.getId());
            starEvaluate.setClassName(classes.getName());
            starEvaluate.setUserId(user.getId());
            starEvaluate.setUserName(user.getName());
            starEvaluate.setScore(score);
            starEvaluateBiz.save(starEvaluate);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        }catch (Exception e){
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 星级评价列表页面
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/starEvaluateList")
    public String starEvaluateList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination){
        try{
            Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
            List<StarEvaluate> starEvaluateList=null;
            String whereSql=" status=1";
            if(userMap.get("userType").equals("2")){
                List<Classes> classesList=classesBiz.find(null," teacherId="+userMap.get("linkId"));
                if(classesList!=null&&classesList.size()>0){
                    whereSql+=" and classId="+classesList.get(0).getId();
                }else{
                    whereSql+=" and classId=0";
                }
            }
            StarEvaluate starEvaluate=new StarEvaluate();
            String userName=request.getParameter("userName");
            if(!StringUtils.isTrimEmpty(userName)){
                whereSql+=" and userName like '%"+userName+"%'";
                starEvaluate.setUserName(userName);
            }

            pagination.setRequest(request);
            starEvaluateList = starEvaluateBiz.find(pagination,whereSql);
            request.setAttribute("starEvaluateList",starEvaluateList);
            request.setAttribute("pagination",pagination);
            request.setAttribute("starEvaluate",starEvaluate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/starEvaluate/starEvaluateList";
    }
}
