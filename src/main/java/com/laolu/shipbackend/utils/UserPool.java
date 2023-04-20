package com.laolu.shipbackend.utils;


import com.laolu.shipbackend.model.User;

/**
 * @author wanyi.lu
 * @date Created in 2023/1/5 14:16
 */
public class UserPool {
    private static final InheritableThreadLocal<User> USER_HOLDER = new InheritableThreadLocal<>();

    public static void setUser(User user) {
        USER_HOLDER.set(user);
    }

    public static User getUser() {
        return USER_HOLDER.get();
    }
}
