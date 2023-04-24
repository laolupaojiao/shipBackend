package com.laolu.shipbackend.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wanyi.lu
 * @date Created in 2022/12/21 16:09
 */

@Component
public class LockTools {

//    static RedissonClient redissonClient;
//
//    @Autowired
//    public void setWsService(RedissonClient redissonClient) {
//        LockTools.redissonClient = redissonClient;
//    }
//
//    public static boolean lock(String target) {
//        RLock lock = null;
//        try {
//            lock = redissonClient.getLock(target);
//            lock.lock();
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//
//    public static void unLock(Integer target) {
//    }
//
//    public static boolean unLock(String target) {
//        RLock lock = null;
//        try {
//            lock = redissonClient.getLock(target);
//            lock.unlock();
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
}
