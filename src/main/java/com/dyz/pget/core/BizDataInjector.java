package com.dyz.pget.core;


import com.dyz.pget.bizdata.IBizData;
import com.dyz.pget.exception.BizDataFetchException;
import com.dyz.pget.provider.ProviderInvoker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by daiyongzhi on 2019/10/26.
 * 业务数据注入器，通过注入的方法获取业务数据
 */
public class BizDataInjector {

    private Object dataWrapper;//数据包裹
    private Map<Class<? extends IBizData>,Field> bizDataTypeFieldMap;
    private List<BizDataFetcher> fetchers;

    private BizDataInjector(Object dataWrapper) {
        this.dataWrapper = dataWrapper;
        if(this.dataWrapper == null){
            throw new RuntimeException("dataWrapper不能为空");
        }
        bizDataTypeFieldMap = new HashMap<>();
        fetchers = new ArrayList<>();

    }

    public static BizDataInjector build(Object dataWrapper){
        return new BizDataInjector(dataWrapper);
    }

    /**
     *
     * @param bizDataClass 只能是实现了IBizData的类，不能是IBizData，否则在注入时会报错。
     * @param args
     * @return
     */
    public BizDataInjector inject(Class<? extends IBizData> bizDataClass,Object... args){
        if(bizDataClass.equals(IBizData.class)){
            throw new RuntimeException("BizData类必须是继承或实现了IBizData的类或接口");
        }
        Field[] fields = dataWrapper.getClass().getDeclaredFields();
        if(fields == null || fields.length<1){
            return this;
        }

        for(Field field : fields){
            Class type = field.getType();
            if(bizDataClass.isAssignableFrom(type)){
                field.setAccessible(true);
                bizDataTypeFieldMap.put(type,field);

                ProviderInvoker providerInvoker = BizDataManager.getProviderByBizDataClass(type);
                providerInvoker.checkParam(args);
                fetchers.add(new BizDataFetcher(providerInvoker,args));
            }
        }
        return this;
    }

    public void doInject(long timeOut) throws BizDataFetchException {
        if(dataWrapper == null){
            return;
        }
        if(fetchers == null || fetchers.size() < 1){
            return;
        }
        BizDataManager.bizDataFetcherExecutor.runBizDataFetcher(fetchers,timeOut, TimeUnit.MILLISECONDS);

        for(BizDataFetcher fetcher : fetchers){
            IBizData bizData = fetcher.getBizData();

            Field field = bizDataTypeFieldMap.get(bizData.getClass());
            try {
                field.set(dataWrapper,bizData);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }

    }
}
