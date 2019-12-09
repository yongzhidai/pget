package com.sankuai.malldelivery.pget.provider;


import com.sankuai.malldelivery.pget.bizdata.IBizData;
import com.sankuai.malldelivery.pget.util.ParamCheckUtil;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Created by daiyongzhi on 2019/12/09.
 *
 */
public class CustomProviderInvoker implements IProviderInvoker{
    private Object object;
    private Method method;

    public CustomProviderInvoker(Object object, Method method) {
        if(object == null || method == null){
            throw new RuntimeException("provider的目标对象和目标方法不能为空!");
        }
        this.object = object;
        this.method = method;
    }

    public IBizData invoke(Object... args){
        return (IBizData) ReflectionUtils.invokeMethod(method,object,args);
    }

    @Override
    public String getMonitorName() {
        Class bizDataClazz = method.getReturnType();
        StringBuilder sb = new StringBuilder();
        sb.append(bizDataClazz.getSimpleName()).append("(")
                .append(object.getClass().getSimpleName()).append(".").append(method.getName())
                .append(")");
        return sb.toString();
    }

    @Override
    public Class<? extends IBizData> getBizDataClass() {
        Class bizDataClazz = method.getReturnType();
        return bizDataClazz;
    }

    @Override
    public void checkParam(Object... args) {
        Class[] paramTypes = method.getParameterTypes();
        ParamCheckUtil.check(paramTypes, args);
    }

    @Override
    public void destroy() {

    }

}
