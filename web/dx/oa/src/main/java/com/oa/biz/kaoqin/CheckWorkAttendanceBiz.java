package com.oa.biz.kaoqin;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.common.JiaoWuHessianService;
import com.oa.dao.kaoqin.CheckWorkAttendanceDao;
import com.oa.entity.department.DepartMent;
import com.oa.entity.kaoqin.CheckWorkAttendance;
import com.oa.entity.sysuser.SysUser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by xiangdong.chang on 2018/5/16 0016.
 */
@Service
public class CheckWorkAttendanceBiz extends BaseBiz<CheckWorkAttendance, CheckWorkAttendanceDao> {
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;

    /**
     * 读取导出考勤
     *
     * @param myfile 文档流
     * @author: xiangdong.chang
     * @create: 2018/5/18 0018 10:44
     * @return:
     */
    public void importAttendanceSheet(MultipartFile myfile, Long departmentId, String attendanceName) throws Exception {
        Calendar now = Calendar.getInstance();
        //年
        String year = String.valueOf(now.get(Calendar.YEAR));
        //月
        String month = String.valueOf(now.get(Calendar.MONTH));
        if (month.length() < 2) {
            month = "0" + month;
        }
        HSSFWorkbook workBook = new HSSFWorkbook(myfile.getInputStream());
        HSSFSheet sheet = workBook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();// 指的行数，一共有多少行+
        int a = 2;
        for (int i = 2; i <= rows - 2; i++) {
            List<CheckWorkAttendance> list = new ArrayList<>();
            HSSFRow userNameRow = sheet.getRow(a);
            a = a + 2;
            //获取用户名
            String userName = getCellValue(userNameRow.getCell((short) 0));
            Long userId = 0L;
            if (!"".equals(userName)) {
                SysUser sysUser = new SysUser();
                sysUser.setUserName(userName);
                //获取用户id
                List<SysUser> sysUserList = baseHessianBiz.getSysUserList(sysUser);
                if (ObjectUtils.isNotNull(sysUserList)) {
                    userId = baseHessianBiz.getSysUserList(sysUser).get(0).getId();
                }
                if(0== departmentId){
                    departmentId = sysUserList.get(0).getDepartmentId()==null?0:sysUserList.get(0).getDepartmentId();
                }
            }
            //从上午的行开始
            HSSFRow amRow = sheet.getRow(i);
            i = i + 1;
            //从下午的行开始
            HSSFRow pmRow = sheet.getRow(i);
            //上午
            if (amRow != null) {
                for (int j = 2; j <= 32; j++) {
                    CheckWorkAttendance checkWorkAttendance = new CheckWorkAttendance();
                    String am = getCellValue(amRow.getCell((short) j));// 上午
                    String addTime = year + "年" + month + "月" + String.valueOf(j - 1) + "日";
                    checkWorkAttendance.setUserName(userName);
                    checkWorkAttendance.setUserId(userId);
                    checkWorkAttendance.setAttendanceName(attendanceName);
                    checkWorkAttendance.setDepartmentId(departmentId);
                    if (!"".equals(am)) {
                        checkWorkAttendance.setAmAttendanceStatus(Integer.valueOf(am));
                    } else {
                        checkWorkAttendance.setAmAttendanceStatus(0);
                    }
                    checkWorkAttendance.setAddTime(addTime);
                    list.add(checkWorkAttendance);
                }
            }
            //下午
            if (pmRow != null) {
                for (int j = 2; j <= 32; j++) {
                    CheckWorkAttendance checkWorkAttendance = new CheckWorkAttendance();
                    String pm = getCellValue(pmRow.getCell((short) j));// 下午
                    String addTime = year + "年" + month + "月" + String.valueOf(j - 1) + "日";
                    checkWorkAttendance.setUserName(userName);
                    checkWorkAttendance.setUserId(userId);
                    checkWorkAttendance.setAttendanceName(attendanceName);
                    checkWorkAttendance.setDepartmentId(departmentId);
                    if (!"".equals(pm)) {
                        checkWorkAttendance.setPmAttendanceStatus(Integer.valueOf(pm));
                    } else {
                        checkWorkAttendance.setPmAttendanceStatus(0);
                    }
                    checkWorkAttendance.setAddTime(addTime);
                    list.add(checkWorkAttendance);
                }
            }
            this.saveBatch(list);
        }
    }

    /**
     * 获得Hsscell内容
     *
     * @param cell
     * @return
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
     * 查询考勤统计列表
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 13:39
     * @return:
     */
    public Map<String, Object> queryAttendanceList(CheckWorkAttendance checkWorkAttendance) {
        Map<String, Object> objectMap = new HashMap<>();
        //查询考勤人姓名
        String whereSql = "1=1";
        if (ObjectUtils.isNotNull(checkWorkAttendance.getDepartmentId())) {
            whereSql += " and departmentId=" + checkWorkAttendance.getDepartmentId();
        }
        if (ObjectUtils.isNotNull(checkWorkAttendance.getAttendanceName()) && !"".equals(checkWorkAttendance.getAttendanceName())) {
            whereSql += " and attendanceName like concat('%','" + checkWorkAttendance.getAttendanceName() + "','%')";
        }
        if (ObjectUtils.isNotNull(checkWorkAttendance.getAddTime()) && !"".equals(checkWorkAttendance.getAddTime())) {
            whereSql += " and left(addTime," + checkWorkAttendance.getAddTime().length() + ") =" + "\'" + checkWorkAttendance.getAddTime() + "\'";
        }
        whereSql += " group by userName order by createTime";
        List<CheckWorkAttendance> checkWorkAttendances = this.find(null, whereSql);
        Map<String, List<CheckWorkAttendance>> dataMap = new HashMap<>();
        if (ObjectUtils.isNotNull(checkWorkAttendances)) {
            for (CheckWorkAttendance workAttendance : checkWorkAttendances) {
                //查询上午
                List<CheckWorkAttendance> amCheckWorkAttendanceList = this.find(null, "1=1  and userName=" + "'" + workAttendance.getUserName() + "'" + " and amAttendanceStatus!=0");
                Map<String, Integer> amMap = this.sum(amCheckWorkAttendanceList, "am");
                //当list大小小于31时增加长度到31
                if (amCheckWorkAttendanceList.size() < 31) {
                    int a = 31 - amCheckWorkAttendanceList.size();
                    for (int i = 0; i < a; i++) {
                        CheckWorkAttendance c = new CheckWorkAttendance();
                        c.setAmOutTime(0);
                        c.setAmRetreat(0);
                        c.setAmLeave(0);
                        c.setAmAbsenteeism(0);
                        amCheckWorkAttendanceList.add(c);
                    }
                }
                amCheckWorkAttendanceList.forEach(a -> {
                    a.setAmOutTime(amMap.get("amOutTime"));
                    a.setAmRetreat(amMap.get("amRetreat"));
                    a.setAmLeave(amMap.get("amLeave"));
                    a.setAmAbsenteeism(amMap.get("amAbsenteeism"));
                });
                dataMap.put(workAttendance.getUserName() + "am", amCheckWorkAttendanceList);
                List<CheckWorkAttendance> pmCheckWorkAttendanceList = this.find(null, "1=1  and userName=" + "'" + workAttendance.getUserName() + "'" + " and pmAttendanceStatus!=0");
                Map<String, Integer> pmMap = this.sum(pmCheckWorkAttendanceList, "pm");
                //当list大小小于31时增加长度到31
                if (pmCheckWorkAttendanceList.size() < 31) {
                    int p = 31 - pmCheckWorkAttendanceList.size();
                    for (int i = 0; i < p; i++) {
                        CheckWorkAttendance c = new CheckWorkAttendance();
                        c.setPmOutTime(0);
                        c.setPmRetreat(0);
                        c.setPmLeave(0);
                        c.setPmAbsenteeism(0);
                        pmCheckWorkAttendanceList.add(c);
                    }
                }
                pmCheckWorkAttendanceList.forEach(p -> {
                    p.setPmOutTime(pmMap.get("pmOutTime"));
                    p.setPmRetreat(pmMap.get("pmRetreat"));
                    p.setPmLeave(pmMap.get("pmLeave"));
                    p.setPmAbsenteeism(pmMap.get("pmAbsenteeism"));
                });
                //查询下午的
                dataMap.put(workAttendance.getUserName() + "pm", pmCheckWorkAttendanceList);
                DepartMent departMent = baseHessianBiz.queryDepartemntById(workAttendance.getDepartmentId());
                if (ObjectUtils.isNotNull(departMent)) {
                    workAttendance.setDepartmentName(departMent.getDepartmentName());
                }
            }
        }
        objectMap.put("checkWorkAttendances", checkWorkAttendances);
        objectMap.put("map", dataMap);
        return objectMap;
    }

    public Map<String, Integer> sum(List<CheckWorkAttendance> checkWorkAttendances, String type) {
        Map<String, Integer> map = new HashMap<>();
        int amOutTime = 0;//全勤
        int amRetreat = 0;//缺勤
        int amLeave = 0;//迟到早退
        int amAbsenteeism = 0;//加班

        int pmOutTime = 0;//全勤
        int pmRetreat = 0;//缺勤
        int pmLeave = 0;//迟到早退
        int pmAbsenteeism = 0;//加班
        if (ObjectUtils.isNotNull(checkWorkAttendances)) {
            for (CheckWorkAttendance attendance : checkWorkAttendances) {
                if ("am".equals(type)) {
                    if (attendance.getAmAttendanceStatus() == 1) {
                        amOutTime++;
                    }
                    if (attendance.getAmAttendanceStatus() == 2) {
                        amLeave++;
                    }
                    if (attendance.getAmAttendanceStatus() == 3 || attendance.getAmAttendanceStatus() == 4 || attendance.getAmAttendanceStatus() == 5 || attendance.getAmAttendanceStatus() == 6) {
                        amRetreat++;
                    }
                    if (attendance.getAmAttendanceStatus() == 8) {
                        amAbsenteeism++;
                    }
                } else {
                    if (attendance.getPmAttendanceStatus() == 1) {
                        pmOutTime++;
                    }
                    if (attendance.getPmAttendanceStatus() == 2) {
                        pmLeave++;
                    }
                    if (attendance.getPmAttendanceStatus() == 3 || attendance.getPmAttendanceStatus() == 4 || attendance.getPmAttendanceStatus() == 5 || attendance.getPmAttendanceStatus() == 6) {
                        pmRetreat++;
                    }
                    if (attendance.getPmAttendanceStatus() == 8) {
                        pmAbsenteeism++;
                    }
                }
            }
        }
        map.put("amOutTime", amOutTime);
        map.put("amRetreat", amRetreat);
        map.put("amLeave", amLeave);
        map.put("amAbsenteeism", amAbsenteeism);

        map.put("pmOutTime", pmOutTime);
        map.put("pmRetreat", pmRetreat);
        map.put("pmLeave", pmLeave);
        map.put("pmAbsenteeism", pmAbsenteeism);
        return map;
    }
}
