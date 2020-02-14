package com.oa.controller.car;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.car.CarBiz;
import com.oa.entity.car.Car;
import com.oa.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 车辆
 * @author lzh
 * @create 2016/12/27
 */
@Controller
@RequestMapping("/admin/oa")
public class CarController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarBiz carBiz;

    private static final String carList = "/car/car_list";
    private static final String toAddCar = "/car/car_add";
    private static final String toUpdateCar = "/car/car_update";

    @InitBinder("car")
    public void initBinderCar(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("car.");
    }

    /**
     * @Description:c
     * @author: lzh
     * @Param: request,pagination
     * @Return:  String
     * @Date: 2016-12-17
     */
    @RequestMapping("/queryAllCar")
    public String queryAllCar(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("car") Car car) {
        try {
            String whereSql = GenerateSqlUtil.getSql(car);
            pagination.setRequest(request);
            List<Car> carList = carBiz.find(pagination, whereSql);
            request.setAttribute("carList", carList);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("CarController.queryAllCar", e);
            return this.setErrorPath(request, e);
        }
        return carList;
    }


    /**
     * ajax查询所有未使用车辆信息，用用户下拉框
     * @return
     */
    @RequestMapping("/ajax/get/all/car")
    @ResponseBody
    public Map<String, Object> getAllCar() {
        Map<String, Object> json = null;
        String sql = " status = 0";
        try {
            List<Car> cars = carBiz.find(null, sql);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, cars);
        } catch (Exception e) {
            logger.error("carController.getAllCar", e);
            json = this.resultJson(ErrorCode.SYS_ERROR_MSG, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * ajax通过id获取车辆信息
     * @param id
     * @return
     */
    @RequestMapping("/ajax/ajax/car/getCar")
    @ResponseBody
    public Map<String, Object> getCarById(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            Car car = carBiz.findById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, car);
        } catch(Exception e) {
            logger.error("carController.getCarById", e);
            json = this.resultJson(ErrorCode.SYS_ERROR_MSG, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description:添加车辆
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-17
     */
    @RequestMapping("/toAddCar")
    public String toAddCar() {
        return toAddCar;
    }

    /**
     * @Description:去修改车辆
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-27
     */
    @RequestMapping("/toUpdateCar")
    public String toUpdateCar(HttpServletRequest request, @RequestParam("id") Long id,  @RequestParam(value = "flag", required = false) int flag) {
        try{
            Car car = carBiz.findById(id);
            request.setAttribute("car", car);
            request.setAttribute("flag", flag);
        } catch(Exception e) {
            logger.error("CarController.toUpdateCar", e);
            this.setErrorPath(request, e);
        }
        return toUpdateCar;
    }

    /**
     * @Description:保存车辆信息
     * @author: lzh
     * @Param: [car]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:59
     */
    @RequestMapping("/addCar")
    @ResponseBody
    public Map<String, Object> addSaveCar(HttpServletRequest request,@ModelAttribute("car") Car car) {
        Map<String,Object> resultMap = null;
           try {
               carBiz.save(car);
               resultMap=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
           } catch (Exception e) {
               logger.error("CarController.addSaveCar", e);
               return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
           }
           return resultMap;
    }

    /**
     * @Description:修改车辆
     * @author: lzh
     * @Param: [car]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:41
     */
    @RequestMapping("/updateCar")
    @ResponseBody
    public Map<String, Object> updateCar(HttpServletRequest request,@ModelAttribute("car") Car car) {
          Map<String, Object> resultMap = null;
          try {
              carBiz.update(car);
              resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
          } catch (Exception e) {
              logger.error("CarController.updateCar", e);
              resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, ErrorCode.SYS_ERROR_MSG, null);
          }
          return resultMap;
    }

    /**
     * @Description:删除车辆
     * @author: lzh
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:55
     */
    @RequestMapping("/deleteCar")
    @ResponseBody
    public Map<String, Object> delCar(@RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            carBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("CarController.delCar", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, ErrorCode.SYS_ERROR_MSG, null);
        }
        return resultMap;

    }

}
