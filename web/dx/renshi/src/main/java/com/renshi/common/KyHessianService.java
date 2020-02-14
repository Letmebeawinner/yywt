package com.renshi.common;

import java.util.Map;

/**
 * 科研资政管理系统Hessian接口
 *
 * @author s.li
 * @create 2016-12-16-16:07
 */
public interface KyHessianService {

    /**
     * @Description:    获得某用户的全部成果
     * @author: xiayong
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @Date: 2016/12/26
     */
    Map<String, String> queryAllResult(String result , String pagination);
}
