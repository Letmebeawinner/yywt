package com.sms.biz.email;

import com.a_268.base.core.BaseBiz;
import com.sms.dao.email.EmailOrientationDao;
import com.sms.entity.email.EmailOrientation;
import org.springframework.stereotype.Service;

/**
 * 根据用户选择邮件接收人时，存储发送邮件记录id与接收人id的Biz
 *
 * @author sk
 */
@Service
public class EmailOrientationBiz extends BaseBiz<EmailOrientation, EmailOrientationDao> {
}
