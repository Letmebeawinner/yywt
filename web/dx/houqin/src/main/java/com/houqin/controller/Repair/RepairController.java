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
 * 报修管理
 *
 * @author ccl
 * @create 2016-12-10-17:43
 */
@Controller
@RequestMapping("/admin/houqin")
public class RepairController extends BaseController {

    private static final String createRepair = "/repair/add-repair";//添加报修
    private static final String toUpdateRepair = "/repair/update-repair";//修改报修
    private static final String descRepair = "/repair/desc-repair";//报修明细
    private static final String repairList = "/repair/repair_list";//报修列表
    private static final String HqRepairList = "/repair/houqin_repair_list";//报修列表
    private static final String InforRepairList = "/repair/information_repair_list";//报修列表
    private static final String myRepairList = "/repair/myRepair_list";//我的报修
    private static final String cancelRepairList = "/repair/mycancleRepair_list";//我处理的报修
    private static final String handleRepair = "/repair/handleRepair";//处理报修
    private static final String statistic_repair = "/repair/statistic_repair";//维修统计
    private static final String statistic_repair_detail = "/repair/statistic_repair_detail";//维修详细统计
    private static final String statistic_repair_desc = "/repair/desc-repair-content";//维修详细统计
    private static Logger logger = LoggerFactory.getLogger(RepairController.class);

    /**
     * 一年12个月
     */
    public static final int MONTH_NUM = 12;

    @Autowired
    private RepairBiz repairBiz;

    @Autowired
    private RepairTypeBiz repairTypeBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private SmsHessianService smsHessianService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RepairDao repairDao;

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
     * 查询报修列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/queryAllRepair")
    public String queryAllRepairList(HttpServletRequest request,
                                     @ModelAttribute("pagination") Pagination pagination,
                                     @ModelAttribute("repair") Repair repair) {
        try {
            //查询所有维修列表
            List<RepairType> repairTypeList = repairTypeBiz.getAllRepairType();
            request.setAttribute("repairTypeList", repairTypeList);

            //根据调价查询维修列表
            pagination.setRequest(request);
            List<RepairDto> repairList = repairBiz.getRepairsDtos(pagination, repair);
            if (ObjectUtils.isNotNull(repairList)) {
                for (int i = 0; i < repairList.size(); i++) {
                    SysUser sysUser = baseHessianBiz.getSysUserById(repairList.get(i).getPepairPeopleId());
                    repairList.get(i).setSysUser(sysUser);
                }
            }
            request.setAttribute("repairList", repairList);
        } catch (Exception e) {
            logger.error("RepairController--queryAllRepairList", e);
            return this.setErrorPath(request, e);
        }
        return repairList;
    }

    /**
     * 查询后勤报修列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/queryHqRepairList")
    public String queryHqRepairList(HttpServletRequest request,
                                    @ModelAttribute("pagination") Pagination pagination,
                                    @ModelAttribute("repair") Repair repair) {
        try {
            //查询所有维修列表
            List<RepairType> repairTypeList = repairTypeBiz.getRepairType(0);
            request.setAttribute("repairTypeList", repairTypeList);

            //根据调价查询维修列表
            pagination.setRequest(request);
            List<RepairDto> repairList = repairBiz.getAllRepairsDtos(pagination, repair, 0);
            if (ObjectUtils.isNotNull(repairList)) {
                for (int i = 0; i < repairList.size(); i++) {
                    SysUser sysUser = baseHessianBiz.getSysUserById(repairList.get(i).getPepairPeopleId());
                    repairList.get(i).setSysUser(sysUser);
                }
            }
            request.setAttribute("repairList", repairList);
        } catch (Exception e) {
            logger.error("RepairController--queryAllRepairList", e);
            return this.setErrorPath(request, e);
        }
        return HqRepairList;
    }

    /**
     * 查询信息处报修列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/queryXxRepairList")
    public String queryXxRepairList(HttpServletRequest request,
                                    @ModelAttribute("pagination") Pagination pagination,
                                    @ModelAttribute("repair") Repair repair) {
        try {
            //查询所有维修列表
            List<RepairType> repairTypeList = repairTypeBiz.getRepairType(1);
            request.setAttribute("repairTypeList", repairTypeList);

            //根据调价查询维修列表
            pagination.setRequest(request);
            List<RepairDto> repairList = repairBiz.getAllRepairsDtos(pagination, repair, 1);
            if (ObjectUtils.isNotNull(repairList)) {
                for (int i = 0; i < repairList.size(); i++) {
                    SysUser sysUser = baseHessianBiz.getSysUserById(repairList.get(i).getPepairPeopleId());
                    repairList.get(i).setSysUser(sysUser);
                }
            }
            request.setAttribute("repairList", repairList);
        } catch (Exception e) {
            logger.error("RepairController--queryAllRepairList", e);
            return this.setErrorPath(request, e);
        }
        return InforRepairList;
    }

    /**
     * 微信导出
     *
     * @param request
     * @param response
     * @param repair
     * @param pagination
     */

    @RequestMapping("/repairExport")
    public void userListExport(HttpServletRequest request, HttpServletResponse response,
                               @ModelAttribute("repair") Repair repair,
                               @ModelAttribute("pagination") Pagination pagination) {
        try {
            // 指定文件生成路径
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/repair");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
            // 文件名
            String expName = "维修统计_" + df.format(new Date());
            // 表头信息
            String[] headName = {"ID", "报修编号", "报修人", "报修物品", "报修分类", "报修地点", "报修时间", "状态", "指派维修人员"};
            //根据调价查询维修列表
            pagination.setRequest(request);
            List<RepairDto> repairList = repairBiz.getRepairsDtos(pagination, repair);
            if (ObjectUtils.isNotNull(repairList)) {
                for (int i = 0; i < repairList.size(); i++) {
                    SysUser sysUser = baseHessianBiz.getSysUserById(repairList.get(i).getPepairPeopleId());
                    repairList.get(i).setSysUser(sysUser);
                }
            }
            List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
            List<List<String>> list = repairJoint(repairList);
            File file = FileExportImportUtil.createExcel(headName, list, expName, dir);
            srcfile.add(file);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 学员信息excel格式拼接
     *
     * @return
     */
    public List<List<String>> repairJoint(List<RepairDto> repairDtoList) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
        for (int i = 0; i < repairDtoList.size(); i++) {
            RepairDto repairDto = repairDtoList.get(i);
            if (ObjectUtils.isNotNull(repairDto)) {
                List<String> small = new ArrayList<String>();
                small.add((i + 1) + "");
                small.add(StringUtils.isNotEmpty(repairDto.getNumber()) ? repairDto.getNumber() : "");
                small.add(StringUtils.isNotEmpty(repairDto.getUserName()) ? repairDto.getUserName() : "");
                small.add(StringUtils.isNotEmpty(repairDto.getName()) ? repairDto.getName() : "");
                small.add(StringUtils.isNotEmpty(repairDto.getTypeName()) ? repairDto.getTypeName() : "");
                small.add(StringUtils.isNotEmpty(repairDto.getRepairSite()) ? repairDto.getRepairSite() : "");
                if (ObjectUtils.isNotNull(repairDto.getCreateTime())) {
                    Timestamp timestamp = repairDto.getCreateTime();
                    Date date = new Date(timestamp.getTime());
                    small.add(df.format(date));
                } else {
                    small.add("");
                }
                String status = "";
                if (repairDto.getStatus() == 0) {
                    status = "未处理";
                } else if (repairDto.getStatus() == 1) {
                    status = "正维修";
                } else if (repairDto.getStatus() == 2) {
                    status = "已维修";
                } else {
                    status = "已取消";
                }
                small.add(status);
                SysUser sysUser = repairDto.getSysUser();
                if (ObjectUtils.isNotNull(sysUser)) {
                    small.add(sysUser.getUserName());
                } else {
                    small.add("");
                }
                list.add(small);
            }
        }
        return list;
    }

    /**
     * 初始化添加维修页面
     *
     * @param request HttpServletRequest
     * @return 添加页面
     */
    @RequestMapping("/toAddRepair")
    public String addRepair(HttpServletRequest request) {
        try {
            List<RepairType> repairTypeList = repairTypeBiz.getAllRepairType();
            request.setAttribute("repairTypeList", gson.toJson(repairTypeList));
        } catch (Exception e) {
            logger.error("RepairController--addRepair", e);
            return this.setErrorPath(request, e);
        }
        return createRepair;
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

            Long userId = SysUserUtils.getLoginSysUserId(request);
            repair.setStatus(0);
            repair.setUserId(userId);
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
                map.put("sendUserId", userId.toString());
                map.put("receiveUserIds", "");
                Boolean isok = smsHessianService.sendMsg(map);
                System.out.println(isok);
            } else {
                return this.resultJson(ErrorCode.ERROR_DATA, "手机号不正确", null);
            }
            //获取当前系统的人的用户名
            SysUser sysUser = baseHessianBiz.getSysUserById(userId);
            //工程主管
            List<Long> userIds = baseHessianBiz.queryUserIdsByRoleId(Long.parseLong(CommonConstants.gczg));
            logger.error("11111111111111111111111111111111111111111111111#" + userIds.toString(), userIds);
            if (ObjectUtils.isNotNull(userIds)) {
                for (Long _userId : userIds) {
                    SysUser sysUser2 = baseHessianBiz.getSysUserById(_userId);
                    logger.error("222222222222222222222222222222222222222222222222#" + sysUser2.toString(), sysUser2);
                    if (ObjectUtils.isNotNull(sysUser2)) {
                        String planContext = sysUser.getUserName() + "提交了一条在" + repair.getRepairSite() + "的" + repair.getName() + "的" + repair.getContext() + "的报修信息,请您及时安排相关人员处理!";
                        Map<String, String> map1 = new HashedMap();
                        map1.put("mobiles", sysUser2.getMobile());
                        map1.put("context", planContext);
                        map1.put("sendType", "1");
                        map1.put("sendUserId", SysUserUtils.getLoginSysUserId(request).toString());
                        map1.put("receiveUserIds", "");
                        Boolean isok1 = smsHessianService.sendMsg(map1);
                        logger.error("工程主管RepairController--addSaveRepair", isok1);
                    }
                }
            }
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("RepairController--addSaveRepair", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 去修改报修
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateRepair")
    public String toUpdateRepair(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            RepairDto repair = repairBiz.getRepairDtosById(id);
            request.setAttribute("repair", repair);
        } catch (Exception e) {
            logger.info("RepairController--toUpdateRepair", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateRepair;
    }

    /**
     * 添加维修人员
     *
     * @param request
     * @param peairPeopleId
     * @param id
     * @return
     */
    @RequestMapping("/toAddRepairPeairPeople")
    @ResponseBody
    public Map<String, Object> toAddRepairPeairPeople(HttpServletRequest request, @RequestParam("peairPeopleId") Long peairPeopleId, @RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            Repair repair = new Repair();
            repair.setId(id);
            repair.setPepairPeopleId(peairPeopleId);
            repairBiz.update(repair);

            Repair repair1 = repairBiz.findById(id);
            //获取当前系统的人的用户名
            SysUser sysUser = baseHessianBiz.getSysUserById(repair1.getUserId());
            String planContext = sysUser.getUserName() + "提交了一条在" + repair1.getRepairSite() + "的" + repair1.getName() + "的" + repair1.getContext() + "的报修信息,请您及时处理!";
            //维修员
            SysUser sysUser1 = baseHessianBiz.getSysUserById(peairPeopleId);
            Map<String, String> map = new HashedMap();
            map.put("mobiles", sysUser1.getMobile());
            map.put("context", planContext);
            map.put("sendType", "1");
            map.put("sendUserId", SysUserUtils.getLoginSysUserId(request).toString());
            map.put("receiveUserIds", "");
            Boolean isok = smsHessianService.sendMsg(map);

            //工程主管
            List<Long> userIds = baseHessianBiz.queryUserIdsByRoleId(Long.parseLong(CommonConstants.gczg));
            if (ObjectUtils.isNotNull(userIds)) {
                for (Long userId : userIds) {
                    SysUser sysUser2 = baseHessianBiz.getSysUserById(userId);
                    if (ObjectUtils.isNotNull(sysUser2)) {
                        Map<String, String> map1 = new HashedMap();
                        map1.put("mobiles", sysUser1.getMobile());
                        map1.put("context", planContext);
                        map1.put("sendType", "1");
                        map1.put("sendUserId", SysUserUtils.getLoginSysUserId(request).toString());
                        map1.put("receiveUserIds", "");
                        Boolean isok1 = smsHessianService.sendMsg(map);
                        logger.error("RepairController--toAddRepairPeairPeople", isok1);
                    }
                }
            }
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加维修人员成功", null);
        } catch (Exception e) {
            logger.info("RepairController--toAddRepairPeairPeople", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
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
    public String descRepair(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            RepairDto repair = repairBiz.getRepairDtosById(id);
            request.setAttribute("repairs", repair);
        } catch (Exception e) {
            logger.info("RepairController--descRepair", e);
            return this.setErrorPath(request, e);
        }
        return descRepair;
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
     * 我的报修
     *
     * @return
     */
    @RequestMapping("/queryMyRepairList")
    public String queryMyRepairList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("repair") Repair repair) {
        try {
            //查询所有维修列表
            List<RepairType> repairTypeList = repairTypeBiz.getAllRepairType();
            request.setAttribute("repairTypeList", repairTypeList);

            repair.setUserId(SysUserUtils.getLoginSysUserId(request));
            List<RepairDto> repairList = repairBiz.getRepairsDtos(pagination, repair);
            request.setAttribute("repairList", repairList);
        } catch (Exception e) {
            logger.error("RepairController--queryMyRepairList", e);
            return this.setErrorPath(request, e);
        }
        return myRepairList;
    }


    /**
     * 报修处理
     *
     * @return
     */
    @RequestMapping("/cancelRepairList")
    public String cancelRepairList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("repair") Repair repair) {
        try {
            repair.setPepairPeopleId(SysUserUtils.getLoginSysUserId(request));
            List<RepairDto> repairList = repairBiz.getRepairsDtos(pagination, repair);
            request.setAttribute("repairList", repairList);
        } catch (Exception e) {
            logger.info("RepairController--cancelRepairList", e);
            return this.setErrorPath(request, e);
        }
        return cancelRepairList;
    }

    /**
     * 修改报修状态
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateRepairStatus")
    public String toUpdateRepairStatus(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            RepairDto repairs = repairBiz.getRepairDtosById(id);
            request.setAttribute("repairs", repairs);
        } catch (Exception e) {
            logger.info("RepairController--toUpdateRepairStatus", e);
            return this.setErrorPath(request, e);
        }
        return handleRepair;
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
                repair = repairBiz.findById(repair.getId());
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

    /**
     * 取消报修
     *
     * @param request
     * @return
     */
    @RequestMapping("/cancleRepair")
    @ResponseBody
    public Map<String, Object> cancleRepair(HttpServletRequest request, @ModelAttribute("repair") Repair repair) {
        try {

        } catch (Exception e) {
            logger.info("RepairController--cancleRepair", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


    /**
     * @Description:修改审核
     * @author: ccl
     * @Param: [request, repair]
     * @Return: java.util.Map<java.lang.String                                                               ,                                                               java.lang.Object>
     * @Date: 2016-12-17
     */
    @RequestMapping("/checkRepair")
    @ResponseBody
    public Map<String, Object> checkRepair(HttpServletRequest request, @ModelAttribute("repair") Repair repair) {
        Map<String, Object> resultMap = null;
        try {
            repairBiz.update(repair);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("RepairController--checkRepair", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 评价维修
     */
    @RequestMapping("/toAppraise")
    public ModelAndView toAppraise(@RequestParam("repairId") Long id) {
        ModelAndView mv = new ModelAndView("/repair/appraise_repair");
        mv.addObject("repairId", id);
        return mv;
    }

    /**
     * 添加报修评价
     */
    @RequestMapping("/saveAppraise")
    @ResponseBody
    public Map<String, Object> saveAppraise(@ModelAttribute("repair") Repair repair) {
        try {
            Integer flag = repairBiz.update(repair);
            if (flag > 0) {
                return this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
            } else {
                return this.resultJson(ErrorCode.SUCCESS, "未做修改", null);
            }
        } catch (Exception e) {
            logger.error("RepairController--saveAppraise", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
    }


    /**
     * 维修统计
     *
     * @param request
     * @return
     */
    @RequestMapping("/maintenanceStatistics")
    public String maintenanceStatistics(HttpServletRequest request) {
        try {
            String year = request.getParameter("year");
            Calendar c = Calendar.getInstance();
            int years = c.get(Calendar.YEAR);    //获取年

            if (year == null || year == "") {
                year = String.valueOf(years);
            }
            request.setAttribute("year", year);
            List<RepairStatistics> repairStatistics = repairDao.queryMaintenanceStatistics(year);
            List<RepairStatistics> result = new ArrayList<>(12);
            Integer sum = 0;
            if (ObjectUtils.isNotNull(repairStatistics)) {
                for (int i = 0; i < MONTH_NUM; i++) {
                    RepairStatistics n = new RepairStatistics();
                    n.setYear(Integer.parseInt(year));
                    n.setMonth(i + 1);
                    n.setYiMaintenance(0);
                    n.setWeiMaintenance(0);
                    n.setCount(0);
                    result.add(n);
                }

                repairStatistics.forEach(
                        n -> result.set(n.getMonth() - 1, n)
                );
                for (int i = 0; i < result.size(); i++) {
                    sum += result.get(i).getCount();
                }
            } else {
                for (int i = 0; i < MONTH_NUM; i++) {
                    RepairStatistics n = new RepairStatistics();
                    n.setYear(Integer.parseInt(year));
                    n.setMonth(i + 1);
                    n.setWeiMaintenance(0);
                    n.setCount(0);
                    n.setYiMaintenance(0);
                    result.add(n);
                }

                repairStatistics.forEach(
                        n -> result.set(n.getMonth() - 1, n)
                );
            }
            request.setAttribute("result", result);
            request.setAttribute("sum", sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statistic_repair;
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/FileList");
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            String year = request.getParameter("year");
            String date = df.format(new Date());
            // 文件名
            String expName = year + "年维修统计_" + date;
            // 表头信息
            String[] headName = {"月份", "未维修记录", "维修总记录", "已维修记录"};
            String head = "中共贵阳市委党校后勤管理处" + year + "年维修统计表";
            List<RepairStatistics> repairStatistics = repairDao.queryMaintenanceStatistics(year);
            List<RepairStatistics> result = new ArrayList<>(12);
            if (ObjectUtils.isNotNull(repairStatistics)) {
                for (int i = 0; i < MONTH_NUM; i++) {
                    RepairStatistics n = new RepairStatistics();
                    n.setYear(Integer.parseInt(year));
                    n.setYiMaintenance(0);
                    n.setMonth(i + 1);
                    n.setWeiMaintenance(0);
                    n.setCount(0);
                    result.add(n);
                }

                repairStatistics.forEach(
                        n -> result.set(n.getMonth() - 1, n)
                );
            } else {
                for (int i = 0; i < MONTH_NUM; i++) {
                    RepairStatistics n = new RepairStatistics();
                    n.setYear(Integer.parseInt(year));
                    n.setMonth(i + 1);
                    n.setWeiMaintenance(0);
                    n.setYiMaintenance(0);
                    n.setCount(0);
                    result.add(n);
                }

                repairStatistics.forEach(
                        n -> result.set(n.getMonth() - 1, n)
                );
            }
            List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
            List<List<String>> list = repairStatisticsJoint(result);

            File file = FileExportImportUtil.createHeadExcel(headName, list, expName, dir, head);
            srcfile.add(file);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> repairStatisticsJoint(List<RepairStatistics> repairStatistics) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        for (int i = 0; i < repairStatistics.size(); i++) {
            List<String> small = new ArrayList<String>();
            RepairStatistics repairStatistics1 = repairStatistics.get(i);
            small.add(repairStatistics1.getMonth().toString());
            small.add(repairStatistics1.getWeiMaintenance() > 0 ? repairStatistics1.getWeiMaintenance().toString() : "0");
            small.add(repairStatistics1.getCount() > 0 ? repairStatistics1.getCount().toString() : "0");
            small.add(repairStatistics1.getYiMaintenance() > 0 ? repairStatistics1.getYiMaintenance().toString() : "0");
            list.add(small);
        }
        return list;
    }


    /**
     * 维修数详细统计
     *
     * @param request
     * @return
     */
    @RequestMapping("/repairDetailStatistic")
    public String repairDetailStatistic(HttpServletRequest request) {
        try {
            String year = request.getParameter("year");
            Calendar c = Calendar.getInstance();
            int years = c.get(Calendar.YEAR);    //获取年

            if (year == null || year == "") {
                year = String.valueOf(years);
            }
            request.setAttribute("year", year);
            List<RepairStatistics> repairStatistics = repairDao.queryMaintenanceStatistics(year);
            List<RepairStatistics> result = new ArrayList<>(12);
            Integer sum = 0;
            if (ObjectUtils.isNotNull(repairStatistics)) {
                for (int i = 0; i < MONTH_NUM; i++) {
                    RepairStatistics n = new RepairStatistics();
                    n.setYear(Integer.parseInt(year));
                    n.setMonth(i + 1);
                    n.setYiMaintenance(0);
                    n.setWeiMaintenance(0);
                    n.setCount(0);
                    result.add(n);
                }

                repairStatistics.forEach(
                        n -> result.set(n.getMonth() - 1, n)
                );
                for (int i = 0; i < result.size(); i++) {
                    sum += result.get(i).getCount();
                }
            } else {
                for (int i = 0; i < MONTH_NUM; i++) {
                    RepairStatistics n = new RepairStatistics();
                    n.setYear(Integer.parseInt(year));
                    n.setMonth(i + 1);
                    n.setWeiMaintenance(0);
                    n.setCount(0);
                    n.setYiMaintenance(0);
                    result.add(n);
                }

                repairStatistics.forEach(
                        n -> result.set(n.getMonth() - 1, n)
                );
            }
            request.setAttribute("result", result);
            request.setAttribute("sum", sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statistic_repair_detail;
    }


    //当月的分类的详情
    @RequestMapping("/repairStatisticInfo")
    public String repairStatisticInfo(HttpServletRequest request, @RequestParam(value = "month", required = false) Integer month,
                                      @RequestParam(value = "year", required = false) Integer year) {
        try {
            Calendar c = Calendar.getInstance();
            int years = c.get(Calendar.YEAR);    //获取年
            int months = c.get(Calendar.MONTH);    //获取月

            if (year == null) {
                year = years;
            }
            if (month == null) {
                month = months;
            }

            List<RepairStatistics> repairStatisticsList = repairDao.queryRepairNumByType(month, year);
            request.setAttribute("repairStatisticsList", repairStatisticsList);
            request.setAttribute("month", month);
            request.setAttribute("year", year);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statistic_repair_desc;
    }


}
