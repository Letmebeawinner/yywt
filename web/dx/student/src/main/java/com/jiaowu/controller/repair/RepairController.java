package com.jiaowu.controller.repair;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.common.base.Joiner;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.common.HqHessianService;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.controller.partySpirit.PartySpiritController;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.repair.Repair;
import com.jiaowu.entity.repair.RepairDto;
import com.jiaowu.entity.repairType.RepairType;
import com.jiaowu.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Created by MaxWe on 2017/10/17.
 */
@Controller
public class RepairController extends BaseController {
    private static final String ADMIN_REPAIR = "/admin/student/repair";
    private static Logger logger = LoggerFactory.getLogger(PartySpiritController.class);
    @Autowired
    private HqHessianService hqHessianService;
    @Autowired
    private ClassTypeBiz classTypeBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ClassesBiz classesBiz;

    @InitBinder
    protected void initRepairBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @InitBinder({"repair"})
    public void initRepair(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("repair.");
    }

    /**
     * 初始化添加维修页面
     *
     * @return 添加页面
     */
    @RequestMapping(ADMIN_REPAIR + "/toAddRepair")
    public String addRepair(HttpServletRequest request) {
        try {
            String repairTypeListjson = hqHessianService.toSaveRepair();
            // List<RepairType> repairTypes = gsons.fromJson(repairTypeListjson, new TypeToken< List<RepairType>>() {}.getType());
            request.setAttribute("repairTypeList", repairTypeListjson);
        } catch (Exception e) {
            logger.error("RepairController--addRepair", e);
            return this.setErrorPath(request, e);
        }
        return "/admin/repair/add-repair";
    }

    /**
     * 保存报修信息
     */
    @RequestMapping(ADMIN_REPAIR + "/saveRepair")
    @ResponseBody
    public Map<String, Object> saveRepair(HttpServletRequest request, @ModelAttribute("repair") Repair repair) {
        Map<String, Object> resultMap = null;
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");   //根据时间创建一个维修编号
            String number = sdf.format(date);

            Long userId = SysUserUtils.getLoginSysUserId(request);
            repair.setStatus(0);
            repair.setUserId(userId);
            repair.setNumber(number);

            String flag = hqHessianService.saveRepair(gson.toJson(repair));

            if (ErrorCode.SUCCESS.equals(flag)) {
                resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
            } else {
                return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
            }
        } catch (Exception e) {
            logger.error("RepairController--saveRepair", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * 我的报修进度
     * /admin/student/repair/queryMyRepair.json
     */
    @RequestMapping(ADMIN_REPAIR + "/queryMyRepairList")
    public ModelAndView queryMyRepairList(HttpServletRequest request,
                                          @ModelAttribute("pagination") Pagination pagination,
                                          @ModelAttribute("repair") Repair repair) {
        ModelAndView mv = new ModelAndView("/admin/repair/myRepair_list");
        try {
            //查询所有维修类型列表
            String repairTypeListjson = hqHessianService.toSaveRepair();
            // super basecontroller 中的gson带时间转换
            List<RepairType> repairTypes = gson.fromJson(repairTypeListjson, new TypeToken<List<RepairType>>() {
            }.getType());
            mv.addObject("repairTypeList", repairTypes);

            // 查询个人的报修列表
            Long userId = SysUserUtils.getLoginSysUserId(request);
            repair.setUserId(userId);

            Map<String, String> result = hqHessianService.getRepairsDtos(gson.toJson(repair), pagination);
            // json转泛型
            String repairJson = result.get("repairList");
            List<RepairDto> repairList = gson.fromJson(repairJson, new TypeToken<List<RepairDto>>() {
            }.getType());
            mv.addObject("repairList", repairList);

            // json转对象
            String paginationJson = result.get("pagination");
            Pagination paginationFromJson = gson.fromJson(paginationJson, Pagination.class);
            // 拼装参数
            paginationFromJson.setRequest(request);
            // 环回分页对象
            mv.addObject("pagination", paginationFromJson);
        } catch (Exception e) {
            logger.error("RepairController--queryMyRepairList", e);
        }
        return mv;
    }

    /**
     * 查看报修详情
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_REPAIR + "/descRepair")
    public String descRepair(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            String repairDtoJson = hqHessianService.getRepairDtosById(id);
            RepairDto repairDto = gson.fromJson(repairDtoJson, RepairDto.class);
            request.setAttribute("repairs", repairDto);
        } catch (Exception e) {
            logger.info("RepairController--descRepair", e);
            return this.setErrorPath(request, e);
        }
        return "/admin/repair/desc-repair";
    }

    @RequestMapping(ADMIN_REPAIR + "/getAllStudentRepairList")
    public ModelAndView getAllStudentRepairList(HttpServletRequest request,
                                                @ModelAttribute("pagination") Pagination pagination,
                                                @ModelAttribute("repair") Repair repair) {
        ModelAndView mv = new ModelAndView("/admin/repair/repair_list");
        try {
            String classTypeId=request.getParameter("classTypeId");
            String classId=request.getParameter("classId");
            mv.addObject("classTypeId",classTypeId);
            mv.addObject("classId",classId);

            List<ClassType> classTypeList = classTypeBiz.find(null," 1=1");
            request.setAttribute("classTypeList",classTypeList);
            // 查询所有维修类型列表
            String repairTypeListjson = hqHessianService.toSaveRepair();
            List<RepairType> repairTypes = gson.fromJson(repairTypeListjson, new TypeToken<List<RepairType>>() {
            }.getType());
            mv.addObject("repairTypeList", repairTypes);

            // 查询所有人(学员)的报修
            Map<String, String> result = hqHessianService.getAllStudentRepairList(gson.toJson(repair), pagination);

            // json转泛型
            String repairListJson = result.get("repairList");
            List<RepairDto> repairListFromJson = gson.fromJson(repairListJson, new TypeToken<List<RepairDto>>() {
            }.getType());
            //需要加班型和班次条件，暂时认为报修的都是学员，写的很渣，赶进度，没办法。
            if(!StringUtils.isTrimEmpty(classTypeId)){
                StringBuilder sb=new StringBuilder(" classTypeId="+classTypeId);

                    if(!StringUtils.isTrimEmpty(classId)){
                        sb.append(" and classId=").append(classId);
                    }
                    List<User> userList=userBiz.find(null,sb.toString());
                    List<Long> userIds=new ArrayList<Long>();
                    if(userList!=null&&userList.size()>0){
                        for(User user:userList){
                            userIds.add(user.getId());
                        }
                        List<Map<String, String>> sysUserList= baseHessianService.querySysUserByLinkIds(3,userIds);
                        List<Long> sysUserIdList=new ArrayList<Long>();
                        if(sysUserList!=null&&sysUserList.size()>0){
                            for(Map<String, String> sysUser:sysUserList){
                                sysUserIdList.add(Long.parseLong(sysUser.get("id")));
                            }
                        }
                        repairListFromJson.removeIf(repairDto -> !sysUserIdList.contains(repairDto.getUserId()));
                    }

            }
            mv.addObject("repairList", repairListFromJson);

            // json转对象
            String paginationJson = result.get("pagination");
            Pagination paginationFromJson = gson.fromJson(paginationJson, Pagination.class);
            // URL拼装参数
            paginationFromJson.setRequest(request);
            paginationFromJson.setTotalCount(repairListFromJson.size());
            paginationFromJson.setTotalPages(paginationFromJson.getTotalCount()%paginationFromJson.getPageSize()==0?paginationFromJson.getTotalCount()/paginationFromJson.getPageSize():(paginationFromJson.getTotalCount()/paginationFromJson.getPageSize())+1);
            mv.addObject("pagination", paginationFromJson);
        } catch (JsonSyntaxException e) {
            logger.error("RepairController.getAllStudentRepairList", e);
        }
        return mv;
    }

    /**
     * 查询所有学员的报修记录
     */
    @RequestMapping(ADMIN_REPAIR + "/allStudentRepairRecord")
    public ModelAndView allStudentRepairRecord(@ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/admin/repair/class_repair_list");
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);

            // 只有教职工能查看
            if (!userMap.get("userType").equals("2")) {
                return null;
            }
            // 查询此教师下的最新班次的学员
            // 该班主任教的最新的一个班级
            List<Classes> classesList=classesBiz.find(null,
                    " teacherId="+userMap.get("linkId")+" order by id desc limit 1");
            List<User> userList=null;

            // 查询确认参加班次的学员
            // 轮训人员通过的学员
            // 毕业的学员
            // 8的含义未知
            if(classesList!=null&&classesList.size()>0){
                userList = userBiz.find(null, " status in (1,4,7,8) and classId=" +classesList.get(0).getId());
            }

            // hashCode去重
            Set<Long> ids = new HashSet<>();
            if (userList != null) {
                userList.forEach(u -> ids.add(u.getId()));
            }

            // 查询这群学员的账号linkId
            String accountLinkIds = baseHessianService.queryLinkIdByUserStudentIds(Joiner.on(",").join(ids));
            // 查询这群账号的报修记录
            Map<String, String> rs = hqHessianService.queryStudentRepairRecord(accountLinkIds, pagination);

            // 反序列化
            String repairJson = rs.get("repairList");
            String pageJson = rs.get("pagination");

            List<Repair> repairTypes = gson.fromJson(repairJson, new TypeToken<List<Repair>>() {
            }.getType());

            Pagination pageFromJson = gson.fromJson(pageJson, Pagination.class);
            // 拼装参数
            pageFromJson.setRequest(request);

            mv.addObject("repairList", repairTypes);
            mv.addObject("pagination", pageFromJson);

            logger.debug(repairTypes.toString());
            logger.debug(pageFromJson.toString());

        } catch (Exception e) {
            logger.error("RepairController.allStudentRepairRecord", e);
        }

        return mv;
    }


}
