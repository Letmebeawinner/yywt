package com.a_268.base.mybatis.codeBuilder;


/**
 * 根据类名生成相关代码的通用代码接口。
 * @author jingxue.chen
 */
public abstract class UniversalCodeBuilder {
	protected static final String NEW_LINE_BREAK = "\r\n";
	
	public abstract <T> String buildByClass(Class<T> clazz);
	
}
