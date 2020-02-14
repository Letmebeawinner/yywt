package com.yicardtong.dao.card;

import com.a_268.base.core.BaseDao;
import com.yicardtong.entity.card.Card;

import java.util.List;

/**
 * Created by caichenglong on 2017/10/30.
 */
public interface CardDao extends BaseDao {
    /**
     * 查询卡剩余使用量
     * @return
     */
    List<Card>  cardList();


    int queryNotUseCard();



}
