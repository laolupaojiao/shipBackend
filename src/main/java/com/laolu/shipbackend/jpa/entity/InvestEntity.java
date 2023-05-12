package com.laolu.shipbackend.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/25 16:39
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "ship_invest")
@Where(clause = "status = 1")
public class InvestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.laolu.shipbackend.jpa.CustomIDGenerator")
    @Column(name = "id", nullable = false, columnDefinition = "int comment '主键'")
    private Integer id;

    @Column(name = "user_id", columnDefinition = "int comment '用户ID'")
    private Integer userId;

    @Column(name = "star_id", columnDefinition = "int comment '星球ID'")
    private Integer starId;

    @Column(name = "amount", columnDefinition = "int default 0 comment '投资金额'")
    private Integer amount;

    @Column(insertable = false, name = "status", columnDefinition = "tinyint default 1 comment '状态'")
    private Integer status;

    @Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "TIMESTAMP default current_timestamp null comment '创建时间'")
    private Integer createTime;
}
