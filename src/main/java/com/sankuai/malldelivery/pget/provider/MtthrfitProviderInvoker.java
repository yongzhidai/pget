package com.sankuai.malldelivery.pget.provider;

import com.meituan.service.mobile.mtthrift.generic.GenericService;
import com.meituan.service.mobile.mtthrift.proxy.ThriftClientProxy;
import com.meituan.service.mobile.mtthrift.util.json.JacksonUtils;
import com.sankuai.malldelivery.pget.bizdata.IBizData;
import com.sankuai.malldelivery.pget.parser.IBizDataParser;
import com.sankuai.malldelivery.pget.provider.annotation.MtthriftProvider;
import com.sankuai.malldelivery.pget.util.ParamCheckUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daiyongzhi on 2019/12/4.
 */
public class MtthrfitProviderInvoker implements IProviderInvoker {

    private Class<? extends IBizData> bizDataClass;
    private String serviceName;
    private String methodName;
    private ThriftClientProxy proxy;
    public MtthrfitProviderInvoker(Class<? extends IBizData> bizDataClass, ThriftClientProxy invokeProxy){
        this.bizDataClass = bizDataClass;
        MtthriftProvider bizDataProvider = bizDataClass.getAnnotation(MtthriftProvider.class);
        this.serviceName = bizDataProvider.serviceClass().getSimpleName();
        this.methodName = bizDataProvider.methodName();
        this.proxy = invokeProxy;
    }

    @Override
    public IBizData invoke(Object... args)throws Exception{
        MtthriftProvider bizDataProvider = bizDataClass.getAnnotation(MtthriftProvider.class);
        List<String> paramTypes = new ArrayList<>();
        Class[] paramTypeList = bizDataProvider.paramTypeList();
        if(paramTypeList != null && paramTypeList.length > 0){
            for(Class paramType : paramTypeList){
                paramTypes.add(paramType.getName());
            }
        }
        List<String> values = new ArrayList<>();
        if(args != null && args.length > 0){
            for(Object arg : args){
                values.add(JacksonUtils.serialize(arg));
            }
        }

        GenericService genericClient = (GenericService) proxy.getObject();
        String respString = genericClient.$invoke(methodName,paramTypes,values);

        Object result = JacksonUtils.simpleDeserialize(respString, bizDataProvider.responseClass());

        Class<? extends IBizDataParser> bizDataParserClass = bizDataProvider.bizDataParser();
        IBizDataParser bizDataParser = bizDataParserClass.newInstance();
        return bizDataParser.parse(result);
    }

    @Override
    public void destroy(){
        if(proxy != null){
            proxy.destroy();
        }
    }

    @Override
    public String getMonitorName(){
        StringBuilder sb = new StringBuilder();
        sb.append(bizDataClass.getSimpleName()).append("(")
                .append(serviceName).append(".").append(methodName)
                .append(")");
        return sb.toString();
    }

    @Override
    public Class<? extends IBizData> getBizDataClass() {
        return bizDataClass;
    }

    @Override
    public void checkParam(Object... args) {
        MtthriftProvider bizDataProvider = bizDataClass.getAnnotation(MtthriftProvider.class);
        if(bizDataProvider == null){
            throw new RuntimeException(bizDataClass.getSimpleName()+"没有配置BizDataProvider注解!");
        }

        Class[] paramTypes = bizDataProvider.paramTypeList();

        ParamCheckUtil.check(paramTypes, args);
    }

}
