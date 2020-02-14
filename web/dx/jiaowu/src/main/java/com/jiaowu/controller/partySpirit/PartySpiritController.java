package com.jiaowu.controller.partySpirit;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.partySpirit.PartySpiritBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.partySpirit.PartySpirit;
import com.jiaowu.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 李帅雷 on 2017/8/9.
 */
@Controller
public class PartySpiritController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(PartySpiritController.class);
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }
    @InitBinder("partySpirit")
    public void initBinderPartySpirit(WebDataBinder binder){
        binder.setFieldDefaultPrefix("partySpirit.");
    }
    private static final String ADMIN_PREFIX="/admin/jiaowu/partySpirit";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/partySpirit";
    @Autowired
    private PartySpiritBiz partySpiritBiz;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private UserBiz userBiz;

    /**
     * 跳转到创建党性锻炼成绩的页面
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/toCreatePartySpirit")
    public String toCreatePartySpirit(){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/partySpirit/create_partySpirit";
    }

    /**
     * 创建党性锻炼成绩
     * @param partySpirit
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/createPartySpirit")
    @ResponseBody
    public Map<String,Object> createPartySpirit(@ModelAttribute("partySpirit")PartySpirit partySpirit){
        Map<String,Object> json=null;
        try{
            if(partySpirit.getUserId()==null||partySpirit.getUserId().equals(0L)){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "学员不能为空", null);
            }
            String errorInfo=validatePartySpirit(partySpirit);
            if(!StringUtils.isTrimEmpty(errorInfo)){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            }
            List<User> userList=userBiz.find(null,"id="+partySpirit.getUserId());
            List<Classes> classesList=classesBiz.find(null," id="+userList.get(0).getClassId());
            if(classesList==null||classesList.size()==0){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "该学员所在班次已不存在", null);
            }
            partySpirit.setClassId(classesList.get(0).getId());
            partySpirit.setClassTypeId(classesList.get(0).getClassTypeId());
            partySpirit.setTotal(partySpirit.getPersonMaterial()*0.4+partySpirit.getOrganisation()*0.4+partySpirit.getAllPerformance()*0.2);
            partySpiritBiz.save(partySpirit);
            json=this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        }catch (Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 党性锻炼成绩列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     *
     */
    @RequestMapping(ADMIN_PREFIX+"/partySpiritList")
    public String partySpiritList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination){
        try{

            List<PartySpirit> partySpiritList=null;
            String whereSql=" status=1";
            PartySpirit partySpirit=new PartySpirit();
            String name=request.getParameter("name");
            if(!StringUtils.isTrimEmpty(name)){
                whereSql+=" and name like '%"+name+"%'";
                partySpirit.setName(name);
            }

            Map<String,String> userMap= SysUserUtils.getLoginSysUser(request);
            List<Classes> classesList=null;
            if(userMap.get("userType").equals("2")){
                classesList=classesBiz.find(null," status=1 and teacherId="+userMap.get("linkId"));
                if(classesList!=null&&classesList.size()>0){
//                    whereSql+=" and classId="+classesList.get(0).getId();
                    whereSql+=" and classId in("+classesList.stream().map(c->c.getId().toString()).collect(Collectors.joining(","))+")";

                }
            }
            pagination.setRequest(request);
            if(classesList!=null&&classesList.size()>0){
                partySpiritList = partySpiritBiz.find(pagination,whereSql);
            }
            request.setAttribute("partySpiritList",partySpiritList);
            request.setAttribute("pagination",pagination);
            request.setAttribute("partySpirit",partySpirit);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "/admin/partySpirit/partySpirit_list";
    }


    /**
     * 跳转到修改党性锻炼成绩的页面
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/toUpdatePartySpirit")
    public String toUpdatePartySpirit(HttpServletRequest request,Long id){
        try{
            List<PartySpirit> partySpiritList=partySpiritBiz.find(null," id="+id);
            request.setAttribute("partySpirit",partySpiritList.get(0));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/partySpirit/update_partySpirit";
    }

    /**
     * 修改党性锻炼成绩
     * @param partySpirit
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/updatePartySpirit")
    @ResponseBody
    public Map<String,Object> updatePartySpirit(@ModelAttribute("partySpirit")PartySpirit partySpirit){
        Map<String,Object> json=null;
        try{
            String errorInfo=validatePartySpirit(partySpirit);
            if(!StringUtils.isTrimEmpty(errorInfo)){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            }
            partySpirit.setTotal(partySpirit.getPersonMaterial()*0.4+partySpirit.getOrganisation()*0.4+partySpirit.getAllPerformance()*0.2);
            partySpiritBiz.update(partySpirit);
            json=this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        }catch (Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 删除党性锻炼成绩
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/delPartySpirit")
    @ResponseBody
    public Map<String,Object> delPartySpirit(Long id){
        Map<String,Object> json=null;
        try{
            partySpiritBiz.deleteById(id);
            json=this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        }catch (Exception e){
            e.printStackTrace();
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 验证党性锻炼成绩
     * @param partySpirit
     * @return
     */
    public String validatePartySpirit(PartySpirit partySpirit){

        if(partySpirit.getPersonMaterial()==null){
            return "个人党性分析材料不能为空";
        }
        if(partySpirit.getOrganisation()==null){
            return "组织纪律不能为空";
        }
        if(partySpirit.getAllPerformance()==null){
            return "综合表现不能为空";
        }
        return null;
    }


    /**
     * 去批量导入
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toAddBatchPartySpirit")
    public String toAddBatchPartySpirit(HttpServletRequest request) {
        return "/admin/partySpirit/import_party_spirit";
    }


    /**
     * 批量导入党性锻炼成绩
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/batchPartySpirit")
    public String batchPartySpirit(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        try {
            String errorInfo = partySpiritBiz.batchImportPartySpirit(myFile, request);
            if (StringUtils.isTrimEmpty(errorInfo)) {
                errorInfo = "导入成功";
            }
            request.setAttribute("errorInfo", errorInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/partySpirit/import_party_spirit";
    }
}
