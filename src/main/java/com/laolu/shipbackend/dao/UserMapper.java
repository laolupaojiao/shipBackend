package com.laolu.shipbackend.dao;

import com.laolu.shipbackend.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from ship_user where user_name=#{userName} and pass_word=#{passWord}")
    User login(User user);
}
