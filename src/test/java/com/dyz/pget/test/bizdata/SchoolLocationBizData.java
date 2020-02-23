package com.dyz.pget.test.bizdata;

import com.dyz.pget.bizdata.IBizData;

/**
 * Created by daiyongzhi on 2020/2/23.
 * 学校位置信息
 */
public class SchoolLocationBizData implements ISchoolBizData{
    private String address;
    private Double latitude;
    private Double longitude;

    public SchoolLocationBizData() {
    }

    public SchoolLocationBizData(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public IBizData defaultBizData() {
        return null;
    }

    @Override
    public String format2String() {
        return new StringBuilder().append("地址：").append(address).toString();
    }
}
