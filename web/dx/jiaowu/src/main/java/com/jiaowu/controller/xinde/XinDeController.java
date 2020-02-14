package com.jiaowu.controller.xinde;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.common.CommonBiz;
import com.jiaowu.biz.evaluate.EvaluateBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.xinde.XinDeBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.entity.evaluate.Evaluate;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.xinde.XinDe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 心得Controller
 *
 * @author 李帅雷
 */
@Controller
public class XinDeController extends BaseController {

    /**
     * sysUser表中的USER_TYPE
     */
    public static final String USER_TYPE = "userType";
    /**
     * sysUser表中的USER_TYPE
     */
    public static final String TEACHER_USER_TYPE = "2";
    private static final String ADMIN_PREFIX = "/admin/jiaowu/xinDe";
    private static Logger logger = LoggerFactory.getLogger(XinDeController.class);
    @Autowired
    private XinDeBiz xinDeBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private CommonBiz commonBiz;
    @Autowired
    private EvaluateBiz evaluateBiz;

    @InitBinder({"xinDe"})
    public void initXinDe(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("xinDe.");
    }

    @InitBinder({"evaluate"})
    public void initEvaluate(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("evaluate.");
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 跳转到新建心得的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/toCreateXinDe")
    public String createXinDe(HttpServletRequest request) {
        return "/admin/xinde/create_xinde";
    }

    /**
     * @param request
     * @param xinDe
     * @return java.util.Map
     * @Description 创建心得
     */
    @RequestMapping(ADMIN_PREFIX + "/createXinDe")
    @ResponseBody
    public Map<String, Object> createXinDe(HttpServletRequest request, @ModelAttribute("xinDe") XinDe xinDe) {
        Map<String, Object> json = null;
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            String errorInfo = xinDeBiz.validateXinDe(xinDe, userMap);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                return json;
            }
            User user = userBiz.findById(Long.parseLong(userMap.get("linkId")));
            xinDe.setClassId(user.getClassId());
            xinDe.setStudentId(Long.parseLong(userMap.get("linkId")));
            xinDe.setStudentName(commonBiz.getCurrentUserName(request));
            xinDeBiz.save(xinDe);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 评价列表
     *
     * @return 列表
     */
    @RequestMapping(ADMIN_PREFIX + "/evaluateList")
    public ModelAndView evaluateList(@ModelAttribute("pagination") Pagination pagination,
                                     HttpServletRequest request,
                                     Long tipsId) {
        ModelAndView mv = new ModelAndView("/admin/evaluate/evaluate_list");
        try {
            pagination.setRequest(request);
            List<Evaluate> evaluates = evaluateBiz.find(pagination, "tipsId = " + tipsId);
            mv.addObject("evaluates", evaluates);

            // 环回心得ID
            mv.addObject("tipsId", tipsId);
        } catch (Exception e) {
            logger.error("XinDeController.evaluateList", e);
        }
        return mv;
    }

    /**
     * 跳转添加评价心得
     *
     * @return 添加评价
     */
    @RequestMapping(ADMIN_PREFIX + "/toSaveEvaluate")
    public ModelAndView toEvaluateXinDe(HttpServletRequest request, @RequestParam("tipsId") Long tipsId) {
        ModelAndView mv = new ModelAndView("/admin/evaluate/evaluate_xinde");
        try {
            // 环回心得ID
            mv.addObject("tipsId", tipsId);
            // 根据心得ID 查询学员ID和名称
            XinDe xinDe = xinDeBiz.findById(tipsId);
            mv.addObject("studentId", xinDe.getStudentId());
            mv.addObject("studentName", xinDe.getStudentName());
        } catch (Exception e) {
            logger.error("XinDeController.toEvaluateXinDe", e);
        }
        return mv;
    }

    /**
     * 添加心得评价
     *
     * @param evaluate
     * @return json
     */
    @RequestMapping(ADMIN_PREFIX + "/evaluateXinDe")
    @ResponseBody
    public Map<String, Object> evaluateXinDe(@ModelAttribute("evaluate") Evaluate evaluate, HttpServletRequest request) {
        Map<String, Object> json;
        try {
            // 获取当前用户的ID
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (!userMap.get(USER_TYPE).equals(TEACHER_USER_TYPE)) {
                return this.resultJson(ErrorCode.ERROR_SYSTEM, "只有教师才能评论", null);
            }
            // 当前用户ID
            evaluate.setEvaluatorId(Long.valueOf(userMap.get("id")));
            // 当前用户名称
            evaluate.setEvaluatorName(userMap.get("userName"));
            evaluateBiz.save(evaluate);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("XinDeController.evaluateXinDe", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 评价详情
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/descEvaluate")
    public ModelAndView descEvaluate(Long id) {
        ModelAndView mv = new ModelAndView("/admin/evaluate/evaluate_desc");
        try {
            Evaluate evaluate = evaluateBiz.findById(id);
            mv.addObject("evaluate", evaluate);
        } catch (Exception e) {
            logger.error("XinDeController.descEvaluate", e);
        }
        return mv;
    }


    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 心得列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/xinDeList")
    public String xinDeList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {

            List<XinDe> xinDeList = null;
            String whereSql = " status=1";
            XinDe xinDe = new XinDe();
            String studentId = request.getParameter("studentId");
            if (!StringUtils.isTrimEmpty(studentId) && Long.parseLong(studentId) > 0) {
                whereSql += " and studentId=" + studentId;
                xinDe.setStudentId(Long.parseLong(studentId));
            }
            String meetingId = request.getParameter("meetingId");
            if (!StringUtils.isTrimEmpty(meetingId) && Long.parseLong(meetingId) > 0) {
                whereSql += " and meetingId=" + meetingId;
                xinDe.setMeetingId(Long.parseLong(meetingId));
            }
            String classId = request.getParameter("classId");
            if (!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId) > 0) {
                whereSql += " and classId=" + classId;
                xinDe.setClassId(Long.parseLong(classId));
            }
            pagination.setRequest(request);
            xinDeList = xinDeBiz.find(pagination, whereSql);
            request.setAttribute("xinDeList", xinDeList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("xinDe", xinDe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/xinde/xinde_list";
    }

    /**
     * 将userList中的id组成字符串
     *
     * @param userList
     * @return
     */
    public String getUserIdsByUserList(List<User> userList) {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        for (User user : userList) {
            sb.append(user.getId() + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    /**
     * @param request
     * @param id
     * @return java.lang.String
     * @Description 某心得详情
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/queryXinDe")
    public String queryXinDe(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            XinDe xinDe = xinDeBiz.findById(id);
            request.setAttribute("xinDe", xinDe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/xinde/query_xinde";
    }

    /**
     * @param request
     * @param response
     * @Description 导出心得列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/exportExcel")
    public void userListExport(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/xinde");
            String expName = "心得列表_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] headName = {"ID", "学员ID", "学员名称", "内容", "类型", "会议ID", "会议名称", "备注", "创建时间"};
            List<File> srcfile = xinDeBiz.getExcelFileList(request, dir, headName, expName);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param request
     * @param id
     * @return java.util.Map
     * @Description 删除心得
     */
    @RequestMapping(ADMIN_PREFIX + "/delXinDe")
    @ResponseBody
    public Map<String, Object> delXinDe(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            XinDe xinde = new XinDe();
            xinde.setId(id);
            xinde.setStatus(0);
            xinDeBiz.update(xinde);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }
}
