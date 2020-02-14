package com.yicardtong.biz.worksmail;

import com.yicardtong.entity.WorkSmail;

import java.util.List;
import java.util.Map;

/**
 * Service
 *
 * @author s.li
 * @create 2017-02-24-13:39
 */
public interface WorkSmailService {

    /**
     * 添加请假登记记录
     * @param workSmail
     * @return
     */
    public int addWorkSmail(WorkSmail workSmail);
}
