package com.sms.biz.info;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.sms.biz.common.BaseHessianService;
import com.sms.dao.info.InfoRecordDao;
import com.sms.entity.info.InfoRecord;
import com.sms.entity.info.InfoUserReceive;
import com.sms.entity.info.InfoUserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 消息操作的Biz
 *
 * @author sk
 * @since 2017-01-20
 */
@Service
public class InfoRecordBiz extends BaseBiz<InfoRecord, InfoRecordDao> {

    @Autowired
    private InfoUserReceiveBiz infoUserReceiveBiz;
    @Autowired
    private InfoUserRecordBiz infoUserRecordBiz;
    @Autowired
    private BaseHessianService baseHessianService;

    /**
     * 删除消息
     *
     * @param id 消息的id
     */
    public void tx_deleteInfo(Long id) {
        //删除infoRecord
        deleteById(id);
        String where = " infoId = " + id;
        List<InfoUserReceive> receiveList = infoUserReceiveBiz.find(null, where);
        List<InfoUserRecord> recordList = infoUserRecordBiz.find(null, where);
        List<Long> ids = new LinkedList<>();
        //删除infoUserReceive
        if (ObjectUtils.isNotNull(receiveList)) {
            ids = receiveList.parallelStream().map(InfoUserReceive::getId).collect(Collectors.toList());
            infoUserReceiveBiz.deleteByIds(ids);
        }
        //删除infoUserRecord
        if (ObjectUtils.isNotNull(recordList)) {
            ids.clear();
            ids = recordList.parallelStream().map(InfoUserRecord::getId).collect(Collectors.toList());
            infoUserRecordBiz.deleteByIds(ids);
        }
    }

    /**
     * 查询系统用户的消息列表
     * @param receiverId  接收人id(系统用户id)
     * @param pagination  分页
     * @param userReceive {@link InfoUserReceive}
     * @return 系统用户的消息列表
     */
    public List<InfoUserReceive> tx_queryInfoReceiveList(Long receiverId,
                                                         Pagination pagination,
                                                         InfoUserReceive userReceive) {
        List<InfoRecord> recordList = new LinkedList<>();
        if (ObjectUtils.isNotNull(receiverId)) {
            //查询最后读取时间
            Map<String, String> map = baseHessianService.querySysUserById(receiverId);
            String lastReadTime = map.getOrDefault("lastReadTime", null); //最后读取时间
            //查询部分发送(创建时间＞最后读取时间)
            String where = " receiverId = " + receiverId;
            if (lastReadTime != null) where += " AND createTime > '" + lastReadTime + "'";
            List<InfoUserRecord> list = infoUserRecordBiz.find(null, where);
            if (ObjectUtils.isNotNull(list)) {
                List<Long> ids = list.stream().map(InfoUserRecord::getInfoId).collect(Collectors.toList());
                recordList = find(null, " id in (" + ids.toString().replaceAll("]|\\[", "") + ")");
            }
            //查询所有发送(创建时间＞最后读取时间)
            where = " infoType=" + InfoRecord.InfoType.SYS_SEND_ALL + " OR infoType=" + InfoRecord.InfoType.ADMIN_SEND_ALL;
            if (lastReadTime != null) where += " AND createTime > '" + lastReadTime + "'";
            List<InfoRecord> temp = find(null, where);
            if (ObjectUtils.isNotNull(temp)) {
                List<InfoUserRecord> userRecordList = new LinkedList<>();
                recordList.addAll(temp);
                temp.forEach(t -> {
                    InfoUserRecord record = new InfoUserRecord();
                    record.setInfoId(t.getId());
                    record.setReceiverId(receiverId);
                    record.setStatus(t.getStatus());
                    userRecordList.add(record);
                });
                //保存所有发送中间表
                infoUserRecordBiz.saveBatch(userRecordList);
            }
            //保存消息记录(所有发送+部分发送)
            if (ObjectUtils.isNotNull(recordList)) {
                List<InfoUserReceive> receiveList = new LinkedList<>();
                recordList.forEach(r -> {
                    InfoUserReceive receive = new InfoUserReceive();
                    receive.setContent(r.getContent());
                    receive.setInfoId(r.getId());
                    receive.setInfoType(r.getInfoType());
                    receive.setSenderId(r.getSenderId());
                    receive.setReceiverId(receiverId);
                    receive.setStatus(r.getStatus());
                    receive.setFileUrl(r.getFileUrl());
                    receive.setFileName(r.getFileName());
                    receiveList.add(receive);
                });
                infoUserReceiveBiz.saveBatch(receiveList);
            }
            String _lastReadTime = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            //更新最后读取时间
            map.put("lastReadTime", _lastReadTime);
            baseHessianService.updateSysUser(map);
            //查询消息
            where = " receiverId =" + receiverId;
            String content = userReceive.getContent();
            if(userReceive.getStatus()!=null && userReceive.getStatus()>-1){
                where += " AND status =" + userReceive.getStatus() ;
            }
            if (!StringUtils.isTrimEmpty(content))
                where += " AND content LIKE '%" + content + "%'";
            where += " order by createTime desc, infoType";
            return infoUserReceiveBiz.find(pagination, where);
        }
        return new LinkedList<>();
    }

    /**
     * 发送消息.
     * 1. 首先保存消息记录<br>
     * 2. 如果是部分发送，保存infoId和receiverId<br>
     *
     * @param infoRecord 消息的属性。包括内容、消息类型等
     * @param ids        接收人id。向所有人发送除外
     * @return {@link ErrorCode#SUCCESS}发送成功
     */
    @SuppressWarnings("unchecked")
    public String tx_sendInfo(InfoRecord infoRecord, String ids) {
        String verify = verify(infoRecord, ids);
        if (verify != null) return verify;
        infoRecord.setSendTime(new Timestamp(new Date().getTime()));
        int infoType = infoRecord.getInfoType();
        if (isInfoSendBySys(infoType))
            infoRecord.setSenderId(0L);
        save(infoRecord);
        Long infoId = infoRecord.getId();
        if (!isInfoSendToAll(infoType)) {
            List<InfoUserRecord> records = new LinkedList<>();
            List<Long> list = parseReceiverId(ids);
            list.forEach(i -> {
                InfoUserRecord record = new InfoUserRecord();
                record.setInfoId(infoId);
                record.setReceiverId(i);
                records.add(record);
            });
            infoUserRecordBiz.saveBatch(records);
        }
        return ErrorCode.SUCCESS;
    }

    /**
     * 得到list集合中的用户id
     *
     * @param list 用户集合
     * @return 集合中用户的id
     */
    private List<Long> getReceiverIds(List<Map<String, String>> list) {
        if (ObjectUtils.isNotNull(list)) {
            return list.parallelStream()
                    .map(user -> Long.parseLong(user.get("id")))
                    .collect(Collectors.toList());
        }
        return new LinkedList<>();
    }

    /**
     * 解析接收人id为list集合
     *
     * @param ids 接收人id。以英文逗号","分割
     * @return 接受人id
     */
    private List<Long> parseReceiverId(String ids) {
        if (StringUtils.isTrimEmpty(ids)) {
            return new LinkedList<>();
        }
        return Stream.of(ids.replaceAll("\\s", "").split(","))
                .parallel()
                .map(Long::valueOf)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 验证{@link InfoRecord}的字段是否合法
     *
     * @param infoRecord  消息，消息内容、接收人等
     * @param receiverIds 接收人id。以英文逗号","分割
     * @return {@code null} 字段合法，否则不合法信息
     */

    private String verify(InfoRecord infoRecord, String receiverIds) {
        if (StringUtils.isTrimEmpty(infoRecord.getContent())) {
            return "消息正文不能为空";
        }
        // 向部分人发送时接收人的id不能为空
        if (!isInfoSendToAll(infoRecord.getInfoType()) && StringUtils.isTrimEmpty(receiverIds)) {
            return "接收人不能为空";
        }
        if (!isInfoSendBySys(infoRecord.getInfoType()) && ObjectUtils.isNull(infoRecord.getSenderId())) {
            return "发送人不能为空";
        }
        return null;
    }

    /**
     * 判断消息是否为发送所有人
     *
     * @param infoType 消息类型
     * @return {@code true} 发送所有人, {@code false} 发送部分人
     */
    public boolean isInfoSendToAll(Integer infoType) {
        return infoType == InfoRecord.InfoType.SYS_SEND_ALL
                || infoType == InfoRecord.InfoType.ADMIN_SEND_ALL;
    }

    /**
     * 判断消息是否为系统发送
     *
     * @param infoType 消息类型
     * @return {@code true} 系统发送, {@code false} 管理员发送
     */
    private boolean isInfoSendBySys(int infoType) {
        return infoType == InfoRecord.InfoType.SYS_SEND_SOME
                || infoType == InfoRecord.InfoType.SYS_SEND_ALL;
    }
}
