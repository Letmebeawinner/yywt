package com.menhu.biz.common;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.menhu.enums.Status;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


/**
 */
@Service
public class OAHessianBiz {

    @Resource
    private OAHessianService oAHessianService;


    /**
     * 政策法规首页
     */
    public Object getRuleListIndex(){
        try {
            Map<String, Object> ruleList = oAHessianService.getRuleList(null, " status="+ Status.正常数据.getStatus()+" order by createTime desc limit 9");
            if(ObjectUtils.isNotNull(ruleList)){
                return  ruleList.get("ruleList");
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 政策法规列表
     */
    public Map<String,Object> getRuleList(Pagination pagination,String title){
        try {
            String sql="";
            if(!StringUtils.isTrimEmpty(title)){
                sql=" and name like '%"+title+"%'";
            }
            Map<String, Object> ruleMap = oAHessianService.getRuleList(pagination, " status="+ Status.正常数据.getStatus()+sql+" order by createTime desc");
            Map<String,String> paginationMap= (Map<String, String>)ruleMap.get("pagination");
            pagination.setPageSize(Integer.parseInt(paginationMap.get("pageSize")));
            pagination.setCurrentPage(Integer.parseInt(paginationMap.get("currentPage")));
            pagination.setTotalCount(Integer.parseInt(paginationMap.get("totalCount")));
            pagination.setTotalPages(Integer.parseInt(paginationMap.get("totalPages")));
            ruleMap.put("pagination",pagination);
            return  ruleMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 政策法规列表点击排行
     */
    public Map<String,Object> getRuleRelated( ){
        try {
            Map<String, Object> ruleList = oAHessianService.getRuleList(null, " status="+ Status.正常数据.getStatus()+" order by createTime   limit 10");
            return  ruleList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取规章详细信息
     */
    public Map<String,String> getRuleById(Long id){
        try {
            return  oAHessianService.getRuleById( id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 通知公告首页
     */
    public Object getNoticeListIndex(){
        try {
            Map<String, Object> noticeList = oAHessianService.getNoticeList(null, " status=" + Status.正常数据.getStatus() + " order by createTime desc limit 5");
            if(ObjectUtils.isNotNull(noticeList)){
                return  noticeList.get("ruleList");
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通知公告列表
     */
    public Map<String,Object> getNoticeList(Pagination pagination,String title){
        try {
            String sql="";
            if(!StringUtils.isTrimEmpty(title)){
                sql=" and title like '%"+title+"%'";
            }
            Map<String,Object> map = oAHessianService.getNoticeList(pagination, " status=" + Status.正常数据.getStatus() +sql+ " order by createTime desc");
            Map<String,String> paginationMap= (Map<String, String>)map.get("pagination");
            pagination.setPageSize(Integer.parseInt(paginationMap.get("pageSize")));
            pagination.setCurrentPage(Integer.parseInt(paginationMap.get("currentPage")));
            pagination.setTotalCount(Integer.parseInt(paginationMap.get("totalCount")));
            pagination.setTotalPages(Integer.parseInt(paginationMap.get("totalPages")));
            map.put("pagination",pagination);
            return  map;
        }catch (Exception e ){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通知公告列表点击排行
     */
    public Map<String,Object> getNoticeRelated( ){
        try {
            Map<String, Object> noticeRelated = oAHessianService.getNoticeList(null, " status=" + Status.正常数据.getStatus() + " order by createTime desc limit 10");
            return noticeRelated;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取通知公告信息
     */
    public Map<String,String> getNoticeById(Long id){
        try {
            return  oAHessianService.getNoticeById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}


