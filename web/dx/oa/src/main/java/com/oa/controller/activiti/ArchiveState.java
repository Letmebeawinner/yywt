package com.oa.controller.activiti;

import com.oa.biz.archivetype.OaArchiveSearchBiz;
import com.oa.entity.archivetype.OaArchiveSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 档案申请状态
 *
 * @author lzh
 * @create 2018-01-02-15:02
 */
@Component
public class ArchiveState implements State{

    private static final String oaArchiveApplyHistory = "/archive/oa_archive_apply_history";

    @Autowired
    private OaArchiveSearchBiz oaArchiveBiz;

    private static ArchiveState archiveState;

    @Override
    public String handle(HttpServletRequest request, String processInstanceId) {
        OaArchiveSearch archive = archiveState.oaArchiveBiz.getOaArchiveByProcessInstanceId(processInstanceId);
        request.setAttribute("oaArchive", archive);
        return oaArchiveApplyHistory;
    }

    @PostConstruct
    public void init() {
        archiveState = this;
    }
}
