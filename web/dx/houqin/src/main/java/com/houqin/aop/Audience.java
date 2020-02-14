package com.houqin.aop;

import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.property.*;
import com.houqin.biz.propertymessage.PropertyMessageBiz;
import com.houqin.biz.transfer.TransferBiz;
import com.houqin.entity.property.*;
import com.houqin.entity.propertymessage.PropertyMessage;
import com.houqin.entity.transfer.Transfer;
import lombok.NonNull;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 调拨走向
 *
 * @author YaoZhen
 * @date 01-09, 15:55, 2018.
 */
@Aspect
public class Audience {
    private static final Logger logger = LoggerFactory.getLogger(Audience.class);

    @Autowired
    private PropertyBiz propertyBiz;
    @Autowired
    private PropertyMessageBiz propertyMessageBiz;
    @Autowired
    private TransferBiz transferBiz;
    @Autowired
    private PropertyAllotBiz propertyAllotBiz;
    @Autowired
    private PropertyBorrowBiz propertyBorrowBiz;
    @Autowired
    private PropertyReceiveBiz propertyReceiveBiz;
    @Autowired
    private PropertyCleanBiz propertyCleanBiz;

    /**
     * 资产调拨
     */
    @Pointcut("execution(public * com.houqin.controller.property.PropertyAllotController.addPropertyAllot(..))")
    public void allotFoo() {}

    @Pointcut("execution(public * com.houqin.controller.property.PropertyAllotController.cancelAllot(..))")
    public void cancelFoo() {}

    /**
     * 资产借用
     */
    @Pointcut("execution(public * com.houqin.controller.property.PropertyBorrowController.addPropertyBorrow(..))")
    public void borrowFoo() {}
    @Pointcut("execution(public * com.houqin.controller.property.PropertyBorrowController.cancelBorrow(..))")
    public void cancelBorrowFoo() {}

    /**
     * 资产领用
     */
    @Pointcut("execution(public * com.houqin.controller.property.PropertyReceiveController.addPropertyReceive(..))")
    public void receiveFoo() {}
    @Pointcut("execution(public * com.houqin.controller.property.PropertyReceiveController.cancelReceive(..))")
    public void cancelReceiveFoo() {}

    /**
     * 资产报废
     */
    @Pointcut("execution(public * com.houqin.controller.property.PropertyCleanController.addPropertyClean(..))")
    public void cleanFoo() {}
    @Pointcut("execution(public * com.houqin.controller.property.PropertyCleanController.cancelClean(..))")
    public void cancelCleanFoo() {}


    @After("allotFoo()")
    public void allotFooAfter(JoinPoint joinPoint) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            logger.debug("记录资产调拨流向+++++++++++++++++" + request.getRequestURL());

            // 获取参数
            PropertyAllot propertyAllot = (PropertyAllot) joinPoint.getArgs()[1];

            // 资产id
            Long propertyId = propertyAllot.getPropertyId();
            // 资产类型id
            Long propertyTypeId =propertyAllot.getPropertyTypeId();

            aspectJ(propertyId, propertyTypeId, request, "添加了调拨");
        } catch (Exception e) {
            logger.error("Audience.allotFooAfter", e);
        }
    }

    @After("cancelFoo()")
    public void cancelFooAfter(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.debug("记录资产调拨流向+++++++++++++++++" + request.getRequestURL());

        Long id = (Long) joinPoint.getArgs()[1];

        // 调拨信息
        PropertyAllot propertyAllot = propertyAllotBiz.findById(id);
        // 资产id
        Long propertyId = propertyAllot.getPropertyId();
        // 资产类型id
        Long propertyTypeId =propertyAllot.getPropertyTypeId();

        aspectJ(propertyId, propertyTypeId, request, "取消了调拨");
    }

    @After("borrowFoo()")
    public void  borrowFooAfter(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.debug("记录资产借用流向+++++++++++++++++" + request.getRequestURL());

        // 获取参数
        PropertyBorrow propertyBorrow = (PropertyBorrow) joinPoint.getArgs()[1];

        // 资产id
        Long propertyId =  propertyBorrow.getPropertyId();
        // 资产类型id
        Long propertyTypeId =propertyBorrow.getPropertyTypeId();
        aspectJ(propertyId, propertyTypeId, request, "借用了资产");
    }

    @After("cancelBorrowFoo()")
    public void  cancelBorrowFooAfter(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.debug("记录资产借用流向+++++++++++++++++" + request.getRequestURL());
        // 获取参数
        Long id = (Long) joinPoint.getArgs()[1];
        PropertyBorrow propertyBorrow = propertyBorrowBiz.findById(id);

        // 资产id
        Long propertyId =  propertyBorrow.getPropertyId();
        // 资产类型id
        Long propertyTypeId =propertyBorrow.getPropertyTypeId();
        aspectJ(propertyId, propertyTypeId, request, "退还了资产");
    }


    @After("receiveFoo()")
    public void  receiveFooAfter(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.debug("记录资产领用流向+++++++++++++++++" + request.getRequestURL());

        // 获取参数
        PropertyReceive propertyReceive = (PropertyReceive) joinPoint.getArgs()[1];

        // 资产id
        Long propertyId =  propertyReceive.getPropertyId();
        // 资产类型id
        Long propertyTypeId =propertyReceive.getPropertyTypeId();
        aspectJ(propertyId, propertyTypeId, request, "领用了资产");
    }

    @After("cancelReceiveFoo()")
    public void  cancelReceiveFooAfter(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.debug("记录资产领用流向+++++++++++++++++" + request.getRequestURL());
        // 获取参数
        Long id = (Long) joinPoint.getArgs()[1];
        PropertyReceive propertyReceive = propertyReceiveBiz.findById(id);

        // 资产id
        Long propertyId =  propertyReceive.getPropertyId();
        // 资产类型id
        Long propertyTypeId =propertyReceive.getPropertyTypeId();
        aspectJ(propertyId, propertyTypeId, request, "退库了资产");
    }

    @After("cleanFoo()")
    public void  cleanFooAfter(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.debug("记录资产报废流向+++++++++++++++++" + request.getRequestURL());

        // 获取参数
        PropertyClean propertyClean = (PropertyClean) joinPoint.getArgs()[1];

        // 资产id
        Long propertyId =  propertyClean.getPropertyId();
        // 资产类型id
        Long propertyTypeId =propertyClean.getPropertyTypeId();
        aspectJ(propertyId, propertyTypeId, request, "报废了资产");
    }

    @After("cancelCleanFoo()")
    public void  cancelCleanFooAfter(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logger.debug("记录资产报废流向+++++++++++++++++" + request.getRequestURL());
        // 获取参数
        Long id = (Long) joinPoint.getArgs()[1];
        PropertyClean propertyClean = propertyCleanBiz.findById(id);

        // 资产id
        Long propertyId =  propertyClean.getPropertyId();
        // 资产类型id
        Long propertyTypeId =propertyClean.getPropertyTypeId();
        aspectJ(propertyId, propertyTypeId, request, "还原了资产");
    }

    /**
     * 添加记录
     * @param propertyId 资产id
     * @param propertyTypeId 资产类型id
     * @param request 获取用户名
     */
    private void aspectJ(@NonNull Long propertyId, @NonNull Long propertyTypeId,
                         HttpServletRequest request, String record) {
        // 操作人
        Map<String, String> sysUser = SysUserUtils.getLoginSysUser(request);
        String userName = sysUser.get("userName");

        // PropertyMessage(name=蓝药水, propertyId=5)
        PropertyMessage propertyMessage = propertyMessageBiz.findById(propertyId);
        String taskName = propertyMessage.getName();

        // Property(typeName=任务物品, sort=0)
        Property property = propertyBiz.findById(propertyTypeId);
        String taskType = property.getTypeName();

        // 记录
        String operationRecord = userName + " " + record + ". 资产类型: " + taskType + " 资产名称: " + taskName + ".";
        saveTransfer(propertyId, operationRecord);
    }

    /**
     * 保存出库记录
     * @param propertyId 资产信息Id
     * @param operationRecord 操作
     */
    private void saveTransfer(Long propertyId, String operationRecord) {
        Transfer transfer = new Transfer(propertyId, operationRecord);
        transferBiz.save(transfer);
    }

}
