package com.oa.biz.archivetype;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.oa.common.FileExportImportUtil;
import com.oa.dao.archivetype.ArchiveDao;
import com.oa.entity.archivetype.Archive;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArchiveBiz extends BaseBiz<Archive, ArchiveDao> {


    /**
     * 获取轮训报名名单的excel文件列表
     *
     * @param request
     * @param dir
     * @param headName
     * @param expName
     * @return
     * @throws Exception
     */
    public List<File> getExcelArchiveList(HttpServletRequest request, String dir, String[] headName, String expName, String whereSql) throws Exception {
        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        pagination.setRequest(request);
        find(pagination, whereSql);
        int num = pagination.getTotalPages();// 总页数
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
            pagination.setCurrentPage(i);
            List<Archive> archiveList = find(pagination, whereSql);
            List<List<String>> list = convert(archiveList);
            File file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
            srcfile.add(file);
        }
        return srcfile;
    }


    /**
     * 将XinDe的集合转化为List<List<String>>类型.
     *
     * @param archiveList
     * @return
     */
    public List<List<String>> convert(List<Archive> archiveList) {
        List<List<String>> list = new ArrayList<List<String>>();
        if (archiveList != null && archiveList.size() > 0) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMHH");

            for (Archive archive : archiveList) {
                List<String> smallList = new ArrayList<String>();
                smallList.add(archive.getDanghao());
                smallList.add(archive.getJianhao());
                smallList.add(archive.getAuthor());
                smallList.add(archive.getWenhao());
                smallList.add(archive.getAutograph());
                String archiveDate = "";
                if (archive.getArchivedate() != null) {
                    archiveDate = sdf.format(archive.getArchivedate());
                }
                smallList.add(archiveDate);
                smallList.add(archive.getPages());
                smallList.add(archive.getOrginzation());
                smallList.add(archive.getDescription());
                list.add(smallList);
            }
        }
        return list;
    }


}
