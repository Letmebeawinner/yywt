package com.jiaowu.controller.classes;

import com.a_268.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 排课导出
 *
 * @author YaoZhen
 * @date 06-05, 14:24, 2018.
 */
@Slf4j
@Controller
@RequestMapping("/admin/jiaowu")
public class ClassExportController extends BaseController {

    /**
     * 导出课表
     */
    @RequestMapping("exportClassSch")
    public ModelAndView queryExportClassSch(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("/admin/class/export_class");
        try {
            mv.addObject("classId", id);
        } catch (Exception e) {
            log.error("ClassExportController.queryExportClassSch", e);
        }
        return mv;
    }
}
