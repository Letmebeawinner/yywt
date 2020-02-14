package com.jiaowu.entity.classroom;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教室
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Classroom extends BaseEntity{
	
	private static final long serialVersionUID = 1875691564332342260L;
	/**
	 * 教室位置
	 */
	private String position;
	/**
	 * 座位数
	 */
	private Integer num;
	/**
	 * 备注
	 */
	private String note;
	//座区图类型，1代表最初的，2代表100座的,3代表大礼堂一二楼座区图
	private Integer type;
}
