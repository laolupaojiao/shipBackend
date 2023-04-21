package com.laolu.shipbackend.model.response;

import lombok.Data;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/20 10:11
 */

@Data
public class GalaxyResponse {
    private Integer id;
    private String name;
    private String description;
    private Double posX;
    private Double posY;
    private String pic;
}
