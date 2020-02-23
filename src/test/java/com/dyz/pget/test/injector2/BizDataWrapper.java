package com.dyz.pget.test.injector2;

import com.dyz.pget.test.bizdata.*;

/**
 * Created by daiyongzhi on 2020/2/23.
 * 用于将所有的BizData封装到一起的包裹对象
 */
public class BizDataWrapper {
    private UserInfoBizData userInfoBizData;
    private SchoolBaseInfoBizData schoolBaseInfoBizData;
    private SchoolLocationBizData schoolLocationBizData;
    private SchoolTeachersBizData schoolTeachersBizData;


    public UserInfoBizData getUserInfoBizData() {
        return userInfoBizData;
    }

    public SchoolBaseInfoBizData getSchoolBaseInfoBizData() {
        return schoolBaseInfoBizData;
    }

    public SchoolLocationBizData getSchoolLocationBizData() {
        return schoolLocationBizData;
    }

    public SchoolTeachersBizData getSchoolTeachersBizData() {
        return schoolTeachersBizData;
    }
}
