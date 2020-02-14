package com.houqin.controller.water;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.water.WaterBiz;
import com.houqin.biz.water.WaterTypeBiz;
import com.houqin.common.CommonConstants;
import com.houqin.common.DateEditor;
import com.houqin.dao.water.WaterDao;
import com.houqin.entity.water.Water;
import com.houqin.entity.water.WaterDto;
import com.houqin.entity.water.WaterStatistic;
import com.houqin.entity.water.WaterType;
import com.houqin.utils.DateUtils;
import com.houqin.utils.FileExportImportUtil;
import com.houqin.utils.GenerateSqlUtil;
import com.houqin.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description:
 * @author: lianyuchao
 * @Param:
 * @Return:
 * @Date: 2016/12/15
 */
@Controller
@RequestMapping("/admin/houqin")
public class WaterController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(WaterController.class);
    //用水量列表
    private static final String waterList = "/water/water_list";
    //添加用水量
    private static final String createWater = "/water/add-water";
    //更新用水量
    private static final String toUpdateWater = "/water/update-water";
    //用水量统计
    private static final String statisticWater = "/water/statistic_water";
    //用水量统计
    private static final String statisticPurpose = "/water/statistic_purpose_water";
    @Autowired
    private WaterBiz waterBiz;
    @Autowired
    private WaterTypeBiz waterTypeBiz;
    @Autowired
    private WaterDao waterDao;
    private RedisCache redisCache = RedisCache.getInstance();
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    @InitBinder("water")
    public void initWater(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
        binder.setFieldDefaultPrefix("water.");
    }

    /**
     * @Description:水吨数列表
     * @author: lianyuchao
     * @Param: pagination
     * @Return:
     * @Date: 2016/12/15
     */
    @RequestMapping("/queryAllWater")
    public String queryAllWater(HttpServletRequest request,
                                @ModelAttribute("pagination") Pagination pagination,
                                @ModelAttribute("water") Water water,
                                @RequestParam(required = false) String previousTime,
                                @RequestParam(required = false) String afterTime) {
        try {
            String whereSql = GenerateSqlUtil.getSql(water);
            if (StringUtils.isNotEmpty(previousTime)) {
                whereSql += " and createTime >= '" + previousTime + "'";
            }
            if (StringUtils.isNotEmpty(afterTime)) {
                whereSql += " and createTime <= '" + afterTime + "'";
            }
            whereSql += " order by id desc";
            pagination.setPageSize(10);
            pagination.setRequest(request);
            List<WaterDto> waterList = waterBiz.getWaterDtoList(pagination, whereSql);
            request.setAttribute("waterList", waterList);
            request.setAttribute("water", water);

            List<WaterType> waterTypeList = waterTypeBiz.find(null, "1=1");
            request.setAttribute("waterTypeList", waterTypeList);
            //查询用户角色
            Long userId = SysUserUtils.getLoginSysUserId(request);

            String roleIds = baseHessianBiz.queryUserRolesByUserId(userId);
            if (!com.houqin.utils.StringUtils.isEmpty(roleIds)) {
                if (roleIds.indexOf("31") != -1) {
                    request.setAttribute("flag", "true");
                }
            }
            request.setAttribute("roleIds", roleIds);

            request.setAttribute("previousTime", previousTime);
            request.setAttribute("afterTime", afterTime);
            request.setAttribute("water", water);
        } catch (Exception e) {
            logger.error("WaterController.queryAllWater()--error", e);
            return this.setErrorPath(request, e);
        }
        return waterList;

    }


    /**
     * 去添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddWater")
    public String addWater(HttpServletRequest request) {
        try {
            List<WaterType> waterTypeList = waterTypeBiz.find(null, "1=1");
            request.setAttribute("waterTypeList", waterTypeList);
        } catch (Exception e) {
            logger.error("WaterController.addWater()--error", e);
            return this.setErrorPath(request, e);
        }
        return createWater;
    }

    /**
     * 添加水表
     *
     * @param request
     * @param water
     * @return
     */
    @RequestMapping("/addSaveWater")
    @ResponseBody
    public Map<String, Object> addSaveWater(HttpServletRequest request, @ModelAttribute("water") Water water) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            water.setUserId(userId);
            waterBiz.save(water);
            redisCache.regionSet(CommonConstants.priceWater, water.getPrice());
        } catch (Exception e) {
            logger.info("WaterController--addSaveWater", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 去修改水表
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateWater")
    public String toUpdateWater(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            Water water = waterBiz.findById(id);
            request.setAttribute("water", water);

            List<WaterType> waterTypeList = waterTypeBiz.find(null, "1=1");
            request.setAttribute("waterTypeList", waterTypeList);

        } catch (Exception e) {
            logger.info("WaterController.toUpdateWater--error", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateWater;
    }

    /**
     * 修改水表
     *
     * @param request
     * @param water
     * @return
     */
    @RequestMapping("/UpdateWater")
    @ResponseBody
    public Map<String, Object> UpdateWater(HttpServletRequest request, @ModelAttribute("water") Water water) {
        Map<String, Object> resultMap = null;
        try {
            waterBiz.update(water);
            redisCache.regiontRemove(CommonConstants.priceWater);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("WaterController--UpdateWater", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 根据id删除水表
     *
     * @param request
     * @return
     */
    @RequestMapping("/delWater")
    @ResponseBody
    public Map<String, Object> delWater(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            waterBiz.deleteById(Long.parseLong(id));
            redisCache.regiontRemove(CommonConstants.priceWater);
        } catch (Exception e) {
            logger.error("WaterController--delWater", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    @RequestMapping("/prevWater")
    @ResponseBody
    public Map<String, Object> prevNatService(String dates, Long typeId) {
        Map<String, Object> objMap;
        try {
            String prevMonth = DateUtils.getPrevMonth(dates);
            List<Water> waters = waterBiz.find(null,
                    "waterType =" + typeId + " and monthTime ='" + prevMonth + "'");

            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(waters)) {
                Double read = waters.get(0).getCurRead();
                objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, read);
            } else {
                objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            }
        } catch (Exception e) {
            logger.error("NaturalController.prevNatService", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    /**
     * 确认用量
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/confirmWater")
    @ResponseBody
    public Map<String, Object> confirmWater(HttpServletRequest request,
                                            @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {

            Water water = waterBiz.findById(id);
            water.setAffirm(1L);
            waterBiz.update(water);
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
    @RequestMapping("/statisticWater")
    public String statisticWater(HttpServletRequest request) {
        try {

            String months = "";
            String whereSql = "1=1";
            String describe = "";
            Calendar c = Calendar.getInstance();
            String years = request.getParameter("year");//年份
            if (StringUtils.isEmpty(years)) {
                years = c.get(Calendar.YEAR) + "";
            }
            String month = request.getParameter("month");//月份
            String season = request.getParameter("season");//季度
            String queryType = request.getParameter("queryType");//季度
            if (years == null || years == "") {
                whereSql += " and  year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + years;
                describe = years + "年";
                months = "'1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'";
            } else {
                whereSql += " and  year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + years;
                describe = years + "年";
                months = "'1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'";
            }
            if (!"".equals(month) && month != null) {
                whereSql += " and  month(str_to_date(na.monthTime, '%Y-%m-%d'))=" + month;
                describe = years + "年" + month + "月";
                months = "'" + month + "月'";
            }
            if (!"".equals(season) && season != null) {
                switch (season) {
                    case "1":
                        whereSql += " and  month(str_to_date(na.monthTime, '%Y-%m-%d')) in (1,2,3)";
                        describe = years + "年第一季度";
                        months = "'1月','2月','3月'";
                        break;
                    case "2":
                        whereSql += " and  month(str_to_date(na.monthTime, '%Y-%m-%d')) in (4,5,6)";
                        describe = years + "年第二季度";
                        months = "'4月','5月','6月'";
                        break;
                    case "3":
                        whereSql += " and  month(str_to_date(na.monthTime, '%Y-%m-%d')) in (7,8,9)";
                        describe = years + "年第三季度";
                        months = "'7月','8月','9月'";
                        break;
                    case "4":
                        whereSql += " and  month(str_to_date(na.monthTime, '%Y-%m-%d')) in (10,11,12)";
                        describe = years + "年第四季度";
                        months = "'10月','11月','12月'";
                        break;
                }
            }
            int lastyear = Integer.parseInt(years) - 1;//获取去年的年份
            int beforeyear = Integer.parseInt(years) - 2;//获取前年
            
            // 查询一年中每个月的总数
            List<WaterStatistic> currentStatisticList = waterDao.queryWaterStatisticByYear(" year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + Integer.parseInt(years));
            //查询去年的数量
            List<WaterStatistic> lastStatisticList = waterDao.queryWaterStatisticByYear(" year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + lastyear);
            //查询前年的总数量
            List<WaterStatistic> beforeStatisticList = waterDao.queryWaterStatisticByYear(" year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + beforeyear);
            // 1-12月
            List<WaterStatistic> result = new ArrayList();
            List<WaterStatistic> result1 = new ArrayList(12);
            List<WaterStatistic> result2 = new ArrayList(12);
            List<WaterStatistic> result3 = new ArrayList(12);

            String[] strings = months.split(",");
            for (String s : strings) {
                WaterStatistic n = new WaterStatistic();
                n.setYear(Integer.parseInt(years));
                n.setMonth(Integer.valueOf(s.replace("月", "").replace("'", "")));
                List<WaterStatistic> naturalStatistics = waterDao.queryWaterStatisticByYear(" year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + years + " and month(str_to_date(na.monthTime, '%Y-%m-%d'))=" + n.getMonth());
                if(ObjectUtils.isNotNull(naturalStatistics)){
                    n.setAmount(naturalStatistics.get(0).getAmount());
                }
                result.add(n);
            }
            
            if (result != null && result.size() > 0) {
                String data = "";
                for (WaterStatistic n : result) {
                    data += n.getAmount() + ",";
                }
                request.setAttribute("data", data);
            }
            request.setAttribute("waterStatisticList", result);


            //////////////////今年的数据
            for (int i = 0; i < 12; i++) {
                WaterStatistic n = new WaterStatistic();
                n.setMonth(i + 1);
                n.setYear(Integer.parseInt(years));
                result1.add(n);
            }

            if (ObjectUtils.isNotNull(currentStatisticList)) {
                currentStatisticList.forEach(
                        n -> result1.set(n.getMonth() - 1, n)
                );
            }


            if (result1 != null && result1.size() > 0) {
                String currentData = "";
                for (WaterStatistic n : result1) {
                    currentData += n.getAmount() + ",";
                }
                request.setAttribute("currentData", currentData);
            }

            /////////////////////////////////////////去年的数据
            for (int i = 0; i < 12; i++) {
                WaterStatistic n = new WaterStatistic();
                n.setMonth(i + 1);
                n.setYear(Integer.parseInt(years));
                result2.add(n);
            }

            if (ObjectUtils.isNotNull(lastStatisticList)) {
                lastStatisticList.forEach(
                        n -> result2.set(n.getMonth() - 1, n)
                );
            }


            if (result2 != null && result2.size() > 0) {
                String lastData = "";
                for (WaterStatistic n : result2) {
                    lastData += n.getAmount() + ",";
                }
                request.setAttribute("lastData", lastData);
            }

            //////////////////////////////////////////前年的数据
            for (int i = 0; i < 12; i++) {
                WaterStatistic n = new WaterStatistic();
                n.setYear(Integer.parseInt(years));
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
                for (WaterStatistic n : result3) {
                    beforeData += n.getAmount() + ",";
                }
                request.setAttribute("beforeData", beforeData);
            }


            Integer sum = waterDao.queryWaterCountByYear(whereSql);
            request.setAttribute("sum", sum);
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
        return statisticWater;
    }


    /**
     * 导出用水使用量
     *
     * @param request
     * @param response
     */
    @RequestMapping("/exportWaterExcel")
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
            // 文件名
            String expName = year + "年用水使用统计_" + describe;
            // 表头信息
            String[] headName = {"月份", "使用量"};
            String head = "中共贵阳市委党校后勤管理处" + describe + "用水统计表";
            List<WaterStatistic> waterStatistics = waterDao.queryWaterStatisticByYear(whereSql);
            List<WaterStatistic> result = new ArrayList<>(12);
            if (ObjectUtils.isNotNull(waterStatistics)) {
                String[] strings = months.split(",");
                for (String s : strings) {
                    WaterStatistic n = new WaterStatistic();
                    n.setYear(year);
                    n.setMonth(Integer.valueOf(s.replace("月", "").replace("'", "")));
                    List<WaterStatistic> naturalStatistics = waterDao.queryWaterStatisticByYear(" year(na.createTime)=" + years + " and month(na.createTime)=" + n.getMonth());
                    if(ObjectUtils.isNotNull(naturalStatistics)){
                        n.setAmount(naturalStatistics.get(0).getAmount());
                    }
                    result.add(n);
                }
            } else {
                for (int i = 0; i < 12; i++) {
                    WaterStatistic n = new WaterStatistic();
                    n.setYear(year);
                    n.setMonth(i + 1);
                    n.setAmount(0);
                    result.add(n);
                }

                waterStatistics.forEach(
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

    public List<List<String>> waterStatisticsJoint(List<WaterStatistic> waterStatistics) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        for (int i = 0; i < waterStatistics.size(); i++) {
            List<String> small = new ArrayList<String>();
            WaterStatistic waterStatistic = waterStatistics.get(i);
            small.add(String.valueOf(waterStatistic.getMonth()));
            small.add(String.valueOf(waterStatistic.getAmount()));
            list.add(small);
        }
        return list;
    }

    /**
     * 用水占比统计
     *
     * @param request
     * @return
     */
    @RequestMapping("/statisticByWaterPurpose")
    public String statisticByWaterPurpose(HttpServletRequest request) {
        try {
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
            } else {
                whereSql += " and  year(na.createTime)=" + years;
                describe = years + "年";
            }
            if (!"".equals(month) && month != null) {
                whereSql += " and  month(na.createTime)=" + month;
                describe = years + "年" + month + "月";
            }
            if (!"".equals(season) && season != null) {
                switch (season) {
                    case "1":
                        whereSql += " and  month(na.createTime) in (1,2,3)";
                        describe = years + "年第一季度";
                        break;
                    case "2":
                        whereSql += " and  month(na.createTime) in (4,5,6)";
                        describe = years + "年第二季度";
                        break;
                    case "3":
                        whereSql += " and  month(na.createTime) in (7,8,9)";
                        describe = years + "年第三季度";
                        break;
                    case "4":
                        whereSql += " and  month(na.createTime) in (10,11,12)";
                        describe = years + "年第四季度";
                        break;
                }
            }
            request.setAttribute("describe", describe);
            request.setAttribute("years", years);
            request.setAttribute("months", month);
            request.setAttribute("season", season);
            request.setAttribute("queryType", queryType);
            //总数
            Integer count = waterDao.queryWaterCountByYear(whereSql.toString());
            request.setAttribute("count", count);
            List<WaterStatistic> waterStatisticList = waterDao.queryWaterPurposeByYear(whereSql.toString());
            //柱状图统计信息
            if (ObjectUtils.isNotNull(waterStatisticList)) {
                String number = "";
                String name = "";
                for (WaterStatistic w : waterStatisticList) {
                    number += w.getAmount() + ",";
                    name += '\"' + w.getType() + '\"' + ",";
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(number)) {
                    number = number.substring(0, number.length() - 1);
                    request.setAttribute("number", number);
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(name)) {
                    name = name.substring(0, name.length() - 1);
                    request.setAttribute("name", name);
                }
            }
            request.setAttribute("waterStatisticList", waterStatisticList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statisticPurpose;
    }

    /**
     * 批量导入
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 17:47
     * @return:
     */
    @RequestMapping("/import/toWaterImport")
    public String toWaterImport() {
        return "/water/water-import";
    }


    /**
     * 导入用气量
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 17:47
     * @return:
     */
    @RequestMapping("/import/waterImport")
    public String waterImport(HttpServletRequest request, @RequestParam("myFile") MultipartFile myfile) {
        try {
            waterBiz.waterImport(myfile, request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("WaterController.waterImport", e);
        }
        return "redirect:/admin/houqin/queryAllWater.json";
    }
}
