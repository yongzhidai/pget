package com.dyz.pget.test.bizdata;

import com.alibaba.fastjson.JSON;
import com.dyz.pget.bizdata.IBizData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daiyongzhi on 2020/2/23.
 * 学校老师
 */
public class SchoolTeachersBizData implements ISchoolBizData{
    private List<String> teacherList = new ArrayList<>();

    public SchoolTeachersBizData() {
    }

    public SchoolTeachersBizData(List<String> teacherList) {
        this.teacherList = teacherList;
    }

    @Override
    public IBizData defaultBizData() {
        return null;
    }

    @Override
    public String format2String() {
        return JSON.toJSONString(teacherList);
    }
}
