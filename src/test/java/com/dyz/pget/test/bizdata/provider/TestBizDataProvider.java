package com.dyz.pget.test.bizdata.provider;

import com.dyz.pget.provider.annotation.BizDataProvider;
import com.dyz.pget.test.bizdata.ProductInfoBizData;
import com.dyz.pget.test.bizdata.UserInfoBizData;

/**
 * Created by daiyongzhi on 2020/2/23.
 */
@BizDataProvider
public class TestBizDataProvider {

    /**
     * 异常要抛出，框架会捕获
     * @param userId
     * @return
     * @throws Exception
     */
    public UserInfoBizData getUserInfoBizData(long userId)throws Exception{
        //调用远程rpc或者其他数据源得到数据，并封装到Test1BizData对象中
        return new UserInfoBizData(userId,"张三",20);
    }

    public ProductInfoBizData getProductInfoBizData(long shopId,long productId)throws Exception{
        //调用远程rpc或者其他数据源得到数据，并封装到ProductInfoBizData对象中
        return new ProductInfoBizData(productId,shopId,"啤酒");
    }
}
