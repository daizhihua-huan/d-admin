package com.daizhihua.manager.service.imple;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daizhihua.core.config.Constant;
import com.daizhihua.core.entity.*;
import com.daizhihua.core.enums.DataScopeEnum;
import com.daizhihua.core.mapper.*;
import com.daizhihua.manager.service.DataScopeService;
import com.daizhihua.manager.service.SystemUserService;
import com.daizhihua.oauth.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SystemUserServiceImple implements SystemUserService {
    /**
     * 用户
     */
    @Autowired
    private SysUserMapper sysUserMapper;
    //密码加密
    @Autowired
    private PasswordEncoder passwordEncoder;
    //角色
    @Autowired
    private SysUsersJobsMapper sysUsersJobsMapper;
    //岗位
    @Autowired
    private SysUsersRolesMapper sysUsersRolesMapper;
    //角色
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private DataScopeService dataScopeService;
    @Autowired
    private SysRoleDeptsMapper sysRoleDeptsMapper;
    @Override
    public List<Map<String,Object>> listUser(int currentPage,int pageSize) {


        return  sysUserMapper.listUserForDepids(getDepIds(),(currentPage-1)*pageSize,pageSize);
    }

    @Override
    public List<Map<String, Object>> listUser(int currentPage, int pageSize, List<Long> depts) {

        return sysUserMapper.listUserForDepids(depts,(currentPage-1)*pageSize,pageSize);
    }

    @Override
    public List<Map<String, Object>> listUser(int currentPage, int pageSize,List<Long> depts, String enabled, String username) {
        if(StringUtils.hasText(username)){
            username = "%"+username+"%";
        }
        if(depts.size()>0){
            return sysUserMapper.listUserForDepidsLikeSearch(depts,(currentPage-1)*pageSize,pageSize,enabled,username);
        }else{
            return sysUserMapper.listUserForDepidsLikeSearch(getDepIds(),(currentPage-1)*pageSize,pageSize,enabled,username);
        }
    }

    @Override
    public void add(SysUser sysUser) {
        String password = passwordEncoder.encode(Constant.PASSWORD);
        String name = SecurityUtils.getCurrentUser().getUsername();
        sysUser.setUser(name,password);
        sysUserMapper.insert(sysUser);
        List<Integer> roles = sysUser.getRoles();
        for (Integer role : roles) {
            SysUsersRoles sysUsersRoles = new SysUsersRoles();
            sysUsersRoles.setRoleId(Long.valueOf(role));
            sysUsersRoles.setUserId(sysUser.getUserId());
            sysUsersRolesMapper.insert(sysUsersRoles);
        }
        List<Integer> jobs = sysUser.getJobs();
        for (Integer job : jobs) {
            SysUsersJobs sysUsersJobs = new SysUsersJobs();
            sysUsersJobs.setJobId(Long.valueOf(job));
            sysUsersJobs.setUserId(sysUser.getUserId());
            sysUsersJobsMapper.insert(sysUsersJobs);
        }
    }

    @Override
    public int getCountUserForUserName(String userName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",userName);
        return sysUserMapper.selectCount(queryWrapper);
    }

    @Override
    public int deleteUserForUserId(Long userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        sysUsersJobsMapper.deleteByMap(map);
        sysUsersRolesMapper.deleteByMap(map);
        return sysUserMapper.deleteById(userId);
    }

    @Override
    public void updateForUser(SysUser sysUser) {
        sysUserMapper.updateById(sysUser);
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",sysUser.getUserId());
        List<Integer> jobs = sysUser.getJobs();
        sysUsersJobsMapper.deleteByMap(map);
        for (Integer job : jobs) {
            SysUsersJobs sysUsersJobs = new SysUsersJobs();
            sysUsersJobs.setUserId(sysUser.getUserId());
            sysUsersJobs.setJobId(Long.valueOf(job));
            sysUsersJobsMapper.insert(sysUsersJobs);
        }
        List<Integer> roles = sysUser.getRoles();
        sysUsersRolesMapper.deleteByMap(map);
        for (Integer role : roles) {
            SysUsersRoles sysUsersRoles = new SysUsersRoles();
            sysUsersRoles.setUserId(sysUser.getUserId());
            sysUsersRoles.setRoleId(Long.valueOf(role));
            sysUsersRolesMapper.insert(sysUsersRoles);
            SysRolesDepts sysRolesDepts = new SysRolesDepts();
            sysRolesDepts.setDeptId(sysUser.getDeptId());
            sysRolesDepts.setRoleId(Long.valueOf(role));
            map.clear();
            map.put("role_id",role);
            sysRoleDeptsMapper.deleteByMap(map);
            sysRoleDeptsMapper.insert(sysRolesDepts);
        }

    }

    @Override
    public void update(long enabled,long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setEnabled(enabled);
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public int countUser() {
        return sysUserMapper.count();
    }


    public List<Long> getDepIds(){
        /**
         * 获取用户的角色
         * 获取角色对权限
         * 获取部门
         * 获取用户列表
         */
        List<SysRole> sysRoles = sysRoleMapper.selectRoleForUserId(SecurityUtils.getCurrentUser().getUserId());
        List<Long> list = new ArrayList<>();
        for (SysRole sysRole : sysRoles) {
            //获取角色权限
            list.addAll(dataScopeService.getDataScope(SecurityUtils.getCurrentUser(), sysRole));
        }
        return list;
    }
}
