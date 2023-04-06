package com.laolu.shipbackend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/11 22:46
 */
@Getter
@Setter
@ToString
public class Heartbeat {
    private Double posX;
    private Double posY;
    private Integer galaxyId;
    private Integer id;
}
