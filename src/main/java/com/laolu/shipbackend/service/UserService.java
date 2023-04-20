package com.laolu.shipbackend.service;

import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.request.user.RegisterRequest;
import com.laolu.shipbackend.model.response.UserResponse;
import com.laolu.shipbackend.utils.CommonResponse;

import java.lang.reflect.InvocationTargetException;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/8 17:01
 */
public interface UserService {
    UserResponse login (User user);

    CommonResponse<String> register(RegisterRequest request);
}
