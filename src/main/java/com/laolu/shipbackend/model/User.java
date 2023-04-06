package com.laolu.shipbackend.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/5 17:59
 */
@Getter
@Setter
@ToString
public class User {
    private Integer id;
    private String userName;
    private String passWord;
    private String nickName;
    private String email;
    private Integer money;
    private Integer galaxyId;
    private String authKey;
    private String avatar;
    private Double posX;
    private Double posY;
    private Integer status;
}
