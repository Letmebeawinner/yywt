package com.oa.biz.paper;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.common.BaseHessianService;
import com.oa.dao.paper.PaperDao;
import com.oa.entity.department.DepartMent;
import com.oa.entity.paper.Paper;
import com.oa.entity.paper.PaperDto;
import com.oa.entity.paper.PaperFunction;
import com.oa.entity.paper.PaperType;
import com.oa.utils.GenerateSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 封条业务层
 *
 * @author lzh
 * @create 2017-01-04-17:54
 */
@Service
public class PaperBiz extends BaseBiz<Paper, PaperDao> {

    @Autowired
    private PaperTypeBiz paperTypeBiz;
    @Autowired
    private PaperFunctionBiz paperFunctionBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    /**
     * @Description: 获取封条管理拓展类
     * @author: lzh
     * @Param: [pagination, paperDto]
     * @Return: java.util.List<com.oa.entity.paper.PaperDto>
     * @Date: 9:43
     */
    public List<PaperDto> getPaperDtos(Pagination pagination, Paper paperEntity) {
        String whereSql = GenerateSqlUtil.getSql(paperEntity);
        List<Paper> papers = this.find(pagination, whereSql);
        List<PaperDto> paperDtos = null;
        paperDtos = papers.stream()
                .map(paper -> convertPaperToDto(paper))
                .collect(Collectors.toList());
        return paperDtos;
    }

    /**
     * @Description: 将实体类的属性，复制到拓展类
     * @author: lzh
     * @Param: [paper]
     * @Return: com.oa.entity.paper.PaperDto
     * @Date: 11:40
     */
    private PaperDto convertPaperToDto(Paper paper) {
        PaperDto paperDto = new PaperDto();
        BeanUtils.copyProperties(paper, paperDto);
        if (paperDto.getPaperTypeId() != null) {
            PaperType paperType = paperTypeBiz.findById(paperDto.getPaperTypeId());
            if (paperType != null) {
                paperDto.setPaperTypeName(paperType.getName());
            }
        }
        if (paperDto.getPaperFunctionId() != null) {
            PaperFunction paperFunction = paperFunctionBiz.findById(paperDto.getPaperFunctionId());
            if (paperFunction != null) {
                paperDto.setPaperFunctionName(paperFunction.getName());
            }
        }
        if (paperDto.getDepartmentId() != null) {
            DepartMent departMent = baseHessianBiz.queryDepartemntById(paperDto.getDepartmentId());
            if (departMent != null) {
                paperDto.setDepartmentName(departMent.getDepartmentName());
            }
        }
        return paperDto;
    }
}
