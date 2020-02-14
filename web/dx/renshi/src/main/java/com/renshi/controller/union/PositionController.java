package com.renshi.controller.union;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.renshi.biz.union.PositionBiz;
import com.renshi.entity.union.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/rs")
public class PositionController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PositionController.class);

    @InitBinder("position")
    public void initPosition(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("position.");
    }
    @Autowired
    private PositionBiz positionBiz;

    /**
     * 跳转职位添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddPosition")
    public ModelAndView toAddPosition(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/union/position_add");
        try {
        } catch (Exception e) {
            logger.error("toAddPosition", e);
        }
        return modelAndView;
    }

    /**
     * 添加职位
     *
     * @param request
     * @param position
     * @return
     */
    @RequestMapping("/addPosition")
    @ResponseBody
    public Map<String, Object> addPosition(HttpServletRequest request, @ModelAttribute("position")Position position) {
        Map<String, Object> objectMap = null;
        try {
            position.setStatus(1);
            positionBiz.save(position);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", position);
        } catch (Exception e) {
            logger.error("addPosition", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 删除职位
     *
     * @param request
     * @return
     */
    @RequestMapping("/deletePosition")
    @ResponseBody
    public Map<String, Object> addPosition(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        Map<String, Object> objectMap = null;
        try {
            Position position = positionBiz.findById(id);
            position.setStatus(2);
            positionBiz.update(position);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", position);
        } catch (Exception e) {
            logger.error("addPosition", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 职位详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/getPositionInfo")
    public ModelAndView getPositionById(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/union/position_info");
        try {
            Position position = positionBiz.findById(id);
            modelAndView.addObject("position", position);
        } catch (Exception e) {
            logger.error("getPositionInfo", e);
        }
        return modelAndView;
    }

    /**
     * 去修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdatePosition")
    public ModelAndView toUpdatePosition(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/union/position_update");
        try {
            Position position = positionBiz.findById(id);
            modelAndView.addObject("position", position);
        } catch (Exception e) {
            logger.error("toUpdatePosition", e);
        }
        return modelAndView;
    }

    /**
     * 修改职位
     *
     * @param request
     * @param position
     * @return
     */
    @RequestMapping("/updatePosition")
    @ResponseBody
    public Map<String, Object> updatePosition(HttpServletRequest request, @ModelAttribute("position") Position position) {
        Map<String, Object> objectMap = null;
        try {
            positionBiz.update(position);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", position);
        } catch (Exception e) {
            logger.error("updatePosition", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", position);
        }
        return objectMap;
    }

    /**
     * 职位列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getPositionList")
    public ModelAndView getPositionList(HttpServletRequest request,
                                        @ModelAttribute("pagination") Pagination pagination,
                                        @ModelAttribute("position") Position position) {
        ModelAndView modelAndView = new ModelAndView("/union/position_list");
        try {
            pagination.setRequest(request);
            List<Position> positionList = positionBiz.getPositionList(position,pagination);
            modelAndView.addObject("positionList", positionList);
        } catch (Exception e) {
            logger.error("getPositionList", e);
        }
        return modelAndView;
    }
    /**
     * 职位列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/ajax/selectPositionList")
    public ModelAndView selectPositionList(HttpServletRequest request,
                                        @ModelAttribute("position") Position position) {
        ModelAndView modelAndView = new ModelAndView("/union/select_position_list");
        try {
            List<Position> positionList = positionBiz.getPositionList(position,null);
            modelAndView.addObject("positionList", positionList);
        } catch (Exception e) {
            logger.error("getPositionList", e);
        }
        return modelAndView;
    }
}
