package com.houqin.biz.propertymessage;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.property.PropertyBiz;
import com.houqin.biz.property.PropertyCleanBiz;
import com.houqin.biz.property.PropertySourceBiz;
import com.houqin.biz.storage.WareHouseBiz;
import com.houqin.dao.propertymessage.PropertyMessageDao;
import com.houqin.entity.property.Property;
import com.houqin.entity.property.PropertyClean;
import com.houqin.entity.property.PropertySource;
import com.houqin.entity.propertymessage.InsidePropertyMessage;
import com.houqin.entity.propertymessage.PropertyMessage;
import com.houqin.entity.propertymessage.PropertyMessageDto;
import com.houqin.entity.storage.WareHouse;
import com.houqin.entity.sysuser.SysUser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 资产信息
 * Created by Administrator on 2016/12/16.
 */
@Service
public class PropertyMessageBiz extends BaseBiz<PropertyMessage, PropertyMessageDao> {

    /**
     * 导入判断日期格式
     */
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PropertyBiz propertyBiz;


    @Autowired
    private BaseHessianBiz baseHessianBiz;

    @Autowired
    private WareHouseBiz wareHouseBiz;

    @Autowired
    private PropertyCleanBiz propertyCleanBiz;

    @Autowired
    private PropertySourceBiz propertySourceBiz;

    @Autowired
    private InsidePropertyMessageBiz insidePropertyMessageBiz;


    /**
     * 入库
     *
     * @param propertyMessage
     */
    public void againPropertyMessage(PropertyMessage propertyMessage) {
        PropertyMessage _propertyMessage = this.findById(propertyMessage.getId());
        _propertyMessage.setAmount(propertyMessage.getAmount());
        _propertyMessage.setPrice(propertyMessage.getPrice());
        InsidePropertyMessage insidePropertyMessage = new InsidePropertyMessage();
        BeanUtils.copyProperties(_propertyMessage, insidePropertyMessage);
        insidePropertyMessage.setId(null);
        //生成单号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        StringBuffer number = new StringBuffer("RK");
        number.append(sdf.format(calendar.getTime()));
        insidePropertyMessage.setInsideNumber(number.toString());
        insidePropertyMessageBiz.save(insidePropertyMessage);
    }

    /**
     * 入库
     *
     * @param propertyMessage
     */
    public void tx_addInsidePropertyMessage(PropertyMessage propertyMessage) {
        this.save(propertyMessage);
        InsidePropertyMessage insidePropertyMessage = new InsidePropertyMessage();
        insidePropertyMessage.setSource(propertyMessage.getSource());
        insidePropertyMessage.setUserId(propertyMessage.getUserId());
        insidePropertyMessage.setUnit(propertyMessage.getUnit());
        insidePropertyMessage.setStorageId(propertyMessage.getStorageId());
        insidePropertyMessage.setPrice(propertyMessage.getPrice());
        insidePropertyMessage.setProduct(propertyMessage.getProduct());
        insidePropertyMessage.setAmount(propertyMessage.getAmount());
        insidePropertyMessage.setPropertyId(propertyMessage.getPropertyId());
        insidePropertyMessage.setName(propertyMessage.getName());

        //生成单号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        StringBuffer number = new StringBuffer("RK");
        number.append(sdf.format(calendar.getTime()));
        System.out.println("number=" + number);
        insidePropertyMessage.setInsideNumber(number.toString());
        insidePropertyMessageBiz.save(insidePropertyMessage);
    }


    public List<PropertyMessageDto> getAllPropertyMessage(@ModelAttribute("pagination") Pagination pagination, String whereSql) {
        List<PropertyMessage> propertyMessageList = this.find(pagination, whereSql);
        List<PropertyMessageDto> propertyMessageDtoList = new ArrayList<>();
        if (propertyMessageList != null && propertyMessageList.size() > 0) {
            for (PropertyMessage propertyMessage : propertyMessageList) {
                PropertyMessageDto dto = new PropertyMessageDto();

                //查询详情
                PropertyMessage propertyMessage1 = this.findById(propertyMessage.getId());
                BeanUtils.copyProperties(propertyMessage1, dto);

                //资产类型
                Property property = propertyBiz.findById(propertyMessage.getPropertyId());
                if (ObjectUtils.isNotNull(property)) {
                    dto.setPropertyTypeName(property.getTypeName());
                }

                //系统用户
                SysUser sysUser = baseHessianBiz.getSysUserById(propertyMessage1.getUserId());
                if (ObjectUtils.isNotNull(sysUser)) {
                    dto.setSysUserName(sysUser.getUserName());
                }

                //库房
                WareHouse wareHouse = wareHouseBiz.findById(propertyMessage1.getStorageId());
                if (ObjectUtils.isNotNull(wareHouse)) {
                    dto.setWareHouseName(wareHouse.getName());
                }

                // 来源
                PropertySource propertySource = propertySourceBiz.findById(propertyMessage1.getSource());
                if (ObjectUtils.isNotNull(propertySource)) {
                    dto.setPropertySource(propertySource.getName());
                }
                propertyMessageDtoList.add(dto);
            }
        }
        return propertyMessageDtoList;
    }


    public PropertyMessage detailById(Long id) {
        PropertyMessage propertyMessage = this.findById(id);
        if (ObjectUtils.isNotNull(propertyMessage)) {
            PropertySource propertySource = propertySourceBiz.findById(propertyMessage.getSource());
            propertyMessage.setPropertySource(propertySource.getName());
        }
        return propertyMessage;
    }

    /**
     * 再次入库
     *
     * @param propertyMessage
     */
    public void tx_editPropertyMessage(PropertyMessage propertyMessage) {
        //再次入库
        this.againPropertyMessage(propertyMessage);

        PropertyMessage propertyMessage1 = this.findById(propertyMessage.getId());
        Integer num = propertyMessage.getAmount();
        num = propertyMessage1.getAmount() + num;
        BigDecimal price = propertyMessage.getPrice();
        price = propertyMessage1.getPrice().add(price);
        propertyMessage.setAmount(num);
        propertyMessage.setPrice(price);
        this.update(propertyMessage);

    }


    public void tx_updateCleanProperty(Long propertyMessageId, PropertyClean propertyClean) {
        propertyCleanBiz.save(propertyClean);
        PropertyMessage propertyMessage = new PropertyMessage();
        propertyMessage.setStatus(4);
        propertyMessage.setId(propertyMessageId);
        this.update(propertyMessage);
    }


    public void updatePropertyStatus() {
        List<PropertyMessage> propertyMessageList = this.find(null, "1=1");
        if (ObjectUtils.isNotNull(propertyMessageList)) {
            for (PropertyMessage propertyMessage : propertyMessageList) {
                Date deadTime = propertyMessage.getLiftTime();
                if (deadTime != null) {
                    if (deadTime.getTime() < System.currentTimeMillis()) {
                        PropertyClean propertyClean = new PropertyClean();
                        propertyClean.setCleanTime(new Date());
                        propertyClean.setPropertyId(propertyMessage.getId());
                        propertyClean.setPropertyTypeId(propertyMessage.getPropertyId());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String codeNumber = sdf.format(new Date());
                        codeNumber = "BF" + codeNumber;
                        propertyClean.setCodeNumber(codeNumber);
                        propertyClean.setUserId(1L);
                        propertyClean.setStatus(1);
                        this.tx_updateCleanProperty(propertyMessage.getId(), propertyClean);
                    }
                }
            }

        }
    }


    public String batchImport(MultipartFile myFile, HttpServletRequest request) throws Exception {

        StringBuffer msg = new StringBuffer();
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);

        // 指的行数，一共有多少行+
        int rows = sheet.getLastRowNum();
        for (int i = 1; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                String name = getCellValue(row.getCell((short) 0));
                String propertyTypeId = getCellValue(row.getCell((short) 1));
                String product = getCellValue(row.getCell((short) 2));
                String amount = getCellValue(row.getCell((short) 3));
                String unit = getCellValue(row.getCell((short) 4));
                String price = getCellValue(row.getCell((short) 5));
                String buyTime = getCellValue(row.getCell((short) 6));
                String liftTime = getCellValue(row.getCell((short) 7));
                String source = getCellValue(row.getCell((short) 8));
                String storageId = getCellValue(row.getCell((short) 9));
                String context = getCellValue(row.getCell((short) 10));

                PropertyMessage propertyMessage = new PropertyMessage();
                // 资产信息名称不能为空 为空时不能导入
                if (StringUtils.isTrimEmpty(name)) {
                    msg.append("第" + i + "行,资产信息名称,格式错误;");
                    continue;
                } else {
                    propertyMessage.setName(name);
                }

                // core包下的stringUtil没有isNumber方法
                // 资产类型ID必须是数字 为空字符串时和非数字时不能导入
                if (!com.houqin.utils.StringUtils.isNumber(propertyTypeId)) {
                    msg.append("第" + i + "行,资产类型ID,格式错误;");
                    continue;
                } else {
                    propertyMessage.setPropertyId(Long.parseLong(propertyTypeId));
                }

                // 型号可以为空 非空时才set
                if (!StringUtils.isTrimEmpty(product)) {
                    propertyMessage.setProduct(product);
                }
                // 数量可以为空 非空时才set
                if (!StringUtils.isTrimEmpty(amount)) {
                    propertyMessage.setAmount(Integer.parseInt(amount));
                }

                // 单位可以为空 非空时才set
                if (!StringUtils.isTrimEmpty(unit)) {
                    propertyMessage.setUnit(unit);
                }

                // 价格可以为空, 但非空时不能为非小数
                if (!StringUtils.isTrimEmpty(price)) {
                    if (!com.houqin.utils.StringUtils.judgeTwoDecimal(price)) {
                        msg.append("第" + i + "行,金额,格式错误;");
                        continue;
                    } else {
                        propertyMessage.setPrice(new BigDecimal(price));

                    }
                }

                // 购入时间不能为空
                try {
                    Date time = sdf.parse(buyTime);
                    propertyMessage.setBuyTime(time);
                } catch (ParseException e) {
                    msg.append("第" + i + "行,购入时间,格式错误;");
                    continue;
                }

                // 使用期限可以为空 但非空时, 不能为非法格式
                if (!StringUtils.isTrimEmpty(liftTime)) {
                    try {
                        Date time = sdf.parse(liftTime);
                        propertyMessage.setLiftTime(time);
                    } catch (ParseException e) {
                        msg.append("第" + i + "行,购入时间,格式错误;");
                        continue;
                    }
                }

                // 来源只能为数字
                if (!com.houqin.utils.StringUtils.isNumber(source)) {
                    msg.append("第" + i + "行,来源ID,格式错误;");
                    continue;
                } else {
                    propertyMessage.setSource(Long.parseLong(source));
                }
                if (!com.houqin.utils.StringUtils.isNumber(storageId)) {
                    msg.append("第" + i + "行,库房名称ID,格式错误;");
                    continue;
                } else {
                    propertyMessage.setStorageId(Long.parseLong(storageId));
                }

                // 备注无限制
                propertyMessage.setContext(context);

                Long userId = SysUserUtils.getLoginSysUserId(request);
                propertyMessage.setUserId(userId);

                super.save(propertyMessage);
            }
        }
        return msg.toString();
    }


    /**
     * 获得Hsscell内容
     *
     * @param cell
     * @return
     */
    public String getCellValue(HSSFCell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat df = new DecimalFormat("0");
                    value = df.format(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value.trim();
    }

}
