package com.daizhihua.manager.service.imple;

import com.daizhihua.core.entity.SysJob;
import com.daizhihua.core.entity.SysUsersJobs;
import com.daizhihua.core.mapper.SysJobMapper;
import com.daizhihua.core.mapper.SysUsersJobsMapper;
import com.daizhihua.manager.service.JobService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImple implements JobService {

    @Autowired
    private SysJobMapper sysJobMapper;

    @Autowired
    private SysUsersJobsMapper sysUsersJobsMapper;

    @Override
    public List<SysJob> listJob() {

        return sysJobMapper.selectByMap(null);
    }

    @Override
    public List<SysUsersJobs> getUserJob(Long userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        return sysUsersJobsMapper.selectByMap(map);
    }
}
