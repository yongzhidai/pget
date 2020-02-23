package com.dyz.pget.core;

import com.dyz.pget.exception.BizDataFetchException;
import com.dyz.pget.exception.BizDataFetchInvokeException;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * 业务数据执行器
 * @author daiyongzhi
 * @date 2019/12/04
 */
class BizDataFetcherExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BizDataFetcherExecutor.class);

    private static final int NORMAL_THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2;


    /**
     * 时间预估数据收集线程池
     */
    private ExecutorService threadPool;

    public BizDataFetcherExecutor(Integer corePoolSize,
                                       Integer maximumPoolSize,
                                       long keepAliveTime,
                                       Integer queueSize) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                corePoolSize == null?NORMAL_THREAD_COUNT * 5:corePoolSize,
                maximumPoolSize == null?200:maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueSize == null ?1000:queueSize),
                new ThreadFactoryBuilder().setNameFormat("BIZ-DATA-FETCHER-THREAD-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        executorService.prestartAllCoreThreads();
        threadPool = executorService;
    }

    public void destroy() throws InterruptedException {
        if (!threadPool.isShutdown()) {
            threadPool.shutdown();
            threadPool.awaitTermination(5000,TimeUnit.SECONDS);
        }
    }


    public void runBizDataFetcher(List<BizDataFetcher> bizDataFetchers, long timeOut, TimeUnit timeUnit)throws BizDataFetchException {
        try {
            List<Future<Void>> futureList = threadPool.invokeAll(bizDataFetchers,timeOut,timeUnit);
            for (Future<Void> future : futureList) {
                if(!future.isDone()){
                    future.cancel(true);
                }
            }
        } catch (Exception e) {
            LOGGER.error("runBizDataFetcher error: ",e);
            throw new BizDataFetchInvokeException("提交任务到线程池异常",e);
        }
    }

}
