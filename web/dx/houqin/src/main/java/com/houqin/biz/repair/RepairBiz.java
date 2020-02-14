package com.houqin.biz.repair;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.repairType.RepairTypeBiz;
import com.houqin.dao.repair.RepairDao;
import com.houqin.entity.repair.Repair;
import com.houqin.entity.repair.RepairDto;
import com.houqin.entity.repairType.RepairType;
import com.houqin.entity.sysuser.SysUser;
import com.houqin.utils.GenerateSqlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 报修管理
 *
 * @author ccl
 * @create 2016-12-10-18:03
 */
@Service
public class RepairBiz extends BaseBiz<Repair, RepairDao> {

    @Autowired
    private RepairTypeBiz repairTypeBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    public RepairDto getRepairDtosById(Long id) {
        RepairDto repairDto = new RepairDto();
        Repair repair = this.findById(id);
        if (repair != null) {
            SysUser sysUser = baseHessianBiz.getSysUserById(repair.getUserId());
            if (ObjectUtils.isNotNull(sysUser)) {
                repairDto.setUserName(sysUser.getUserName());
            }

            RepairType repairType = repairTypeBiz.findById(repair.getTypeId());
            if (ObjectUtils.isNotNull(repairType)) {
                repairDto.setTypeName(repairType.getName());
            }

            if (ObjectUtils.isNull(repair.getTechId())) {
                SysUser sysUser1 = baseHessianBiz.getSysUserById(repair.getTechId());
                if (ObjectUtils.isNotNull(sysUser1)) {
                    repairDto.setTechName(sysUser1.getUserName());
                }
            }

            if (ObjectUtils.isNotNull(repair.getPepairPeopleId())) {
                SysUser sysUser1 = baseHessianBiz.getSysUserById(repair.getPepairPeopleId());
                repairDto.setSysUser(sysUser1);
            }

        }
        repairDto.setStatus(repair.getStatus());
        repairDto.setName(repair.getName());
        repairDto.setContext(repair.getContext());
        repairDto.setNumber(repair.getNumber());
        repairDto.setRepairTime(repair.getRepairTime());
        repairDto.setResult(repair.getResult());
        repairDto.setId(repair.getId());
        repairDto.setResponseTime(repair.getResponseTime());
        repairDto.setQuality(repair.getQuality());
        repairDto.setAttitude(repair.getAttitude());
        repairDto.setCommentsBelow(repair.getCommentsBelow());
        repairDto.setRepairSite(repair.getRepairSite());
        repairDto.setWarnTime(repair.getWarnTime());
        repairDto.setTelephone(repair.getTelephone());
        return repairDto;
    }


    /**
     * @Description:获取报修拓展类
     * @author: ccl
     * @Param: [pagination, repair]
     * @Return: java.util.List<com.houqin.entity.repair.RepairDto>
     * @Date: 2017-02-13
     */
    public List<RepairDto> getRepairsDtos(Pagination pagination, Repair repair) {
        String whereSql = GenerateSqlUtil.getSql(repair);
        whereSql += "  order by id desc";
        List<Repair> repairs = this.find(pagination, whereSql);
        for (int i = 0; i < repairs.size(); i++) {
            if (StringUtils.isNotEmpty(repairs.get(i).getPepairPeopleId() + "")) {
                SysUser sysUser = baseHessianBiz.getSysUserById(repairs.get(i).getPepairPeopleId());
                if (ObjectUtils.isNotNull(sysUser)) {
                    repairs.get(i).setSysUser(sysUser);
                }
            }
        }
        List<RepairDto> repairDtos = null;
        repairDtos = repairs.stream()
                .map(re -> convertRepairToDto(re))
                .collect(Collectors.toList());

        return repairDtos;
    }


    /**
     * @Description:获取报修拓展类
     * @author: ccl
     * @Param: [pagination, repair]
     * @Return: java.util.List<com.houqin.entity.repair.RepairDto>
     * @Date: 2017-02-13
     */
    public List<RepairDto> getAllRepairsDtos(Pagination pagination, Repair repair,int functionType) {
        String whereSql = GenerateSqlUtil.getSql(repair);

        whereSql += "  and functionType="+functionType+" order by id desc";
        List<Repair> repairs = this.find(pagination, whereSql);
        for (int i = 0; i < repairs.size(); i++) {
            if (StringUtils.isNotEmpty(repairs.get(i).getPepairPeopleId() + "")) {
                SysUser sysUser = baseHessianBiz.getSysUserById(repairs.get(i).getPepairPeopleId());
                if (ObjectUtils.isNotNull(sysUser)) {
                    repairs.get(i).setSysUser(sysUser);
                }
            }
        }
        List<RepairDto> repairDtos = null;
        repairDtos = repairs.stream().map(re -> convertRepairToDto(re)).collect(Collectors.toList());
        return repairDtos;
    }





    /**
     * @Description:将类的属性全部放到拓展类
     * @author: ccl
     * @Param: [repairs]
     * @Return: com.houqin.entity.repair.RepairDto
     * @Date: 2017-02-13
     */
    private RepairDto convertRepairToDto(Repair repairs) {
        RepairDto repairDto = new RepairDto();
        BeanUtils.copyProperties(repairs, repairDto);
        if (repairDto.getTypeId() != null) {
            RepairType repairType = repairTypeBiz.findById(repairDto.getTypeId());
            if (repairType != null) {
                repairDto.setTypeName(repairType.getName());
            }
        }
        if (repairDto.getUserId() != null && repairDto.getUserId() > 0) {
            SysUser sysUser = baseHessianBiz.getSysUserById(repairDto.getUserId());
            repairDto.setUserName(sysUser.getUserName());
        }
        return repairDto;
    }


}
