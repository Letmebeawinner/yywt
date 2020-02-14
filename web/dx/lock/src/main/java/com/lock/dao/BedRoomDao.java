package com.lock.dao;

import com.a_268.base.core.BaseDao;
import com.lock.entity.BedRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by caichenglong on 2017/10/25.
 */
public interface BedRoomDao extends BaseDao<BedRoom> {

    /**
     * 获取住房所有信息
     *
     * @return
     */
    public List<BedRoom> bedRoomList();

    /**
     * 获取按照几号楼获取房间信息
     *
     * @param whereSql
     * @return
     */
    public List<BedRoom> bedRoomListByFloor(@Param("whereSql") String whereSql);

    /**
     * 查询房间信息通过id
     * @param whereSql
     * @return
     */
    public BedRoom queryBedRoomById(@Param("whereSql") String whereSql);


    /**
     * 查询房间信息通过房间名
     * @param whereSql
     * @return
     */
    public BedRoom queryBedRoomByName(@Param("whereSql") String whereSql);


}
