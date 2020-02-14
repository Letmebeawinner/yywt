package com.houqin.controller.oil;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.oil.OilBiz;
import com.houqin.biz.oil.OilTypeBiz;
import com.houqin.biz.oil.OilUseBiz;
import com.houqin.dao.oil.OilDao;
import com.houqin.entity.oil.*;
import com.houqin.entity.water.WaterStatistic;
import com.houqin.utils.FileExportImportUtil;
import com.houqin.utils.GenerateSqlUtil;
import com.houqin.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Administrator on 2017/6/13 0013.
 */
@Controller
@RequestMapping("/admin/houqin")
public class OilController extends BaseController {
    /**
     * 一年12个月
     */
    public static final int MONTH_NUM = 12;
    private static final String addOil = "/oil/add_oil"; //添加油用量
    private static final String oilList = "/oil/oil_list"; //油用量列表
    private static final String oilTypeList = "/oil/oilType_list"; //油类型列表
    private static final String oilUseList = "/oil/oilUse_list"; //油类型列表
    private static final String addOilType = "/oil/add_oilType"; //添加油类型
    private static final String addOilUse = "/oil/add_oilUse"; //添加油用途
    private static final String updateOilType = "/oil/update_oilType"; //更新油类型
    private static final String updateOilUse = "/oil/update_oilUse"; //更新油类型
    private static final String updateOil = "/oil/update_oil"; //更新油用量

    private static final String statisticOil = "/oil/statistic_oil";//用油量统计
    private static final String statisticPurposeOil = "/oil/statistic_purpose_oil";//用油量占比统计
    private static Logger logger = LoggerFactory.getLogger(OilController.class);
    //自动注入
    @Autowired
    private OilBiz oilBiz;

    @Autowired
    private OilDao oilDao;

    @Autowired
    private OilTypeBiz oilTypeBiz;
    @Autowired
    private OilUseBiz oilUseBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    //绑定表单
    @InitBinder({"oil"})
    public void initOil(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("oil.");
    }

    //绑定表单
    @InitBinder({"oilType"})
    public void initOilType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("oilType.");
    }

    //绑定表单
    @InitBinder({"oilUse"})
    public void initOilUse(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("oilUse.");
    }

    /**
     * 跳转到添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddOil")
    public String toAddOil(HttpServletRequest request) {
        try {
            List<OilUse> oilUseList = oilUseBiz.findAll();
            List<OilType> oilTypeList = oilTypeBiz.findAll();
            request.setAttribute("oilUseList", oilUseList);
            request.setAttribute("oilTypeList", oilTypeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addOil;
    }

    /**
     * 添加柴油用量
     *
     * @param request
     * @param oil
     * @return
     */
    @RequestMapping("/addOil")
    @ResponseBody
    public Map<String, Object> addOil(HttpServletRequest request, @ModelAttribute("oil") Oil oil) {
        Map<String, Object> resultMap = null;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            oil.setUserId(userId);
            oilBiz.save(oil);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.info("OilController--addOil", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误,请稍后再试", null);
        }
        return resultMap;
    }

    /**
     * 柴油用量列表
     *
     * @param request
     * @param pagination
     * @param oil
     * @return
     */
    @RequestMapping("/queryAllOil")
    public String queryAllOil(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,
                              @ModelAttribute("oil") Oil oil) {
        try {
            pagination.setPageSize(10);
            pagination.setRequest(request);
            String whereSql = GenerateSqlUtil.getSql(oil);
            whereSql += " order by id desc";
            List<OilDto> oilDtoList = oilBiz.getOilDtoList(pagination, whereSql);
            List<OilUse> oilUseList = oilUseBiz.findAll();
            List<OilType> oilTypeList = oilTypeBiz.findAll();
            request.setAttribute("oilUseList", oilUseList);
            request.setAttribute("oilTypeList", oilTypeList);
            request.setAttribute("oilDtoList", oilDtoList);
            request.setAttribute("oil", oil);

            //查询用户角色
            Long userId = SysUserUtils.getLoginSysUserId(request);

            String roleIds = baseHessianBiz.queryUserRolesByUserId(userId);
            if (!StringUtils.isEmpty(roleIds)) {
                if (roleIds.indexOf("31") != -1) {
                    request.setAttribute("flag", "true");
                }
            }
            request.setAttribute("roleIds", roleIds);

        } catch (Exception e) {
            logger.error("OilController--queryAllOil", e);
            return this.setErrorPath(request, e);
        }
        return oilList;
    }

    /**
     * 跳转到修改页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateOil")
    public String toUpdateOil(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            Oil oil = oilBiz.findById(id);
            List<OilUse> oilUseList = oilUseBiz.findAll();
            List<OilType> oilTypeList = oilTypeBiz.findAll();
            request.setAttribute("oilUseList", oilUseList);
            request.setAttribute("oilTypeList", oilTypeList);
            request.setAttribute("oil", oil);
        } catch (Exception e) {
            logger.error("OilController--toUpdateOil", e);
            return this.setErrorPath(request, e);
        }
        return updateOil;
    }

    /**
     * 修改柴油用量
     *
     * @param request
     * @param oil
     * @return
     */
    @RequestMapping("updateOil")
    @ResponseBody
    public Map<String, Object> updateOil(HttpServletRequest request, @ModelAttribute("oil") Oil oil) {
        Map<String, Object> resultMap = null;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            oil.setUserId(userId);
            oilBiz.update(oil);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("OilController--updateOil", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, "系统错误,请稍后再试", null);
        }
        return resultMap;
    }


    /**
     * 删除柴油用量
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/delOil")
    @ResponseBody
    public Map<String, Object> delOil(HttpServletRequest request,
                                      @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            oilBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("OilController--updateOil", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, "系统错误,请稍后再试", null);
        }
        return resultMap;
    }

    /**
     * 确认柴油用量
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/confirmOil")
    @ResponseBody
    public Map<String, Object> confirmOil(HttpServletRequest request,
                                          @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {

            Oil oil = oilBiz.findById(id);
            oil.setAffirm(1L);
            oilBiz.update(oil);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "确认成功", null);
        } catch (Exception e) {
            logger.error("OilController--updateOil", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, "系统错误,请稍后再试", null);
        }
        return resultMap;
    }


    /**
     * 统计天然气的使用量
     *
     * @param request
     * @return
     */
    @RequestMapping("/statisticOil")
    public String statisticNatural(HttpServletRequest request) {
        try {
            List<OilType> oilTypeList = oilTypeBiz.find(new Pagination(), " 1=1");
            request.setAttribute("oilTypeList", oilTypeList);
            String months = "";
            String whereSql = "1=1";
            String describe = "";
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);    //获取年
            int lastyear = year - 1;//获取去年的年份
            int beforeyear = year - 2;//获取前年
            String years = request.getParameter("year");//年份
            String month = request.getParameter("month");//月份
            String season = request.getParameter("season");//季度
            String queryType = request.getParameter("queryType");//季度
            if (years == null || years == "") {
                years = String.valueOf(year);
                whereSql += " and  year(na.createTime)=" + years;
                describe = years + "年";
                months = "'1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'";
            } else {
                whereSql += " and  year(na.createTime)=" + years;
                describe = years + "年";
                months = "'1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'";
            }
            if (!"".equals(month) && month != null) {
                whereSql += " and  month(na.createTime)=" + month;
                describe = years + "年" + month + "月";
                months = "'" + month + "月'";
            }
            if (!"".equals(season) && season != null) {
                switch (season) {
                    case "1":
                        whereSql += " and  month(na.createTime) in (1,2,3)";
                        describe = years + "年第一季度";
                        months = "'1月','2月','3月'";
                        break;
                    case "2":
                        whereSql += " and  month(na.createTime) in (4,5,6)";
                        describe = years + "年第二季度";
                        months = "'4月','5月','6月'";
                        break;
                    case "3":
                        whereSql += " and  month(na.createTime) in (7,8,9)";
                        describe = years + "年第三季度";
                        months = "'7月','8月','9月'";
                        break;
                    case "4":
                        whereSql += " and  month(na.createTime) in (10,11,12)";
                        describe = years + "年第四季度";
                        months = "'10月','11月','12月'";
                        break;
                }
            }
            String oilType = request.getParameter("oilType");
            if (oilType != null && oilType != "") {
                whereSql += " and oilType=" + oilType;
            }
            // 查询一年中每个月的总数
            List<OilStatistic> currentStatisticList = oilDao.queryOilStatisticByYear(" year(na.createTime)=" + year);
            //查询去年的数量
            List<OilStatistic> lastStatisticList = oilDao.queryOilStatisticByYear(" year(na.createTime)=" + lastyear);
            //查询前年的总数量
            List<OilStatistic> beforeStatisticList = oilDao.queryOilStatisticByYear(" year(na.createTime)=" + beforeyear);

            // 1-12月
            List<OilStatistic> result = new ArrayList();
            List<OilStatistic> result1 = new ArrayList(12);
            List<OilStatistic> result2 = new ArrayList(12);
            List<OilStatistic> result3 = new ArrayList(12);
            String[] strings = months.split(",");
            for (String s : strings) {
                OilStatistic n = new OilStatistic();
                n.setYear(year);
                n.setMonth(Integer.valueOf(s.replace("月", "").replace("'", "")));
                String sql = " year(na.createTime)=" + years + " and month(na.createTime)=" + n.getMonth() + " and na.oilType=" + oilType;
                if (oilType == null || "".equals(oilType)) {
                    sql = " year(na.createTime)=" + years + " and month(na.createTime)=" + n.getMonth();
                }
                List<OilStatistic> naturalStatistics = oilDao.queryOilStatisticByYear(sql);
                if (ObjectUtils.isNotNull(naturalStatistics)) {
                    n.setAmount(naturalStatistics.get(0).getAmount());
                }
                result.add(n);
            }
            if (result != null && result.size() > 0) {
                String data = "";
                for (OilStatistic n : result) {
                    data += n.getAmount() + ",";
                }
                request.setAttribute("data", data);
            }
            request.setAttribute("oilStatisticList", result);


            //////////////////今年的数据
            for (int i = 0; i < 12; i++) {
                OilStatistic n = new OilStatistic();
                n.setMonth(i + 1);
                n.setYear(year);
                result1.add(n);
            }

            if (ObjectUtils.isNotNull(currentStatisticList)) {
                currentStatisticList.forEach(
                        n -> result1.set(n.getMonth() - 1, n)
                );
            }


            if (result1 != null && result1.size() > 0) {
                String currentData = "";
                for (OilStatistic n : result1) {
                    currentData += n.getAmount() + ",";
                }
                request.setAttribute("currentData", currentData);
            }

            /////////////////////////////////////////去年的数据
            for (int i = 0; i < 12; i++) {
                OilStatistic n = new OilStatistic();
                n.setMonth(i + 1);
                n.setYear(year);
                result2.add(n);
            }

            if (ObjectUtils.isNotNull(lastStatisticList)) {
                lastStatisticList.forEach(
                        n -> result2.set(n.getMonth() - 1, n)
                );
            }


            if (result2 != null && result2.size() > 0) {
                String lastData = "";
                for (OilStatistic n : result2) {
                    lastData += n.getAmount() + ",";
                }
                request.setAttribute("lastData", lastData);
            }

            //////////////////////////////////////////前年的数据
            for (int i = 0; i < 12; i++) {
                OilStatistic n = new OilStatistic();
                n.setYear(year);
                n.setMonth(i + 1);
                result3.add(n);
            }

            if (ObjectUtils.isNotNull(beforeStatisticList)) {
                beforeStatisticList.forEach(
                        n -> result3.set(n.getMonth() - 1, n)
                );
            }


            if (result3 != null && result3.size() > 0) {
                String beforeData = "";
                for (OilStatistic n : result3) {
                    beforeData += n.getAmount() + ",";
                }
                request.setAttribute("beforeData", beforeData);
            }

            Integer sum = oilDao.queryCountByYear(whereSql);
            request.setAttribute("sum", sum);
            request.setAttribute("oilType", oilType);
            request.setAttribute("lastyear", lastyear);
            request.setAttribute("beforeyear", beforeyear);
            request.setAttribute("describe", describe);
            request.setAttribute("years", years);
            request.setAttribute("months", month);
            request.setAttribute("season", season);
            request.setAttribute("queryType", queryType);
            request.setAttribute("monthTypes", months);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return statisticOil;
    }


    /**
     * 通过油类型显示
     *
     * @param request
     * @return
     */
    @RequestMapping("/statisticByOilPurpose")
    public String statisticByOilType(HttpServletRequest request) {
        try {
            String months = "";
            String whereSql = "1=1";
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);    //获取年
            int lastyear = year - 1;//获取去年的年份
            int beforeyear = year - 2;//获取前年
            String years = request.getParameter("year");//年份
            String month = request.getParameter("month");//月份
            String season = request.getParameter("season");//季度
            String queryType = request.getParameter("queryType");//季度
            if (years == null || years == "") {
                years = String.valueOf(year);
                whereSql += " and  year(na.createTime)=" + years;

                months = "'1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'";
            } else {
                whereSql += " and  year(na.createTime)=" + years;

                months = "'1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'";
            }
            if (!"".equals(month) && month != null) {
                whereSql += " and  month(na.createTime)=" + month;

                months = "'" + month + "月'";
            }
            if (!"".equals(season) && season != null) {
                switch (season) {
                    case "1":
                        whereSql += " and  month(na.createTime) in (1,2,3)";

                        months = "'1月','2月','3月'";
                        break;
                    case "2":
                        whereSql += " and  month(na.createTime) in (4,5,6)";

                        months = "'4月','5月','6月'";
                        break;
                    case "3":
                        whereSql += " and  month(na.createTime) in (7,8,9)";

                        months = "'7月','8月','9月'";
                        break;
                    case "4":
                        whereSql += " and  month(na.createTime) in (10,11,12)";

                        months = "'10月','11月','12月'";
                        break;
                }
            }
            String oilType = request.getParameter("oilType");
            if (oilType != null && oilType != "") {
                whereSql += " and oilType=" + oilType;
            }
            //总数
            Integer count = oilDao.queryCountByYear(whereSql);
            request.setAttribute("count", count);
            request.setAttribute("oilType", oilType);
            request.setAttribute("lastyear", lastyear);
            request.setAttribute("beforeyear", beforeyear);

            request.setAttribute("years", years);
            request.setAttribute("months", month);
            request.setAttribute("season", season);
            request.setAttribute("queryType", queryType);
            request.setAttribute("monthTypes", months);

            //汽油查询
            String qiyou = whereSql + " and  na.purpose=4";
            // 柴油查询
            String chaiyou = whereSql + " and na.purpose=5";

            String other = whereSql + " and  na.purpose=6";
            // 查询一年中每个月的总数
            Integer lvhuaCount = oilDao.queryCountByYear(qiyou);
            Integer fadianCount = oilDao.queryCountByYear(chaiyou);
            Integer othserCount = oilDao.queryCountByYear(other);

            request.setAttribute("lvhuaCount", lvhuaCount);
            request.setAttribute("fadianCount", fadianCount);
            request.setAttribute("othserCount", othserCount);


            // 查询一年中每个月的总数
            List<OilStatistic> currentStatisticList = oilDao.queryOilStatisticByYear(String.valueOf(year));
            //查询去年的数量
            List<OilStatistic> lastStatisticList = oilDao.queryOilStatisticByYear(String.valueOf(lastyear));
            //查询前年的总数量
            List<OilStatistic> beforeStatisticList = oilDao.queryOilStatisticByYear(String.valueOf(beforeyear));

            // 1-12月
            List<OilStatistic> result = new ArrayList();
            List<OilStatistic> result1 = new ArrayList(12);
            List<OilStatistic> result2 = new ArrayList(12);
            List<OilStatistic> result3 = new ArrayList(12);
            String[] strings = months.split(",");
            for (String s : strings) {
                OilStatistic n = new OilStatistic();
                n.setYear(year);
                n.setMonth(Integer.valueOf(s.replace("月", "").replace("'", "")));
                String sql = " year(na.createTime)=" + years + " and month(na.createTime)=" + n.getMonth() + " and na.oilType=" + oilType;
                if (oilType == null || "".equals(oilType)) {
                    sql = " year(na.createTime)=" + years + " and month(na.createTime)=" + n.getMonth();
                }
                List<OilStatistic> naturalStatistics = oilDao.queryOilStatisticByYear(sql);
                if (ObjectUtils.isNotNull(naturalStatistics)) {
                    n.setAmount(naturalStatistics.get(0).getAmount());
                }
                result.add(n);
            }

            if (result != null && result.size() > 0) {
                String data = "";
                for (OilStatistic n : result) {
                    data += n.getAmount() + ",";
                }
                request.setAttribute("data", data);
            }
            request.setAttribute("oilStatisticList", result);


            //////////////////今年的数据
            for (int i = 0; i < 12; i++) {
                OilStatistic n = new OilStatistic();
                n.setMonth(i + 1);
                n.setYear(year);
                result1.add(n);
            }

            if (ObjectUtils.isNotNull(currentStatisticList)) {
                currentStatisticList.forEach(
                        n -> result1.set(n.getMonth() - 1, n)
                );
            }


            if (result1 != null && result1.size() > 0) {
                String currentData = "";
                for (OilStatistic n : result1) {
                    currentData += n.getAmount() + ",";
                }
                request.setAttribute("currentData", currentData);
            }

            /////////////////////////////////////////去年的数据
            for (int i = 0; i < 12; i++) {
                OilStatistic n = new OilStatistic();
                n.setMonth(i + 1);
                n.setYear(year);
                result2.add(n);
            }

            if (ObjectUtils.isNotNull(lastStatisticList)) {
                lastStatisticList.forEach(
                        n -> result2.set(n.getMonth() - 1, n)
                );
            }


            if (result2 != null && result2.size() > 0) {
                String lastData = "";
                for (OilStatistic n : result2) {
                    lastData += n.getAmount() + ",";
                }
                request.setAttribute("lastData", lastData);
            }

            //////////////////////////////////////////前年的数据
            for (int i = 0; i < 12; i++) {
                OilStatistic n = new OilStatistic();
                n.setYear(year);
                n.setMonth(i + 1);
                result3.add(n);
            }

            if (ObjectUtils.isNotNull(beforeStatisticList)) {
                beforeStatisticList.forEach(
                        n -> result3.set(n.getMonth() - 1, n)
                );
            }

            if (result3 != null && result3.size() > 0) {
                String beforeData = "";
                for (OilStatistic n : result3) {
                    beforeData += n.getAmount() + ",";
                }
                request.setAttribute("beforeData", beforeData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statisticPurposeOil;
    }


    /**
     * 查询油类型列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/oilTypeManage")
    public String queryAllOilType(HttpServletRequest request, @ModelAttribute("oilType") OilType oilType, @ModelAttribute("pagination") Pagination pagination) {
        try {
            pagination.setPageSize(10);
            String whereSql = GenerateSqlUtil.getSql(oilType);
            whereSql += " order by sort desc";
            pagination.setRequest(request);
            List<OilType> oilTypeList = oilTypeBiz.find(pagination, whereSql);
            request.setAttribute("oilTypeList", oilTypeList);
            request.setAttribute("oilType", oilType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oilTypeList;
    }

    /**
     * 去添加油量类型
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddOilType")
    public String toAddOilType(HttpServletRequest request) {
        return addOilType;
    }

    /**
     * 添加类型
     *
     * @param request
     * @param oilType
     * @return
     */
    @RequestMapping("/addOilType")
    @ResponseBody
    public Map<String, Object> addOilType(HttpServletRequest request, @ModelAttribute("oilType") OilType oilType) {
        Map<String, Object> resultMap = null;
        try {
            oilTypeBiz.save(oilType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误", null);
        }
        return resultMap;
    }

    /**
     * 删除类型
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/delOilType")
    @ResponseBody
    public Map<String, Object> delOilType(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            oilTypeBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误", null);
        }
        return resultMap;
    }

    /**
     * 去修改类型
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateOilType/{id}")
    public String toUpdateOilType(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            OilType oilType = oilTypeBiz.findById(id);
            request.setAttribute("oilType", oilType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateOilType;
    }

    /**
     * 修改类型信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateOilType")
    @ResponseBody
    public Map<String, Object> updateOilType(HttpServletRequest request, @ModelAttribute("oilType") OilType oilType) {
        Map<String, Object> resultMap = null;
        try {
            oilTypeBiz.update(oilType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误", null);
        }
        return resultMap;
    }

    /**
     * 查询用途
     *
     * @param request
     * @param oilUse
     * @param pagination
     * @return
     */
    @RequestMapping("/oilUseManage")
    public String oilUseManage(HttpServletRequest request, @ModelAttribute("oilUse") OilUse oilUse, @ModelAttribute("pagination") Pagination pagination) {
        try {
            pagination.setPageSize(10);
            String whereSql = GenerateSqlUtil.getSql(oilUse);
            whereSql += " order by sort desc";
            pagination.setRequest(request);
            List<OilUse> oilUseList = oilUseBiz.find(pagination, whereSql);
            request.setAttribute("oilUseList", oilUseList);
            request.setAttribute("oilUse", oilUse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oilUseList;
    }

    /**
     * 去添加油量用途
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddOilUser")
    public String toAddOilUser(HttpServletRequest request) {
        return addOilUse;
    }

    /**
     * 添加用途
     *
     * @param request
     * @return
     */
    @RequestMapping("/addOilUse")
    @ResponseBody
    public Map<String, Object> addOilUse(HttpServletRequest request, @ModelAttribute("oilUse") OilUse oilUse) {
        Map<String, Object> resultMap = null;
        try {
            oilUseBiz.save(oilUse);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误", null);
        }
        return resultMap;
    }


    /**
     * 删除用途
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/delOilUse")
    @ResponseBody
    public Map<String, Object> delOilUse(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            oilUseBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误", null);
        }
        return resultMap;
    }


    /**
     * 去修改用途
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateOilUse/{id}")
    public String toUpdateOilUse(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            OilUse oilUse = oilUseBiz.findById(id);
            request.setAttribute("oilUse", oilUse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateOilUse;
    }

    /**
     * 修改用途
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateOilUse")
    @ResponseBody
    public Map<String, Object> updateOilUse(HttpServletRequest request, @ModelAttribute("oilUse") OilUse oilUse) {
        Map<String, Object> resultMap = null;
        try {
            oilUseBiz.update(oilUse);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误", null);
        }
        return resultMap;
    }

    /**
     * 导出用油使用量
     *
     * @param request
     * @param response
     */
    @RequestMapping("/exportOilExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/FileList");
            String months = "";
            String whereSql = "1=1";
            String describe = "";
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);    //获取年
            String years = request.getParameter("year");//年份
            String month = request.getParameter("month");//月份
            String season = request.getParameter("season");//季度
            String queryType = request.getParameter("queryType");//季度
            if (years == null || years == "") {
                years = String.valueOf(year);
                whereSql += " and  year(na.createTime)=" + years;
                describe = years + "年";
                months = "'1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'";
            } else {
                whereSql += " and  year(na.createTime)=" + years;
                describe = years + "年";
                months = "'1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'";
            }
            if (!"".equals(month) && month != null) {
                whereSql += " and  month(na.createTime)=" + month;
                describe = years + "年" + month + "月";
                months = "'" + month + "月'";
            }
            if (!"".equals(season) && season != null) {
                switch (season) {
                    case "1":
                        whereSql += " and  month(na.createTime) in (1,2,3)";
                        describe = years + "年第一季度";
                        months = "'1月','2月','3月'";
                        break;
                    case "2":
                        whereSql += " and  month(na.createTime) in (4,5,6)";
                        describe = years + "年第二季度";
                        months = "'4月','5月','6月'";
                        break;
                    case "3":
                        whereSql += " and  month(na.createTime) in (7,8,9)";
                        describe = years + "年第三季度";
                        months = "'7月','8月','9月'";
                        break;
                    case "4":
                        whereSql += " and  month(na.createTime) in (10,11,12)";
                        describe = years + "年第四季度";
                        months = "'10月','11月','12月'";
                        break;
                }
            }
            String oilType = request.getParameter("oilType");
            if (oilType != null && oilType != "") {
                whereSql += " and oilType=" + oilType;
            }


            // 文件名
            String expName = year + "年用油使用统计_" + describe;
            // 表头信息
            String[] headName = {"月份", "使用量"};
            String head = "中共贵阳市委党校后勤管理处" + describe + "用油统计表";
            List<OilStatistic> oilStatistics = oilDao.queryOilStatisticByYear(whereSql);
            List<OilStatistic> result = new ArrayList<>();
            if (ObjectUtils.isNotNull(oilStatistics)) {
                String[] strings = months.split(",");
                for (String s : strings) {
                    OilStatistic n = new OilStatistic();
                    n.setYear(year);
                    n.setMonth(Integer.valueOf(s.replace("月", "").replace("'", "")));
                    String sql = " year(na.createTime)=" + years + " and month(na.createTime)=" + n.getMonth() + " and na.oilType=" + oilType;
                    if (oilType == null || "".equals(oilType)) {
                        sql = " year(na.createTime)=" + years + " and month(na.createTime)=" + n.getMonth();
                    }
                    List<OilStatistic> naturalStatistics = oilDao.queryOilStatisticByYear(sql);
                    if (ObjectUtils.isNotNull(naturalStatistics)) {
                        n.setAmount(naturalStatistics.get(0).getAmount());
                    }
                    result.add(n);
                }
            } else {
                for (int i = 0; i < 12; i++) {
                    OilStatistic n = new OilStatistic();
                    n.setYear(year);
                    n.setMonth(i + 1);
                    n.setAmount(0);
                    result.add(n);
                }

                oilStatistics.forEach(
                        n -> result.set(n.getMonth() - 1, n)
                );
            }
            List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
            List<List<String>> list = waterStatisticsJoint(result);

            File file = FileExportImportUtil.createHeadExcel(headName, list, expName, dir, head);
            srcfile.add(file);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> waterStatisticsJoint(List<OilStatistic> oilStatistics) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        for (int i = 0; i < oilStatistics.size(); i++) {
            List<String> small = new ArrayList<String>();
            OilStatistic oilStatistic = oilStatistics.get(i);
            small.add(String.valueOf(oilStatistic.getMonth()));
            small.add(String.valueOf(oilStatistic.getAmount()));
            list.add(small);
        }
        return list;
    }

}
