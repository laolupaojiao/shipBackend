package com.laolu.shipbackend.service.impl;

import com.laolu.shipbackend.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/8 17:28
 */

@Service
public class CacheServiceImpl implements CacheService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setData(String key,Object value){
        redisTemplate.opsForValue().set(key,value,30L, TimeUnit.MINUTES);
    }

    @Override
    public Object getData(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
