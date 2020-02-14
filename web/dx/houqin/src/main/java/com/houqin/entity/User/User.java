package com.houqin.entity.User;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学员
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity{
	private static final long serialVersionUID = 8575252963969481402L;
	/**
	 * status各个值意义,0-删除,1-正常,2-新生(批量导入学员为2),3-设置为轮训人员,4-设置为轮训人员通过,5-设置为轮训人员驳回,
	 * 1-学员确认参加班次,6-学员拒绝参加班次,7-毕业.
	 */
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
	 * 级别
	 */
	private Integer business;

	/**
	 * 图片路径
	 */
	private String path ; //图片路径

	/**
	 *  车辆编号
	 */
	private String carNumber;
	//单位ID
	private Long unitId;
	//单位名称
	private String unit;
	//民族
	private String nationality;
	//如果学员选择了其它,则手动填写单位名称.
//	private String unitName;
	//学历
	private String qualification;
	//属于哪个系统用户导入的
	private Long sysUserId;
	//政治面貌
	private Long politicalStatus;
	//出生日期
	private String birthday;

	private int sort;
	//党校一卡通员工编号
	private String perId;
	//党校一卡通卡号
	private String cardNo;
	//组别
	private Integer groupId;
	//房间信息
	private String roomInformation;
	//导入排序值
	private Integer importsort;
}
