package com.keyanzizheng.controller.statistics;

import com.a_268.base.controller.BaseController;
import com.a_268.base.util.DateUtils;
import com.keyanzizheng.biz.award.AwardBiz;
import com.keyanzizheng.biz.category.CategoryBiz;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.common.StudentHessianService;
import com.keyanzizheng.constant.ResultTypeConstants;
import com.keyanzizheng.entity.category.Category;
import com.keyanzizheng.entity.statictics.StaticticVO;
import com.keyanzizheng.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.keyanzizheng.constant.ResultFormConstants.*;

/**
 * 科研统计
 * <p>包含获奖统计
 * 成果类型统计
 * 以及成果各个种类的细分类别<p>
 *
 *     更改JournalNature值时 参考/admin/ky/listCategory.json里的二级分类ID
 *
 * @author YaoZhen
 * @date 12-18, 09:27, 2017.
 */
@Controller
@RequestMapping("/admin/ky")
public class ScientificResearchStatisticsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ScientificResearchStatisticsController.class);

    @Autowired private AwardBiz awardBiz;
    @Autowired private ResultBiz resultBiz;
    @Autowired private StudentHessianService studentHessianService;
    @Autowired private CategoryBiz categoryBiz;

    /**
     * 统计科研各个成果的数量
     *
     * @param startTime 按时间段查询开始时间之后
     * @param endTime   按时间段查询开始时间之前
     * @return 饼状图和常规柱状图
     */
    @RequestMapping("scientificAchievements")
    public ModelAndView scientificAchievements(
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("statistic/result_achievements");
        try {
            String whereTime = timeSql(startTime, endTime, mv);
            // 统计论文数量
            int lunwen = resultBiz.count(whereTime + " and resultType = 1 and resultForm = 1 and ifFile = 0 AND STATUS != 2");
            // 统计著作数量
            int zhuzuo = resultBiz.count(whereTime + " and resultType = 1 and resultForm = 2 and ifFile = 0 AND STATUS != 2");
            // 统计课题数量
            int keti = resultBiz.count(whereTime + " and resultType = 1 and resultForm = 3 and ifFile = 0 and STATUS != 2 and passStatus = 8");
            // 统计内刊数量
            int neikan = resultBiz.count(whereTime + " and resultType = 1 and resultForm = 4 and ifFile = 0 AND STATUS != 2");
            // 统计调研报告数量
            int baogao = studentHessianService.countResearchReportByKy(whereTime, ResultTypeConstants.KE_YAN);
            // 统计其他成果数量
            int qita = resultBiz.count(whereTime + " and resultType = 1 and resultForm = 6 and ifFile = 0 AND STATUS != 2");

            mv.addObject("lunwen", lunwen);
            mv.addObject("zhuzuo", zhuzuo);
            mv.addObject("keti", keti);
            mv.addObject("neikan", neikan);
            mv.addObject("baogao", baogao);
            mv.addObject("qita", qita);
        } catch (Exception e) {
            logger.error("ResultStatisticsController.scientificAchievements", e);
        }
        return mv;
    }


    /**
     * 论文类别统计
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 饼状图和柱状图
     */
    @RequestMapping("lunwenDetailsStatistics")
    public ModelAndView detailsStatistics(@RequestParam(value = "startTime", required = false) String startTime,
                                          @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("statistic/result_statistics_lunwen");
        try {
            String whereTime = timeSql(startTime, endTime, mv);
            String condition = " and resultType = 1 and resultForm = 1 and ifFile = 0 AND STATUS != 2";

            List<StaticticVO> serList = getStaVOS(1, whereTime, condition);

            isNoData(serList, mv);

            mv.addObject("series", serList);
        } catch (ParseException e) {
            logger.error("ScientificResearchStatisticsController.detailsStatistics", e);
        }
        return mv;
    }

    // 统计出来的成果总和 是否为0
    private void isNoData(List<StaticticVO> serList, ModelAndView mv) {
        boolean flag = serList.stream()
                .map(StaticticVO::getData).reduce(Integer::sum)
                .orElse(0) == 0;
        if (flag) {
            mv.addObject("nodata", 1);
        }
    }

    // 查询各个类别的数量
    private List<StaticticVO> getStaVOS(int formId, String whereTime, String condition) {
        List<Category> categoryList = categoryBiz.findChildByParentId(formId);
        return categoryList.stream().map(c -> new StaticticVO(
                c.getName(),
                resultBiz.count(
                        whereTime + condition +
                        " and journalNature = " +
                        c.getId()),
                c.getId())).collect(Collectors.toList());
    }

    /**
     * 著作类别统计
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 饼状图和柱状图
     */
    @RequestMapping("zhuzuoDetailsStatistics")
    public ModelAndView zhuzuoDetailsStatistics(@RequestParam(value = "startTime", required = false) String startTime,
                                                @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("statistic/result_statistics_zhuzuo");
        try {
            String whereTime = timeSql(startTime, endTime, mv);
            String condition = " and resultType = 1 and resultForm = 2 and ifFile = 0 and status != 2";

            List<StaticticVO> serList = getStaVOS(2, whereTime, condition);
            isNoData(serList, mv);

            mv.addObject("series", serList);
        } catch (ParseException e) {
            logger.error("ScientificResearchStatisticsController.detailsStatistics", e);
        }
        return mv;
    }

    /**
     * 课题类别统计
     * 课题没有二级分类
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 饼状图和柱状图
     */
    @RequestMapping("ketiDetailsStatistics")
    public ModelAndView ketiDetailsStatistics(@RequestParam(value = "startTime", required = false) String startTime,
                                              @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("statistic/result_statistics_keti");
        try {
            String whereTime = timeSql(startTime, endTime, mv);
            String condition = " and resultType = 1 and resultForm = 3 and passStatus = 8 AND STATUS != 2";
            // 国家级
            int hexin = resultBiz.count(whereTime + condition + " and level = 1");
            // 省部级
            int neikan = resultBiz.count(whereTime + condition + " and level = 2");
            // 地市级
            int yiban = resultBiz.count(whereTime + condition + " and level = 3");
            // 校级
            int xiao = resultBiz.count(whereTime + condition + " and level = 4");

            if (hexin + neikan + yiban + xiao == 0) {
                mv.addObject("nodata", 1);
            }

            List<StaticticVO> series = new ArrayList<>();
            series.add(new StaticticVO("国家级", hexin));
            series.add(new StaticticVO("省部级", neikan));
            series.add(new StaticticVO("地市级", yiban));
            series.add(new StaticticVO("校级", xiao));

            mv.addObject("series", series);
        } catch (ParseException e) {
            logger.error("ScientificResearchStatisticsController.detailsStatistics", e);
        }
        return mv;
    }

    /**
     * 内刊类别统计
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 饼状图和柱状图
     */
    @RequestMapping("neikanDetailsStatistics")
    public ModelAndView neikanDetailsStatistics(
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("statistic/result_statistics_neikan");
        try {
            String whereTime = timeSql(startTime, endTime, mv);
            String condition = " and resultType = 1 and resultForm = 4 and ifFile = 0 and status != 2";

            List<StaticticVO> serList = getStaVOS(4, whereTime, condition);
            isNoData(serList, mv);

            mv.addObject("series", serList);
        } catch (ParseException e) {
            logger.error("ScientificResearchStatisticsController.detailsStatistics", e);
        }
        return mv;
    }


    /**
     * 其他类别统计
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 饼状图和柱状图
     */
    @Deprecated
    @RequestMapping("qitaDetailsStatistics")
    public ModelAndView qitaDetailsStatistics(
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("statistic/result_statistics_qita");
        try {
            String whereTime = timeSql(startTime, endTime, mv);
            String condition = " and resultType = 1 and resultForm = 6 and ifFile = 0 and status != 2";

            List<StaticticVO> serList = getStaVOS(6, whereTime, condition);
            isNoData(serList, mv);

            // 国家级
            int hexin = resultBiz.count(whereTime + condition + " and level = 1");
            // 省部级
            int neikan = resultBiz.count(whereTime + condition + " and level = 2");
            // 市级
            int yiban = resultBiz.count(whereTime + condition + " and level = 3");
            // 校级
            int baozhi = resultBiz.count(whereTime + condition + " and level = 4");

            if (hexin + neikan + yiban + baozhi == 0) {
                mv.addObject("nodata", 1);
            }

            List<StaticticVO> series = new ArrayList<>();
            series.add(new StaticticVO("国家级", hexin));
            series.add(new StaticticVO("省部级", neikan));
            series.add(new StaticticVO("市级", yiban));
            series.add(new StaticticVO("校级", baozhi));

            mv.addObject("series", series);
        } catch (ParseException e) {
            logger.error("ScientificResearchStatisticsController.detailsStatistics", e);
        }
        return mv;
    }

    /**
     * 统计调研报告数量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 列表
     */
    @RequestMapping("statisticalResearchReport")
    public ModelAndView statisticalResearchReport(
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("statistic/result_statistics_report");
        try {
            String whereTime = timeSql(startTime, endTime, mv);
            int teacherList = studentHessianService.queryForTeacherKyReportListByTime(whereTime);
            int studentList = studentHessianService.queryForStudentKyReportListByTime(whereTime);
            List<StaticticVO> series = new ArrayList<>();
            series.add(new StaticticVO("教职工调研报告数量", teacherList));
            series.add(new StaticticVO("学员调研报告数量", studentList));
            if (teacherList + studentList == 0) {
                mv.addObject("nodata", 1);
            }
            mv.addObject("series", series);
        } catch (Exception e) {
            logger.error("ScientificResearchStatisticsController.statisticalResearchReport", e);
        }

        return mv;

    }

    /**
     * 科研 统计获奖各个成果的数量
     *
     * @param startTime 按时间段查询开始时间之后
     * @param endTime   按时间段查询开始时间之前
     * @return 获奖按照课题和论文进行统计（包含打印和时间段查询）
     */
    @RequestMapping("awardStatistics")
    public ModelAndView awardStatistics(@RequestParam(value = "startTime", required = false) String startTime,
                                        @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("statistic/result_awardStatistics");

        try {
            String whereTime = timeSql(startTime, endTime, mv);
            int lunwen = awardBiz.count(whereTime + " and resultForm = 1");
            int zhuzuo = awardBiz.count(whereTime + " and resultForm = 2");
            int keti = awardBiz.count(whereTime + " and resultForm = 3");
            int neikan = awardBiz.count(whereTime + " and resultForm = 4");
            int diaoyan = awardBiz.count(whereTime + " and resultForm = 5");
            int qita = awardBiz.count(whereTime + " and resultForm = 6");

            List<StaticticVO> series = new ArrayList<>();
            series.add(new StaticticVO("论文", lunwen, 1L));
            series.add(new StaticticVO("著作", zhuzuo,2L));
            series.add(new StaticticVO("课题", keti,3L));
            series.add(new StaticticVO("内刊", neikan, 4L));
            series.add(new StaticticVO("调研报告", diaoyan, 5L));
            series.add(new StaticticVO("其他", qita, 6L));


            if (lunwen + zhuzuo + keti + neikan + diaoyan + qita == 0) {
                mv.addObject("nodata", 1);
            }

            mv.addObject("series", series);
        } catch (ParseException e) {
            logger.error("ResultStatisticsController.awardStatistics", e);
        }
        return mv;
    }

    /**
     * 统计各个获奖种类详情
     *
     * @param resultForm 获奖形式: 论文 -- 其他
     * @return 论文 -- 其他下的分类数量 : 国家级 市级 校级
     */
    @RequestMapping("detailAwardStatistics")
    public ModelAndView detailAwardStatistics(@RequestParam("resultForm") Integer resultForm,
                                              @RequestParam(value = "startTime", required = false) String startTime,
                                              @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("statistic/result_detail_awardStatistics");
        try {
            String whereTime = timeSql(startTime, endTime, mv);

            int national = awardBiz.count(whereTime + " and resultForm = " + resultForm + " and awardSituation = 1");
            int provincial = awardBiz.count(whereTime + " and resultForm = " + resultForm + " and awardSituation = 2");
            int cityLevel = awardBiz.count(whereTime + " and  resultForm = " + resultForm + " and awardSituation = 3");
            int otherLevel = awardBiz.count(whereTime + " and  resultForm = " + resultForm + " and awardSituation = 4");

            List<StaticticVO> series = new ArrayList<>();

            if (resultForm == QUESTION) {
                series.add(new StaticticVO("党校系统级", national, 1L));
                series.add(new StaticticVO("哲学社会成果奖", provincial, 2L));
                series.add(new StaticticVO("其他", cityLevel,3L));
            } else {
                series.add(new StaticticVO("国家级", national,1L));
                series.add(new StaticticVO("省部级", provincial,2L));
                series.add(new StaticticVO("地市级", cityLevel,3L));
                series.add(new StaticticVO("其他", otherLevel,4L));
            }

            if (national + provincial + cityLevel + otherLevel == 0) {
                mv.addObject("nodata", 1);
            }

            switch (resultForm) {
                case PAPER:
                    mv.addObject("title", "论文");
                    break;
                case BOOK:
                    mv.addObject("title", "著作");
                    break;
                case QUESTION:
                    mv.addObject("title", "课题");
                    break;
                case INTERNAL_PUBLICATION:
                    mv.addObject("title", "内刊");
                    break;
                case RESEARCH:
                    mv.addObject("title", "调研报告");
                    break;
                case OTHER:
                    mv.addObject("title", "其他类型");
                    break;
                default:
            }

            mv.addObject("series", series);
            mv.addObject("resultForm", resultForm);
        } catch (ParseException e) {
            logger.error("ScientificResearchStatisticsController.detailAwardStatistics", e);
        }

        return mv;
    }



    /**
     * 拼接时间日期
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回值末尾无空格
     */
    public String timeSql(String startTime, String endTime, ModelAndView mv) throws ParseException {
        StringBuilder whereTime = new StringBuilder("1=1");

        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtil.isNotBlank(startTime)) {
            whereTime.append(" and createTime >= '")
                    .append(DateUtils.format(smf.parse(startTime), "yyyy-MM-dd HH:mm:ss"))
                    .append("'");
            mv.addObject("startTime", startTime);
        }
        if (StringUtil.isNotBlank(endTime)) {
            whereTime.append(" and createTime <= '")
                    .append(DateUtils.format(smf.parse(endTime), "yyyy-MM-dd HH:mm:ss"))
                    .append("'");
            mv.addObject("endTime", endTime);
        }
        return whereTime.toString();
    }
}
