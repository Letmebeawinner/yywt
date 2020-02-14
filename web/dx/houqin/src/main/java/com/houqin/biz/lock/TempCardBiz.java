package com.houqin.biz.lock;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.houqin.biz.common.AttendanceHessianService;
import com.houqin.biz.common.JiaoWuHessianService;
import com.houqin.biz.common.LockHessianService;
import com.houqin.common.CommonConstants;
import com.houqin.controller.card.CardController;
import com.houqin.dao.lock.TempCardDao;
import com.houqin.entity.User.User;
import com.houqin.entity.User.UserInfo;
import com.houqin.entity.lock.TempCard;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TempCardBiz extends BaseBiz<TempCard, TempCardDao> {

    private static final Logger logger = Logger.getLogger(CardController.class);

    /**
     * JSON工具类
     */
    protected static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * 通过房间名称查询卡号
     */
    private final static String BED_ROOM_LIST = CommonConstants.LockPath + "/queryBedRoomByName.json";

    @Autowired
    private LockHessianService lockHessianService;
    @Autowired
    private AttendanceHessianService attendanceHessianService;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;

    /**
     * 批量添加开放
     *
     * @param myFile
     * @param request
     * @return
     */
    public String batchAddImportTempOpen(MultipartFile myFile, HttpServletRequest request) throws Exception {
        StringBuffer msg = new StringBuffer();
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 指的行数，一共有多少行+
        int rows = sheet.getLastRowNum();
        for (int i = 1; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                TempCard tempCard = new TempCard();
                String searchFloor = getCellValue(row.getCell((short) 0));//地点楼号
                String CridentialId = getCellValue(row.getCell((short) 1));//房间号
                String userName = getCellValue(row.getCell((short) 2));//姓名
                String sex = getCellValue(row.getCell((short) 3));//性别
                String IdNumber = getCellValue(row.getCell((short) 4));//身份证号
                String beginTime = getCellValue(row.getCell((short) 5));//开始时间
                String endTime = getCellValue(row.getCell((short) 6));//结束时间
                String content = getCellValue(row.getCell((short) 7));//备注
                if (com.a_268.base.util.StringUtils.isTrimEmpty(searchFloor)) {
                    msg.append("第" + i + "行楼号填写错误;");
                    break;
                }
                if (com.a_268.base.util.StringUtils.isTrimEmpty(CridentialId)) {
                    msg.append("第" + i + "行房间号填写错误;");
                    break;
                }
                if (com.a_268.base.util.StringUtils.isTrimEmpty(userName)) {
                    msg.append("第" + i + "行姓名填写错误;");
                    break;
                }
                if (com.a_268.base.util.StringUtils.isTrimEmpty(beginTime)) {
                    msg.append("第" + i + "行开始时间填写错误;");
                    break;
                }
                if (com.a_268.base.util.StringUtils.isTrimEmpty(endTime)) {
                    msg.append("第" + i + "行结束时间填写错误;");
                    break;
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(userName)) {
                    tempCard.setUserName(userName);
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(sex)) {
                    tempCard.setSex(sex);
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(IdNumber)) {
                    tempCard.setIdNumber(IdNumber);
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(beginTime)) {
                    tempCard.setBeginTime(sdf.parse(beginTime));
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(endTime)) {
                    tempCard.setEndTime(sdf.parse(endTime));
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(content)) {
                    tempCard.setContext(content);
                }
                //获取房间信息
                Map<String, String> stringStringMap = lockHessianService.queryBedRoomByName("'" + CridentialId + "'");
                if (ObjectUtils.isNotNull(stringStringMap)) {
                    tempCard.setCridentialId(CridentialId);
                    tempCard.setRoomId(Long.valueOf(stringStringMap.get("id")));
                    Map<String, Object> stringObjectMap = lockHessianService.queryBedRoomList(stringStringMap.get("id"));
                    if (ObjectUtils.isNotNull(stringObjectMap)) {
                        List<UserInfo> userInfoList = (List<UserInfo>) stringObjectMap.get("userInfoList");
                        System.out.println(userInfoList);
                        int size = userInfoList.size();
                        logger.error("获取房间所住人数##############################" + size);
                        //5号楼是单间
                        if (Integer.parseInt(searchFloor) == 5) {
                            if (size == 1) {
                                msg.append("第" + i + "行楼号填写错误，已住满;");
                                break;
                            }
                        } else {
                            if (size == 2) {
                                msg.append("第" + i + "行地点楼号填写错误，已住满;");
                                break;
                            }
                        }
                    }
                    logger.error("##############################" + stringObjectMap);
                }


                String addTempOpenCard = addTempOpenCard(tempCard);
                logger.error("是否添加成功##############################" + addTempOpenCard);
                if ("notExist".equals(addTempOpenCard)) {
                    msg.append("导入 " + userName + " 失败，请检查数据后操作#");
                    break;
                }else if("error".equals(addTempOpenCard)){
                    msg.append("导入 " + userName + " 失败，该房间卡号不可用，请及时处理#");
                    break;
                }
            }
        }
        return msg.toString();
    }


    public String addTempOpenCard(TempCard tempCard) {
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
            if (ObjectUtils.isNotNull(userInfoList)) {

                //住宿卡号
                String cardId = userInfoList.get(0).getCardId();
                //考勤卡号
                String timeCardId = CardController.conversionCardId(cardId);
                //房间号
                String roomInformation = userInfoList.get(0).getCridentialId();
                boolean isOk = attendanceHessianService.queryRoomExist(timeCardId);
                if(!isOk){
                    return "notExist";
                }
                Map<String, Object> userInfoMap = new HashMap<String, Object>();
                //用户id
                userInfoMap.put("userId", userInfoList.get(0).getUserId());
                //用户名称
                userInfoMap.put("userName", tempCard.getUserName());
                userInfoMap.put("BedchamberId", tempCard.getRoomId());
                String idNumber = tempCard.getIdNumber();
                if (!StringUtils.isTrimEmpty(idNumber)) {
                    userInfoMap.put("identityNo", tempCard.getIdNumber());
                } else {
                    userInfoMap.put("identityNo", "");
                }
                //住房过期时间
                userInfoMap.put("ExpiredTime", tempCard.getEndTime());
                logger.error("userInfoMap-------1---" + userInfoMap);
                String errorInfo = lockHessianService.updateBaseUserInfo(userInfoMap);
                if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {

                } else {
                    if (!StringUtils.isTrimEmpty(userInfoList.get(0).getCardId())) {

                        tempCard.setCardNo(cardId);
                        tempCard.setTimeCardNo(timeCardId);
                        tempCard.setRoomNo(roomInformation);
                        this.save(tempCard);
                        //修改考勤记录
                        Map<String, Object> generalPersonMap = new HashMap<>();
                        generalPersonMap.put("Base_PerNo", userInfoList.get(0).getCridentialId());
                        generalPersonMap.put("Base_PerName", tempCard.getUserName());
                        generalPersonMap.put("Base_CardNo", timeCardId);
                        String sex = tempCard.getSex();
                        if (!StringUtils.isTrimEmpty(sex)) {
                            if (sex.equals("男")) {
                                sex = "1";
                            } else {
                                sex = "0";
                            }
                            generalPersonMap.put("setBase_Sex", sex);
                        } else {
                            generalPersonMap.put("setBase_Sex", "");
                        }
                        String errors = attendanceHessianService.updateGeneralPersonal(generalPersonMap);
                        logger.error("TempCardBiz.addTempOpenCard########" + errors);
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * @param cell
     * @return
     * @Description 获得Hsscell内容
     */
    public String getCellValue(HSSFCell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat df = new DecimalFormat("0");
                    value = df.format(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value.trim();
    }

    /**
     * 批量添加开放
     *
     * @param myFile
     * @param request
     * @return
     */
    public String batchAddImportUserOpen(MultipartFile myFile, HttpServletRequest request) throws Exception {
        StringBuffer msg = new StringBuffer();
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 指的行数，一共有多少行+
        int rows = sheet.getLastRowNum();
        for (int i = 1; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                TempCard tempCard = new TempCard();
                String searchFloor = getCellValue(row.getCell((short) 0));//地点楼号
                String classId = getCellValue(row.getCell((short) 1));//班级id
                String CridentialId = getCellValue(row.getCell((short) 2));//房间号
                String userName = getCellValue(row.getCell((short) 3));//姓名
                String sex = getCellValue(row.getCell((short) 4));//性别
                String IdNumber = getCellValue(row.getCell((short) 5));//身份证号
                String beginTime = getCellValue(row.getCell((short) 6));//开始时间
                String endTime = getCellValue(row.getCell((short) 7));//结束时间
                String content = getCellValue(row.getCell((short) 8));//备注
                if (com.a_268.base.util.StringUtils.isTrimEmpty(searchFloor)) {
                    msg.append("第" + i + "行楼号填写错误;");
                    break;
                }
                if (com.a_268.base.util.StringUtils.isTrimEmpty(classId)) {
                    msg.append("第" + i + "行班级填写错误;");
                    break;
                }
                if (com.a_268.base.util.StringUtils.isTrimEmpty(CridentialId)) {
                    msg.append("第" + i + "行房间号填写错误;");
                    break;
                }
                if (com.a_268.base.util.StringUtils.isTrimEmpty(userName)) {
                    msg.append("第" + i + "行姓名填写错误;");
                    break;
                }
                if (com.a_268.base.util.StringUtils.isTrimEmpty(beginTime)) {
                    msg.append("第" + i + "行开始时间填写错误;");
                    break;
                }
                if (com.a_268.base.util.StringUtils.isTrimEmpty(endTime)) {
                    msg.append("第" + i + "行结束时间填写错误;");
                    break;
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(classId)) {
                    tempCard.setClassId(classId);
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(userName)) {
                    tempCard.setUserName(userName);
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(sex)) {
                    tempCard.setSex(sex);
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(IdNumber)) {
                    tempCard.setIdNumber(IdNumber);
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(beginTime)) {
                    tempCard.setBeginTime(sdf.parse(beginTime));
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(endTime)) {
                    tempCard.setEndTime(sdf.parse(endTime));
                }
                if (!com.a_268.base.util.StringUtils.isTrimEmpty(content)) {
                    tempCard.setContext(content);
                }
                //获取房间信息
                Map<String, String> stringStringMap = lockHessianService.queryBedRoomByName("'" + CridentialId + "'");
                if (ObjectUtils.isNotNull(stringStringMap)) {
                    tempCard.setCridentialId(CridentialId);
                    tempCard.setRoomId(Long.valueOf(stringStringMap.get("id")));
                    Map<String, Object> stringObjectMap = lockHessianService.queryBedRoomList(stringStringMap.get("id"));
                    if (ObjectUtils.isNotNull(stringObjectMap)) {
                        List<UserInfo> userInfoList = (List<UserInfo>) stringObjectMap.get("userInfoList");
                        System.out.println(userInfoList);
                        int size = userInfoList.size();
                        logger.error("获取房间所住人数##############################" + size);
                        //5号楼是单间
                        if (Integer.parseInt(searchFloor) == 5) {
                            if (size == 1) {
                                msg.append("第" + i + "行楼号填写错误，已住满;");
                                break;
                            }
                        } else {
                            if (size == 2) {
                                msg.append("第" + i + "行地点楼号填写错误，已住满;");
                                break;
                            }
                        }
                    }
                    logger.error("##############################" + stringObjectMap);
                }
                boolean addTempOpenCard = addUserOpenCard(tempCard);
                logger.error("是否添加成功##############################" + addTempOpenCard);
                if (!addTempOpenCard) {
                    msg.append("导入 " + userName + " 失败，请检查数据后操作#");
                }
            }
        }
        return msg.toString();
    }

    public boolean addUserOpenCard(TempCard tempCard) {
        boolean flag = false;
        try {
            String errorInfo = "";
            String userName = tempCard.getUserName();
            userName = userName.replaceAll(" ", "");

            // 当前导入学员有多少个，无用代码，只是看班级数据
            String _whereSql = "name='" + userName + "'";
            Map<String, Object> _stringObjectMap = jiaoWuHessianService.userList(null, _whereSql);
            logger.error("当前导入学员有多少个，无用代码，只是看数据######" + _stringObjectMap);

            // 当前导入学员信息
            String whereSql = "name='" + userName + "'" + " and classId=" + tempCard.getClassId();
            Map<String, Object> stringObjectMap = jiaoWuHessianService.userList(null, whereSql);
            String userlist = gson.toJson(stringObjectMap.get("userList"));
            logger.error("userlist--------+++++++++----" + userlist);
            List<User> userList = gson.fromJson(userlist, new TypeToken<List<User>>() {
            }.getType());
            //查询剩余房间
            String CridentialId = tempCard.getCridentialId().replace("-", "");
            Map<String, Object> result1 = new HashMap<>();
            result1.put("roomId", tempCard.getRoomId());
            result1.put("CridentialId", CridentialId);
            Map<String, Object> map = lockHessianService.queryAvaliableRoomByRoom(result1);
            String userInfos = gson.toJson(map.get("userInfoList"));
            List<UserInfo> userInfoList = gson.fromJson(userInfos, new TypeToken<List<UserInfo>>() {
            }.getType());
            logger.error("userInfoList--------+++++++++----" + userInfoList);
            //如果培训人员和剩余房间不为空
            if (ObjectUtils.isNotNull(userList) && ObjectUtils.isNotNull(userInfoList)) {
                Map<String, Object> userInfoMap = new HashMap<String, Object>();
                //用户id
                userInfoMap.put("userId", userInfoList.get(0).getUserId());
                //用户名称
                userInfoMap.put("userName", userList.get(0).getName());
                userInfoMap.put("BedchamberId", tempCard.getRoomId());
                String idNumber = tempCard.getIdNumber();
                if (!StringUtils.isTrimEmpty(idNumber)) {
                    userInfoMap.put("identityNo", tempCard.getIdNumber());
                } else {
                    userInfoMap.put("identityNo", "");
                }
                //住房过期时间
                userInfoMap.put("ExpiredTime", tempCard.getEndTime());
                logger.error("userInfoMap--------2----" + userInfoMap);
                errorInfo = lockHessianService.updateBaseUserInfo(userInfoMap);
                if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {

                } else {
                    if (!StringUtils.isTrimEmpty(userInfoList.get(0).getCardId())) {
                        Long userId = userList.get(0).getId();
                        //住宿卡号
                        String cardId = userInfoList.get(0).getCardId();
                        //考勤卡号
                        String timeCardId = conversionCardId(cardId);
                        //房间号
                        String roomInformation = userInfoList.get(0).getCridentialId();
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("userId", userId);
                        userMap.put("cardId", cardId);
                        userMap.put("timeCardId", timeCardId);
                        userMap.put("roomInformation", roomInformation);
                        String error = jiaoWuHessianService.updateUserInfo(userMap);
                        logger.error("TempCardBiz.addUserOpenCard#jiaoWuHessianService.updateUserInfo#######" + error);
                        Map<String, Object> generalPersonMap = new HashMap<>();
                        generalPersonMap.put("Base_PerNo", userInfoList.get(0).getCridentialId());
                        generalPersonMap.put("Base_PerName", userList.get(0).getName());
                        generalPersonMap.put("Base_CardNo", timeCardId);
                        String sex = tempCard.getSex();
                        if (!StringUtils.isTrimEmpty(sex)) {
                            if (sex.equals("男")) {
                                sex = "1";
                            } else {
                                sex = "0";
                            }
                        } else {
                            sex = "";
                        }
                        generalPersonMap.put("setBase_Sex", sex);
                        String errors = attendanceHessianService.updateGeneralPersonal(generalPersonMap);
                        logger.error("TempCardBiz.addUserOpenCard########" + errors);
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
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


}
