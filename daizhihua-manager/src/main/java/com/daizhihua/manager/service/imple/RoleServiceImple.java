package com.daizhihua.manager.service.imple;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daizhihua.core.entity.SysRole;
import com.daizhihua.core.entity.SysUsersRoles;
import com.daizhihua.core.mapper.SysRoleMapper;
import com.daizhihua.core.mapper.SysUsersRolesMapper;
import com.daizhihua.manager.service.RoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RoleServiceImple implements RoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUsersRolesMapper sysUsersRolesMapper;

    @Override
    public List<SysRole> listRoles() {
        return sysRoleMapper.selectByMap(null);
    }

    @Override
    public List<SysUsersRoles> getUserRoles(long userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        return sysUsersRolesMapper.selectByMap(map);
    }

    @Override
    public void addRole(SysRole sysRole) {

      sysRoleMapper.insert(sysRole);
    }

    @Override
    public int deleteRole(Long roleId) {
        return sysRoleMapper.deleteById(roleId);
    }

    @Override
    public int countRole(String name) {
        Wrapper<SysRole> queryWrapper = new QueryWrapper<>();
        ((QueryWrapper<SysRole>) queryWrapper).eq("name",name);
        return sysRoleMapper.selectCount(queryWrapper);
    }

    @Override
    public int countRoleForId(Long roleId) {

        return sysRoleMapper.countRoleForRoleId(roleId);
    }

    @Override
    public int updateRole(SysRole sysRole) {
        return sysRoleMapper.updateById(sysRole);
    }
}
