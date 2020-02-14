package com.houqin.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by caichenglong on 2017/10/25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo implements Serializable {

    private int UserId;
    private String UserName;
    private String CardId;
    private BigDecimal Balance;
    private int ConsumeTotal;
    private int CardStatusId;
    private int CardType;
    private String Phonetized_Name;
    private int GenderId;
    private Date RegisterTime;
    private int WorkPosition;
    private int WorkType;
    private int EmployeeType;
    private Date ExpiredTime;
    private int Status;
    private String PassWord;
    private String IdentityNo;
    private String CridentialId;
    private String HomePhone;
    private String CellPhone;
    private Date Birthday;
    private String Address;
    private int EduLevelId;
    private int StuClassId;
    private int DutyId;
    private int EmpDeptId;
    private int TchSpecialityId;
    private String GraduatedSchool;
    private String GraduateSpeciality;
    private Date WorkTime;
    private int BedchamberId;
    private int StudentFlag;
    private int EmployeeFlag;
    private int TeacherFlag;
    private String ACRuleId;
    private Date DeleteDate;
    private String OrgCorp;
    private String CardId_10;
    private int CredentialsTypeId;
    private String Corporation;
    private int NationId;
    private String Instructor;
    private int PoliticsId;
    private Date GraduateTime;
    private String GuardianName;
    private String StudentStatus;
    private int StudentTypeId;
    private String SchoolSystem;
    private String StudentChange;
    private String Remarks;
    private int BedNum;
    private int UserType;
    private String ExtUserId;
    private String OfficePhone;
    private String SubCardId;
    private Date InsureTime;
    private String InsutanceId;
    private int HealthStatusId;
    private String SubCardId_1;
    private int RankId;
    private int bRecordingInOut;
    private int InstructorId;
    private int SubCardStatusId;
    private int SubCard_1StatusId;
    private String EmailAddress;
    private String PresentAddress;
    private int RelationUserId;
    private int RelationId;
    private int OperatorId;
    private int Weight;
    private int CompanionNum;
    private String TempAddress;
    private String PersonnelEntryCauses;
    private String DocumentsRequiredConditions;
    private String ExtensionInfo;
    private String LockPassWord;
    private String CoercionPassWword;
    private String WeChatInfo;
    private int SubCardType;
    private int SubCard1Type;
    private int VerifyTypeId;
    private Date UpdateTime;
    private int OpenDoorAuth;
    private String TimeLimitPeriodIds;
    private int UserRole;
    private int DataSource;
    private String equipmentId;
    private int equipmentupdatetime;
    private int validStatus;
    private String vphoneId;
}
