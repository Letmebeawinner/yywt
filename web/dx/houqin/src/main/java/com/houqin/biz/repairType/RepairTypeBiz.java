package com.houqin.biz.repairType;

import com.a_268.base.core.BaseBiz;
import com.houqin.dao.repairType.RepairTypeDao;
import com.houqin.entity.repairType.RepairType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报修类型Biz
 * @author ccl
 */
@Service
public class RepairTypeBiz extends BaseBiz<RepairType, RepairTypeDao>{

    public List<RepairType> getAllRepairType() {
        List<RepairType> repairTypeList = this.find(null,"1=1");
        return repairTypeList;
    }

    public List<RepairType> getRepairType(int department) {
        List<RepairType> repairTypeList = this.find(null,"1=1 and functionType="+department);
        return repairTypeList;
    }


}
