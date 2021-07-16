package com.daizhihua.manager.service;

import com.daizhihua.core.entity.SysRole;
import com.daizhihua.core.entity.SysUsersRoles;

import java.util.List;

public interface RoleService {

    List<SysRole> listRoles();

    List<SysUsersRoles> getUserRoles(long userId);

    void addRole(SysRole sysRole);

    int deleteRole(Long roleId);

    int countRole(String name);

    int countRoleForId(Long roleId);

    int updateRole(SysRole sysRole);

}
