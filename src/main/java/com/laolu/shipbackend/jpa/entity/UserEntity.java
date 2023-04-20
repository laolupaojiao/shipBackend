package com.laolu.shipbackend.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/3 11:44
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "ship_user")
public class UserEntity implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.laolu.shipbackend.jpa.CustomIDGenerator")
    @Column(name = "id", nullable = false, columnDefinition = "int comment '主键'")
    private Integer id;

    @Column(name = "user_name", columnDefinition = "varchar(255) default '' comment '用户名'")
    private String userName;

    @Column(name = "pass_word", columnDefinition = "varchar(300) default '' comment '密码'")
    private String passWord;

    @Column(name = "nick_name", columnDefinition = "varchar(300) default '' comment '昵称'")
    private String nickName;

    @Column(name = "email", columnDefinition = "varchar(300) default '' comment '邮箱'")
    private String email;

    @Column(name = "money",insertable = false , columnDefinition = "int default 0 comment '金钱'")
    private Integer money;

    @Column(name = "galaxy_id",insertable = false , columnDefinition = "int default 1 comment '星系ID'")
    private Integer galaxyId;

    @Column(name = "auth_key", columnDefinition = "varchar(300) default '' comment '解码'")
    private String authKey;

    @Column(name = "pos_x",insertable = false , columnDefinition = "double default 200 comment '世界X坐标'")
    private Double posX;

    @Column(name = "pos_y",insertable = false , columnDefinition = "double default 200 comment '世界Y坐标'")
    private Double posY;

    @Column(name = "avatar",insertable = false , columnDefinition = "varchar(100) default 'avatar1' comment '头像'")
    private String avatar;

    @Column(name = "move_level",insertable = false , columnDefinition = "int default 1 comment '移动等级'")
    private Integer moveLevel;

    @Column(name = "status",insertable = false , columnDefinition = "tinyint default 1 comment '状态'")
    private Integer status;
}
