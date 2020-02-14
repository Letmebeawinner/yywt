package com.yicardtong.entity;

import com.a_268.base.core.BaseDao;

/**
 * Created by caichenglong on 2017/10/29.
 */
public interface WorkSmailDao extends BaseDao<WorkSmail> {


    void addWork(WorkSmail workSmail);


    void deleteWork(String Base_OperCode);

}
