package com.yicardtong.dao.general;

import com.a_268.base.core.BaseDao;
import com.yicardtong.entity.general.GeneralPersonnel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GeneralPersonalDao extends BaseDao<GeneralPersonnel> {

    /**
     * 添加人员
     * @param generalPersonnel
     */
    public void addGeneralPersonal(GeneralPersonnel generalPersonnel);

    /**
     * 修改人员信息
     * @param generalPersonnel
     */
    public void updateGeneralPersonal(GeneralPersonnel generalPersonnel);

    /**
     * 查询卡人员列表
     * @return
     */
    public List<GeneralPersonnel>  queryGeneralPersonList();

    /**
     * 通过卡号查询用户表
     * @return
     */
    public GeneralPersonnel queryPersonalInfoByCardNo(@Param("whereSql") String whereSql);

    /**
     * 删除人员
     * @param Base_PerID
     */
    public void deleteGeneralPersonal(Long Base_PerID);

}
