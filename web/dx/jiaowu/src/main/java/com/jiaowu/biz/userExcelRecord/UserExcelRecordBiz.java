package com.jiaowu.biz.userExcelRecord;

import com.a_268.base.core.BaseBiz;
import com.jiaowu.dao.userExcelRecord.UserExcelRecordDao;
import com.jiaowu.entity.userExcelRecord.UserExcelRecord;
import org.springframework.stereotype.Service;

/**
 * Created by 李帅雷 on 2017/11/3.
 */
@Service
public class UserExcelRecordBiz extends BaseBiz<UserExcelRecord,UserExcelRecordDao>{
    /**
     * 验证UserExcelRecord
     * @param userExcelRecord
     * @return
     */
    public String validate(UserExcelRecord userExcelRecord){
        if(userExcelRecord.getTitle()==null||userExcelRecord.getTitle().equals("")){
            return "请填写名称";
        }
        if(userExcelRecord.getClassTypeId()==null||userExcelRecord.getClassTypeId().equals(0L)){
            return "请选择班型";
        }
        if(userExcelRecord.getClassId()==null||userExcelRecord.getClassId().equals(0L)){
            return "请选择班次";
        }
        if(userExcelRecord.getUrl()==null||userExcelRecord.getUrl().equals("")){
            return "请上传附件";
        }
        return null;
    }
}
