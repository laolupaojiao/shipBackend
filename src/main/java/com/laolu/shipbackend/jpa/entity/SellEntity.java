package com.laolu.shipbackend.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/6 18:56
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "ship_sell")
public class SellEntity implements BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.laolu.shipbackend.jpa.CustomIDGenerator")
    @Column(name = "id", nullable = false, columnDefinition = "int comment '主键'")
    private Integer id;

    @Column(name = "star_id", columnDefinition = "int comment '星球ID'")
    private Integer starId;

    @Column(name = "store_id", columnDefinition = "int comment '商品'")
    private Integer storeId;

    @Column(name = "price", columnDefinition = "int default 0 comment '金额'")
    private Integer price;

    @Column(name = "type", columnDefinition = "tinyint default 1 comment '类型，1出售，2收购'")
    private Integer type;

    @Column(name = "left_amount", columnDefinition = "int default 0 comment '剩余数量'")
    private Integer leftAmount;

    @Column(name = "origin_amount", columnDefinition = "int default 0 comment '初始数量'")
    private Integer originAmount;

    @Column(name = "status", columnDefinition = "tinyint default 1 comment '状态'")
    private Integer status;
}
