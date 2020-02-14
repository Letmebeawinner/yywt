package com.menhu.entity.article;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * app资讯类
 *
 * @author guoshiqi
 * @create 2016-12-09-9:31
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleApp extends Article{
	private static final long serialVersionUID = -6034634689022222288L;


	private List<String> pictureList;

	public ArticleApp (Article article){
		this.setCreateTime(article.getCreateTime());
		this.setId(article.getId());
		this.setTitle(article.getTitle());
		this.setUserId(article.getUserId());
		this.setStatus(article.getStatus());
		this.setAuthor(article.getAuthor());
		this.setClickTimes(article.getClickTimes());
		this.setContent(article.getContent());
		this.setDescription(article.getDescription());
		this.setIsIndex(article.getIsIndex());
		this.setSource(article.getSource());
		this.setUpdateTime(article.getUpdateTime());
		this.setTypeId(article.getTypeId());
	}
}
