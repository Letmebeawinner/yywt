package com.houqin.biz.menus;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.houqin.biz.menuType.MenuTypeBiz;
import com.houqin.dao.menus.MenusDao;
import com.houqin.entity.menuType.MenuType;
import com.houqin.entity.menus.Menus;
import com.houqin.entity.menus.MenusDto;
import org.apache.tools.ant.types.resources.Archives;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单管理
 *
 * @author ccl
 * @create 2016-12-14-15:24
 */
@Service
public class MenusBiz extends BaseBiz<Menus,MenusDao> {
    @Resource
    private MenuTypeBiz menuTypeBiz;

    public List<MenusDto> getMenusType(@ModelAttribute("pagination") Pagination pagination, String whereSql) {
        List<Menus> menusList=this.find(pagination,whereSql);
        List<MenusDto> list = new ArrayList<>();
        if(menusList!=null && menusList.size()>0){
            for (Menus menus : menusList) {
                MenusDto dto = new MenusDto();
                MenuType menuType = menuTypeBiz.findById(menus.getTypeId());
                Menus menus1 = this.findById(menus.getId());
                dto.setId(menus.getId());
                dto.setTitle(menus1.getTitle());
                dto.setPrice(menus1.getPrice());
                dto.setMenuTypeName(menuType.getName());
                list.add(dto);
            }
        }

        return list;
    }
}
