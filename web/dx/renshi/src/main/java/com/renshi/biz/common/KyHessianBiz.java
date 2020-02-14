package com.renshi.biz.common;

import com.a_268.base.core.Pagination;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.renshi.common.KyHessianService;
import com.renshi.entity.result.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 科研资政系统接口调用
 *
 * @author 268
 */
@Service
public class KyHessianBiz {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    /*@Autowired
    private KyHessianService kyHessianService;
   *//**
     * @Description:    获得某用户的全部成果
     * @author: xiayong
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @Date: 2016/12/26
   *//*

    public List<QueryResult> queryAllResult(QueryResult queryResult, Pagination pagination){
        String taskJson=gson.toJson(queryResult);
        String paginationJson=gson.toJson(pagination);
        Map<String, String> map=kyHessianService.queryAllResult(taskJson,paginationJson);
        String tasks=map.get("taskList");
        String pagina=map.get("pagination");
        List<QueryResult> resultList=gson.fromJson(tasks,new TypeToken<List<QueryResult>>(){}.getType());
        Pagination pagination1=gson.fromJson(pagina,Pagination.class);
        pagination.setTotalCount(pagination1.getTotalCount());
        pagination.setTotalPages(pagination1.getTotalPages());
        return resultList;
    }*/
}
