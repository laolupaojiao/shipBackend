package com.laolu.shipbackend.dao;

import com.laolu.shipbackend.dao.provider.UserSqlProvider;
import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.request.store.BuyRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

@Mapper
public interface UserMapper {
    @Select("select * from old_ship_user where user_name=#{userName} and pass_word=#{passWord}")
    User login(User user);

    @Select("select * from old_ship_user where id=#{id} and money>#{money}")
    User getUserByIdWithMoneyCondition(Integer id, Integer money);


    @UpdateProvider(type = UserSqlProvider.class, method = "update")
    void update(User user);
}
