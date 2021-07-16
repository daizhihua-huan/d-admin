package com.daizhihua.oauth.service.imple;

import com.daizhihua.core.util.RedisUtil;
import com.daizhihua.excpetion.MessageNotFoundException;
import com.daizhihua.oauth.service.AuthService;
import com.wf.captcha.SpecCaptcha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 授权的逻辑操作
 *
 * @author daizhihua
 * @version 1.0
 */
@Service
@Slf4j
public class AuthServiceImple implements AuthService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    @Qualifier(value = "userService")
    private UserServiceImple userDetailsService;

    @Override
    public Map<String, Object> getImage() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisUtil.set(key, verCode, 30);
        // 将key和base64返回给前端
        Map<String,Object> map  = new HashMap<>();
        map.put("key",key);
        map.put("image",specCaptcha.toBase64());
        return map;
    }

    @Override
    public Map<String, Object> auth(String username, String password, String code, String key)throws MessageNotFoundException {
        Map<String, Object> map = new HashMap<>();
        log.info("秘钥key是:"+key);
        if(redisUtil.get(key)==null){
            map.put("message","验证码不存在，请重新生成");
           throw new MessageNotFoundException("验证码不存在，请重新生成");
        }
        String redisCode = redisUtil.get(key).toString();
        System.out.println(redisCode);
        // 判断验证码
        if (code ==null || !redisCode.equals(code.trim().toLowerCase())) {
            map.put("status",HttpServletResponse.SC_CREATED);
            map.put("message","验证码不正确");
            throw new MessageNotFoundException("验证码不正确");
        }
        map = userDetailsService.authLogin(username, password, code);
        map.put("status",HttpServletResponse.SC_OK);
        return map;
    }
}
