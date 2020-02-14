package com.oa.biz.duty;

import com.a_268.base.core.BaseBiz;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.dao.duty.DutyDao;
import com.oa.entity.duty.Duty;
import com.oa.entity.sysuser.SysUser;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 值班业务层
 *
 * @author lzh
 * @create 2017-01-07-15:50
 */
@Service
public class DutyBiz extends BaseBiz<Duty, DutyDao>{
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    /**
     * @Description:将值班列表进行数据转换，取其中的title和名字
     * @author: lzh
     * @Param: [duties]
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Date: 11:35
     */
    public List<Map<String, Object>> getDuties(List<Duty> duties) {
        if (duties == null || duties.size() == 0) {
            return null;
        }
        List<Map<String, Object>> lists = duties
              .parallelStream()
              .map(duty -> this.getUserNameById(duty.getSysUserId(), duty.getDutyTime(), duty.getId()))
              .collect(Collectors.toList());
        return lists;
    }

    /**
     * @Description: 根据id查询到对应的用户
     * @author: lzh
     * @Param: [id],[dutyId]
     * @Return: java.lang.map
     * @Date: 11:36
     */
    public Map<String, Object> getUserNameById(Long id, Date beginTime, Long dutyId) {
        Map<String, Object> map = new HashMap<>();
        SysUser sysUser = baseHessianBiz.getSysUserById(id);
        map.put("title", sysUser.getUserName());
        map.put("sysUserId", id);
        map.put("start", beginTime);
        map.put("dutyId", dutyId);
        return map;
    }
}
