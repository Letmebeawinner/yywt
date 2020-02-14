package com.yicardtong.controller;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.yicardtong.biz.consume.ConsumeService;
import com.yicardtong.biz.general.PersonService;
import com.yicardtong.dao.general.GeneralPersonalDao;
import com.yicardtong.entity.general.GeneralPersonnel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by caichenglong on 2017/10/17.
 */
@Controller
public class PersonController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AttendController.class);

    private static final String ADMIN_PREFIX = "/yikatong";

    @Autowired
    private PersonService personService;
    @Autowired
    private GeneralPersonalDao generalPersonalDao;

    /**
     * @param request
     * @return java.lang.String
     * @Description 获取用户列表
     * @author CCL
     */
    @RequestMapping(ADMIN_PREFIX + "/showPersonList")
    @ResponseBody
    public Map<String, Object> showPersonList(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<Map<String, String>> personList = personService.queryPersonList("select * from General_Personnel");
            json = this.resultJson(ErrorCode.SUCCESS, "", personList);
        } catch (Exception e) {
            logger.info("ConsumeController.showPersonList", e);
        }
        return json;
    }

    /**
     * 添加
     *
     * @param
     * @return
     */
    @RequestMapping(value = ADMIN_PREFIX + "/addPersonal", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addOrUpdatePersonal(@ModelAttribute("generalPersonnel") GeneralPersonnel generalPersonnel) {
        Map<String, Object> json = null;
        try {

            generalPersonnel = generalPersonalDao.queryPersonalInfoByCardNo(generalPersonnel.getBase_CardNo());
            if (ObjectUtils.isNotNull(generalPersonnel)) {
                generalPersonalDao.updateGeneralPersonal(generalPersonnel);
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
            } else {
                List<GeneralPersonnel> generalPersonnelList = generalPersonalDao.queryGeneralPersonList();

                String base_PerId = generalPersonnelList.get(0).getBase_PerID();
                Integer basePerId = Integer.parseInt(base_PerId) + 1;
                DecimalFormat decimalFormat = new DecimalFormat("0000000000");

                generalPersonnel.setBase_PerID(decimalFormat.format(basePerId));
                generalPersonnel.setBase_PerNo("蔡成龙");
                generalPersonnel.setBase_PerName("123123");
                generalPersonnel.setBase_CardNo("001487DEF9");
                generalPersonnel.setBase_GroupID("1");
                generalPersonnel.setBase_IsDel("0");
                generalPersonnel.setBase_Work("0");
                generalPersonnel.setBase_Money(new BigDecimal(0.00));
                generalPersonnel.setBase_Deposit(new BigDecimal(0.00));
                generalPersonnel.setBase_Sex("1");
                generalPersonnel.setBase_BirthDay("");
                generalPersonalDao.addGeneralPersonal(generalPersonnel);
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 修改学员
     *
     * @param request
     * @return
     */
    @RequestMapping("/updatePersonal")
    @ResponseBody
    public Map<String, Object> updatePersonal(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            GeneralPersonnel generalPersonnel = new GeneralPersonnel();
            generalPersonalDao.updateGeneralPersonal(generalPersonnel);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, generalPersonnel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 删除人员
     *
     * @param request
     * @return
     */
    @RequestMapping("/deletePersonal")
    @ResponseBody
    public Map<String, Object> deletePersonal(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            String Base_PerID = request.getParameter("Base_PerID");
            generalPersonalDao.deleteGeneralPersonal(Long.parseLong(Base_PerID));
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
