package com.laolu.shipbackend.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author wanyi.lu
 * @date Created in 2023/3/13 17:52
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "ship_star")
@SQLDelete(sql = "UPDATE ship_star SET status=-1 WHERE id=?")
@Where(clause = "status=1")
public class StarEntity implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.laolu.shipbackend.jpa.CustomIDGenerator")
    @Column(name = "id", nullable = false, columnDefinition = "int comment '主键'")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(100) default '' comment '星球名称'")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(300) default '' comment '星球描述'")
    private String description;

    @Column(name = "pos_x", columnDefinition = "double default 0 comment '世界X坐标'")
    private Double posX;

    @Column(name = "pos_y", columnDefinition = "double default 0 comment '世界Y坐标'")
    private Double posY;

    @Column(name = "pic", columnDefinition = "varchar(100) default 'star1.png' comment '星球图片'")
    private String pic;

    @Column(name = "galaxy_id", columnDefinition = "int default 1 comment '星系ID'")
    private Integer galaxyId;

    @Column(name = "status", columnDefinition = "tinyint default 1 comment '状态'")
    private Integer status;
}
