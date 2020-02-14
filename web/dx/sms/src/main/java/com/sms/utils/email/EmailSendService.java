package com.sms.utils.email;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.sms.biz.profile.ProfileBiz;
import com.sms.entity.email.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmailSendService {

    private static final Logger logger = LoggerFactory.getLogger(EmailSendService.class);

    /**
     * smtp服务器地址
     */
    private String smtp;
    /**
     * smtp服务器端口(s指SSL协议)
     * <table>
     * <tbody>
     * <tr><td>端口</td><td>协议</td></tr>
     * <tr><td>25</td><td>SMTP</td></tr>
     * <tr><td>110</td><td>POP2</td></tr>
     * <tr><td>143</td><td>POP3</td></tr>
     * <tr><td>465</td><td>SMTPs</td></tr>
     * <tr><td>993</td><td>IMAPs</td></tr>
     * <tr><td>995</td><td>POP3s</td></tr>
     * </tbody>
     * </table>
     */
    private static final int port = 465;
    /**
     * smtp服务器协议
     *
     * @see #port
     */
    private static final String protocol = "smtps";
    /**
     * 发件人邮箱地址(帐号)
     */
    private String from;
    /**
     * 发件人密码
     */
    private String password;
    /**
     * 邮件编码
     */
    private static final String charset = "utf-8";

    @Autowired
    private ProfileBiz profileBiz;

    /**
     * 发送邮件(不带附件)
     *
     * @param email 邮件
     * @return {@link com.a_268.base.constants.ErrorCode#SUCCESS 发送成功}
     */
    public String sendEmail(Email email) {
        return sendEmail(email, null);
    }

    /**
     * 发送邮件(带附件)
     *
     * @param email 邮件
     * @param is    附件的输入流
     * @return {@link ErrorCode#SUCCESS 发送成功}
     */
    public String sendEmail(Email email, InputStream is) {
        try {
            if (StringUtils.isTrimEmpty(email.getReceivers())) {
                return "收件人不能为空";
            }
            init();
            String subject = email.getSubject();
            String content = email.getContent();
            List<String> emails = filterEmail(email.getReceivers());
            ExecutorService service = Executors.newCachedThreadPool();
            emails.parallelStream().forEach(to -> service.submit(new EmailSendThread(subject, content, to, is)));
            return ErrorCode.SUCCESS;
        } catch (Exception e) {
            return "发送失败：" + "错误信息为[" + e.getLocalizedMessage() + "]";
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    logger.error("sendEmail()--error", ex);
                }
            }
        }
    }

    /**
     * 邮件添加附件
     *
     * @param message {@link MimeMessage} MimeMessage
     * @param is      附件的输入流
     * @throws MessagingException {@link MessagingException} MessagingException
     */
    private void appendEmailAttachments(MimeMessage message, InputStream is) throws MessagingException {
        if (ObjectUtils.isNotNull(is)) {
            Multipart multipart = new MimeMultipart();
            MimeBodyPart part = new MimeBodyPart(is);
            multipart.addBodyPart(part);
            message.setContent(multipart);
        }
    }

    /**
     * 配置{@link JavaMailSenderImpl JavaMailSenderImpl}
     * 包括smtp服务器 端口 用户名 密码 协议
     *
     * @param sender {@link JavaMailSenderImpl JavaMailSenderImpl}
     */
    private void appendEmailConfig(JavaMailSenderImpl sender) {
        sender.setHost(smtp);
        sender.setPassword(password);
        sender.setPort(port);
        sender.setProtocol(protocol);
        sender.setUsername(from);
    }

    /**
     * 添加邮件信息. 邮件标题 正文 收件人
     *
     * @param helper  {@link MimeMessageHelper MimeMessageHelper}
     * @param subject 邮件主题
     * @param content 邮件正文
     * @param to      收件人邮箱
     * @throws Exception exception
     */
    private void appendEmailInfo(MimeMessageHelper helper, String subject, String content, String to) throws Exception {
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(content);
        helper.setTo(new InternetAddress(to));
    }

    /**
     * 过滤掉不合法的邮箱
     *
     * @param receivers 收件人的邮箱
     * @return 过滤掉不合法邮箱的收件人的邮箱
     */
    private List<String> filterEmail(String receivers) {
        return Stream.of(receivers.replaceAll("\\s", "").split(","))
                .parallel()
                .filter(StringUtils::isEmail) // 过滤掉不合法邮箱
                .collect(Collectors.toList());
    }

    /**
     * 初始化邮箱的配置.smtp服务器地址 发件人地址 帐号 密码等
     */
    private void init() {
        Map<String, String> config = profileBiz.getConfigInfo("email");
        smtp = config.get("smtp");
        from = config.get("email");
        password = config.get("password");
    }

    /**
     * 发送邮件线程
     *
     * @author sk
     * @since 2017-01-04
     */
    class EmailSendThread implements Runnable {

        //邮件主题
        private String subject;
        //邮件内容
        private String content;
        //收件人邮箱
        private String to;
        //邮件附件
        private InputStream is;

        /**
         * 初始化发送邮件线程
         *
         * @param subject 邮件主题
         * @param content 邮件内容
         * @param to      收件人
         * @param is      附件输入流，可为{@code null}
         */
        EmailSendThread(String subject, String content, String to, InputStream is) {
            this.subject = subject;
            this.content = content;
            this.to = to;
            this.is = is;
        }

        public void run() {
            try {
                JavaMailSenderImpl sender = new JavaMailSenderImpl();
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, charset);

                appendEmailAttachments(message, is);

                appendEmailConfig(sender);

                appendEmailInfo(helper, subject, content, to);

                sender.send(helper.getMimeMessage());
            } catch (Exception e) {
                logger.error("EmailSendThread#run()-error", e);
            }
        }
    }
}