package com.laolu.shipbackend.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wanyi.lu
 * @date Created in 2023/1/4 11:04
 */

@Getter
@Setter
public class Goods {
    private Integer id;
    private Integer starId;
    private String name;
    private Integer price;
    private Integer type;
    private Integer leftAmount;
    private Integer originAmount;
}
