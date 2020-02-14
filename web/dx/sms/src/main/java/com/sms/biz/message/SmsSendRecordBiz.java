package com.sms.biz.message;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;


import com.sms.dao.message.SmsSendRecordDao;
import com.sms.entity.message.MessageUser;
import com.sms.entity.message.SmsSendRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信记录Biz
 * 林明亮
 * Created by Administrator on 2016/12/22.
 */
@Service
public class SmsSendRecordBiz extends BaseBiz<SmsSendRecord,SmsSendRecordDao>{

    @Autowired
    private MessageUserBiz messageUserBiz;
    /**
     * 添加短信发送记录，同时添加短息记录和用户的中间表
     * @param smssendrecord 短信记录
     * @param userIds 短信学员id
     */
    public void tx_saveSmssendrecord(SmsSendRecord smssendrecord, String userIds){
        this.save(smssendrecord);
        if(!StringUtils.isTrimEmpty(userIds)){//添加短信 用户 中间表
            String [] userId=userIds.trim().substring(0,userIds.length()-1).split(",");
            List<MessageUser> messageUserList=new ArrayList<>();
            for (String receiveUserId:userId){
                MessageUser messageUser=new MessageUser();
                messageUser.setRecodeId(smssendrecord.getId());
                messageUser.setReceiveUserId(Long.parseLong(receiveUserId));
                messageUserList.add(messageUser);
            }
            messageUserBiz.saveBatch(messageUserList);
        }
    }

}
