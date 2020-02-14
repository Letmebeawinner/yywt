package com.information.dao;

import com.a_268.base.core.BaseDao;
import com.information.entity.InfoClass;

import java.util.List;

public interface InfoClassDao extends BaseDao<InfoClass> {


    public List<InfoClass>  queryInfoClassList();

}
