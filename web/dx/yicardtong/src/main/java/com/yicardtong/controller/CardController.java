package com.yicardtong.controller;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.yicardtong.dao.card.CardDao;
import com.yicardtong.entity.card.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by caichenglong on 2017/10/30.
 */
@Controller
public class CardController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CardController.class);

    private static final String ADMIN_PREFIX = "/yikatong";
    @Autowired
    private CardDao cardDao;

    /**
     * 查询未使用的卡
     */
    @RequestMapping(ADMIN_PREFIX+"/queryNotUserCard")
    @ResponseBody
    public Map<String, Object> queryNotUserCard(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<Card> cardList = cardDao.cardList();
            json = resultJson(ErrorCode.SUCCESS, "", cardList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }


    /**
     * 卡的未使用量
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/queryNotUseCardCount")
    @ResponseBody
    public Map<String, Object> queryNotUseCardCount(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            int num = cardDao.queryNotUseCard();
            json = resultJson(ErrorCode.SUCCESS, "", num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }



}
