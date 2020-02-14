package com.keyanzizheng.biz.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.keyanzizheng.common.StudentHessianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 教务系统接口调用
 *
 * @author 268
 */
@Service
public class JwHessianBiz implements Serializable {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Autowired
    private StudentHessianService studentHessianService;

}
