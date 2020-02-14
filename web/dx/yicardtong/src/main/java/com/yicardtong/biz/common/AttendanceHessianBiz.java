package com.yicardtong.biz.common;

import com.a_268.base.util.ObjectUtils;
import com.yicardtong.dao.general.GeneralPersonalDao;
import com.yicardtong.entity.general.GeneralPersonnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class AttendanceHessianBiz implements AttendanceHessianService {

    @Autowired
    private GeneralPersonalDao generalPersonalDao;


    /**
     * 修改用户的信息
     *
     * @param generalPersonMap
     */
    public String updateGeneralPersonal(Map<String, Object> generalPersonMap) {

        List<GeneralPersonnel> generalPersonnelList = generalPersonalDao.queryGeneralPersonList();

        String base_PerId = generalPersonnelList.get(0).getBase_PerID();
        Integer basePerId = Integer.parseInt(base_PerId) + 1;
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");

        String Base_PerNo = generalPersonMap.get("Base_PerNo").toString();
        String Base_PerName = generalPersonMap.get("Base_PerName").toString();
        String Base_CardNo = generalPersonMap.get("Base_CardNo").toString();
        String setBase_Sex = generalPersonMap.get("setBase_Sex").toString();

        //查询
        GeneralPersonnel generalPersonnel = new GeneralPersonnel();
        generalPersonnel.setBase_PerID(decimalFormat.format(basePerId));
        generalPersonnel.setBase_PerNo(Base_PerNo);
        generalPersonnel.setBase_PerName(Base_PerName);
        generalPersonnel.setBase_CardNo(Base_CardNo);
        generalPersonnel.setBase_GroupID("1");
        generalPersonnel.setBase_IsDel("0");
        generalPersonnel.setBase_Work("0");
        generalPersonnel.setBase_Deposit(new BigDecimal(0.00));
        generalPersonnel.setBase_Sex(setBase_Sex);


        GeneralPersonnel generalPersonnel1 = new GeneralPersonnel();
        generalPersonnel1 = generalPersonalDao.queryPersonalInfoByCardNo("'"+Base_CardNo+"'");
        if (ObjectUtils.isNotNull(generalPersonnel1)) {
            generalPersonalDao.updateGeneralPersonal(generalPersonnel);
        } /*else {
            generalPersonalDao.addGeneralPersonal(generalPersonnel);
        }*/
        return null;
    }



    /**
     * 验证房间对应考勤卡是否有效
     *
     * @param timeCardId
     */
    public boolean queryRoomExist(String timeCardId) {
        GeneralPersonnel generalPersonnel = generalPersonalDao.queryPersonalInfoByCardNo("'"+timeCardId+"'");
        if(ObjectUtils.isNotNull(generalPersonnel)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 转化成为考勤卡
     *
     * @param cardId
     * @return
     */

    public static String conversionCardId(String cardId) {
        cardId = cardId.substring(2, cardId.length());
        String st[] = new String[8];
        st = cardId.split("");
        return st[6] + st[7] + st[4] + st[5] + st[2] + st[3] + st[0] + st[1];
    }


}
