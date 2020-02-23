package com.dyz.pget.test.injector2;

import com.dyz.pget.core.BizDataInjector;
import com.dyz.pget.exception.BizDataFetchException;
import com.dyz.pget.test.TestBase;
import com.dyz.pget.test.bizdata.*;
import org.junit.Test;

/**
 * Created by daiyongzhi on 2020/2/23.<br/>
 * 使用BizDataInjector通过注入模式获取业务数据对象
 */
public class TestBizDataInjector extends TestBase{

    @Test
    public void testGetter() throws BizDataFetchException {
        long userId = 1345L;
        long schoolId = 11L;
        BizDataWrapper bizDataWrapper = new BizDataWrapper();
        //并行获取用户信息、以及三个学校相关的数据。并注入到bizDataWrapper中，以方便使用。超时时间是100毫秒

        //如果不使用分类注入模式
        /*BizDataInjector.build(bizDataWrapper)
                .inject(SchoolBaseInfoBizData.class,schoolId)
                .inject(SchoolLocationBizData.class,schoolId)
                .inject(SchoolTeachersBizData.class,schoolId)
                .inject(UserInfoBizData.class,userId)
                .doInject(100L);*/

        //使用分类注入模式
        BizDataInjector.build(bizDataWrapper)
                .inject(ISchoolBizData.class,schoolId)
                .inject(UserInfoBizData.class,userId)
                .doInject(100L);



        System.out.println(bizDataWrapper.getUserInfoBizData().format2String());
        System.out.println(bizDataWrapper.getSchoolBaseInfoBizData().format2String());
        System.out.println(bizDataWrapper.getSchoolLocationBizData().format2String());
        System.out.println(bizDataWrapper.getSchoolTeachersBizData().format2String());
    }

}
