package com.daizhihua.manager.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;


@Data
public class UserVo {

    @ApiModelProperty(value = "分页")
    private int currentPage;
    @ApiModelProperty(value = "显示数")
    private int pageSize;

    private List<Long> deptId;

    private String enabled;

    private String username;




}
