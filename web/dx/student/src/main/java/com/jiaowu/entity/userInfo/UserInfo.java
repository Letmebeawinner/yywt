package com.jiaowu.entity.userInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.research.ResearchReport;

/**
 * 学员
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserInfo extends BaseEntity{
	private static final long serialVersionUID = 8575252963969481402L;
	/**
	 * 班型ID
	 */
	private Long classTypeId;
	
	/**
	 * 班型名称
	 */
	private String classTypeName;
	/**
	 * 班次ID
	 */
	private Long classId;
	/**
	 * 班次名称
	 */
	private String className;
	/**
	 * 序号
	 */
	private String serialNumber;
	/**
	 * 学号
	 */
	private String studentId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 职务
	 */
	private String job;
	/**
	 * 身份证号
	 */
	private String idNumber;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 确认密码
	 */
	private String confirmPassword;
	/**
	 * 用户key
	 */
	private String customerKey;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 是否已报到,1代表已报到,0代表未报到.
	 */
	private Integer baodao;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 是否审批,0代表未审批,1代表审批.
	 */
	private Integer hasApprove;
	/**
	 * 是否负责人核对了,0代表未核对,1代表已核对.
	 */
	private Integer hasCheck;
	/**
	 * 是否是班长,0代表不是,1代表是.
	 */
	private Integer isMonitor;
}
