package com.oa.biz.telephone;

import com.a_268.base.core.BaseBiz;
import com.oa.dao.telephone.TelephoneDao;
import com.oa.entity.telephone.Telephone;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 电话本
 *
 * @author ccl
 * @create 2016-12-28-16:04
 */
@Service
public class TelephoneBiz extends BaseBiz<Telephone,TelephoneDao> {

    public List<Telephone> telephoneList(){
        List<Telephone> telephoneList=this.find(null,"1=1");
        return telephoneList;
    }

}
