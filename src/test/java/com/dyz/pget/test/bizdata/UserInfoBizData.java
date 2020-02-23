package com.dyz.pget.test.bizdata;

import com.dyz.pget.bizdata.IBizData;

/**
 * Created by daiyongzhi on 2020/2/23.
 * 用户信息业务数据对象
 */
public class UserInfoBizData implements IBizData{
    private Long userId;
    private String name;
    private Integer age;

    /**
     * 必须提供默认构造参数
     */
    public UserInfoBizData() {
    }

    public UserInfoBizData(Long userId, String name, Integer age) {
        this.userId = userId;
        this.name = name;
        this.age = age;
    }

    /**
     * 如果此数据对象获取失败时的默认兜底值。如果不支持兜底，则返回null即可。
     * @return
     */
    @Override
    public IBizData defaultBizData() {
        return null;
    }

    @Override
    public String format2String() {
        return new StringBuilder()
                .append("ID:").append(userId)
                .append(",名字:").append(name)
                .append(",年龄:").append(age)
                .toString();
    }
}
