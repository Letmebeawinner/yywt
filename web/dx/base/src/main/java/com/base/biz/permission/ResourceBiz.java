package com.base.biz.permission;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.core.BaseBiz;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.StringUtils;
import com.base.dao.permission.ResourceDao;
import com.base.entity.permission.Resource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限资源Biz
 *
 * @author s.li
 */
@Service
public class ResourceBiz extends BaseBiz<Resource, ResourceDao> {

    private RedisCache redisCache = RedisCache.getInstance();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    /**
     * 修改权限资源数据，及修改权限资源所属的子级权限的系统类型
     * @param resource
     */
    public List<Resource> tx_updateResource(Resource resource){
        List<Resource> reList = new ArrayList<>();
        reList.add(resource);
        //得到第二级的子级节点
        List<Resource> resourceList = this.find(null," parentId="+resource.getId());
        if(!CollectionUtils.isEmpty(resourceList)){
            String ids = "";
            for(int i=0;i<resourceList.size();i++){
                resourceList.get(i).setResourceSite(resource.getResourceSite());
                if(i<resourceList.size()-1){
                    ids+=resourceList.get(i).getId()+",";
                }else{
                    ids+=resourceList.get(i).getId();
                }
            }
            reList.addAll(resourceList);
            //得到第三级的子级节点
            List<Resource> _resourceList = this.find(null," parentId in ("+ids+")  order by resourceOrder desc");
            if(!CollectionUtils.isEmpty(_resourceList)){
                for (Resource r : _resourceList){
                    r.setResourceSite(resource.getResourceSite());
                }
                reList.addAll(_resourceList);
            }
        }
        this.updateBatch(reList);
        redisCache.regiontRemove(BaseCommonConstants.ALL_AUTHORITY_KEY);
        return reList;
    }

    /**
     * 获取所有的权限数据，优先从缓存中获取
     * @return
     */
    public List<Resource> queryAllResource(){
        String  all = (String)redisCache.regiontGet(BaseCommonConstants.ALL_AUTHORITY_KEY);
        List<Resource> resourceList;
        if(StringUtils.isTrimEmpty(all)){
            resourceList = this.find(null," 1=1 order by resourceOrder desc, resourceSite, resourceType");
            redisCache.regionSet(BaseCommonConstants.ALL_AUTHORITY_KEY,gson.toJson(resourceList));
        }else{
            resourceList = gson.fromJson(all,new TypeToken<List<Resource>>(){}.getType());
        }
        return resourceList;
    }
}
