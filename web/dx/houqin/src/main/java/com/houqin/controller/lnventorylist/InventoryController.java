package com.houqin.controller.lnventorylist;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.houqin.biz.lnventorylist.LnventorylistBiz;
import com.houqin.biz.meeting.MeetingBiz;
import com.houqin.entity.lnventorylist.Lnventorylist;
import com.houqin.entity.meeting.Meeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/14 0014.
 */
@Controller
@RequestMapping("/admin/houqin")
public class InventoryController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private LnventorylistBiz inventoryBiz;
    @Autowired
    private MeetingBiz meetingBiz;

    /**
     * 绑数据
     */
    @InitBinder({"lnventorylist"})
    public void initInventory(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("lnventorylist.");
    }

    /**
     * 查询清单列表
     *
     * @param meetingId
     * @param pagination
     * @return
     */
    @RequestMapping("/inventoryList")
    public ModelAndView InventoryList(@RequestParam("meetingId") Long meetingId, @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/lnventorylist/lnventory_list");
        try {
            String whereSql = "meetingId=" + meetingId;
            List<Lnventorylist> inventoryList = inventoryBiz.find(pagination, whereSql);

            Meeting meeting = meetingBiz.findById(meetingId);
            mv.addObject("meeting", meeting);
            mv.addObject("inventoryList", inventoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 去批量导入
     *
     * @param request
     * @param meetingId
     * @return
     */
    @RequestMapping("/toImport")
    public ModelAndView toImport(HttpServletRequest request, @RequestParam("id") Long meetingId) {
        ModelAndView mv = new ModelAndView("/lnventorylist/import_lnventory");
        try {
            mv.addObject("meetingId", meetingId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 批量导入
     *
     * @param request 请求
     * @param myFile  文件
     * @return 错误信息
     */
    @RequestMapping("/import")
    public String importInventory(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile, @RequestParam("meetingId") Long meetingId) {
        try {
            String errorInfo = inventoryBiz.batchImport(myFile, request, meetingId);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                request.setAttribute("errorInfo", errorInfo);
                return "/admin/houqin/toImport.json?meetingId=" + meetingId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/houqin/inventoryList.json?meetingId=" + meetingId;
    }


    /**
     * @Description:删除
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String   ,   java.lang.Object>
     */
    @RequestMapping("/delInventory")
    @ResponseBody
    public Map<String, Object> delInventory(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            String id = request.getParameter("id");
            Lnventorylist lnventorylist = inventoryBiz.findById(Long.parseLong(id));
            inventoryBiz.deleteById(Long.parseLong(id));
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, lnventorylist.getMeetingId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 去添加会场清单
     *
     * @param request
     * @param meetingId
     * @return
     */
    @RequestMapping("/toAddLnventorylist")
    public ModelAndView toAddLnventorylist(HttpServletRequest request, @RequestParam("id") Long meetingId) {
        ModelAndView mv = new ModelAndView("/lnventorylist/lnventorylist_add");
        try {
            mv.addObject("meetingId", meetingId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 添加清单
     *
     * @param lnventorylist
     * @return
     */
    @RequestMapping("/addLnventorylist")
    @ResponseBody
    public Map<String, Object> addLnventorylist(@ModelAttribute("lnventorylist") Lnventorylist lnventorylist) {
        Map<String, Object> json = null;
        try {
            lnventorylist.setStatus(0);
            inventoryBiz.save(lnventorylist);
            json = this.resultJson(ErrorCode.SUCCESS, "添加成功", lnventorylist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 去修改会场清单
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateLnventorylist")
    public ModelAndView toUpdateLnventorylist(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("/lnventorylist/lnventorylist_update");
        try {
            Lnventorylist lnventorylist = inventoryBiz.findById(id);
            mv.addObject("lnventorylist", lnventorylist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 修改会场清单
     *
     * @param request
     * @param lnventorylist
     * @return
     */
    @RequestMapping("/updateLnventorylist")
    @ResponseBody
    public Map<String, Object> updateLnventorylist(HttpServletRequest request, @ModelAttribute("lnventorylist") Lnventorylist lnventorylist) {
        Map<String, Object> json = null;
        try {
            inventoryBiz.update(lnventorylist);
            json = this.resultJson(ErrorCode.SUCCESS, "修改成功", lnventorylist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}
