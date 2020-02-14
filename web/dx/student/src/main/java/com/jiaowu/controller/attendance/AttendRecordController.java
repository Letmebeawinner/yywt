package com.jiaowu.controller.attendance;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.CommonBiz;
import com.jiaowu.biz.common.WorkSourceService;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.userWorkDayData.UserWorkDayDataBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.user.UserCondition;
import com.jiaowu.entity.userWorkDayData.UserWorkDayData;
import com.jiaowu.entity.workdaydata.WorkDayDataNode;
import com.jiaowu.entity.workdaydata.WorkDayDataVO;
import com.jiaowu.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 考勤记录
 *
 * @author YaoZhen
 * @date 04-08, 11:58, 2018.
 */
@Controller
@Slf4j
public class AttendRecordController extends BaseController {

    public static final String CARD_NO = "'A247BA39', 'A4C41746', '14274D7A', 'D5C23CA4'";

    @Autowired
    private UserBiz userBiz;
    @Autowired
    private WorkSourceService workSourceService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CommonBiz commonBiz;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private UserWorkDayDataBiz userWorkDayDataBiz;


    @InitBinder({"userCondition"})
    public void initUserCondition(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("userCondition.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 查询本班所有学员的考勤记录
     */
    @RequestMapping("/admin/jiaowu/attendRecord")
    public ModelAndView attendanceRecord(@ModelAttribute("pagination") Pagination pagination, @ModelAttribute("userCondition") UserCondition userCondition) {
        ModelAndView mv = new ModelAndView("/admin/attendrecord/list_attendance_record");
        try {
            /*
            当前登录的账号为班主任
            查询到班级
            查询到所有学生
             */
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            int userType = Integer.parseInt(userMap.get("userType"));
            if (userType != 2) {
                return new ModelAndView("redirect:" + BaseCommonConstants.basePath + "/admin/notAuthority.json");
            }
            long linkId = Long.parseLong(userMap.get("linkId"));
            List<Classes> classesList = classesBiz.find(null,
                    "(teacherId=" + linkId + " or deputyTeacherId="+ linkId +") ORDER BY id DESC");

            // 最新的班级
            Classes clz = classesList.get(0);
            // 班级开始时间
           /* Date startTime = clz.getStartTime();
            String strStart = DateUtils.format(startTime, DateUtils.PATTERN_YYYY_MM_DD);
            // 班级结束时间
            Date endTime = clz.getEndTime();
            String strEnd = DateUtils.format(endTime, DateUtils.PATTERN_YYYY_MM_DD);
            // 该班的所有学员
            List<User> userList = userBiz.find(null, "classId=" + clz.getId());

            /// 所有学员的考勤卡号
            String cardNOs = getStringCardNO(userList);

            if (StringUtils.isNotBlank(cardNOs)) {
                // 按照每个学员分组 查询. 把卡号传到一卡通模块, 接收返回值
                String rsJson = workSourceService.queryAllWordData(cardNOs, strStart, strEnd);
                List<WorkDayDataVO> dayDataVOS = gson.fromJson(rsJson,
                        new TypeToken<List<WorkDayDataVO>>(){}.getType());
                mv.addObject("dayDataVOS", dayDataVOS);
            }*/

            List<UserWorkDayData> userWorkDayDataList;
            StringBuilder whereSql = new StringBuilder(" userName is not null and userName!='' and type=1");
            whereSql.append(" and classId=").append(clz.getId());
            SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (!com.a_268.base.util.StringUtils.isTrimEmpty(userCondition.getUserName())) {
                whereSql.append(" and userName like '%").append(userCondition.getUserName()).append("%'");
            }
            if (ObjectUtils.isNotNull(userCondition.getStartTime())) {
                whereSql.append(" and  TO_DAYS(workDate) >= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getStartTime()).trim()).append("')");
            }
            if (ObjectUtils.isNotNull(userCondition.getEndTime())) {
                whereSql.append(" and  TO_DAYS(workDate) <= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getEndTime()).trim()).append("')");
            }
            whereSql.append(" order by TO_DAYS(workDate) desc");
            pagination.setRequest(request);
            userWorkDayDataList = userWorkDayDataBiz.find(pagination, whereSql.toString());
            userWorkDayDataList.forEach(e -> {
                User user = userBiz.findById(e.getUserId());
                Integer groupId = user == null?0:user.getGroupId()==null?0:user.getGroupId();
                switch (groupId) {
                    case 1:
                        e.setGroupName("第一组别");
                        break;
                    case 2:
                        e.setGroupName("第二组别");
                        break;
                    case 3:
                        e.setGroupName("第三组别");
                        break;
                    case 4:
                        e.setGroupName("第四组别");
                        break;
                }
            });
            mv.addObject("userWorkDayDataList", userWorkDayDataList);
            mv.addObject("pagination", pagination);
            mv.addObject("userCondition", userCondition);
            mv.addObject("classId", clz.getId());
        } catch (JsonSyntaxException e) {
            log.error("AttendRecordController.attendanceRecord", e);
        }
        return mv;
    }

    /**
     * 查询所有学员的考勤记录
     */
    @RequestMapping("/admin/jiaowu/allUserAttendRecord")
    public ModelAndView queryAllUserAttendanceRecord(@ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/admin/attendrecord/list_all_user_attendance_record");
        try {
            List<User> userList = userBiz.findAll();
            String cardNOs = getStringCardNO(userList);

            if (StringUtils.isNotBlank(cardNOs)) {
                // 按照每个学员分组 查询. 把卡号传到一卡通模块, 接收返回值
                pagination.setPageSize(10);
                Map<String, String> rsJson = workSourceService.queryAllWordDataByPage(pagination,
                        cardNOs, "2001-01-01", "2099-01-01");

                String rsListJson = rsJson.get("list");
                String rsListCount = rsJson.get("count");

                List<WorkDayDataVO> dayDataVOS = gson.fromJson(rsListJson, new TypeToken<List<WorkDayDataVO>>() {
                }.getType());

                mv.addObject("dayDataVOS", dayDataVOS);

                pagination.init(Integer.parseInt(rsListCount), 10, pagination.getCurrentPage());
                pagination.setRequest(request);
            }
        } catch (JsonSyntaxException e) {
            log.error("AttendRecordController.attendanceRecord", e);
        }
        return mv;
    }

    private String getStringCardNO(List<User> userList) {
        /// 所有学员的考勤卡号
        List<String> cardList = userList.stream()
                .map(User::getTimeCardNo).distinct()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());

        // 根据考勤卡号 查询所有的考勤记录
        return StringUtil.listToString(cardList);
    }


    /**
     * 解析子查询
     *
     * @param node 二级json数组
     * @return 打卡记录
     */
    @RequestMapping("/admin/jiaowu/viewRecord")
    public ModelAndView queryViewRecord(@RequestParam("node") String node, @RequestParam("name") String name) {
        ModelAndView mv = new ModelAndView("/admin/attendrecord/detail_attendance_record");
        try {
            List<WorkDayDataNode> nodes = gson.fromJson(node,
                    new TypeToken<List<WorkDayDataNode>>() {
                    }.getType());

            mv.addObject("nodes", nodes);
            mv.addObject("name", name);
        } catch (Exception e) {
            log.error("AttendRecordController.doViewRecord", e);
        }
        return mv;
    }

    /**
     * 查询学员的打卡记录
     */
    @RequestMapping("/admin/jiaowu/myAttendRecord")
    public ModelAndView queryMyAttendRecord() {
        ModelAndView mv = new ModelAndView("/admin/attendrecord/list_attendance_record");

        try {
            Long id = commonBiz.getCurrentUserId(request);
            User user = userBiz.findById(id);
            if (user == null) {
                return new ModelAndView("redirect:" + BaseCommonConstants.basePath + "/admin/notAuthority.json");
            }

            String cardNOs = user.getCardNo();
            getCardData(mv, cardNOs);

        } catch (Exception e) {
            log.error("AttendRecordController.queryMyAttendRecord", e);
        }

        return mv;
    }

    /**
     * 拼接<b>个人</b>的考勤卡号查询字符串
     *
     * @param mv      org.springframework.web.servlet.ModelAndView
     * @param cardNOs 考勤卡号
     */
    private void getCardData(ModelAndView mv, String cardNOs) {
        if (StringUtils.isNotBlank(cardNOs)) {
            cardNOs = "'" + cardNOs + "'";
            String rsJson = workSourceService.queryAllWordData(cardNOs, "2000-01-01", "2099-01-01");
            List<WorkDayDataVO> dayDataVOS = gson.fromJson(rsJson,
                    new TypeToken<List<WorkDayDataVO>>() {
                    }.getType());
            mv.addObject("dayDataVOS", dayDataVOS);
        }
    }

    /**
     * 导出考勤表
     */
    @RequestMapping("/admin/jiaowu/exportAttend")
    public void queryExportAttend(HttpServletResponse response) {
        try {

            // 查询打卡记录
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            long linkId = Long.parseLong(userMap.get("linkId"));
            List<Classes> classesList = classesBiz.find(null,
                    "teacherId=" + linkId + " ORDER BY id DESC");

            // 最新的班级
            Classes clz = classesList.get(0);
            // 班级开始时间
            Date startTime = clz.getStartTime();
            String strStart = DateUtils.format(startTime, DateUtils.PATTERN_YYYY_MM_DD);
            // 班级结束时间
            Date endTime = clz.getEndTime();
            String strEnd = DateUtils.format(endTime, DateUtils.PATTERN_YYYY_MM_DD);
            // 该班的所有学员
            List<User> userList = userBiz.find(null, "classId=" + clz.getId());

            /// 所有学员的考勤卡号
            String cardNOs = getStringCardNO(userList);

            List<WorkDayDataVO> dayDataVOS = new ArrayList<>();
            if (StringUtils.isNotBlank(cardNOs)) {
                // 按照每个学员分组 查询. 把卡号传到一卡通模块, 接收返回值
                String rsJson = workSourceService.queryAllWordData(cardNOs, strStart, strEnd);
                dayDataVOS = gson.fromJson(rsJson,
                        new TypeToken<List<WorkDayDataVO>>() {
                        }.getType());
            }

            exportExcel(response, dayDataVOS);
        } catch (Exception e) {
            log.error("AttendRecordController.queryExportAttend", e);
        }
    }

    /**
     * 导出考勤表
     */
    @RequestMapping("/admin/jiaowu/exportAllUserAttend")
    public void queryexportAllUserAttend(HttpServletResponse response) {
        try {
            // 该班的所有学员
            List<User> userList = userBiz.findAll();

            /// 所有学员的考勤卡号
            String cardNOs = getStringCardNO(userList);

            List<WorkDayDataVO> dayDataVOS = new ArrayList<>();
            if (StringUtils.isNotBlank(cardNOs)) {
                // 按照每个学员分组 查询. 把卡号传到一卡通模块, 接收返回值
                String rsJson = workSourceService.queryAllWordData(cardNOs, "2000-01-01", "2099-01-01");
                dayDataVOS = gson.fromJson(rsJson,
                        new TypeToken<List<WorkDayDataVO>>() {
                        }.getType());
            }

            exportExcel(response, dayDataVOS);
        } catch (Exception e) {
            log.error("AttendRecordController.queryExportAttend", e);
        }
    }

    private void exportExcel(HttpServletResponse response, List<WorkDayDataVO> dayDataVOS) throws Exception {
        // 指定文件生成路径
        String dir = request.getSession().getServletContext().getRealPath("/FileList");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String date = df.format(new Date());
        // 文件名
        String expName = "考勤表_" + date;
        String[] headName = {"打卡日期", "打卡记录"};
        // 生成的excel的文件的list
        List<File> srcfile = new ArrayList<>();
        List<List<String>> list;
        for (WorkDayDataVO dayDataVO : dayDataVOS) {
            if (CollectionUtils.isEmpty(dayDataVO.getNodes())) {
                continue;
            } else {
                list = nodeJoint(dayDataVO.getNodes());
            }
            String fileName = dayDataVO.getName() + "的打卡记录";
            File file = FileExportImportUtil.createExcel(headName, list, fileName, dir);
            srcfile.add(file);
        }

        if (CollectionUtils.isEmpty(srcfile)) {
            expName = "学员暂无打卡数据";
        }

        FileExportImportUtil.createRar(response, dir, srcfile, expName);
    }

    private List<List<String>> nodeJoint(List<WorkDayDataNode> nodes) {
        List<List<String>> rs = new ArrayList<List<String>>();
        if (CollectionUtils.isEmpty(nodes)) {
            return new ArrayList<>();
        }

        for (WorkDayDataNode node : nodes) {
            List<String> small = new ArrayList<String>();
            rs.add(small);
            // 打卡日期
            String dateStr = node.getWorkDate();
            // 记录
            String memoStr = node.getMemo();

            small.add((dateStr != null) ? dateStr : "");
            small.add((memoStr != null) ? memoStr : "");
        }
        return rs;
    }


}
