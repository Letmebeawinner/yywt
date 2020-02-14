package com.yicardtong.entity.general;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by caichenglong on 2017/10/30.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GeneralPersonnel extends BaseEntity {

    private String Base_PerID;

    private String Base_PerNo;

    private String Base_PerName;

    private String Base_GroupID;

    private String Base_CardNo;

    private BigDecimal Base_Money;

    private BigDecimal Base_Deposit;

    private String Base_SubID;

    private String Base_Sex;

    private String Base_Tel;

    private String Base_BirthDay;

    private String Base_Photo;

    private String Base_IdCard;

    private String Base_Memo;

    private String Base_StartDateTime;

    private String Base_StopDateTime;

    private String Base_Password;

    private String Base_IsIDIC;

    private String Base_Type;

    private String Base_State;

    private String Base_CostList;

    private String Base_DataOne;

    private String Base_DataTwo;

    private String Base_DataThr;

    private String Base_IsDel;

    private String Base_Modify_User;

    private Date Base_Modify_DateTime;

    private String Base_RoleID;

    private String Base_AssPerID;

    private String Base_WebPassword;

    private String Base_RepealCardNo;

    private BigDecimal Base_RepealMoney;

    private String Base_Work;

    private String Base_AddrID;

    private String YD_CARDID;

    private String YD_APP;

    private String YD_TEL;

    private String YD_SENDMODE;

    private String PapersType;

    private String VehicleNo;

    private String Base_CardNo_1;

    private String Base_CardNo_2;

    private String Base_CardNo_3;

    private String Base_CardNo_4;

    private float BASE_CMONEY;

    private float Base_SUBMONEY;



}
