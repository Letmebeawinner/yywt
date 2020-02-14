package com.houqin.biz.price;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.redis.RedisCache;
import com.houqin.common.CommonConstants;
import com.houqin.dao.price.PriceDao;
import com.houqin.entity.price.Price;
import com.houqin.enums.PriceType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 价格业务层
 *
 * @author lzh
 * @create 2017-02-27-16:45
 */
@Service
public class PriceBiz extends BaseBiz<Price, PriceDao> {

    private RedisCache redisCache = RedisCache.getInstance();

    /**
     * @Description: 获取价格
     * @author: lzh
     * @Param: [type]
     * @Return: java.lang.Double
     * @Date: 11:45
     */
    public Double getPriceByType(String type) {
        Double price = null;
        if (PriceType.WATER.toString().equals(type)) {
            price = (Double)redisCache.regiontGet(CommonConstants.priceWater);
        }
        if (PriceType.ELECTRICITY.toString().equals(type)) {
            price = (Double)redisCache.regiontGet(CommonConstants.priceElectricity);
        }
        if (price == null) {
            String sql = " type = '" + type + "'";
            List<Price> prices = this.find(null, sql);
            if (prices != null && prices.size() > 0) {
                price = prices.get(0).getInwardPrice();
            }
            if (PriceType.WATER.toString().equals(type)) {
                redisCache.regionSet(CommonConstants.priceWater, price);
            }
            if (PriceType.ELECTRICITY.toString().equals(type)) {
                redisCache.regionSet(CommonConstants.priceElectricity, price);
            }
        }
        return price;
    }
}
