package com.laolu.shipbackend.dao.provider;

import com.laolu.shipbackend.model.User;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;

/**
 * @author wanyi.lu
 * @date Created in 2023/1/4 11:33
 */
public class UserSqlProvider {
    public String update(User user) {
        return new SQL() {{
            UPDATE("ship_user");
            if (!ObjectUtils.isEmpty(user.getUserName())) {
                SET("user_name=#{userName}");
            }
            if (!ObjectUtils.isEmpty(user.getPassWord())) {
                SET("pass_word=#{passWord}");
            }
            if (!ObjectUtils.isEmpty(user.getAuthKey())) {
                SET("auth_key=#{authKey}");
            }
            if (!ObjectUtils.isEmpty(user.getNickName())) {
                SET("nick_name=#{nickName}");
            }
            if (!ObjectUtils.isEmpty(user.getAvatar())) {
                SET("avatar=#{avatar}");
            }
            if (!ObjectUtils.isEmpty(user.getEmail())) {
                SET("email=#{email}");
            }
            if (!ObjectUtils.isEmpty(user.getMoney())) {
                SET("money=#{money}");
            }
            if (!ObjectUtils.isEmpty(user.getGalaxyId())) {
                SET("galaxy=#{galaxy}");
            }
            if (!ObjectUtils.isEmpty(user.getPosX())) {
                SET("pos_x=#{posX}");
            }
            if (!ObjectUtils.isEmpty(user.getPosY())) {
                SET("pos_y=#{posY}");
            }
            if (!ObjectUtils.isEmpty(user.getStatus())) {
                SET("status=#{status}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }
}
