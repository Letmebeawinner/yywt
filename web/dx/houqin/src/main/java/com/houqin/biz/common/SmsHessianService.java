package com.houqin.biz.common;


import java.util.Map;

/**
 * 短信Hessian
 *
 * @author ccl
 */
public interface SmsHessianService {

    /**
     * 发送短信
     *
     * @param map
     * @return
     */
   public boolean sendMsg(Map<String, String> map) throws Exception;


}
