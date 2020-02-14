package com.sms.controller.profile;



import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.sms.biz.profile.ProfileBiz;
import com.sms.common.CommonConstants;
import com.sms.entity.profile.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 配置控制类
 * 林明亮
 * Created by Administrator on 2016/12/22.
 */
@Controller
@RequestMapping("/admin/sms/profile")
public class ProfileController extends BaseController {
    private final Logger logger= LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private ProfileBiz profileBiz;

    @InitBinder({"profile"})
    public void initBinder(WebDataBinder binder){
        binder.setFieldDefaultPrefix("profile.");
    }

    /**
     * 查询配置
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping("/profileList")
    public String profileList(HttpServletRequest request){
        try{
            List<Profile> profileList= profileBiz.getAllConfig();
            request.setAttribute("profileList",profileList);
        }catch (Exception e){
            logger.error("profileList()--error",e);
        }
        return "/profile/profile-list";
    }

    /**
     * 删除配置
     * @param id
     * @return
     */
    @RequestMapping("/deleteProfile")
    @ResponseBody
    public Map<String,Object> deleteProfile(@RequestParam("id") Long id){
        try{
            profileBiz.deleteById(id);
            redisCache.regiontRemove(CommonConstants.ALL_CONFIG_KEY);
            return this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,null);
        }catch (Exception e){
            logger.error("deleteProfile()--error",e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM,ErrorCode.SYS_ERROR_MSG,null);
        }
    }

    /**
     * 添加、修改配置
     * @return
     */
    @RequestMapping("/addOrUpdateProfile")
    @ResponseBody
    public Map<String,Object> addOrUpdateProfile(HttpServletRequest request,
                                                 @ModelAttribute("profile") Profile profile){
        try{

            if(StringUtils.isTrimEmpty(profile.getConfigName())){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,"配置名不能为空",null);
            }
            if(StringUtils.isTrimEmpty(profile.getConfigKey())){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,"配置关键字不能为空",null);
            }
            String whereSql = " 1=1";
            if(profile.getId()!=null && profile.getId().longValue()>0){
                whereSql+=" and id !="+profile.getId();
            }
            whereSql+=" and configKey='"+profile.getConfigKey().trim()+"'";
            List<Profile> profiles = profileBiz.find(null,whereSql);
            if(!CollectionUtils.isEmpty(profiles)){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,"配置Key已经被使用",null);
            }

            Map<String,String> configMap=getConfig(request);
            String profileContext=null;
            if(configMap.size()>0){
                profileContext = gson.toJson(configMap);
            }
            profile.setConfigContext(profileContext);
            if(profile.getId()==null || profile.getId().intValue()==0){//添加
                profileBiz.save(profile);
            }else{//修改、
                profileBiz.update(profile);
            }
            redisCache.regiontRemove(CommonConstants.ALL_CONFIG_KEY);
            return this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,profile);
        }catch (Exception e){
            logger.error("addOrUpdateProfile()--error",e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM,ErrorCode.SYS_ERROR_MSG,null);
        }

    }
    /**
     * 初始化修改或添加配置页面
     * @return
     */
    @RequestMapping("/toAddOrUpdateProfile")
    public String toAddOrUpdateProfile(HttpServletRequest request,@RequestParam(value="id",required=false) Long id){
        if(id!=null && id.intValue()!=0){
            Profile profile=profileBiz.findById(id);

            List<Map<String,String>>list=getListMap(profile.getConfigContext());
            request.setAttribute("configContext",list);

            profile.setConfigContext(null);
            request.setAttribute("profile",profile);
        }
        return "/profile/addOrUpdateProfile";
    }


    /**
     * 获取配置内容 用于添加和修改
     * @param request
     * @return
     */
    private Map<String,String> getConfig(HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        Enumeration<String> enumeration=request.getParameterNames();
        while (enumeration.hasMoreElements()){
            String key=enumeration.nextElement();//配置名
            if(key.startsWith("configName_")){
                String value=request.getParameter(key);//配置值
                map.put(key.replace("configName_","").trim(),value);
            }
        }
        return map;
    }

    /**
     * 配置内容转list集合  TreeMap 方便页面取map的key
     * @param configContext 配置内容  json串
     * @return
     */
    private List<Map<String,String>>getListMap(String configContext){
        List<Map<String,String>>listMap=null;
        if(!StringUtils.isTrimEmpty(configContext)){
            Map<String,String> map=gson.fromJson(configContext,new TypeToken<Map<String,String>>(){}.getType());
            if(map!=null && map.size()>0){
                listMap=new ArrayList<>();
                Set<String> keySet=map.keySet();
                Iterator<String> it=keySet.iterator();
                while(it.hasNext()){
                    String key=it.next();
                    String value=map.get(key);
                    Map<String,String> _map=new TreeMap<>();
                    _map.put("key",key);
                    _map.put("value",value);
                    listMap.add(_map);
                }
            }
        }
        return listMap;
    }
}
