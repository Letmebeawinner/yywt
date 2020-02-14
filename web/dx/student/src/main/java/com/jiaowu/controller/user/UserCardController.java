package com.jiaowu.controller.user;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
@Controller
public class UserCardController extends BaseController{


    @Autowired
    UserBiz userBiz;
    @Autowired
    ClassesBiz classesBiz;
    @Autowired
    ClassTypeBiz classTypeBiz;
    @Autowired private BaseHessianService baseHessianService;

    private static final String ADMIN_PREFIX = "/admin/jiaowu/user";

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 用户车辆列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/userCardList")
    public String userCardList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            List<ClassType> classTypeList = classTypeBiz.find(null, " 1=1 order by id desc");
            request.setAttribute("classTypeList", classTypeList);

            String whereSql = " status in (1,7,8)";
            User user = new User();
            String userId = request.getParameter("userId");
            if (!StringUtils.isTrimEmpty(userId) && Long.parseLong(userId) > 0) {
                whereSql += " and id=" + userId;
                user.setId(Long.parseLong(userId));
            }
            String classTypeId = request.getParameter("classTypeId");
            if (!StringUtils.isTrimEmpty(classTypeId) && Long.parseLong(classTypeId) > 0) {
                whereSql += " and classTypeId=" + classTypeId;
                user.setClassTypeId(Long.parseLong(classTypeId));
            }
            String classId = request.getParameter("classId");
            if (!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId) > 0) {
                whereSql += " and classId=" + classId;
                user.setClassId(Long.parseLong(classId));
                Classes classes = classesBiz.findById(Long.parseLong(classId));
                user.setClassName(classes.getName());
            }
            String studentId = request.getParameter("studentId");
            if (!StringUtils.isTrimEmpty(studentId)) {
                whereSql += " and studentId like '%" + studentId + "%'";
                user.setStudentId(studentId);
            }
            String name = request.getParameter("name");
            if (!StringUtils.isTrimEmpty(name)) {
                whereSql += " and name like '%" + name + "%'";
                user.setName(name);
            }
            String cardNoId = request.getParameter("cardNoId");
            if (!StringUtils.isTrimEmpty(cardNoId)) {
                if (Integer.parseInt(cardNoId) == 1) {
                    whereSql += " and carNumber is not NUll and carNumber!=''";
                }
                if (Integer.parseInt(cardNoId) == 2) {
                    whereSql += " and (carNumber is  NUll or trim(carNumber) ='')";
                }
            }
            pagination.setRequest(request);
            List<User> userList = userBiz.find(pagination, whereSql + " order by unitId,business,id desc ");
            addEmailAndMobileToUserList(userList);
            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            request.setAttribute("sysUser", userMap);
            request.setAttribute("cardNoId", cardNoId);
            request.setAttribute("showIdnumber", userMap.get("userType").equals("1") ? true : false);
            //查询用户角色

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/user_card_list";
    }


    /**
     * 关联查询手机号和邮箱
     * @param userList list里存的都是引用
     */
    private void addEmailAndMobileToUserList(List<User> userList) {
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, user.getId());
                if (baseUserList != null && baseUserList.size() > 0) {
                    user.setMobile(baseUserList.get(0).get("mobile"));
                    user.setEmail(baseUserList.get(0).get("email"));
                }
            }
        }
    }
}
