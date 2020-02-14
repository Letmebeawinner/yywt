package com.jiaowu.controller.twentyoneEvaluate;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.twentyoneEvaluate.TwentyoneEvaluateBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.twentyoneEvaluate.TwentyoneEvaluate;
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
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by 李帅雷 on 2017/8/28.
 */
@Controller
public class TwentyoneEvaluateController extends BaseController {
    private static Logger logger = LoggerFactory
            .getLogger(TwentyoneEvaluateController.class);
    private static final String ADMIN_PREFIX = "/admin/jiaowu/twentyoneEvaluate";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/twentyoneEvaluate";
    @InitBinder("twentyoneEvaluate")
    public void initBinderTwentyoneEvaluate(WebDataBinder binder){
        binder.setFieldDefaultPrefix("twentyoneEvaluate.");
    }
    @Autowired
    private TwentyoneEvaluateBiz twentyoneEvaluateBiz;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private UserBiz userBiz;
    /**
     * 跳转到填写21项测评的页面
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/toCreateTwentyoneEvaluate")
    public String toCreateTwentyoneEvaluate(HttpServletRequest request){
        try{
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if(userMap.get("userType").equals("3")){
                User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));
                Classes classes=classesBiz.findById(user.getClassId());
                request.setAttribute("classes",classes);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/twentyoneEvaluate/create_twentyoneEvaluate";
    }

    /**
     * 添加21项测评记录
     * @param twentyoneEvaluate
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/createTwentyoneEvaluate")
    @ResponseBody
    public Map<String,Object> createTwentyoneEvaluate(HttpServletRequest request,TwentyoneEvaluate twentyoneEvaluate){
        Map<String,Object> json=null;
        try{
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if(!userMap.get("userType").equals("3")){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您没有权限填写21项测评",
                        null);
            }
            twentyoneEvaluate.setCreateTime(new Timestamp(System.currentTimeMillis()));
            User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));
            List<TwentyoneEvaluate> twentyoneEvaluateList=twentyoneEvaluateBiz.find(null," status=1 and userId="+user.getId());
            if(twentyoneEvaluateList!=null&&twentyoneEvaluateList.size()>0){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您已填写过21项测评,不能重复提交。",
                        null);
            }
            String errorInfo=validateTwentyoneEvaluate(twentyoneEvaluate);
            if(!StringUtils.isTrimEmpty(errorInfo)){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,errorInfo,
                        null);
            }
            twentyoneEvaluate.setClassId(user.getClassId());
            twentyoneEvaluate.setUserId(user.getId());
            twentyoneEvaluate.setUserName(user.getName());
            twentyoneEvaluateBiz.save(twentyoneEvaluate);
            json = this.resultJson(ErrorCode.SUCCESS, "提交成功",
                    null);
        }catch (Exception e){
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 验证21项测评
     * @param twentyoneEvaluate
     * @return
     */
    public String validateTwentyoneEvaluate(TwentyoneEvaluate twentyoneEvaluate){
        if(twentyoneEvaluate.getIndex1()==null||twentyoneEvaluate.getIndex1().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex2()==null||twentyoneEvaluate.getIndex2().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex3()==null||twentyoneEvaluate.getIndex3().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex4()==null||twentyoneEvaluate.getIndex4().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex5()==null||twentyoneEvaluate.getIndex5().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex6()==null||twentyoneEvaluate.getIndex6().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex7()==null||twentyoneEvaluate.getIndex7().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex8()==null||twentyoneEvaluate.getIndex8().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex9()==null||twentyoneEvaluate.getIndex9().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex10()==null||twentyoneEvaluate.getIndex10().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex11()==null||twentyoneEvaluate.getIndex11().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex12()==null||twentyoneEvaluate.getIndex12().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex13()==null||twentyoneEvaluate.getIndex13().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex14()==null||twentyoneEvaluate.getIndex14().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex15()==null||twentyoneEvaluate.getIndex15().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex16()==null||twentyoneEvaluate.getIndex16().equals("0")){
            return "请完整填写21项测评";
        }

        if(twentyoneEvaluate.getIndex17()==null||twentyoneEvaluate.getIndex17().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex18()==null||twentyoneEvaluate.getIndex18().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex19()==null||twentyoneEvaluate.getIndex19().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex20()==null||twentyoneEvaluate.getIndex20().equals("0")){
            return "请完整填写21项测评";
        }
        if(twentyoneEvaluate.getIndex21()==null||twentyoneEvaluate.getIndex21().equals("0")){
            return "请完整填写21项测评";
        }
        if(StringUtils.isTrimEmpty(twentyoneEvaluate.getGain())){
            return "请完整填写21项测评";
        }
        if(StringUtils.isTrimEmpty(twentyoneEvaluate.getAdvice())){
            return "请完整填写21项测评";
        }
        return null;
    }

    /**
     * 21项测评列表
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/twentyoneEvaluateList")
    public String twentyoneEvaluateList(HttpServletRequest request,
                                        @ModelAttribute("pagination") Pagination pagination){
        try{
            String whereSql=" status=1";
            TwentyoneEvaluate twentyoneEvaluate = new TwentyoneEvaluate();
            String userName = request.getParameter("userName");
            if(!StringUtils.isTrimEmpty(userName)){
                whereSql+=" and userName like '%"+userName+"%'";
                twentyoneEvaluate.setUserName(userName);
            }
            pagination.setRequest(request);
            List<TwentyoneEvaluate> twentyoneEvaluateList = twentyoneEvaluateBiz.find(pagination,whereSql);
            request.setAttribute("twentyoneEvaluateList", twentyoneEvaluateList);
            request.setAttribute("twentyoneEvaluate", twentyoneEvaluate);
            request.setAttribute("pagination",pagination);
        }catch(Exception e){
            e.printStackTrace();
        }

        return "/admin/twentyoneEvaluate/twentyoneEvaluate_list";
    }

    /**
     * 查询某21测评
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/queryTwentyoneEvaluate")
    public String queryTwentyoneEvaluate(HttpServletRequest request,Long id){
        try{
            TwentyoneEvaluate twentyoneEvaluate=twentyoneEvaluateBiz.findById(id);
            request.setAttribute("twentyoneEvaluate",twentyoneEvaluate);
            Classes classes=classesBiz.findById(twentyoneEvaluate.getClassId());
            request.setAttribute("classes",classes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/twentyoneEvaluate/queryTwentyoneEvaluate";
    }
    /**
     * 我的综合质量评估测评
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/myTwentyoneEvaluateList")
    public String myTwentyoneEvaluateList(HttpServletRequest request,TwentyoneEvaluate twentyoneEvaluate,
                                          @ModelAttribute("pagination") Pagination pagination){
        try{
            String whereSql=" status=1";
            Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
            pagination.setRequest(request);
            List<TwentyoneEvaluate> twentyoneEvaluateList = twentyoneEvaluateBiz.find(pagination,whereSql+" and userId ="+userMap.get("linkId"));
            request.setAttribute("twentyoneEvaluateList", twentyoneEvaluateList);
            request.setAttribute("twentyoneEvaluate", twentyoneEvaluate);
            request.setAttribute("pagination",pagination);
        }catch(Exception e){
            e.printStackTrace();
        }

        return "/admin/twentyoneEvaluate/twentyoneEvaluate_list";
    }
}
