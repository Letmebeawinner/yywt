package com.menhu.entity.article;

import com.a_268.base.core.BaseEntity;
import com.menhu.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.Date;

/**
 * 资讯类
 *
 * @author guoshiqi
 * @create 2016-12-09-9:31
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Article extends BaseEntity{

	private Long userId;//创建人id
	@Like
	private String title;//题目
	private String description;//简介（自动生成，前200个汉字）
	private String content;//文件号
	private String picture;//图片
	private String author;//责任者
	private Long typeId;//档案类别id
	private Integer clickTimes;//点击数量
	private Integer isIndex;//0不添加到首页，1添加到首页
	private String source;//来源
	private String fileUrl;//文件地址
}
