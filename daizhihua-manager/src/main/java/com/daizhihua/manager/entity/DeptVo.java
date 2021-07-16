package com.daizhihua.manager.entity;

import io.swagger.annotations.Api;
import lombok.Data;

import java.util.List;

@Data
@Api(tags = "部门目标对象")
public class DeptVo {
    private String id;

    private String label;

    private List<DeptVo> children;

    private boolean isDisabled;

    private boolean isNew;

    private boolean isDefaultExpanded;

}
