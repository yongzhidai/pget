package com.dyz.pget.provider;


import com.dyz.pget.bizdata.IBizData;
import com.dyz.pget.util.ParamCheckUtil;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Created by daiyongzhi on 2019/12/09.
 *
 */
public class ProviderInvoker {
    private Object object;
    private Method method;

    public ProviderInvoker(Object object, Method method) {
        if(object == null || method == null){
            throw new RuntimeException("provider的目标对象和目标方法不能为空!");
        }
        this.object = object;
        this.method = method;
    }

    public IBizData invoke(Object... args){
        return (IBizData) ReflectionUtils.invokeMethod(method,object,args);
    }


    public String getMonitorName() {
        Class bizDataClazz = method.getReturnType();
        StringBuilder sb = new StringBuilder();
        sb.append(bizDataClazz.getSimpleName()).append("(")
                .append(object.getClass().getSimpleName()).append(".").append(method.getName())
                .append(")");
        return sb.toString();
    }

    public Class<? extends IBizData> getBizDataClass() {
        Class bizDataClazz = method.getReturnType();
        return bizDataClazz;
    }

    public void checkParam(Object... args) {
        Class[] paramTypes = method.getParameterTypes();
        ParamCheckUtil.check(paramTypes, args);
    }

    public void destroy() {

    }

}
