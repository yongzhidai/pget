package com.dyz.pget.test.bizdata;

import com.dyz.pget.bizdata.IBizData;

/**
 * Created by daiyongzhi on 2020/2/23.
 */
public class ShopInfoBizData implements IBizData{
    private Long shopId;
    private String shopName;

    public ShopInfoBizData() {
    }

    public ShopInfoBizData(Long shopId, String shopName) {
        this.shopId = shopId;
        this.shopName = shopName;
    }

    @Override
    public IBizData defaultBizData() {
        return new ShopInfoBizData(0L,"兜底门店");
    }

    @Override
    public String format2String() {
        return new StringBuilder()
                .append("ID:").append(shopId)
                .append(",名称:").append(shopName)
                .toString();
    }
}
