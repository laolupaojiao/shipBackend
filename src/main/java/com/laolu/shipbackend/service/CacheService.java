package com.laolu.shipbackend.service;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/8 17:28
 */
public interface CacheService {
    /**
     * 设置data
     *
     * @param key key
     * @param value value
     */
    void setData(String key, Object value);

    /**
     * 获取data
     *
     * @param key key
     * @return Object
     */
    Object getData(String key);
}
