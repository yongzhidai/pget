package com.sankuai.malldelivery.pget.provider;

import com.sankuai.malldelivery.pget.bizdata.IBizData;

/**
 * Created by daiyongzhi on 2019/12/9.
 */
public interface IProviderInvoker {

    IBizData invoke(Object... args)throws Exception;

    String getMonitorName();

    Class<? extends IBizData> getBizDataClass();

    void checkParam(Object... args);

    void destroy();
}
