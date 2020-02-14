package com.renshi.biz.attendance;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.dao.attendance.MeetAttendanceDao;
import com.renshi.entity.attendance.MeetAttendance;
import com.renshi.entity.attendance.QueryMeetAttendance;
import com.renshi.entity.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议考勤Biz
 *
 * @author 268
 */
@Service
public class MeetAttendanceBiz extends BaseBiz<MeetAttendance, MeetAttendanceDao> {
    @Autowired
    private EmployeeBiz employeeBiz;

    /**
     * 会议考勤列表
     *
     * @param queryMeetAttendance
     */
    public List<QueryMeetAttendance> getMeetAttendanceList(QueryMeetAttendance queryMeetAttendance, Pagination pagination) {
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        Long id = queryMeetAttendance.getId();
        if (id!=null && id > 0) {
            whereSql += " and id=" + id;
        }
        Long employeeId = queryMeetAttendance.getEmployeeId();
        if (employeeId!=null && employeeId > 0) {
            whereSql += " and employeeId=" + employeeId;
        }
        String epmloyeeName=queryMeetAttendance.getEmployeeName();
        if (!StringUtils.isTrimEmpty(epmloyeeName)) {
            String ids="";
            String _whereSql="name like '%" + epmloyeeName + "%'";
            List<Employee> employeeList=employeeBiz.find(null,_whereSql);
            if(!CollectionUtils.isEmpty(employeeList)){
                for(Employee employee:employeeList){
                    ids+=employee.getId().toString()+' ';
                }
                ids=ids.trim().replace(' ',',');
            }else{
                return  null;
            }
            whereSql+=" and employeeId in("+ids+")";
        }
        if (queryMeetAttendance.getSignInTime()!=null) {
            whereSql += " and left(createTime,10) = " + DateUtils.format(queryMeetAttendance.getSignInTime(),"yyyy-MM-dd");
        }
        String meetName=queryMeetAttendance.getMeetName();
        if(!StringUtils.isTrimEmpty(meetName)){
            whereSql += " and meetName like '" +meetName+"'";
        }
        whereSql += " order by id desc";
        List<MeetAttendance> list=this.find(pagination,whereSql);
        List<QueryMeetAttendance> meetAttendanceList=new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            return null;
        }else{
            for(MeetAttendance meetAttendance:list){
                QueryMeetAttendance _queryMeetAttendance=new QueryMeetAttendance(meetAttendance);
                Employee employee=employeeBiz.findById(meetAttendance.getEmployeeId());
                if(ObjectUtils.isNotNull(employee)){
                    _queryMeetAttendance.setEmployeeName(employee.getName());
                }
                meetAttendanceList.add(_queryMeetAttendance);
            }
        }
        return meetAttendanceList;
    }
}
