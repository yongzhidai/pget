package com.dyz.pget.core;

import com.dyz.pget.bizdata.IBizData;
import com.dyz.pget.provider.ProviderInvoker;
import com.dyz.pget.provider.annotation.BizDataProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 初始化类。需要在spring容器启动前完成初始化工作
 */
public class BizDataManager implements BeanPostProcessor {

    Integer corePoolSize;
    Integer maximumPoolSize;
    long keepAliveTime = 0L;
    Integer queueSize = 1000;


    public static BizDataFetcherExecutor bizDataFetcherExecutor;
    private static Map<Class<? extends IBizData>,ProviderInvoker>  providerInvokerMap = new HashMap<>();
    public static BizDataManager instance;

    public void init()throws Exception {
        bizDataFetcherExecutor = new BizDataFetcherExecutor(corePoolSize,maximumPoolSize,keepAliveTime,queueSize);
        instance = this;
    }

    public static ProviderInvoker getProviderByBizDataClass(Class<? extends IBizData> bizDataClass) {
        ProviderInvoker providerInvoker = providerInvokerMap.get(bizDataClass);
        if(providerInvoker == null){
            throw new RuntimeException("未找到"+bizDataClass.getSimpleName()+"对应的provider");
        }
        return providerInvoker;
    }



    public void destroy() throws InterruptedException {
        if(bizDataFetcherExecutor != null){
            try {
                bizDataFetcherExecutor.destroy();
            }catch (InterruptedException e){
                throw e;
            }finally {
                Collection<ProviderInvoker> providerInvokers = providerInvokerMap.values();
                if(providerInvokers != null && providerInvokers.size() > 0){
                    for(ProviderInvoker providerInvoker : providerInvokers){
                        providerInvoker.destroy();
                    }
                }
            }
        }

    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        BizDataProvider providerAnnotation = bean.getClass().getAnnotation(BizDataProvider.class);
        if(providerAnnotation != null){
            Method[] methods = bean.getClass().getDeclaredMethods();
            for(Method method : methods){
                Class returnClazz = method.getReturnType();
                if(IBizData.class.isAssignableFrom(returnClazz) && !returnClazz.isInterface()){
                    ReflectionUtils.makeAccessible(method);
                    providerInvokerMap.put(returnClazz,new ProviderInvoker(bean,method));
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }





    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }


    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }


    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }


    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }



}
