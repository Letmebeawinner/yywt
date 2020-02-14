package com.yicardtong.entity.card;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by caichenglong on 2017/10/30.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Card implements Serializable {

    //卡号
    private String Base_CardNo;

    //卡片状态(0:未发行1:已发行2:挂失)
    private String Base_CardState;



}
