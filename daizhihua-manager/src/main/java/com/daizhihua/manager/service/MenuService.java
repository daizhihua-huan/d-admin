package com.daizhihua.manager.service;

import com.daizhihua.core.entity.SysMenu;
import com.daizhihua.manager.entity.MenuVo;

import java.util.List;

public interface MenuService {

    List<SysMenu> listMenu(Long userId);

    /**
     * 构建菜单树
     * @param menuDtos 原始数据
     * @return /
     */
    List<SysMenu> buildTree(List<SysMenu> menuDtos);

    /**
     * 构建菜单树
     * @param menuDtos /
     * @return /
     */
    List<MenuVo> buildMenus(List<SysMenu> menuDtos);

    List<Long> listMenuIds(Long roleId);
}
