package com.daizhihua.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring上下文环境获取工具类
 * 1获取容器加载完成后自己注入的类
 *
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;

    }

    public static Object getBean(String beanName){
        Object bean = applicationContext.getBean(beanName);
        if(bean==null){
            throw new  RuntimeException("容器中没有叫"+beanName+"的类");
        }
        return bean;
    }

    public static Object getBean(Class clzz){

        Object bean = applicationContext.getBean(clzz);
        if(bean==null){
            throw new  RuntimeException("容器中没有叫"+clzz.getName()+"的类");
        }
        return bean;
    }


}
