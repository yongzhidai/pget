package com.sankuai.malldelivery.pget.exception;

/**
 * Created by daiyongzhi on 2019/8/22.
 * 获取业务数据超时异常
 */
public class BizDataFetchTimeOutException extends BizDataFetchException{

    public BizDataFetchTimeOutException(String message) {
        super(message);
    }
}
