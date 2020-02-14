package com.sms.biz.email;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.sms.biz.common.BaseHessianService;
import com.sms.dao.email.EmailDao;
import com.sms.entity.email.Email;
import com.sms.entity.email.EmailOrientation;
import com.sms.utils.email.EmailSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 邮件操作的Biz。包括发送邮件、查询发送记录、删除发送记录等
 *
 * @author sk
 */
@Service
public class EmailBiz extends BaseBiz<Email, EmailDao> {

    private static final Logger logger = LoggerFactory.getLogger(EmailBiz.class);

    @Autowired
    private EmailOrientationBiz emailOrientationBiz;
    @Autowired
    private EmailSendService emailSendService;
    @Autowired
    private BaseHessianService baseHessianService;

    /**
     * 发送邮件
     *
     * @param email 邮件。包括邮件的主题、内容和收件人邮箱地址
     * @return 发送结果
     */
    public String sendEmail(Email email) {
        return sendEmail(email, null);
    }

    /**
     * 发送邮件
     *
     * @param email 邮件。包括邮件的主题、内容和收件人邮箱地址
     * @param is    邮件附件的输入流。如果没有附件可以传{@code null}
     * @return 发送结果。成功或者失败
     */
    public String sendEmail(Email email, InputStream is) {
        try {
            String sendResult = emailSendService.sendEmail(email, is);
            if (ErrorCode.SUCCESS.equals(sendResult)) {
                email.setRemarks("发送成功");
                save(email);
                return "发送成功";
            } else {
                email.setRemarks(sendResult);
                save(email);
                return "发送失败";
            }
        } catch (Exception e) {
            logger.error("sendEmail()-error", e);
            return "发送失败";
        }
    }

    /**
     * 发送邮件
     *
     * @param email   邮件。包括邮件的主题、内容
     * @param userIds 收件人的ID
     * @param is      附件的输入流。如果没有附件可以传{@code null}
     * @return 发送结果
     */
    public String tx_sendEmail(Email email, List<Long> userIds, InputStream is) {
        try {
            if (ObjectUtils.isNull(userIds)) {
                return "收件人不能为空";
            }
            String receivers = getUserEmails(userIds);
            email.setReceivers(receivers);
            String sendResult = emailSendService.sendEmail(email, is);
            if (ErrorCode.SUCCESS.equals(sendResult)) {
                sendResult = "发送成功";
                email.setRemarks(sendResult);
            } else {
                email.setRemarks(sendResult);
                sendResult = "发送失败";
            }
            save(email);
            List<EmailOrientation> orientations = new LinkedList<>();
            Long emailId = email.getId();
            for (Long userId : userIds) {
                EmailOrientation orientation = new EmailOrientation();
                orientation.setEmailId(emailId);
                orientation.setReceiverId(userId);
                orientations.add(orientation);
            }
            emailOrientationBiz.saveBatch(orientations);
            return sendResult;
        } catch (Exception e) {
            logger.error("tx_sendEmail()-error", e);
            return "发送失败";
        }
    }

    /**
     * 得到收件人(用户)的邮箱
     *
     * @param userIds 收件人的id
     * @return 收件人(用户)的邮箱
     */
    @SuppressWarnings("unchecked")
    private String getUserEmails(List<Long> userIds) {
        List<Map<String, String>> users = (List<Map<String, String>>) getUsers(userIds).get("userList");
        int size = users.size();
        List<String> emails =  users.parallelStream().map(u -> u.get("email")).collect(Collectors.toList());
        return emails.toString().replaceAll("]|\\[", "");
    }

    /**
     * 根据条件查询收件人。收件人的邮箱不能为空且状态不能为0
     *
     * @param userIds 收件人的id
     * @return 收件人列表
     */
    private Map<String, Object> getUsers(List<Long> userIds) {
        String where = " id in (" + userIds.toString().replaceAll("\\[|]", "") + ") and email IS NOT NULL and email <> '' AND status = 0";
        return baseHessianService.querySysUserList(null, where);
    }
}
