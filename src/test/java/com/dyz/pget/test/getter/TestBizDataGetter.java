package com.dyz.pget.test.getter;

import com.dyz.pget.bizdata.IBizData;
import com.dyz.pget.core.BizDataGetter;
import com.dyz.pget.exception.BizDataFetchException;
import com.dyz.pget.test.TestBase;
import com.dyz.pget.test.bizdata.ProductInfoBizData;
import com.dyz.pget.test.bizdata.ShopInfoBizData;
import com.dyz.pget.test.bizdata.UserInfoBizData;
import org.junit.Test;

import java.util.List;

/**
 * Created by daiyongzhi on 2020/2/23.<br/>
 * 使用BizDataGetter并行获取数据对象
 */
public class TestBizDataGetter extends TestBase{

    @Test
    public void testGetter() throws BizDataFetchException {
        long userId = 1345L;
        long shopId = 11L;
        long productId = 11011L;
        List<IBizData> bizDataList = BizDataGetter.build()
                .get(UserInfoBizData.class,userId)
                .get(ProductInfoBizData.class,shopId,productId)
                .get(ShopInfoBizData.class,shopId)
                .doGet(100L);

        UserInfoBizData userInfoBizData = (UserInfoBizData)bizDataList.get(0);
        ProductInfoBizData productInfoBizData = (ProductInfoBizData)bizDataList.get(1);
        ShopInfoBizData shopInfoBizData = (ShopInfoBizData)bizDataList.get(2);

        System.out.println(userInfoBizData.format2String());
        System.out.println(productInfoBizData.format2String());
        System.out.println(shopInfoBizData.format2String());
    }

}
