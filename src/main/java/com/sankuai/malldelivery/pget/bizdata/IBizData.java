package com.sankuai.malldelivery.pget.bizdata;

/**
 * Created by daiyongzhi on 2019/12/4.
 * 实现此类的BizData必须提供无参构造函数。<br/>
 * 此对象的数据结构设计需遵守通用、直接的原则。
 */
public interface IBizData {
    /**
     * 获取此数据失败时，用于兜底的默认值<br/>
     * 如果不支持默认值，可以返回null
     * @return
     */
    IBizData defaultBizData();

    /**
     * 将自己格式化为字符串，方便日志打印查看
     * @return
     */
    String format2String();

}
