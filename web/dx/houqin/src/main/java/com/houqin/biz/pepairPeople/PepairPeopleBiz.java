package com.houqin.biz.pepairPeople;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.houqin.dao.pepairPeople.PepairPeopleDao;
import com.houqin.entity.pepairPeople.PepairPeople;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 维修人员
 *
 * @author wanghailong
 * @create 2017-06-08-下午 3:02
 */
@Service
public class PepairPeopleBiz extends BaseBiz<PepairPeople,PepairPeopleDao> {

    /**
     * 事务管理先删除后增加
     * @param functionIds
     */
    public void tx_addPeopaorPeople(String functionIds){
//        List<PepairPeople> pepairPeopleList = this.findAll();
//        if (ObjectUtils.isNotNull(pepairPeopleList)) {
//            List listIds = new ArrayList();
//            for (int i = 0; i < pepairPeopleList.size(); i++) {
//                listIds.add(pepairPeopleList.get(i).getId());
//            }
//            this.deleteByIds(listIds);
//        }
        String[] ids = functionIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            PepairPeople pepairPeople = new PepairPeople();
            pepairPeople.setUserId(Long.parseLong(ids[i].toString()));
            this.save(pepairPeople);
        }
    }
}
