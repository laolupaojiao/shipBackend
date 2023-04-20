package com.laolu.shipbackend.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/19 15:21
 */

@Data
@NoArgsConstructor
public class BagItemResponse {
    private String name;
    private String description;
    private String pic;
    private Integer amount;
}
