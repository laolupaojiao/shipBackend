package com.laolu.shipbackend.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author wanyi.lu
 * @date Created in 2023/3/14 13:30
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "ship_store")
public class StoreEntity implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.laolu.shipbackend.jpa.CustomIDGenerator")
    @Column(name = "id", nullable = false, columnDefinition = "int comment '主键'")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(100) default '' comment '商品名称'")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(300) default '' comment '商品描述'")
    private String description;

    @Column(name = "pic", columnDefinition = "varchar(100) default '' comment '商品图形'")
    private String pic;

    @Column(name = "status", columnDefinition = "tinyint default 1 comment '状态'")
    private Integer status;
}
