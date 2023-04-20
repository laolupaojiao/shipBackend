package com.laolu.shipbackend.model.request.store;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author wanyi.lu
 * @date Created in 2022/12/21 15:56
 */
@Data
public class BuyRequest {
    private Integer targetId;

    @Min(message = "不能小于1！", value = 1)
    private Integer amount;

    @NotNull
    private Integer userId;
}
