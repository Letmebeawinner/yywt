package com.houqin.controller.card;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.houqin.biz.common.AttendanceHessianService;
import com.houqin.biz.common.JiaoWuHessianService;
import com.houqin.biz.common.LockHessianService;
import com.houqin.common.CommonConstants;
import com.houqin.entity.User.User;
import com.houqin.entity.User.UserInfo;
import com.houqin.utils.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/houqin")
public class CardController extends BaseController {

    private static final Logger logger = Logger.getLogger(CardController.class);

    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;

    @Autowired
    private LockHessianService lockHessianService;

    @Autowired
    private AttendanceHessianService attendanceHessianService;

    private final static String Available = CommonConstants.LockPath + "/queryAvailableList.json";
    private final static String BED_ROOM_LIST = CommonConstants.LockPath + "/queryBedRoomByName.json";//通过房间名称查询卡号
    private final static String UPDATE_BED_ROOM_LIST = CommonConstants.LockPath + "/updateUserInfo.json";


    /**
     * 开卡
     *
     * @param request
     * @return
     */
    @RequestMapping("/openCard")
    public ModelAndView openCard(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/card/card_list");
        try {
            // 班级类型列表
            Map<String, Object> classTypeMap = jiaoWuHessianService.listClassType();
            mv.addObject("classTypeMap", classTypeMap);

            Map<String, Object> jsonClass = jiaoWuHessianService.queryClassesList(null, "");
            request.setAttribute("classesList", jsonClass.get("classesList"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * 开卡
     *
     * @param request
     * @return
     */
    @RequestMapping("/openCardById/{id}")
    public ModelAndView openCardById(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("/card/card_open_list");
        try {
            String whereSql = "classId=" + id;
            Map<String, Object> map = jiaoWuHessianService.userList(pagination, whereSql);
            if (ObjectUtils.isNotNull(map)) {
                //分页
                Object obj = map.get("pagination");
                pagination = gson.fromJson(gson.toJson(obj), Pagination.class);
                pagination.setRequest(request);
                pagination.setPageSize(15);
                //培训人员列表
                List<Map<String, String>> userList = (List<Map<String, String>>) map.get("userList");
                //班级类型
                Map<String, Object> classTypeMap = jiaoWuHessianService.listClassType();
                mv.addObject("pagination", pagination);
                mv.addObject("userList", userList);
                mv.addObject("classTypeMap", classTypeMap);
            }
            mv.addObject("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * 查询未
     *
     * @param request
     * @return
     */
    @RequestMapping("/queryAvailableCard")
    public ModelAndView queryAvailableCard(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/card/avaiable_card_list");
        try {
            Map<String, Object> resultMap = HttpUtil.doGet(Available, "");
            request.setAttribute("roomList", resultMap.get("data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     * 客房对接
     *
     * @param request
     * @return
     */
    @RequestMapping("/searchCardInfo")
    public ModelAndView searchCardInfo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/card/card_room_list");
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 一键分房
     *
     * @param request
     * @return
     */
    @RequestMapping("/fenFang")
    @ResponseBody
    public Map<String, Object> fenFang(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {

            String errorInfo = "";

            String classesId = request.getParameter("classesId");
            String whereSql = "classId=" + classesId;
            Map<String, Object> map = jiaoWuHessianService.userList(null, whereSql);
            Map<String, Object> resultMap = lockHessianService.queryAvaliavableList();

            if (ObjectUtils.isNotNull(map) && ObjectUtils.isNotNull(resultMap)) {
                //班级类型
                Map<String, String> classesMap = jiaoWuHessianService.getClassesById(Long.parseLong(classesId));
                //培训人员列表
                String userlist = gson.toJson(map.get("userList"));
                List<User> userList = gson.fromJson(userlist, new TypeToken<List<User>>() {
                }.getType());

                //剩余房间
                String userInfolist = gson.toJson(resultMap.get("userInfoList"));
                List<UserInfo> userInfoList = gson.fromJson(userInfolist, new TypeToken<List<UserInfo>>() {
                }.getType());

                //如果培训人员和剩余房间不为空
                if (ObjectUtils.isNotNull(userList) && ObjectUtils.isNotNull(userInfoList)) {

                    for (int i = 0; i < userList.size(); i++) {
                        for (int j = 0; j < userInfoList.size(); j++) {
                            if (i == j) {
                                Map<String, Object> userInfoMap = new HashMap<String, Object>();
                                //用户id
                                userInfoMap.put("userId", userInfoList.get(j).getUserId());
                                //用户名称
                                userInfoMap.put("userName", userList.get(i).getName());
                                String zimu = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                                if (ObjectUtils.isNotNull(userInfoList.get(j).getCridentialId())) {
                                    String str = userInfoList.get(j).getCridentialId();
                                    if (ObjectUtils.isNotNull(str)) {
                                        //不知道之前的逻辑是什么   发现截取后房间号会少一位  造成找不到的问题
                                        if(zimu.contains(str.substring(str.length()-1,str.length()))){
                                            str = str.substring(0, str.length() - 1);
                                        }
                                        StringBuffer stringBuffer = new StringBuffer(str);
                                        stringBuffer.insert(1, "-");
                                        logger.error("stringBuffer--------" + stringBuffer);
                                        Map<String, Object> roomMap = HttpUtil.doGet(BED_ROOM_LIST, "whereSql='" + stringBuffer + "'");
                                        if (ObjectUtils.isNotNull(roomMap)) {
                                            Object bedChamberId = roomMap.getOrDefault("data", 0);
                                            if (ObjectUtils.isNotNull(bedChamberId)) {
                                                userInfoMap.put("BedchamberId", bedChamberId.toString().substring(0, bedChamberId.toString().length() - 2));
                                            } else {
                                                userInfoMap.put("BedchamberId", 0);
                                            }

                                        }
                                    }
                                }
                                userInfoMap.put("identityNo", userList.get(i).getIdNumber());
                                if (ObjectUtils.isNotNull(classesMap)) {
                                    //住房过期时间
                                    userInfoMap.put("ExpiredTime", classesMap.get("endTime"));
                                } else {
                                    userInfoMap.put("ExpiredTime", new Date());
                                }
                                logger.error("userInfoMap--------2----" + userInfoMap);
                                errorInfo = lockHessianService.updateBaseUserInfo(userInfoMap);
                                if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {

                                } else {
                                    if (!StringUtils.isTrimEmpty(userInfoList.get(j).getCardId())) {
                                        Long userId = userList.get(i).getId();
                                        //住宿卡号
                                        String cardId = userInfoList.get(j).getCardId();
                                        //考勤卡号
                                        String timeCardId = conversionCardId(cardId);
                                        //房间号
                                        String roomInformation = userInfoList.get(j).getCridentialId();

                                        Map<String, Object> userMap = new HashMap<>();
                                        userMap.put("userId", userId);
                                        userMap.put("cardId", cardId);
                                        userMap.put("timeCardId", timeCardId);
                                        userMap.put("roomInformation", roomInformation);
                                        String error = jiaoWuHessianService.updateUserInfo(userMap);
                                        Map<String, Object> generalPersonMap = new HashMap<>();
                                        generalPersonMap.put("Base_PerNo", userInfoList.get(j).getCridentialId());
                                        generalPersonMap.put("Base_PerName", userList.get(i).getName());
                                        generalPersonMap.put("Base_CardNo", timeCardId);
                                        String sex = userList.get(i).getSex();
                                        if (sex.equals("男")) {
                                            sex = "1";
                                        } else {
                                            sex = "0";
                                        }

                                        generalPersonMap.put("setBase_Sex", sex);
                                        String errors = attendanceHessianService.updateGeneralPersonal(generalPersonMap);

                                    }
                                }
                            }
                        }
                    }
                }
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 转化成为考勤卡
     *
     * @param cardId
     * @return
     */

    public static String conversionCardId(String cardId) {
        cardId = cardId.substring(2, cardId.length());
        String st[] = new String[8];
        st = cardId.split("");
        return st[6] + st[7] + st[4] + st[5] + st[2] + st[3] + st[0] + st[1];
    }


    /**
     * 考勤卡反转
     *
     * @param cardId
     * @return
     */

    public static String backConversionCardId(String cardId) {
        String st[] = new String[8];
        st = cardId.split("");
        return "00" + st[6] + st[7] + st[4] + st[5] + st[2] + st[3] + st[0] + st[1];
    }


}
