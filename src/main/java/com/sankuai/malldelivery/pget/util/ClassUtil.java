package com.sankuai.malldelivery.pget.util;

/**
 * Created by daiyongzhi on 2019/12/9.
 */
public class ClassUtil {



    public static Class convert2PackageType(Class type) {
        if(!type.isPrimitive()){
            throw new RuntimeException("此类不是一个基本类型:"+type.getName());
        }
        String typeName = type.getTypeName();
        if (typeName.equals("byte"))
            return Byte.class;
        if (typeName.equals("short"))
            return Short.class;
        if (typeName.equals("int"))
            return Integer.class;
        if (typeName.equals("long"))
            return Long.class;
        if (typeName.equals("char"))
            return Character.class;
        if (typeName.equals("float"))
            return Float.class;
        if (typeName.equals("double"))
            return Double.class;
        if (typeName.equals("boolean"))
            return Boolean.class;
        if (typeName.equals("void"))
            return Void.class;

        throw new RuntimeException("未知类型:"+type.getName());
    }
}
