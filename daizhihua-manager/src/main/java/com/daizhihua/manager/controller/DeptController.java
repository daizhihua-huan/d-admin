package com.daizhihua.manager.controller;

import com.daizhihua.core.res.Resut;
import com.daizhihua.manager.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "部门的方法")
@RequestMapping(value = "dept")
@Slf4j
public class DeptController {

    @Autowired
    DeptService deptService;
    @ApiOperation(value = "查询部门")
    @RequestMapping(value = "listDept",method = RequestMethod.GET)
    public Resut listDept(String name){
        log.info("name是"+name);
        return Resut.ok(deptService.listDept(name));
    }
}
