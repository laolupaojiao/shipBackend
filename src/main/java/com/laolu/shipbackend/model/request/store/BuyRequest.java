package com.laolu.shipbackend.model.request.store;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wanyi.lu
 * @date Created in 2022/12/21 15:56
 */
@Getter
@Setter
public class BuyRequest {
    private Integer targetId;
    private Integer amount;
    private Integer userId;
}
