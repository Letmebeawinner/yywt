package com.oa.biz.asyncTask;

import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.common.SmsHessianService;
import com.oa.entity.message.InfoRecord;
import com.oa.entity.sysuser.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : 金烁
 * @CreateTime: 2018-12-10 18:29
 * @Description: 异步发送消息
 */
@Service("sendMessageAsyncTask")
public class SendMessageAsyncTask {
    @Autowired
    private SmsHessianService smsHessianService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    @Async("asyncExecutor")
    public void sendAgendaMessage(List<String> userIdList,Long agendaId,Long userId){
        try {
            for (int i=0;i<10000000;i++){
                System.out.println("异步任务: 发送消息");
            }
            smsHessianService.sendInfo("您的议题/议程已通过审批，请及时查看相关详细信息。<a style='color:red;' href='http://10.100.101.1:6687/admin/oa/queryMeetingAgenda.json?id="+agendaId+"'>点击查看</a>",0L,String.join(",",userIdList),InfoRecord.InfoType.SEND_BY_SYS);
            for (String id : userIdList) {
                SysUser sysUser = baseHessianBiz.getSysUserById(Long.valueOf(id));
                Map<String, String> map = new HashMap<>();
                if (sysUser.getMobile() == null || sysUser.getMobile() == "") {
                    continue;
                }
                map.put("mobiles", sysUser.getMobile());
                map.put("context", "您的议题/议程已通过审批，请及时登陆智慧校园软件查看相关详细信息。");
                map.put("sendType", "3");
                map.put("sendUserId", "" + userId);
                map.put("receiveUserIds", null);
                smsHessianService.sendMsg(map);
            }
            System.out.println("异步任务: 发送消息");
        } catch (Exception e) {
            System.out.println("sendMessageAsyncTask.Exception"+e);
        }
        
    }
    
}