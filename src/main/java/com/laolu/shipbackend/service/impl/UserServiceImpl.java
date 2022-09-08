package com.laolu.shipbackend.service.impl;

import com.laolu.shipbackend.dao.UserMapper;
import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.response.UserResponse;
import com.laolu.shipbackend.service.UserService;
import com.laolu.shipbackend.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/8 17:01
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(User user) throws IllegalAccessException {
        User result = userMapper.login(user);
        if (result != null) {
            UserResponse userResponse = new UserResponse();
            userResponse = (UserResponse) CommonTools.castAtoB(result, userResponse);
        }
        return userMapper.login(user);
    }
}
