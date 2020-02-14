package com.renshi.biz.common;

import com.a_268.base.core.Pagination;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 发送短信和邮件的对外接口调用.<br>
 * ps:配置客户端时添加<br>
 * &lt;property name="overloadEnabled" value="true" /&gt;，<br>
 * hessian远程调用启用方法重载<br>
 * ps:发送邮件时handshake_failure<br>
 *
 * @since 2016-12-27
 */
public interface SmsHessianService {

    // 系统发送。如找回密码、注册等由用户触发的发送
    int SEND_BY_SYSTEM = 1;
    // 管理员发送。
    int SEND_BY_ADMIN = 2;
    //系统发送部分人
    int SEND_BY_SYS = 0;
    //管理员发送所有人
    int SEND_ALL = 1;
    //管理员发送部分人
    int SEND_SOME_PEOPLE = 2;
    //系统发送所有人
    int SEND_BY_SYS_TO_ALL = 3;

    /**
     * 删除指定的信息用户接收
     *
     * @param infoUserReceiveId 信息用户接收表id
     * @param receiverId        接收人id
     */
    void deleteInfo(Long infoUserReceiveId, Long receiverId);

    /**
     * 删除指定的信息用户接收
     *
     * @param infoUserReceiveIds 信息用户接收表id，使用英文逗号","分割
     * @param receiverId         接收人id
     */
    void deleteInfo(String infoUserReceiveIds, Long receiverId);

    /**
     * 查询指定收件人的消息列表
     *
     * @param pagination 分页
     * @param receiverId 收件人id
     * @return {@link Map} 消息概览。包括消息列表和分页
     */
    Map<String, Object> findInfoList(Pagination pagination, Long receiverId);

    /**
     * 查询用户的未读消息条数(未实现)
     *
     * @param receiverId 接收人id
     * @return {@link Map}.key:count
     * @since 2017-02-08
     */
    Map<String, Integer> getUnreadInfoCount(Long receiverId);

    /**
     * 查询指定接收人的指定消息详情
     *
     * @param infoUserReceiveId 信息用户接收表的id
     * @return 消息详情
     */
    Map<String, String> findInfoDetail(Long infoUserReceiveId);

    /**
     * 发送邮件
     *
     * @param subject   邮件的主题
     * @param content   邮件的内容
     * @param receivers 收件人的邮箱地址，使用英文状态","分割
     * @param sendType  发送类型 {@link #SEND_BY_SYSTEM} or {@link #SEND_BY_ADMIN}
     * @return 发送结果
     */
    String sendEmail(String subject, String content, String receivers, int sendType);

    /**
     * 发送邮件
     *
     * @param subject   邮件的主题
     * @param content   邮件的内容
     * @param receivers 收件人的收件人的邮箱地址，使用英文状态","分割
     * @param sendType  发送类型 {@link #SEND_BY_SYSTEM} or {@link #SEND_BY_ADMIN}
     * @param is        附件的输入流
     * @return 发送结果
     * @see #sendEmail(String, String, String, int)
     */
    String sendEmail(String subject, String content, String receivers, int sendType, InputStream is);

    /**
     * 发送邮件
     *
     * @param subject  邮件的主题
     * @param content  邮件的内容
     * @param sendType 发送类型 {@link #SEND_BY_SYSTEM} or {@link #SEND_BY_ADMIN}
     * @param userIds  收件人(用户)的ID
     * @return 发送结果
     */
    String sendEmail(String subject, String content, int sendType, List<Long> userIds);

    /**
     * 发送邮件
     *
     * @param subject  邮件的主题
     * @param content  邮件的内容
     * @param sendType 发送类型 {@link #SEND_BY_SYSTEM} or {@link #SEND_BY_ADMIN}
     * @param userIds  收件人(用户)的ID
     * @param is       附件的输入流
     * @return 发送结果
     * @see #sendEmail(String, String, int, List)
     */
    String sendEmail(String subject, String content, int sendType, List<Long> userIds, InputStream is);

    /**
     * 发送消息
     *
     * @param content     消息内容
     * @param senderId    发送人id
     * @param receiverIds 接收人id。使用英文逗号","分割。<br>
     *                    infoType为{@link #SEND_ALL}时receiverIds为{@code null}
     * @param infoType    消息类型
     * @return 发送消息结果
     */
    String sendInfo(String content, Long senderId, String receiverIds, int infoType);


    /**
     * 发送短信
     *
     * @param map <table>
     *            <thead>
     *            <tr>
     *            <td>键</td>
     *            <td>类型</td>
     *            <td>默认值</td>
     *            <td>说明</td>
     *            </tr>
     *            </thead>
     *            <tbody>
     *            <tr>
     *            <td>type</td>
     *            <td>Integer</td>
     *            <td>5</td>
     *            <td>短信类型. 1.注册 2.密码找回 3.绑定手机 4.改绑手机 5.自定义</td>
     *            </tr>
     *            <tr>
     *            <td>code</td>
     *            <td>String</td>
     *            <td>无</td>
     *            <td>验证码</td>
     *            </tr>
     *            <tr>
     *            <td>time</td>
     *            <td>Integer</td>
     *            <td>3</td>
     *            <td>验证码有效时间.单位 min</td>
     *            </tr>
     *            <tr>
     *            <td>context</td>
     *            <td>String</td>
     *            <td>无</td>
     *            <td>自定义类型的内容</td>
     *            </tr>
     *            <tr>
     *            <td>sendType</td>
     *            <td>Integer</td>
     *            <td>1</td>
     *            <td>1.系统发送 2.管理员发送 3.用户对发</td>
     *            </tr>
     *            <tr>
     *            <td>senderId</td>
     *            <td>Long</td>
     *            <td>0</td>
     *            <td>发送人id. 系统发送不传或传0</td>
     *            </tr>
     *            <tr>
     *            <td>receiverIds</td>
     *            <td>String</td>
     *            <td>无</td>
     *            <td>接收人id, 使用英文","分割</td>
     *            </tr>
     *            <tr>
     *            <td>mobiles</td>
     *            <td>String</td>
     *            <td>无</td>
     *            <td>接收人手机号,使用英文","分割</td>
     *            </tr>
     *            </tbody>
     *            </table>
     * @return {@code true} 发送成功
     * @throws Exception Exception
     */
    boolean sendMsg(Map<String, String> map) throws Exception;
}
