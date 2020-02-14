package com.oa.biz.letter;

import com.a_268.base.core.BaseBiz;
import com.oa.dao.letter.LetterTypeDao;
import com.oa.entity.flow.FlowType;
import com.oa.entity.letter.LetterType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公文类型
 *
 * @author ccl
 * @create 2017-02-07-11:44
 */
@Service
public class LetterTypeBiz extends BaseBiz<LetterType,LetterTypeDao>{


    public List<LetterType> letterTypeList(){
        List<LetterType> letterTypeList=this.find(null," parentId=0");
        return letterTypeList;
    }

}
