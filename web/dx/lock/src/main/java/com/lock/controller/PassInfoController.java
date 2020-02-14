package com.lock.controller;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.lock.dao.BedRoomDao;
import com.lock.dao.PassInfoDao;
import com.lock.entity.BedRoom;
import com.lock.entity.PassInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class PassInfoController extends BaseController {
    private static final Logger logger = Logger.getLogger(PassInfoController.class);
    @Autowired
    private PassInfoDao passInfoDao;

    @Autowired
    private BedRoomDao bedRoomDao;

    /**
     * 查询住房情况
     *
     * @param request
     * @return
     */
    @RequestMapping("/passInfoList")
    @ResponseBody
    public Map<String, Object> passInfoList(HttpServletRequest request, String whereSql) {
        Map<String, Object> json = null;
        try {
            List<PassInfo> passInfoList = passInfoDao.passInfoList(whereSql);

            if (ObjectUtils.isNotNull(passInfoList)) {
                for (PassInfo p : passInfoList) {
                    BedRoom bedRoom = bedRoomDao.queryBedRoomById(p.getBedroomid().toString());
                    p.setBedRoomName(bedRoom.getName());
                }
            }
            json = this.resultJson(ErrorCode.SUCCESS, "", passInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 通过房间号查询房间信息
     *
     * @param request
     * @param whereSql
     * @return
     */
    @RequestMapping("/queryBedRoomByName")
    @ResponseBody
    public Map<String, Object> queryBedRoomByName(HttpServletRequest request, String whereSql) {
        logger.error("whereSql---1--" + whereSql);
        Map<String, Object> json = null;
        try {
            System.out.println("whereSql-----" + whereSql);
            BedRoom bedRoom = bedRoomDao.queryBedRoomByName(whereSql);
            if (ObjectUtils.isNotNull(bedRoom)) {
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, bedRoom.getId());
            } else {
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
