package com.laolu.shipbackend.controller;

import com.google.gson.Gson;
import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.request.user.RegisterRequest;
import com.laolu.shipbackend.model.response.UserResponse;
import com.laolu.shipbackend.service.UserService;
import com.laolu.shipbackend.utils.CommonResponse;
import com.laolu.shipbackend.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.laolu.shipbackend.utils.AESTools;

import java.util.Map;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/11 12:54
 */

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> data) throws Exception {
        String str = data.get("data");
        User req = new Gson().fromJson(AESTools.decrypt(str,"0D7FB71E8EC31E97"),User.class);
        req.setPassWord(AESTools.md5(req.getPassWord()));
        UserResponse user = userService.login(req);
        if(user == null) {
            return JsonResponse.message(500,"用户名或密码错误","0D70523E8EC31E97");
        } else {
            return JsonResponse.success(user,"0D70523E8EC31E97");
        }
    }

    @PostMapping("/reg")
    public CommonResponse<String> login(@Validated @RequestBody RegisterRequest request) {
        return userService.register(request);
    }
}
