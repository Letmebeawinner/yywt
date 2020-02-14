package com.sms.controller.email;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.sms.biz.common.BaseHessianService;
import com.sms.biz.common.SmsHessianService;
import com.sms.biz.email.EmailBiz;
import com.sms.entity.email.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 邮件相关操作的控制器. 包括向指定的用户发送邮件
 * 查询邮件发送记录 删除邮件发送记录等<br>
 * <p>
 * handshake_failure：<br>
 * 下载对应jdk版本的JCE<br>
 * jdk 1.6: <a href="http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html">1.6</a><br>
 * jdk 1.7: <a href="http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html">1.7</a><br>
 * jdk 1.8: <a href="http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html">1.8</a><br>
 * 替换<br>
 * 开发环境：<br>
 * %JAVA_HOME%/jdk/jre/lib/security/local_policy.jar<br>
 * %JAVA_HOME%/jdk/jre/lib/security/US_export_policy.jar<br>
 * 生产环境：<br>
 * %JAVA_HOME%/jre/lib/security/local_policy.jar<br>
 * %JAVA_HOME%/jre/lib/security/US_export_policy.jar
 *
 * @author sk
 * @since 2016-12-26
 */
@Controller
@RequestMapping("/admin/sms/email")
public class EmailController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailBiz emailBiz;
    @Autowired
    private BaseHessianService baseHessianService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("email.");
    }

    /**
     * 根据ID删除邮件记录.删除多条数据时，使用","分隔
     *
     * @param recordIds 邮件记录ID
     * @return {@link ErrorCode#SUCCESS}删除成功
     */
    @RequestMapping("/deleteEmailSendRecord")
    @ResponseBody
    public Map<String, Object> deleteEmailSendRecord(@RequestParam("recordIds") String recordIds) {
        try {
            List<Long> ids = Stream.of(recordIds.replaceAll("\\s", "").split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            emailBiz.deleteByIds(ids);
            return resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("deleteEmailSendRecord()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 根据id获取指定邮件记录的详细信息。包括发件人、收件人等
     *
     * @param recordId 邮件发送记录的ID
     * @return 指定ID的邮件发送记录的详细信息
     */
    @RequestMapping("/getEmailSendRecord")
    public ModelAndView getEmailSendRecord(HttpServletRequest request,
                                           @RequestParam("recordId") Long recordId) {
        ModelAndView mv = new ModelAndView("/email/emailSendRecord");
        try {
            Email email = emailBiz.findById(recordId);
            if (ObjectUtils.isNotNull(email)) {
                Map<String, String> sysUser = baseHessianService.querySysUserById(email.getUserId());
                mv.addObject("email", email);
                mv.addObject("sysUser", sysUser);
            }
            return mv;
        } catch (Exception e) {
            logger.error("getEmailSendRecord()--error", e);
            mv.setViewName(setErrorPath(request, e));
            return mv;
        }
    }

    /**
     * 查询邮件发送记录
     *
     * @param email 查询邮件发送记录的条件(主题或者内容)
     * @return 邮件发送记录
     */
    @RequestMapping("/queryEmailSendRecord")
    public ModelAndView queryEmailSendRecord(HttpServletRequest request,
                                             @ModelAttribute("email") Email email,
                                             @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/email/emailSendRecord-list");
        try {
            pagination.setRequest(request);
            List<Email> emailList = emailBiz.find(pagination, where(email));
            if (ObjectUtils.isNotNull(emailList)) {
                Map<String, String> sysUsers = getSysUserNames(emailList);
                mv.addObject("emailList", emailList);
                mv.addObject("sysUsers", sysUsers);
            }
            return mv;
        } catch (Exception e) {
            logger.error("queryEmailSendRecord()--error", e);
            mv.setViewName(setErrorPath(request, e));
            return mv;
        }
    }

    /**
     * 查询收件人(用户)
     *
     * @param request    {@link HttpServletRequest}
     * @param pagination {@link Pagination} 分页
     * @return 收件人(用户列表)
     */
    @RequestMapping("/queryReceivers")
    @SuppressWarnings("unchecked")
    public ModelAndView queryReceivers(HttpServletRequest request,
                                       @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/user/userList");
        try {
            pagination.setPageSize(10);
            String where = " email IS NOT NULL AND email <> '' AND status = 0";
            Map<String, Object> map = baseHessianService.querySysUserList(pagination, where);
            List<Map<String, String>> userList = (List<Map<String, String>>) map.get("userList");
            Map<String, String> _pagination = (Map<String, String>) map.get("pagination");
            mv.addObject("userList", userList);
            mv.addObject("_pagination", _pagination);
            mv.addObject("currentPath", request.getRequestURI());
            return mv;
        } catch (Exception e) {
            logger.error("queryReceivers()--error", e);
            mv.setViewName(setErrorPath(request, e));
            return mv;
        }
    }

    /**
     * 发送邮件.
     *
     * @param email email的主题、内容、收件人等
     * @return 发送结果
     * @see EmailBiz#sendEmail(Email)
     * @see com.sms.utils.email.EmailSendService#sendEmail(Email)
     */
    @RequestMapping("/sendEmail")
    @ResponseBody
    public Map<String, Object> sendEmail(HttpServletRequest request,
                                         @ModelAttribute("email") Email email) {
        try {
            String verify = verify(email);
            if (verify != null) {
                return resultJson(ErrorCode.ERROR_PARAMETER, verify, null);
            }
            email.setUserId(SysUserUtils.getLoginSysUserId(request));
            email.setSendType(SmsHessianService.SEND_BY_ADMIN);
            String sendResult;
            if (email.getCustom() == 1) {
                sendResult = emailBiz.sendEmail(email);
            } else {
                List<Long> userIds = Stream.of(email.getReceivers().replaceAll("\\s", "").split(","))
                        .map(Long::valueOf)
                        .collect(Collectors.toList());
                sendResult = emailBiz.tx_sendEmail(email, userIds, null);
            }
            return resultJson(ErrorCode.SUCCESS, sendResult, "/admin/sms/email/queryEmailSendRecord.json");
        } catch (Exception e) {
            logger.error("sendEmail()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 跳转到发送邮件页面
     *
     * @return 邮件页面
     */
    @RequestMapping("/toSendEmail")
    public String toSendEmail() {
        return "/email/emailSend";
    }

    /**
     * 根据邮件记录列表中的系统用户ID得到系统用户名
     *
     * @param emails 邮件记录列表
     * @return key为系统用户ID，值为系统用户名的{@link LinkedHashMap LinkedHashMap}
     */
    private Map<String, String> getSysUserNames(List<Email> emails) {
        List<Long> userIds = emails.stream()
                .filter(e -> e.getUserId() > 0)
                .map(Email::getUserId)
                .collect(Collectors.toList());
        Map<String, String> sysUserNames = new LinkedHashMap<>();
        List<Map<String, String>> sysUsers = baseHessianService.querySysUserByIds(userIds);
        for (Map<String, String> sysUser : sysUsers) {
            sysUserNames.put(sysUser.get("id"), sysUser.get("userName"));
        }
        return sysUserNames;
    }

    /**
     * 验证Email必填字段的合法性
     *
     * @param email Email的信息.包括主题、正文和收件人等
     * @return {@code null} 字段合法，否则返回不合法的字段提示
     */
    private String verify(Email email) {
        if (StringUtils.isTrimEmpty(email.getSubject())) {
            return "邮件主题不能为空";
        }
        if (StringUtils.isTrimEmpty(email.getContent())) {
            return "邮件内容不能为空";
        }
        if (StringUtils.isTrimEmpty(email.getReceivers())) {
            return "收件人不能为空，请选择收件人";
        }
        String receivers = email.getReceivers().trim();
        if(receivers.startsWith(",")){
            receivers = receivers.substring(1,receivers.length());
        }
        if(receivers.endsWith(",")){
            receivers = receivers.substring(0,receivers.length()-1);
        }
        email.setReceivers(receivers);
        String[] eamilArr = receivers.split(",");
        for(String e : eamilArr){
            if(!StringUtils.isEmail(e.trim())){
                return "邮箱号【"+e+"】格式不正确";
            }
        }
        return null;
    }

    /**
     * 拼装sql where语句.按发布时间排序
     *
     * @param email 查询email的条件
     * @return where语句
     */
    private String where(Email email) {
        String where = " 1=1";
        if (!StringUtils.isTrimEmpty(email.getContent())) {
            where += " and (subject like '%" + email.getContent() + "%' or content like '%" + email.getContent() + "%')";
        }
        where += " order by createTime desc";
        return where;
    }
}
