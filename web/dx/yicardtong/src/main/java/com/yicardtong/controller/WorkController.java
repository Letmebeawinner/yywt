package com.yicardtong.controller;

import com.a_268.base.controller.BaseController;
import com.yicardtong.biz.work.WorkSourceService;
import com.yicardtong.biz.worksmail.WorkSmailService;
import com.yicardtong.entity.WorkSmail;
import com.yicardtong.entity.WorkSmailDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by caichenglong on 2017/10/29.
 */
@Slf4j
@Controller
public class WorkController extends BaseController {


    @Autowired
    private WorkSmailService workSmailService;

    @Autowired
    private WorkSmailDao workSmailDao;

    @Autowired
    private WorkSourceService workSourceService;

    /**
     * 添加工作内容
     */
    @RequestMapping("/addWorkSmail")
    public void workSmail() {

        WorkSmail workSmail = new WorkSmail();
        workSmail.setBase_OperCode("1231");
        workSmail.setBase_Mail("1231");
        workSmail.setBase_Password("1231");
        workSmail.setBase_MP("1231");
        int result = workSmailService.addWorkSmail(workSmail);
        if (result > 0) {
            System.out.println("添加成功");
        }
    }


    /**
     * 添加工作内容
     */
    @RequestMapping("/addSaveWorkSmail")
    public void addSaveWorkSmail() {

        WorkSmail workSmail = new WorkSmail();
        workSmail.setBase_OperCode("long");
        workSmail.setBase_Mail("long");
        workSmail.setBase_Password("long");
        workSmail.setBase_MP("long");
        workSmailDao.addWork(workSmail);
    }


    @RequestMapping("/delwork")
    public void delWork(){

        workSmailDao.deleteWork("long");
    }
}
