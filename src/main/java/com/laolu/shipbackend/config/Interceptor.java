package com.laolu.shipbackend.config;

import com.laolu.shipbackend.model.SocketClient;
import com.laolu.shipbackend.socket.SocketHandle;
import com.laolu.shipbackend.utils.UserPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AuthInterceptor class
 *
 * @author wanyi lu
 * @date 2022/8/18
 */
@Component
@Slf4j
public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Headers","*");
        response.setHeader("Access-Control-Allow-Methods","*");
        response.setHeader("Vary","");

        // 获取用户信息
        String token = "";
        String authorization = "";
        // 下载token为url拼接
        authorization = request.getHeader("AuthToken");
        if (authorization != null) {
            token = authorization;

            if (SocketHandle.CLIENT_POOL.containsKey(authorization)) {
                SocketClient socketClient = SocketHandle.CLIENT_POOL.get(authorization);
                UserPool.setUser(socketClient.getUser());
            }
        }
        log.info("{} {} {}",request.getMethod(), request.getRequestURI().toLowerCase(), token);
        System.out.println(token);
        return true;
    }
}
