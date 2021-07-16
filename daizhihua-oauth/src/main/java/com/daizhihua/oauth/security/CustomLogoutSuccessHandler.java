package com.daizhihua.oauth.security;

import com.daizhihua.core.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        JSONObject o = new JSONObject();
        o.put("status",HttpServletResponse.SC_OK);
        o.put("messgae","退出成功");
        try {
            ResponseUtil.write(response,o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
