package com.laolu.shipbackend.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/8 17:58
 */
@Getter
@Setter
@ToString
public class UserResponse {
    private Integer id;
    private String userName;
    private String nickName;
    private String email;
    private Integer money;
    private Integer star;
    private Integer galaxyId;
    private String authKey;
    private String avatar;
    private Double posX;
    private Double posY;
}
