package com.houqin.common;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.electricity.ElectricityBiz;
import com.houqin.biz.meeting.MeetingBiz;
import com.houqin.biz.meeting.MeetingRecordBiz;
import com.houqin.biz.price.PriceBiz;
import com.houqin.biz.repair.RepairBiz;
import com.houqin.biz.repairType.RepairTypeBiz;
import com.houqin.biz.water.WaterBiz;
import com.houqin.entity.electricity.Electricity;
import com.houqin.entity.electricity.ElectricityDto;
import com.houqin.entity.meeting.Meeting;
import com.houqin.entity.meeting.MeetingRecord;
import com.houqin.entity.repair.Repair;
import com.houqin.entity.repair.RepairDto;
import com.houqin.entity.repairType.RepairType;
import com.houqin.entity.sysuser.SysUser;
import com.houqin.entity.water.Water;
import com.houqin.entity.water.WaterDto;
import com.houqin.enums.PriceType;
import com.houqin.utils.GenerateSqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 后勤hessian接口
 *
 * @author lzh
 * @create 2017-02-23-15:27
 */
@Slf4j
@Service
public class HqHessianBiz implements HqHessianService {

    @Autowired
    private ElectricityBiz electricityBiz;
    @Autowired
    private PriceBiz priceBiz;
    @Autowired
    private WaterBiz waterBiz;
    @Autowired
    private RepairTypeBiz repairTypeBiz;
    @Autowired
    private RepairBiz repairBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private MeetingBiz meetingBiz;
    @Autowired
    private MeetingRecordBiz meetingRecordBiz;

    private Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public Double getAnnualEnergyExpense(String year) {
        String where = " LEFT(createTime, 4) LIKE '" + year + "'";
        double sum = 0D;
        List<Electricity> electricityList = electricityBiz.find(null, where);
        if (ObjectUtils.isNotNull(electricityList)) {
            double price = priceBiz.getPriceByType(PriceType.ELECTRICITY.toString());
            sum += electricityList.stream().mapToDouble(e -> e.getDegrees() * price).sum();
        }
        List<Water> waterList = waterBiz.find(null, where);
        if (ObjectUtils.isNotNull(waterList)) {
            double price = priceBiz.getPriceByType(PriceType.WATER.toString());
            sum += waterList.stream().mapToDouble(w -> w.getTunnage() * price).sum();
        }
        return new BigDecimal(String.valueOf(sum)).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    /**
     * @Description: 获取用水量统计
     * @author: lzh
     * @Param: [fromDate, toDate, pagination]
     * @Date: 13:00
     */
    @Override
    public Map<String, Object> getWaterStatisticsGroupByUserId(Date fromDate, Date toDate, Pagination pagination) {
        Map<String, Object> map = new HashMap<>();
        int pageSize = pagination.getPageSize();
        int offset = (pagination.getCurrentPage() - 1) * pageSize;
        String whereSql = sqlForPage(fromDate, toDate, PriceType.WATER.toString());
        String whereSql2 = sqlForPage(fromDate, toDate, pageSize, offset, PriceType.WATER.toString());
        double price = priceBiz.getPriceByType(PriceType.WATER.toString());
        //  以用户分组求出的用水量
        List<Water> waters = waterBiz.find(null, whereSql);
        List<Water> waters1 = waterBiz.find(null, whereSql2);
        List<WaterDto> waterDtos = waters1.stream()
                .map(water -> {
                    return waterBiz.convertWaterToDto(water, price);
                })
                .collect(Collectors.toList());
        double totalWater = 0.0;
        for (Water water : waters1) {
            totalWater += water.getTunnage();
        }
        int totalSize = waters.size();

        BigDecimal bg = new BigDecimal(price * totalWater).setScale(2, RoundingMode.UP);

        //将分页数据初始化
        pagination.init(totalSize, pageSize, pagination.getCurrentPage());
        map.put("pagination", pagination);
        map.put("WaterDtos", waterDtos);
        map.put("totalWater", totalWater);
        map.put("price", price);
        map.put("totalMoney", bg.doubleValue());
        return map;
    }

    /**
     * @Description: 获取用电量统计
     * @author: lzh
     * @Param: [fromDate, toDate, pagination]
     * @Date: 13:00
     */
    @Override
    public Map<String, Object> getElectricityStatisticsGroupByUserId(Date fromDate, Date toDate, Pagination pagination) {
        Map<String, Object> map = new HashMap<>();
        int pageSize = pagination.getPageSize();
        int offset = (pagination.getCurrentPage() - 1) * pageSize;
        String whereSql = sqlForPage(fromDate, toDate, PriceType.ELECTRICITY.toString());
        String whereSql2 = sqlForPage(fromDate, toDate, pageSize, offset, PriceType.ELECTRICITY.toString());
        //获取单价
        double price = priceBiz.getPriceByType(PriceType.ELECTRICITY.toString());
        //自定义分页数据
        //没有分页的sql语句，求总数的
        List<Electricity> electricities = electricityBiz.find(null, whereSql);
        //分页过后的sql语句，需要传当前页和每页数量
        List<Electricity> electricities1 = electricityBiz.find(null, whereSql2);
        List<ElectricityDto> electricityDtos = electricities1.stream()
                .map(electricity -> {
                    return electricityBiz.convertWaterToDto(electricity, price);
                })
                .collect(Collectors.toList());
        int totalSize = electricities.size();
        double totalElectricity = 0.0;
        for (Electricity electircity : electricities1) {
            totalElectricity += electircity.getDegrees();
        }
        //将分页数据初始化
        pagination.init(totalSize, pageSize, pagination.getCurrentPage());
        BigDecimal bg = new BigDecimal(price * totalElectricity).setScale(2, RoundingMode.UP);

        map.put("pagination", pagination);
        map.put("electricityDtos", electricityDtos);
        map.put("totalElectricity", totalElectricity);
        map.put("price", price);
        map.put("totalMoney", bg.doubleValue());
        return map;
    }

    /**
     * @Description: 获取用水量统计
     * @author: lzh
     * @Param: [fromDate, toDate, pagination]
     * @Date: 15:29
     */
    @Override
    public Map<String, Object> getWaterStatistics(Date start, Date end, Pagination pagination) {
        Map<String, Object> data = new HashMap<>(4);
        String sql = compareTime(start, end);
        List<Water> electricityList = waterBiz.find(pagination, sql);
        Map<Integer, Map<Integer, Double>> waterMap = electricityList.parallelStream()
                .collect(Collectors.groupingBy(Water::getYear, /* 按年分组 */
                        Collectors.groupingBy(Water::getMonth, /* 按月分组 */
                                Collectors.reducing(0.0, Water::getTunnage, Double::sum) /* 分组求和 */
                        )));
        //查询水价
        Double price = priceBiz.getPriceByType(PriceType.WATER.toString());
        data.putAll(computePerMonthEnergy(price, waterMap));
        data.put("pagination", pagination);
        return data;
    }

    /**
     * @Description: 获取用电量统计
     * @author: lzh
     * @Param: [fromDate, toDate, pagination]
     * @Date: 15:30
     */
    @Override
    public Map<String, Object> getElectricityStatistics(Date start, Date end, Pagination pagination) {
        Map<String, Object> data = new HashMap<>(4);
        String sql = compareTime(start, end);
        List<Electricity> electricityList = electricityBiz.find(pagination, sql);
        Map<Integer, Map<Integer, Double>> electricityMap = electricityList.parallelStream()
                .collect(Collectors.groupingBy(Electricity::getYear, /* 按年分组 */
                        Collectors.groupingBy(Electricity::getMonth, /* 按月分组 */
                                Collectors.reducing(0.0, Electricity::getDegrees, Double::sum) /* 分组求和 */
                        )));
        //查询电价
        Double price = priceBiz.getPriceByType(PriceType.ELECTRICITY.toString());
        data.putAll(computePerMonthEnergy(price, electricityMap));
        data.put("pagination", pagination);
        return data;
    }

    /**
     * 初始化添加维修页面
     */
    @Override
    public String toSaveRepair() {
        List<RepairType> repairTypeList = repairTypeBiz.getAllRepairType();
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(repairTypeList);
    }

    /**
     * 添加报修申请
     *
     * @param param
     */
    @Override
    public String saveRepair(String param) {
        //Repair repair = new Repair();
        // 获取页面的参数
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Repair repair = gson.fromJson(param, Repair.class);
        repairBiz.save(repair);
        return ErrorCode.SUCCESS;
    }

    /**
     * 查询个人报修
     */
    @Override
    public Map<String, String> getRepairsDtos(String repairJson, Pagination pagination) {
        Map<String, String> result = new HashMap<>();
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Repair repair = gson.fromJson(repairJson, Repair.class);
        List<RepairDto> repairList = repairBiz.getRepairsDtos(pagination, repair);

        result.put("repairList", gson.toJson(repairList));
        result.put("pagination", gson.toJson(pagination));
        return result;
    }

    /**
     * 查看报修详情
     *
     * @param id 保修单ID
     */
    @Override
    public String getRepairDtosById(Long id) {
        RepairDto repair = repairBiz.getRepairDtosById(id);
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(repair);
    }

    /**
     * 获取所有学员的报修申请
     *
     * @param pagination 分页对象
     * @return Json
     */
    @Override
    public Map<String, String> getAllStudentRepairList(String repairJson, Pagination pagination) {
        Map<String, String> result = new HashMap<>();
        // 根据调价查询维修列表
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Repair repair = gson.fromJson(repairJson, Repair.class);
        List<RepairDto> repairList = repairBiz.getRepairsDtos(pagination, repair);
        if (ObjectUtils.isNotNull(repairList)) {
            for (RepairDto aRepairList : repairList) {
                SysUser sysUser = baseHessianBiz.getSysUserById(aRepairList.getPepairPeopleId());
                aRepairList.setSysUser(sysUser);
            }
        }
        result.put("repairList", gson.toJson(repairList));
        result.put("pagination", gson.toJson(pagination));
        return result;
    }

    /**
     * @Description: 处理时间
     * @author: lzh
     * @Param: [fromDate, toDate]
     * @Date: 9:28
     */
    private String sqlForPage(Date fromDate, Date toDate, String type) {
        return dealSqlForDate(fromDate, toDate, type);
    }

    /**
     * @Description: 重载分页
     * @author: lzh
     * @Param: [fromDate, toDate, pageSize, offset]
     * @Date: 11:01
     */
    private String sqlForPage(Date fromDate, Date toDate, int pageSize, int offset, String type) {
        String sql = dealSqlForDate(fromDate, toDate, type);
        sql += " limit " + offset + ", " + pageSize;
        return sql;
    }

    /**
     * @Description: 根据时间判断
     * @author: lzh
     * @Param: [fromDate, toDate]
     * @Date: 11:01
     */
    private String dealSqlForDate(Date fromDate, Date toDate, String type) {
        String sql = "";
        if (PriceType.WATER.toString().equals(type)) {
            sql = " 1 = 0 union select id, userId, sum(tunnage),context, status, createTime, updateTime from water where";
        }
        if (PriceType.ELECTRICITY.toString().equals(type)) {
            sql = " 1 = 0 union select id, userId, sum(degrees),context, status, createTime, updateTime from electricity where";
        }
        sql += compareTime(fromDate, toDate);
        return sql + " group by userId";
    }

    private String compareTime(Date fromDate, Date toDate) {
        String sql = " 1 = 1";
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH::mm:ss");
        if (fromDate == null && toDate == null) {
        } else if (fromDate != null && toDate == null) {
            String fDate = smf.format(fromDate);
            sql += " and createTime >= '" + fDate + "'";
        } else if (fromDate == null && toDate != null) {
            String tDate = smf.format(toDate);
            sql += " and createTime <= '" + tDate + "'";
        } else {
            String fDate = smf.format(fromDate);
            String tDate = smf.format(toDate);
            sql += " and createTime >= '" + fDate + "' and createTime <= '" + tDate + "'";
        }
        return sql;
    }

    /**
     * 计算每月水电用量及支出
     *
     * @param price 水电价格
     * @param data  起止时间内的数据
     * @return 每月用量、支出及起止时间内的总用量、总支出
     * @since 2017-03-02
     */
    private Map<String, Object> computePerMonthEnergy(Double price,
                                                      Map<Integer, Map<Integer, Double>> data) {
        Map<String, Object> result = new HashMap<>(3);
        if (null != price && price > 0) {
            double[] total = new double[2]; /* 总量、总费用 */
            List<Map<String, Object>> perMonthList = new LinkedList<>();
            new TreeMap<>(data).descendingMap().keySet().forEach(i -> { /* 按年遍历 */
                Map<Integer, Double> temp = new TreeMap<>(data.get(i)).descendingMap();
                temp.keySet().forEach(j -> { /* 按月遍历 */
                    Map<String, Object> perMonthMap = new HashMap<>(1);
                    double perElectricity = temp.getOrDefault(j, 0.0); /* 每月用量 */
                    double perExpense = new BigDecimal(perElectricity * price)
                            .setScale(2, BigDecimal.ROUND_UP).doubleValue();
                    total[0] += perElectricity; /* 总用量 */
                    total[1] += perExpense; /* 总费用 */
                    perMonthMap.put(i + "-" + (j < 10 ? "0" + j : j), Arrays.asList(perElectricity, perExpense));
                    perMonthList.add(perMonthMap);
                });
            });
            result.put("totalConsume", total[0]);
            result.put("totalExpense", total[1]);
            result.put("perMonthList", perMonthList);
        }
        return result;
    }


    /**
     * 查询所有的会场
     *
     * @return
     */
    @Override
    public List<Map<String, String>> queryAllMeeting() {
        List<Meeting> meetingList = meetingBiz.find(null, " 1=1");
        return ObjectUtils.listObjToListMap(meetingList);
    }

    @Override
    public Map<String, String> queryStudentRepairRecord(String join, Pagination pagination) {
        Map<String, String> rs = new HashMap<>(16);

        try {
            List<Repair> repairList = repairBiz.find(pagination, "userId in (" + join + ")");
            if (ObjectUtils.isNotNull(repairList)) {
                for (Repair repair : repairList) {
                    SysUser sysUser = baseHessianBiz.getSysUserById(repair.getUserId());
                    repair.setSysUser(sysUser);
                }
            }
            rs.put("repairList", gson.toJson(repairList));
            rs.put("pagination", gson.toJson(pagination));
        } catch (Exception e) {
            log.error("HqHessianBiz.queryStudentRepairRecord", e);
        }
        return rs;
    }

    @Override
    public Map<String, Object> queryHqAllMeeting(Pagination pagination, @RequestParam("name") String name) {
        Map<String, Object> result = new HashMap<>(3);
        Meeting meeting = new Meeting();
        if (!StringUtils.isEmpty(name)) {
            meeting.setName(name);
        }
        // 会场未使用的
        meeting.setStatus(0);
        String whereSql = GenerateSqlUtil.getSql(meeting);
        pagination.setPageSize(10);
        List<Meeting> meetingList = meetingBiz.find(pagination, whereSql);
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(meetingList);
        Map<String, String> objMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<>();
        map.put("meetingList", listMap);
        map.put("pagination", objMap);
        result.put("meeting", meeting);
        return map;
    }

    /**
     * 查看会场详情
     *
     * @param id 保修单ID
     */
    @Override
    public Map<String, String> getMeetingForOAById(Long id) {
        Map<String, String> result = new HashMap<>();
        Meeting meeting = meetingBiz.findById(id);
        if (ObjectUtils.isNotNull(meeting)) {
            result.put("id", meeting.getId().toString());
            result.put("name", meeting.getName());
            result.put("useTime", meeting.getUseTime().toString());
            result.put("turnTime", meeting.getTurnTime().toString());
        }
        return result;
    }

    /**
     * 查看最新会场记录
     *
     * @param id 会场ID
     */
    @Override
    public Map<String, String> getMeetingRecordForOAById(Long id) {
        Map<String, String> result = new HashMap<>();
        MeetingRecord meetingRecord = new MeetingRecord();
        String whereSql = GenerateSqlUtil.getSql(meetingRecord);
        whereSql += " and meetingId=" + id + " order by id desc";
        List<MeetingRecord> meetingRecordList = meetingRecordBiz.find(null, whereSql);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (ObjectUtils.isNotNull(meetingRecordList)) {
            for (MeetingRecord m : meetingRecordList) {
                SysUser sysUser = baseHessianBiz.getSysUserById(m.getUserId());
                m.setUserName(sysUser.getUserName());
            }
            MeetingRecord _meetingRecord = meetingRecordList.get(0);
            result.put("id", _meetingRecord.getId().toString());
            result.put("classesId", _meetingRecord.getClassesId().toString());
            result.put("classesName", _meetingRecord.getClassesName().toString());
            result.put("meetingId", _meetingRecord.getMeetingId().toString());

            result.put("useTime", formatter.format(_meetingRecord.getUseTime()));
            result.put("turnTime", formatter.format(_meetingRecord.getTurnTime()));
        }
        return result;
    }

    @Override
    public List<Map<String, String>> queryMeetingList() {
        List<Meeting> meetingList = meetingBiz.find(null, " 1=1 and status != 1");
        return ObjectUtils.listObjToListMap(meetingList);
    }

    @Override
    public List<Map<String, String>> queryMeetingListByTimeOrName(String useTime, String turnTime, String name) {
        String sql;
        if (!StringUtils.isEmpty(name)) {
            sql = " 1=1 and status != 1 and name like'%" + name + "%'";
        } else {
            sql = " 1=1 and status != 1";
        }
        List<Meeting> meetingList = meetingBiz.find(null, sql);
        return ObjectUtils.listObjToListMap(meetingList);
    }

    /**
     * 选择会场前，排查会场使用状态
     */
    @Override
    public void changeMeetingStatusWhenChose() {
        meetingRecordBiz.changeMeetingStatusWhenChose();
    }

    /**
     * 修改会场的状态
     *
     * @param id
     * @param status 使用状态 0未使用正常  1是维修中 2是使用者中
     * @return
     */
    @Override
    public Map<String, String> updateMeetingStatus(Long id, Integer status) {
        Map<String, String> result = new HashMap<>(2);
        Meeting meetingById = meetingBiz.findById(id);
        meetingById.setStatus(status);
        meetingBiz.update(meetingById);
        result.put("code", "0");
        result.put("message", "修改成功");
        return result;
    }

    /**
     * 添加会场使用记录
     *
     * @param userId      用户id
     * @param meetingId   会场id
     * @param useTime     会场使用时间
     * @param turnTime    会场结束时间
     * @param description 备注说明
     * @param status      状态 0正常 1取消
     * @param classesName 班级名称
     * @param classesId   班级id
     * @return
     */
    @Override
    public Map<String, String> addMeetingRecordFromOther(Long userId, Long meetingId, Integer status, Long classesId, String classesName, Date useTime, Date turnTime, String description) {
        Map<String, String> result = new HashMap<>(1);
        MeetingRecord meetingRecord = new MeetingRecord();
        if (ObjectUtils.isNotNull(userId)) {
            meetingRecord.setUserId(userId);
        }
        if (ObjectUtils.isNotNull(meetingId)) {
            meetingRecord.setMeetingId(meetingId);
        }
        meetingRecord.setStatus(status);
        if (ObjectUtils.isNotNull(classesId)) {
            meetingRecord.setClassesId(classesId);
        }
        if (StringUtils.isTrimEmpty(classesName)) {
            meetingRecord.setClassesName(classesName);
        }
        if (ObjectUtils.isNotNull(useTime)) {
            meetingRecord.setUseTime(useTime);
        }
        if (ObjectUtils.isNotNull(turnTime)) {
            meetingRecord.setTurnTime(turnTime);
        }
        if (StringUtils.isTrimEmpty(description)) {
            meetingRecord.setDescription(description);
        }
        meetingRecordBiz.save(meetingRecord);
        result.put("meetingRecordId", meetingRecord.getId().toString());
        return result;
    }

    /**
     * 修改会场使用记录的状态
     *
     * @param id          会场记录id
     * @param meetingId   会场id
     * @param status      状态 0正常 1取消
     * @param classesId   班级id
     * @param classesName 班级名称
     * @param useTime     会场使用时间
     * @param turnTime    会场结束时间
     * @param description 备注说明
     * @return
     */
    @Override
    public Map<String, String> updateMeetingRecordStatus(Long id, Long meetingId, Integer status, Long classesId, String classesName, Date useTime, Date turnTime, String description) {
        Map<String, String> result = new HashMap<>(2);
        MeetingRecord meetingRecord = meetingRecordBiz.findById(id);
        if (ObjectUtils.isNotNull(meetingId)) {
            meetingRecord.setMeetingId(meetingId);
        }
        meetingRecord.setStatus(status);
        if (ObjectUtils.isNotNull(classesId)) {
            meetingRecord.setClassesId(classesId);
        }
        if (!StringUtils.isTrimEmpty(classesName)) {
            meetingRecord.setClassesName(classesName);
        }
        if (ObjectUtils.isNotNull(useTime)) {
            meetingRecord.setUseTime(useTime);
        }
        if (ObjectUtils.isNotNull(turnTime)) {
            meetingRecord.setTurnTime(turnTime);
        }
        if (!StringUtils.isTrimEmpty(description)) {
            meetingRecord.setDescription(description);
        }
        meetingRecordBiz.update(meetingRecord);
        result.put("code", "0");
        result.put("meetingRecord", meetingRecord.toString());
        return result;
    }

    /**
     * 判断这个时间段是否有使用的,添加操作不用传meetingRecordId，修改需要。别的参数都需要
     *
     * @param meetingRecordId 会场记录id
     * @param meetingId       会场id
     * @param useTime         会场使用时间
     * @param turnTime        会场结束时间
     * @param operation       ADD,UPDATE
     * @return true 有冲突，false没有冲突
     */
    @Override
    public Map<String, Boolean> whetherToUseDuringTheTimePeriod(Long meetingRecordId, Long meetingId, Date useTime, Date turnTime, String operation) {
        Map<String, Boolean> result = new HashMap<>(1);
        MeetingRecord meetingRecord = new MeetingRecord();
        if (ObjectUtils.isNotNull(meetingRecordId)) {
            meetingRecord.setId(meetingRecordId);
        }
        if (ObjectUtils.isNotNull(meetingId)) {
            meetingRecord.setMeetingId(meetingId);
        }
        if (ObjectUtils.isNotNull(useTime)) {
            meetingRecord.setUseTime(useTime);
        }
        if (ObjectUtils.isNotNull(turnTime)) {
            meetingRecord.setTurnTime(turnTime);
        }
        boolean theTimePeriod = meetingRecordBiz.whetherToUseDuringTheTimePeriod(meetingRecord, operation);
        result.put("whetherUse", theTimePeriod);
        return result;
    }


}
