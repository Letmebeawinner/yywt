package com.houqin.biz.electricityType;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.BaseEntity;
import com.a_268.base.core.Pagination;
import com.houqin.dao.electricityType.EleSecTypeDao;
import com.houqin.entity.electricityType.EleSecType;
import com.houqin.entity.electricityType.EleSecTypeVO;
import com.houqin.entity.electricityType.ElectricityType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 二级用电区域
 *
 * @author YaoZhen
 * @date 06-25, 13:50, 2018.
 */
@Service
public class EleSecTypeBiz extends BaseBiz<EleSecType, EleSecTypeDao> {

    @Autowired private ElectricityTypeBiz electricityTypeBiz;

    public List<EleSecTypeVO> findVOS(Pagination pagination, String whereSql) {
        List<EleSecType> types = this.find(pagination, whereSql + " and status = 0");
        return convert(types);
    }

    private List<EleSecTypeVO> convert(List<EleSecType> types) {
        List<ElectricityType> electricityTypes = electricityTypeBiz.findAll();
        Map<Long, String> parentMap = electricityTypes.stream()
                .collect(Collectors.toMap(BaseEntity::getId, ElectricityType::getType));

        return types.stream().map(e -> {
            EleSecTypeVO vo = new EleSecTypeVO();
            BeanUtils.copyProperties(e, vo);
            vo.setEleTypeName(parentMap.get(e.getEleTypeId()));
            return vo;
        }).collect(Collectors.toList());
    }

    public Map<Long, String> findAllMap() {
        List<EleSecType> secTypes = super.findAll();
        return secTypes.stream().collect(Collectors.toMap(BaseEntity::getId, EleSecType::getTypeName));
    }
}
