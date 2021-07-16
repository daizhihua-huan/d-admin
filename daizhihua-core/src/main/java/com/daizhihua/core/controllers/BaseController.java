package com.daizhihua.core.controllers;

import com.daizhihua.core.res.Resut;

public interface BaseController<T> {

    Resut list(Integer currentPage,Integer pageSize);

    Resut add(T t);

    Resut delete(Long id);

    Resut update(T t);

}
