package com.laolu.shipbackend.model.response;

import lombok.Data;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/6 19:41
 */

@Data
public class StarStoreListResponse {
    private Integer id;
    private String name;
    private String description;
    private String pic;
    private Integer leftAmount;
    private Integer originAmount;
    private Integer price;
    private Integer type;
}
