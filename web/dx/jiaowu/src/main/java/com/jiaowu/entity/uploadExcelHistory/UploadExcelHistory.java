package com.jiaowu.entity.uploadExcelHistory;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 导入Excel表格记录
 * @author 王赛
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UploadExcelHistory extends BaseEntity{
	private static final long serialVersionUID = 8575252963969481402L;

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件路径
	 */
	private String fileUrl;
}
