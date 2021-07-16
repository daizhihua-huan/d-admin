package com.daizhihua.manager.controller;

import com.daizhihua.core.controllers.BaseController;
import com.daizhihua.core.entity.SysUser;
import com.daizhihua.manager.service.MenuService;
import com.daizhihua.core.entity.SysMenu;
import com.daizhihua.core.res.Resut;
import com.daizhihua.oauth.util.SecurityUtils;
import com.daizhihua.oauth.entity.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "菜单")
@RestController(value = "menuController")
@RequestMapping(value = "menu")
public class MenuController implements BaseController<SysMenu> {

    @Autowired
    private MenuService menuService;

    /**
     * 查询当前所有的菜单
     * @return
     */
    @RequestMapping(value = "/listMenu",method = RequestMethod.POST)
    public Resut listMenu(){
        SysUser userDto =  SecurityUtils.getCurrentUser();
        List<SysMenu> sysMenus = menuService.listMenu(userDto.getUserId());
        return Resut.ok(menuService.buildMenus( menuService.buildTree(sysMenus)));
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @Override
    public Resut list(Integer currentPage, Integer pageSize) {
        SysUser userDto =  SecurityUtils.getCurrentUser();
        List<SysMenu> sysMenus = menuService.listMenu(userDto.getUserId());
        return Resut.ok(menuService.buildTree(sysMenus));
    }

    @ApiOperation(value = "根据角色查询菜单id")
    @RequestMapping(value = "/listMenuIds",method = RequestMethod.GET)
    public Resut listMenuIds(Long roleId){

        return Resut.ok(menuService.listMenuIds(roleId));
    }

    @Override
    public Resut add(SysMenu sysMenu) {
        return null;
    }

    @Override
    public Resut delete(Long id) {
        return null;
    }

    @Override
    public Resut update(SysMenu sysMenu) {
        return null;
    }
}
