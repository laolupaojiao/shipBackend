package com.laolu.shipbackend.service.impl;

import com.laolu.shipbackend.config.consts.CommonCode;
import com.laolu.shipbackend.dao.UserMapper;
import com.laolu.shipbackend.jpa.dao.UserDao;
import com.laolu.shipbackend.jpa.entity.QUserEntity;
import com.laolu.shipbackend.jpa.entity.UserEntity;
import com.laolu.shipbackend.model.RegCode;
import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.request.user.RegisterRequest;
import com.laolu.shipbackend.model.response.UserResponse;
import com.laolu.shipbackend.service.CacheService;
import com.laolu.shipbackend.service.UserService;
import com.laolu.shipbackend.utils.AESTools;
import com.laolu.shipbackend.utils.CommonResponse;
import com.laolu.shipbackend.utils.CommonTools;
import com.laolu.shipbackend.utils.MailTool;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    @Autowired
    CacheService cacheService;
    @Autowired
    JPAQueryFactory jpaQueryFactory;


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

    @Override
    public CommonResponse<String> register(RegisterRequest request) {
        RegCode redisMail = (RegCode) cacheService.getData(request.getEmail());
        if (redisMail == null || !redisMail.getCode().equals(request.getCode()) || System.currentTimeMillis() - redisMail.getTime() > 180000) {
            return CommonResponse.error("验证码错误或失效！");
        }

        UserEntity user = new UserEntity();
        user.setUserName(request.getUserName());
        user.setNickName(request.getNickName());
        user.setPassWord(AESTools.md5(request.getPassWord()));
        user.setEmail(request.getEmail());
        user.setAuthKey(AESTools.md5(String.valueOf(System.currentTimeMillis())).substring(0,16));
        System.out.println(user.getAuthKey());

        QUserEntity userEntity = QUserEntity.userEntity;
        Long check = jpaQueryFactory.select(Expressions.simpleTemplate(Long.class, "count(id)"))
                .from(userEntity).where(
                        userEntity.userName.eq(request.getUserName())
                                .or(userEntity.nickName.eq(request.getNickName()))
                ).fetchOne();

        if (check != null && check > 1) {
            return CommonResponse.error("用户名或昵称重复！");
        }

        userDao.save(user);
        return CommonResponse.success("注册成功！");
    }
}
