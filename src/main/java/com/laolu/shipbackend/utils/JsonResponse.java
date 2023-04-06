package com.laolu.shipbackend.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;

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
    public static final Gson GSON = new Gson();

    public static String success(Object result, String key) {
        Map<String , Object> map = new HashMap();
        map.put("code", 200);
        map.put("data",result);
        return AESTools.encrypt(GSON.toJson(map), key);
    }

    public static String success(Object result, int type, String key) {
        Map<String , Object> map = new HashMap();
        map.put("type", type);
        map.put("data",result);
        return AESTools.encrypt(GSON.toJson(map), key);
    }

    public static String message(int code, String message, String key) {
        Map<Object,Object> result = new HashMap<>();
        result.put("code",500);
        result.put("data",message);
        return AESTools.encrypt(GSON.toJson(result), key);
    }

}
