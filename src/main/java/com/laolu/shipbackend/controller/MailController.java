package com.laolu.shipbackend.controller;

import com.laolu.shipbackend.model.RegCode;
import com.laolu.shipbackend.model.response.StarStoreListResponse;
import com.laolu.shipbackend.service.CacheService;
import com.laolu.shipbackend.utils.CommonResponse;
import com.laolu.shipbackend.utils.MailTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.laolu.shipbackend.utils.JsonResponse.GSON;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/19 13:05
 */

@RestController
@CrossOrigin
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    MailTool mailTool;
    @Autowired
    CacheService cacheService;
    @Autowired
    TemplateEngine templateEngine;

    @GetMapping("/code/{address}")
    public CommonResponse<String> getRegCode(@PathVariable String address) {
        RegCode redisMail = (RegCode) cacheService.getData(address);

        RegCode regCode = new RegCode();
        regCode.setTime(System.currentTimeMillis());
        String code;

        if(redisMail != null){
            if(System.currentTimeMillis() - redisMail.getTime() <60000){
                return CommonResponse.error("该邮箱地址请求过快！");
            } else {
                code = new Random().nextInt(899999)+100000+"";
                regCode.setCode(code);
            }
        } else {
            code = new Random().nextInt(899999)+100000+"";
            regCode.setCode(code);
        }

        Context context = new Context();
        context.setVariable("message", "您好，您正在进行游戏《我的舰长生涯》账号注册，请尽快操作，若非本人操作请忽视");
        context.setVariable("code", code);
        String mail = templateEngine.process("mailtemplate.html", context);

        if (mailTool.sendHtmlMail(address, "注册验证码-《我的舰长生涯》", mail)) {
            cacheService.setData(address, regCode);
            return CommonResponse.success("邮件发送成功");
        }
        return CommonResponse.error("邮件发送失败！");
    }
}
