package com.houqin.biz.dishes;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.houqin.biz.dishesType.DishesTypeBiz;
import com.houqin.dao.dishes.DishesDao;
import com.houqin.entity.dishes.Dishes;
import com.houqin.entity.dishes.DishesDto;
import com.houqin.entity.dishesType.DishesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */
@Service
public class DishesBiz extends BaseBiz<Dishes, DishesDao> {
    @Autowired
    private DishesTypeBiz dishesTypeBiz;



}
