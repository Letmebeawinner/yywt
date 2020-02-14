package com.houqin.controller.propertymessage;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.property.PropertyBiz;
import com.houqin.biz.property.PropertySourceBiz;
import com.houqin.biz.propertymessage.InsidePropertyMessageBiz;
import com.houqin.biz.propertymessage.OutOfPropertyMessageBiz;
import com.houqin.biz.propertymessage.PropertyMessageBiz;
import com.houqin.biz.storage.WareHouseBiz;
import com.houqin.common.BaseHessianService;
import com.houqin.entity.property.Property;
import com.houqin.entity.property.PropertySource;
import com.houqin.entity.propertymessage.InsidePropertyMessage;
import com.houqin.entity.propertymessage.OutOfPropertyMessage;
import com.houqin.entity.propertymessage.PropertyMessage;
import com.houqin.entity.propertymessage.PropertyMessageDto;
import com.houqin.entity.storage.WareHouse;
import com.houqin.entity.sysuser.SysUser;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资产信息
 * Created by Administrator on 2016/12/16.
 */
@Controller
@RequestMapping("/admin/houqin")
public class PropertyMessageController extends BaseController {
    //添加资产信息
    private static final String agianPropertyMessage = "/propertymessage/again-propertyMessage";
    //再次入库
    private static final String createPropertyMessage = "/propertymessage/add-propertyMessage";
    //修改资产信息
    private static final String toUpdatePropertyMessage = "/propertymessage/update-propertyMessage";
    //资产信息详情
    private static final String infoPropertyMessage = "/propertymessage/info-propertyMessage";
    //资产信息详情
    private static final String insideInfoPropertyMessage = "/propertymessage/info-propertyMessage";
    //资产信息列表
    private static final String propertyMessageList = "/propertymessage/propertyMessage_list";
    private static Logger logger = LoggerFactory.getLogger(PropertyMessageController.class);
    @Autowired
    private PropertyMessageBiz propertyMessageBiz;
    @Autowired
    private PropertyBiz propertyBiz;
    @Autowired
    private OutOfPropertyMessageBiz outOfPropertyMessageBiz;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private WareHouseBiz wareHouseBiz;
    @Autowired
    private PropertySourceBiz propertySourceBiz;
    @Autowired
    private InsidePropertyMessageBiz insidePropertyMessageBiz;

    @InitBinder("propertyMessage")
    public void initPropertyMessage(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("propertyMessage.");
    }

    @InitBinder("outOfPropertyMessage")
    public void initOutOfPropertyMessage(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("outOfPropertyMessage.");
    }

    @InitBinder("insidePropertyMessage")
    public void initInsideOfPropertyMessage(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("insidePropertyMessage.");
    }

    @InitBinder
    protected void initRepairBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 查询资产信息列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/queryAllPropertyMessage")
    public String queryAllPropertyMessage(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("propertyMessage") PropertyMessage propertyMessage) {
        try {
            String whereSql = GenerateSqlUtil.getSql(propertyMessage);
            whereSql = whereSql + " and status!=4 order by id desc";
            pagination.setRequest(request);
            List<PropertyMessageDto> propertyMessageDtoList = propertyMessageBiz.getAllPropertyMessage(pagination, whereSql);
            request.setAttribute("propertyMessageDtoList", propertyMessageDtoList);
            request.setAttribute("propertyMessage", propertyMessage);

            List<Property> propertyList = propertyBiz.find(null, "1=1");
            request.setAttribute("propertyList", propertyList);

            //库房列表
            List<WareHouse> wareHouseList = wareHouseBiz.find(null, "1=1");
            request.setAttribute("wareHouseList", wareHouseList);
        } catch (Exception e) {
            logger.info("PropertyMessageController--queryAllPropertyMessage", e);
            return this.setErrorPath(request, e);
        }
        return propertyMessageList;
    }


    /**
     * 去添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddPropertyMessage")
    public String toAddPropertyMessage(HttpServletRequest request) {
        try {
            //资产类型
            List<Property> propertyList = propertyBiz.find(null, "1=1");
            request.setAttribute("propertyList", propertyList);

            //库房列表
            List<WareHouse> wareHouseList = wareHouseBiz.find(null, "1=1");
            request.setAttribute("wareHouseList", wareHouseList);

            List<PropertySource> propertySourceList = propertySourceBiz.find(null, "1=1");
            request.setAttribute("propertySourceList", propertySourceList);

        } catch (Exception e) {
            logger.info("PropertyMessageController--toAddPropertyMessage", e);
            return this.setErrorPath(request, e);
        }
        return createPropertyMessage;
    }

    /**
     * 添加资产信息
     *
     * @param request
     * @param propertyMessage
     * @return
     */
    @RequestMapping("/addSavePropertyMessage")
    @ResponseBody
    public Map<String, Object> addSavePropertyMessage(HttpServletRequest request, @ModelAttribute("propertyMessage") PropertyMessage propertyMessage) {
        try {
            // 数量不填时 显示为0
            if (ObjectUtils.isNull(propertyMessage.getAmount())) {
                propertyMessage.setAmount(0);
            }

            //管理员
            Long userId = SysUserUtils.getLoginSysUserId(request);
            propertyMessage.setUserId(userId);
            propertyMessage.setStatus(0);
            propertyMessageBiz.tx_addInsidePropertyMessage(propertyMessage);
        } catch (Exception e) {
            logger.info("PropertyMessageController--addSavePropertyMessage", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 再次入库
     *
     * @return
     */
    @RequestMapping("/toAgainPropertyMessage")
    public String toAgainPropertyMessage(@RequestParam(value = "id", required = true) Long id) {
        try {
            PropertyMessage propertyMessage = propertyMessageBiz.findById(id);
            request.setAttribute("propertyMessage", propertyMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agianPropertyMessage;
    }

    /**
     * 再次入库
     *
     * @return
     */
    @RequestMapping("/againPropertyMessage")
    @ResponseBody
    public Map<String, Object> againPropertyMessage(@ModelAttribute("propertyMessage") PropertyMessage propertyMessage) {
        Map<String, Object> json = null;
        try {
            if (ObjectUtils.isNotNull(propertyMessage)) {
                propertyMessageBiz.tx_editPropertyMessage(propertyMessage);
                json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 出库
     *
     * @param propertyMessageId
     * @return
     */
    @RequestMapping("/outOfPropertyMessage")
    public ModelAndView outOfPropertyMessage(@RequestParam("id") Long propertyMessageId) {
        ModelAndView mv = new ModelAndView("/propertymessage/out_property_message");
        try {
            //查询所有部门
            List<Map<String, String>> departmentList = baseHessianService.queryAllDepartment();
            mv.addObject("departmentList", departmentList);

            // 显示剩余数量
            PropertyMessage propertyMessage = propertyMessageBiz.findById(propertyMessageId);
            mv.addObject("propertyMessage", propertyMessage);


        } catch (Exception e) {
            logger.info("PropertyMessageController--outOfPropertyMessage", e);
        }

        return mv;
    }

    /**
     * 保存出库的信息
     */
    @RequestMapping("/saveOutPM")
    @ResponseBody
    public Map<String, Object> saveOutPM(
            @ModelAttribute("outOfPropertyMessage") OutOfPropertyMessage outOfPropertyMessage,
            @RequestParam("amount") Integer amount) {
        Map<String, Object> resultMap;
        try {
            // 出库数量不能大于库存
            Integer outboundNumber = outOfPropertyMessage.getOutboundNumber();
            if (outboundNumber > amount) {
                return this.resultJson(ErrorCode.ERROR_DATA, "出库数量不能大于库存", null);
            } else {
                // 计算剩余库存
                Integer i = amount - outboundNumber;
                PropertyMessage message = new PropertyMessage();
                message.setId(outOfPropertyMessage.getOutboundItemId());
                message.setAmount(i);
                propertyMessageBiz.update(message);
            }

            //生成单号
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Calendar calendar = Calendar.getInstance();
            outOfPropertyMessage.setSerialNo("CK" + sdf.format(calendar.getTime()));

            PropertyMessage propertyMessage = propertyMessageBiz.findById(outOfPropertyMessage.getOutboundItemId());
            WareHouse wareHouse = wareHouseBiz.findById(propertyMessage.getStorageId());
            if (wareHouse == null) {
                return this.resultJson(ErrorCode.ERROR_DATA, "该库房不存在, 请核对后再试", null);
            }
            outOfPropertyMessage.setStorageName(wareHouse.getName());
            // 存入db
            outOfPropertyMessageBiz.save(outOfPropertyMessage);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("PropertyMessageController--saveOutPM", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return resultMap;
    }


    /**
     * 跳转到出库列表
     */
    @RequestMapping("/listInsidePM")
    public ModelAndView listInsidePM(@ModelAttribute("pagination") Pagination pagination, @ModelAttribute("insidePropertyMessage") InsidePropertyMessage insidePropertyMessage) {
        ModelAndView mv = new ModelAndView("/propertymessage/inside_property_message");
        try {
            String whereSql = GenerateSqlUtil.getSql(insidePropertyMessage);
            whereSql += " order by id desc";
            pagination.setRequest(request);
            List<InsidePropertyMessage> insidePropertyMessageList = insidePropertyMessageBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(insidePropertyMessageList)) {
                for (InsidePropertyMessage insidePropertyMessage1 : insidePropertyMessageList) {
                    WareHouse wareHouse = wareHouseBiz.findById(insidePropertyMessage1.getStorageId());
                    if (ObjectUtils.isNotNull(wareHouse)) {
                        insidePropertyMessage1.setStorageName(wareHouse.getName());

                    }
                    //系统用户
                    SysUser sysUser = baseHessianBiz.getSysUserById(insidePropertyMessage1.getUserId());
                    if (ObjectUtils.isNotNull(sysUser)) {
                        insidePropertyMessage1.setUserName(sysUser.getUserName());
                    }
                    //资产类型
                    Property property = propertyBiz.findById(insidePropertyMessage1.getPropertyId());
                    if (ObjectUtils.isNotNull(property)) {
                        insidePropertyMessage1.setPropertyType(property.getTypeName());
                    }
                }
            }
            mv.addObject("insidePropertyMessageList", insidePropertyMessageList);
            mv.addObject("insidePropertyMessage", insidePropertyMessage);
        } catch (Exception e) {
            logger.error("PropertyMessageController--listInsidePM", e);
        }
        return mv;
    }


    /**
     * 跳转到出库列表
     */
    @RequestMapping("/listOutPM")
    public ModelAndView listOutPM(@ModelAttribute("pagination") Pagination pagination,
                                  @ModelAttribute("outOfPropertyMessage") OutOfPropertyMessage outOfPropertyMessage) {
        ModelAndView mv = new ModelAndView("/propertymessage/list_property_message");
        try {
            String whereSql = GenerateSqlUtil.getSql(outOfPropertyMessage);
            whereSql += " order by id desc";
            pagination.setRequest(request);
            List<OutOfPropertyMessage> out = outOfPropertyMessageBiz.find(pagination, whereSql);
            mv.addObject("out", out);
        } catch (Exception e) {
            logger.error("PropertyMessageController--listOutPM", e);
        }
        return mv;
    }

    /**
     * 查看出库记录详情
     */
    @RequestMapping("/detailOpm")
    public ModelAndView detailOpm(@RequestParam("id") Long OpmId) {
        ModelAndView mv = new ModelAndView("/propertymessage/detail_property_message");
        try {
            OutOfPropertyMessage o = outOfPropertyMessageBiz.findById(OpmId);
            mv.addObject("obj", o);
        } catch (Exception e) {
            logger.error("PropertyMessageController--detailOpm", e);
        }
        return mv;
    }

    /**
     * 去修改页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdatePropertyMessage")
    public String toUpdatePropertyMessage(HttpServletRequest request,
                                          @RequestParam(value = "id", required = true) Long id) {
        try {
            List<Property> propertyList = propertyBiz.find(null, "1=1");
            request.setAttribute("propertyList", propertyList);

            PropertyMessage propertyMessage = propertyMessageBiz.findById(id);
            request.setAttribute("propertyMessage", propertyMessage);

            //库房列表
            List<WareHouse> wareHouseList = wareHouseBiz.find(null, "1=1");
            request.setAttribute("wareHouseList", wareHouseList);

            List<PropertySource> propertySourceList = propertySourceBiz.find(null, "1=1");
            request.setAttribute("propertySourceList", propertySourceList);
        } catch (Exception e) {
            logger.error("PropertyMessageController--toUpdatePropertyMessage", e);
            return this.setErrorPath(request, e);
        }
        return toUpdatePropertyMessage;
    }

    /**
     * 修改资产信息
     *
     * @param propertyMessage
     * @return
     */
    @RequestMapping("/updatePropertyMessage")
    @ResponseBody
    public Map<String, Object> updatePropertyMessage(HttpServletRequest request, @ModelAttribute("propertyMessage") PropertyMessage propertyMessage) {
        Map<String, Object> resultMap;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            propertyMessage.setUserId(userId);

            propertyMessageBiz.update(propertyMessage);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("PropertyMessageController--updatePropertyMessage", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * 根据id删除资产信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/deletePropertyMessage")
    @ResponseBody
    public Map<String, Object> deletePropertyMessage(HttpServletRequest request) {
        try {
            String ids = request.getParameter("id");
            propertyMessageBiz.deleteById(Long.parseLong(ids));
        } catch (Exception e) {
            logger.info("PropertyMessageController--deletePropertyMessage", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 跳转到批量添加资产信息页面
     *
     * @return
     */
    @RequestMapping("/toBatchImportPropertyMessage")
    public String toBatchImportPropertyMessage() {
        //库房列表
        List<WareHouse> wareHouseList = wareHouseBiz.find(null, "1=1");
        request.setAttribute("wareHouseList", wareHouseList);
        return "/propertymessage/batch_import_property_message";
    }

    /**
     * 批量导入
     *
     * @param request 请求
     * @param myFile  文件
     * @return 错误信息
     */
    @RequestMapping("/batchImportPropertyMessage")
    public String batchImportUser(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        try {
            String errorInfo = propertyMessageBiz.batchImport(myFile, request);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                request.setAttribute("errorInfo", errorInfo);
                return "/propertymessage/batch_import_property_message";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/houqin/queryAllPropertyMessage.json";
    }


    /**
     * 去修改页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/detailPropertyMessage")
    public String detailPropertyMessage(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {

            PropertyMessage propertyMessage = propertyMessageBiz.detailById(id);
            request.setAttribute("propertyMessage", propertyMessage);

            List<Property> propertyList = propertyBiz.find(null, "1=1");
            request.setAttribute("propertyList", propertyList);
        } catch (Exception e) {
            logger.error("PropertyMessageController--detailPropertyMessage", e);
            return this.setErrorPath(request, e);
        }
        return infoPropertyMessage;
    }


    /**
     * 查看入库详情
     *
     * @return
     */
    @RequestMapping("/detailInsidePropertyMessage")
    public ModelAndView detailInsidePropertyMessage(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("/propertymessage/inside-info-propertyMessage");
        try {
            InsidePropertyMessage propertyMessage = insidePropertyMessageBiz.findById(id);
            request.setAttribute("propertyMessage", propertyMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;

    }


}
