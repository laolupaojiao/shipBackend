package com.laolu.shipbackend.service;

import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.response.UserResponse;

import java.lang.reflect.InvocationTargetException;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/8 17:01
 */
public interface UserService {
    UserResponse login (User user);
}
