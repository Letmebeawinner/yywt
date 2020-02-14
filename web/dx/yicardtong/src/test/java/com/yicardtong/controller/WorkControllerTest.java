package com.yicardtong.controller;

import com.yicardtong.biz.worksmail.WorkSmailService;
import com.yicardtong.entity.WorkSmail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by caichenglong on 2017/10/29.
 */
public class WorkControllerTest {


    @Autowired
    private WorkSmailService workSmailService;
    @Test
    public void aa(){

        WorkSmail workSmail=new WorkSmail();
        workSmail.setBase_OperCode("1231");
        workSmail.setBase_Mail("1231");
        workSmail.setBase_Password("1231");
        workSmail.setBase_MP("1231");
        int result=workSmailService.addWorkSmail(workSmail);
        if(result>0){
            System.out.println("添加成功");
        }
    }

}