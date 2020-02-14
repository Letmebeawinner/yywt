package com.sms.controller.message;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.sms.biz.common.BaseHessianService;
import com.sms.biz.message.SmsSendRecordBiz;
import com.sms.entity.message.SmsSendRecord;
import com.sms.utils.message.SendMessageUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 短信控制类
 * 林明亮
 * Created by Administrator on 2016/12/24.
 */
@Controller
@RequestMapping("/admin/sms/message")
public class MessageController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private SmsSendRecordBiz smssendrecordBiz;
    @Autowired
    private BaseHessianService baseHessianService;


    @InitBinder({"smssendrecord"})
    public void initSmssendrecord(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("smssendrecord.");
    }

    @InitBinder({"pagination"})
    public void initPagination(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("pagination.");
    }


    /**
     * 短信列表
     *
     * @param request {@link HttpServletRequest}
     * @return 短信列表
     */
    @RequestMapping("/messageList")
    public String messageList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = " 1=1";
            String queryMessage = request.getParameter("queryMessage");
            request.setAttribute("queryMessage", queryMessage);
            if (!StringUtils.isTrimEmpty(queryMessage)) {
                whereSql += " and mobiles like'%" + queryMessage + "%'";
            }
            String type = request.getParameter("type");
            request.setAttribute("type", type);
            if (!StringUtils.isTrimEmpty(type) && Integer.parseInt(type) != 0) {
                whereSql += " and sendType='" + type + "'";
            }
            whereSql+=" order by createTime desc";
            pagination.setRequest(request);
            pagination.setPageSize(10);
            List<SmsSendRecord> messageList = smssendrecordBiz.find(pagination, whereSql);

            List<Long> sysUserIds = new ArrayList<>();
            for (SmsSendRecord smssendrecord : messageList) {
                sysUserIds.add(smssendrecord.getUserId());
            }
            //查询发送人姓名
            Map<String, String> userNames = getUserName(sysUserIds);
            if (userNames != null) {
                request.setAttribute("userNames", userNames);
            }
            request.setAttribute("messageList", messageList);
        } catch (Exception e) {
            logger.error("messageList()--error", e);
        }
        return "/message/message-list";
    }

    /**
     * 初始化发送短信页面
     *
     * @return 发送短信页面路径
     */
    @RequestMapping("/addMessage")
    public String addMessage() {
        return "/message/addMessage";
    }

    /**
     * 发送短信
     *
     * @param request        {@link HttpServletRequest}
     * @param mobiles        手机号
     * @param context        短信内容
     * @param receiveUserIds 接收短信id,使用英文","分割
     * @return 发送短信结果
     */
    @RequestMapping("/addMsg")
    @ResponseBody
    public Map<String, Object> addMsg(HttpServletRequest request,
                                      @RequestParam("mobiles") String mobiles,
                                      @RequestParam("context") String context,
                                      @RequestParam("receiveUserIds") String receiveUserIds) {
        try {
            Map<String, Object> mobileMap = testMobiles(mobiles);//验证手机号
            if (!"0".equals(mobileMap.get("code"))) {
                return mobileMap;
            }
            Map<String, Object> contextMap = testContext(context);//验证短信内容
            if (!"0".equals(contextMap.get("code"))) {
                return contextMap;
            }
            mobiles = mobiles.replace(",", " ").trim().replace(" ", ",");
            String result = SendMessageUtils.sendMsg(mobiles, context);
            if (result == null) {
                //添加发送记录
                Long id = SysUserUtils.getLoginSysUserId(request);
                SmsSendRecord smssendrecord = new SmsSendRecord();
                smssendrecord.setUserId(id);
                smssendrecord.setContext(context);
                smssendrecord.setMobiles(mobiles);
                smssendrecord.setSendType(2);
                smssendrecordBiz.tx_saveSmssendrecord(smssendrecord, receiveUserIds);
                return this.resultJson(ErrorCode.SUCCESS, "发送成功", null);
            } else {
                return this.resultJson(ErrorCode.ERROR_SYSTEM, "发送失败，失败原因：" + result , null);
            }
        } catch (Exception e) {
            logger.error("addMsg()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 删除短信记录
     *
     * @param id 删除短信记录的id
     * @return 删除结果
     */
    @RequestMapping("/deleteMsgRecode")
    @ResponseBody
    public Map<String, Object> deleteMsgRecode(@RequestParam("id") Long id) {
        try {
            smssendrecordBiz.deleteById(id);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("deleteMsgRecode()-error");
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 查看短信详情
     *
     * @param request {@link HttpServletRequest}
     * @param id      短信记录id
     * @return 短信详情
     */
    @RequestMapping("/getMsgRecodeInfo")
    public String getMsgRecodeInfo(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            SmsSendRecord smssendrecord = smssendrecordBiz.findById(id);
            //获取发送短信用户的用户名
            List<Long> ids = new ArrayList<>();
            ids.add(smssendrecord.getUserId());
            Map<String, String> map = this.getUserName(ids);
            request.setAttribute("smssendrecord", smssendrecord);
            request.setAttribute("map", map);
        } catch (Exception e) {
            logger.error("getMsgRecodeInfo()--error");
            return setErrorPath(request, e);
        }
        return "/message/msgRecodeInfo";
    }

    /**
     * 查询接收人
     *
     * @return 接收人列表路径
     */
    @RequestMapping("/getUser")
    @SuppressWarnings("unchecked")
    public String getUser(HttpServletRequest request,
                          @ModelAttribute("pagination") Pagination pagination) {
        try {
            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            if (sysUserId == null) sysUserId = 0L;
            String whereSql = " mobile IS NOT NULL AND mobile <> '' AND status = 0";
            pagination.setPageSize(10);
            Map<String, Object> userMap = baseHessianService.querySysUserList(pagination, whereSql);
            List<Map<String, String>> userList = (List<Map<String, String>>) userMap.get("userList");
            Map<String, String> paginationMap = (Map<String, String>) userMap.get("pagination");
            request.setAttribute("userList", userList);
            request.setAttribute("_pagination", paginationMap);
            request.setAttribute("currentPath", request.getRequestURI());
            return "/user/userList";
        } catch (Exception e) {
            logger.error("getUser()--error", e);
            return setErrorPath(request, e);
        }
    }

    /**
     * 获取短信发送者的名字
     *
     * @param userIds 发送者的id集合
     * @return map 键为用户的id 值为用户的名字
     */
    private Map<String, String> getUserName(List<Long> userIds) {
        List<Map<String, String>> userList = baseHessianService.querySysUserByIds(userIds);
        if (userList != null && userList.size() > 0) {
            Map<String, String> userNames = new HashMap<>();
            for (Map<String, String> map : userList) {
                userNames.put(map.get("id"), map.get("userName"));
            }
            return userNames;
        }
        return new HashMap<>();
    }

    /**
     * 验证手机号
     *
     * @param mobiles 手机号串
     * @return 验证手机号结果
     */
    private Map<String, Object> testMobiles(String mobiles) {
        if (StringUtils.isTrimEmpty(mobiles)) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "联系人不能为空", null);
        }
        mobiles = mobiles.replaceAll("\\s", "");// 去除回车
        String[] mobile = mobiles.split(",");
        List<String> list = Stream.of(mobile).distinct().collect(Collectors.toList());
        if (list.size() > 200) {//群发短信手机个数
            return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "联系人不能超过200个", null);
        }
        for (String tel : list) {
            if (!StringUtils.isMobile(tel)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER, "联系人【" + tel + "】格式不正确", null);
            }
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, list);
    }

    /**
     * 验证短信内容
     *
     * @param context 短信内容
     * @return 验证短信内容结果
     */
    private Map<String, Object> testContext(String context) {
        if (StringUtils.isTrimEmpty(context)) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "短信内容不能为空", null);
        }
        if (context.length() > 70) {//短信内容长度
            return this.resultJson(ErrorCode.ERROR_PARAMETER, "短信内容过长，请按要求输入", null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, context);
    }
}
