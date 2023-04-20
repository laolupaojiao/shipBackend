package com.laolu.shipbackend.model.request.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/19 11:09
 */

@Data
public class RegisterRequest {
    @Length(min = 6, max = 10, message = "账号在6-30字符")
    @NotEmpty
    private String userName;

    @NotEmpty(message = "密码不能为空！")
    private String passWord;

    @Email
    private String email;

    @Length(min = 2, max = 20, message = "昵称在6-30字符")
    @NotEmpty
    private String nickName;

    @NotEmpty
    private String code;
}
