package com.daizhihua.manager.controller;


import com.daizhihua.core.entity.SysUser;
import com.daizhihua.core.res.Resut;

import com.daizhihua.manager.entity.UserVo;
import com.daizhihua.manager.service.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "用户信息")
@RestController
@RequestMapping(value = "sysUser")
public class UserController  {

    @Autowired
    private SystemUserService systemUserService;


    /**
     * 获取用户列表分三种情况
     * 1、直接获取分页的列表
     * 2、通过部门选择获取用户分页的列表
     * 3、搜索获取用户的列表
     *
     * 后期优化
     * 1、不需要每次获取部门id 将部门id更新到redis 同时维护
     * 2、mysql 增加索引提交访问速度
     * @param userVo
     * @return
     */
    @ApiOperation(value = "获取用户",notes = "获取所有用户列表")
    @RequestMapping(value = "list",method = RequestMethod.POST)
    public Resut list(@RequestBody UserVo userVo){

        Map<String,Object> map = new HashMap<>();
        if(userVo.getDeptId().size()>0){
            map.put("list", systemUserService.listUser(userVo.getCurrentPage(), userVo.getPageSize(),userVo.getDeptId()));
        }else if(StringUtils.hasText(userVo.getUsername())||StringUtils.hasText(userVo.getEnabled())){
            map.put("list",systemUserService.listUser(userVo.getCurrentPage(),userVo.getPageSize(),userVo.getDeptId(),userVo.getEnabled(),userVo.getUsername()));
        }else{
            map.put("list", systemUserService.listUser(userVo.getCurrentPage(), userVo.getPageSize()));
        }

        map.put("count", systemUserService.countUser());
        return Resut.ok(map);
    }

    @ApiOperation(value = "搜索",notes = "搜索符合条件的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enable",value = "状态"),
            @ApiImplicitParam(name = "username", value = "用户名")
    })
    @RequestMapping(value = "searchUser",method = RequestMethod.GET)
    public Resut searchUser(String enabled,String username){

        return Resut.ok();

    }


    @ApiOperation(value = "添加")
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Resut add(@RequestBody SysUser sysUser){
        if(systemUserService.getCountUserForUserName(sysUser.getUsername())>0){
            return Resut.error("用户名不能重复");
        }else{
            systemUserService.add(sysUser);
            return Resut.ok();
        }

    }
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "userId",value = "用户主键")
    @RequestMapping(value = "delete",method = RequestMethod.GET)
    public Resut delete(Long userId) {

        return Resut.ok(systemUserService.deleteUserForUserId(userId));
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Resut update(@RequestBody SysUser sysUser) {
        systemUserService.updateForUser(sysUser);
        return Resut.ok();
    }
    @ApiOperation(value = "更新禁用和激活")
    @RequestMapping(value = "updateEnable",method = RequestMethod.POST)
    public Resut updateEnable(Long enabled, Long userId){
        systemUserService.update(enabled,userId);
        return Resut.ok();
    }

}
