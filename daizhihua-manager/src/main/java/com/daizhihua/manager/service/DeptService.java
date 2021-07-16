package com.daizhihua.manager.service;


import com.daizhihua.core.entity.SysDept;
import com.daizhihua.manager.entity.DeptVo;

import java.util.List;

public interface DeptService {

    List<SysDept> listDept(String name);

    /**
     * 查找子类部门
     * @param deptList
     * @return
     */
    List<Long> getDeptChildren(List<SysDept> deptList);
}
