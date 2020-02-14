package com.houqin.controller.Repair;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.common.SmsHessianService;
import com.houqin.biz.repair.RepairBiz;
import com.houqin.biz.repairType.RepairTypeBiz;
import com.houqin.common.CommonConstants;
import com.houqin.dao.repair.RepairDao;
import com.houqin.entity.repair.Repair;
import com.houqin.entity.repair.RepairDto;
import com.houqin.entity.repair.RepairStatistics;
import com.houqin.entity.repairType.RepairType;
import com.houqin.entity.sysuser.SysUser;
import com.houqin.utils.FileExportImportUtil;
import com.houqin.utils.StringUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报修管理（APP接口）
 *
 * @author ccl
 * @create 2016-12-10-17:43
 */
@Controller
@RequestMapping("/app/houqin")
public class AppRepairController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AppRepairController.class);


    @Autowired
    private RepairBiz repairBiz;

    @Autowired
    private RepairTypeBiz repairTypeBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private SmsHessianService smsHessianService;

    @InitBinder
    protected void initRepairBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"repair"})
    public void initRepair(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("repair.");
    }



    /**
     * 获取维修类型列表
     *
     * @param
     * @return 添加页面
     */
    @RequestMapping("/repairTypeList")
    @ResponseBody
    public Map<String, Object> repairTypeList() {
        try {
            List<RepairType> repairTypeList = repairTypeBiz.getAllRepairType();
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, repairTypeList);
        } catch (Exception e) {
            logger.error("RepairController--repairTypeList", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
    }


    /**
     * 添加报修
     */
    @RequestMapping("/addSaveRepair")
    @ResponseBody
    public Map<String, Object> addSaveRepair(@ModelAttribute("repair") Repair repair) {
        Map<String, Object> resultMap = null;
        try {
            Date date = new Date();
            //根据时间创建一个维修编号
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String number = sdf.format(date);

            repair.setStatus(0);
            repair.setNumber(number);

            //获取类型详情
            RepairType repairType = repairTypeBiz.findById(repair.getTypeId());
            repair.setFunctionType(repairType.getFunctionType());
            repairBiz.save(repair);

            // 发手机短信
            String tel = repair.getTelephone();
            if (StringUtils.isNotEmpty(tel) && StringUtils.isMobileNo(tel)) {
                Map<String, String> map = new HashedMap();
                map.put("mobiles", tel);
                map.put("context", "您的报修工单" + number + "已经提交, 请耐心等待维修, 谢谢支持!");
                map.put("sendType", "1");
                map.put("sendUserId", repair.getUserId().toString());
                map.put("receiveUserIds", "");
                Boolean isok = smsHessianService.sendMsg(map);
                System.out.println(isok);
            } else {
                return this.resultJson(ErrorCode.ERROR_DATA, "手机号不正确", null);
            }
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("RepairController--addSaveRepair", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }



    /**
     * 查看报修详情
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/descRepair")
    @ResponseBody
    public Map<String, Object> descRepair(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            RepairDto repair = repairBiz.getRepairDtosById(id);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, repair);
        } catch (Exception e) {
            logger.info("RepairController--descRepair", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 删除报修列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/delRepair")
    @ResponseBody
    public Map<String, Object> delRepair(HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            String id = request.getParameter("id");
            repairBiz.deleteById(Long.parseLong(id));
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("RepairController--delRepair", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 我的报修(历史记录)
     *
     * @return
     */
    @RequestMapping("/queryMyRepairList")
    @ResponseBody
    public Map<String, Object> queryMyRepairList(HttpServletRequest request,
                                                 @RequestParam(value = "userId") Long userId,
                                                 @RequestParam(value = "status", required = false) Integer status,
                                                 @ModelAttribute("pagination") Pagination pagination) {
        try {
            Repair repair=new Repair();
            repair.setUserId(userId);
            if(status!=null && status>-1){
                repair.setStatus(status);
            }
            List<RepairDto> repairList = repairBiz.getRepairsDtos(pagination, repair);
            Map<String, Object> dataMap=new HashMap<>();
            dataMap.put("repairList", repairList);
            dataMap.put("pagination", pagination);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, dataMap);
        } catch (Exception e) {
            logger.error("RepairController--queryMyRepairList", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
    }


    /**
     * 修改报修状态
     *
     * @param request
     * @param repair
     * @return
     */
    @RequestMapping("/updateRepairStatus")
    @ResponseBody
    public Map<String, Object> updateRepairStatus(HttpServletRequest request, @ModelAttribute("repair") Repair repair) {
        try {
            repairBiz.update(repair);
            if (repair.getStatus() == 2) {
                //获取当前系统的人的用户名
                SysUser sysUser = baseHessianBiz.getSysUserById(repair.getUserId());
                String planContext = sysUser.getUserName() + "提交了一条在" + repair.getRepairSite() + "的" + repair.getName() + "的" + repair.getContext() + "的报修信息,请您及时处理!";
                //维修员
                SysUser sysUser1 = baseHessianBiz.getSysUserById(repair.getUserId());
                Map<String, String> map = new HashedMap();
                map.put("mobiles", sysUser1.getMobile());
                map.put("context", planContext);
                map.put("sendType", "1");
                map.put("sendUserId", SysUserUtils.getLoginSysUserId(request).toString());
                map.put("receiveUserIds", "");
                Boolean isok = smsHessianService.sendMsg(map);
            }
        } catch (Exception e) {
            logger.info("RepairController--updateRepairStatus", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }






}
