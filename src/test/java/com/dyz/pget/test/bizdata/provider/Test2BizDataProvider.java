package com.dyz.pget.test.bizdata.provider;

import com.dyz.pget.provider.annotation.BizDataProvider;
import com.dyz.pget.test.bizdata.ShopInfoBizData;

/**
 * Created by daiyongzhi on 2020/2/23.
 */
@BizDataProvider
public class Test2BizDataProvider {

    /**
     * 异常要抛出，框架会捕获
     * @param shopId
     * @return
     * @throws Exception
     */
    public ShopInfoBizData getShopInfoBizData(long shopId)throws Exception{
        //调用远程rpc或者其他数据源得到数据，并封装到Test1BizData对象中
        if(shopId <= 0L){
            throw new Exception("门店不存在");
        }
        return new ShopInfoBizData(shopId,"全聚德");
    }


}
