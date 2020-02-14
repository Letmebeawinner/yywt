package com.houqin.controller.natural;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.natural.NaturalBiz;
import com.houqin.biz.natural.NaturalTypeBiz;
import com.houqin.common.DateEditor;
import com.houqin.dao.natural.NaturalDao;
import com.houqin.entity.natural.Natural;
import com.houqin.entity.natural.NaturalStatistic;
import com.houqin.entity.natural.NaturalType;
import com.houqin.entity.natural.NaturalVO;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 天燃气
 *
 * @author ccl
 * @create 2017-05-18-17:22
 */
@Controller
@RequestMapping("/admin/houqin")
public class NaturalController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NaturalController.class);
    /**
     * 一年12个月
     */
    public static final int MONTH_NUM = 12;
    //添加天燃气
    private static final String createNatural = "/natural/add-natural";
    //列表
    private static final String naturalList = "/natural/natural_list";
    //更新天燃气
    private static final String toUpdateNatural = "/natural/update_natural";
    //天然气统计
    private static final String statisticNatural = "/natural/statistic_natural";
    //用气量统计
    private static final String statisticPurpose = "/natural/statistic_purpose_natural";

    @Autowired
    private NaturalBiz naturalBiz;

    @Autowired
    private NaturalTypeBiz naturalTypeBiz;

    @Autowired
    private NaturalDao naturalDao;

    @Autowired
    private BaseHessianBiz baseHessianBiz;

    @InitBinder({"natural"})
    public void initNatural(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
        binder.setFieldDefaultPrefix("natural.");
    }


    /**
     * @Description:查询天燃气列表
     * @author: ccl
     * @Param: [request, pagination, natural]
     * @Return: java.lang.String
     * @Date: 2017-05-18
     */
    @RequestMapping("/queryAllNatural")
    public String queryAllNatural(HttpServletRequest request,
                                  @ModelAttribute("pagination") Pagination pagination,
                                  @ModelAttribute("natural") Natural natural,
                                  @RequestParam(required = false) String previousTime,
                                  @RequestParam(required = false) String afterTime) {
        try {
            String whereSql = GenerateSqlUtil.getSql(natural);
            if (StringUtils.isNotEmpty(previousTime)) {
                whereSql += " and createTime >= '" + previousTime + "'";
            }
            if (StringUtils.isNotEmpty(afterTime)) {
                whereSql += " and createTime <= '" + afterTime + "'";
            }
            whereSql += " order by id desc";
            pagination.setPageSize(10);
            pagination.setRequest(request);
            List<NaturalVO> naturalList = naturalBiz.getOilDtoList(pagination, whereSql);
            request.setAttribute("naturalList", naturalList);

            //查询用户角色
            Long userId = SysUserUtils.getLoginSysUserId(request);

            String roleIds = baseHessianBiz.queryUserRolesByUserId(userId);
            if (!StringUtils.isEmpty(roleIds)) {
                if (roleIds.indexOf("31") != -1) {
                    request.setAttribute("flag", "true");
                }
            }
            request.setAttribute("roleIds", roleIds);
            request.setAttribute("previousTime", previousTime);
            request.setAttribute("afterTime", afterTime);

            List<NaturalType> naturalTypeList = naturalTypeBiz.find(null, "1=1");
            request.setAttribute("naturalTypeList", naturalTypeList);

        } catch (Exception e) {
            logger.error("NaturalController--queryAllNatural", e);
            return this.setErrorPath(request, e);
        }
        return naturalList;
    }


    /**
     * @Description:添加天燃气
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2017-05-18
     */

    @RequestMapping("/toAddNatural")
    public String toAddNatural(HttpServletRequest request) {
        try {
            List<NaturalType> naturalTypeList = naturalTypeBiz.find(null, "1=1");
            request.setAttribute("naturalTypeList", naturalTypeList);
        } catch (Exception e) {
            logger.error("NaturalController--toAddNatural", e);
            return this.setErrorPath(request, e);
        }
        return createNatural;
    }

    /**
     * @Description:添加保存
     * @author: ccl
     * @Param: [request, natural]
     * @Return: java.util.Map<java.lang.String ,   java.lang.Object>
     * @Date: 2017-05-18
     */
    @RequestMapping("/addSaveNatural")
    @ResponseBody
    public Map<String, Object> addSaveNatural(HttpServletRequest request, @ModelAttribute("natural") Natural natural) {
        Map<String, Object> resultMap;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            natural.setUserId(userId);
            naturalBiz.save(natural);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("NaturalController--addSaveNatural", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:去修改
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2017-05-18
     */
    @RequestMapping("/toUpdateNatural")
    public String toUpdateNatural(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            List<NaturalType> naturalTypeList = naturalTypeBiz.find(null, "1=1");
            request.setAttribute("naturalTypeList", naturalTypeList);

            Natural natural = naturalBiz.findById(id);
            request.setAttribute("natural", natural);
        } catch (Exception e) {
            logger.error("NaturalController--toUpdateNatural", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateNatural;
    }


    /**
     * @Description:修改天燃气
     * @author: ccl
     * @Param: [request, natural]
     * @Return: java.util.Map<java.lang.String               ,               java.lang.Object>
     * @Date: 2017-05-18
     */
    @RequestMapping("/updateNatural")
    @ResponseBody
    public Map<String, Object> updateNatural(HttpServletRequest request, @ModelAttribute("natural") Natural natural) {
        Map<String, Object> resultMap = null;
        try {
            naturalBiz.update(natural);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("NaturalController--updateNatural", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除天燃气
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String               ,               java.lang.Object>
     * @Date: 2017-05-18
     */
    @RequestMapping("/deleteNatural")
    @ResponseBody
    public Map<String, Object> deleteNatural(HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            String id = request.getParameter("id");
            naturalBiz.deleteById(Long.parseLong(id));
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("NaturalController--deleteNatural", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * 确认用量
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/confirmNatural")
    @ResponseBody
    public Map<String, Object> confirmOil(HttpServletRequest request,
                                          @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {

            Natural natural = naturalBiz.findById(id);
            natural.setAffirm(1L);
            naturalBiz.update(natural);
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
    @RequestMapping("/statisticNatural")
    public String statisticNatural(HttpServletRequest request) {
        try {
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
            // 查询一年中每个月的总数
            List<NaturalStatistic> currentStatisticList = naturalDao.queryStatisticByYearAndMonth(" year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + Integer.parseInt(years));

            List<NaturalStatistic> lastStatisticList = naturalDao.queryStatisticByYearAndMonth(" year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + lastyear);

            List<NaturalStatistic> beforeStatisticList = naturalDao.queryStatisticByYearAndMonth("year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + beforeyear);


            // 1-12月
            List<NaturalStatistic> result = new ArrayList();
            List<NaturalStatistic> result1 = new ArrayList(12);
            List<NaturalStatistic> result2 = new ArrayList(12);
            List<NaturalStatistic> result3 = new ArrayList(12);
            String[] strings = months.split(",");
            for (String s : strings) {
                NaturalStatistic n = new NaturalStatistic();
                n.setYear(Integer.parseInt(years));
                n.setMonth(Integer.valueOf(s.replace("月", "").replace("'", "")));
                List<NaturalStatistic> naturalStatistics = naturalDao.queryStatisticByYearAndMonth(" year(str_to_date(na.monthTime, '%Y-%m-%d'))=" + years + " and month(str_to_date(na.monthTime, '%Y-%m-%d'))=" + n.getMonth());
                if (ObjectUtils.isNotNull(naturalStatistics)) {
                    n.setAmount(naturalStatistics.get(0).getAmount());
                }
                result.add(n);
            }
            if (result != null && result.size() > 0) {
                String data = "";
                for (NaturalStatistic n : result) {
                    data += n.getAmount() + ",";
                }
                request.setAttribute("data", data);
            }


            //////////////////今年的数据
            for (int i = 0; i < MONTH_NUM; i++) {
                NaturalStatistic n = new NaturalStatistic();
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
                for (NaturalStatistic n : result1) {
                    currentData += n.getAmount() + ",";
                }
                request.setAttribute("currentData", currentData);
            }

            /////////////////////////////////////////去年的数据
            for (int i = 0; i < 12; i++) {
                NaturalStatistic n = new NaturalStatistic();
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
                for (NaturalStatistic n : result2) {
                    lastData += n.getAmount() + ",";
                }
                request.setAttribute("lastData", lastData);
            }

            //////////////////////////////////////////前年的数据
            for (int i = 0; i < 12; i++) {
                NaturalStatistic n = new NaturalStatistic();
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
                for (NaturalStatistic n : result3) {
                    beforeData += n.getAmount() + ",";
                }
                request.setAttribute("beforeData", beforeData);
            }

            request.setAttribute("naturalStatisticList", result);

            Integer sum = naturalDao.queryCountByYear(whereSql);
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
        return statisticNatural;
    }


    /**
     * 导出用气使用量
     *
     * @param request
     * @param response
     */
    @RequestMapping("/exportNaturalExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String whereSql = "1=1";
            String describe = "";
            String months = "";
            String dir = request.getSession().getServletContext().getRealPath("/FileList");
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
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
            String expName = year + "年用气使用统计_" + describe;
            // 表头信息
            String[] headName = {"月份", "使用量"};
            String head = "中共贵阳市委党校后勤管理处" + describe + "用气统计表";
            List<NaturalStatistic> naturalStatisticList = naturalDao.queryStatisticByYearAndMonth(whereSql);
            List<NaturalStatistic> result = new ArrayList<>();
            if (ObjectUtils.isNotNull(naturalStatisticList)) {
                String[] strings = months.split(",");
                for (String s : strings) {
                    NaturalStatistic n = new NaturalStatistic();
                    n.setYear(year);
                    n.setMonth(Integer.valueOf(s.replace("月", "").replace("'", "")));
                    List<NaturalStatistic> naturalStatistics = naturalDao.queryStatisticByYearAndMonth(" year(na.createTime)=" + years + " and month(na.createTime)=" + n.getMonth());
                    if (ObjectUtils.isNotNull(naturalStatistics)) {
                        n.setAmount(naturalStatistics.get(0).getAmount());
                    }
                    result.add(n);
                }
            } else {
                for (int i = 0; i < MONTH_NUM; i++) {
                    NaturalStatistic n = new NaturalStatistic();
                    n.setYear(year);
                    n.setMonth(i + 1);
                    n.setAmount(0);
                    result.add(n);
                }

                naturalStatisticList.forEach(
                        n -> result.set(n.getMonth() - 1, n)
                );
            }
            List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
            List<List<String>> list = naturalStatisticsJoint(result);

            File file = FileExportImportUtil.createHeadExcel(headName, list, expName, dir, head);
            srcfile.add(file);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> naturalStatisticsJoint(List<NaturalStatistic> naturalStatistics) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        for (int i = 0; i < naturalStatistics.size(); i++) {
            List<String> small = new ArrayList<String>();
            NaturalStatistic naturalStatistic = naturalStatistics.get(i);
            small.add(String.valueOf(naturalStatistic.getMonth()));
            small.add(String.valueOf(naturalStatistic.getAmount()));
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
    @RequestMapping("/statisticByNaturalPurpose")
    public String statisticByNaturalPurpose(HttpServletRequest request) {
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
            Integer count = naturalDao.queryCountByYear(whereSql);
            request.setAttribute("count", count);
            List<NaturalStatistic> naturalStatisticList = naturalDao.queryNaturalPurposeByYear(whereSql);
            request.setAttribute("naturalStatisticList", naturalStatisticList);

            //柱状图统计信息
            if (ObjectUtils.isNotNull(naturalStatisticList)) {
                String number = "";
                String name = "";
                for (NaturalStatistic w : naturalStatisticList) {
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


        } catch (Exception e) {
            e.printStackTrace();
        }
        return statisticPurpose;
    }


    @RequestMapping("/prevNat")
    @ResponseBody
    public Map<String, Object> prevNatService(String dates, Long typeId) {
        Map<String, Object> objMap;
        try {
            String prevMonth = DateUtils.getPrevMonth(dates);
            List<Natural> naturals = naturalBiz.find(null,
                    "type =" + typeId + " and monthTime = '" + prevMonth + "'");

            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(naturals)) {
                Double read = naturals.get(0).getCurRead();
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
     * 批量导入
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 17:47
     * @return:
     */
    @RequestMapping("/import/toNaturalImport")
    public String toNaturalImport() {
        return "/natural/natural-import";
    }

    /**
     * 导入用气量
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 17:47
     * @return:
     */
    @RequestMapping("/import/naturalImport")
    public String naturalImport(HttpServletRequest request, @RequestParam("myFile") MultipartFile myfile) {
        try {
            naturalBiz.naturalImport(myfile, request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("NaturalController.naturalImport", e);
        }
        return "redirect:/admin/houqin/queryAllNatural.json";
    }
}
