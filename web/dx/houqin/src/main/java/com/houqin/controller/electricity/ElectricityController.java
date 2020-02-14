package com.houqin.controller.electricity;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.electricity.ElectricityBiz;
import com.houqin.biz.electricityType.EleSecTypeBiz;
import com.houqin.biz.electricityType.ElectricityTypeBiz;
import com.houqin.common.CommonConstants;
import com.houqin.dao.electricity.ElectricityDao;
import com.houqin.entity.electricity.Electricity;
import com.houqin.entity.electricity.ElectricityDto;
import com.houqin.entity.electricity.EletricityStatistic;
import com.houqin.entity.electricityType.EleSecType;
import com.houqin.entity.electricityType.ElectricityType;
import com.houqin.entity.electricityType.ElectricityTypeDTO;
import com.houqin.entity.electricityType.ElectricityTypeVO;
import com.houqin.entity.sysuser.SysUser;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用电管理
 * Electricity
 * Created by Administrator on 2016/12/15.
 */
@Controller
@RequestMapping("/admin/houqin")
public class ElectricityController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ElectricityController.class);
    //添加电表
    private static final String createElectricity = "/electricity/add-electricity";
    //用电列表
    private static final String electricityList = "/electricity/electricity_list";
    //更新用电量
    private static final String toUpdatelectricity = "/electricity/update_electricity";

    private static final String statisticEletricity = "/electricity/statistic_electricity";

    /**
     * 一年12个月
     */
    public static final int MONTH_YEAR = 12;

    private RedisCache redisCache = RedisCache.getInstance();

    @Autowired
    private ElectricityBiz electricityBiz;
    @Autowired
    private ElectricityTypeBiz electricityTypeBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private ElectricityDao electricityDao;
    @Autowired
    private EleSecTypeBiz eleSecTypeBiz;

    @InitBinder({"electricity"})
    public void initElectricity(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("electricity.");
    }

    /**
     * 电价列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/queryAllElectricity")
    public String queryAllElectricity(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("electricity") Electricity electricity) {
        try {
            String whereSql = GenerateSqlUtil.getSql(electricity);
            whereSql += " order by id desc";
            pagination.setRequest(request);
            List<ElectricityDto> electricityList = electricityBiz.getElectricityList(pagination, whereSql);
            List<ElectricityType> typeList = electricityTypeBiz.queryAllType();
            request.setAttribute("electricityList", electricityList);
            request.setAttribute("typeList", typeList);
            request.setAttribute("electricity", electricity);

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
            logger.error("ElectricityController.queryAllElectricity()--error", e);
            return this.setErrorPath(request, e);
        }
        return electricityList;
    }

    /**
     * 去添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddElectricity")
    public String addElectricity(HttpServletRequest request) {
        try {
            List<ElectricityType> typeList = electricityTypeBiz.queryAllType();
            request.setAttribute("typeList", typeList);
        } catch (Exception e) {
            logger.error("ElectricityController.addElectricity()--error", e);
            return this.setErrorPath(request, e);
        }
        return createElectricity;
    }

    /**
     * 查询二级用电区域
     */
    @RequestMapping("/findSecByParentType")
    @ResponseBody
    public Map<String, Object> findSecByParentType(Long typeId) {
        Map<String, Object> objMap;
        try {
            List<EleSecType> secTypes = eleSecTypeBiz.find(null,
                    "status = 0 and eleTypeId = " + typeId);
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, secTypes);
        } catch (Exception e) {
            logger.error("ElectricityController.findSecByParentType", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    /**
     * 添加电表
     *
     * @param request
     * @param electricity
     * @return
     */
    @RequestMapping("/addSaveElectricity")
    @ResponseBody
    public Map<String, Object> addSaveElectricity(HttpServletRequest request, @ModelAttribute("electricity") Electricity electricity) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            electricity.setUserId(userId);
            List<Electricity> electricities = electricityBiz.find(null, " typeId =" + electricity.getTypeId() + " and secTypeId=" + electricity.getSecTypeId() + "   and monthTime=" + "'" + electricity.getMonthTime() + "'");
            if (ObjectUtils.isNotNull(electricities)) {
                return this.resultJson(ErrorCode.ERROR_DATA, "当前月份已添加，请选择其他月份", null);
            }
            electricityBiz.save(electricity);
            redisCache.regionSet(CommonConstants.priceElectricity, electricity.getPrice());

        } catch (Exception e) {
            logger.info("ElectricityController--addSaveElectricity", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 去修改电表
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateElectricity")
    public String toUpdateElectricity(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            //用电区域下拉列表
            List<ElectricityType> typeList = electricityTypeBiz.queryAllType();
            request.setAttribute("typeList", typeList);

            //用电对象
            Electricity electricity = electricityBiz.findById(id);
            request.setAttribute("electricity", electricity);
            EleSecType secType = eleSecTypeBiz.findById(electricity.getSecTypeId() != null ? electricity.getSecTypeId() : 0);
            request.setAttribute("secType", secType);
            //提交人姓名,用于显示,id,用于提交修改
            SysUser sysUser = baseHessianBiz.getSysUserById(electricity.getUserId());
            request.setAttribute("userName", sysUser.getUserName());
            request.setAttribute("userId", sysUser.getId());

        } catch (Exception e) {
            logger.info("ElectricityController.toUpdateElectricity--error", e);
            return this.setErrorPath(request, e);
        }
        return toUpdatelectricity;
    }

    /**
     * 修改电表
     *
     * @param request
     * @param electricity
     * @return
     */
    @RequestMapping("/updateElectricity")
    @ResponseBody
    public Map<String, Object> updateElectricity(HttpServletRequest request, @ModelAttribute("electricity") Electricity electricity) {
        Map<String, Object> resultMap = null;
        try {
            electricityBiz.update(electricity);
            redisCache.regiontRemove(CommonConstants.priceElectricity);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("ElectricityController--updateElectricity", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 根据id删除电表
     *
     * @param request
     * @return
     */
    @RequestMapping("/delElectricity")
    @ResponseBody
    public Map<String, Object> delElectricity(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            electricityBiz.deleteById(Long.parseLong(id));
            redisCache.regiontRemove(CommonConstants.priceElectricity);
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


    /**
     * 确认用量
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/confirmEle")
    @ResponseBody
    public Map<String, Object> confirmEle(HttpServletRequest request,
                                          @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {

            Electricity electricity = electricityBiz.findById(id);
            electricity.setAffirm(1L);
            electricityBiz.update(electricity);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "确认成功", null);
        } catch (Exception e) {
            logger.error("OilController--updateOil", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, "系统错误,请稍后再试", null);
        }
        return resultMap;
    }


    /**
     * 统计用电量的使用量
     *
     * @param request
     * @return
     */
    @RequestMapping("/statisticElectricity")
    public String statisticElectricity(HttpServletRequest request) {
        try {
            //查询用电区域
            List<ElectricityType> electricityTypeList = electricityTypeBiz.queryAllType();
            request.setAttribute("electricityTypeList", electricityTypeList);
            String months = "";
            String whereSql = "1=1";
            String describe = "";
            Calendar c = Calendar.getInstance();
            String years = request.getParameter("year");//年份
            if (StringUtils.isEmpty(years)) {
                years = c.get(Calendar.YEAR) + "";
            }
            int lastyear = Integer.parseInt(years) - 1;//获取去年的年份
            int beforeyear = Integer.parseInt(years) - 2;//获取前年
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
            String type = request.getParameter("type");
            if (type != null && type != "") {
                whereSql += " and na.typeId=" + type;
            }


            // 1-12月
            List<EletricityStatistic> result = new ArrayList();
            StringBuilder powerRs = new StringBuilder();
            String[] strings = months.split(",");
            for (String s : strings) {
                EletricityStatistic n = new EletricityStatistic();
                n.setYear(Integer.parseInt(years));
                n.setMonth(Integer.valueOf(s.replace("月", "").replace("'", "")));
                String sql = " year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + years +
                        " and month(str_to_date(na.monthTime, '%Y-%m-%d'))=" + n.getMonth() +
                        " and na.typeId=" + type;
                if (type == null || "".equals(type)) {
                    sql = " year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + years + " and month(str_to_date(na.monthTime, '%Y-%m-%d'))=" + n.getMonth();
                }
                List<EletricityStatistic> naturalStatistics = electricityDao.queryEleStatisticByYear(sql);
                if (ObjectUtils.isNotNull(naturalStatistics)) {
                    n.setDegrees(naturalStatistics.get(0).getDegrees());
                }
                result.add(n);


                // 新增供电局 抄表数
                List<EletricityStatistic> powerStatistics =
                        electricityDao.queryElePowerByYear(" year(na.createTime)=" + years +
                                " and month(na.createTime)=" + n.getMonth());
                if (org.apache.commons.collections.CollectionUtils.isNotEmpty(powerStatistics)) {
                    powerRs.append(powerStatistics.get(0).getDegrees()).append(",");
                } else {
                    powerRs.append(0).append(",");
                }
            }

            if (result != null && result.size() > 0) {
                String data = "";
                for (EletricityStatistic n : result) {
                    data += n.getDegrees() + ",";
                }
                request.setAttribute("data", data);
            }
            request.setAttribute("naturalStatisticList", result);
            request.setAttribute("powerRs", powerRs);


            setThreeYearData(request, Integer.parseInt(years), lastyear, beforeyear);

            Integer sum = electricityDao.queryCountByYear(whereSql);
            request.setAttribute("sum", sum);
            request.setAttribute("type", type);
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
        return statisticEletricity;
    }

    private void setThreeYearData(HttpServletRequest request, int year, int lastyear, int beforeyear) {
        List<EletricityStatistic> result1 = new ArrayList(12);
        List<EletricityStatistic> result2 = new ArrayList(12);
        List<EletricityStatistic> result3 = new ArrayList(12);
        // 查询一年中每个月的总数
        List<EletricityStatistic> currentStatisticList = electricityDao.queryEleStatisticByYear(" year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + year);
        //查询去年的数量
        List<EletricityStatistic> lastStatisticList = electricityDao.queryEleStatisticByYear(" year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + lastyear);
        //查询前年的总数量
        List<EletricityStatistic> beforeStatisticList = electricityDao.queryEleStatisticByYear(" year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + beforeyear);
        //////////////////今年的数据
        for (int i = 0; i < MONTH_YEAR; i++) {
            EletricityStatistic n = new EletricityStatistic();
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
            for (EletricityStatistic n : result1) {
                currentData += n.getDegrees() + ",";
            }
            request.setAttribute("currentData", currentData);
        }

        /////////////////////////////////////////去年的数据
        for (int i = 0; i < 12; i++) {
            EletricityStatistic n = new EletricityStatistic();
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
            for (EletricityStatistic n : result2) {
                lastData += n.getDegrees() + ",";
            }
            request.setAttribute("lastData", lastData);
        }

        //////////////////////////////////////////前年的数据
        for (int i = 0; i < 12; i++) {
            EletricityStatistic n = new EletricityStatistic();
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
            for (EletricityStatistic n : result3) {
                beforeData += n.getDegrees() + ",";
            }
            request.setAttribute("beforeData", beforeData);
        }
    }

    /**
     * 导出用电使用量
     *
     * @param request
     * @param response
     */
    @RequestMapping("/exportElectricityExcel")
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
            String type = request.getParameter("type");
            if (type != null && type != "") {
                whereSql += " and typeId=" + type;
            }

            // 文件名
            String expName = year + "年用电使用统计_" + describe;
            // 表头信息
            String[] headName = {"月份", "使用量"};
            String head = "中共贵阳市委党校后勤管理处" + describe + "用电统计表";
            List<EletricityStatistic> eletricityStatistics = electricityDao.queryEleStatisticByYear(whereSql);
            List<EletricityStatistic> result = new ArrayList<>(12);
            if (ObjectUtils.isNotNull(eletricityStatistics)) {
                String[] strings = months.split(",");
                for (String s : strings) {
                    EletricityStatistic n = new EletricityStatistic();
                    n.setYear(year);
                    n.setMonth(Integer.valueOf(s.replace("月", "").replace("'", "")));
                    String sql = " year(na.createTime)=" + years + " and month(na.createTime)=" + n.getMonth() + " and na.typeId=" + type;
                    if (type == null || "".equals(type)) {
                        sql = " year(na.createTime)=" + years + " and month(na.createTime)=" + n.getMonth();
                    }
                    List<EletricityStatistic> naturalStatistics = electricityDao.queryEleStatisticByYear(sql);
                    if (ObjectUtils.isNotNull(naturalStatistics)) {
                        n.setDegrees(naturalStatistics.get(0).getDegrees());
                    }
                    result.add(n);
                }
            } else {
                for (int i = 0; i < 12; i++) {
                    EletricityStatistic n = new EletricityStatistic();
                    n.setYear(year);
                    n.setMonth(i + 1);
                    n.setDegrees(0);
                    result.add(n);
                }

                eletricityStatistics.forEach(
                        n -> result.set(n.getMonth() - 1, n)
                );
            }
            List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
            List<List<String>> list = electricityStatisticsJoint(result);

            File file = FileExportImportUtil.createHeadExcel(headName, list, expName, dir, head);
            srcfile.add(file);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> electricityStatisticsJoint(List<EletricityStatistic> eletricityStatistics) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        for (int i = 0; i < eletricityStatistics.size(); i++) {
            List<String> small = new ArrayList<String>();
            EletricityStatistic eletricityStatistic = eletricityStatistics.get(i);
            small.add(String.valueOf(eletricityStatistic.getMonth()));
            small.add(String.valueOf(eletricityStatistic.getDegrees()));
            list.add(small);
        }
        return list;
    }


    /**
     * 用电区域占比
     *
     * @return 各区域用电量
     */
    public
    @RequestMapping("regionalElectricityConsumptionRatio")
    ModelAndView RegionalElectricityConsumptionRatio(@RequestParam(value = "queryYear", required = false) Integer queryYear,
                                                     @RequestParam(value = "ids", required = false) String ids) {
        ModelAndView mv = new ModelAndView("/electricity/regionalElectricityConsumptionRatio");

        String sql = "1=1";
        // 查询所有的用电区域
        List<ElectricityType> electricityTypeList = electricityTypeBiz.find(null, sql);
        mv.addObject("electricityTypeList", electricityTypeList);

        if (ids != null && !"".equals(ids)) {
            sql += " and id in ( " + ids + " ) ";
            mv.addObject("ids", ids.split(","));
        }

        // 查询选中的用电区域
        List<ElectricityType> electricityTypes = electricityTypeBiz.find(null, sql);
        List<Long> typeIds = new ArrayList<>();
        electricityTypes.forEach(e -> typeIds.add(e.getId()));

        ElectricityTypeDTO electricityTypeDTO = new ElectricityTypeDTO();
        Calendar c = Calendar.getInstance();
        Integer calYear = c.get(Calendar.YEAR);
        mv.addObject("year", calYear);
        //获取年
        if (ObjectUtils.isNull(queryYear)) {
            electricityTypeDTO.setYear(calYear);
        } else {
            electricityTypeDTO.setYear(queryYear);
        }
        mv.addObject("queryYear", queryYear);


        // 查询各用点区域列表
        List<ElectricityTypeVO> typeVOS = new ArrayList<>();
        String number = "";
        String name = "";
        for (Long id : typeIds) {
            electricityTypeDTO.setTypeId(id);
            ElectricityTypeDTO e = electricityDao.getEnergyUsedById(electricityTypeDTO);
            ElectricityTypeVO vo = new ElectricityTypeVO(e.getTypeName(), e.getEnergyUsed());
            typeVOS.add(vo);

            number += vo.getNum() + ",";
            name += '\"' + vo.getName() + '\"' + ",";
        }

        mv.addObject("number", number);
        mv.addObject("name", name);
        mv.addObject("typeVOS", typeVOS);
        return mv;
    }

    /**
     * 查询上期指数
     *
     * @param typeId
     * @param secTypeId
     */
    @RequestMapping("/ajax/getLastPeriod")
    @ResponseBody
    public Map<String, Object> getLastPeriod(Long typeId, Long secTypeId, String monthTime) {
        try {
            List<Electricity> electricities = electricityBiz.find(null, " 1=1 and typeId = " + typeId + " and secTypeId=" + secTypeId + " and monthTime=" + "'" + monthTime + "'");
            return resultJson("0", "", ObjectUtils.isNotNull(electricities) ? StringUtils.isNotEmpty((electricities.get(0).getCurrentDegrees().toString())) ? electricities.get(0).getCurrentDegrees().toString() : "" : "");
        } catch (Exception e) {
            logger.error("getLastPeriod", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 导入用电量
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 17:47
     * @return:
     */
    @RequestMapping("/import/electricityImport")
    public String electricityImport(HttpServletRequest request, @RequestParam("myFile") MultipartFile myfile) {
        try {
            electricityBiz.electricityImport(myfile, request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ElectricityController.electricityImport", e);
        }
        return "redirect:/admin/houqin/queryAllElectricity.json";
    }

    /**
     * 批量导入
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 17:47
     * @return:
     */
    @RequestMapping("/import/toElectricityImport")
    public String toElectricityImport() {
        return "/electricity/electricity-import";
    }


}
