package com.dyz.pget.exception;

/**
 * Created by daiyongzhi on 2019/8/22.
 * 业务数据获取可容忍异常，因为有数据兜底。
 */
public class BizDataFetchTolerableException extends BizDataFetchException{

    public BizDataFetchTolerableException(String message, Throwable cause) {
        super(message, cause);
    }
}
