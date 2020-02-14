package com.jiaowu.entity.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 成果
 * Created by 268 on 2016/12/7.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class QueryResult extends Result {
    private static final long serialVersionUID = -3545341344340282402L;
    private ApproveBill approveBill;//成果审批记录
    private String employeeName; //申报人姓名
    private List<Employee> EmployeeList;

    public QueryResult() {
    }

    public QueryResult(Result result) {
        this.setId(result.getId());
        this.setResultForm(result.getResultForm());
        this.setName(result.getName());
        this.setWordsNumber(result.getWordsNumber());
        this.setDatTime(result.getDatTime());
        this.setPublish(result.getPublish());
        this.setPublishNumber(result.getPublishNumber());
        this.setPublishTime(result.getPublishTime());
        this.setWorkName(result.getWorkName());
        this.setAssociateEditor(result.getAssociateEditor());
        this.setAssociateNumber(result.getAssociateNumber());
        this.setChapter(result.getChapter());
        this.setDigest(result.getDigest());
        this.setRemark(result.getRemark());
        this.setIntoStorage(result.getIntoStorage());
        this.setCreateTime(result.getCreateTime());
        this.setPassStatus(result.getPassStatus());
        this.setResultDepartment(result.getResultDepartment());
        this.setIfFile(result.getIfFile());
        this.setStorageTime(result.getStorageTime());
        this.setSysUserId(result.getSysUserId());
        this.setResultType(result.getResultType());
        this.setTeacherResearch(result.getTeacherResearch());
        this.setResultSwitch(result.getResultSwitch());
        this.setJournalNature(result.getJournalNature());
        this.setAwardSituation(result.getAwardSituation());
        this.setAddTime(result.getAddTime());
        this.setEndTime(result.getEndTime());
        this.setEmployeeId(result.getEmployeeId());
    }
}
