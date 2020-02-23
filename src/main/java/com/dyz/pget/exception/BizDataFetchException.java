package com.dyz.pget.exception;

/**
 * Created by daiyongzhi on 2019/8/28.
 */
public class BizDataFetchException extends Exception{
    public BizDataFetchException(String message) {
        super(message);
    }

    public BizDataFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
