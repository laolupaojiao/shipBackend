package com.laolu.shipbackend.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/8 17:59
 */
public class CommonTools {

    public static <T> void copy(T source, T target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }
    public static <T> void copyWithIgnore(T source, T target, String... ignoreProperties) {
        String[] pns = getNullAndIgnorePropertyNames(source, ignoreProperties);
        BeanUtils.copyProperties(source, target, pns);
    }
    public static String[] getNullAndIgnorePropertyNames(Object source, String... ignoreProperties) {
        Set<String> emptyNames = getNullPropertyNameSet(source);
        emptyNames.addAll(Arrays.asList(ignoreProperties));
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    public static String[] getNullPropertyNames(Object source) {
        Set<String> emptyNames = getNullPropertyNameSet(source);
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    public static Set<String> getNullPropertyNameSet(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames;
    }


    /**
     * A类 转 B类
     * @param a 源类
     * @param b 目标类
     * @return Object
     */
    public static <T> T castAtoB(Object a, Class<T> b) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Field[] aFields = a.getClass().getDeclaredFields();
        Field[] bFields = b.getDeclaredFields();
        T t = b.getDeclaredConstructor().newInstance();
        for (Field aField: aFields
        ) {
            aField.setAccessible(true);
            String aFieldName = aField.getName();
            for (Field bField: bFields
            ) {
                String bFieldName = bField.getName();
                if (aFieldName.equals(bFieldName) && aField.getType() == bField.getType()) {
                    bField.setAccessible(true);
                    bField.set(t, aField.get(a));
                }
            }
        }
        return t;
    }
}
