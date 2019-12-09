package com.sankuai.malldelivery.pget;

import com.meituan.service.mobile.mtthrift.util.json.JacksonUtils;

/**
 * Created by daiyongzhi on 2019/12/4.
 */
public class Test {

    public static void main(String[] args) {
        Integer a = JacksonUtils.simpleDeserialize(null, Integer.class);
        System.out.println(a);


        Boolean b = JacksonUtils.simpleDeserialize("true", Boolean.class);
        System.out.println(b);

        String c = JacksonUtils.serialize("adf");
        System.out.println(c);

        c = JacksonUtils.serialize(1232);
        System.out.println(c);
    }
}
