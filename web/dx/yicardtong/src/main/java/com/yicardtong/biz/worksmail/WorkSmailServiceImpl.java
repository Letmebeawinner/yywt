package com.yicardtong.biz.worksmail;

import com.yicardtong.dao.commons.GenericDaoImpl;
import com.yicardtong.entity.WorkSmail;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 接口实现类
 *
 * @author ccl
 * @create
 */
@Service("workSmailService")
public class WorkSmailServiceImpl extends GenericDaoImpl<Map<String, String>> implements WorkSmailService {
    /**
     * 添加记录
     * @param workSmail
     * @return
     */
    public int addWorkSmail(WorkSmail workSmail){
        String sql = "insert into Work_Smail(Base_OperCode,base_Mail,base_Password,base_MP) values (?,?,?,?)";
        Object[] parameters = {workSmail.getBase_OperCode(),workSmail.getBase_Mail(),workSmail.getBase_Password(),workSmail.getBase_MP()};
        int result= this.insert(sql,parameters);
        return result;
    }

}
