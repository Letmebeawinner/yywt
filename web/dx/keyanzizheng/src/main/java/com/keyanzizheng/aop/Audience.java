package com.keyanzizheng.aop;

import com.a_268.base.util.ObjectUtils;
import com.keyanzizheng.biz.result.TaskChangeBiz;
import com.keyanzizheng.common.EcologicalCivilizationConstants;
import com.keyanzizheng.constant.ApprovalStatusConstants;
import com.keyanzizheng.entity.result.Result;
import com.keyanzizheng.entity.result.TaskChange;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 记录审批
 *
 * @author YaoZhen
 * @date 01-09, 15:55, 2018.
 */
@Aspect
public class Audience {
    private static final Logger logger = LoggerFactory.getLogger(Audience.class);
    private static final int DO_STORAGE = 2;

    @Autowired
    private TaskChangeBiz taskChangeBiz;

    @Pointcut("execution(public * com.keyanzizheng.controller.result.ResultController.updateResult(..))")
    private void fooKY() {
    }

    @Pointcut("execution(public * com.keyanzizheng.controller.result.ResultEcologicalCivilizationController.projectEstablishmentUpdate(..))")
    private void fooZZ() {
    }

    @Pointcut("execution(public * com.keyanzizheng.controller.result.ResultArchiveController.saveArchiveResult(..))")
    private void fooZZArchive() {
    }

    @Pointcut("execution(public * com.keyanzizheng.controller.result.ResultArchiveController.doFileArchive(..))")
    private void fooZZFileArchive() {
    }

    /**
     * 科研操作记录
     */
    @After("fooKY()")
    public void researchTaskAfter(JoinPoint joinPoint) {
        logger.info("开始记录科研修改");
        Result result = (Result) joinPoint.getArgs()[1];
        logger.info("passStatus=============" + result.getPassStatus());
        Integer status = result.getPassStatus();
        if (ObjectUtils.isNotNull(status)) {
            switch (status) {
                case ApprovalStatusConstants.NOT_PASS_DEPT:
                    taskChangeResult(result, "课题未通过部门审批.");
                    break;
                case ApprovalStatusConstants.PASS_DEPT:
                    taskChangeResult(result, "课题通过部门审批.");
                    break;
                case ApprovalStatusConstants.NOT_PASS_OFFICE:
                    taskChangeResult(result, "课题未通过科研处审批.");
                    break;
                case ApprovalStatusConstants.PASS_OFFICE:
                    taskChangeResult(result, "课题通过科研处审批.");
                    break;
                case ApprovalStatusConstants.NOT_PASS_LEADER:
                    taskChangeResult(result, "课题未通过科研处领导审批, 请重新提交申请");
                    break;
                case ApprovalStatusConstants.PASS_LEADER:
                    taskChangeResult(result, "课题已通过科研处领导审批.");
                    break;
                case ApprovalStatusConstants.FINISH:
                    taskChangeResult(result, "课题已通过科研处最终审批, 已结项.");
                    break;
                case ApprovalStatusConstants.NOT_FINISH:
                    taskChangeResult(result, "课题未通过科研处最终审批, 请重新提交申请.");
                    break;
                default:
                    taskChangeResult(result, "该成果已被修改");
            }
        }
        logger.info("结束记录科研修改");
    }

    /**
     * 生态审批记录
     */
    @After("fooZZ()")
    public void ecologicalTaskAfter(JoinPoint joinPoint) {
        logger.info("开始记录生态修改");
        Result result = (Result) joinPoint.getArgs()[0];
        logger.info("passStatus==============" + result.getPassStatus());
        Integer status = result.getPassStatus();
        if (ObjectUtils.isNotNull(status)) {
            switch (status) {
                case EcologicalCivilizationConstants.CONFIRM_PROJECT:
                    taskChangeResult(result, "课题立项已审批");
                    break;
                case EcologicalCivilizationConstants.REFUSED_PROJECT:
                    taskChangeResult(result, "课题立项已拒绝");
                    break;
                case EcologicalCivilizationConstants.CONFIRM_FILE:
                    taskChangeResult(result, "课题结项已确认");
                    break;
                case EcologicalCivilizationConstants.REFUSED_FILE:
                    taskChangeResult(result, "课题结项已拒绝");
                    break;
                default:
                    taskChangeResult(result, "该成果已被修改");
            }
        }
        logger.info("结束记录生态修改");
    }

    /**
     * 入库记录
     */
    @After("fooZZArchive()")
    public void taskZZArchive(JoinPoint joinPoint) {
        logger.info("开始记录生态入库");
        Result result = (Result) joinPoint.getArgs()[0];
        if (result.getIntoStorage() == DO_STORAGE) {
            taskChangeResult(result, "生态所课题成果已入库");
        }
        logger.info("IntoStorage==============" + result.getIntoStorage());
    }

    /**
     * 归档记录
     */
    @After("fooZZFileArchive()")
    public void taskZZFileArchive(JoinPoint joinPoint) {
        logger.info("开始记录生态归档");
        Result result = (Result) joinPoint.getArgs()[0];

        /*Integer flag = (Integer) joinPoint.getArgs()[1];
        if (flag == ResultFileConstants.SHORT_TERM) {
            taskChangeResult(result, "生态所课题成果已短期归档");
        }
        if (flag == ResultFileConstants.LONG_TERM) {
            taskChangeResult(result, "生态所课题成果已长期归档");
        }*/

        taskChangeResult(result, "生态所课题成果已归档");
        logger.info("IntoStorage==============" + result.getIntoStorage());
    }

    /**
     * 变更记录
     *
     * @param result 课题
     * @param desc   是否通过的描述
     */
    private void taskChangeResult(Result result, String desc) {
        TaskChange taskChange = new TaskChange();
        taskChange.setTaskId(result.getId());
        taskChange.setOperate(desc);
        taskChange.setStatus(1);
        taskChangeBiz.save(taskChange);
    }
}
