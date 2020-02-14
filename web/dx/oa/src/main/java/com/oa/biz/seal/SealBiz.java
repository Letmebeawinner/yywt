package com.oa.biz.seal;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.oa.dao.seal.SealDao;
import com.oa.entity.seal.Seal;
import com.oa.entity.seal.SealDto;
import com.oa.entity.seal.SealFunction;
import com.oa.entity.seal.SealType;
import com.oa.utils.GenerateSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 印章业务
 *
 * @author lzh
 * @create 2016-12-28-17:22
 */
@Service
public class SealBiz extends BaseBiz<Seal, SealDao> {
    @Autowired
    private SealTypeBiz sealTypeBiz;
    @Autowired
    private SealFunctionBiz sealFunctionBiz;

    /**
     * @Description:
     * @author: lzh
     * @Param: [pagination, sealDto]
     * @Return: java.util.List<com.oa.entity.seal.SealDto>
     * @Date: 9:43
     */
    public List<SealDto> getSealDtos(Pagination pagination, Seal sealEntity) {
        String whereSql = GenerateSqlUtil.getSql(sealEntity);
        List<Seal> seals = this.find(pagination, whereSql);
        List<SealDto> sealDtos = null;
        sealDtos = seals.stream()
                        .map(seal -> convertSealToDto(seal))
                        .collect(Collectors.toList());
        return sealDtos;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: [seal]
     * @Return: com.oa.entity.seal.SealDto
     * @Date: 11:40
     */
    private SealDto convertSealToDto(Seal seal) {
        SealDto sealDto = new SealDto();
        BeanUtils.copyProperties(seal, sealDto);
        if (sealDto.getSealTypeId() != null) {
            SealType sealType = sealTypeBiz.findById(sealDto.getSealTypeId());
            if (sealType != null) {
                sealDto.setSealTypeName(sealType.getName());
            }
        }
        if (sealDto.getSealFunctionId() != null) {
            SealFunction sealFunction = sealFunctionBiz.findById(sealDto.getSealFunctionId());
            if (sealFunction != null) {
                sealDto.setSealFunctionName(sealFunction.getName());
            }
        }
        return sealDto;
    }

}
