package com.a_268.base.sitemesh;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.sitemesh.DecoratorSelector;
import org.sitemesh.content.Content;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.webapp.WebAppContext;

/**
 * 过滤处理自定义标签中的元素
 * @author s.li
 *
 */
public class ParamDecoratorSelector implements DecoratorSelector<WebAppContext> {

	private DecoratorSelector<WebAppContext> defaultDecoratorSelector;
	 
    public ParamDecoratorSelector(DecoratorSelector<WebAppContext> defaultDecoratorSelector) {
        this.defaultDecoratorSelector = defaultDecoratorSelector;
    }
    
	@Override
	public String[] selectDecoratorPaths(Content content, WebAppContext context) throws IOException {
		HttpServletRequest request = context.getRequest();
		/*if(request.getRequestURI().startsWith("/admin")){
			ContentProperty cp = content.getExtractedProperties().getChild("myMeta");
			System.out.println(cp.toString());
			if(cp.toString().trim().length()>0){
				return new String[] { "/WEB-INF/layouts/admin/" + "default-deta" + ".jsp" };
			}
		}*/
		//sitemesh默认（在sitemesh3.xml中配置的是属性装饰器）装饰器，
        return defaultDecoratorSelector.selectDecoratorPaths(content, context);
	}

}
