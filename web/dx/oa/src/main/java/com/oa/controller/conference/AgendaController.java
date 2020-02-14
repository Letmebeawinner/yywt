package com.oa.controller.conference;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.conference.AgendaBiz;
import com.oa.biz.conference.OaMeetingTopicBiz;
import com.oa.common.StatusConstants;
import com.oa.entity.conference.Agenda;
import com.oa.entity.conference.OaMeetingTopic;
import com.oa.entity.sysuser.SysUser;
import com.oa.utils.GenerateSqlUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 会议管理
 *
 * @author YaoZhen
 * @create 10-23, 11:19, 2017.
 */
@Controller
public class AgendaController extends BaseController {
    /**
     * 通用URL
     */
    public static final String ADMIN_OA_CONFERENCE = "/admin/oa/conference";
    private static final Logger logger = Logger.getLogger(AgendaController.class);
    @Autowired
    private AgendaBiz agendaBiz;
    @Autowired
    private OaMeetingTopicBiz oaMeetingTopicBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    //数据绑定到实体
    @InitBinder({"agenda"})
    public void agendaInit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("agenda.");
    }

    // 格式化时间
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    /**
     * 添加议程
     *
     * @return 添加议程
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/agenda/toSave")
    public String toSaveAgenda(HttpServletRequest request) {
        //只查审批通过的,和没人使用的
        String sql = " audit = 1 and status = 0";
        try {
            List<OaMeetingTopic> oaMeetingTopics = oaMeetingTopicBiz.find(null, sql);
            request.setAttribute("oaMeetingTopics", oaMeetingTopics);
        } catch(Exception e) {
            logger.error("AgendaController.toSaveAgenda", e);
            return this.setErrorPath(request, e);
        }
        return "/conference/save_agenda";
    }


    /**
     * 保存议程
     *
     * @param agenda 议程
     * @return json
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/agenda/save")
    @ResponseBody
    public Map<String, Object> saveAgenda(@ModelAttribute("agenda") Agenda agenda) {
        Map<String, Object> json;
        try {
            agendaBiz.tx_saveAgenda(agenda);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("ConferenceController.saveAgenda", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    @RequestMapping(ADMIN_OA_CONFERENCE + "/agenda/userById")
    @ResponseBody
    public Map<String, Object> userById(@RequestParam("bePresent") String bePresent,@RequestParam("attend") String attend) {
        Map<String, Object> json;
        try {
            List<SysUser> sysUserList1 = new ArrayList<>();
            List<SysUser> sysUserList2 = new ArrayList<>();
            String ids[];
            if(bePresent.indexOf(",")>-1){
                ids = bePresent.split(",");
            }else{
                ids = new String[1];
                ids[0] = bePresent;
            }
            for(String id : ids){
                SysUser sysUser = baseHessianBiz.getSysUserById(Long.valueOf(id));
                if(sysUser.getMobile()==""){
                    sysUser.setMobile("未綁定");
                }
                sysUserList1.add(sysUser);
            }
            String ids1[];
            if(attend.indexOf(",")>-1){
                ids1 = attend.split(",");
            }else{
                ids1 = new String[1];
                ids1[0] = attend;
            }
            for(String id : ids1){
                SysUser sysUser = baseHessianBiz.getSysUserById(Long.valueOf(id));
                sysUserList2.add(sysUser);
            }
            Map<String,List<SysUser>> map = new HashMap<>();
            map.put("bePresent",sysUserList1);
            map.put("attend",sysUserList2);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, map);
        } catch (Exception e) {
            logger.error("ConferenceController.userById", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 议程列表
     *
     * @param pagination 分页
     * @param agenda    主持人
     * @param request    请求
     * @return 列表页面
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/agenda/list")
    public ModelAndView listAgenda(@ModelAttribute("pagination") Pagination pagination,
                                   @ModelAttribute("agenda") Agenda agenda,
                                   HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/conference/list_agenda");
        agenda.setStatus(0);
        String sql = GenerateSqlUtil.getSql(agenda);
        try {
            pagination.setRequest(request);
            List<Agenda> agendas = agendaBiz.find(pagination, sql);
            if (agendas != null && agendas.size() > 0) {
                agendas.forEach(agenda1 -> {
                    Long topicId = agenda1.getTopicId();
                    OaMeetingTopic topic = oaMeetingTopicBiz.findById(topicId);
                    agenda1.setAgendaName("");
                    if (topic != null) {
                        agenda1.setAgendaName(topic.getName());
                    }
                    String bePresent = "";
                    String attend = "";
                    if (!StringUtils.isTrimEmpty(agenda1.getBePresent())) {
                        //把id转换成实际名字
                        String[] ids;
                        if(agenda1.getBePresent().indexOf(",")>-1){
                            ids = agenda1.getBePresent().split(",");
                        }else{
                            ids = new String[1];
                            ids[0] = agenda1.getBePresent();
                        }
                        for (String id: ids) {
                            if (!StringUtils.isDigit(id)) {
                                continue;
                            }
                            SysUser sysUser = baseHessianBiz.getSysUserById(Long.parseLong(id));
                            if (sysUser!= null) {
                                bePresent += sysUser.getUserName() + " ";
                            }
                        }
                    }
                    if (!StringUtils.isTrimEmpty(agenda1.getAttend())) {
                        //把id转换成实际名字
                        String[] ids;
                        if(agenda1.getAttend().indexOf(",")>-1){
                            ids = agenda1.getAttend().split(",");
                        }else{
                            ids = new String[1];
                            ids[0] = agenda1.getAttend();
                        }
                        for (String id: ids) {
                            if (!StringUtils.isDigit(id)) {
                                continue;
                            }
                            SysUser sysUser = baseHessianBiz.getSysUserById(Long.parseLong(id));
                            if (sysUser!= null) {
                                attend += sysUser.getUserName() + " ";
                            }
                        }
                    }
                    agenda1.setBePresent(bePresent);
                    agenda1.setAttend(attend);
                });
            }
            mv.addObject("agendas", agendas);
            mv.addObject("agenda", agenda);
        } catch (Exception e) {
            logger.error("ConferenceController.listAgenda", e);
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * 跳转编辑
     *
     * @param id id
     * @return 编辑
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/agenda/toUpdate")
    public ModelAndView toUpdateAgenda(Long id) {
        ModelAndView mv = new ModelAndView("/conference/update_agenda");
        //查询审核已通过的议题, 未使用的议题
        String sql = "audit = 1 and status = 0";
        try {
            Agenda agenda = agendaBiz.findById(id);
            //或者会议主题是已经选择过得
            sql += " or id = " + agenda.getTopicId();
            List<OaMeetingTopic> oaMeetingTopics = oaMeetingTopicBiz.find(null, sql);
            mv.addObject("oaMeetingTopics", oaMeetingTopics);
            mv.addObject("agenda", agenda);
        } catch (Exception e) {
            logger.error("ConferenceController.toUpdateAgenda", e);
        }
        return mv;
    }

    /**
     * 修改
     *
     * @param agenda 议程
     * @return json
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/agenda/update")
    @ResponseBody
    public Map<String, Object> updateAgenda(@ModelAttribute("agenda") Agenda agenda, @RequestParam(value = "oldTopicId", required = false) Long oldTopicId) {
        Map<String, Object> json;
        try {
            agendaBiz.tx_updateAgenda(agenda, oldTopicId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("ConferenceController.updateAgenda", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 删除
     *
     * @param id 主键
     * @return
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/agenda/del")
    @ResponseBody
    public Map<String, Object> delAgenda(Long id) {
        Map<String, Object> json;
        try {
            Agenda topic = agendaBiz.findById(id);
            if (ObjectUtils.isNotNull(topic)) {
                topic.setStatus(StatusConstants.remove);
                agendaBiz.update(topic);
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("ConferenceController.delAgenda", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    @RequestMapping(ADMIN_OA_CONFERENCE + "/agenda/userList")
    @ResponseBody
    public Map<String, Object> userById(@RequestParam("userIds") String userIds) {
        Map<String, Object> json;
        try {
            List<SysUser> userList = new ArrayList<>();
            String ids[];
            if(userIds.indexOf(",")>-1){
                ids = userIds.split(",");
            }else{
                ids = new String[1];
                ids[0] = userIds;
            }
            for(String id : ids){
                SysUser sysUser = baseHessianBiz.getSysUserById(Long.valueOf(id));
                if(sysUser.getMobile()==""){
                    sysUser.setMobile("未綁定");
                }
                userList.add(sysUser);
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, userList);
        } catch (Exception e) {
            logger.error("ConferenceController.userById", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    @RequestMapping(ADMIN_OA_CONFERENCE + "/agenda/topicList")
    @ResponseBody
    public Map<String, Object> topicList(@RequestParam("topicIds") String topicIds) {
        Map<String, Object> json;
        try {
            List<OaMeetingTopic> topicList = new ArrayList<>();
            String ids[];
            if(topicIds.indexOf(",")>-1){
                ids = topicIds.split(",");
            }else{
                ids = new String[1];
                ids[0] = topicIds;
            }
            for(String id : ids){
                OaMeetingTopic oaMeetingTopic = oaMeetingTopicBiz.findById(Long.valueOf(id));
                topicList.add(oaMeetingTopic);
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, topicList);
        } catch (Exception e) {
            logger.error("ConferenceController.topicList", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

}
