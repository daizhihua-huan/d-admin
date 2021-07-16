package com.daizhihua.manager.service;

import com.daizhihua.core.entity.SysJob;
import com.daizhihua.core.entity.SysUsersJobs;

import java.util.List;

public interface JobService {

    List<SysJob> listJob();

    List<SysUsersJobs> getUserJob(Long userId);
}
