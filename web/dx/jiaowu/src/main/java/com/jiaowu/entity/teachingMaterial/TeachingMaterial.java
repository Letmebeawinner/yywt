package com.jiaowu.entity.teachingMaterial;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.teachingComment.TeachingCommentManagement;

/**
 * 教学资料
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TeachingMaterial extends BaseEntity{
	private static final long serialVersionUID = 2166753465176114183L;
	/**
	 * 教师ID
	 */
	/*private Long teacherId;
	*//**
	 * 教师名称
	 *//*
	private String teacherName;*/
	//用户ID
	private Long createUserId;
	//用户名称
	private String createUserName;
	//创建人的类型,1代表领导,2代表教师,3代表学员.
	private Integer type;
	//班次ID
	private Long classId;
	/**
	 * 名称
	 */
	private String title;
	//正文
	private String content;
	//资料分类ID
	private Long materialTypeId;
	//资料分类名称
	private String materialTypeName;
	/**
	 * 上传路径
	 */
	private String path;
	/**
	 * 审核状态,0代表未审核,1代表审核.
	 */
	private Integer hasCheck;
	/**
	 * 备注
	 */
	private String note;
}
