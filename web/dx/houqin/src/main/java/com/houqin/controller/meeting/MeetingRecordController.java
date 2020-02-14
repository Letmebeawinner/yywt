package com.houqin.controller.meeting;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.common.JiaoWuHessianService;
import com.houqin.biz.meeting.MeetingBiz;
import com.houqin.biz.meeting.MeetingRecordBiz;
import com.houqin.dao.meeting.MeetingRecordDao;
import com.houqin.entity.meeting.Meeting;
import com.houqin.entity.meeting.MeetingRecord;
import com.houqin.entity.meeting.MeetingRecordDto;
import com.houqin.entity.sysuser.SysUser;
import com.houqin.utils.GenerateSqlUtil;
import com.houqin.utils.StringUtils;
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
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/houqin")
public class MeetingRecordController extends BaseController {


    private static Logger logger = LoggerFactory.getLogger(MeetingRecordController.class);

    @Autowired
    private MeetingBiz meetingBiz;
    @Autowired
    private MeetingRecordBiz meetingRecordBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;
    @Autowired
    private MeetingRecordDao meetingRecordDao;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"meetingRecord"})
    public void initMeeting(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("meetingRecord.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 查询会常使用记录
     *
     * @return
     */
    @RequestMapping("/queryMeetingRecord")
    public ModelAndView queryMeetingRecord(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,
                                           @RequestParam("meetingId") Long id, @ModelAttribute("meetingRecord") MeetingRecord meetingRecord) {
        ModelAndView mv = new ModelAndView("/meeting/meeting_record_list");
        try {
            String whereSql = GenerateSqlUtil.getSql(meetingRecord);
            whereSql += " and meetingId=" + id + " order by id desc";
            List<MeetingRecord> meetingRecordList = meetingRecordBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(meetingRecordList)) {
                for (MeetingRecord m : meetingRecordList) {
                    SysUser sysUser = baseHessianBiz.getSysUserById(m.getUserId());
                    m.setUserName(sysUser.getUserName());
                }
            }
            mv.addObject("meetingRecordList", meetingRecordList);
            Meeting meeting = meetingBiz.findById(id);
            mv.addObject("meeting", meeting);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;

    }

    /**
     * 查询会常使用记录
     *
     * @return
     */
    @RequestMapping("/ajax/queryMeetingRecord")
    @ResponseBody
    public Map<String, Object> queryMeetingRecordByMeetingId(@RequestParam("meetingId") Long meetingId) {
        Map<String, Object> json = null;
        String sql = "meetingId = " + meetingId;
        try {
            List<MeetingRecord> meetingRecords = meetingRecordBiz.find(null, sql);
            List<MeetingRecordDto> meetingRecordDtoLists = meetingRecordBiz.convertMeetingToDto(meetingRecords);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, meetingRecordDtoLists);
        } catch (Exception e) {
            logger.error("MeetingRecordController.queryAllMeetingRecord", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;

    }

    /**
     * 会场申请记录表
     *
     * @return
     */
    @RequestMapping("/meetingApplyRecordList")
    public String meetingApplyRecordList(HttpServletRequest request, @RequestParam("meetingId") String meetingId) {
        Meeting meeting = meetingBiz.findById(Long.parseLong(meetingId));
        request.setAttribute("meetingId", meetingId);
        request.setAttribute("meeting", meeting);
        return "/meeting/meeting_address_apply";
    }

    /**
     * to会场申请
     *
     * @return
     */
    @RequestMapping("/to/meetingApply")
    public String toApplyMeeting(HttpServletRequest request) {
        try {
            meetingRecordBiz.changeMeetingStatusWhenChose();
            // 班级类型列表
            Map<String, Object> classTypeMap = jiaoWuHessianService.listClassType();
            request.setAttribute("classTypeMap", classTypeMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/meeting/meeting_apply";
    }

    /**
     * 申请会议
     *
     * @param request
     * @param meetingRecord
     * @return
     */
    @RequestMapping("/applyMeeting")
    @ResponseBody
    public Map<String, Object> applyMeeting(HttpServletRequest request,
                                            @ModelAttribute("meetingRecord") MeetingRecord meetingRecord) {
        Map<String, Object> json = null;
        try {
            //获取用户id
            Long userId = SysUserUtils.getLoginSysUserId(request);
            meetingRecord.setUserId(userId);
            Long meetingId = meetingRecord.getMeetingId();
            if (ObjectUtils.isNotNull(meetingRecord.getClassesId())) {
                Map<String, String> classes = jiaoWuHessianService.queryClassNameById(meetingRecord.getClassesId());
                meetingRecord.setClassesName(classes.get("name").toString());
            }
            meetingRecord.setStatus(0);
            int flag = meetingRecordBiz.addMeetingApply(meetingRecord);
            // 需改会场为使用中
            Meeting meetingBizById = meetingBiz.findById(meetingId);
            meetingBizById.setStatus(2);
            meetingBiz.update(meetingBizById);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, flag);
        } catch (Exception e) {
            logger.error("MeetingRecordController.applyMeeting", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 去修改
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateApply")
    public ModelAndView toUpdateApply(HttpServletRequest request, @RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("/meeting/meeting_apply_update");
        try {
            MeetingRecord meetingRecord = meetingRecordBiz.findById(id);
            Meeting meeting = meetingBiz.findById(meetingRecord.getMeetingId());
            mv.addObject("meetingRecord", meetingRecord);
            mv.addObject("meeting", meeting);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 去修改会议记录的时间
     *
     * @param request
     * @return
     */
    @RequestMapping("/ajax/toUpdateApply")
    public String toUpdateMeetingRecordApply(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            MeetingRecord meetingRecord = meetingRecordBiz.findById(id);
            Meeting meeting = meetingBiz.findById(meetingRecord.getMeetingId());
            request.setAttribute("meetingRecord", meetingRecord);
            request.setAttribute("meeting", meeting);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setErrorPath(request, e);
        }
        return "/meeting/meeting_record_update";
    }

    /**
     * 修改
     *
     * @param request
     * @param meetingRecord
     * @return
     */
    @RequestMapping("/updateApplyMeeting")
    @ResponseBody
    public Map<String, Object> updateApplyMeeting(HttpServletRequest request, @ModelAttribute("meetingRecord") MeetingRecord meetingRecord) {
        Map<String, Object> json = null;
        try {
            int flag = meetingRecordBiz.updateMeetRecord(meetingRecord);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, flag);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 选择会场
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectMeeting")
    public ModelAndView selectMeeting(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/meeting/select_meeting_list");
        try {
            List<Meeting> meetingList = meetingBiz.find(null, " 1=1 and status !=1");
            for (Meeting meeting : meetingList) {
                Long meetingId = meeting.getId();
                List<MeetingRecord> meetingRecords = meetingRecordBiz.find(null, " meetingId=" + meetingId + " and status =0");
                if (meetingRecords.size() > 0) {
                    for (MeetingRecord meetingRecord : meetingRecords) {
                        Date turnTime = meetingRecord.getTurnTime();
                        Date nowDate = new Date();
                        int compareTo = nowDate.compareTo(turnTime);
                        if (compareTo > 0) {
                            MeetingRecord meetingRecordBizById = meetingRecordBiz.findById(meetingRecord.getId());
                            meetingRecordBizById.setStatus(1);
                            meetingRecordBiz.updateMeetRecord(meetingRecordBizById);
                            Meeting meetingBizById = meetingBiz.findById(meetingId);
                            meetingBizById.setStatus(0);
                            meetingBiz.update(meetingBizById);
                        }
                    }
                }
            }
            mv.addObject("meetingList", meetingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * 查询会场名称
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/searchMeeting")
    @ResponseBody
    public Map<String, Object> searchProperty(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            Meeting meeting = meetingBiz.findById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, meeting);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping("/to/meetingRecord/statistics")
    public String toStatisticMeetingRecord(HttpServletRequest request, @RequestParam(value = "year", required = false) String year) {
        SimpleDateFormat smf = new SimpleDateFormat("yyyy");
        Date nowDate = new Date();
        String nowYear = smf.format(nowDate);
        try {
            if (StringUtils.isBlank(year)) {
                year = nowYear;
            }
            List<Map<String, Object>> meetingRecords = meetingRecordDao.getMeetingRecordByYear(year);
            List<Map<String, Object>> meetingRecordsGroupByClass = meetingRecordDao.getMeetingRecordGroupByClassesId(year);
            List<Map<String, Object>> meetingByClassMaps = null;
            if (meetingRecordsGroupByClass != null && meetingRecordsGroupByClass.size() > 0) {
                meetingByClassMaps = meetingRecordsGroupByClass.stream()
                        .map(map -> mapToTreeMap(map)
                        )
                        .collect(Collectors.toList());
            }
            Map<String, Object> treeMeetingRecords = new TreeMap<>((s1, s2) -> Long.parseLong(s1) > Long.parseLong(s2) ? 1 : 0);

            String pictureData = "";
            for (int i = 1; i <= 12; i++) {
                String key = "" + i;
                if (meetingRecords != null && meetingRecords.size() > 0) {
                    if (meetingRecords.get(0) != null) {
                        treeMeetingRecords.put(key, meetingRecords.get(0).get(key).toString());
                        pictureData += meetingRecords.get(0).get(key).toString() + ",";
                    }

                }
            }
            request.setAttribute("data", treeMeetingRecords);
            request.setAttribute("pictureData", pictureData);
            request.setAttribute("queryYear", year);
            request.setAttribute("meetingRecordsGroupByClass", meetingByClassMaps);

        } catch (Exception e) {
            logger.error("StockController.toStatisticStock", e);
            return this.setErrorPath(request, e);
        }

        return "/meeting/statistics_meetingRecord";
    }

    /**
     * 会场在对应年月的使用情况，以物品进行分组
     *
     * @param request
     * @param year
     * @param month
     * @return
     */
    @RequestMapping("/to/meetingRecord/statistics/detail")
    public String toMeetingRecordDetail(HttpServletRequest request,
                                        @RequestParam("year") String year,
                                        @RequestParam("month") String month) {
        try {
            List<Map<String, String>> recordsDetail = meetingRecordDao.getMeetingRecordDetailByYearAndMonth(year, month);
            request.setAttribute("recordsDetail", recordsDetail);
            request.setAttribute("year", year);
            request.setAttribute("month", month);
        } catch (Exception e) {
            logger.error("MeetingRecordController.toMeetingRecordDetail", e);
            return this.setErrorPath(request, e);
        }

        return "/meeting/statistics_meetingRecord_list";
    }

    private Map<String, Object> mapToTreeMap(Map<String, Object> map) {
        Map<String, Object> treeMap = new TreeMap<>((s1, s2) -> Long.parseLong(s1) > Long.parseLong(s2) ? 1 : 0);
        if (map != null) {
            for (int i = 0; i <= 12; i++) {
                String key = i + "";
                treeMap.put(key, map.get(key));
            }
        }
        return treeMap;
    }

    /**
     * 取消使用
     *
     * @param request
     * @param id
     * @param meetingId
     * @return
     */
    @RequestMapping("/cancelMeetingUse")
    @ResponseBody
    public Map<String, Object> cancelMeetingUse(HttpServletRequest request, @RequestParam("id") Long id, @RequestParam("meetingId") Long meetingId) {
        Map<String, Object> json = null;
        try {
            MeetingRecord meetingRecordById = meetingRecordBiz.findById(id);
            meetingRecordById.setStatus(1);
            meetingRecordBiz.update(meetingRecordById);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, meetingRecordById);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

}
