package com.daizhihua.manager.service.imple;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daizhihua.core.entity.SysDept;
import com.daizhihua.core.mapper.SysDeptMapper;
import com.daizhihua.manager.entity.DeptVo;
import com.daizhihua.manager.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeptServiceImple implements DeptService {

    @Autowired
    SysDeptMapper sysDeptMapper;
    private Map<String,Object> map = new HashMap<>();
    @Override
    public List<SysDept> listDept(String name) {
        List<SysDept> sysDepts;
        if(StringUtils.hasText(name)){
            QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("name",name);
            sysDepts = sysDeptMapper.selectList(queryWrapper);
            tree(sysDepts);
        }else{
            map.put("pid",null);
            sysDepts = sysDeptMapper.selectByMap(map);
            tree(sysDepts);
        }

        return sysDepts;
    }

    @Override
    public List<Long> getDeptChildren(List<SysDept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dep->{
            //部门存在并且启用
            if(dep!=null&&dep.getEnabled()){
                map.put("pid",dep.getDeptId());
                List<SysDept> sysDepts = sysDeptMapper.selectByMap(map);
                if(sysDepts!=null&&sysDepts.size()>0){
                    list.addAll(getDeptChildren(sysDepts));
                }
                list.add(dep.getDeptId());
            }
        });
        return list;
    }

    public synchronized void tree( List<SysDept> list){
        for (SysDept sysDept : list) {
            map.put("pid",sysDept.getDeptId());
            DeptVo deptVo = new DeptVo();
            deptVo.setId(sysDept.getDeptId().toString());
            deptVo.setLabel(sysDept.getName());
            List<SysDept> sysDepts = sysDeptMapper.selectByMap(map);
            if(sysDepts.size()>0){
                sysDept.setChildren(sysDepts);
                tree(sysDepts);
            }

        }
    }
}
