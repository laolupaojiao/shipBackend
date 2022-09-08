package com.laolu.shipbackend.utils;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * JsonResponse class
 *
 * @author wanyi-lu
 * @date 2022/8/18
 */
public class JsonResponse {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String success(Object result) {
        Map<String , Object> map = new HashMap<String , Object>(16);
        map.put("code", 200);
        map.put("data",result);
        map.put("time",simpleDateFormat.format(System.currentTimeMillis()));
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public static String message(int code, String message) {
        Map<Object,Object> result = new HashMap<>(16);
        result.put("code",code);
        result.put("data",message);
        result.put("time",simpleDateFormat.format(System.currentTimeMillis()));
        return new Gson().toJson(result);
    }

}
