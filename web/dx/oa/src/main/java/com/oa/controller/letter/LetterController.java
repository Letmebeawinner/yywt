package com.oa.controller.letter;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.letter.LetterBiz;
import com.oa.biz.letter.UserLetterBiz;
import com.oa.biz.workflow.OaLetterBiz;
import com.oa.entity.letter.Letter;
import com.oa.entity.letter.UserLetter;
import com.oa.entity.workflow.OaLetter;
import com.oa.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 公文
 *
 * @author ccl
 * @create 2017-03-08-18:45
 */
@Controller
@RequestMapping("/admin/oa")
public class LetterController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LetterController.class);

    @Autowired
    private LetterBiz letterBiz;
    @Autowired
    private UserLetterBiz userLetterBiz;
    @Autowired
    private OaLetterBiz oaLetterBiz;

    @InitBinder({"letter"})
    public void initLetter(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("letter.");
    }

    @InitBinder({"oaLetter"})
    public void initOaLetter(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("oaLetter.");
    }

    private static final String toAddLetter = "/letter/letter_add";//添加公文
    private static final String toUpdateLetter = "/letter/letter_update";//修改公文
    private static final String letterList = "/letter/letter_list";//公文列表
    private static final String myLetterList = "/letter/myLetter_list";//公文列表
    private static final String oaLetterSendToMe = "/letter/oa_letter_send_to_me_list";//公文列表


    /**
     * @Description:查询所有公文
     * @author: ccl
     * @Param: [request, pagination, letter]
     * @Return: java.lang.String
     * @Date: 2017-03-08
     */
    @RequestMapping("/queryAllLetter")
    public String queryAllLetter(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("letter") Letter letter) {
        try {
            String whereSql = GenerateSqlUtil.getSql(letter);
            List<Letter> letterList = letterBiz.find(pagination, whereSql);
            request.setAttribute("letterList", letterList);
            request.setAttribute("letter", letter);
        } catch (Exception e) {
            logger.error("LetterController--queryAllLetter", e);
            return this.setErrorPath(request, e);
        }
        return letterList;
    }


    /**
     * @Description:我的公文列表
     * @author: ccl
     * @Param: [request, pagination, letter]
     * @Return: java.lang.String
     * @Date: 2017-03-09
     */
    @RequestMapping("/queryMyLetter")
    public String queryMyLetter(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("letter") Letter letter) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            if (userId != null && userId > 0) {
                letter.setSysUserId(userId);
            }
            String whereSql = GenerateSqlUtil.getSql(letter);
            List<Letter> letterList = letterBiz.find(pagination, whereSql);
            request.setAttribute("letterList", letterList);
            request.setAttribute("letter", letter);
        } catch (Exception e) {
            logger.error("LetterController--queryMyLetter", e);
            return this.setErrorPath(request, e);
        }
        return myLetterList;
    }

    /**
     * 查询发送给我的公文
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/letter/send/to/me")
    public String querySendToMeLetter(HttpServletRequest request,
                                      @ModelAttribute("pagination") Pagination pagination,
                                      @ModelAttribute("oaLetter") OaLetter oaLetter) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String sql = "userId = " + userId;
            List<OaLetter> oaLetters = null;
            List<UserLetter> userLetters = userLetterBiz.find(null, sql);
            if (userLetters == null || userLetters.size() == 0) {
                oaLetters = oaLetterBiz.find(pagination, " 1 = 0");
            } else {
                String letterIds = userLetters.stream()
                        .map(userLetter -> userLetter.getLetterId() + "")
                        .collect(Collectors.joining(",", "(", ")"));
                String letterSql = GenerateSqlUtil.getSql(oaLetter);
                oaLetters = oaLetterBiz.find(pagination, letterSql + " and id in" + letterIds);
            }
            request.setAttribute("oaLetters", oaLetters);
            request.setAttribute("oaLetter", oaLetter);
        } catch (Exception e) {
            logger.error("LetterController--querySendToMeLetter", e);
            return this.setErrorPath(request, e);
        }
        return oaLetterSendToMe;
    }


    /**
     * @Description:去添加公文
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2017-03-08
     */
    @RequestMapping("/toAddLetter")
    public String toAddLetter(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            logger.error("LetterController--toAddLetter", e);
            return this.setErrorPath(request, e);
        }
        return toAddLetter;
    }


    /**
     * @Description:添加保存
     * @author: ccl
     * @Param: [request, letter]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-03-08
     */
    @RequestMapping("/addLetter")
    @ResponseBody
    public Map<String, Object> addLetter(HttpServletRequest request, @ModelAttribute("letter") Letter letter) {
        Map<String, Object> resultMap = null;
        try {

            //获取系统用户id
            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            letter.setSysUserId(sysUserId);

            letterBiz.save(letter);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("LetterController--addLetter", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:去修改公文
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2017-03-08
     */
    @RequestMapping("/toUpdateLetter")
    public String toUpdateLetter(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            Letter letter = letterBiz.findById(id);
            request.setAttribute("letter", letter);
        } catch (Exception e) {
            logger.error("LetterController--toUpdateLetter", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateLetter;
    }


    /**
     * @Description:修改公文
     * @author: ccl
     * @Param: [request, letter]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-03-08
     */
    @RequestMapping("updateLetter")
    @ResponseBody
    public Map<String, Object> updateLetter(HttpServletRequest request, @ModelAttribute("letter") Letter letter) {
        Map<String, Object> resultMap = null;
        try {
            letterBiz.update(letter);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("LetterController--updateLetter", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:删除公文
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-03-08
     */
    @RequestMapping("/delLetter")
    @ResponseBody
    public Map<String, Object> delLetter(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            letterBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("LetterController--delLetter", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

}
