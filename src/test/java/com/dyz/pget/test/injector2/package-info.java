/**
 * Created by daiyongzhi on 2020/2/23.
 * 此包用于展示业务数据分类注入模式<br/>
 * 有的时候，我们会调用下游很多个方法获取业务数据，但是他们的参数是一样的。<br/>
 * 我们可以把获取业务数据对象参数一致的业务数据对象作为一个分类。
 * 如案例中的SchoolBaseInfoBizData、SchoolLocationBizData、SchoolTeachersBizData，他们都是通过一个schoolId查询得到，
 * 于是我们定义了一个ISchoolBizData接口，此接口继承了IBizData，并让以上三个数据对象实现。<br/>
 * 具体看代码案例
 */
package com.dyz.pget.test.injector2;