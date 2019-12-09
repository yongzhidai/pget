package com.sankuai.malldelivery.pget.provider;


import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Created by daiyongzhi on 2019/12/09.
 *
 */
public class CustomProviderInvoker {
    private Object object;
    private Method method;

    public CustomProviderInvoker(Object object, Method method) {
        if(object == null || method == null){
            throw new RuntimeException("provider的目标对象和目标方法不能为空!");
        }
        this.object = object;
        this.method = method;
    }

    public Object invoke(Object... args){
        return ReflectionUtils.invokeMethod(method,object,args);
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }
}
