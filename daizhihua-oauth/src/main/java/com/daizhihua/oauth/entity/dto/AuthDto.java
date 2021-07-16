package com.daizhihua.oauth.entity.dto;

import com.sun.istack.internal.NotNull;
import lombok.Data;

@Data
public class AuthDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String code;
    @NotNull
    private String key;
}
