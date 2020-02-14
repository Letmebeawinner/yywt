package com.menhu.biz.common;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.menhu.enums.Status;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 教学动态
 */
@Service
public class JiaoWuHessianBiz {
    protected static Gson gson = new Gson();

    @Resource
    private JiaoWuHessianService jiaoWuHessianService;


    /**
     * @Description 获取最新的教学动态
     */
    public List<Map<String,String>> getLatestTeachingIndex(){
        try {
            return  jiaoWuHessianService.getLatestTeachingInfo(6L);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description 查询教学动态详情
     */
    public Map<String,String> getTeachingInfoById(Long id){
        try {
            addClickTimes(id);
            return  jiaoWuHessianService.getTeachingInfoById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 教学动态列表
     */
    public Map<String,Object> teachingInfoList(Pagination pagination,String title ){
        try {
            String sql="";
            if(!StringUtils.isTrimEmpty(title)){
                sql=" and title like '%"+title+"%'";
            }
            Map<String, Object> map = jiaoWuHessianService.teachingInfoList( pagination, " status=1 "+sql+" ORDER BY  CASE WHEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) < date(createTime) THEN clickTimes ELSE 0 END DESC ");//1=正常
            Map<String,String> paginationMap= (Map<String, String>) map.get("pagination");
            pagination.setPageSize(Integer.parseInt(paginationMap.get("pageSize")));
            pagination.setCurrentPage(Integer.parseInt(paginationMap.get("currentPage")));
            pagination.setTotalCount(Integer.parseInt(paginationMap.get("totalCount")));
            pagination.setTotalPages(Integer.parseInt(paginationMap.get("totalPages")));
            map.put("pagination",pagination);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取点击量最高的教学动态
     */
    public List<Map<String,String>> hotTeachingInfoList( ){
        try {
            return  jiaoWuHessianService.hotTeachingInfoList(10L);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 增加教学动态的点击量
     */
    public void addClickTimes(Long id){
        try {
            jiaoWuHessianService.addClickTimes(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}


