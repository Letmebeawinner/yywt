package com.oa.controller.message;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.google.gson.reflect.TypeToken;
import com.oa.biz.common.BaseHessianBiz;
import org.activiti.engine.impl.util.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消息管理
 *
 * @author ccl
 * @create 2017-02-17-14:36
 */
@Controller
@RequestMapping("/admin/oa")
public class MessageController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private BaseHessianBiz bassHessianBiz;

    private final static String messageList = "/message/message_list";

    private final static String sendMessageList = "/message/sendMessage_list";

    private final static String messageInfo = "/message/message_info";

    private final static String sendMessageInfo = "/message/sendMessage_info";

    /**
     * @Description:查询个人收件箱和回收站
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2017-02-17
     */
    @RequestMapping("/querySysUserInfoList")
    public String queryUserInfoList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @RequestParam(value = "status", required = true) int status) {
        try {
            List<Map<String, String>> messageList = new ArrayList<>();
            Map<String, Object> userMap = bassHessianBiz.querySysUserInfoList(pagination, SysUserUtils.getLoginSysUserId(request), status);
            messageList = (List<Map<String, String>>) userMap.get("infoList");

            Map<String, String> _pagination = (Map<String, String>) userMap.get("pagination");
            if (_pagination != null && _pagination.size() > 0) {
                Pagination page = gson.fromJson(gson.toJson(_pagination), new TypeToken<Pagination>() {
                }.getType());
                page.setRequest(request);
                request.setAttribute("pagination", page);
            }
            request.setAttribute("messageList", messageList);
            request.setAttribute("status", status);
        } catch (Exception e) {
            logger.error("MessageController--queryUserInfoList", e);
            return this.setErrorPath(request, e);
        }
        return messageList;
    }


    /**
     * @Description:发件箱
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2017-02-20
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryUserSendList")
    public String queryUserSendList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            Map<String, Object> userMap = bassHessianBiz.querySysUserSendList(pagination, SysUserUtils.getLoginSysUserId(request));

            //通过字符串，获得最外部的json对象
            JSONObject jsonObj = new JSONObject(userMap);
            //通过属性名，获得内部的对象
            JSONObject jsonMessage = jsonObj.getJSONObject("data");
            //获得json对象组
            if (jsonMessage != null && jsonMessage.get("infoRecordList") != null) {
                List<Map<String, String>> sendInfoList = gson.fromJson(jsonMessage.get("infoRecordList").toString(), new TypeToken<List<Map<String, String>>>() {
                }.getType());


                Map<String, String> _pagination = gson.fromJson(jsonMessage.get("pagination").toString(), new TypeToken<Map<String, String>>() {
                }.getType());
                if (_pagination != null && _pagination.size() > 0) {
                    Pagination page = gson.fromJson(gson.toJson(_pagination), new TypeToken<Pagination>() {
                    }.getType());
                    page.setRequest(request);
                    request.setAttribute("pagination", page);
                }
                request.setAttribute("sendInfoList", sendInfoList);
            }

        } catch (Exception e) {
            logger.error("MessageController--queryUserSendList", e);
            return this.setErrorPath(request, e);
        }
        return sendMessageList;
    }


    /**
     * 假删除，放到回收站中
     *
     * @author: ccl
     * @Param: [request, ids]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-02-20
     */
    @RequestMapping("/updateMessage")
    @ResponseBody
    public Map<String, Object> updateMessage(HttpServletRequest request, @RequestParam("ids") String ids) {
        Map<String, Object> resultMap = null;
        try {
            bassHessianBiz.deleteInfo(ids, false);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("MessageController--updateInfoList", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * 真删除
     *
     * @author: ccl
     * @Param: [request, ids]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-02-20
     */
    @RequestMapping("/delMessage")
    @ResponseBody
    public Map<String, Object> delMessage(HttpServletRequest request, @RequestParam("ids") String ids) {
        Map<String, Object> resultMap = null;
        try {
            bassHessianBiz.deleteInfo(ids, true);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("MessageController--delMessage", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:回收站的恢复
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-02-21
     */
    @RequestMapping("/recoverInfoInRecycleBin")
    @ResponseBody
    public Map<String, Object> recoverInfoInRecycleBin(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            bassHessianBiz.recoverInfoInRecycleBin(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("MessageController--recoverInfoInRecycleBin", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:查看发件详情
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2017-02-21
     */
    @RequestMapping("/findSendInfoDetail")
    public String findSendInfoDetail(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            Map<String, Object> userMap = bassHessianBiz.findSendInfoDetail(id);
            //通过字符串，获得最外部的json对象
            JSONObject jsonObj = new JSONObject(userMap);
            //通过属性名，获得内部的对象
            JSONObject map = jsonObj.getJSONObject("data");
            Map<String, String> sendInfo = gson.fromJson(map.toString(), new TypeToken<Map<String, String>>() {
            }.getType());
            request.setAttribute("sendInfo", sendInfo);
        } catch (Exception e) {
            logger.error("MessageController--findSendInfoDetail", e);
            return this.setErrorPath(request, e);
        }
        return sendMessageInfo;
    }

    /**
     * @Description:查看收件详情
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2017-02-21
     */
    @RequestMapping("/findInfoDetail")
    public String findInfoDetail(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            Map<String, String> info = bassHessianBiz.findInfoDetail(id);
            request.setAttribute("info", info);
        } catch (Exception e) {
            logger.error("MessageController--findInfoDetail", e);
            return this.setErrorPath(request, e);
        }
        return messageInfo;
    }


}
