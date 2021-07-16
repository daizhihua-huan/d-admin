package com.daizhihua.manager.controller;

import com.daizhihua.core.res.Resut;
import com.daizhihua.manager.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "岗位接口")
@RestController
@RequestMapping(value = "job")
public class JobController {

    @Autowired
    private JobService jobService;
    @ApiOperation(value = "查询所有岗位")
    @RequestMapping(value = "listJob",method = RequestMethod.GET)
    public Resut listJob(){
        return Resut.ok(jobService.listJob());
    }

    @RequestMapping(value = "getUserJob",method = RequestMethod.GET)
    public Resut getUserJob(Long userId){
        return Resut.ok(jobService.getUserJob(userId));
    }
}
