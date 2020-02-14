package com.jiaowu.controller.umc;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.umc.DpWebService;
import com.jiaowu.biz.umc.WifiUserBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.common.StringUtils;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.umc.WifiUser;
import com.jiaowu.entity.umc.WifiUserVO;
import com.jiaowu.entity.user.User;
import com.jiaowu.util.GenerateSqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 编辑自己的wifi账号
 *
 * @author YaoZhen
 * @date 03-30, 11:02, 2018.
 */
@Slf4j
@Controller
public class WifiController extends BaseController {
    /**
     * 当前线程的req
     */
    @Autowired private HttpServletRequest request;
    @Autowired private WifiUserBiz wifiUserBiz;
    @Autowired private DpWebService dpWebService;
    @Autowired private UserBiz userBiz;
    @Autowired private ClassesBiz classesBiz;

    /**
     * 定制WebDataBinder的初始化
     * @param binder 用以为Controller方法填充命令对象和表单对象的参数
     */
    @InitBinder("wifiUser")
    public void wifiUserInit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("wifiUser.");
    }

    /**
     * 查询所有的wifi用户
     * 只能修改当前在校状态的学员
     */
    @RequestMapping("/admin/jiaowu/wifiuser/list")
    public ModelAndView listWifiUser(@ModelAttribute("pagination") Pagination pagination,
                                     @ModelAttribute("wifiUser") WifiUser wifiUser) {
        ModelAndView mv = new ModelAndView("/admin/umc/wifi_user_list");
        try {
            String whereSql = GenerateSqlUtil.getSql(wifiUser);
            pagination.setRequest(request);
            List<WifiUserVO> wifiUserList = wifiUserBiz.findWifiUserVO(pagination, whereSql);
            mv.addObject("wifiUserList", wifiUserList);
        } catch (Exception e) {
            log.error("WifiController.listWifiUser", e);
        }

        return mv;
    }

    /**
     * 重置用户密码
     */
    @RequestMapping("/admin/jiaowu/wifiuser/reset")
    @ResponseBody
    public Map<String, Object> resetWifiUser(@RequestParam("wifiUserId") Long wifiUserId) {
        Map<String, Object> objMap;

        try {
            boolean flag = dpWebService.resetUserPassWord(wifiUserId);
            if (flag) {
                objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } else {
                objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, "添加失败, 请联系管理员", null);
            }
        } catch (Exception e) {
            log.error("WifiController.resetWifiUser", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    /**
     * 如果用户没有开通wifi 跳转到 添加wifi页面
     */
    @RequestMapping("/admin/jiaowu/wifiuser/update")
    public ModelAndView updWifiPassword() {
        ModelAndView mv = new ModelAndView("/admin/umc/update_wifi_user");
        try {
            Map<String, String> baseUser = SysUserUtils.getLoginSysUser(request);
            if (baseUser == null) {
                return new ModelAndView(setErrorPath(request, new Exception("找不到用户信息, 请重新登录")));
            }

            // 根据userId 查询对应的wifi账号
            List<WifiUser> wifiUsers = wifiUserBiz.find(null, "studentLinkId = " + baseUser.get("linkId"));

            // 没有wifi的去开通wifi
            if (CollectionUtils.isEmpty(wifiUsers)) {
                return new ModelAndView("redirect:/admin/jiaowu/wifiuser/save.json");
            }

            mv.addObject("wifiUser", wifiUsers.get(0));
        } catch (Exception e) {
            log.error("WifiController.updWifiPassword", e);
        }
        return mv;
    }


    /**
     * 修改用户密码
     */
    @RequestMapping("/admin/jiaowu/wifiuser/doUpdate")
    @ResponseBody
    public Map<String, Object> doUpdWifiPassword(@ModelAttribute("wifiUser") WifiUser wifiUser) {
        Map<String, Object> objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        try {
            // 不存在的要先去开通wifi账号
            List<WifiUserVO> vos = wifiUserBiz
                    .findWifiUserVO(null, "account=" + wifiUser.getAccount());
            if (CollectionUtils.isEmpty(vos)) {
                return objMap;
            }

            WifiUser dbUser = vos.get(0);
            boolean flag = dpWebService.editWifi(dbUser.getAccount(), wifiUser.getPassword(),
                    dbUser.getOverdueTime().getTime(), dbUser.getAccount(), wifiUser.getId());
            if (flag) {
                objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            }
        } catch (Exception e) {
            log.error("WifiController.", e);
        }
        return objMap;
    }

    /**
     * 访问修改页面时,
     * 没有wifi账号的被重定向到新增页面
     */
    @RequestMapping("/admin/jiaowu/wifiuser/save")
    public String saveWifiUser() {
        return "/admin/umc/save_wifi_user";
    }

    /**
     * 新增wifi用户
     */
    @RequestMapping("/admin/jiaowu/wifiuser/doSave")
    @ResponseBody
    public Map<String, Object> doSaveWifiUser(@ModelAttribute("wifiUser") WifiUser wifiUser) {
        Map<String, Object> objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        try {
            String phone = wifiUser.getPhone();
            if (phone == null || StringUtils.isNotMobileNo(wifiUser.getPhone())) {
                return this.resultJson(ErrorCode.ERROR_SYSTEM, "请输入正确的手机号", null);
            }

            Map<String,String> stringMap = SysUserUtils.getLoginSysUser(request);

            Long linkId = Long.valueOf(stringMap.get("linkId"));
            User user = userBiz.findById(linkId);
            if (user == null) {
                return this.resultJson(ErrorCode.ERROR_SYSTEM, "当前用户不是有效的学员账号", null);
            }

            // 查询是否已存在wifi用户
            boolean flagDB = wifiUserBiz.alreadyExist(phone);
            if (flagDB) {
                return this.resultJson(ErrorCode.ERROR_SYSTEM, "您已开通wifi账号", null);
            }

            // 获得学员的报名的班
            Classes cla = classesBiz.findById(user.getClassId());

            boolean flag = dpWebService.saveWifiUser(wifiUser.getPhone(), stringMap.get("userName"),
                    cla.getEndTime(), linkId);
            if (flag) {
                objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            }
        } catch (Exception e) {
            log.error("WifiController.", e);
        }
        return objMap;
    }




}
