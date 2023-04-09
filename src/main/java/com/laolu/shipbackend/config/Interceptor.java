package com.laolu.shipbackend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
        authorization = request.getHeader("Authorization");
        if (authorization != null) {
            String[] temp = authorization.split(" ");
            if (temp.length > 1) {
                token = temp[1];
            }
        }

        log.info("{} {} {}",request.getMethod(), request.getRequestURI().toLowerCase(), token);
        return true;
    }
}
