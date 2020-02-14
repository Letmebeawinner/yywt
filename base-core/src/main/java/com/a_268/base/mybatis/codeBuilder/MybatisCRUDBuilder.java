package com.a_268.base.mybatis.codeBuilder;

import com.a_268.base.metadata.EntityMappingManager;
import com.a_268.base.metadata.PropertyInfo;
import com.a_268.base.util.ClassUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.VelocityTemplateUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据类名生成BaseDao中定义的Mybatis的映射SQL语句
 * 现在支持动态insert
 * @author jingxue.chen
 */
public class MybatisCRUDBuilder extends UniversalCodeBuilder {

    @Override
    public <T> String buildByClass(Class<T> clazz) {
        return this.createSqlByEntity(clazz);
    }

    private static String templateFile = "createSql.vm";

    public String mergeTemplate(VelocityContext context) {
        Template template = null;
        StringWriter writer = null;
        try {
            template = VelocityTemplateUtils.getTemplate(templateFile);
            writer = new StringWriter();
            if (template != null)
                template.merge(context, writer);
            writer.flush();


        } catch (ResourceNotFoundException rnfe) {
            rnfe.printStackTrace();
        } catch (ParseErrorException pee) {
            pee.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writer.toString();
    }

    /******************************************************************************/

    private List<String> pkList = new ArrayList<String>();

    /**
     * 功能:初始化类字段属性
     * @param clazz 要转换的实体类
     * @return 如果type=file返回xml文件绝对路径，否则返回生成的xml内容
     */
    public <T> String createSqlByEntity(Class<T> clazz) {
        String tableName = ClassUtils.getUpperFirstLetterSimpleClassName(clazz.getSimpleName());
        tableName = "`" + tableName + "`";
        pkList.clear();
        List<String> columns = new ArrayList<String>();
        List<PropertyInfo> propertyinfos = EntityMappingManager.getBeanInfo(clazz).getProperties();
        for (PropertyInfo propertyinfo : propertyinfos) {
            String mName = propertyinfo.getPropertyName();
            columns.add(mName);
            // 判断是否是主键
            if (mName.equalsIgnoreCase("id"))
                pkList.add(mName);
        }
        String entityClassName = clazz.getName();
        String findByIdSql = findByIdSql(tableName, columns);
        String insertSql = insertSql(tableName, columns);
        String updateSql = updateSql(tableName, columns);
        String updateSql2 = updateSql2(tableName, columns);
        String updateSql3 = updateSql3(tableName, columns);
        VelocityContext context = new VelocityContext();
        String deleteSql;
        String deleteByIdsSql;
        String deleteBatchSql;

        deleteSql = deleteSql(tableName, columns);
        deleteByIdsSql = "delete from " + tableName + " WHERE id in <include refid=\"common.idsForEach\"/>";
        deleteBatchSql = "delete from " + tableName + " WHERE ${whereSql} ";

        context.put("delete", deleteSql);
        context.put("deleteById", deleteSql);
        context.put("deleteByIdsSql", deleteByIdsSql);
        context.put("deleteBatchSql", deleteBatchSql);

        context.put("findById", findByIdSql);
        context.put("findSql", findSql(tableName, clazz));
        context.put("findSql2", findSql2(tableName));

        context.put("insert", insertSql);
        context.put("update", updateSql);
        context.put("update2",updateSql2);
        context.put("update3",updateSql3);
        context.put("updateBatchSql", updateBatchSql(tableName, columns));

        context.put("countSql",countSql(tableName));

        context.put("tableName", tableName);
        String daoClassName = getDaoClassName(entityClassName);

        context.put("daoClass", daoClassName);
        context.put("entityClass", entityClassName);
        context.put("cacheClass", "");
        context.put("cached", false);
        return mergeTemplate(context);

    }

    /**
     * 生成
     * @param entityClassName
     * @return
     */
    private String getDaoClassName(String entityClassName) {
        String daoClassName = entityClassName.replace("entity", "dao") + "Dao";
        return daoClassName;
    }

    /**
     * 生成insert语句
     * @param tableName
     * @param columns
     * @return
     */
    private String insertSql(String tableName, List<String> columns) {
        StringBuilder insertSql = new StringBuilder();
        StringBuilder valueSql = new StringBuilder();
        insertSql.append("insert into ").append(tableName).append("(");
        insertSql.append(" <trim suffix='' suffixOverrides=','>");
        valueSql.append(" <trim suffix='' suffixOverrides=','>");
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            insertSql.append("<if test=\"" + column + " != null\" >");
            insertSql.append(column).append(",");
            insertSql.append("</if>");
            valueSql.append("<if test=\"" + column + " != null\" >");
            valueSql.append("<![CDATA[#{").append(column).append("}]]>").append(",");
            valueSql.append("</if>");
        }
        valueSql.append("</trim>");
        insertSql.append("</trim>) values (").append(valueSql).append(")");
        return insertSql.toString();
    }

    /**
     * 生成update语句
     * @param tableName
     * @param columns
     * @return
     */
    private String updateSql(String tableName, List<String> columns) {
        // <set>元素会动态前置 SET关键字,而且也会消除任意无关的逗号
        StringBuilder updateSql = updateFields(tableName, columns, false);
        if (pkList.size() > 0) {
            updateSql.append(pkWhereSqlStr());
        }
        return updateSql.toString();
    }

    /**
     * 生成update语句
     * @param tableName
     * @param columns
     * @return
     */
    private String updateSql2(String tableName, List<String> columns) {
        // <set>元素会动态前置 SET关键字,而且也会消除任意无关的逗号
        StringBuilder updateSql = updateFields(tableName, columns, true);
        updateSql.append(NEW_LINE_BREAK).append("<where>");
        updateSql.append(NEW_LINE_BREAK).append(
                "<include refid=\"common.dynamicConditionsNoWhere\"/>");
        updateSql.append(NEW_LINE_BREAK).append("</where>");
        return updateSql.toString();
    }

    /**
     * 生成update语句
     * @param tableName
     * @param columns
     * @return
     */
    private String updateSql3(String tableName, List<String> columns) {
        // <set>元素会动态前置 SET关键字,而且也会消除任意无关的逗号
        StringBuilder updateSql = updateFields(tableName, columns, true);
        updateSql.append(NEW_LINE_BREAK).append(" where ${whereSql}");
        return updateSql.toString();
    }

    /**
     * 拼接更新字段
     * @param tableName
     * @param columns
     * @param useAlias
     * @return
     */
    private StringBuilder updateFields(String tableName, List<String> columns, boolean useAlias) {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("update ").append(tableName).append(" <set> ");
        for (String column : columns) {
            if(!column.equals("createTime")){
                String columnAlias = useAlias ? "entity." + column : column;
                updateSql.append(" <if test=\"").append(columnAlias).append("!= null\"> ");
                updateSql.append(column).append("=<![CDATA[#{").append(columnAlias).append("}]]>,");
                updateSql.append(" </if> ");
            }
        }
        updateSql.append(" </set> ");
        return updateSql;
    }

    /**
     * 生成批量更新语句
     * @param tableName
     * @param columns
     * @return
     */
    private String updateBatchSql(String tableName, List<String> columns) {
        StringBuilder updateSql = updateFields(tableName, columns, true);
        updateSql.append(NEW_LINE_BREAK).append(" WHERE id in ");
        updateSql.append("<include refid=\"common.idsForEach\"/>");
        return updateSql.toString();
    }

    /**
     * 生成findById语句
     * @param tableName
     * @param columns
     * @return
     */
    private String findByIdSql(String tableName, List<String> columns) {
        StringBuilder findByIdSql = new StringBuilder();
        findByIdSql.append("select * from ").append(tableName);
        findByIdSql.append(pkWhereSqlStr());
        return findByIdSql.toString();
    }

    /**
     * 生成查询语句
     * @param tableName
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> String findSql(String tableName, Class<T> clazz) {

        StringBuilder findSql = new StringBuilder();
        findSql.append("select * from ").append(tableName);
        findSql.append(NEW_LINE_BREAK).append("<where>");
        findSql.append(NEW_LINE_BREAK).append(
                "<include refid=\"common.dynamicConditionsNoWhere\"/>");
        findSql.append(NEW_LINE_BREAK).append("</where>");
        String orderBy = EntityMappingManager.getBeanInfo(clazz).getOrderBy();
        if (!StringUtils.isEmpty(orderBy)) {
            findSql.append(NEW_LINE_BREAK);
            findSql.append(" ORDER BY ").append(orderBy);
        }
        return findSql.toString();
    }

    /**
     * 生成查询语句
     * @param tableName
     * @param <T>
     * @return
     */
    private <T> String findSql2(String tableName) {

        StringBuilder findSql = new StringBuilder();
        findSql.append("select * from ").append(tableName);
        findSql.append(NEW_LINE_BREAK).append("where ${whereSql}");
        return findSql.toString();
    }

    /**
     * 生成delete语句
     * @param tableName
     * @param columns
     * @return
     */
    private String deleteSql(String tableName, List<String> columns) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from ").append(tableName);
        deleteSql.append(pkWhereSqlStr());
        return deleteSql.toString();
    }

    /**
     * 主键where条件拼接
     * @return
     */
    private String pkWhereSqlStr() {
        if (pkList.size() == 0)
            return "";
        StringBuilder pkStr = new StringBuilder();
        pkStr.append(" where ");
        for (String pk : pkList) {
            pkStr.append(pk).append("=").append("#{").append(pk).append("}").append(" and ");
        }

        return pkStr.delete(pkStr.length() - 4, pkStr.length()).toString();
    }

    /**
     * 查询记录数
     * @param tableName
     * @param
     * @return
     */
    private String countSql(String tableName) {
        StringBuilder findSql = new StringBuilder();
        findSql.append("select count(id) from ").append(tableName);
        findSql.append(NEW_LINE_BREAK).append("where ${whereSql}");
        return findSql.toString();
    }

}
