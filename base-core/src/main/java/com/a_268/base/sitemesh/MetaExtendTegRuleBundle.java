package com.a_268.base.sitemesh;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * sitemesh自定义标签工具类
 * @author s.li
 *
 */
public class MetaExtendTegRuleBundle implements TagRuleBundle {

	/**
	 * 在jsp页面渲染前初始化sitemesh自定义标签
	 */
	@Override
	public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
		defaultState.addRule("myMeta", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("myMeta"), false));
    }

	@Override
	public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
		
	}

}
