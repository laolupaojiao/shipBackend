package com.laolu.shipbackend.utils;

import java.lang.reflect.Field;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/8 17:59
 */
public class CommonTools {
    /**
     * A类 转 B类
     * @param a 源类
     * @param b 目标类
     * @return Object
     */
    public static Object castAtoB(Object a, Object b) throws IllegalAccessException {
        Field[] aFields = a.getClass().getDeclaredFields();
        Field[] bFields = b.getClass().getDeclaredFields();

        for (Field aField: aFields
        ) {
            aField.setAccessible(true);
            String aFieldName = aField.getName();
            for (Field bField: bFields
            ) {
                String bFieldName = bField.getName();
                if (aFieldName.equals(bFieldName) && aField.getType() == bField.getType()) {
                    bField.setAccessible(true);
                    bField.set(b, aField.get(a));
                }
            }
        }
        return b;
    }
}
