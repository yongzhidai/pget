package com.sankuai.malldelivery.pget.util;

import com.sankuai.malldelivery.pget.bizdata.IBizData;
import com.sankuai.malldelivery.pget.provider.annotation.MtthriftBizDataProvider;

/**
 * Created by daiyongzhi on 2019/12/4.
 */
public class ParamCheckUtil {

    public static void check(Class<? extends IBizData> bizDataClass, Object... args){
        MtthriftBizDataProvider bizDataProvider = bizDataClass.getAnnotation(MtthriftBizDataProvider.class);
        if(bizDataProvider == null){
            throw new RuntimeException(bizDataClass.getSimpleName()+"没有配置BizDataProvider注解!");
        }

        Class[] paramTypes = bizDataProvider.paramTypeList();

        if(paramTypes == null || paramTypes.length < 1){
            if(args == null || args.length < 1){
                return;
            }else{
                throw new RuntimeException("实际传参与BizData配置的参数不符");
            }
        }else{//指定的参数不为空
            if(args == null || args.length < 1){//实际参数为空
                throw new RuntimeException("参数不能为空");
            }
            if(paramTypes.length != args.length){
                throw new RuntimeException("实际传参与BizData配置的参数数量不一致");
            }
            for(int i=0;i<paramTypes.length;i++){
                if(!paramTypes[i].isAssignableFrom(args[i].getClass())){
                    throw new RuntimeException("实际传参与BizData配置的参数类型不一致");
                }
            }
        }

    }


}
