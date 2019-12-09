package com.sankuai.malldelivery.pget.provider.annotation;

import com.sankuai.malldelivery.pget.parser.IBizDataParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by daiyongzhi on 2019/12/4.
 * 用于指定得到BizData的来源(远程服务的appkey、服务类、方法名、参数和返回值信息等)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MtthriftBizDataProvider {
    /** 远程服务的appkey */
    String remoteAppKey();
    /** 远程服务的service类 */
    Class  serviceClass();
    /** 远程服务service类的方法名 */
    String methodName();
    /** 方法的参数类型列表 */
    Class[] paramTypeList();
    /** 方法返回值类型 */
    Class  responseClass();
    /** 业务数据解析器 */
    Class<? extends IBizDataParser> bizDataParser();
    /** 超时时间(毫秒) */
    int timeout() default 500;
}
