package com.sms.controller.info;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.sms.biz.common.BaseHessianService;
import com.sms.biz.common.HrHessianBiz;
import com.sms.biz.info.InfoRecordBiz;
import com.sms.biz.info.InfoUserReceiveBiz;
import com.sms.biz.info.InfoUserRecordBiz;
import com.sms.entity.employee.Employee;
import com.sms.entity.info.InfoRecord;
import com.sms.entity.info.InfoUserReceive;
import com.sms.entity.info.InfoUserRecord;
import org.apache.commons.io.IOUtils;
import org.omg.CORBA.OBJ_ADAPTER;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 消息相关操作的控制器。包括查询消息发送记录、删除消息发送记录等
 *
 * @author sk
 * @since 2017-01-20
 */
@Controller
@RequestMapping("/admin/sms/info")
public class InfoController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private HrHessianBiz hrHessianBiz;
    @Autowired
    private InfoRecordBiz infoRecordBiz;
    @Autowired
    private InfoUserReceiveBiz infoUserReceiveBiz;
    @Autowired
    private InfoUserRecordBiz infoUserRecordBiz;

    @InitBinder("infoRecord")
    public void infoRecordBind(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("infoRecord.");
    }

    @InitBinder("userReceive")
    public void infoUserReceiveBind(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("userReceive.");
    }

    /**
     * 删除消息接收记录
     * @param id 删除的消息记录id
     * @return {@link Map}中的code为{@code true}时，删除成功
     * @since 2017-02-08
     */
    @RequestMapping("/deleteInfoReceiveRecord")
    @ResponseBody
    public Map<String, Object> deleteInfoReceiveRecord(
            @RequestParam(value = "id", required = false) Long id) {
        try {
            if (ObjectUtils.isNotNull(id)) {
                infoUserReceiveBiz.deleteById(id);
            }
            return resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("deleteInfoReceiveRecord()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 修改读读的状态
     * @param id
     * @return
     */

    @RequestMapping("/updateInfoReceiveRecord")
    @ResponseBody
    public Map<String,Object> updateInfoReceiveRecord(@RequestParam(value = "id", required = false) Long id){
        try {
            if(ObjectUtils.isNotNull(id)){
                InfoUserReceive infoUserReceive=infoUserReceiveBiz.findById(id);
                infoUserReceive.setStatus(1);
                infoUserReceiveBiz.update(infoUserReceive);

//                InfoRecord infoRecord=new InfoRecord();
//                infoRecord.setId(infoUserReceive.getInfoId());
//                infoRecord.setStatus(1);
//                infoRecordBiz.update(infoRecord);
            }
            return resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
    }


    /**
     * 删除消息发送记录(infoRecord)
     *
     * @param recordId 删除的消息发送记录id
     * @return {@link Map}中的code为{@code true}时，删除成功
     */
    @RequestMapping("/deleteInfoSendRecord")
    @ResponseBody
    public Map<String, Object> deleteInfoSendRecord(@RequestParam("recordId") Long recordId) {
        try {
            infoRecordBiz.tx_deleteInfo(recordId);
            return resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("deleteInfoSendRecord()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 查询消息接收记录详情
     *
     * @param req {@link HttpServletRequest}
     * @param id  消息接收记录id
     * @return 消息接收记录详情路径
     */
    @RequestMapping("/getInfoReceiveRecord")
    public ModelAndView getInfoReceiveRecord(HttpServletRequest req,
                                             @RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("info/infoReceiveRecord");
        try {
            InfoUserReceive info = infoUserReceiveBiz.findById(id);
            String sender = getSysUsers(Collections.singletonList(info.getSenderId())).getOrDefault("names", "");
            Calendar c = Calendar.getInstance();
            //七天前
            c.setTime(new Date());
            c.add(Calendar.DATE, - 7);
            Date sevenDaysAgo = c.getTime();
            mv.addObject("info", info);
            mv.addObject("sevenDaysAgo", sevenDaysAgo);
            mv.addObject("sender", sender);
        } catch (Exception e) {
            logger.error("getInfoReceiveRecord()--error", e);
            mv.setViewName(setErrorPath(req, e));
        }
        return mv;
    }

    /**
     * 查询消息发送记录详情
     *
     * @param request  {@link HttpServletRequest}
     * @param recordId 消息发送记录id
     * @return {@link ModelAndView} 消息发送记录详情路径
     */
    @RequestMapping("/getInfoSendRecordById")
    public ModelAndView getInfoSendRecord(HttpServletRequest request,
                                          @RequestParam("recordId") Long recordId) {
        ModelAndView mv = new ModelAndView("/info/infoSendRecord");
        try {
            InfoRecord infoRecord = infoRecordBiz.findById(recordId);
//            infoRecord.setStatus(1);
//            infoRecordBiz.update(infoRecord);
            Map<String, String> sysUser = baseHessianService.querySysUserById(infoRecord.getSenderId());
            List<InfoUserRecord> infoUserRecords = infoUserRecordBiz.find(null, " infoId=" + recordId);
            String receivers = "";
            if (!infoRecordBiz.isInfoSendToAll(infoRecord.getInfoType())) {
                List<Long> receiverIds = infoUserRecords.stream()
                        .map(InfoUserRecord::getReceiverId).collect(Collectors.toList());
//                receivers = getSysUsers(receiverIds).getOrDefault("names", "");
                if (ObjectUtils.isNotNull(receiverIds)) {
                    List<Map<String, String>> sysUsers = baseHessianService.querySysUserByIds(receiverIds);
                    if (ObjectUtils.isNotNull(sysUsers)) {
                        for (Map<String, String> u:sysUsers){
                            List<InfoUserReceive> iList=infoUserReceiveBiz.find(null,"infoId="+recordId+" and receiverId="+u.get("id"));
                            String status="<font color=\"red\">(未读)</font>";
                            if(iList!=null && iList.size()>0 && iList.get(0).getStatus()==1){
                                status="<font color=\"green\">(已读)</font>";
                            }
                            u.put("userName",u.get("userName")+status);
                        }
                        Map<String, String> userNames = new LinkedHashMap<>();
                        sysUsers.stream()
                                .forEach(u -> userNames.put(u.get("id"), u.get("userName")));
                        receivers=userNames.values().toString().replaceAll("]|\\[", "");
                    }
                }
            }

            logger.info("这里是全部收件人："+receivers);

            mv.addObject("infoRecord", infoRecord);
            mv.addObject("receivers", receivers);
            mv.addObject("sysUser", sysUser);

            Calendar c = Calendar.getInstance();
            //七天前
            c.setTime(new Date());
            c.add(Calendar.DATE, - 7);
            Date sevenDaysAgo = c.getTime();
            mv.addObject("sevenDaysAgo", sevenDaysAgo);
        } catch (Exception e) {
            logger.error("getInfoRecord()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }


    /**
     * 查询指定用户接收的消息列表
     *
     * @param request    {@link HttpServletRequest}
     * @param pagination 分页
     * @return 用户的消息列表
     * @since 2017-02-08
     */
    @RequestMapping("/queryInfoList")
    public ModelAndView queryInfoReceiveRecord(HttpServletRequest request,
                                               @ModelAttribute("pagination") Pagination pagination,
                                               @ModelAttribute("userReceive") InfoUserReceive userReceive) {
        ModelAndView mv = new ModelAndView("/info/infoReceiveRecord-list");
        try {
            Long receiverId = SysUserUtils.getLoginSysUserId(request);
            pagination.setRequest(request);
            List<InfoUserReceive> infoList = infoRecordBiz.tx_queryInfoReceiveList(receiverId, pagination, userReceive);
            List<Long> senderIds = infoList.stream()
                    .distinct().map(InfoUserReceive::getSenderId)
                    .collect(Collectors.toList());
            Map<String, String> senders = getSysUsers(senderIds);
            mv.addObject("infoList", infoList);
            mv.addObject("senders", senders);
        } catch (Exception e) {
            logger.error("queryInfoReceiveRecord()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 查询信息发送记录
     *
     * @param request    {@link HttpServletRequest request}
     * @param infoRecord 消息记录查询条件
     * @param pagination 分页
     * @return {@link ModelAndView}消息记录列表页
     */
    @RequestMapping("/queryInfoSendRecord")
    public ModelAndView queryInfoSendRecord(HttpServletRequest request,
                                            @ModelAttribute("infoRecord") InfoRecord infoRecord,
                                            @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("info/infoSendRecord-list");
        try {
            pagination.setPageSize(10);
            pagination.setRequest(request);
            String where = where(infoRecord);
            where += " and infoType!=4";
            where += " order by createTime desc";
            List<InfoRecord> infoRecordList = infoRecordBiz.find(pagination, where);
            List<Long> sysUserIds = infoRecordList.stream()
                    .map(InfoRecord::getSenderId).collect(Collectors.toList());
            Map<String, String> senders = getSysUsers(sysUserIds);
            mv.addObject("infoRecordList", infoRecordList);
            mv.addObject("senders", senders);
            mv.addObject("from", 1);
        } catch (Exception e) {
            logger.error("queryInfoSendRecord()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 查询我的信息发送记录
     *
     * @param request    {@link HttpServletRequest request}
     * @param infoRecord 消息记录查询条件
     * @param pagination 分页
     * @return {@link ModelAndView}消息记录列表页
     */
    @RequestMapping("/queryMyInfoSendRecord")
    public ModelAndView queryMyInfoSendRecord(HttpServletRequest request,
                                            @ModelAttribute("infoRecord") InfoRecord infoRecord,
                                            @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("info/infoSendRecord-list");
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            pagination.setPageSize(10);
            pagination.setRequest(request);
            String where = where(infoRecord);
            where += " and infoType=4";
            where += " and senderId=" + userId;
            where += " order by createTime desc";
            List<InfoRecord> infoRecordList = infoRecordBiz.find(pagination, where);
            List<Long> sysUserIds = infoRecordList.stream()
                    .map(InfoRecord::getSenderId).collect(Collectors.toList());
            Map<String, String> senders = getSysUsers(sysUserIds);
            mv.addObject("infoRecordList", infoRecordList);
            mv.addObject("senders", senders);
            mv.addObject("from", 2);
        } catch (Exception e) {
            logger.error("queryInfoSendRecord()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 查询接收人(用户)
     *
     * @param request    {@link HttpServletRequest}
     * @param pagination {@link Pagination} 分页
     * @param from       来源。1.发送邮件 2.发送短信 3.发送消息
     * @return {@link ModelAndView}收件人(用户列表)页
     */
    @RequestMapping("/queryReceivers")
    @SuppressWarnings("unchecked")
    public ModelAndView queryReceivers(HttpServletRequest request,
                                       @ModelAttribute("pagination") Pagination pagination,
                                       @RequestParam(value = "departmentId", required = false) Long departmentId,
                                       @RequestParam(value = "userType", required = false) Integer userType,
                                       @RequestParam(value = "selectType", required = false) Integer selectType) {
        ModelAndView mv = new ModelAndView("/user/userList");
        try {
            pagination.setPageSize(10);

            String where = "  status = 0 and userType = 2";
            String userName = request.getParameter("userName");
            if (!StringUtils.isTrimEmpty(userName)) {
                where += " and userName like '%" + userName + "%'";
            }
            if (userType != null) {
                if (userType == 1) {//查询部门负责人
                    String departmentIds = baseHessianService.queryParentDepartment();
                    where += " and departmentId in (" + departmentIds + ")";
                    departmentId = 0L;
                } else if (userType == 2) {//查询中层干部
                    List<Employee> employeeList = hrHessianBiz.getEmployeeList("employeeType=4");
                    AtomicReference<String> employeeIds = new AtomicReference<>("");
                    employeeList.forEach(e -> {
                        employeeIds.updateAndGet(v -> v + e.getId() + ",");
                    });
                    where += " and linkId in (" + employeeIds.get().substring(0, employeeIds.get().length() - 1) + ")";
                    departmentId = 0L;
                } else if (userType == 3) {//查询中心组成员
                    List<Employee> employeeList = hrHessianBiz.getEmployeeList("employeeType=4");
                    AtomicReference<String> employeeIds = new AtomicReference<>("");
                    employeeList.forEach(e -> {
                        employeeIds.updateAndGet(v -> v + e.getId() + ",");
                    });
                    String departmentIds = baseHessianService.queryParentDepartment();
                    where += " and ( linkId in (" + employeeIds.get().substring(0, employeeIds.get().length() - 1) + ") or departmentId in (" +departmentIds+ ",22) )";
                    departmentId = 0L;
                } else {//按部门查询
                    if (departmentId != null) {
                        where += " and departmentId =" + departmentId;
                    }
                }
            } else {//按部门查询
                if (departmentId != null) {
                    where += " and departmentId =" + departmentId;
                }
            }
            where += " order by sort";

            Map<String, Object> map = baseHessianService.querySysUserList(pagination, where);

            List<Map<String, String>> list = baseHessianService.queryAllDepartment();
            List<Map<String, String>> userList = (List<Map<String, String>>) map.get("userList");
            Map<String, String> _pagination = (Map<String, String>) map.get("pagination");

            pagination.setBegin(Integer.parseInt(_pagination.get("begin")));
            pagination.setTotalCount(Integer.parseInt(_pagination.get("totalCount")));
            pagination.setEnd(Integer.parseInt(_pagination.get("end")));
            pagination.setCurrentPage(Integer.parseInt(_pagination.get("currentPage")));
            pagination.setCurrentUrl(_pagination.get("currentUrl"));
//            pagination.setRequest(request);
            pagination.setPageSize(Integer.parseInt(_pagination.get("pageSize")));
            pagination.setTotalPages(Integer.parseInt(_pagination.get("totalPages")));
            request.setAttribute("userList", userList);
            request.setAttribute("departmentId", departmentId);
            request.setAttribute("list", list);
            request.setAttribute("currentPath", request.getRequestURI());
            request.setAttribute("userName", userName);
            request.setAttribute("userType", userType);
            request.setAttribute("selectType", selectType);
        } catch (Exception e) {
            logger.error("queryReceivers()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 发送消息
     *
     * @param infoRecord  消息的相关属性。包括内容、消息类型、接收人等
     * @param receiverIds 接收消息用户的id。使用英文逗号","分割
     * @return {@link Map}。code为{@link ErrorCode#SUCCESS}发送成功，否则失败的原因
     */
    @ResponseBody
    @RequestMapping("/sendInfo")
    public Map<String, Object> sendInfo(HttpServletRequest request,
                                        @ModelAttribute("infoRecord") InfoRecord infoRecord,
                                        @RequestParam(value = "receiverIds", required = false) String receiverIds) {
        try {
            Long senderId = SysUserUtils.getLoginSysUserId(request);
            if (ObjectUtils.isNull(senderId)) {
                return resultJson(ErrorCode.ERROR_SYSTEM, "请登录后再操作", null);
            }
            infoRecord.setSenderId(senderId);
            String result = infoRecordBiz.tx_sendInfo(infoRecord, receiverIds);
            if (ErrorCode.SUCCESS.equals(result)) {
                return resultJson(ErrorCode.SUCCESS, null, "/admin/sms/info/queryInfoSendRecord.json");
            }
            return resultJson(ErrorCode.ERROR_PARAMETER, result, null);
        } catch (Exception e) {
            logger.error("tx_sendInfo()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 跳转到发送消息页面
     *
     * @return 发送消息页面
     */
    @RequestMapping("/toSendInfo")
    public String toSendInfo() {
        return "info/infoSend";
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
                sysUsers.stream()
                        .forEach(u -> userNames.put(u.get("id"), u.get("userName")));
                userNames.put("names", userNames.values().toString().replaceAll("]|\\[", ""));
                return userNames;
            }
        }
        return new HashMap<>(0);
    }

    /**
     * 拼查询{@link InfoRecord}的条件
     *
     * @param record {@link InfoRecord}的查询条件
     */

    private String where(InfoRecord record) {
        String where = " 1=1";
        String regexp = "\'";
        if (!StringUtils.isTrimEmpty(record.getContent())) {
            String records=record.getContent().replaceAll(regexp, "");
            where += " and content like '%" + records + "%'";
        }
        if (record.getInfoType() != null && record.getInfoType() > -1) {
            where += " and infoType=" + record.getInfoType();
        }
        return where;
    }


    /**
     * 跳转到用户发送消息页面
     *
     * @return 发送消息页面
     */
    @RequestMapping("/toSendInfoAndFile")
    public String toSendInfoAndFile() {
        return "info/infoSendAndFile";
    }


    /**
     * 下载文件，自定义名字
     * @param response
     * @param request
     * @param fileUrl
     * @param fileName
     */
    @RequestMapping("/file/load")
    public void downloadNet(HttpServletResponse response,
                            HttpServletRequest request,
                            @RequestParam("fileUrl") String fileUrl,
                            @RequestParam("fileName") String fileName) {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //处理中文乱码
            request.setCharacterEncoding("UTF-8");
            //处理浏览器兼容
            response.setContentType("application/msexcel;charset=utf-8");//定义输出类型
            Enumeration enumeration = request.getHeaders("User-Agent");
            String browserName = (String) enumeration.nextElement();
            boolean isMSIE = browserName.contains("MSIE");
            if (isMSIE) {
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            } else {
                response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            }
            //url地址如果存在空格，会导致报错！  解决方法为：用+或者%20代替url参数中的空格。
            fileUrl = fileUrl.replace(" ", "%20");
            //图片下载
            URL url = new URL(fileUrl);
            URLConnection conn = url.openConnection();
            outputStream = response.getOutputStream();
            inputStream = conn.getInputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            System.err.println(e);
        }finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }

    }
}
