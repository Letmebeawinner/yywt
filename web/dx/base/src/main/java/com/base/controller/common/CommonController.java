package com.base.controller.common;

import com.a_268.base.controller.BaseController;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.a_268.base.util.WebUtils;
import com.base.biz.common.OAHessianService;
import com.base.biz.user.SysUserBiz;
import com.base.common.CommonConstants;
import com.base.entity.permission.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基础系统中公用的Controller
 *
 * @author s.li
 * @create 2016-12-14-14:48
 */
@Controller
@RequestMapping("/admin/base")
public class CommonController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserBiz sysUserBiz;
    @Autowired
    private OAHessianService oaHessianService;

    /**
     * @Description: 获取管理员显示的信息
     * @author: s.li
     * @Param: [request]
     * @Return: void
     * @Date: 2016/12/14
     */
    @RequestMapping("/querySysUserInfo")
    public void querySysUserInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (userMap != null) {
                userMap.put("password", null);
                String callback = request.getParameter("callback");
                //由于回复头没有指定content-type，Firefox将回复内容当成html解析。 由ajax指定的dataType : “json” ，并不对浏览器生效
                response.setContentType("text/plain");
                response.getWriter().print(callback + "(" + gson.toJson(userMap) + ")");
            }
        } catch (Exception e) {
            logger.error("querySysUserInfo()--error", e);
        }
    }

    /**
     * @Description: 公共获取对应系统权限列表
     * @author: s.li
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016/12/14
     */
    @RequestMapping("/queryChildSysAuthority")
    public void queryChildSysAuthority(HttpServletRequest request, HttpServletResponse response) {
        try {
            //得到登录的用户ID
            Long userId = SysUserUtils.getLoginSysUserId(request);
            if (ObjectUtils.isNull(userId)) {
                return;
            }
            if (userId > 0) {
                String sid = WebUtils.getCookie(request, com.a_268.base.constants.BaseCommonConstants.LOGIN_KEY);
                String sysKey = (String) redisCache.get(CommonConstants.THIS_SYS_KEY_PX + sid + userId);

                // OA的权限 显示未读消息
                Integer approvalNum = "OA".equals(sysKey) ? oaHessianService.approvalNum(userId) : 0;

                //得到用户的所有权限列表
                List<Resource> userResourceList = sysUserBiz.getSysUserResourceList(sid, userId);
                if (!CollectionUtils.isEmpty(userResourceList)) {
                    List<Resource> _sysResourceList = new ArrayList<>();
                    //获取当前访问站点的权限列表
                    for (Resource r : userResourceList) {
                        if (r.getResourceSite().equals(sysKey) && (r.getResourceType().intValue() == 1 || r.getParentId().longValue() == 0)) {
                            if (423 == r.getId() && approvalNum != 0) {
                                r.setResourceName(r.getResourceName() + "&nbsp;&nbsp;<div style=\" min-width:20px; height:20px; background:red; box-sizing:border-box;color: white;font-size: 10px;text-align: center; line-height: 20px; padding: 0 5px; border-radius: 10px; display: inline-block;\">" + approvalNum + "</div>");
                            }
                            _sysResourceList.add(r);
                        }
                    }
                    List<Resource> showResourceList = this.handleResourceLevel(_sysResourceList);
                    String callname = request.getParameter("callback");
                    //由于回复头没有指定content-type，Firefox将回复内容当成html解析。 由ajax指定的dataType : “json” ，并不对浏览器生效
                    response.setContentType("text/plain");
                    response.getWriter().print(callname + "(" + gson.toJson(showResourceList) + ")");
                }
            }
        } catch (Exception e) {
            logger.error("queryChildSysAuthority()--error", e);
        }
    }

    /**
     * @Description: 处理权限层级, 只要二级和三级，一级不能显示（页面样式而已原因）
     * @author: s.li
     * @Param: [resourceList]
     * @Return: java.util.List<com.base.entity.permission.Resource>
     * @Date: 2016/12/14
     */
    private List<Resource> handleResourceLevel(List<Resource> resourceList) {
        if (!CollectionUtils.isEmpty(resourceList)) {
            List<Resource> parentList = new ArrayList<>();
            for (Resource r : resourceList) {
                if (r.getParentId().longValue() == 0) {
                    for (Resource _r : resourceList) {
                        if (_r.getParentId().longValue() == r.getId().longValue()) {
                            List<Resource> childList = new ArrayList<>();
                            for (Resource _re : resourceList) {
                                if (_re.getParentId().longValue() == _r.getId().longValue()) {
                                    childList.add(_re);
                                }
                            }
                            _r.setChildList(childList);
                            parentList.add(_r);
                        }
                    }
                }
            }
            return parentList;
        }
        return null;
    }

}
