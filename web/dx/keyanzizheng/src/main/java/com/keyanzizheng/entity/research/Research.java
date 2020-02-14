package com.keyanzizheng.entity.research;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 调研报告
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Research extends BaseEntity{
	private static final long serialVersionUID = 7758461969326661382L;
	/**
	 * 名称
	 */
	private String title;
	/**
	 * 调研报告开始时间
	 */
	private Date startTime;
	/**
	 * 调研报告结束时间
	 */
	private Date endTime;
	/**
	 * 备注
	 */
	private String note;	
}
