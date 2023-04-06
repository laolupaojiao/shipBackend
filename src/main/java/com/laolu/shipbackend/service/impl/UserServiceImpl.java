package com.laolu.shipbackend.service.impl;

import com.laolu.shipbackend.dao.UserMapper;
import com.laolu.shipbackend.jpa.dao.UserDao;
import com.laolu.shipbackend.jpa.entity.UserEntity;
import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.response.UserResponse;
import com.laolu.shipbackend.service.UserService;
import com.laolu.shipbackend.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/8 17:01
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public UserResponse login(User user) {
        Optional<UserEntity> userEntity = userDao.findByUserNameAndPassWord(user.getUserName(), user.getPassWord());
        if (userEntity.isPresent()) {
            UserResponse response = new UserResponse();
            CommonTools.copy(userEntity.get(), response);
            return response;
        }
        return null;
    }
}
