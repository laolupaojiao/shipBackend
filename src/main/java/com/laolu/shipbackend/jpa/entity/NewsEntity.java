package com.laolu.shipbackend.jpa.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/20 13:15
 */
public class NewsEntity implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.laolu.shipbackend.jpa.CustomIDGenerator")
    @Column(name = "id", nullable = false, columnDefinition = "int comment '主键'")
    private Integer id;

    @Column(name = "user_id", columnDefinition = "int comment '用户ID'")
    private Integer userId;

    @Column(name = "store_id", columnDefinition = "int comment '商品ID'")
    private Integer storeId;

    @Column(name = "amount", columnDefinition = "int default 0 comment '数量'")
    private Integer amount;

    @Column(name = "status", columnDefinition = "tinyint default 1 comment '状态'")
    private Integer status;

    @Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "TIMESTAMP default current_timestamp null comment '创建时间'")
    private Integer createTime;
}
