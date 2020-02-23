package com.dyz.pget.test.bizdata.provider;

import com.dyz.pget.provider.annotation.BizDataProvider;
import com.dyz.pget.test.bizdata.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daiyongzhi on 2020/2/23.
 */
@BizDataProvider
public class TestBiz3DataProvider {

    /**
     * 异常要抛出，框架会捕获
     * @param schoolId
     * @return
     * @throws Exception
     */
    public SchoolBaseInfoBizData getSchoolBaseInfoBizData(long schoolId)throws Exception{
        //调用远程rpc或者其他数据源得到数据，并封装到SchoolBaseInfoBizData对象中
        return new SchoolBaseInfoBizData(schoolId,"清华",20);
    }

    public SchoolLocationBizData getSchoolLocationBizData(long schoolId)throws Exception{
        //调用远程rpc或者其他数据源得到数据，并封装到SchoolLocationBizData对象中
        return new SchoolLocationBizData("北京海淀",49.123234D,160.132324D);
    }

    public SchoolTeachersBizData getSchoolTeachersBizData(long schoolId)throws Exception{
        //调用远程rpc或者其他数据源得到数据，并封装到SchoolTeachersBizData对象中
        List<String> teachers = new ArrayList<>();
        teachers.add("王教授");
        teachers.add("李教授");
        teachers.add("赵教授");
        return new SchoolTeachersBizData(teachers);
    }

}
