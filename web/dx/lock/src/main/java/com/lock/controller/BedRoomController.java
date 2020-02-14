package com.lock.controller;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.StringUtils;
import com.lock.dao.BedRoomDao;
import com.lock.entity.BedRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by caichenglong on 2017/10/25.
 */
@Controller
public class BedRoomController extends BaseController {

    @Autowired
    private BedRoomDao bedRoomDao;

    /**
     * 查询所有的住房
     *
     * @return
     */
    @RequestMapping("/queryAllBedRoom")
    @ResponseBody
    public Map<String, Object> queryAllBedRoom(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<BedRoom> bedRoomList = bedRoomDao.bedRoomList();
            json = this.resultJson(ErrorCode.SUCCESS, "", bedRoomList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 通过楼层获取房间号码
     *
     * @param whereSql
     * @return
     */
    @RequestMapping("/bedRoomListByFloor")
    @ResponseBody
    public Map<String, Object> bedRoomListByFloor(String whereSql) {
        Map<String, Object> json = null;
        try {
            String search=" where 1=1";
            if(!StringUtils.isTrimEmpty(whereSql)){
                search=search+whereSql;
            }
            List<BedRoom> bedRoomList = bedRoomDao.bedRoomListByFloor(search);
            json = this.resultJson(ErrorCode.SUCCESS, "", bedRoomList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
