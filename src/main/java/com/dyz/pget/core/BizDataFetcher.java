package com.dyz.pget.core;

import com.dyz.pget.bizdata.AbstractParamedBizData;
import com.dyz.pget.bizdata.IBizData;
import com.dyz.pget.exception.*;
import com.dyz.pget.provider.ProviderInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created by daiyongzhi on 2019/8/21.
 * 业务数据获取类
 */
class BizDataFetcher implements Callable<Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BizDataFetcher.class);

    private ProviderInvoker providerInvoker;
    private Object[] args;

    private volatile boolean success = false;
    private volatile IBizData bizData;
    private IBizData defaultBizData;
    private volatile Exception exception;

    /* --------------------------------对外使用方法------------------------------ */

    public BizDataFetcher(ProviderInvoker providerInvoker, Object... args) {
        this.providerInvoker = providerInvoker;
        this.args = args;
        if(this.providerInvoker == null){
            throw new RuntimeException("provider不能为空");
        }
        defaultBizData = defaultBizDataOnError();
    }


    @Override
    public Void call() throws Exception {
        String monitorName = null;
        try {
            monitorName = providerInvoker.getMonitorName();
        }catch (Exception e){
            monitorName = "Unknown";
        }
        long startTime = System.currentTimeMillis();
        StringBuilder logContent = new StringBuilder();
        logContent.append(monitorName).append("---");
        doFetch();
        long elapsedTime = System.currentTimeMillis() - startTime;
        logContent.append(elapsedTime).append("(ms)").append("---");
        if(exception == null){
            logContent.append("success");
        }else{
            logContent.append("failed:").append(exception);
        }
        LOGGER.info(logContent.toString());
        return null;
    }

    /**
     * 返回业务数据
     * @return
     */
    public IBizData getBizData() throws BizDataFetchException {
        if(success){
            return bizData;
        }
        if(defaultBizData != null){
            return defaultBizData;
        }
        if(exception != null){//出现异常
            throw new BizDataFetchFailedException(providerInvoker.getMonitorName()+"获取数据时，发生异常. error:",exception);
        }else if(!success){//没有成功 & 没有异常，则是因为没执行完。
            throw new BizDataFetchTimeOutException(providerInvoker.getMonitorName()+"尚未执行完!");
        }else {
            throw new BizDataFetchUnknownException(providerInvoker.getMonitorName()+"获取业务数据时，发生未知异常!");
        }
    }


    /* --------------------------------内部使用方法------------------------------ */

    private void doFetch(){
        try {
            IBizData bizData = providerInvoker.invoke(args);
            if(bizData != null && bizData instanceof AbstractParamedBizData){
                ((AbstractParamedBizData) bizData).setParams(args);
            }
            setSuccess(bizData);
        } catch (Exception e) {
            if(defaultBizData != null){
                StringBuilder log = new StringBuilder();
                log.append(providerInvoker.getMonitorName())
                        .append("调用失败. ")
                        .append("使用默认值：")
                        .append(defaultBizData.format2String())
                        .append(". Cause by:");
                LOGGER.warn(log.toString(),e);
                this.exception = new BizDataFetchTolerableException(providerInvoker.getMonitorName()+"获取异常(有兜底). error:",e);
            }else{
                this.exception = e;
            }
        }
    }

    private void setSuccess(IBizData bizData) {
        this.bizData = bizData;
        this.success = true;
    }

    /**
     * 当出现调用错误是，返回的默认值，如果不支持默认值，可以返回null；
     * @return
     */
    private IBizData defaultBizDataOnError() {
        try {
            Class bizDataClazz = providerInvoker.getBizDataClass();
            Method method = bizDataClazz.getDeclaredMethod("defaultBizData");
            method.setAccessible(true);
            Object instance = bizDataClazz.newInstance();
            return (IBizData) method.invoke(instance);
        }catch (Exception e){
            LOGGER.error("获取默认值时，发生异常：",e);
            return null;
        }
    }


}
