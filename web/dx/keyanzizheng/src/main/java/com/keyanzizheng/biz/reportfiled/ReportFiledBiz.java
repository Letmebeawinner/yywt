package com.keyanzizheng.biz.reportfiled;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.core.BaseBiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.keyanzizheng.common.StudentHessianService;
import com.keyanzizheng.constant.StatusConstants;
import com.keyanzizheng.dao.reportfiled.ReportFiledDao;
import com.keyanzizheng.entity.reportfiled.ReportFiled;
import com.keyanzizheng.entity.research.ResearchReport;
import com.keyanzizheng.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 归档
 *
 * @author YaoZhen
 * @date 01-10, 14:37, 2018.
 */
@Service
public class ReportFiledBiz extends BaseBiz<ReportFiled, ReportFiledDao> {

    @Autowired
    private StudentHessianService studentHessianService;

    /**
     * 生态所调研报告归档
     *  @param researchReport 调研报告
     * @param reportFiled    归档到本数据的档案
     */
    public String filed(ResearchReport researchReport, ReportFiled reportFiled) {

        ResearchReport r = new ResearchReport();
        r.setArchive(StatusConstants.DONE);
        r.setId(researchReport.getId());
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Integer rowNum = studentHessianService.updateResearchReport(gson.toJson(r));
        if (rowNum > 0) {
            BeanUtil.copyProperties(researchReport, reportFiled);
            // 标记归档 并且library_research_report_zz.jsp页面由此字段判断显示的按钮
            reportFiled.setArchive(StatusConstants.DONE);
            reportFiled.setId(null);
            this.save(reportFiled);
        } else {
            return ErrorCode.ERROR_DATA;
        }

        return ErrorCode.SUCCESS;
    }
}
