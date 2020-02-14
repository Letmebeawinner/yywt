package com.base.controller.sysuserlog;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.base.biz.sysuserlog.SysUserLogBiz;
import com.base.biz.user.SysUserBiz;
import com.base.entity.sysuserlog.SysUserLog;
import com.base.entity.user.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/base")
public class SysUserlogController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SysUserlogController.class);

    @Autowired
    private SysUserLogBiz sysUserLogBiz;

    @Autowired
    private SysUserBiz sysUserBiz;

    /**
     * 查询登录日志
     *
     * @return
     */
    @RequestMapping("/querySysUserlogList")
    public String querySysUserlogList(HttpServletRequest request, @ModelAttribute("sysUserLog") SysUserLog sysUserLog,
                                      @ModelAttribute("pagination") Pagination pagination) {
        try {
            pagination.setRequest(request);
            String whereSq = "1=1 order by id desc";
            List<SysUserLog> sysUserLogList = sysUserLogBiz.find(pagination, whereSq);
            if (ObjectUtils.isNotNull(sysUserLogList)) {
                for (SysUserLog userLog : sysUserLogList) {
                    SysUser sysUser = sysUserBiz.findById(userLog.getUserId());
                    userLog.setUserName(sysUser.getUserName());
                }
            }
            request.setAttribute("sysUserLogList", sysUserLogList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/sysuserlog/sysuser-log-list";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteLogById")
    @ResponseBody
    public Map<String, Object> deleteLogById(@RequestParam("id") Long id) {
        try {
            sysUserLogBiz.deleteById(id);
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, "");

    }


}
