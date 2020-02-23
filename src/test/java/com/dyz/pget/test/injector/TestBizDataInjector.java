package com.dyz.pget.test.injector;

import com.dyz.pget.core.BizDataInjector;
import com.dyz.pget.exception.BizDataFetchException;
import com.dyz.pget.test.TestBase;
import com.dyz.pget.test.bizdata.ProductInfoBizData;
import com.dyz.pget.test.bizdata.ShopInfoBizData;
import com.dyz.pget.test.bizdata.UserInfoBizData;
import org.junit.Test;

/**
 * Created by daiyongzhi on 2020/2/23.<br/>
 * 使用BizDataInjector通过注入模式获取业务数据对象
 */
public class TestBizDataInjector extends TestBase{

    @Test
    public void testGetter() throws BizDataFetchException {
        long userId = 1345L;
        long shopId = 11L;
        long productId = 11011L;
        BizDataWrapper bizDataWrapper = new BizDataWrapper();
        BizDataInjector.build(bizDataWrapper)
                .inject(UserInfoBizData.class,userId)
                .inject(ProductInfoBizData.class,shopId,productId)
                .inject(ShopInfoBizData.class,shopId)
                .doInject(100L);



        System.out.println(bizDataWrapper.getUserInfoBizData().format2String());
        System.out.println(bizDataWrapper.getProductInfoBizData().format2String());
        System.out.println(bizDataWrapper.getShopInfoBizData().format2String());
    }

}
