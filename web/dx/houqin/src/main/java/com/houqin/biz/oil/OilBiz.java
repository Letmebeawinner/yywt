package com.houqin.biz.oil;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.dao.oil.OilDao;
import com.houqin.entity.oil.Oil;
import com.houqin.entity.oil.OilDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
@Service
public class OilBiz extends BaseBiz<Oil, OilDao>{

    @Autowired
    private BaseHessianBiz baseHessianBiz;

    public List<OilDto> getOilDtoList(Pagination pagination, String whereSql) {
        List<Oil> oilList = this.find(pagination,whereSql);
        List<OilDto> oilDtoList = new ArrayList<>();
        if(oilList.size() > 0) {
            for (Oil o : oilList) {
                OilDto oilDto = new OilDto();
                // 查询录入人名称
                String userName = baseHessianBiz.getSysUserById(o.getUserId()).getUserName();
                if (userName != null && !"".equals(userName)) {
                    // 拼装拓展对象
                    oilDto = convertOilToDto(o, userName);
                }
                oilDtoList.add(oilDto);
            }
        }
        return oilDtoList;
    }

    /**
     * 拓展柴油类
     * @param oil
     * @param userName
     * @return
     */
    public OilDto convertOilToDto(Oil oil, String userName) {
        OilDto oilDto = new OilDto();
        BeanUtils.copyProperties(oil, oilDto);
        oilDto.setUserName(userName);
        return oilDto;
    }
}
