package com.information.controller;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.information.dao.InfoClassDao;
import com.information.entity.InfoClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class InformationController extends BaseController {


    @Autowired
    private InfoClassDao infoClassDao;

    /**
     * 获取所有的文章分类
     *
     * @return
     */
    @RequestMapping("/queryInfoClassList")
    @ResponseBody
    public Map<String, Object> queryInfoClassList() {
        Map<String, Object> json = null;
        try {
            List<InfoClass> infoClassList = infoClassDao.queryInfoClassList();
            json = resultJson(ErrorCode.SUCCESS, "", gson.toJson(infoClassList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
