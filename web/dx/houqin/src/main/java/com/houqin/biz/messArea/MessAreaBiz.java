package com.houqin.biz.messArea;

import com.a_268.base.core.BaseBiz;
import com.houqin.dao.messArea.MessAreaDao;
import com.houqin.entity.messArea.MessArea;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用餐人类型
 *
 * @author YaoZhen
 * @create 06-19, 13:41, 2017.
 */
@Service
public class MessAreaBiz extends BaseBiz<MessArea, MessAreaDao> {

    /**
     * 查询所有的用餐人类型
     * @return
     */
    public List<MessArea> queryListForMessDiner() {
        return this.find(null,"1=1");
    }
}
