package com.renshi.entity.job;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 干部类
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class JobOrder extends BaseEntity{
	private static final long serialVersionUID = -6034634689029517111L;

	private String name;//序列名称
}
