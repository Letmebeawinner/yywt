package com.sms.biz.profile;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.CollectionUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sms.common.CommonConstants;
import com.sms.dao.profile.ProfileDao;
import com.sms.entity.profile.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 配置Biz
 * 林明亮
 * Created by Administrator on 2016/12/22.
 */
@Service
public class ProfileBiz extends BaseBiz<Profile, ProfileDao> {
    private RedisCache redisCache = RedisCache.getInstance();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * 获取所有配置
     *
     * @return 配置
     */
    @SuppressWarnings("unchecked")
    public List<Profile> getAllConfig() {
        List<Profile> profileList = (List<Profile>) redisCache.regiontGet(CommonConstants.ALL_CONFIG_KEY);
        if (CollectionUtils.isEmpty(profileList)) {
            profileList = this.findAll();
            redisCache.regionSet(CommonConstants.ALL_CONFIG_KEY, profileList);
        }
        return profileList;
    }

    /**
     * 通过Key获取配置的详细属性
     *
     * @param key 配置的key
     * @return 配置详情
     */
    public Map<String, String> getConfigInfo(String key) {
        List<Profile> profileList = this.getAllConfig();
        if (!CollectionUtils.isEmpty(profileList)) {
            for (Profile p : profileList) {
                if (p.getConfigKey().trim().equals(key.trim())) {
                    String stringKey = p.getConfigContext();
                    return gson.fromJson(stringKey, new TypeToken<Map<String, String>>() {
                    }.getType());
                }
            }
        }
        return null;
    }
}
