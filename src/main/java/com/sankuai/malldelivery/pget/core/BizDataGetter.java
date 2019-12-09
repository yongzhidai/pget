package com.sankuai.malldelivery.pget.core;

import com.sankuai.malldelivery.pget.bizdata.IBizData;
import com.sankuai.malldelivery.pget.exception.BizDataFetchException;
import com.sankuai.malldelivery.pget.provider.MtthrfitProviderInvoker;
import com.sankuai.malldelivery.pget.util.ParamCheckUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by daiyongzhi on 2019/10/28.
 * 业务数据获取工具
 */
public class BizDataGetter {

    List<BizDataFetcher> fetcherList;

    private BizDataGetter() {
        this.fetcherList = new ArrayList<>();
    }

    public static BizDataGetter build(){
        return new BizDataGetter();
    }

    public BizDataGetter get(Class<? extends IBizData> bizDataClass, Object... args){
        if(bizDataClass.isInterface()){
            throw new RuntimeException("通过Getter获取BizData,类型必须是具体的BizData实现类!");
        }
        ParamCheckUtil.check(bizDataClass,args);
        MtthrfitProviderInvoker providerInvoker = BizDataManager.getProviderByBizDataClass(bizDataClass);
        fetcherList.add(new BizDataFetcher(providerInvoker,args));
        return this;
    }

    /**
     * 使用时，是通过add顺序，来获取bizdata
     * @param timeOut
     * @return
     * @throws BizDataFetchException
     */
    public List<IBizData> doGet(long timeOut) throws BizDataFetchException {
        if(fetcherList == null || fetcherList.size() < 1){
            return new ArrayList<>(0);
        }
        BizDataManager.bizDataFetcherExecutor.runBizDataFetcher(fetcherList,timeOut, TimeUnit.MILLISECONDS);
        List<IBizData> bizDataList = new ArrayList<>(fetcherList.size());
        for(BizDataFetcher fetcher : fetcherList){
            bizDataList.add(fetcher.getBizData());
        }
        return bizDataList;
    }


}
