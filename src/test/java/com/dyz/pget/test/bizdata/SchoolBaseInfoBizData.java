package com.dyz.pget.test.bizdata;

import com.dyz.pget.bizdata.IBizData;

/**
 * Created by daiyongzhi on 2020/2/23.
 * 学校基本信息数据对象
 */
public class SchoolBaseInfoBizData implements ISchoolBizData{
    private Long id;
    private String name;
    private Integer age;

    /**
     * 必须提供默认构造函数
     */
    public SchoolBaseInfoBizData() {
    }

    public SchoolBaseInfoBizData(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public IBizData defaultBizData() {
        return null;
    }

    @Override
    public String format2String() {
        return new StringBuilder()
                .append("ID:").append(id)
                .append(",名称:").append(name)
                .append(",校龄:").append(age)
                .toString();
    }
}
