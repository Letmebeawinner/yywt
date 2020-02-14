package com.sms.biz.common;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.core.Pagination;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.sms.biz.email.EmailBiz;
import com.sms.biz.info.InfoRecordBiz;
import com.sms.biz.info.InfoUserReceiveBiz;
import com.sms.biz.info.InfoUserRecordBiz;
import com.sms.biz.message.SmsSendRecordBiz;
import com.sms.entity.email.Email;
import com.sms.entity.info.InfoRecord;
import com.sms.entity.info.InfoUserReceive;
import com.sms.entity.info.InfoUserRecord;
import com.sms.entity.message.SmsSendRecord;
import com.sms.utils.message.SendMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 发送短信和邮件的对外接口实现类
 */
@Service
public class SmsHessianBiz implements SmsHessianService {

    private static final Logger logger = LoggerFactory.getLogger(SmsHessianBiz.class);

    private final RedisCache redisCache = RedisCache.getInstance();

    @Autowired
    private EmailBiz emailBiz;
    @Autowired
    private InfoRecordBiz infoRecordBiz;
    @Autowired
    private InfoUserReceiveBiz infoUserReceiveBiz;
    @Autowired
    private InfoUserRecordBiz infoUserRecordBiz;
    @Autowired
    private SmsSendRecordBiz smssendrecordBiz;
    @Autowired
    private BaseHessianService baseHessianService;

    @Override
    public Map<String, Object> deleteInfo(Long infoUserReceiveId, Boolean downright) {
        return deleteInfo(String.valueOf(infoUserReceiveId), downright);
    }

    @Override
    public Map<String, Object> deleteInfo(String infoUserReceiveIds, Boolean downright) {
        try {
            if (!StringUtils.isTrimEmpty(infoUserReceiveIds)) {
                String where = " id in (" + infoUserReceiveIds + ")";
                List<InfoUserReceive> receives = infoUserReceiveBiz.find(null, where);
                if (ObjectUtils.isNotNull(receives)) {
                    if (downright != null && downright) { /* 彻底删除 */
                        List<Long> ids = receives.stream().map(InfoUserReceive::getId).collect(Collectors.toList());
                        infoUserReceiveBiz.deleteByIds(ids);
                    } else { /* 回收站 */
                        receives.forEach(r -> r.setStatus(1));
                        infoUserReceiveBiz.updateBatch(receives);
                    }
                }
            }
            return resultJson(ErrorCode.SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("deleteInfo()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    @Override
    public Map<String, Object> deleteInfoInRecycleBin(Long infoUserReceiveId) {
        return deleteInfoInRecycleBin(String.valueOf(infoUserReceiveId));
    }

    @Override
    public Map<String, Object> deleteInfoInRecycleBin(String infoUserReceiveIds) {
        try {
            if (!StringUtils.isTrimEmpty(infoUserReceiveIds)) {
                String where = " id in (" + infoUserReceiveIds + ")";
                List<InfoUserReceive> infoUserReceiveList = infoUserReceiveBiz.find(null, where);
                if (ObjectUtils.isNotNull(infoUserReceiveList)) {
                    List<Long> ids = infoUserReceiveList.parallelStream().map(InfoUserReceive::getId).collect(Collectors.toList());
                    infoUserReceiveBiz.deleteByIds(ids);
                }
            }
            return resultJson(ErrorCode.SUCCESS, null, null);
        } catch (Exception e) {
            logger.error("deleteInfoInRecycleBin()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    @Override
    public Map<String, Object> findInfoList(Pagination pagination, Long receiverId, Integer status) {
        Map<String, Object> map = new HashMap<>();
        if (status == null)
            status = 0;
        String where = " receiverId=" + receiverId + " AND status=" + status;
        List<InfoUserReceive> infoUserReceiveList = infoUserReceiveBiz.find(pagination, where);
        List<Map<String, String>> infoList = new LinkedList<>();
        if (ObjectUtils.isNotNull(infoUserReceiveList)) {
            Map<String, String> receiver = baseHessianService.querySysUserById(receiverId);
            //消息发送人用户名
            List<Long> senderIds = infoUserReceiveList.parallelStream()
                    .map(InfoUserReceive::getSenderId).distinct()
                    .collect(Collectors.toList());
            if (ObjectUtils.isNotNull(senderIds)) {
                List<Map<String, String>> senders = baseHessianService.querySysUserByIds(senderIds);
                Map<String, String> senderMap = new HashMap<>();
                if (ObjectUtils.isNotNull(senders))
                    senders.parallelStream().forEach(s -> senderMap.put(s.getOrDefault("id", "0"), s.getOrDefault("userName", "system")));
                //消息列表添加发送人用户名和接收人用户名
                infoList.addAll(ObjectUtils.listObjToListMap(infoUserReceiveList));
                //消息接收人用户名
                String[] receiverName = new String[1];
                if (ObjectUtils.isNotNull(receiver))
                    receiverName[0] = receiver.get("userName");
                infoList.parallelStream().forEach(m -> {
                    m.put("senderName", senderMap.get(m.get("senderId")));
                    m.put("receiverName", receiverName[0]);
                });
            }
        }
        map.put("infoList", infoList);
        map.put("pagination", ObjectUtils.objToMap(pagination));
        return map;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String> findInfoDetail(Long infoUserReceiveId) {
        Map<String, String> result = new HashMap<>();
        InfoUserReceive receive = infoUserReceiveBiz.findById(infoUserReceiveId);
        Map<String, String> sysUser = baseHessianService.querySysUserById(receive.getSenderId());
        if (ObjectUtils.isNotNull(sysUser))
            result.put("sender", sysUser.get("userName"));
        result.putAll(ObjectUtils.objToMap(receive));
        return result;
    }

    @Override
    public Map<String, Object> findSendInfoList(Pagination pagination, Long senderId) {
        try {
            List<InfoRecord> infoRecordList = new LinkedList<>();
            Map<String, Object> data = new HashMap<>();
            String[] senderName = new String[1];
            if (ObjectUtils.isNotNull(senderId)) {
                String where = " senderId = " + senderId;
                //发送记录
                infoRecordList.addAll(infoRecordBiz.find(pagination, where));
                Map<String, String> sysUser = baseHessianService.querySysUserById(senderId);
                if (ObjectUtils.isNotNull(sysUser))
                    senderName[0] = sysUser.getOrDefault("userName", "");
            } else {
                senderName[0] = "system";
            }
            List<Map<String, String>> infoRecordMapList = ObjectUtils.listObjToListMap(infoRecordList);
            if (infoRecordMapList != null && infoRecordMapList.size() > 0) {
                infoRecordMapList.forEach(i -> i.put("senderName", senderName[0]));
                data.put("infoRecordList", infoRecordMapList);
                data.put("pagination", pagination);
            } else {
                data.put("infoRecordList", infoRecordMapList);
                data.put("pagination", pagination);
            }

            return resultJson(ErrorCode.SUCCESS, null, data);
        } catch (Exception e) {
            logger.error("findSendInfoList()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    @Override
    public Map<String, Object> findSendInfoDetail(Long infoRecordId) {
        try {
            if (ObjectUtils.isNull(infoRecordId)) {
                return resultJson(ErrorCode.ERROR_PARAMETER, "记录不存在", null);
            } else {
                InfoRecord infoRecord = infoRecordBiz.findById(infoRecordId);
                if (null == infoRecord) {
                    return resultJson(ErrorCode.ERROR_DATA, "记录不存在", null);
                } else {
                    String receiverNames;
                    if (isInfoSendToAll(infoRecord.getInfoType())) {
                        receiverNames = "该消息发送给所有人。";
                    } else {
                        Pagination pagination = new Pagination();
                        pagination.setPageSize(10);
                        List<InfoUserRecord> list = infoUserRecordBiz.find(pagination, " infoId =" + infoRecordId);
                        if (ObjectUtils.isNull(list)) {
                            return resultJson(ErrorCode.ERROR_DATA, "记录不存在", null);
                        } else {
                            List<Long> ids = list.stream().map(InfoUserRecord::getReceiverId).collect(Collectors.toList());
                            List<Map<String, String>> receivers = baseHessianService.querySysUserByIds(ids);
                            if (ObjectUtils.isNull(receivers)) {
                                return resultJson(ErrorCode.ERROR_DATA, "记录不存在", null);
                            } else {
                                StringBuilder sb = new StringBuilder();
                                receivers.forEach(r -> sb.append(r.getOrDefault("userName", " ")).append(','));
                                receiverNames = sb.toString();
                                if (pagination.getTotalCount() > 10) {
                                    receiverNames += "...等" + pagination.getTotalCount() + "人";
                                } else {
                                    int lastIndexOf = receiverNames.lastIndexOf(',');
                                    receiverNames = receiverNames.substring(0, lastIndexOf - 1);
                                }
                            }
                        }
                    }
                    Map<String, String> data = ObjectUtils.objToMap(infoRecord);
                    data.put("receiverNames", receiverNames);
                    return resultJson(ErrorCode.SUCCESS, null, data);
                }
            }
        } catch (Exception e) {
            logger.error("findSendInfoDetail()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    @Override
    public Map<String, Object> recoverInfoInRecycleBin(Long infoUserReceiveId) {
        return recoverInfoInRecycleBin(String.valueOf(infoUserReceiveId));
    }

    @Override
    public Map<String, Object> recoverInfoInRecycleBin(String infoUserReceiveIds) {
        if (!StringUtils.isTrimEmpty(infoUserReceiveIds)) {
            String where = " id in (" + infoUserReceiveIds + ") AND status=1";
            List<InfoUserReceive> infoUserReceiveList = infoUserReceiveBiz.find(null, where);
            if (ObjectUtils.isNotNull(infoUserReceiveList)) {
                infoUserReceiveList.forEach(i -> i.setStatus(0));
                infoUserReceiveBiz.updateBatch(infoUserReceiveList);
            }
        }
        return resultJson(ErrorCode.SUCCESS, null, null);
    }

    @Override
    public String sendEmail(String subject, String content, String receivers, int sendType) {
        return sendEmail(subject, content, receivers, sendType, null);
    }

    @Override
    public String sendEmail(String subject, String content, String receivers, int sendType, InputStream is) {
        try {
            Email email = new Email();
            email.setSubject(subject);
            email.setContent(content);
            email.setReceivers(receivers);
            return emailBiz.sendEmail(email, is);
        } catch (Exception e) {
            logger.error("sendEmail()--error", e);
            return e.getLocalizedMessage();
        }
    }

    @Override
    public String sendEmail(String subject, String content, int sendType, List<Long> userIds) {
        return sendEmail(subject, content, sendType, userIds, null);
    }

    @Override
    public String sendEmail(String subject, String content, int sendType, List<Long> userIds, InputStream is) {
        try {
            Email email = new Email();
            email.setSubject(subject);
            email.setContent(content);
            email.setSendType(sendType);
            return emailBiz.tx_sendEmail(email, userIds, is);
        } catch (Exception e) {
            logger.error("sendEmail()--error", e);
            return e.getLocalizedMessage();
        }
    }

    @Override
    public String sendInfo(String content, Long senderId, String receiverIds, int infoType) {
        InfoRecord infoRecord = new InfoRecord();
        infoRecord.setContent(content);
        infoRecord.setSenderId(senderId);
        infoRecord.setInfoType(infoType);
        return infoRecordBiz.tx_sendInfo(infoRecord, receiverIds);
    }

    @Override
    public boolean sendMsg(Map<String, String> map) throws Exception {
        return this.sendAndSaveRecord(map);
    }

    /**
     * 判断消息是否为发送所有人
     *
     * @param infoType 消息类型
     * @return {@code true} 发送所有人, {@code false} 发送部分人
     */
    private boolean isInfoSendToAll(Integer infoType) {
        return infoType == InfoRecord.InfoType.SYS_SEND_ALL
                || infoType == InfoRecord.InfoType.ADMIN_SEND_ALL;
    }

    /**
     * 操作返回结果
     *
     * @param code    {@link ErrorCode}
     * @param message 操作结果信息
     * @param data    操作结果数据
     * @return 操作结果
     * @since 2017-02-20
     */
    private Map<String, Object> resultJson(String code, String message, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        map.put("data", data);
        return map;
    }

    /**
     * 发送短信  发送成功则储存短信内容
     *
     * @param map 短信参数
     * @return {@code true} 发送成功
     * @throws ApiException ApiException
     */
    private boolean sendAndSaveRecord(Map<String, String> map){
        String mobiles = map.get("mobiles");
        String context = map.get("context");
        String result = SendMessageUtils.sendMsg(mobiles, context);
        if (result == null) {
            SmsSendRecord smssendrecord = new SmsSendRecord();
            smssendrecord.setContext(context);
            smssendrecord.setSendType(Integer.parseInt(map.get("sendType")));//发送类型
            smssendrecord.setUserId(Long.parseLong(map.get("sendUserId")));//发送者用户id
            smssendrecord.setMobiles(mobiles);//接收者手机号码串
            smssendrecordBiz.tx_saveSmssendrecord(smssendrecord, map.get("receiveUserIds"));
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> getMyInfoRecordList(Pagination pagination, Long userId) {
        InfoUserReceive infoUserReceive=new InfoUserReceive();
        infoUserReceive.setStatus(0);
        List<InfoUserReceive> infoList = infoRecordBiz.tx_queryInfoReceiveList(userId, pagination,infoUserReceive);
        List<Long> senderIds = infoList.parallelStream()
                .distinct().map(InfoUserReceive::getSenderId)
                .collect(Collectors.toList());
        Map<String, String> senders = getSysUsers(senderIds);
        Map<String, Object> data=new HashMap<>();
        data.put("infoRecordList", ObjectUtils.listObjToListMap(infoList));
        data.put("senders", senders);
        return data;
    }

    /**
     * 根据id查询系统用户名
     *
     * @param sysUserIds 系统用户id
     * @return {@link Map} 系统用户名.key为系统用户id，value为系统用户名
     */
    @SuppressWarnings("unchecked")
    private Map<String, String> getSysUsers(List<Long> sysUserIds) {
        if (ObjectUtils.isNotNull(sysUserIds)) {
            List<Map<String, String>> sysUsers = baseHessianService.querySysUserByIds(sysUserIds);
            if (ObjectUtils.isNotNull(sysUsers)) {
                Map<String, String> userNames = new LinkedHashMap<>();
                sysUsers.parallelStream()
                        .forEach(u -> userNames.put(u.get("id"), u.get("userName")));
                userNames.put("names", userNames.values().toString().replaceAll("]|\\[", ""));
                return userNames;
            }
        }
        return new HashMap<>(0);
    }

    @Override
    public void updateInfoReceiveRecord(Long id) {
        if(ObjectUtils.isNotNull(id)){
            InfoUserReceive infoUserReceive=infoUserReceiveBiz.findById(id);
            infoUserReceive.setStatus(1);
            infoUserReceiveBiz.update(infoUserReceive);

//            InfoRecord infoRecord=new InfoRecord();
//            infoRecord.setId(infoUserReceive.getInfoId());
//            infoRecord.setStatus(1);
//            infoRecordBiz.update(infoRecord);
        }
    }


    /**
     * 批量发送议程信息
     *
     * @param userIdList    接收人ID集合
     * @param agendaId    议程ID
     * @param userId    发送人ID
     */
    @Override
    public void batchSendAgendaMessage(List<String> userIdList, Long agendaId, Long userId) {
//        for (int i=0;i<1000000;i++){
//            System.out.println("异步任务: 发送消息");
//        }
        //发送消息
        InfoRecord infoRecord = new InfoRecord();
        infoRecord.setContent("您的议题/议程已通过审批，请及时查看相关详细信息。<a style=\"color:red;\" href=\"http://10.100.101.1:6687/admin/oa/queryMeetingAgenda.json?id="+agendaId+"\">点击查看</a>");
        infoRecord.setSenderId(0L);
        infoRecord.setInfoType(0);
        infoRecordBiz.tx_sendInfo(infoRecord, String.join(",",userIdList));
        //发送短信
        String mobiles = baseHessianService.getUserMobiles(userIdList);
        Map<String, String> map = new HashMap<>();
        if (mobiles != null || !"".equals(mobiles)) {
            map.put("mobiles", mobiles);
            map.put("context", "您的议题/议程已通过审批，请及时登陆智慧校园软件查看相关详细信息。");
            map.put("sendType", "3");
            map.put("sendUserId", "" + userId);
            map.put("receiveUserIds", null);
            this.sendAndSaveRecord(map);
        }
        System.out.println("异步任务: 发送消息");
    }
}
