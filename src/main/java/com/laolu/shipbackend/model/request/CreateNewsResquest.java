package com.laolu.shipbackend.model.request;

import lombok.Data;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/24 20:18
 */

@Data
public class CreateNewsResquest {
    Integer starId;
    Integer userId;
    String content;
}
