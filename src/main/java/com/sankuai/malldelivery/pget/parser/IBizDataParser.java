package com.sankuai.malldelivery.pget.parser;

import com.sankuai.malldelivery.pget.bizdata.IBizData;

/**
 * Created by daiyongzhi on 2019/12/4.
 * 业务数据解析器。用于将远程服务的响应数据转换为对应的BizData对象。
 */
public interface IBizDataParser<R extends IBizData,P> {


    /**
     * 完成rpc响应数据对象到BizData的转换
     * @param response rpc服务的响应对象
     * @return 转换后的BizData数据对象
     * @throws Exception 解析失败时，可以抛出异常
     */
    R parse(P response) throws Exception;
}
