package com.oa.biz.news;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.dao.news.NewsDao;
import com.oa.entity.news.News;
import com.oa.entity.news.NewsDepartment;
import com.oa.entity.news.NewsDto;
import com.oa.entity.news.NewsType;
import com.oa.entity.sysuser.SysUser;
import com.oa.utils.GenerateSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 新闻业务类
 *
 * @author lzh
 * @create 2016-12-29-17:35
 */
@Service
public class NewsBiz extends BaseBiz<News, NewsDao>{

    @Autowired
    private NewsTypeBiz newsTypeBiz;
    @Autowired
    private NewsDepartmentBiz newsDepartmentBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    /**
     * @Description: 获取新闻拓展类
     * @author: lzh
     * @Param: [pagination, newsDto]
     * @Return: java.util.List<com.oa.entity.news.NewsDto>
     * @Date: 9:43
     */
    public List<NewsDto> getNewsDtos(Pagination pagination, News newsEntity) {
        String whereSql = GenerateSqlUtil.getSql(newsEntity);
        List<News> newss = this.find(pagination, whereSql);
        List<NewsDto> newsDtos = null;
        newsDtos = newss.stream()
                .map(news -> convertNewsToDto(news))
                .collect(Collectors.toList());
        return newsDtos;
    }

    /**
     * @Description: 将类的属性全部放到拓展类
     * @author: lzh
     * @Param: [news]
     * @Return: com.oa.entity.news.NewsDto
     * @Date: 11:40
     */
    private NewsDto convertNewsToDto(News news) {
        NewsDto newsDto = new NewsDto();
        BeanUtils.copyProperties(news, newsDto);
        if (newsDto.getNewsTypeId() != null) {
            NewsType newsType = newsTypeBiz.findById(newsDto.getNewsTypeId());
            if (newsType != null) {
                newsDto.setNewsTypeName(newsType.getName());
            }
        }
        if (newsDto.getUserId() != null && newsDto.getUserId() > 0) {
            SysUser sysUser = baseHessianBiz.getSysUserById(newsDto.getUserId());
            newsDto.setPublishName(sysUser.getUserName());
        }
        return newsDto;
    }

    /**
     * @Description:发布新闻，同时更新发布状态
     * @author: lzh
     * @Param: [id, ids], id新闻id，ids部门ids
     * @Return: void
     * @Date: 10:52
     */
    public void tx_publish(Long userId, Long id, Long [] ids) {
        News news = new News();
        news.setId(id);
        news.setPublish(1);
        news.setUserId(userId);
        this.update(news);
        NewsDepartment newsDepartment = new NewsDepartment();
        newsDepartment.setNewsId(id);
        //先删除再增加
        List<NewsDepartment> newsDepartments = newsDepartmentBiz.find(null, GenerateSqlUtil.getSql(newsDepartment));
        List<Long> idSets = newsDepartments.stream()
                                           .map(NewsDepartment::getId)
                                           .collect(Collectors.toList());
        //删除掉和这个新闻相关的
        newsDepartmentBiz.deleteByIds(idSets);
        List<NewsDepartment> departments = Arrays.asList(ids)
                                                 .stream()
                                                 .map(departmentId -> idConvertToObject(id, departmentId))
                                                 .collect(Collectors.toList());
       newsDepartmentBiz.saveBatch(departments);
    }

    /**
     * @Description:id转换成实体
     * @author: lzh
     * @Param: [newsId, departmentId]
     * @Return: com.oa.entity.news.NewsDepartment
     * @Date: 11:06
     */
    private NewsDepartment idConvertToObject(Long newsId, Long departmentId) {
        NewsDepartment newsDepartment = new NewsDepartment();
        newsDepartment.setNewsId(newsId);
        newsDepartment.setDepartmentId(departmentId);
        return newsDepartment;
    }


}
