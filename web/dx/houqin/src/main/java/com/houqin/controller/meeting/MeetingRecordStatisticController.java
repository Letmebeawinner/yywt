package com.houqin.controller.meeting;

import com.a_268.base.controller.BaseController;
import com.houqin.biz.meeting.MeetingRecordBiz;
import com.houqin.dao.meeting.MeetingDao;
import com.houqin.entity.electricity.EletricityStatistic;
import com.houqin.entity.meeting.MeetingRecord;
import com.houqin.entity.meeting.MeetingStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/admin/houqin")
public class MeetingRecordStatisticController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MeetingRecordStatisticController.class);

    @Autowired
    private MeetingDao meetingDao;

    /**
     * 统计会场使用记录(按照年份进行统计)
     */
    @RequestMapping("/statisticMeetRecord")
    public ModelAndView statisticMeetRecord(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/meeting/statistic_meeting_record_list");
        try {
            //查询会议使用的汇总数量
            String whereSql = "";


            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);    //获取年

            String years = request.getParameter("year");
            if (years == null || years == "") {
                years = String.valueOf(year);
            }
            whereSql = years;
            request.setAttribute("year", years);

            Integer recordNum = meetingDao.querySchoolMeetingRecordNum(whereSql);
            mv.addObject("recordNum", recordNum);
            // 查询一年中每个月的总数
            List<MeetingStatistic> meetingStatisticList = meetingDao.queryMeetingRecordByYear(whereSql);
            // 1-12月
            List<MeetingStatistic> result = new ArrayList(12);
            for (int i = 0; i < 12; i++) {
                MeetingStatistic n = new MeetingStatistic();
                n.setYear(year);
                n.setMonth(i + 1);
                result.add(n);
            }
            meetingStatisticList.forEach(n -> result.set(n.getMonth() - 1, n));
            mv.addObject("meetingStatisticList", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 查询详细统计
     *
     * @return
     */
    @RequestMapping("/queryMeetingRecordDetailStatistic")
    public ModelAndView queryMeetingRecordDetailStatistic(@RequestParam("month") String month,@RequestParam("year") String year) {
        ModelAndView mv = new ModelAndView("");
        try {
            MeetingRecord meetingRecord=meetingDao.queryMeetingRecordDetailStatistic(month,year);
            mv.addObject("meetingRecord",meetingRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


}
