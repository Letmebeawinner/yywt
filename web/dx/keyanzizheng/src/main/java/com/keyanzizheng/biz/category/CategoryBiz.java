package com.keyanzizheng.biz.category;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.keyanzizheng.biz.result.ResultFormBiz;
import com.keyanzizheng.constant.StatusConstants;
import com.keyanzizheng.dao.category.CategoryDao;
import com.keyanzizheng.entity.category.Category;
import com.keyanzizheng.entity.category.CategoryDTO;
import com.keyanzizheng.entity.result.ResultForm;
import com.keyanzizheng.utils.BeanUtil;
import com.keyanzizheng.utils.GenerateSqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 成果类别下的类型
 * <p>可增加成果形式名称字段 适当增加冗余 避免联合查询<p>
 *
 * @author YaoZhen
 * @date 12-20, 15:06, 2017.
 */
@Service
public class CategoryBiz extends BaseBiz<Category, CategoryDao> {

    @Autowired
    private ResultFormBiz resultFormBiz;

    /**
     * 返回拓展类
     *
     * @param pagination 分页
     * @param category   与数据库对应的实体类
     * @return 包含形式名称的拓展类
     */
    public List<CategoryDTO> findDTOList(Pagination pagination, Category category) {
        // List转Map
        List<ResultForm> resultForms = resultFormBiz.findAll();
        Map<Long, String> formMap = resultForms.stream().collect(Collectors.toMap(ResultForm::getId, ResultForm::getName));

        // 拼装DTO
        category.setStatus(StatusConstants.NEGATE);
        List<Category> categories = this.find(pagination, GenerateSqlUtil.getSql(category) + " order by sort desc");
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category c : categories) {
            CategoryDTO dto = new CategoryDTO();
            categoryDTOList.add(dto);
            BeanUtil.copyProperties(c, dto);

            // 实现联合查询
            dto.setResultFormName(formMap.get(c.getResultFormId()));
        }

        return categoryDTOList;
    }

    public List<Category> findChildByParentId(int parentId) {
        return super.find(null, "resultFormId = " + parentId);
    }
}
