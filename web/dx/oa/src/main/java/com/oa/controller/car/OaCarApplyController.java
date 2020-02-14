package com.oa.controller.car;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.car.OaCarApplyBiz;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.common.SmsHessianService;
import com.oa.common.BaseHessianService;
import com.oa.entity.car.CarApplyTask;
import com.oa.entity.car.OaCarApply;
import com.oa.entity.sysuser.SysUser;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用车申请控制层
 *
 * @author lzh
 * @create 2017-01-19-13:40
 */
@Controller
@RequestMapping("/admin/oa")
public class OaCarApplyController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OaCarApplyController.class);

    @Autowired
    private OaCarApplyBiz carApplyBiz;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private SmsHessianService smsHessianService;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    private static final String toCarApply = "/car/car_apply_add";
    private static final String carApplyMine = "/car/car_apply_mine";
    private static final String carApplyManager = "/car/car_apply_manager";
    private static final String carApplyAudit = "/car/car_apply_audit";

    @InitBinder("carApply")
    public void initBinderCar(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("carApply.");
    }

    @InitBinder("carApplyTask")
    public void initBinderCarTask(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("carApplyTask.");
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: []
     * @Return: java.lang.String
     * @Date: 13:46
     */
    @RequestMapping("/toCarApply")
    public String toCarApply() {
        return toCarApply;
    }

    /**
     * @Description:开启用车申请工作流
     * @author: lzh
     * @Param: [request, carApply]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 14:14
     */
    @RequestMapping("/startCarApply")
    @ResponseBody
    public Map<String, Object> startCarApplyWorkFlow(HttpServletRequest request,
                                                     @ModelAttribute("carApply") OaCarApply carApply,
                                                     @RequestParam("processDefinitionId") String processDefinitionId) {
        Map<String, Object> resultMap = null;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            Map<String, Object> map = new HashMap<>();
            map.put("start", userId);
            String processInstanceId = carApplyBiz.tx_startCarApplyProcess(carApply, processDefinitionId, userId);
            logger.info("用车申请流程已启动 id = {}", processInstanceId);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("OaCarApplyController.startCarApplyWorkFlow", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description: 查询我发起的流程
     * @author: lzh
     * @Param: [request]
     * @Return: java.util.List<com.oa.entity.car.CarApply>
     * @Date: 15:13
     */
    @RequestMapping("/myCarProcess")
    public String getMyCarProcess(HttpServletRequest request) {
        try {
            String whereSql = " id in ";
            Long userId = SysUserUtils.getLoginSysUserId(request);
            ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
            List<ProcessInstance> processInstances = processInstanceQuery.processDefinitionKey("car").involvedUser(String.valueOf(userId)).list();
            //获取业务id的字符串，以逗号拼接
            String businessIds = processInstances.stream()
                    .map(ProcessInstance::getBusinessKey)
                    .collect(Collectors.joining(", ", "(", ")"));
            String sql = whereSql + businessIds;
            List<OaCarApply> carApplies = carApplyBiz.find(null, sql);
            List<OaCarApply> carApplies2 = carApplies.stream()
                    .map(carApply -> carApplyBiz.carApplySetName(carApply))
                    .collect(Collectors.toList());
            request.setAttribute("carApplies", carApplies2);
        } catch (Exception e) {
            logger.error("CarApplyController.getMyCarProcess", e);
            return this.setErrorPath(request, e);
        }
        return carApplyMine;
    }

    /**
     * @Description: 该用户的待办列表页
     * @author: lzh
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 17:25
     */
    @RequestMapping("/getMyCarApplyManager")
    public String getMyCarApplyManager(HttpServletRequest request, @ModelAttribute("carApplyTask") CarApplyTask carApplyTask) {
        try {
            List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("20").list();
            List<CarApplyTask> carApplyTasks = tasks
                    .stream()
                    .map(task -> {
                        return carApplyBiz.getCarApplyTaskByTask(task);
                    })
                    .collect(Collectors.toList());
            request.setAttribute("carApplyTasks", carApplyTasks);
            request.setAttribute("carApplyTask", carApplyTask);
        } catch (Exception e) {
            logger.error("CarApplyController.getMyCarApplyManager", e);
            return this.setErrorPath(request, e);
        }
        return carApplyManager;
    }

    /**
     * @Description: 进入车辆申请详情页面，详情页包含审核页面
     * @author: lzh
     * @Param: [request, taskId]
     * @Return: java.lang.String
     * @Date: 10:10
     */
    @RequestMapping("/getCarMangerInfo")
    public String getCarManagerInfo(HttpServletRequest request, @RequestParam("taskId") String taskId) {
        try {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            CarApplyTask carApplyTask = carApplyBiz.getCarApplyTaskByTask(task);
            request.setAttribute("carApplyTask", carApplyTask);
        } catch (Exception e) {
            logger.error("CarApplyController.getCarMangerInfo", e);
            return this.setErrorPath(request, e);
        }
        return carApplyAudit;
    }

    /**
     * @Description: 用车审核
     * @author: lzh
     * @Param: [taskId, carApplyOpiont, refuseReason]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:31
     */
    @RequestMapping("/carApplyDeptAudit")
    @ResponseBody
    public Map<String, Object> carApplyAudit(@RequestParam("taskId") String taskId,
                                             @ModelAttribute("carApply") OaCarApply oaCarApply,
                                             @RequestParam(value = "comment", required = false) String comment,
                                             HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            carApplyBiz.tx_startCarApplyAudit(oaCarApply, taskId, comment, userId);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("OaCarApplyController.carApplyAudit", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }

        return json;
    }

    /**
     * 发送短信给驾驶员，短信提醒 单条
     * @param message
     * @param mobile
     * @param request
     * @return
     */
    @RequestMapping("/ajax/mobile/sendMessage")
    @ResponseBody
    public Map<String, Object> sendMobileMessage(@RequestParam("message") String message,
                                                 @RequestParam("mobile") String mobile,
                                                 HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("mobiles", mobile);
            map.put("context", message);
            map.put("sendType", "3");
            map.put("sendUserId", "" + userId);
            map.put("receiveUserIds", null);
            boolean flag = smsHessianService.sendMsg(map);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch(Exception e) {
            logger.error("OaCarApplyController.sendMobileMessage", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 发送短信，多条发送
     * @param message
     * @param mobiles
     * @param userNames
     * @param request
     * @return
     */
    @RequestMapping("/ajax/mobile/sendMessages")
    @ResponseBody
    public Map<String, Object> sendMobileMessage(@RequestParam("message") String message,
                                                 @RequestParam("mobiles[]") List<String> mobiles,
                                                 @RequestParam("userNames[]") List<String> userNames,
                                                 HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            if (mobiles != null && mobiles.size() > 0) {
                int i = 0;
                for (String mobile : mobiles) {
                    Map<String, String> map = new HashMap<>();
                    map.put("mobiles", mobile);
                    map.put("context", userNames.get(0) + message);
                    map.put("sendType", "3");
                    map.put("sendUserId", "" + userId);
                    map.put("receiveUserIds", null);
                    boolean flag = smsHessianService.sendMsg(map);
                    i ++;
                }
            }

            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch(Exception e) {
            logger.error("OaCarApplyController.sendMobileMessage", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 还车，存档
     *
     * @param processInstanceId
     * @param taskId
     * @return
     */
    @RequestMapping("/carApply/saveRecord")
    @ResponseBody
    public Map<String, Object> saveCarApplyRecord(@RequestParam("processInstanceId") String processInstanceId,
                                                  @RequestParam("taskId") String taskId,
                                                  @ModelAttribute("carApply") OaCarApply carApply) {
        Map<String, Object> json = null;
        try {
            carApplyBiz.tx_saveCarApplyRecord(processInstanceId, taskId, carApply);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("CarApplyController.saveCarApplyRecord", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }
        return json;
    }

    /**
     * @Description: 获取已经结束的，我发起的用车申请流程实例
     * @author: lzh
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 11:25
     */
    @RequestMapping("/myCarApplyHistory")
    public String getMyCarApplyHistory(HttpServletRequest request) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            List<HistoricProcessInstance> historyProcessInstances = historyService.createHistoricProcessInstanceQuery()
                    .processDefinitionKey("car")
                    .startedBy(String.valueOf(userId))
                    .finished()
                    .list();
            List<OaCarApply> carApplies = carApplyBiz.getApplyListByHistoryProcess(historyProcessInstances);
            request.setAttribute("carApplies", carApplies);
        } catch (Exception e) {
            logger.error("CarApplyController.getMyCarApplyHistory", e);
            return this.setErrorPath(request, e);
        }
        return "";
    }

    /**
     * 获取所有驾驶员信息
     * @return
     */
    @RequestMapping("/ajax/get/all/driver")
    @ResponseBody
    public Map<String, Object> getAllDriver() {
        Map<String, Object> json = null;
        try {
            Map<String, Object> userListMap = baseHessianService.querySysUserList(null, " userType = 4");
            Object o = Optional.ofNullable(userListMap).map(map ->  userListMap.get("userList")).orElse(new ArrayList<>());
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, o);
        }  catch(Exception e) {
            logger.error("OaCarApplyController.getAllDriver", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 根据用户id，查找对应的用户信息
     * @param id
     * @return
     */
    @RequestMapping("/ajax/driver/getName")
    @ResponseBody
    public Map<String, Object> getDriverNameById(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            SysUser sysUser = baseHessianBiz.getSysUserById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, sysUser);
        }  catch(Exception e) {
            logger.error("OaCarApplyController.getAllDriver", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

}
