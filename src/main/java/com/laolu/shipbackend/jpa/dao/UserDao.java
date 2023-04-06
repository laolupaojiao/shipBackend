package com.laolu.shipbackend.jpa.dao;

import com.laolu.shipbackend.jpa.entity.UserEntity;

import java.util.Optional;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/3 11:55
 */
public interface UserDao extends BaseDao<UserEntity, Integer> {

    /**
     * 账密查找
     * @param userName 账号
     * @param passWord 密码
     * @return 用户
     */
    Optional<UserEntity> findByUserNameAndPassWord(String userName, String passWord);
}
