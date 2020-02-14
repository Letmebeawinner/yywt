package com.oa.biz.desk;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.oa.biz.function.FunctionBiz;
import com.oa.dao.desk.DeskDao;
import com.oa.entity.desk.Desk;
import com.oa.entity.desk.DeskDto;
import com.oa.entity.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的桌面
 *
 * @author ccl
 * @create 2017-01-17-14:42
 */
@Service
public class DeskBiz extends BaseBiz<Desk, DeskDao> {

    @Autowired
    private FunctionBiz functionBiz;

    /**
     * @Description:查询所有的桌面链接
     * @author: ccl
     * @Param: [sql]
     * @Return: java.util.List<com.oa.entity.desk.Desk>
     * @Date: 2017-01-20
     */
    public List<Desk> queryDeskList(String sql) {
        List<Desk> deskList = this.find(null, sql);
        return deskList;
    }


    public List<DeskDto> getDeskDtoList(@ModelAttribute("pagination") Pagination pagination, String whereSql) {
        List<Desk> deskList = this.find(pagination, whereSql);
        List<DeskDto> list = new ArrayList<>();
        if (deskList != null && deskList.size() > 0) {
            for (Desk desk : deskList) {
                DeskDto dto = new DeskDto();
                Function function1 = functionBiz.findById(desk.getFunctionId());
                if (function1 != null) {
                    dto.setName(function1.getName());
                    dto.setLink(function1.getLink());
                    dto.setSort(function1.getSort());
                    list.add(dto);
                }
            }
        }
        return list;
    }


    /**
     * 添加桌面
     *
     * @param userId
     * @param functionIds
     */
    public void tx_updateDesk(Long userId, String functionIds) {

        List<Long> ids = new ArrayList<>();
        String whereSql = " userId='" + userId + "'";
        List<Desk> deskList = this.find(null, whereSql);

        //刪除
        if (deskList != null && deskList.size() > 0) {
            for (Desk desk : deskList) {
                ids.add(desk.getId());
            }
            this.deleteByIds(ids);
        }

        //添加  scheduleIds格式多个id以，隔开
        if (!StringUtils.isTrimEmpty(functionIds)) {
            String[] arr = functionIds.substring(0, functionIds.length() - 1).split(",");
            List<Desk> desks = new ArrayList<>();
            for (String functionId : arr) {
                Desk desk = new Desk();
                desk.setUserId(userId);
                desk.setFunctionId(Long.parseLong(functionId));
                desks.add(desk);
            }
            this.saveBatch(desks);
        }
    }
}
