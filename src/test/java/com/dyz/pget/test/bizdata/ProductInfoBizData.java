package com.dyz.pget.test.bizdata;

import com.alibaba.fastjson.JSON;
import com.dyz.pget.bizdata.AbstractParamedBizData;
import com.dyz.pget.bizdata.IBizData;

/**
 * Created by daiyongzhi on 2020/2/23.
 * 商品信息业务数据对象<br/>
 * 如果继承 AbstractParamedBizData 则可以调用getParams方法，获得得到此数据对象时的请求参数(用于日志打印、问题排查)
 */
public class ProductInfoBizData extends AbstractParamedBizData implements IBizData{
    private Long prouctId;
    private Long shopId;
    private String name;

    /**
     * 必须提供默认构造参数
     */
    public ProductInfoBizData() {
    }

    public ProductInfoBizData(Long prouctId, Long shopId, String name) {
        this.prouctId = prouctId;
        this.shopId = shopId;
        this.name = name;
    }

    /**
     * 如果此数据对象获取失败时的默认兜底值。如果不支持兜底，则返回null即可。
     * @return
     */
    @Override
    public IBizData defaultBizData() {
        return null;
    }

    @Override
    public String format2String() {
        return new StringBuilder()
                .append("参数:").append(JSON.toJSONString(getParams())).append('\n')
                .append("ID:").append(prouctId)
                .append(",门店id:").append(shopId)
                .append(",名称:").append(name)
                .toString();
    }
}
