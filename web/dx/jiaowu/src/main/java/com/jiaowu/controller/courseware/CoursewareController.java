package com.jiaowu.controller.courseware;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.jiaowu.biz.courseware.CoursewareBiz;
import com.jiaowu.biz.teacherLibrary.TeacherLibraryBiz;
import com.jiaowu.entity.courseware.Courseware;
import com.jiaowu.entity.teacherLibrary.TeacherLibrary;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by 李帅雷 on 2017/10/21.
 */
@Controller
public class CoursewareController extends BaseController{
    private static final Logger logger = Logger.getLogger(CoursewareController.class);
    public static final String ADMIN_PREFIX = "/admin/jiaowu/courseware";

    @Autowired
    private CoursewareBiz coursewareBiz;
    @Autowired
    private TeacherLibraryBiz teacherLibraryBiz;

    @InitBinder("courseware")
    public void initBinderCourseware(WebDataBinder binder){
        binder.setFieldDefaultPrefix("courseware.");
    }

    /**
     * 跳转到添加课件的页面
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toCreateCourseware")
    public String toCreateCourseware(HttpServletRequest request) {
        try{
            List<TeacherLibrary> teacherLibraryList=teacherLibraryBiz.find(null," status=1");
            request.setAttribute("teacherLibraryList",teacherLibraryList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/courseware/create_courseware";
    }


    /**
     * 添加课件
     *
     * @return json
     */
    @RequestMapping(ADMIN_PREFIX + "/createCourseware")
    @ResponseBody
    public Map<String, Object> createCourseware(@ModelAttribute("courseware") Courseware courseware) {
        Map<String, Object> json;
        try {
            coursewareBiz.save(courseware);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("CoursewareController.createCourseware", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 删除课件
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/delCourseware")
    @ResponseBody
    public Map<String,Object> delCourseware(Long id){
        Map<String, Object> json;
        try {
            coursewareBiz.deleteById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("CoursewareController.createCourseware", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 课件列表
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/coursewareList")
    public String coursewareList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,@ModelAttribute("courseware")Courseware courseware){
        try{
            List<TeacherLibrary> teacherLibraryList=teacherLibraryBiz.find(null," status=1");
            request.setAttribute("teacherLibraryList",teacherLibraryList);

            String whereSql = " status=1";
            if(courseware.getTeacherLibraryId()!=null&&!courseware.getTeacherLibraryId().equals(0L)){
                whereSql+=" and teacherLibraryId="+courseware.getTeacherLibraryId();
            }
            pagination.setRequest(request);
            List<Courseware> coursewareList = coursewareBiz.find(pagination,whereSql);
            request.setAttribute("coursewareList",coursewareList);
        }catch(Exception e){
            logger.info("CoursewareController.coursewareList",e);
        }
        return "/admin/courseware/courseware_list";
    }
}
