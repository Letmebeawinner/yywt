package com.houqin.controller.plan;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.JiaoWuHessianService;
import com.houqin.biz.common.SmsHessianService;
import com.houqin.biz.managemobile.ManageMobileBiz;
import com.houqin.biz.mess.MessBiz;
import com.houqin.biz.messArea.MessAreaBiz;
import com.houqin.biz.plan.PlanBiz;
import com.houqin.entity.managemobile.ManageMobile;
import com.houqin.entity.mess.Mess;
import com.houqin.entity.messArea.MessArea;
import com.houqin.entity.plan.Plan;
import com.houqin.utils.GenerateSqlUtil;
import org.apache.commons.collections.map.HashedMap;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * plan
 *
 * @author wanghailong
 * @create 2017-07-25-下午 3:53
 */
@Controller
@RequestMapping("/admin/houqin")
public class PlanController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PlanController.class);
    //更新食材分类
    private static final String toUpdatePlan = "/plan/update-plan";
    //食材分类列表
    private static final String planList = "/plan/plan_list";
    //后勤主管电话
    private static final String manageMobile = "/plan/add-manageMobile";

    @Autowired
    private PlanBiz planBiz;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;
    @Autowired
    private SmsHessianService smsHessianService;
    @Autowired
    private ManageMobileBiz manageMobileBiz;
    @Autowired
    private MessBiz messBiz;
    @Autowired
    private MessAreaBiz messAreaBiz;
    @Autowired
    private HttpServletRequest request;

    @InitBinder("plan")
    public void initPlan(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("plan.");
    }

    @InitBinder("manageMobile")
    public void initmanageMobile(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("manageMobile.");
    }


    @InitBinder
    protected void initPlanBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 跳转添加页面
     */
    @RequestMapping("/toAddPlan")
    public ModelAndView toAddPlan() {
        ModelAndView mv = new ModelAndView("/plan/add-plan");
        try {
            // 班级类型列表
            Map<String, Object> classTypeMap = jiaoWuHessianService.listClassType();
            request.setAttribute("classTypeMap", classTypeMap);

            // 食堂列表
            List<Mess> messes = messBiz.find(null, "1=1");
            mv.addObject("messes", messes);
        } catch (Exception e) {
            logger.error("PlanController--toAddPlan", e);
        }
        return mv;
    }

    /**
     * 区域列表
     *
     * @param messId 食堂id
     * @return json
     */
    @RequestMapping("/listAreaJson")
    @ResponseBody
    public Map<String, Object> listAreaJson(@RequestParam("messId") Long messId) {
        Map<String, Object> json;
        try {
            List<MessArea> messAreas = messAreaBiz.find(null, "messId = " + messId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, messAreas);
        } catch (Exception e) {
            logger.error("PlanController.listAreaJson", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * @param classTypeId
     * @return java.util.Map
     * @Description 通过班型ID获取班次列表
     */
    @RequestMapping("/getClassListByClassType")
    @ResponseBody
    public Map<String, Object> getClassListByClassType(@RequestParam("classTypeId") Long classTypeId) {
        Map<String, Object> json = null;
        try {
            Map<String, Object> map = jiaoWuHessianService.queryClassesList(null, "classTypeId=" + classTypeId + " and status=1");
            if (ObjectUtils.isNotNull(map)) {
                //培训批次
                List<Map<String, String>> classesList = (List<Map<String, String>>) map.get("classesList");
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, classesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 添加方法
     *
     * @param request
     * @param plan
     * @return
     */
    @RequestMapping("/addPlan")
    @ResponseBody
    public Map<String, Object> addPlan(HttpServletRequest request, @ModelAttribute("plan") Plan plan) {
        try {
            planBiz.save(plan);

            //获取当前系统的人的用户名
            Map<String, String> resultMap = SysUserUtils.getLoginSysUser(request);
            String userName = resultMap.get("userName").toString();

            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
            String newTime = dateFormater.format(plan.getCreateTime());

            String planContext = userName + "在" + newTime + "在" + "添加了id为" + plan.getId() + "的一个就餐计划，请注意查收!";

            //获取后勤主管的电话
            ManageMobile manageMobile = manageMobileBiz.findById(1L);

            Map<String, String> map = new HashedMap();
            map.put("mobiles", manageMobile.getMobile());
            map.put("context", planContext);
            map.put("sendType", "1");
            map.put("sendUserId", SysUserUtils.getLoginSysUserId(request).toString());
            map.put("receiveUserIds", "");
            Boolean isok = smsHessianService.sendMsg(map);
            System.out.println("++++++isok=" + isok);

        } catch (Exception e) {
            logger.error("PlanController--addPlan", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 去查询
     *
     * @param request
     * @param plan
     * @param pagination
     * @return
     */
    @RequestMapping("/selectAllPlan")
    public String selectAllPlan(HttpServletRequest request, @ModelAttribute("plan") Plan plan, @ModelAttribute("pagination") Pagination pagination) {
        try {
            // 显示班次名称
            Map<String, Object> jsonClass = jiaoWuHessianService.queryClassesList(null, "1=1");
            request.setAttribute("classesList", jsonClass.get("classesList"));

            // 班级类型列表
            Map<String, Object> classTypeMap = jiaoWuHessianService.listClassType();
            request.setAttribute("classTypeMap", classTypeMap);

            // 显示食堂名称
            List<Mess> messes = messBiz.find(null, "1=1");
            request.setAttribute("messes", messes);

            List<MessArea> messAreas=messAreaBiz.find(null,"1=1");
            request.setAttribute("messAreas",messAreas);

            String whereSql = GenerateSqlUtil.getSql(plan);
            pagination.setRequest(request);
            List<Plan> planList = planBiz.find(pagination, whereSql);
            request.setAttribute("planList", planList);
            request.setAttribute("plan", plan);
        } catch (Exception e) {
            logger.error("PlanController--selectAllPlan", e);
        }
        return planList;
    }

    /**
     * 去更新
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdatePlan")
    public String toUpdatePlan(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            List<Mess> messes = messBiz.find(null, " 1=1");
            Plan plan = planBiz.findById(id);
            //班级类型列表
            Map<String, Object> classTypeMap = jiaoWuHessianService.listClassType();
            request.setAttribute("classTypeMap", classTypeMap);
            request.setAttribute("plan", plan);
            request.setAttribute("messes", messes);


        } catch (Exception e) {
            logger.error("PlanController--toUpdatePlan", e);
        }
        return toUpdatePlan;
    }

    /**
     * 更新
     *
     * @param request
     * @param plan
     * @return
     */
    @RequestMapping("/updatePlan")
    @ResponseBody
    public Map<String, Object> updatePlan(HttpServletRequest request, @ModelAttribute("plan") Plan plan) {
        try {
            planBiz.update(plan);


            Mess mess = messBiz.findById(plan.getMessId());

            //获取当前系统的人的用户名
            Map<String, String> resultMap = SysUserUtils.getLoginSysUser(request);
            String userName = resultMap.get("userName").toString();

            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
            String newTime = dateFormater.format(plan.getCreateTime());

            String planContext = userName + "在" + newTime + "在" + mess.getName() + "修改了id为" + plan.getId() + "的一个就餐计划，请注意查收!";

            //获取后勤主管的电话
            ManageMobile manageMobile = manageMobileBiz.findById(1L);

            Map<String, String> map = new HashedMap();
            map.put("mobiles", manageMobile.getMobile());
            map.put("context", planContext);
            map.put("sendType", "1");
            map.put("sendUserId", SysUserUtils.getLoginSysUserId(request).toString());
            map.put("receiveUserIds", "");
            Boolean isok = smsHessianService.sendMsg(map);
            System.out.println("++++++isok=" + isok);
        } catch (Exception e) {
            logger.error("PlanController--updatePlan", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 删除
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/delPlan")
    @ResponseBody
    public Map<String, Object> delPlan(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            planBiz.deleteById(id);
        } catch (Exception e) {
            logger.error("PlanController--delPlan", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 查询后勤主管电话
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateManageMobile")
    public String updateManageMobile(HttpServletRequest request) {
        try {
            List<ManageMobile> manageMobileList = manageMobileBiz.find(null, " 1=1");
            request.setAttribute("manageMobileList", manageMobileList);
        } catch (Exception e) {
            logger.error("PlanController.updateManageMobile", e);
        }
        return manageMobile;
    }

    /**
     * 添加或者修改主管手机号码
     *
     * @param request
     * @param manageMobile
     * @return
     */
    @RequestMapping("/addOrUpdateManageMobile")
    @ResponseBody
    public Map<String, Object> addOrUpdateManageMobile(HttpServletRequest request, @ModelAttribute("manageMobile") ManageMobile manageMobile) {
        Map<String, Object> json = null;
        try {
            if (ObjectUtils.isNotNull(manageMobile.getId())) {
                manageMobileBiz.update(manageMobile);
                json = this.resultJson(ErrorCode.SUCCESS, "修改成功", "");
            } else {
                manageMobileBiz.save(manageMobile);
                json = this.resultJson(ErrorCode.SUCCESS, "修改成功", "");
            }
        } catch (Exception e) {
            json = this.resultJson(ErrorCode.ERROR_DATA, "", "");
            logger.error("PlanController.addOrUpdateManageMobile", e);
        }
        return json;
    }

    /**
     * 早中餐管理
     *
     * @param PlanId 就餐计划id
     * @return 早中餐添加修改一个页面
     */
    @RequestMapping("/lunchManagement")
    public ModelAndView lunchManagement(@RequestParam("id") Long PlanId) {
        ModelAndView mv = new ModelAndView("/plan/lunchManagement");
        try {
            Plan plan = planBiz.findById(PlanId);
            mv.addObject("plan", plan);
        } catch (Exception e) {
            logger.error("PlanController.lunchManagement", e);
        }
        return mv;
    }

    /**
     * 修改早中餐时间人数
     *
     * @param plan
     * @return
     */
    @RequestMapping("/addLunchManagement")
    @ResponseBody
    public Map<String, Object> addLunchManagement(@ModelAttribute("plan") Plan plan) {
        Map<String, Object> json;
        try {
            planBiz.update(plan);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("PlanController.addLunchManagement", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }
}
