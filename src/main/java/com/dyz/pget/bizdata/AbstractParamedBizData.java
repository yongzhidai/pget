package com.dyz.pget.bizdata;

/**
 * Created by daiyongzhi on 2019/12/14.
 * 如果想获取请求BizData时的参数列表，可实现此抽象类
 */
public abstract class AbstractParamedBizData {

    private Object[] params;

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}