package com.houqin.biz.menuType;

import com.a_268.base.core.BaseBiz;
import com.houqin.dao.menuType.MenuTypeDao;
import com.houqin.entity.menuType.MenuType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单类型管理
 *
 * @author ccl
 * @create 2016-12-14-13:52
 */
@Service
public class MenuTypeBiz extends BaseBiz<MenuType,MenuTypeDao>{

    public List<MenuType> getAllMenuType() {
        List<MenuType> repairTypeList = this.find(null,"1=1");
        return repairTypeList;
    }
}
