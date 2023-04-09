package com.laolu.shipbackend.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.laolu.shipbackend.config.consts.ResponseCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;


/**
 * @author wanyi.lu
 * @date Created in 2023/1/10 13:51
 */

@Component
public class CommonResponse<T> implements Serializable {
    private static final long serialVersionUID = -7268040542410707954L;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 消息内容
     */
    private String message;

    /**
     * 数据内容，部分请求可能不返回此key，只含有message和code、timestamp
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 响应结果，200为请求成功，500为失败，501为运行中[定时任务]
     */
    private Integer code;

    /**
     * 时间
     */
    private String timestamp;

    public CommonResponse() {
    }

    public CommonResponse(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public CommonResponse(boolean success, String message, T data) {
        this.setMessage(message);
        this.setData(data);
    }

    public static CommonResponse success () {
        return success("请求成功！");
    }

    public static <T> CommonResponse<T> success (String message) {
        return baseCreate(ResponseCode.SUCCESS, message, null);
    }

    public static <T> CommonResponse<T> success (T data) {
        return baseCreate(ResponseCode.SUCCESS, "请求成功", data);
    }

    public static <T> CommonResponse<T> error (String message) {
        return baseCreate(ResponseCode.ERROR, message, null);
    }

    public static <T> CommonResponse<T> login (String message) {
        return baseCreate(400, message, null);
    }

    public static CommonResponse error () {
        return error("请求失败！");
    }

    public static <T> CommonResponse<T> running (String message) {
        return baseCreate(ResponseCode.RUNNING, message, null);
    }

    private static <T> CommonResponse<T> baseCreate(Integer code, String msg, T data) {
        CommonResponse<T> result = new CommonResponse<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setData(data);
        result.setTimestamp(simpleDateFormat.format(System.currentTimeMillis()));
        return result;
    }

    public CommonResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{" +
                "\"message\" :\"" + message + "\"" +
                ", \"code\" :" + code  +
                ", \"timestamp\" :\"" + timestamp + "\"" +
                '}';
    }
}
