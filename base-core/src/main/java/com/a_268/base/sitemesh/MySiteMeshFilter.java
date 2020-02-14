package com.a_268.base.sitemesh;

import org.sitemesh.DecoratorSelector;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.webapp.WebAppContext;

/**
 * 扩展sitemesh的ConfigurableSiteMeshFilter过滤器
 * 对包含在自定义标签中的元素进行处理
 * @author s.li
 *
 */
public class MySiteMeshFilter extends ConfigurableSiteMeshFilter{
	
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		//获取原有默认配置装饰选择器
        DecoratorSelector<WebAppContext> defaultDecoratorSelector = builder.getDecoratorSelector();
        //赋给自定义装饰选择器，则自定义规则未匹配时调用默认选择器获取
        builder.setCustomDecoratorSelector(new ParamDecoratorSelector(defaultDecoratorSelector));
    
	}
}
