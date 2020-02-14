package com.oa.biz.File;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.dao.file.FileDao;
import com.oa.entity.file.File;
import com.oa.entity.file.FileDto;
import com.oa.entity.news.News;
import com.oa.entity.news.NewsDto;
import com.oa.entity.news.NewsType;
import com.oa.entity.sysuser.SysUser;
import com.oa.utils.GenerateSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件管理
 *
 * @author ccl
 * @create 2017-01-04-9:44
 */
@Service
public class FileBiz extends BaseBiz<File,FileDao>{

    @Autowired
    private BaseHessianBiz baseHessianBiz;
    /**
     * @Description: 获取新闻拓展类
     * @author: ccl
     * @Param: [pagination, newsDto]
     * @Date: 9:43
     */
    public List<FileDto> getFilesDtos(Pagination pagination, File _files) {
        _files.setShare(1L);
        String whereSql = GenerateSqlUtil.getSql(_files);
        List<File> files = this.find(pagination, whereSql);
        List<FileDto> fileDtos = null;
        fileDtos = files.stream()
                .map(file -> convertFilesToDto(file))
                .collect(Collectors.toList());
        return fileDtos;
    }

    /**
     * @Description: 将类的属性全部放到拓展类
     * @author: ccl
     * @Date: 11:40
     */
    private FileDto convertFilesToDto(File file) {
        FileDto fileDto = new FileDto();
        BeanUtils.copyProperties(file, fileDto);
        if (fileDto.getUserId() != null && fileDto.getUserId() > 0) {
            SysUser sysUser = baseHessianBiz.getSysUserById(fileDto.getUserId());
            fileDto.setSysUserName(sysUser.getUserName());
        }
        return fileDto;
    }


}
