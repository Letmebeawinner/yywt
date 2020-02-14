package com.houqin.controller.lock;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.houqin.biz.common.AttendanceHessianService;
import com.houqin.biz.common.LockHessianService;
import com.houqin.biz.lock.RoomRepairBiz;
import com.houqin.biz.lock.TempCardBiz;
import com.houqin.common.CommonConstants;
import com.houqin.controller.card.CardController;
import com.houqin.controller.messstock.MessStockController;
import com.houqin.entity.User.UserInfo;
import com.houqin.entity.lock.BedRoom;
import com.houqin.entity.lock.RoomRepair;
import com.houqin.entity.lock.TempCard;
import com.houqin.entity.lock.UserInfoDto;
import com.houqin.utils.GenerateSqlUtil;
import com.houqin.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by caichenglong on 2017/10/25.
 */
@Slf4j
@Controller
@RequestMapping("/admin/houqin")
public class LockController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MessStockController.class);

    private final static String BED_ROOM = CommonConstants.LockPath + "/queryAllBedRoom.json";
    private final static String BED_ROOM_FLOOR = CommonConstants.LockPath + "/bedRoomListByFloor.json";
    private final static String USER_LIST = CommonConstants.LockPath + "/queryAllUserList.json";
    private final static String USER_INFO_LIST = CommonConstants.LockPath + "/queryAllUserInfoDto.json";
    private final static String PASS_INFO_LIST = CommonConstants.LockPath + "/passInfoList.json";
    private final static String BED_ROOM_LIST = CommonConstants.LockPath + "/queryBedRoomByName.json";

    private final static String bedroomList = "/lock/bedRoom_list";//房间管理
    private final static String passInfoList = "/lock/passInfo_list";//房间管理
    private final static String userList = "/lock/user_list";//房间管理
    private final static String temCardList = "/lock/tempcard_list";//房间管理

    @Autowired
    private LockHessianService lockHessianService;
    @Autowired
    private AttendanceHessianService attendanceHessianService;
    @Autowired
    private TempCardBiz temLockBiz;
    @Autowired
    private RoomRepairBiz roomRepairBiz;


    @InitBinder({"tempCard"})
    public void initTemCard(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("tempCard.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 获取所有的客房
     *
     * @param request
     * @return
     */
    @RequestMapping("/BedRoomList")
    public String BedRoomList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,
                              @RequestParam(value = "searchFloor", required = false) String searchFloor) {
        // 拼装参数
        try {
            pagination.setPageSize(10);
            pagination.setRequest(request);

            Map<String, Object> resultMap;
            if (!StringUtils.isTrimEmpty(searchFloor)) {
                StringBuffer sb = new StringBuffer("whereSql=")
                        .append(URLEncoder.encode(" and Bedchamber.Name LIKE '", "UTF-8"))
                        .append(searchFloor)
                        .append(URLEncoder.encode("-%'", "UTF-8"));
                resultMap = HttpUtil.doGet(BED_ROOM_FLOOR, sb.toString());
                // 环回
                request.setAttribute("searchFloor", searchFloor);
            } else {
                resultMap = HttpUtil.doGet(BED_ROOM, "");

            }

            if (resultMap != null && resultMap.get("code").equals("0")) {
                List<Map<String, Object>> ps = (List<Map<String, Object>>) resultMap.get("data");
                Map<String, Object> map = getOnePage(ps, pagination);

                // 分页
                pagination = (Pagination) map.get("pagination");
                List<Map<String, Object>> roomList = (List<Map<String, Object>>) map.get("data");
                request.setAttribute("pagination", pagination);
                request.setAttribute("roomList", roomList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bedroomList;
    }


    /**
     * 获取一页的数据
     *
     * @param room
     * @param pagination
     * @return
     */
    public Map<String, Object> getOnePage(List<Map<String, Object>> room, Pagination pagination) {
        if (room == null || room.size() == 0) {
            return null;
        }
        pagination.setTotalPages(room.size() % pagination.getPageSize() == 0 ? room.size() / pagination.getPageSize() : room.size() / pagination.getPageSize() + 1);
        pagination.setTotalCount(room.size());
        int begin = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
        int end = pagination.getCurrentPage() * pagination.getPageSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", room.subList(begin, end > room.size() ? room.size() : end));
        map.put("pagination", pagination);
        return map;
    }

    /**
     * 获取所有用户
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/userBasic")
    public String userList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        // 拼装参数
        try {
            pagination.setPageSize(10);
            pagination.setRequest(request);

            Map<String, Object> resultMap = HttpUtil.doGet(USER_LIST, "");
            if (resultMap != null && resultMap.get("code").equals("0")) {
                List<Map<String, Object>> ps = (List<Map<String, Object>>) resultMap.get("data");
                Map<String, Object> map = getOnePage(ps, pagination);

                // 分页
                pagination = (Pagination) map.get("pagination");
                List<Map<String, Object>> userList = (List<Map<String, Object>>) map.get("data");
                request.setAttribute("userList", userList);
                request.setAttribute("pagination", pagination);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * 住房使用记录
     *
     * @param request
     * @param pagination
     * @return
     */

    @RequestMapping("/passInfoList")
    public String passInfoList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,
                               @RequestParam(value = "searchRoom", required = false) String searchRoom) {
        // 拼装参数
        try {

            pagination.setPageSize(10);
            pagination.setRequest(request);
            request.setAttribute("searchRoom", searchRoom);

            if (searchRoom != "" && searchRoom != null) {
                searchRoom = searchRoom.substring(0, searchRoom.length() - 2);
            }
            //查询

            Map<String, Object> resultMap;
            if (!StringUtils.isTrimEmpty(searchRoom)) {
                StringBuffer sb = new StringBuffer("whereSql=").append(URLEncoder.encode(" and UserBedroomRef.bedroomid= ", "UTF-8")).append(searchRoom);
                resultMap = HttpUtil.doGet(PASS_INFO_LIST, sb.toString());
            } else {
                resultMap = HttpUtil.doGet(PASS_INFO_LIST, "");
            }

            if (resultMap != null && resultMap.get("code").equals("0")) {
                List<Map<String, Object>> ps = (List<Map<String, Object>>) resultMap.get("data");
                Map<String, Object> map = getOnePage(ps, pagination);

                // 分页
                pagination = (Pagination) map.get("pagination");
                List<Map<String, Object>> passInfoList = (List<Map<String, Object>>) map.get("data");
                request.setAttribute("pagination", pagination);
                request.setAttribute("passInfoList", passInfoList);
            }

            Map<String, Object> roomResultMap;
            roomResultMap = HttpUtil.doGet(BED_ROOM_FLOOR, "");
            List<Map<String, Object>> roomList = (List<Map<String, Object>>) roomResultMap.get("data");
            request.setAttribute("roomList", roomList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return passInfoList;
    }

    /**
     * 查询房间使用状态
     *
     * @return
     */
    @RequestMapping("/queryRoomStatus")
    public ModelAndView queryRoomStatus(HttpServletRequest request, @RequestParam(value = "searchFloor", required = false) String searchFloor) {
        ModelAndView mv = new ModelAndView("/lock/room_status_list");
        try {
            if (searchFloor == null || searchFloor == "") {
                searchFloor = String.valueOf(1);
            }

            String id = request.getParameter("id");
            String roomId = request.getParameter("roomId");
            if (id == null && roomId == null) {
                request.setAttribute("id", 1);
                request.setAttribute("roomId", 1);
            } else {
                request.setAttribute("id", Long.valueOf(id));
                request.setAttribute("roomId", Long.valueOf(roomId));
            }

            Map<String, Object> resultMap;
            if (!StringUtils.isTrimEmpty(searchFloor)) {
                StringBuffer sb = new StringBuffer("whereSql=")
                        .append(URLEncoder.encode(" and Bedchamber.Name LIKE '", "UTF-8"))
                        .append(searchFloor)
                        .append(URLEncoder.encode("-%'", "UTF-8"));
                resultMap = HttpUtil.doGet(BED_ROOM_FLOOR, sb.toString());
                // 环回
                request.setAttribute("searchFloor", searchFloor);
            } else {
                resultMap = HttpUtil.doGet(BED_ROOM, "");
            }

            List<BedRoom> roomList = new ArrayList<>();
            if (resultMap != null && resultMap.get("code").equals("0")) {
                String data = gson.toJson(resultMap.get("data"));
                roomList = gson.fromJson(data, new TypeToken<List<BedRoom>>() {
                }.getType());
            }


            Map<String, Object> userMap = null;
            if (!StringUtils.isTrimEmpty(searchFloor)) {
                StringBuffer sb = new StringBuffer("whereSql=")
                        .append(URLEncoder.encode(" and bed.Name LIKE '", "UTF-8"))
                        .append(searchFloor)
                        .append(URLEncoder.encode("-%'", "UTF-8"));
                userMap = HttpUtil.doGet(USER_INFO_LIST, sb.toString());
            }

            List<UserInfoDto> userInfoDtoList = new ArrayList<>();
            if (userMap != null && userMap.get("code").equals("0")) {
                String data = gson.toJson(userMap.get("data"));
                userInfoDtoList = gson.fromJson(data, new TypeToken<List<UserInfoDto>>() {
                }.getType());
                mv.addObject("userInfoDtoList", userInfoDtoList);
            }


            if (ObjectUtils.isNotNull(roomList) && ObjectUtils.isNotNull(userInfoDtoList)) {
                for (BedRoom b : roomList) {
                    for (UserInfoDto u : userInfoDtoList) {
                        if (b.getId().equals(u.getBedchamberId())) {
                            if ("5".equals(searchFloor)) {//5号楼是单人间
                                b.setType(2);
                            } else {
                                b.setType(1);
                            }
                        }
                    }
                }
            }

            // 住了一个人的
            List<BedRoom> bedRoomList = roomList.stream().filter(e -> e.getType() == 1).collect(Collectors.toList());
            // 除去住了一个人的
            roomList.removeAll(bedRoomList);
            for (BedRoom bedRoom : bedRoomList) {
                List<UserInfo> userInfoList = new ArrayList<>();
                Map<String, Object> roomMap = lockHessianService.queryBedRoomList(String.valueOf(bedRoom.getId()));
                userInfoList = gson.fromJson(gson.toJson(roomMap.get("userInfoList")), new TypeToken<List<UserInfo>>() {
                }.getType());
                if (userInfoList.size() > 1 && userInfoList.size() == 2) {
                    bedRoom.setType(2);
                }
            }
            // 加上住了一个人的
            roomList.addAll(bedRoomList);
            request.setAttribute("roomList", roomList);

            // 查询已维修的房间
            List<RoomRepair> roomRepairs = roomRepairBiz.findAll();
            Map<Long, RoomRepair> repairList = roomRepairs.stream().filter(r -> r.getServiceStatus() == 1)
                    .collect(Collectors.toMap(RoomRepair::getRoomId, Function.identity()));

            request.setAttribute("repairList", repairList);

            //空闲房间
            List<BedRoom> roomList1 = roomList.stream().filter(r -> r.getType() == 0).filter(r -> repairList.get(r.getId()) == null).collect(Collectors.toList());
            if (roomList1 != null) {
                request.setAttribute("idle", roomList1.size());
            } else {
                request.setAttribute("idle", 0);
            }

        } catch (Exception e) {
            log.error("LockController.queryRoomStatus", e);
            ;
        }
        return mv;
    }

    /**
     * 查询房间信息通过房间名称
     *
     * @param whereSql
     * @return
     */
    @RequestMapping("/queryBedRoomByName")
    @ResponseBody
    public Map<String, Object> queryBedRoomByName(String whereSql) {
        Map<String, Object> json = null;
        try {
            System.out.println("查询房间信息通过房间名称--/queryBedRoomByName+++++++++" + whereSql);
            Map<String, Object> resultMap = HttpUtil.doGet(BED_ROOM_LIST, "whereSql=" + whereSql);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 临时开房
     *
     * @param id
     * @return
     */
    @RequestMapping("/tempOpenCardById/{id}/{roomNo}")
    public ModelAndView openCardById(@PathVariable("id") Long id, @PathVariable("roomNo") String cridentialId) {
        ModelAndView mv = new ModelAndView("/lock/add-temporaryHousing");
        mv.addObject("id", id);
        mv.addObject("cridentialId", cridentialId);
        boolean isRepairing = isRepairing(id);
        mv.addObject("isRepairing", isRepairing);
        return mv;
    }

    private boolean isRepairing(Long id) {
        // 根据roomID 查询是否正在维修
        List<RoomRepair> roomRepairs = roomRepairBiz.find(null, "1=1 and roomId=" + id);
        boolean isRepairing;
        if (CollectionUtils.isEmpty(roomRepairs)) {
            isRepairing = false;
        } else {
            if (roomRepairs.get(0).getServiceStatus() == 1) {
                isRepairing = true;
            } else {
                isRepairing = false;
            }
        }
        return isRepairing;
    }

    /**
     * 更换房间
     *
     * @param roomId
     * @param name
     * @param oldId
     * @return
     */
    @RequestMapping("/updateTempOpenCard")
    @ResponseBody
    public Map<String, Object> updateTempOpenCard(@RequestParam("roomId") Long roomId,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("oldId") Long oldId,
                                                  @RequestParam("oldRoomId") Long oldRoomId) {
        Map<String, Object> json = null;
        try {
            //查询以前的房间信息
            Map<String, Object> roomMap = lockHessianService.queryBedRoomList(String.valueOf(oldRoomId));
            List<UserInfo> pldUserInfoList = gson.fromJson(gson.toJson(roomMap.get("userInfoList")), new TypeToken<List<UserInfo>>() {
            }.getType());

            //查询剩余房间
            String CridentialId = name.replace("-", "");
            Map<String, Object> result1 = new HashMap<>();
            result1.put("roomId", roomId);
            result1.put("CridentialId", CridentialId);

            Map<String, Object> map = lockHessianService.queryAvaliableRoomByRoom(result1);
            String userInfos = gson.toJson(map.get("userInfoList"));
            List<UserInfo> userInfoList = gson.fromJson(userInfos, new TypeToken<List<UserInfo>>() {
            }.getType());


            // 新房间号码
            String roomInformation = userInfoList.get(0).getCridentialId();

            Map<String, Object> userInfoMap = new HashMap<String, Object>();
            // 以前的用户id
            userInfoMap.put("userId", pldUserInfoList.get(0).getUserId());
            // 新的房间id
            userInfoMap.put("BedchamberId", roomId);
            // 新的
            userInfoMap.put("CridentialId", roomInformation);

            TempCard tempCard = new TempCard();
            tempCard.setId(oldId);
            tempCard.setRoomId(roomId);
            tempCard.setRoomNo(roomInformation);

            boolean flag = lockHessianService.updateCridentialIdByUserId(userInfoMap);
            if (flag) {
                temLockBiz.update(tempCard);
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 开通临时卡
     *
     * @param tempCard
     * @return
     */
    @RequestMapping("/addTempOpenCard")
    @ResponseBody
    public Map<String, Object> addTempOpenCard(@ModelAttribute("tempCard") TempCard tempCard,
                                               @RequestParam(value = "serviceStatus", required = false) Integer serviceStatus) {
        Map<String, Object> json = null;
        try {

            //查询剩余房间
            String CridentialId = tempCard.getCridentialId().replace("-", "");
            Map<String, Object> result1 = new HashMap<>();
            result1.put("roomId", tempCard.getRoomId());
            result1.put("CridentialId", CridentialId);

            Map<String, Object> map = lockHessianService.queryAvaliableRoomByRoom(result1);
            String userInfos = gson.toJson(map.get("userInfoList"));
            List<UserInfo> userInfoList = gson.fromJson(userInfos, new TypeToken<List<UserInfo>>() {
            }.getType());


            Map<String, Object> userInfoMap = new HashMap<String, Object>();
            //用户id
            userInfoMap.put("userId", userInfoList.get(0).getUserId());
            //用户名称
            userInfoMap.put("userName", tempCard.getUserName());
            userInfoMap.put("BedchamberId", tempCard.getRoomId());
            userInfoMap.put("identityNo", tempCard.getIdNumber());
            //住房过期时间
            userInfoMap.put("ExpiredTime", tempCard.getEndTime());
            System.out.println("userInfoMap-------1---" + userInfoMap);
            String errorInfo = lockHessianService.updateBaseUserInfo(userInfoMap);

            if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {

            } else {
                if (!StringUtils.isTrimEmpty(userInfoList.get(0).getCardId())) {
                    //住宿卡号
                    String cardId = userInfoList.get(0).getCardId();
                    //考勤卡号
                    String timeCardId = CardController.conversionCardId(cardId);
                    //房间号
                    String roomInformation = userInfoList.get(0).getCridentialId();
                    tempCard.setCardNo(cardId);
                    tempCard.setTimeCardNo(timeCardId);
                    tempCard.setRoomNo(roomInformation);
                    temLockBiz.save(tempCard);

                    //修改考勤记录
                    Map<String, Object> generalPersonMap = new HashMap<>();
                    generalPersonMap.put("Base_PerNo", userInfoList.get(0).getCridentialId());
                    generalPersonMap.put("Base_PerName", tempCard.getUserName());
                    generalPersonMap.put("Base_CardNo", timeCardId);
                    String sex = tempCard.getSex();
                    if (sex.equals("男")) {
                        sex = "1";
                    } else {
                        sex = "0";
                    }
                    generalPersonMap.put("setBase_Sex", sex);
                    String errors = attendanceHessianService.updateGeneralPersonal(generalPersonMap);
                }
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 新增维修状态
     *
     * @param roomId    房间roomId
     * @param serStatus 维修状态
     */
    @RequestMapping("/markRepair")
    @ResponseBody
    public Map<String, Object> markRepair(@RequestParam("roomId") Long roomId,
                                          @RequestParam("serviceStatus") Integer serStatus) {
        Map<String, Object> rs;
        try {
            RoomRepair roomRepair = new RoomRepair();
            roomRepair.setRoomId(roomId);
            String whereSql = GenerateSqlUtil.getSql(roomRepair);
            List<RoomRepair> roomRepairs = roomRepairBiz.find(null, whereSql);
            if (CollectionUtils.isEmpty(roomRepairs)) {
                roomRepair.setServiceStatus(serStatus);
                roomRepairBiz.save(roomRepair);
            } else {
                roomRepair.setId(roomRepairs.get(0).getId());
                roomRepair.setServiceStatus(serStatus);
                roomRepairBiz.update(roomRepair);
            }
            rs = super.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            log.error("LockController.markRepair", e);

            rs = super.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }

        return rs;
    }

    /**
     * 临时开卡记录
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/tempOpenCardList")
    public String tempOpenCardList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,
                                   @ModelAttribute("tempCard") TempCard tempCard) {
        try {
            String whereSql = GenerateSqlUtil.getSql(tempCard);
            whereSql += " order by id desc";
            pagination.setRequest(request);
            List<TempCard> temCardList = temLockBiz.find(pagination, whereSql);
            request.setAttribute("temCardList", temCardList);
            request.setAttribute("tempCard", tempCard);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temCardList;
    }

    /**
     * 去批量导入临时住房
     *
     * @return
     */
    @RequestMapping("/toBatchTempOpenCard")
    public ModelAndView toBatchTempOpenCard() {
        ModelAndView modelAndView = new ModelAndView("/lock/import_temp_card");
        try {
            System.out.println("后勤开卡去批量添加");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 批量导入临时住房
     *
     * @return
     */
    @RequestMapping("/batchAddImportTempCard")
    public ModelAndView batchAddImportTempOpen(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        ModelAndView modelAndView = new ModelAndView("/lock/import_temp_card");
        try {
            String importTempOpen = temLockBiz.batchAddImportTempOpen(myFile, request);
            if (StringUtils.isTrimEmpty(importTempOpen)) {
                importTempOpen = "导入成功";
            }
            modelAndView.addObject("errorInfo", importTempOpen);
        } catch (Exception e) {
            logger.error("EmployeeController.batchAddImportTempCard", e);
            modelAndView.addObject("errorInfo", "导入失败, 请核对模板格式");
        }
        return modelAndView;
    }

    /**
     * 去批量导入学员住房
     *
     * @return
     */
    @RequestMapping("/toBatchUserOpenCard")
    public ModelAndView toBatchUserOpenCard() {
        ModelAndView modelAndView = new ModelAndView("/lock/import_user_card");
        try {
            System.out.println("后勤开卡去批量添加");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 批量导入学员住房
     *
     * @return
     */
    @RequestMapping("/batchAddImportUserCard")
    public ModelAndView batchAddImportUserCard(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        ModelAndView modelAndView = new ModelAndView("/lock/import_user_card");
        try {
            String importTempOpen = temLockBiz.batchAddImportUserOpen(myFile, request);
            if (StringUtils.isTrimEmpty(importTempOpen)) {
                importTempOpen = "导入成功";
            }
            modelAndView.addObject("errorInfo", importTempOpen);
        } catch (Exception e) {
            logger.error("EmployeeController.batchAddImportUserCard", e);
            modelAndView.addObject("errorInfo", "导入失败, 请核对模板格式");
        }
        return modelAndView;
    }


}
