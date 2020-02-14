package com.a_268.base.core;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

/**
 * @author jingxue.chen
 */
public class QueryIntercepterUtils {

    public static MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
    	return copyFromMappedStatement(ms,newSqlSource , ms.getResultMaps());
    }

    
    public static MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource, List<ResultMap> maps) {
        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        /**
         * mybatis 低版本3.0可用
         * builder.keyProperty(ms.getKeyProperty());
         */
        /**
         * mybatis 3.0+ 添加了联合主键支持
         */
        String[] s = ms.getKeyProperties();
        if(s == null || s.length == 0) builder.keyProperty(null);
        else builder.keyProperty(StringUtils.join(s,","));
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(maps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }    
}
