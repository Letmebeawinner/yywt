package com.keyanzizheng.controller.personnel;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.google.gson.reflect.TypeToken;
import com.keyanzizheng.biz.reportfiled.ReportFiledBiz;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.common.HrHessianService;
import com.keyanzizheng.constant.PaginationConstants;
import com.keyanzizheng.entity.personnel.Personnel;
import com.keyanzizheng.entity.reportfiled.ReportFiled;
import com.keyanzizheng.entity.result.Result;
import com.keyanzizheng.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 人员列表
 *
 * @author YaoZhen
 * @create 11-21, 15:28, 2017.
 */
@Controller
@RequestMapping("/admin/ky/")
public class PersonnelController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PersonnelController.class);


    @Autowired
    private ResultBiz resultBiz;
    @Autowired
    private HrHessianService hrHessianService;
    @Autowired
    private ReportFiledBiz reportFiledBiz;
    @Autowired
    private HttpServletRequest request;

    /**
     * 查询提交了成果的教职工列表
     * 成果包括课题和调研报告
     * 成果登记时判断了用户是否为教职工
     * 调研报告添加时判断了用户是教师还是学生
     */
    @RequestMapping("/getPersonnelList")
    public ModelAndView getPersonnelList(@ModelAttribute Pagination pagination,
                                         @RequestParam Integer resultType,
                                         @RequestParam(value = "name", required = false) String name) {
        ModelAndView mv = new ModelAndView("/employee/personnel_list");
        try {
            Map<String, String> personnelJson;

            // 所有发表过课题成果的教职工ids
            List<Result> resultList = resultBiz.find(null, "1=1 AND resultType = " + resultType + " AND employeeId IS NOT NULL GROUP BY employeeId");
            List<ReportFiled> reportFiledList = reportFiledBiz.find(null, "type = 'teacher' AND peopleId IS NOT NULL  GROUP BY peopleId");


            List<Long> ids = new ArrayList<>();
            resultList.forEach(r -> ids.add(r.getEmployeeId()));
            reportFiledList.forEach(r -> ids.add(r.getPeopleId()));
            // List去除List中重复的ID
            List<Long> unique = ids.stream().distinct().collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(unique)) {
                pagination.setPageSize(PaginationConstants.PAGE_SIZE);
                // 姓名 教研部 民族 职位职务 手机号
                personnelJson = hrHessianService.getEmployeeListByIds(pagination, unique, name);

                if (personnelJson != null) {
                    List<Personnel> list = gson.fromJson(personnelJson.get("list"),
                            new TypeToken<List<Personnel>>() {
                            }.getType());

                    pagination = gson.fromJson(personnelJson.get("pagination"), Pagination.class);
                    mv.addObject("list", list);
                    pagination.setRequest(request);
                    mv.addObject(pagination);
                }
            }
            mv.addObject("resultType", resultType);
            // 环回条件
            if (StringUtil.isNotBlank(name)) {
                mv.addObject(name);
            }
        } catch (Exception e) {
            logger.error("PersonnelController.getPersonnelList", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }
}
