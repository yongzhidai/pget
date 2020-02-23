package com.dyz.pget.test.injector;

import com.dyz.pget.test.bizdata.ProductInfoBizData;
import com.dyz.pget.test.bizdata.ShopInfoBizData;
import com.dyz.pget.test.bizdata.UserInfoBizData;

/**
 * Created by daiyongzhi on 2020/2/23.
 * 用于将所有的BizData封装到一起的包裹对象
 */
public class BizDataWrapper {
    private UserInfoBizData userInfoBizData;
    private ShopInfoBizData shopInfoBizData;
    private ProductInfoBizData productInfoBizData;


    public UserInfoBizData getUserInfoBizData() {
        return userInfoBizData;
    }

    public ShopInfoBizData getShopInfoBizData() {
        return shopInfoBizData;
    }

    public ProductInfoBizData getProductInfoBizData() {
        return productInfoBizData;
    }
}
