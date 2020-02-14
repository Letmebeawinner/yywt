package com.a_268.base.extend;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
import java.util.Map;

/**
 * 重新实现ViewResolver，实现多视图
 */
public class MultiViewResolver implements ViewResolver {

    private Map<String, ViewResolver> resolvers;

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        int pos = viewName.lastIndexOf(".");
        ViewResolver resolver;
        if (pos == -1) {
            resolver = resolvers.get("vm");
        }else{
            String suffix = viewName.substring(pos + 1);
            resolver = resolvers.get(suffix);
        }
        if (resolver == null) {

            return null;
        }

        return resolver.resolveViewName(viewName, locale);
    }

    public Map<String, ViewResolver> getResolvers() {
        return resolvers;
    }

    public void setResolvers(Map<String, ViewResolver> resolvers) {
        this.resolvers = resolvers;
    }
}
