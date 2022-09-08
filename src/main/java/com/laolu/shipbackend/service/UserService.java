package com.laolu.shipbackend.service;

import com.laolu.shipbackend.model.User;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/8 17:01
 */
public interface UserService {
    User login (User user) throws IllegalAccessException;
}
