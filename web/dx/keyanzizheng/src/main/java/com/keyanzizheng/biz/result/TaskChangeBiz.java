package com.keyanzizheng.biz.result;

import com.a_268.base.core.BaseBiz;
import com.keyanzizheng.dao.result.TaskChangeDao;
import com.keyanzizheng.entity.result.Result;
import com.keyanzizheng.entity.result.TaskChange;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课题仓库Biz
 *
 * @author 268
 */
@Service
public class TaskChangeBiz extends BaseBiz<TaskChange, TaskChangeDao> {

    /**
     * 课题变更记录条件查询
     *
     * @author 268
     */
    public List<TaskChange> getTaskChangeList(TaskChange taskChange){
        String whereSql=" 1=1";
        whereSql+=" and status!=2 ";
        if(taskChange.getId()!=null && taskChange.getId()>0){
            whereSql+=" and id="+taskChange.getId();
        }
        if(taskChange.getTaskId()!=null && taskChange.getTaskId()>0){
            whereSql+=" and taskId="+taskChange.getTaskId();
        }
        return this.find(null,whereSql);
    }

    /**
     * 添加变更记录
     *
     * @param result 课题
     * @param desc   描述
     */
    public void addTask(Result result, String desc) {
        TaskChange taskChange = new TaskChange();
        taskChange.setTaskId(result.getId());
        taskChange.setOperate(desc);
        taskChange.setStatus(1);
        this.save(taskChange);
    }
}
