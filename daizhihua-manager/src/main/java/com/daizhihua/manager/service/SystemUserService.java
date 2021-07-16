package com.daizhihua.manager.service;

import com.daizhihua.core.entity.SysUser;

import java.util.List;
import java.util.Map;

public interface SystemUserService {

    List<Map<String,Object>> listUser(int currentPage,int pageSize);

    List<Map<String,Object>> listUser(int currentPage,int pageSize,List<Long>depts);

    List<Map<String,Object>> listUser(int currentPage,int pageSize,List<Long> depts,String enabled,String username);

    void add(SysUser sysUser);

    int getCountUserForUserName(String userName);

    int deleteUserForUserId(Long userId);

    void updateForUser(SysUser sysUser);

    void update(long enabled,long uerId);

    int countUser();




}
