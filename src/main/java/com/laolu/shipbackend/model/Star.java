package com.laolu.shipbackend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/11 13:54
 */
@Getter
@Setter
@ToString
public class Star {
    private Integer id;
    private String name;
    private String description;
    private Float posX;
    private Float posY;
    private String pic;
    private Integer galaxyId;
}
