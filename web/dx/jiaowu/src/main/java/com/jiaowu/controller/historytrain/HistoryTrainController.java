package com.jiaowu.controller.historytrain;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.partySpirit.PartySpiritBiz;
import com.jiaowu.biz.studyTest.StudyTestBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.partySpirit.PartySpirit;
import com.jiaowu.entity.studyTest.StudyTest;
import com.jiaowu.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 李帅雷 on 2017/8/23.
 */
@Controller
public class HistoryTrainController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(HistoryTrainController.class);
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }
    @Autowired
    private PartySpiritBiz partySpiritBiz;
    @Autowired
    private StudyTestBiz studyTestBiz;
    @Autowired
    private UserBiz userBiz;



    @RequestMapping("/admin/jiaowu/historyTrain/historyTrainList")
    public String historyTrainList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination){
        try{
            String whereSql=" status=7";
            pagination.setRequest(request);
            List<User> userList = userBiz.find(pagination,whereSql+" order by unitId");
            List<Map<String,Object>> historyTrainList=new LinkedList<Map<String,Object>>();
            if(userList!=null&&userList.size()>0){
                for(User user:userList){
                    String graduateNumber=null;
                    double partySpiritTotal=0;
                    double studyTestTotal=0;
                    List<PartySpirit> partySpiritList=partySpiritBiz.find(null," userId="+user.getId());
                    if(partySpiritList!=null&&partySpiritList.size()>0){
                        partySpiritTotal=partySpiritList.get(0).getTotal();
                    }
                    List<StudyTest> studyTestList=studyTestBiz.find(null," userId="+user.getId());
                    if(studyTestList!=null&&studyTestList.size()>0){
                        graduateNumber=studyTestList.get(0).getGraduateNumber();
                        studyTestTotal=studyTestList.get(0).getTotal();
                    }
                    Map<String,Object> map=new HashMap<String,Object>();
                    map.put("name",user.getName());
                    map.put("className",user.getClassName());
                    map.put("createTime",user.getCreateTime());
                    map.put("graduateNumber",graduateNumber);
                    map.put("partySpiritTotal",partySpiritTotal);
                    map.put("studyTestTotal",studyTestTotal);
                    historyTrainList.add(map);
                }
            }
            request.setAttribute("historyTrainList",historyTrainList);
            request.setAttribute("pagination",pagination);

        }catch(Exception e){
            e.printStackTrace();
        }
        return "/admin/historyTrain/historyTrain_list";
    }
}
