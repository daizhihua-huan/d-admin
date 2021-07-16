package com.daizhihua.manager.controller;

import com.daizhihua.core.controllers.BaseController;
import com.daizhihua.core.entity.SysRole;
import com.daizhihua.core.res.Resut;
import com.daizhihua.manager.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = "角色")
@RequestMapping(value = "roles")
@RestController
public  class RoleController implements BaseController<SysRole> {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "查询角色列表",notes = "查询所有的角色列表")
    @RequestMapping(value = "/listRoles",method = RequestMethod.GET)
    @Override
    public Resut list(Integer currentPage, Integer pageSize) {
        return Resut.ok(roleService.listRoles());
    }


    @ApiOperation(value = "根据用户id查询角色")
    @RequestMapping(value = "/getUserRoles",method = RequestMethod.GET)
    public Resut getUserRoles(Long userId){

        return Resut.ok(roleService.getUserRoles(userId));
    }
    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    @ApiOperation(value = "添加角色",notes = "添加角色")
    @Override
    public Resut add(@RequestBody SysRole sysRole) {
        if(roleService.countRole(sysRole.getName())>0){
            return Resut.error("角色名称不能重复！");
        }else{
            roleService.addRole(sysRole);
        }

        return Resut.ok();
    }

    @RequestMapping(value = "deleteRole",method = RequestMethod.GET)
    @Override
    public Resut delete(Long id) {
        if( roleService.countRoleForId(id)>0){
            return Resut.error("当前角色和用户关联不能删除");
        }


        roleService.deleteRole(id);
        return Resut.ok();
    }

    @ApiOperation(value = "更新角色")
    @RequestMapping(value = "updateRole",method = RequestMethod.POST)
    @Override
    public Resut update(@RequestBody SysRole sysRole) {
        roleService.updateRole(sysRole);
        return Resut.ok();
    }
}
