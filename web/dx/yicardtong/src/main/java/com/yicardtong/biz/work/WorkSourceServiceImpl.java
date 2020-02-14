package com.yicardtong.biz.work;

import com.a_268.base.core.Pagination;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yicardtong.dao.commons.GenericDaoImpl;
import com.yicardtong.dao.workdata.WorkDataDao;
import com.yicardtong.entity.attend.WorkLeaveInfo;
import com.yicardtong.entity.attend.WorkLeaveInfoEntity;
import com.yicardtong.entity.work.NewWorkSource;
import com.yicardtong.entity.work.WorkDayData;
import com.yicardtong.entity.work.WorkDayDetail;
import com.yicardtong.entity.work.WorkSourceMapper;
import com.yicardtong.entity.workdaydata.WorkDayDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤Service接口实现类
 *
 * @author s.li
 * @create 2017-02-24-13:40
 */
@Service("workSourceService")
public class WorkSourceServiceImpl extends GenericDaoImpl<Map<String, String>> implements WorkSourceService {

    @Autowired
    private WorkDataDao workDataDao;

    @Override
    public List<Map<String, String>> queryWorkSourceList(String sql) {
        return this.queryList(sql, new WorkSourceMapper());
    }

    @Override
    public List<Map<String, String>> queryWorkDayDataList(String sql) {
        return this.queryList(sql, new NewWorkSource());
    }

    @Override
    public List<Map<String, String>> queryWorkDayDetailList(String sql) {
        return this.queryList(sql, new WorkDayDetail());
    }

    /**
     * 查询请假登记表
     *
     * @param sql
     * @return
     */
    public List<Map<String, String>> queryWorkLeaveInfoList(String sql) {
        return this.queryList(sql, new WorkLeaveInfo());
    }

    /**
     * 添加请假登记记录
     *
     * @param workLeaveInfoEntity
     * @return
     */
    public int addWorkLeaveInfo(WorkLeaveInfoEntity workLeaveInfoEntity) {
        /*String sql = "insert into Work_Leave_Info(CID,Lea_Date,lea_Name,Lea_Type,Modify_User,Modify_Date,Define1,Define2,Base_PerID,Beg_Date,End_Date) values (?,?,?,?,?,?,?,?,?,?,?)";
        Object[] parameters = {workLeaveInfoEntity.getCid(),workLeaveInfoEntity.getLeaDate(),workLeaveInfoEntity.getLeaName(),workLeaveInfoEntity.getLeaType(),
                workLeaveInfoEntity.getModifyUser(),workLeaveInfoEntity.getModifyDate(),workLeaveInfoEntity.getDefine1(),workLeaveInfoEntity.getDefine2(),
                workLeaveInfoEntity.getBasePerId(),workLeaveInfoEntity.getBegDate(),workLeaveInfoEntity.getEndDate()};*/
        String sql = "insert into Work_Leave_Info(Lea_Date,lea_Name,Lea_Type,Modify_User,Base_PerID,Beg_Date,End_Date) values (convert(datetime,?,120),?,?,?,?,convert(datetime,?,120),convert(datetime,?,120))";
        Object[] parameters = {workLeaveInfoEntity.getLeaDate(), workLeaveInfoEntity.getLeaName(), workLeaveInfoEntity.getLeaType(),
                workLeaveInfoEntity.getModifyUser(), workLeaveInfoEntity.getBasePerId(), workLeaveInfoEntity.getBegDate(), workLeaveInfoEntity.getEndDate()};
        return this.insert(sql, parameters);
//        return this.insert("insert into Work_Leave_Info(Lea_Date,lea_Name,Lea_Type,Modify_User,Base_PerID,Beg_Date,End_Date) values(convert(datetime,'2017-10-23',120),'生病','病假','admin','0000000221',convert(datetime,'2017-10-24',120),convert(datetime,'2017-10-25',120))",null);
    }

    @Override
    public String queryAllWordData(String sql, String strStart, String strEnd) {
        strStart = "'" + strStart + "'";
        strEnd = "'" + strEnd + "'";
        List<WorkDayDataVO> workDayDataVOS = workDataDao.queryAllWorkData(sql, strStart, strEnd);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(workDayDataVOS);
    }

    @Override
    public Map<String, String> queryAllWordDataByPage(Pagination page, String sql, String strStart, String strEnd) {
        Map<String, String> rs = new HashMap<>();


        Map<String, Object> param = new HashMap<>(16);
        param.put("ids", sql);

        strStart = "'" + strStart + "'";
        strEnd = "'" + strEnd + "'";
        param.put("start", strStart);
        param.put("end", strEnd);

        // 当前页之前的所有数据
        int offSet = page.getPageSize() * page.getCurrentPage();
        // 当前页的前一页 之前的所有数据
        int endSet = page.getPageSize() * (page.getCurrentPage() - 1);
        param.put("offSet", offSet);
        param.put("endSet", endSet);

        List<WorkDayDataVO> workDayDataVOS = workDataDao.queryAllWorkDataByPage(param);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        rs.put("list", gson.toJson(workDayDataVOS));

        // 结果总数
        int count = workDataDao.countQueryAllWorkDataByPage(sql);
        rs.put("count", String.valueOf(count));


        return rs;
    }
}
