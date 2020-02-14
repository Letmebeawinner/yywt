package com.jiaowu.entity.reportProcedure;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;

/**
 * 报道流程
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ReportProcedure extends BaseEntity{
	private static final long serialVersionUID = 5688707555526232734L;
	/**
	 * 名称
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String note;
}
