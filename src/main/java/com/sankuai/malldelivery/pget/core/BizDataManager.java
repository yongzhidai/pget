package com.sankuai.malldelivery.pget.core;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.meituan.mtrace.Tracer;
import com.meituan.service.mobile.mtthrift.proxy.ThriftClientProxy;
import com.sankuai.malldelivery.pget.bizdata.IBizData;
import com.sankuai.malldelivery.pget.provider.CustomProviderInvoker;
import com.sankuai.malldelivery.pget.provider.IProviderInvoker;
import com.sankuai.malldelivery.pget.provider.MtthrfitProviderInvoker;
import com.sankuai.malldelivery.pget.provider.annotation.CustomProvider;
import com.sankuai.malldelivery.pget.provider.annotation.MtthriftProvider;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by daiyongzhi on 2019/12/4.
 * 初始化类。需要在spring容器启动前完成初始化工作
 */
public class BizDataManager implements BeanPostProcessor {

    private String bizDataPackage;

    Integer corePoolSize;
    Integer maximumPoolSize;
    long keepAliveTime = 0L;
    Integer queueSize = 1000;


    public static BizDataFetcherExecutor bizDataFetcherExecutor;
    private Table<String, String, ThriftClientProxy> proxyTable = TreeBasedTable.create();
    private static Map<Class<? extends IBizData>,IProviderInvoker>  providerInvokerMap = new HashMap<>();
    public static BizDataManager instance;

    public void init()throws Exception {
        bizDataFetcherExecutor = new BizDataFetcherExecutor(corePoolSize,maximumPoolSize,keepAliveTime,queueSize);

        Reflections reflections = new Reflections(bizDataPackage);
        Set<Class<? extends IBizData>> bizDataClasses = reflections.getSubTypesOf(IBizData.class);
        if(bizDataClasses != null && bizDataClasses.size() > 0){
            for(Class<? extends IBizData> bizDataClass : bizDataClasses){
                MtthriftProvider bizDataProvider = bizDataClass.getAnnotation(MtthriftProvider.class);
                if(!bizDataClass.isInterface() && bizDataProvider != null){
                    String remoteAppKey = bizDataProvider.remoteAppKey();
                    String serviceName = bizDataProvider.serviceClass().getName();
                    if(!proxyTable.contains(remoteAppKey,serviceName)){
                        ThriftClientProxy clientProxy = new ThriftClientProxy();
                        clientProxy.setAppKey(Tracer.getAppKey());
                        clientProxy.setRemoteAppkey(bizDataProvider.remoteAppKey());
                        clientProxy.setGenericServiceName(bizDataProvider.serviceClass().getName());
                        clientProxy.setFilterByServiceName(true);
                        clientProxy.setTimeout(bizDataProvider.timeout());
                        clientProxy.setGeneric("json-common");
                        clientProxy.setNettyIO(true);
                        clientProxy.afterPropertiesSet();
                        proxyTable.put(remoteAppKey,serviceName,clientProxy);
                    }
                    ThriftClientProxy invokeProxy = proxyTable.get(remoteAppKey,serviceName);
                    providerInvokerMap.put(bizDataClass,new MtthrfitProviderInvoker(bizDataClass,invokeProxy));

                }
            }
        }

        instance = this;
    }

    public static IProviderInvoker getProviderByBizDataClass(Class<? extends IBizData> bizDataClass) {
        IProviderInvoker providerInvoker = providerInvokerMap.get(bizDataClass);
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
                Collection<IProviderInvoker> providerInvokers = providerInvokerMap.values();
                if(providerInvokers != null && providerInvokers.size() > 0){
                    for(IProviderInvoker providerInvoker : providerInvokers){
                        providerInvoker.destroy();
                    }
                }
            }
        }

    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        CustomProvider providerAnnotation = bean.getClass().getAnnotation(CustomProvider.class);
        if(providerAnnotation != null){
            Method[] methods = bean.getClass().getDeclaredMethods();
            for(Method method : methods){
                Class returnClazz = method.getReturnType();
                if(IBizData.class.isAssignableFrom(returnClazz) && !returnClazz.isInterface()){
                    ReflectionUtils.makeAccessible(method);
                    providerInvokerMap.put(returnClazz,new CustomProviderInvoker(bean,method));
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }




    public String getBizDataPackage() {
        return bizDataPackage;
    }

    public void setBizDataPackage(String bizDataPackage) {
        this.bizDataPackage = bizDataPackage;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }



}
