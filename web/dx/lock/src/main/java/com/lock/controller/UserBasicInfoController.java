package com.lock.controller;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.lock.dao.BedRoomDao;
import com.lock.dao.UserBedroomRefDao;
import com.lock.dao.UserInfoDao;
import com.lock.dao.UserInfoDtoDao;
import com.lock.entity.BedRoom;
import com.lock.entity.UserBedroomRef;
import com.lock.entity.UserInfo;
import com.lock.entity.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by caichenglong on 2017/10/25.
 */
@Controller
public class UserBasicInfoController extends BaseController {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserInfoDtoDao userInfoDtoDao;

    @Autowired
    private UserBedroomRefDao userBedroomRefDao;

    @Autowired
    private BedRoomDao bedRoomDao;


    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"userInfo"})
    public void initUserBasic(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("userInfo.");
    }

    /**
     * 查询所有的住房
     *
     * @return
     */
    @RequestMapping("/queryAllUserList")
    @ResponseBody
    public Map<String, Object> queryAllUserList(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            //查询未住的房间
            List<UserInfo> userInfoList = userInfoDao.UserList();
            json = this.resultJson(ErrorCode.SUCCESS, "", userInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 查询所有的住房
     *
     * @return
     */
    @RequestMapping("/queryAllUserInfoDto")
    @ResponseBody
    public Map<String, Object> queryAllUserInfoDto(HttpServletRequest request, String whereSql) {
        Map<String, Object> json = null;
        try {
            List<UserInfoDto> userInfoList = userInfoDtoDao.queryFloorNum(whereSql);
            json = this.resultJson(ErrorCode.SUCCESS, "", userInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 添加开房
     *
     * @return
     */
    @RequestMapping("/addUserInfo")
    @ResponseBody
    public Map<String, Object> addUserInfo(@ModelAttribute("userInfo") UserInfo userInfo) {
        Map<String, Object> json = null;
        try {
            userInfo.setUserName("马博伟");
            userInfo.setCardId("A123456");
            userInfo.setBalance(null);
            userInfo.setConsumeTotal(0);
            userInfo.setCardStatusId(0);
            userInfo.setCardType(0);
            userInfo.setPhonetized_Name("");
            userInfo.setGenderId(0);
            userInfo.setRegisterTime(new Date());
            userInfo.setWorkPosition(0);
            userInfo.setWorkType(0);
            userInfo.setEmployeeType(0);
            userInfo.setExpiredTime(new Date());
            userInfo.setStatus(0);
            userInfo.setPassWord("");
            userInfo.setIdentityNo("");
            userInfo.setCridentialId("");
            userInfo.setHomePhone("");
            userInfo.setCellPhone("");
            userInfo.setBirthday(new Date());
            userInfo.setAddress("");
            userInfo.setEduLevelId(0);
            userInfo.setStuClassId(0);
            userInfo.setDutyId(0);
            userInfo.setEmpDeptId(0);
            userInfo.setTchSpecialityId(0);
            userInfo.setGraduatedSchool("");
            userInfo.setGraduateSpeciality("");
            userInfo.setWorkTime(new Date());
            userInfo.setBedchamberId(0);
            userInfo.setStudentFlag(0);
            userInfo.setTeacherFlag(0);
            userInfo.setACRuleId("");
            userInfo.setPassWord("");
            userInfo.setDeleteDate(new Date());
            userInfo.setOrgCorp("");
            userInfo.setCardId_10("");
            userInfo.setCredentialsTypeId(0);
            userInfo.setCorporation("");
            userInfo.setNationId(0);
            userInfo.setInstructor("");
            userInfo.setPoliticsId(0);
            userInfo.setGraduateTime(new Date());
            userInfo.setGuardianName("");
            userInfo.setStudentStatus("");
            userInfo.setStudentTypeId(0);
            userInfo.setSchoolSystem("");
            userInfo.setStudentChange("");
            userInfo.setRemarks("");
            userInfo.setBedNum(0);
            userInfo.setUserType(0);
            userInfo.setExtUserId("");
            userInfo.setOfficePhone("");
            userInfo.setSubCardId("");
            userInfo.setInsureTime(new Date());
            userInfo.setInsutanceId("");
            userInfo.setHealthStatusId(0);
            userInfo.setSubCardId_1("");
            userInfo.setRankId(0);
            userInfo.setBRecordingInOut(0);
            userInfo.setInstructorId(0);
            userInfo.setSubCard1Type(0);
            userInfo.setEmailAddress("");
            userInfo.setPresentAddress("");
            userInfo.setRelationUserId(0);
            userInfo.setRelationId(0);
            userInfo.setOperatorId(0);
            userInfo.setWeight(0);
            userInfo.setCompanionNum(0);
            userInfo.setTempAddress("");
            userInfo.setPersonnelEntryCauses("");
            userInfo.setDocumentsRequiredConditions("");
            userInfo.setExtensionInfo("");
            userInfo.setLockPassWord("");
            userInfo.setCoercionPassWword("");
            userInfo.setWeChatInfo("");
            userInfo.setSubCardType(0);
            userInfo.setSubCard1Type(0);
            userInfo.setVerifyTypeId(0);
            userInfo.setUpdateTime(new Date());
            userInfo.setOpenDoorAuth(0);
            userInfo.setTimeLimitPeriodIds("");
            userInfo.setUserRole(0);
            userInfo.setDataSource(0);
            userInfo.setEquipmentId("");
            userInfo.setEquipmentupdatetime(0);
            userInfo.setValidStatus(0);
            userInfo.setVphoneId("");
            userInfoDao.addUserInfo(userInfo);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, userInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 修改用户信息
     *
     * @return
     */
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public Map<String, Object> updateUserInfo() {
        Map<String, Object> json = null;
        try {
            UserInfo userInfo = new UserInfo();
            userInfoDao.updateUserInfoByUserId(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 查询管理卡
     *
     * @param request
     * @return
     */
    @RequestMapping("/queryUserList")
    @ResponseBody
    public Map<String, Object> queryUserList(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<UserBedroomRef> userBedroomRefList = userBedroomRefDao.queryUserList();
            json = this.resultJson(ErrorCode.SUCCESS, "", userBedroomRefList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 查询可用房间
     *
     * @param request
     * @return
     */
    @RequestMapping("/queryAvailableList")
    @ResponseBody
    public Map<String, Object> queryAvailableList(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            //查询未住的房间
            List<UserInfo> userInfoList = userInfoDao.UserList();
            //查询管理房间
            List<UserBedroomRef> userBedroomRefList = userBedroomRefDao.queryUserList();

            List<UserInfo> newUserInfoList = new ArrayList<UserInfo>();

            for (int i = 0; i < userInfoList.size(); i++) {
                for (int j = 0; j < userBedroomRefList.size(); j++) {
                    if (userInfoList.get(i).getUserId() == userBedroomRefList.get(j).getUserId()) {
                        newUserInfoList.add(userInfoList.get(i));
                    }
                }
            }
            userInfoList.removeAll(newUserInfoList);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, userInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


//    public String convertAttendanceCard(String cardNo){
//        if(!StringUtils.isTrimEmpty(cardNo)){
//            String[] attendanceCard=cardNo.charAt();
//        }
//    }

    @RequestMapping("/temOpenCard")
    @ResponseBody
    public Map<String, Object> temOpenCard(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            String userName = request.getParameter("userName");
            String roomCode = request.getParameter("roomCode");
            roomCode = "\'" + roomCode + "\'";

            String newRoomCode = request.getParameter("newRoomCode");
            newRoomCode = "\'" + newRoomCode + "\'";
            UserInfo userInfo = new UserInfo();

            BedRoom bedRoom = bedRoomDao.queryBedRoomByName(newRoomCode);
            if (ObjectUtils.isNotNull(bedRoom)) {
                userInfo.setBedchamberId(bedRoom.getId().intValue());
            } else {
                json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.ERROR_SYSTEM, "");
                return json;
            }
            //获取用户id
            int userId = userInfoDao.queryUserId(roomCode);
            userInfo.setUserId(userId);
            userInfo.setUserName(userName);
            userInfoDao.updateTemUserInfoByUserId(userInfo);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
